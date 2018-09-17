package com.innominds.team.nunit;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.testng.annotations.BeforeSuite;
import org.xml.sax.SAXException;

import com.innominds.team.frameworkengine.CommonUtils;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.utils.CommandRunner;
import com.innominds.team.utils.PropertyFileUtils;

public class WhiteTestRunner {
	public static boolean whitetestscompleted = false;
	public static String whitepath;
	
	
	public void runWhiteTests() {
		if(!whitetestscompleted) {
			try {
				PropertyFileUtils props = new PropertyFileUtils(
						CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
				String isWhite = props.getDataFromPropertyFile("TestStackWhite");
				if (Boolean.parseBoolean(isWhite)) {
					whitetestscompleted = runWhiteTestsAndIntegrate();
					System.out.println("White Execution completed");
					Thread.sleep(2000);
				}
					
			} catch (TransformerFactoryConfigurationError | ParserConfigurationException | SAXException | IOException
					| TransformerException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private boolean runWhiteTestsAndIntegrate() throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException {		
		whitepath = Constants.ENVIRONMENT_PROPERTIES_PATH+File.separator+"white"+File.separator;
		PropertyFileUtils props1 = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isWhiteIntegration = props1.getDataFromPropertyFile("TestStackWhite");
		if (Boolean.parseBoolean(isWhiteIntegration)) {			
			String nunitCmd = whitepath+"nunit3/nunit3-console.exe";
			String csproj = whitepath+"Demo/NUnit.OfficeAppsTests/NUnit.OfficeAppsTests/NUnit.OfficeAppsTests.csproj";
			String resultArg = "--result:"+whitepath+"Result.xml";
			CommandRunner c1 = new CommandRunner(nunitCmd, csproj, resultArg);
            Thread t1 = new Thread(c1);
            t1.start();
            long duration = System.currentTimeMillis();
            while (t1.isAlive()) {
            	//Max duration to check if thread has completed (30 minutes)
            	long MAX_DURATION = duration+1800000;
            	if (duration <= MAX_DURATION) {
            		duration = System.currentTimeMillis();
            		//Sleep for a minute and check again
            		Thread.sleep(60000);
            	}
            	else
            		break;
            }
		}
		whitetestscompleted = true;
		return whitetestscompleted;
	}
}
