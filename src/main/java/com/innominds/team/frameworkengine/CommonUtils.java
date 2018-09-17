package com.innominds.team.frameworkengine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CommonUtils class holds wrapper api build using java API's which are commonly
 * used for.
 *
 * @author paggrawal, Chaya Venkateswarlu
 */

@SuppressWarnings("deprecation")
public class CommonUtils {

	public static Logger logger = LogManager.getLogger(CommonUtils.class.getName());

	/**
	 * Split the string.
	 *
	 * @param stringToSplit
	 *            the string to split
	 * @param delimiter
	 *            the delimiter
	 * @return the string[]
	 */
	public static String[] splitString(String stringToSplit, String delimiter) {
		String[] flag = null;

		try {
			String[] result = stringToSplit.split(delimiter);
			if (result.length < 2) {
				throw new IllegalArgumentException("String not in correct format");
			} else {
				flag = result;
			}
		} catch (Throwable t) {
			logger.error("Unable to split the string-" + stringToSplit + " due to erro-", t);
		}
		return flag;
	}

	/**
	 * Gets the unique string of given length.
	 *
	 * @param ilength
	 *            the ilength
	 * @return the unique string
	 */
	public static String getUniqueString(int ilength) {
		return RandomStringUtils.randomAlphabetic(ilength);
	}
	
	/**
	 * Store data in prop.
	 *
	 * @param filePath
	 *            the file path
	 * @param key
	 * 
	 * @param value
	 */
	public static void updateDataInPropertiesFile(String filePath, String key, String value) 
	{
	
		try
		{
			Properties pop = new Properties();
			pop.load(new FileInputStream(new File(filePath)));
			pop.put(key, value);
			FileOutputStream output = new FileOutputStream(new File(filePath));
			pop.store(output, "This is overwrite file");	
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to udpate value in Properties file "+e.getMessage());
		}

	}
	
	/**
	 * check sorting of data in ascending  order
	 *
	 *@param array String array
	 * 
	 * @return boolean sorting order
	 */
	public static ArrayList<String> sortAscending(String[] list)
	{
		List<String> result = new ArrayList<String>();
		try
		{
			List<String> lists = Arrays.asList(list);

		    Collections.sort(lists, String.CASE_INSENSITIVE_ORDER);
		    for(String s : list)
		    {
		    	result.add(s.intern().trim());
		    }
			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to sort the arraylist "+e.getMessage());
		}
		return (ArrayList<String>) result;
	}

	/**
	 * check duplicate of data in array
	 *
	 *@param array String array
	 * 
	 * @return boolean 
	 */

	public static boolean isDuplicatesInArrayList(ArrayList<String> sortedData) 
	{
		boolean found = true;
		Set<String> set = new HashSet<String>();
		try
		{
			for (String name : sortedData) 
			{ 
				if (set.add(name) == false) 
				{
					return found = false;
				}
			}			
					
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to find duplicate data "+e.getMessage());
		}
		return found;
		
	}
	
	/**
	 * check sorting of data in descending order
	 *
	 *@param array String array
	 * 
	 * @return boolean sorting order
	 */
	public static List<String> sortDescending(String[] list)
	{
		List<String> result = new ArrayList<String>();
		try
		{
			List<String> lists = Arrays.asList(list);

		    Collections.sort(lists, String.CASE_INSENSITIVE_ORDER);
		    for(String s : list)
		    {
		    	result.add(s);
		    }
		    Collections.reverse(result);
			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to sort the arraylist "+e.getMessage());
		}
		return result;
	}

	/**
	 * Gets the domain with at the rate '@'.
	 *
	 * @param strEmailId
	 *            the str email id
	 * @return the domain with at the rate
	 */
	public static String getDomainwithAtTheRate(String strEmailId) {
		return strEmailId.substring(strEmailId.indexOf("@"));
	}

	/**
	 * Gets the current date in required date format.
	 *
	 * @param dateFormat
	 *            the date format
	 * @return the current date in required date format
	 */
	public static String getCurrentDateInRequiredDateFormat(String dateFormat) {
		String timeStamp = null;
		try {
			timeStamp = new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime());
		} catch (Throwable t) {
			logger.error("unable to format the given date format:" + dateFormat + " of the current date due to error",
					t);
		}

		return timeStamp;
	}

	/**
	 * Method to return past date in given format.
	 *
	 * @param strFormat
	 *            the str format
	 * @param iDays
	 *            the i days
	 * @return the past date
	 */
	public static String getPastDate(String strFormat, int iDays) {
		String pastDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -iDays);
			pastDate = dateFormat.format(cal.getTime());
		} catch (Throwable t) {
			logger.error("Previous date is specific format in throwing error");
		}

		return pastDate;
	}

	/**
	 * Wait using sleep.
	 *
	 * @param sleepWait
	 *            the sleep wait
	 */
	public static void syncUsingSleep(int sleepWait) {
		try {
			Thread.sleep(sleepWait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zip a file for given path.
	 *
	 * @param filepath
	 *            the filepath
	 */
	public static void zip(String filepath) {
		try {
			File inFolder = new File(filepath);
			File outFolder = new File(System.getProperty("user.dir") + "_" + "Reports" + ".zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data = new byte[1000];
			String files[] = inFolder.list();
			for (int i = 0; i < files.length; i++) {
				in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
				out.putNextEntry(new ZipEntry(files[i]));
				int count;
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the current working directory path.
	 */
	public static String getWorkingDir() {
		String workingDir = null;
		try {
			workingDir = new File(".").getCanonicalPath();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return workingDir;
	}

	/**
	 * Convert string to int.
	 *
	 * @param strValue
	 *            the str value
	 * @return the int
	 * @throws Exception
	 *             the exception
	 */
	public int convertStringToInt(String strValue) throws Exception {
		int value = 0;
		try {
			value = Integer.parseInt(strValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Convert int to string.
	 *
	 * @param iValue
	 *            the i value
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String convertIntToString(int iValue) throws Exception {
		String value = null;
		try {
			value = String.valueOf(iValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Generate random numbers.
	 *
	 * @param digits
	 *            the digits
	 * @return the string
	 */
	public static String generateRandomNumbers(int digits) {
		logger.info("Generate Random Numbers of " + digits + " Digits");
		Random r = new Random();
		long l = r.nextLong();
		//System.out.println("######################### GenerateRandomNumbers = " + l);
		//System.out.println("######################### GenerateRandomNumbers.l = " + String.format("%015d", l));
		String number = String.format("%015d", l).substring(1, digits + 1);
		//System.out.println(String.format("%015d", l).substring(1, digits + 1));
		//System.out.println("######################### GenerateRandomNumbers number = " + number);
		return number;
	}

	/**
	 * Generate positive random number.
	 *
	 * @param digits
	 *            the digits
	 * @return the string
	 */
	public String GeneratePositiveRandomNumber(int digits) {
		logger.info("Generate Random Numbers of " + digits + " Digits");
		Random r = new Random();
		int i = r.nextInt(9999);
		System.out.println("######################### GeneratePositiveRandomNumber i = " + i);
//		System.out.println(
//				"######################### GeneratePositiveRandomNumber format.l = " + String.format("%015d", i));
		String number = String.format("%015d", i).substring(1, digits + 1);
//		System.out.println(String.format("%015d", i).substring(1, digits + 1));
//		System.out.println("######################### GeneratePositiveRandomNumber number = " + number);
		return number;
	}

	/**
	 * Unique token.
	 * 
	 * @return the string
	 */
	public static String uniqueToken() {
		String lastToken = null;
		try {
			UUID randomUUID = UUID.randomUUID();
			String[] tokens = randomUUID.toString().split("-");
			lastToken = tokens[tokens.length - 1];
		} catch (Exception e) {
			throw new RuntimeException("Failed : to generate Unique Token" + e.getMessage());
		}
		return lastToken;
	}

	/*
	 * Generate Random Number
	 */
	public static int getRandomNumber() {
		int i = 0;
		try {
			Random r = new Random(System.currentTimeMillis());
			i = 10000 + r.nextInt(20000);
		} catch (Exception e) {
			throw new RuntimeException("Failed : to generate Unique Token" + e.getMessage());
		}
		return i;
	}

	/*
	 * Get folder path
	 */

	/**
	 * Gets the file path.
	 *
	 * @param folderName
	 *            the folder name
	 * @param fileName
	 *            the file name
	 * @return the file path
	 */
	public static String getFilePath(String folderName, String fileName) {
		String path = null;
		try {

			path = new File(folderName + fileName).getAbsolutePath();

		} catch (Exception e) {
			throw new RuntimeException("Failed : to get file path from Resource source folder" + e.getMessage());
		}
		return path;
	}

	/**
	 * Delete files.
	 * 
	 * @param path
	 *            the path
	 */

	public static void deleteFile(String path) {
		try {
			List<String> filesList = new ArrayList<String>();
			List<String> folderList = new ArrayList<String>();
			File file = new File(path);
			if (file.exists()) {
				fetchCompleteList(filesList, folderList, path);
				for (String filePath : filesList) {
					File tempFile = new File(filePath);
					tempFile.delete();
				}
				for (String filePath : folderList) {
					File tempFile = new File(filePath);
					tempFile.delete();
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed: to delete file from folder " + e.getMessage());
		}
	}

	/**
	 * Fetch complete list.
	 * 
	 * @param filesList
	 *            the files list
	 * @param folderList
	 *            the folder list
	 * @param path
	 *            the path
	 */
	private static void fetchCompleteList(List<String> filesList, List<String> folderList, String path) {
		try {
			File file = new File(path);
			File[] listOfFile = file.listFiles();
			for (File tempFile : listOfFile) {
				if (tempFile.isDirectory()) {
					folderList.add(tempFile.getAbsolutePath());
					fetchCompleteList(filesList, folderList, tempFile.getAbsolutePath());
				} else {
					filesList.add(tempFile.getAbsolutePath());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to fetch complete list from folder " + e.getMessage());
		}

	}

	/*
	 * Returns file type of excel
	 * 
	 * @param filename with path
	 */

	/**
	 * Gets the excel type.
	 *
	 * @param fileName
	 *            the file name
	 * @return the excel type
	 */
	public String getExcelType(String fileName) {
		String excelExtn = null;
		try {
			if (FilenameUtils.isExtension(fileName, ".xls")) {
				excelExtn = ".xls";
			} else {
				excelExtn = ".xlsx";
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed: to get Excel File Name extension " + e.getMessage());
		}
		return excelExtn;
	}

	/*
	 * Returns file extension
	 * 
	 * @param filename with path
	 */

	/**
	 * Gets the file extn.
	 *
	 * @param fileName
	 *            the file name
	 * @return the file extn
	 */
	public static String getFileExtn(String fileName) {
		String fileExtension = "";
		try {
			String fn = new File(fileName).getName();
			if (fn.contains(".") && fn.lastIndexOf(".") != 0) {
				fileExtension = fn.substring(fn.lastIndexOf(".") + 1);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get Excel File Name extension " + e.getMessage());
		}
		return fileExtension;
	}

	/*
	 * Store properties file data into Hashmap
	 * 
	 * @param - path of file
	 * 
	 */

	/**
	 * Store prop into map.
	 *
	 * @param filePath
	 *            the file path
	 * @return the hash map
	 */
	public static HashMap<String, String> storePropIntoMap(String filePath) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			Properties prop = new Properties();
			FileInputStream ip = new FileInputStream(new File(filePath));
			prop.load(ip);
			Set<Object> keys = prop.keySet();
			for (Object k : keys) {
				map.put((String) k, prop.getProperty((String) k));
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get Excel File Name extension " + e.getMessage());
		}
		return map;
	}

	public static String getSysTime() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String date = sdf.format(cal.getTime());
		return date;
	}

}
