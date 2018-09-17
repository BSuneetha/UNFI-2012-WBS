package com.innominds.team.apiutils;

import java.util.ArrayList;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ResponseValidator {

	public void checkStatusEquals(Response res, int StatusCode) {
		Assert.assertEquals(StatusCode, res.getStatusCode(), "Status Check Failed!");

	}

	// Get Video Ids (For use case-1)
	public String getValue(JsonPath jp, String searchString) {
		String value = jp.get(searchString);
		return value;
	}

	// Get Video Ids (For use case-1)
	public String getIntegerValue(JsonPath jp, String searchString) {
		String value = jp.get(searchString).toString();
		return value;
	}

	// Get Video Ids (For use case-1)
	public ArrayList getList(JsonPath jp, String searchString) {
		ArrayList valueList = jp.get(searchString);
		return valueList;
	}

	// Get Video Ids (For use case-1)
	public String getValue(XmlPath xmlPath, String searchString) {
		String value = xmlPath.get(searchString);
		return value;
	}

	// Get Video Ids (For use case-1)
	public String getIntegerValue(XmlPath xmlPath, String searchString) {
		String value = xmlPath.get(searchString).toString();
		return value;
	}

	// Get Video Ids (For use case-1)
	public ArrayList getList(XmlPath xmlPath, String searchString) {
		ArrayList valueList = xmlPath.get(searchString);
		return valueList;
	}

}
