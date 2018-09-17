package com.web.tests.apiTestCases.ifusion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.innominds.team.apiutils.TrustModifier;
import com.innominds.team.driverinit.DriverManager;

public class RestApiTests2 extends DriverManager{

	/**
	 * Gets the API Response.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Parameters({ "browser", "os" })
	@Test(groups = { "Smoke", "Sanity" })
	public void dsnDataWithAPIGet(String browser, String osName,ITestContext context) throws Exception {
		
		try {
		
			context.setAttribute("dpName", " ");
			browserName = browser;
			os = osName;
			URL url = new URL("https://172.17.17.61:8444/iscope-metadata/rest/resource/listallsoftdsn");
			//URL url = new URL("https://192.168.16.74:8444/iscope-metadata/rest/resource/listallsoftdsn");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			TrustModifier.relaxHostChecking(conn);

			conn.setRequestMethod("GET");// dynamic
			conn.setRequestProperty("Accept", "application/json");// dynamic
			//			conn.setRequestProperty("Accept", "X509Certificate");// dynamic

			System.out.println(conn.getResponseCode());

			if (conn.getResponseCode() != 200) {// code static
				throw new RuntimeException(" HTTP error code : " + conn.getResponseCode());

			}

			System.out.println("--"+conn.getResponseMessage());

			Scanner scan = new Scanner(conn.getInputStream());
			String entireResponse = new String();
			while (scan.hasNext())
				entireResponse += scan.nextLine();

			System.out.println("Response : " + entireResponse);
			scan.close();
			conn.disconnect();
			
	} catch (Exception e) {
		Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
	}

	}

	@Parameters({ "browser", "os" })
	@Test(groups = { "Smoke", "Sanity" })
	public void loginTestWithAPIPost(String browser, String osName,ITestContext context) throws Exception {
		
		try {
		
			context.setAttribute("dpName", " ");
			browserName = browser;
			os = osName;
			String url = "https://172.17.17.61:8444/iscope-security/rest/user/authenticate";
			//String url = "https://192.168.16.74:8444/iscope-security/rest/user/authenticate";
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
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
			
	} catch (Exception e) {

		Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
	}


	}	

}
