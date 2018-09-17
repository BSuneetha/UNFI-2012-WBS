package com.innominds.team.apiutils;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth;
import static io.restassured.RestAssured.oauth2;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.put;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.authentication.OAuthSignature;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Utils {

	public static String path = null;
	public static String jsonPathTerm;
	private String sessionId;

	// Sets Base URI
	public static void setBaseURI() {
		RestAssured.baseURI = "http://www.xyz.com";
	}

	// Sets base path
	public static void setBasePath(String basePathTerm) {
		basePath = basePathTerm;
	}

	// Reset Base URI (after test)
	public static void resetBaseURI() {
		RestAssured.baseURI = null;
	}

	// Reset base path
	public static void resetBasePath() {
		RestAssured.basePath = null;
	}

	// Sets ContentType
	public static void setContentType(ContentType Type) {
		given().contentType(Type);
	}

	// Sets Json path term
	public static void setJsonPathTerm(String jsonPath) {
		jsonPathTerm = jsonPath;
	}

	// Returns response
	public static Response getResponse() {
		// System.out.print("path: " + path +"\n");
		RestAssured.config = RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());

		if (!(path == null)) {
			return get(path);
		} else {
			return get();
		}

	}

	public static Response getResponse(String url, Map<String, Object> headers, Map<String, Object> parameters) {
		buildUrl(url, parameters);
		// System.out.print("path: " + path +"\n");
		if (!(path == null)) {
			//System.out.println("URL:" + url);
			return given().headers(headers).log().all().get(url);
		} else {
			return get();
		}
	}

	public static Response getResponse(String url, Map<String, Object> pathParms, Map<String, Object> headers,
			Map<String, Object> parameters) {
		buildUrl(url, parameters);
		// System.out.print("path: " + path +"\n");
		if (!(path == null)) {
			//System.out.println("URL:" + url);
			return given().headers(headers).log().all().get(url, pathParms);
		} else {
			return get();
		}
	}

	// Returns response
	public static Response postResponse() {
		// System.out.print("path: " + path +"\n");
		return post(path);
	}

	public static Response postResponse(String url, Map<String, Object> headers, Map<String, Object> parameters,
			File file) {
		// System.out.print("path: " + path +"\n");
		buildUrl(url, parameters);
		if (!(path == null)) {

			return given().headers(headers).body(file).log().all().post(url);
		} else {
			return post();
		}

	}

	public static Response postResponse(String url, Map<String, Object> headers, Map<String, Object> parameters,
			String bodyPayload) {
		buildUrl(url, parameters);
		if (!(path == null)) {
			return given().headers(headers).body(bodyPayload).post(url);
		} else {
			return post();
		}

	}

	public static Response putResponse(String url, Map<String, Object> headers, Map<String, Object> parameters,
			File file) {
		buildUrl(url, parameters);
		if (!(path == null)) {

			return given().headers(headers).body(file).put(url);
		} else {
			return put();
		}

	}

	public static Response putResponse(String url, Map<String, Object> headers, Map<String, Object> parameters,
			String bodyPayload) {
		buildUrl(url, parameters);
		if (!(path == null)) {

			return given().headers(headers).body(bodyPayload).put(url);
		} else {
			return given().body(bodyPayload).put();
		}

	}

	public static Response deleteResponse(String url, Map<String, Object> headers, Map<String, Object> parameters,
			String bodyPayload) {
		// System.out.print("path: " + path +"\n");
		buildUrl(url, parameters);
		if (!(path == null)) {

			return given().headers(headers).body(bodyPayload).delete(url);
		} else {
			return given().body(bodyPayload).delete();
		}

	}

	public static Response deleteResponse(String url, Map<String, Object> headers, Map<String, Object> parameters) {
		// System.out.print("path: " + path +"\n");
		buildUrl(url, parameters);
		if (!(path == null)) {

			return given().headers(headers).delete(url);
		} else {
			return given().delete();
		}

	}

	// Returns JsonPath object
	public static JsonPath getJsonPath(Response res) {
		String json = res.asString();
		return new JsonPath(json);
	}

	public static XmlPath getXMLPath(Response res) {
		String xml = res.asString();
		return new XmlPath(xml);
	}

	public static void setBaseURI(String uri) {
		RestAssured.baseURI = uri;

	}

	public static String buildUrl(String resource, Map<String, Object> parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append(resource);

		if (parameters != null && parameters.size() > 0) {
			sb.append("?");
			boolean firstParamDone = false;
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				if (firstParamDone)
					sb.append("&");
				else
					firstParamDone = true;

				try {
					String keyValue = URLEncoder.encode(entry.getKey(), "UTF-8");
					if (keyValue.endsWith("%3D")) {
						keyValue = keyValue.substring(0, keyValue.length() - 3);
						sb.append(keyValue + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
					} else if (keyValue.endsWith("%3C") || keyValue.endsWith("%3E")) {
						sb.append(keyValue + URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
					} else {
						sb.append(keyValue + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
					}

				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		}
		path = sb.toString();
		return sb.toString();
	}

	public static void setHeaders(Map<String, Object> headers) {
		// TODO Auto-generated method stub

		RestAssured.given().headers(headers);

	}

	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Set the session identifier after login to preserve it for further API
	 * calls.
	 * <p>
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static void setBasicAuth(String userName, String password) {
		RestAssured.authentication = basic(userName, password);
	}

	public static void setOAuth(String consumerKey, String consumerSecret, String accessToken, String secretToken) {
		RestAssured.authentication = oauth(consumerKey, consumerSecret, accessToken, secretToken);
	}

	public static void setOAuth(String consumerKey, String consumerSecret, String accessToken, String secretToken,
			OAuthSignature signature) {
		RestAssured.authentication = oauth(consumerKey, consumerSecret, accessToken, secretToken, signature);
	}

	public static void setOAuth2(String accessToken) {
		RestAssured.authentication = oauth2(accessToken);
	}

	public static void setOAuth2(String accessToken, OAuthSignature signature) {
		RestAssured.authentication = oauth2(accessToken, signature);
	}

	public static void setPathParam(String key, String value) {
		// TODO Auto-generated method stub
		given().pathParam(key, value);
	}
}
