package com.web.tests.apiTestCases.ifusion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.innominds.team.apiutils.RestResponse;
import com.innominds.team.apiutils.TrustModifier;
import com.innominds.team.driverinit.DriverManager;

public class RestApiTests extends DriverManager{

	/**
	 * Gets the API Response.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Parameters({ "browser", "os" })
	@Test(groups = { "Smoke", "Sanity" })
	public void dsnDataWithAPIGet(String browser, String osName,ITestContext context) throws Exception {

		String dataToBeValidated="dsn_name\":\"hive_dsn_test";
		boolean contentValidationFlag=false;
		try {			

			context.setAttribute("dpName", " ");
			browserName = browser;
			os = osName;
			//			URL url = new URL("https://172.17.17.61:8444/iscope-metadata/rest/resource/listallsoftdsn");
			URL url = new URL("https://104.211.243.134:8444/iscope-metadata/rest/resource/listallsoftdsn");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			TrustModifier.relaxHostChecking(conn);

			conn.setRequestMethod("GET");// dynamic
			conn.setRequestProperty("Accept", "application/json");// dynamic

			if (conn.getResponseCode() != 200) {// code static
				throw new RuntimeException(" HTTP error code : " + conn.getResponseCode());

			}

			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				if(inputLine.contains(dataToBeValidated)) {
					contentValidationFlag=true;
					break;
				}
			}
			in.close();
			conn.disconnect();

		} catch (Exception e) {
			Assert.assertTrue(contentValidationFlag, "out put does not conatin " +dataToBeValidated + e.getMessage());
		}

	}

	@Parameters({ "browser", "os" })
	@Test(groups = { "Smoke", "Sanity" })
	public void loginTestWithAPIPost(String browser, String osName,ITestContext context) throws Exception {

		String dataToBeValidated="token";
		boolean contentValidationFlag=false;

		try {

			context.setAttribute("dpName", " ");
			browserName = browser;
			os = osName;
			//			String url = "https://172.17.17.61:8444/iscope-security/rest/user/authenticate";
			String url = "https://104.211.243.134:8444/iscope-security/rest/user/authenticate";

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			TrustModifier.relaxHostChecking(con);

			//add reuqest header
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestMethod("POST");
			String urlParameters = "username=admin&password=admin";


			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println(con.getResponseMessage());
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();		

			while ((inputLine = in.readLine()) != null) {
				if(inputLine.contains(dataToBeValidated)) {
					contentValidationFlag=true;
					break;
				}
			}
			in.close();


		} catch (Exception e) {
			Assert.assertTrue(contentValidationFlag, "out put does not conatin " +dataToBeValidated + e.getMessage());
		}


	}	

}
