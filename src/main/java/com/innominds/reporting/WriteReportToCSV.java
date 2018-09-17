package com.innominds.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.testng.Assert;

public class WriteReportToCSV {

	private static final char DEFAULT_SEPARATOR = ',';
	private static String file = new String();
	private static FileWriter w;

	public static String getCSVFilePath() throws IOException {

		return file;

	}

	public static FileWriter getCSVFileObj() throws IOException {

		return w;

	}

	public static void createCSVReport(String... values) {
		String fn = WriteLogsInXLFile.getLogFilePath();
		//Empty or null values filled with empty space
		for (int i=0; i<values.length; i++) {
			if (values[i]==null || values[i].isEmpty()) 
				values[i] = "NA";
		}
		
		if (fn.contains(".xlsx"))
			file = fn.substring(0, fn.length() - 5) + ".csv";
		else
			file = fn + ".csv";
		if (file != null) {
			try {
				writeLine(file, ',', values);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	static boolean firstrow = false;
	public static void writeLine(String filename, char separators, String... values)
			throws IOException {
		
		boolean first = true;
		// default customQuote is empty
		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();

		for (int i=0; i<values.length; i++) {
			if (!first) {
				sb.append(separators);
			}
			if (i==7){
				values[i] = values[i].replaceAll(System.lineSeparator(), "");
				//System.out.println(values[i]);
				values[i] = values[i].replace('\n', ' ');
				values[i] = values[i].replace('\r', ' ');
				values[i] = values[i].replace('\t', ' ');
				values[i] = values[i].replace(',', ' ');
				values[i] = values[i].replaceAll("\"", "\"\"");
				//values[i] = values[i].replace('\n', ' ');
				//System.out.println(values[i]);
				//values[i] = values[i].replaceAll(, replacement)
				values[i]='"'+values[i]+'"';
			}

			sb.append(values[i]);
			first = false;
		}

		File f1 = new File(filename);
		if (!f1.exists()) {
			String values1[] = { "Senario Name,", "Test Name,", "Browser Name,", "Os Name,", "Test Status,", "Test Data Row,",
					"Link to Screen Shot,", "Error Log,", "Test Type" };
			StringBuilder sb1 = new StringBuilder();
			for (String sv:values1) {
				sb1.append(sv);
			}
			try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
				out.println(sb1);
			}
		}
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {

			out.println(sb);
			out.flush();
			out.close();
		} catch (IOException e) {
			Assert.fail("Error during File IO: -error with file##" + filename);
		}
	}

}
