package com.innominds.team.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CSVUtils.
 */
/*
 * CSVUtils - Reading, writing of data into it
 * 
 * 
 * @author Chaya Venkateswarlu
 */
public class CSVUtils {

	/** The Constant DEFAULT_SEPARATOR. */
	private static final char DEFAULT_SEPARATOR = ',';

	/*
	 * Reading of CSV File
	 * 
	 * @param Path of File
	 */

	/**
	 * Read CSV file to list.
	 *
	 * @param filePath
	 *            the file path
	 * @return the array list
	 */
	@SuppressWarnings("resource")
	public ArrayList<String> readCSVFileToList(String filePath) {
		ArrayList<String> list = new ArrayList<String>();
		String size = null;
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);

			while ((size = br.readLine()) != null) {
				String[] csvData = size.split("\\s*, \\s*");
				for (int i = 0; i < csvData.length; i++) {
					if (!(csvData[i] == null) || !(csvData[i].length() == 0)) {
						list.add(csvData[i].trim());
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed : to load CSV File " + e.getMessage());
		}
		return list;
	}

	/*
	 * Writing to CSV File
	 * 
	 * @param list of values to add
	 * 
	 * @param chars separation
	 * 
	 * @param customQuote is empty by default, mention any expression to list
	 * 
	 */

	/**
	 * Write line.
	 *
	 * @param values
	 *            the values
	 * @param separators
	 *            the separators
	 * @param customQuote
	 *            the custom quote
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("null")
	public static void writeLine(List<String> values, char separators, char customQuote) throws IOException {

		boolean first = true;
		FileWriter w = null;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());

	}

	/**
	 * Follow CV sformat.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

}
