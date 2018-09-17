package com.web.tests.unfi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.frameworkengine.DataInjection;

public class LoginDP extends DataInjection {

	Logger logger = LogManager.getLogger(LoginDP.class.getName());

	public Hashtable<String, String> td;
	HashMap<String, Map<String, String>> or;
	HashMap<String, String> config;
	String sheetName;

	public LoginDP(String orPath, String tdPath, String configPath, String datarows) {

		super(orPath, tdPath, configPath);
		this.sheetName = "Login";
		this.td = _testData(sheetName, prop.getDataFromPropertyFile(datarows));
		this.td = _testData(sheetName, datarows);
		this.or = _objRepository(sheetName);
		this.config = _configData();
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setORData(HashMap<String, Map<String, String>> or) {
		this.or = or;
	}

	public HashMap<String, Map<String, String>> getORData() {
		return or;
	}

	public void setTDData(Hashtable<String, String> td) {
		this.td = td;
	}

	public Hashtable<String, String> getTDData() {
		return td;
	}

	public void setConfigData(HashMap<String, String> config) {
		this.config = config;
	}

	public HashMap<String, String> getConfigData() {
		return config;
	}

	public static Object[][] createDP(String datarows) {

		String orPath = Constants.WEB_OBJECT_REPO_FILE_PATH + "Web_Unfi_OR.xls";
		String tdPath = Constants.TESTDATA_FILE_PATH + "TestData_Unfi.xlsx";
		String configPath = Constants.ENVIRONMENT_PROPERTIES_PATH + "web.properties";

		String[] datarowsArray = datarows.split(",");
		Object[][] xx = new Object[datarowsArray.length][1];
		int i = 0;
		for (String dataRow : datarowsArray) {

			xx[i][0] = new LoginDP(orPath, tdPath, configPath, dataRow);
			i++;
		}

		return xx;

	}

}