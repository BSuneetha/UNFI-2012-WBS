package com.innominds.team.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.innominds.team.frameworkengine.Constants;

/*
 * Property File Reader and Writer API's
 * 
 * @author Chaya Venkateswarlu
 * 
 */

/**
 * The Class PropertyFileUtils.
 */
public class PropertyFileUtils {

	static Properties properties = new Properties();
	public FileInputStream fis;
	protected String filePath;

	/**
	 * Instantiates a new property file utils.
	 *
	 * @param path
	 *            the path
	 */
	public PropertyFileUtils(String path) {
		try {
			this.filePath = path;
			String configFilePath = new File(path).getAbsolutePath();
			fis = new FileInputStream(configFilePath);
			properties.load(fis);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load Properties file: " + e.getMessage());
		}

	}

	/**
	 * Gets the property.
	 *
	 * @param key
	 *            the key
	 * @return the property
	 */
	public String getDataFromPropertyFile(String key) {

		String value = "";
		try {
			value = properties.getProperty(key);
		} catch (Exception e) {
			throw new RuntimeException("Failed: to read Properties file data " + e.getMessage());
		}
		return value;
	}

	/**
	 * Sets the property.
	 *
	 * @param path
	 *            the path
	 * @param propertyName
	 *            the property name
	 * @param propertyValue
	 *            the property value
	 */
	public static void setProperty(String path, String propertyName, String propertyValue) {
		InputStream projectStream = null;
		FileOutputStream outStream = null;
		try {
			java.util.Properties projectProperties = new java.util.Properties();
			projectStream = new FileInputStream(new File(path));
			projectProperties.load(projectStream);

			outStream = new FileOutputStream(path);
			projectProperties.setProperty(propertyName, propertyValue);
			projectProperties.store(outStream, null);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (projectStream != null) {
				try {
					projectStream.close();
				} catch (IOException e) {
					//System.out.println(e.getStackTrace());
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					//System.out.println(e.getStackTrace());
				}
			}
		}
	}

	/**
	 * Gets the prop values from config.
	 *
	 * @param propertyPath
	 *            the property path
	 * @param propertyKey
	 *            the property key
	 * @return the prop values from config
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@SuppressWarnings("unused")
	public static String getPropValuesFromConfig(String propertyPath, String propertyKey) throws FileNotFoundException {
		Map<String, String> propMap = new HashMap<String, String>();
		String propFileName = null;

		FileInputStream fileInput = new FileInputStream(propertyPath);

		if (propertyPath != null) {
			try {
				properties.load(fileInput);
			} catch (Exception e) {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		}
		return (String) properties.get(propertyKey);
	}

	/**
	 * Gets the prop values from env config.
	 *
	 * @param propertyKey
	 *            the property key
	 * @param environment
	 *            the environment
	 * @return the prop values from env config
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@SuppressWarnings("unused")
	public static String getPropValuesFromEnvConfig(String propertyKey, String environment)
			throws FileNotFoundException {
		Map<String, String> propMap = new HashMap<String, String>();
		String propFileName = null;

		if (environment.trim().equalsIgnoreCase("Testing") || environment.trim().equalsIgnoreCase("QA")) {
			propFileName = Constants.WEB_PROPERTIES_FILE;
		}

		if (environment.trim().equalsIgnoreCase("DB") || environment.trim().equalsIgnoreCase("database")
				|| environment.trim().equalsIgnoreCase("Database")) {
			propFileName = Constants.DB_PROPERTIES_FILE;
		}

		if (environment.trim().equalsIgnoreCase("android")) {
			propFileName = Constants.ANDROID_PROPERTIES_FILE;
		}

		if (environment.trim().equalsIgnoreCase("ios")) {
			propFileName = Constants.IOS_PROPERTIES_FILE;
		}

		FileInputStream fileInput = new FileInputStream(propFileName);

		if (propFileName != null) {
			try {
				properties.load(fileInput);
			} catch (Exception e) {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		}
		return (String) properties.get(propertyKey);
	}

}
