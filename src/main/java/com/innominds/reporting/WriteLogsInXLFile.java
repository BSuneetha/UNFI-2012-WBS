package com.innominds.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.innominds.team.utils.DateTime;

public class WriteLogsInXLFile {

	public static XSSFWorkbook readFile(String filename) throws IOException {
		Object obj = new Object();
		synchronized(obj) {
			return new XSSFWorkbook(new FileInputStream(filename));
		}
	}

	public static String getCurrentProjeDir() {

		return System.getProperty("user.dir");

	}

	private static String sDate = DateTime.getDate(), sFileName;
	private static String sDateTime = String.valueOf(System.nanoTime());

	public static String projectPath = getCurrentProjeDir();

	public static String slogFilePath = projectPath + "/logs/ITAFLogs_" + sDateTime + ".xlsx";

	public static String slogFilePathdir = projectPath + "/logs";
	public static int pass_Count, fail_Count = 0;
	public static int passed_TestCases, failed_TestCases = 0;
	public static int totalTestCases = 0;
	public static int xx, yy = 0;
	public static int x = 0;
	// Write the logs to a file.

	public static void writeLogs(String senarioName, String testName, String browserName, String osName, String status,
			String testDataRow, String screenShotName, String errorLog, String groupName) throws NullPointerException {
		chkEmptyValues(senarioName, testName, browserName, osName, status,
				testDataRow, screenShotName, errorLog, groupName);
		System.setProperty("ResultPath", slogFilePath);
		createFolders(slogFilePathdir);
		FileOutputStream out; // declare a file out put object.

		if ("ie".equalsIgnoreCase(browserName)) {
			browserName = "Internet Explorer";

		} else if ("chrome".equalsIgnoreCase(browserName)) {
			browserName = "Chrome";

		} else if ("safari".equalsIgnoreCase(browserName)) {
			browserName = "Safari";

		}

		else if ("firefox".equalsIgnoreCase(browserName)) {
			browserName = "Firefox";

		}

		if ("mac".equalsIgnoreCase(osName)) {
			browserName = "MAC";

		} else if ("win10".equalsIgnoreCase(browserName)) {
			browserName = "WIN10";

		}

		try {

			// Check if the directory structure in the logFilePath exists either

			// create it

			XSSFWorkbook wb = null;
			Sheet s1 = null;
			File file = new File(slogFilePath);

			if (!file.exists()) {
				wb = new XSSFWorkbook();
				// create a new sheet
				s1 = wb.createSheet("Logs");

				Row row = s1.createRow((short) xx);
				xx++;
				Cell cell0 = row.createCell(0);
				Cell cell1 = row.createCell(1);
				Cell cell2 = row.createCell(2);
				Cell cell3 = row.createCell(3);
				Cell cell4 = row.createCell(4);
				Cell cell5 = row.createCell(5);
				Cell cell6 = row.createCell(6);
				Cell cell7 = row.createCell(7);
				Cell cell8 = row.createCell(8);

				cell0.setCellValue("Senario Name");
				cell1.setCellValue("Test Name");
				cell2.setCellValue("Browser Name");
				cell3.setCellValue("Os Name");
				cell4.setCellValue("Test Status");
				cell5.setCellValue("Test Data Row");
				cell6.setCellValue("Link to Screen Shot");
				cell7.setCellValue("Error Log");
				cell8.setCellValue("Type");

			} else {

				wb = WriteLogsInXLFile.readFile(slogFilePath);
				s1 = wb.getSheet("Logs");

			}

			Row row = s1.createRow((short) xx);
			xx++;
			pass_Count = xx;
			Cell cell0 = row.createCell(0);
			Cell cell1 = row.createCell(1);
			Cell cell2 = row.createCell(2);
			Cell cell3 = row.createCell(3);
			Cell cell4 = row.createCell(4);
			Cell cell5 = row.createCell(5);
			Cell cell6 = row.createCell(6);
			Cell cell7 = row.createCell(7);
			Cell cell8 = row.createCell(8);

			cell0.setCellValue(senarioName);
			cell1.setCellValue(testName);
			cell2.setCellValue(browserName);
			cell3.setCellValue(osName);
			cell4.setCellValue(status);
			cell5.setCellValue(testDataRow);
			cell6.setCellValue(screenShotName);
			cell7.setCellValue(errorLog);
			cell8.setCellValue(groupName);

			// Creating a file output stream.

			out = new FileOutputStream(slogFilePath);
			wb.write(out);
			out.close();

		} catch (Exception e) {
//			String s1 = e.getMessage();
//			String s2 = e.getStackTrace().toString();
//			System.out.println(s1 + s2);
			
			e.printStackTrace();
		}
	}

	private static void chkEmptyValues(String...values) {
		//Empty or null values filled with empty space
		for (int i=0; i<values.length; i++) {
			if (values[i]==null || values[i].isEmpty()) 
				values[i] = "NA";
		}
	}

	public static String createFolders(String sFilePath) {

		String sAbsolutePath = "";
		try {

			File file = new File(sFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			sAbsolutePath = file.getAbsolutePath();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return sAbsolutePath;
	}

	public static String getLogFilePath() {

		return slogFilePath.substring(0, slogFilePath.length() - 5);
	}

}
