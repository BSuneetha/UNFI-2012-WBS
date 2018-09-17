package com.innominds.team.listeners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.io.FileUtils;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Csv;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.xml.sax.SAXException;

import com.innominds.reporting.WriteLogsInXLFile;
import com.innominds.reporting.WriteReportToCSV;
import com.innominds.reporting.XMLCSVConverter;
import com.innominds.team.driverinit.AppiumServerManager;
import com.innominds.team.driverinit.DriverManager;
import com.innominds.team.frameworkengine.CommonUtils;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.frameworkengine.SendEmail;
import com.innominds.team.nunit.WhiteTestRunner;
import com.innominds.team.pjython.PythonJythonHandler;
import com.innominds.team.utils.CreateHTML;
import com.innominds.team.utils.CreateIssueInJira;
import com.innominds.team.utils.PropertyFileUtils;
import com.innominds.team.utils.ScreenRecord;
import com.innominds.team.utils.SimpleExcelReaderExample;

public class TestReportListner extends DriverManager implements ITestListener, ISuiteListener {

	static int passcount = 0;
	static int failcount = 0;
	static int skippedcount = 0;
	static int totalcount = 0;
	static float passPercentage;
	static float failPercentage;
	static int pyscriptcount = 0;
	
	String startTime = null;
	String EndTime = null;
	String osName = null;
	String browserName = null;
	String testName = null;
	String senarioName = null;
	String screenShotName = null;
	String[] classNameExtractorFromPack = null;
	String groupName = null;
	Boolean isRecordingEnabled = false;

	//public WebDriver driver1;

	@Override
	public void onTestStart(ITestResult arg0) {
		//driver1 = getCurrentDriver();
		
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		failcount++;
		totalcount++;
		List group = arg0.getTestContext().getCurrentXmlTest().getIncludedGroups();
		groupName = group.get(0).toString();
		browserName = arg0.getTestContext().getCurrentXmlTest().getParameter("browser");
		osName = arg0.getTestContext().getCurrentXmlTest().getParameter("os");
		testName = arg0.getMethod().getMethodName();
		senarioName = arg0.getInstanceName();
		classNameExtractorFromPack = senarioName.split("\\.");
		for (String className : classNameExtractorFromPack) {
			senarioName = className;
		}

		String userDir = System.getProperty("user.dir").toString().replace('\\', '/');
		screenShotName = "file://" + userDir + "/ScreenShots/" + senarioName + "_" + testName + "_" + osName + "_"
				+ browserName + ".png";
		//String screenshotfilename = senarioName + "_" + testName + "_" + osName + "_"
			//	+ browserName + ".png";
		String screenshotfilename = userDir + "/ScreenShots/" + senarioName + "_" + testName + "_" + osName + "_"
				+ browserName + ".png";
		
		String testDataRow;
		if (arg0.getTestContext().getAttribute("dpName")==null)
			testDataRow = "NA";
		else
			testDataRow = arg0.getTestContext().getAttribute("dpName").toString();
		
		
		//Results Holder utilized when Python/Jython scripts are integrated
		ArrayList<String> res11 = ResultsHolder.getResults();
		
		String errlog = arg0.getThrowable().toString();
		String s1 = arg0.getTestContext().getName().toLowerCase();
		if ((res11!=null) && (s1.contains("python"))) {	
			//Increment script count to indicate python/jython script being executed
			int mn=0;			
			for (String line: res11) {				
				String[] cols = line.split(",");
				if (cols[4].equalsIgnoreCase("fail")) {
					errlog = cols[7];
				}
				if (mn==0) {
					osName = cols[3];
					testDataRow = cols[5];
					browserName = cols[2];
				}
				mn++;
			}
		}
//		if (browserName!=null && browserName.toLowerCase().contains("api")) {
//			DriverManager.dr_count++;
//		}
//		WriteLogsInXLFile.writeLogs(senarioName, testName, browserName, osName, "Fail", testDataRow, screenShotName,
//				errlog, groupName);
		WriteReportToCSV.createCSVReport(senarioName, testName, browserName, osName, "Fail", testDataRow,
				screenShotName, errlog, groupName);
		PropertyFileUtils props = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isJIRA = props.getDataFromPropertyFile("JIRAIntegration");
		if (Boolean.parseBoolean(isJIRA)) {
			//30 seconds wait until screenshot is created successfully
			while (DriverManager.screenshotprocessing) {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			CreateIssueInJira issue = new CreateIssueInJira();
			issue.formJson(testName, arg0.getThrowable().toString(), screenshotfilename);
		}		

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		skippedcount++;
		totalcount++;

		List group = arg0.getTestContext().getCurrentXmlTest().getIncludedGroups();
		groupName = group.get(0).toString();
		browserName = arg0.getTestContext().getCurrentXmlTest().getParameter("browser");
		osName = arg0.getTestContext().getCurrentXmlTest().getParameter("os");

		testName = arg0.getMethod().getMethodName();
		senarioName = arg0.getInstanceName();
		classNameExtractorFromPack = senarioName.split("\\.");
		for (String className : classNameExtractorFromPack) {
			senarioName = className;
		}
		
		String testDataRow;
		if (arg0.getTestContext().getAttribute("dpName")==null)
			testDataRow = "NA";
		else
			testDataRow = arg0.getTestContext().getAttribute("dpName").toString();
//		if (browserName!=null && browserName.toLowerCase().contains("api")) {
//			DriverManager.dr_count++;
//		}
//		WriteLogsInXLFile.writeLogs(senarioName, testName, browserName, osName, "Skipped", testDataRow, "", "",
//				groupName);
		WriteReportToCSV.createCSVReport(senarioName, testName, browserName, osName, "Skipped", testDataRow, "", "",
				groupName);
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		totalcount++;
		List group = arg0.getTestContext().getCurrentXmlTest().getIncludedGroups();
		groupName = group.get(0).toString();
		browserName = arg0.getTestContext().getCurrentXmlTest().getParameter("browser");
		osName = arg0.getTestContext().getCurrentXmlTest().getParameter("os");

		testName = arg0.getMethod().getMethodName();
		senarioName = arg0.getInstanceName();
		classNameExtractorFromPack = senarioName.split("\\.");
		for (String className : classNameExtractorFromPack) {
			senarioName = className;
		}
		
//		if (browserName!=null && browserName.toLowerCase().contains("api")) {
//			DriverManager.dr_count++;
//		}
		String testDataRow;
		if (arg0.getTestContext().getAttribute("dpName")==null)
			testDataRow = "NA";
		else
			testDataRow = arg0.getTestContext().getAttribute("dpName").toString();
		//Results Holder utilized when Python/Jython scripts are integrated
		ArrayList<String> res11 = ResultsHolder.getResults();
		String passresult = "Pass";
		String errlog = "NA";
		//Flag to check if any python script test has failed
		boolean pyfail = false;
		String s1 = arg0.getTestContext().getName().toLowerCase();
		//System.out.println("%%%%%%%%%^^^^^^^^^^^"+s1+" flag "+s1.contains("python")+" Not flag "+!(s1.contains("python")));
		if ((res11!=null) && (s1.contains("python"))) {		
			//Python scripts counter
			int mn=0;			
			for (String line: res11) {
				
				String[] cols = line.split(",");
				if (cols[4].equalsIgnoreCase("fail")) {
					pyfail = true;
					passresult = "Fail";
					errlog = cols[7];
				} 
				if (mn==0) {
					osName = cols[3];
					testDataRow = cols[5];
					browserName = cols[2];
				}
				
				mn++;
			}
		} 
		if (pyfail) {
			failcount++;
		} else {
			passcount++;
		}
		//WriteLogsInXLFile.writeLogs(senarioName, testName, browserName, osName, passresult, testDataRow, "", errlog, groupName);
		WriteReportToCSV.createCSVReport(senarioName, testName, browserName, osName, passresult, testDataRow, "", errlog,
				groupName);
	}
	
	static Boolean isWhiteExecuted = false;
	static Boolean runWhiteOnceFlag = false;
	

	@Override
	public void onStart(ITestContext arg0) {
		
		startTime = CommonUtils.getCurrentDateInRequiredDateFormat("dd-MMM-yyyy hh:mm aaa");
		
		
		//Execution Video Recording Start...
		PropertyFileUtils props = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isRecording = props.getDataFromPropertyFile("ScreenRecord");
		isRecordingEnabled = Boolean.parseBoolean(isRecording);
		if (isRecordingEnabled) {
			try 
			{
				String path = System.getProperty("user.dir")+"\\RecordedExecution\\";
				File f = new File(path);
				File[] paths = f.listFiles();
				if(paths.length >5)
				{
					FileUtils.cleanDirectory(f); 
				}
				
				ScreenRecord.startRecording();
			} catch (Exception e) {
		
				e.printStackTrace();
			}
		}

	}

	static String finalhtmlreport;

	@Override
	public void onFinish(ITestContext arg0) {
		
		//System.out.println("Test Name "+arg0.getName()+" th_count# "+DriverManager.thr_count+" dr_count# "+DriverManager.dr_count+"Python script count: "+PythonJythonHandler.getCounter());
		Object obj = new Object();
		
		PropertyFileUtils props11 = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		boolean isPythonIntegration = Boolean.parseBoolean(props11.getDataFromPropertyFile("PythonIntegration"));
		boolean isBrowserTests = Boolean.parseBoolean(props11.getDataFromPropertyFile("BrowserTests"));
		PropertyFileUtils props12 = new PropertyFileUtils(Constants.WEB_PROPERTIES_FILE);
		boolean isGridEnabled = Boolean.parseBoolean(props12.getDataFromPropertyFile("GridExecution"));
		//If the current failed test is not a Python script
		//Standard to be followed where Test name in Testng.xml should have Python string for the name
		if(!(arg0.getName().toLowerCase().contains("python")) && !(arg0.getName().toLowerCase().contains("api"))) {
			if (DriverManager.isGrid==true) 
				DriverManager.thr_count--;
			else if (DriverManager.isGrid==false)
				DriverManager.dr_count--;	
		}
		//Handling Python scripts counter part here
		PropertyFileUtils props13 = new PropertyFileUtils(Constants.PYTHONJYTHON_PROPERTIES_FILE);
		if (isPythonIntegration) 
			pyscriptcount = Integer.parseInt(props13.getDataFromPropertyFile("count"));
		else {
			pyscriptcount = 0;
			PythonJythonHandler.setCounter(0);
		}
		boolean gridflag = ((DriverManager.isGrid==null)||(DriverManager.isGrid==false)) ? false : true;
		synchronized (obj) {
			if ((DriverManager.thr_count == 0) && (gridflag) && (pyscriptcount == PythonJythonHandler.getCounter())) {				
				try {
					if (isRecordingEnabled) 
						ScreenRecord.stopRecording();
					PropertyFileUtils props1 = new PropertyFileUtils(
							CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
					String isWhiteIntegration = props1.getDataFromPropertyFile("TestStackWhite");
					if (Boolean.parseBoolean(isWhiteIntegration)) {
						if (!AppiumServerManager.appiumStarted) {
							WhiteTestRunner wtr = new WhiteTestRunner();
							wtr.runWhiteTests();
						}
						int retries = 0;
						boolean iscomplete;
						if (AppiumServerManager.appiumStarted)
							iscomplete = AppiumServerManager.whitetestscompleted;
						else
							iscomplete = WhiteTestRunner.whitetestscompleted;
						while (!iscomplete) {
							if (retries<11) {
								//Wait 5 minutes with 11 retries for 55 minutes to allow white to complete
								Thread.sleep(300000);
								retries++;
							}
						}
						//Since white integration is turned -we wait for an Average duration of 
						//20 minutes (1200000) to allow Appium tests
						//to complete
						//Thread.sleep(1200000);
						whiteReportsStatusUpdate();
					}
					
					createReportsAndSendMail(isWhiteExecuted);					
					try {
						pushCSVToDashboard(startTime,EndTime, isWhiteExecuted);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				} catch (IOException | TransformerFactoryConfigurationError |  SQLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (!gridflag && (DriverManager.dr_count == 0) &&(pyscriptcount == PythonJythonHandler.getCounter())) {
				try {
					//Stop Execution video record
					if (isRecordingEnabled) 
						ScreenRecord.stopRecording();
					PropertyFileUtils props1 = new PropertyFileUtils(
							CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
					String isWhiteIntegration = props1.getDataFromPropertyFile("TestStackWhite");
					if (Boolean.parseBoolean(isWhiteIntegration)) {
						if (!AppiumServerManager.appiumStarted) {
							WhiteTestRunner wtr = new WhiteTestRunner();
							wtr.runWhiteTests();
						}
						int retries = 0;
						boolean iscomplete;
						if (AppiumServerManager.appiumStarted)
							iscomplete = AppiumServerManager.whitetestscompleted;
						else
							iscomplete = WhiteTestRunner.whitetestscompleted;
						while (!iscomplete) {
							if (retries<6) {
								//Wait 5 minutes with 5 retries for 25 minutes to allow white to complete
								Thread.sleep(300000);
								retries++;
							}
						}
						whiteReportsStatusUpdate();
					}
					createReportsAndSendMail(isWhiteExecuted);					
					try {
						pushCSVToDashboard(startTime,EndTime, isWhiteExecuted);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException | TransformerFactoryConfigurationError |  SQLException | InterruptedException | ParserConfigurationException | SAXException | TransformerException e) {

					e.printStackTrace();
				}
			}
			

		}

	}

//	private void closeOpenBrowsersAndQuit() {
//		// TODO Auto-generated method stub
//		if (!DriverManager.isGrid) {
//			
//		}
//	}

	private void pushCSVToDashboard(String stTime, String eTime, boolean isWhiteExecuted) throws SQLException {
		String fname = (isWhiteExecuted)? whitecsvfile: WriteLogsInXLFile.getLogFilePath()+".csv";
		PropertyFileUtils props = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isDashboard = props.getDataFromPropertyFile("Dashboard");
		if (Boolean.parseBoolean(isDashboard)) {
			JdbcConnectionPool cp;
			
			String tname = "TEST"+System.currentTimeMillis();
			//System.out.println("File for CSV Report: "+tname+".csv");
			PropertyFileUtils props1 = new PropertyFileUtils(
					CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.DASHBOARD_PROPERTIES_FILE));
			String JDBCURL = props.getDataFromPropertyFile("JDBCURL");
			String username = props.getDataFromPropertyFile("username");
			String password = props.getDataFromPropertyFile("password");
			cp = JdbcConnectionPool.create(JDBCURL, username, password);
			Connection cn = cp.getConnection();
			Statement st = cn.createStatement();
			createTableAndInsertValues(cn, fname, tname);			
			st.execute("UPDATE RECENT SET TSTAMP='"+tname+"', STTIME='"+stTime+"', ENDTIME='"+eTime+"' where ID=1");
			cn.close();
		}
	}

	private void createTableAndInsertValues(Connection cn, String fname, String tname) {
		try (FileReader reader = new FileReader(fname);
			BufferedReader br = new BufferedReader(reader);) {			
			String header = br.readLine();
			if (header!=null) {
				StringBuilder createSQL = new StringBuilder("create table "+tname+" (");
				String[] vals = header.split(",");
				//Varchar lengths for each column
				int[] varlengths = {60,60,30,30,30,25,200,3000,30};
				for (int i=0; i<vals.length; i++) {
					if (i==vals.length-1)
						createSQL.append("\""+vals[i]+"\""+" varchar("+varlengths[i]+")");
					else 
						createSQL.append("\""+vals[i]+"\""+" varchar("+varlengths[i]+"),");
				}			
				createSQL.append(");");
				PreparedStatement cSQL = cn.prepareStatement(createSQL.toString());
				cSQL.executeUpdate();
				PreparedStatement insertSQL = cn.prepareStatement
						("insert into "+tname+" values(?,?,?,?,?,?,?,?,?)");
				for (String col = br.readLine(); col!=null;) {						
					String[] vals1 = col.split(",");
					for (int i=0; i<vals1.length; i++) {						
						StringBuilder errlog = new StringBuilder();
						
						if (i==7) {
							if((i+2)<vals1.length) {
								for (int j = i; j<vals1.length-1;j++) {						
									errlog.append(vals1[j]+",");									
								}
								String err1 = errlog.substring(0, errlog.length()-1);									
								insertSQL.setString((i+1), err1);									
								insertSQL.setString((i+2), vals1[vals1.length-1]);
								break;
							} else {									
								insertSQL.setString((i+1), vals1[i]);
							}
						} else {								
							insertSQL.setString((i+1), vals1[i]);
						}
					}	
					col = br.readLine();
					insertSQL.executeUpdate();						
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private void createReportsAndSendMail(boolean isWhiteExecuted) throws IOException, SQLException {
		//For usage of latest CSV file to be read from JavaFX tool
		System.setProperty("CSVFile", WriteLogsInXLFile.getLogFilePath()+".csv");
		EndTime = CommonUtils.getCurrentDateInRequiredDateFormat("dd-MMM-yyyy hh:mm aaa");		
		String infile = System.getProperty("ResultPath");
		String finalReportFileName = "FinalReport" + System.currentTimeMillis();
		if (!isWhiteExecuted) {
			//System.out.println("-------Total rows: "+totalcount);
			passPercentage = (100 * passcount) / totalcount;
			failPercentage = (100 * failcount) / totalcount;
//			SimpleExcelReaderExample.convertResultReportToHtml(infile, finalReportFileName, startTime, EndTime,
//					passPercentage, failPercentage);
			Csv csvread = new Csv();
			FileReader reader = new FileReader(WriteLogsInXLFile.getLogFilePath()+".csv");
			String colNames[] = {"Senario Name","Test Name","Browser Name","Os Name","Test Status","Test Data Row","Link to Screen Shot","Error Log","Test Type"};
			ResultSet rs = csvread.read(reader, colNames);
			//Skip header
			rs.next();
			CreateHTML.convertResultReportToHtml(finalReportFileName, rs, startTime, String.valueOf(passPercentage), EndTime, String.valueOf(failPercentage));
			//For sending mail
			PropertyFileUtils props1 = new PropertyFileUtils(
					CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
			String isAutoEmail = props1.getDataFromPropertyFile("AutoEmail");
			if (Boolean.parseBoolean(isAutoEmail)) {
				SendEmail se = new SendEmail();
				try {
					finalhtmlreport = System.getProperty("user.dir") + "/Reports/" + finalReportFileName
							+ ".html";
					//System.out.println("##Email report name: " + finalhtmlreport);
					se.sendEmailReport(finalhtmlreport);
				} catch (InterruptedException e) {		
					e.printStackTrace();
				}
			}
		} else {
			String csv1 = WriteLogsInXLFile.getLogFilePath()+".csv";
			String csv2 = whitecsvfile;
			mergeCSVFilesAndClean(csv1, csv2);			
			Csv csvread = new Csv();
			FileReader reader = new FileReader(csv2);
			String colNames[] = {"Senario Name","Test Name","Browser Name","Os Name","Test Status","Test Data Row","Link to Screen Shot","Error Log","Test Type"};
			ResultSet rs = csvread.read(reader, colNames);
			//Skip header
			rs.next();
			CreateHTML.convertResultReportToHtml(finalReportFileName, rs, startTime, String.valueOf(passPercentage), EndTime, String.valueOf(failPercentage));
			//For sending mail
			PropertyFileUtils props1 = new PropertyFileUtils(
					CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
			String isAutoEmail = props1.getDataFromPropertyFile("AutoEmail");
			if (Boolean.parseBoolean(isAutoEmail)) {
				SendEmail se = new SendEmail();
				try {
					finalhtmlreport = System.getProperty("user.dir") + "/Reports/" + finalReportFileName
							+ ".html";
					System.out.println("##Email report name: " + finalhtmlreport);
					se.sendEmailReport(finalhtmlreport);
				} catch (InterruptedException e) {
		
					e.printStackTrace();
				}
			}
			
		}
	}
	private void mergeCSVFilesAndClean(String csv1, String csv2) {
		
		try {
			//Merge content from csv1 into csv2 ignoring headers of csv1
			BufferedReader bin = new BufferedReader(new FileReader(csv1));
			//Append mode
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv2, true));
			PrintWriter pw = new PrintWriter(bw);
			//Skip header
			bin.readLine();
			
			for (String inline=bin.readLine(); inline!=null;inline=bin.readLine()) {				
				pw.println(inline);
				pw.flush();
			}
			pw.close();
			bin.close();
			bw.close();
			//Replace csv1 with csv2 content
			bin = new BufferedReader(new FileReader(csv2));
			//Non append mode
			bw = new BufferedWriter(new FileWriter(csv1));
			pw = new PrintWriter(bw);			
			for (String inline=bin.readLine(); inline!=null;inline=bin.readLine()) {				
				pw.println(inline);
				pw.flush();
			}
			pw.close();
			bin.close();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//White Framework specific variables
	private String whitecsvfile;
	private String whitefinalReportName;
	private String whitepath;
	
	private void whiteReportsStatusUpdate() throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException {
		if (AppiumServerManager.appiumStarted) {
			whitepath = AppiumServerManager.whitepath;
			isWhiteExecuted = AppiumServerManager.whitetestscompleted;
		}
		else {
			whitepath = WhiteTestRunner.whitepath;
			isWhiteExecuted = WhiteTestRunner.whitetestscompleted;
		}		
		
		String xsl = whitepath+"converter.xsl";
        String xml = whitepath+"Result.xml";
    	whitecsvfile = whitepath+"outwhitereport.csv";
    	XMLCSVConverter xmcsv = new XMLCSVConverter();
        xmcsv.convert(xml, xsl, whitecsvfile);
        //Check for pass, fail values and update
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(whitecsvfile));
        String header = br.readLine();
        for (String line = br.readLine(); line!=null; line=br.readLine()) {
        	if (line.contains("Passed"))
        		line = line.replace("Passed", "Pass");
        	else if (line.contains("Failed"))
        		line = line.replace("Failed", "Fail");
        	lines.add(line);
        }
        br.close();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(whitecsvfile));
        PrintWriter pw = new PrintWriter(bw);
        //Write header
        pw.println(header);
        for (String s: lines) {
        	pw.println(s);
        }
        pw.flush();
        pw.close();
        bw.close();
        
        xsl = whitepath+"passfailextract.xsl";
        String whiteteststats = whitepath +"whiteteststats.csv";
        xmcsv.convert(xml, xsl, whiteteststats);
        br = new BufferedReader(new FileReader(whiteteststats));
        //Skip headers
        br.readLine();
        String result = br.readLine();
        String values[] = result.split(",");
        //System.out.println("##Before total "+totalcount);
        totalcount = totalcount + Integer.parseInt(values[2]);
        passcount = passcount + Integer.parseInt(values[3]);
        passPercentage = (100 * passcount) / totalcount;
		failPercentage = (100 * failcount) / totalcount;
        //System.out.println("$$After total "+totalcount);
	}
	

	@Override
	public String getCurrentProjectDirecotry() throws IOException {
		return new java.io.File(".").getCanonicalPath();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ISuite suite) {
		//startWhiteTests();
		
		//Optional method to automatically configure selenium grid hub and nodes
		configureSeleniumGrid();
		
	}

	private void configureSeleniumGrid() {
		// Setup selenium nodes and hub on remote windows and/or Linux instances
		
	}

	private void startWhiteTests() {
		//Start White Tests
		PropertyFileUtils props11 = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isWhiteIntegration = props11.getDataFromPropertyFile("TestStackWhite");
		Object obj11 = new Object();
		synchronized(obj11) {
			if (Boolean.parseBoolean(isWhiteIntegration)) {				
				if (!AppiumServerManager.appiumStarted && !runWhiteOnceFlag) {
					runWhiteOnceFlag = true;
					WhiteTestRunner wtr = new WhiteTestRunner();
					wtr.runWhiteTests();
				}
				int retries = 0;
				boolean iscomplete;
				if (AppiumServerManager.appiumStarted)
					iscomplete = AppiumServerManager.whitetestscompleted;
				else
					iscomplete = WhiteTestRunner.whitetestscompleted;
				while (!iscomplete) {
					if (retries<6) {
						//Wait 5 minutes with 5 retries for 25 minutes to allow white to complete
						try {
							Thread.sleep(300000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						retries++;
					}
				}
			}
		}
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

}
