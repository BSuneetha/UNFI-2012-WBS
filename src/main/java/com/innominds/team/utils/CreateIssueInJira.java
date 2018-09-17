package com.innominds.team.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.sasl.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

/**
 * This class creates JSON object and create issue in JIRA through restful
 * service
 * 
 * @author Pradeep Reddy K
 *
 */
@SuppressWarnings("deprecation")
public class CreateIssueInJira {

//	public static void main(String args[]) {
//		String scr="D:/streamlined/HarmonyCoreiFusion/ScreenShots/IFusionTest_createSource_WIN10_chrome.png";
//		String data = new String("{\"fields\":{\"summary\":\"#Automated loginApplication Milli secs Time: 1518414387802\",\"issuetype\":{\"id\":\"1\"},\"project\":{\"id\":\"12912\"},\"description\":\"#Automated java.lang.AssertionError: Test case failed due to exception no such element: Unable to locate element: {\"method\":\"id\",\"selector\":\"email\"}\n  (Session info: chrome=64.0.3282.140)\n  (Driver info: chromedriver=2.33.506120 (e3e53437346286c0bc2d2dc9aa4915ba81d9023f),platform=Windows NT 10.0.16299 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 0 milliseconds\nFor documentation on this error, please visit: http://seleniumhq.org/exceptions/no_such_element.html\nBuild info: version: '3.6.0', revision: '6fbf3ec767', time: '2017-09-27T15:28:36.4Z'\nSystem info: host: 'IM-LP-719', ip: '192.168.238.14', os.name: 'Windows 10', os.arch: 'amd64', os.version: '10.0', java.version: '1.8.0_131'\nDriver info: org.openqa.selenium.chrome.ChromeDriver\nCapabilities [{mobileEmulationEnabled=false, hasTouchScreen=false, platform=XP, acceptSslCerts=true, webStorageEnabled=true, browserName=chrome, takesScreenshot=true, javascriptEnabled=true, platformName=XP, setWindowRect=true, unexpectedAlertBehaviour=, applicationCacheEnabled=false, rotatable=false, networkConnectionEnabled=false, chrome={chromedriverVersion=2.33.506120 (e3e53437346286c0bc2d2dc9aa4915ba81d9023f), userDataDir=C:\\Users\\CPINNA~1\\AppData\\Local\\Temp\\scoped_dir8552_4238}, takesHeapSnapshot=true, pageLoadStrategy=normal, unhandledPromptBehavior=, databaseEnabled=false, handlesAlerts=true, version=64.0.3282.140, browserConnectionEnabled=false, nativeEvents=true, locationContextEnabled=true, cssSelectorsEnabled=true}]\nSession ID: 3e80b810d5d9092833e064bb4df6af2a\n*** Element info: {Using=id, value=email} expected [true] but found [false] Milli secs time: 1518414387802\",\"assignee\":{\"name\":\"cpinnamaraju\"}}}");
//		CreateIssueInJira cij = new CreateIssueInJira(); 
//		//cij.formJson("Fail1", "Chkthis1", "NA");
//		cij.formJson("Fail3-withattachment", "Chkthis2", scr);
//	}
	
	JsonParameters jsonParams = new JsonParameters();

	/**
	 * Forms JSON object for restful API input
	 * 
	 * @param failedMethodName
	 * @param failureMessage
	 * @param screenshotfilename
	 * @throws JSONException
	 */
	public void formJson(String failedMethodName, String failureMessage, String screenshotfilename)
			throws JSONException {

		JSONObject objFields = new JSONObject();

		JSONObject objProjID = new JSONObject();
		objProjID.put("id", jsonParams.getProjectID());

		objFields.put("project", objProjID);

		objFields.put("summary", "#Automated " + failedMethodName + " Milli secs Time: " + System.currentTimeMillis());

		JSONObject objAssigneName = new JSONObject();
		objAssigneName.put("name", jsonParams.getAssignee());

		objFields.put("assignee", objAssigneName);

		JSONObject objReporterName = new JSONObject();
		objReporterName.put("name", jsonParams.getReporter());

		/*
		 * objFields.put("reporter", objReporterName);
		 */
		if ((failureMessage == null) || (failureMessage.equals(""))) {
			failureMessage = "#Automated Jira with no error Milli secs time: " + System.currentTimeMillis();
		} else
			failureMessage = "#Automated " + failureMessage + " Milli secs time: " + System.currentTimeMillis();
		objFields.put("description", failureMessage);

		JSONObject objIssuType = new JSONObject();
		objIssuType.put("id", jsonParams.getIssueType());

		objFields.put("issuetype", objIssuType);

		JSONObject obj = new JSONObject();
		obj.put("fields", objFields);

		//System.out.println(obj);
		if (failedMethodName == null)
			failedMethodName = "Failed Method is unavailable-Check Advanced Logs to debug";
		if (screenshotfilename == "")
			screenshotfilename = "NA";
		createIssueInJira(failedMethodName, obj.toString(), screenshotfilename);

	}

	/**
	 * Creates Issue in JIRA through Restful API
	 * 
	 * @param string
	 *            - json object
	 * @param screenshotfilename
	 */
	private void createIssueInJira(String methodName, String string, String screenshotfilename) {
		boolean isScreenShotNA = false;
		String responseChk = "";
		String auth = "";
		if (string !=null) {
			try {
				String name = jsonParams.getUserName();
				String password = AES.decrypt(jsonParams.getPassWord(), "Innominds123$");
				// String authString = name + ":" + password;
				// byte[] authEncBytes = Base64.encode(authString.getBytes());
				auth = new String(Base64.encode(name + ":" + password));
				String data = string;
				Client client = Client.create();
				String url = jsonParams.getURL();
				WebResource webResource = client.resource(url);
				ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
						.accept("application/json").post(ClientResponse.class, data);
	
				int statusCode = response.getStatus();
				if (statusCode == 401) {
					throw new AuthenticationException("Invalid Username or Password");
				}
				responseChk = response.getEntity(String.class);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		org.json.simple.JSONObject jsonObject = null;
		JSONParser parser = new JSONParser(); // this needs the
												// "json-simple" library
		Object obj = null;
		try {
			obj = parser.parse(responseChk);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		jsonObject = (org.json.simple.JSONObject) obj;
		//Attach url containing JIRA id extracted. Also self should be appended with /attachments
		String key11 = (String) jsonObject.get("key");
		String attachUrl = "https://jira.innominds.com/rest/api/2/issue/"+key11+"/attachments";
		URL url = null;
		try {
			url = new URL(attachUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		Object jirauploadobj = new Object();
		synchronized(jirauploadobj) {
			final File uploadFile = new File(screenshotfilename);
	        try {
	            final HttpFileUploader http = new HttpFileUploader(url, "Basic " + auth);
	            http.addFilePart("file", uploadFile);
	            final byte[] bytes = http.finish();
	            try (final OutputStream os = new FileOutputStream(screenshotfilename)) {
	                os.write(bytes);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
}