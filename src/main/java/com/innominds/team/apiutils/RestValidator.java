package com.innominds.team.apiutils;

import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;

public class RestValidator {

	private RestResponse response;

	/**
	 * Instantiates a new rest validator.
	 *
	 * @param response
	 *            the response
	 */
	RestValidator(RestResponse response) {
		this.response = response;
	}

	/**
	 * Expect code.
	 *
	 * @param expectedCode
	 *            the expected code
	 * @return the rest validator
	 */
	public RestValidator expectCode(int expectedCode) {
		Assert.assertEquals("Incorrect Response Code", expectedCode, response.getResponseCode());
		return this;
	}

	/**
	 * Expect message.
	 *
	 * @param message
	 *            the message
	 * @return the rest validator
	 */
	public RestValidator expectMessage(String message) {
		Assert.assertEquals("Incorrect Response Message", message, response.getResponseMessage());
		return this;
	}

	/**
	 * Expect header.
	 *
	 * @param headerName
	 *            the header name
	 * @param headerValue
	 *            the header value
	 * @return the rest validator
	 */
	public RestValidator expectHeader(String headerName, String headerValue) {
		Assert.assertEquals("Incorrect header - " + headerName, headerValue, response.getHeader(headerName));
		return this;
	}

	/**
	 * Expect headers.
	 *
	 * @param headers
	 *            the headers
	 * @return the rest validator
	 */
	public RestValidator expectHeaders(HashMap<String, String> headers) {
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			Assert.assertEquals("Incorrect header - " + key, headers.get(key), response.getHeader(key));
		}
		return this;
	}

	/**
	 * Expect in body.
	 *
	 * @param content
	 *            the content
	 * @return the rest validator
	 */
	public RestValidator expectInBody(String content) {
		Assert.assertTrue("Body doesnt contain string : " + content, response.getResponseBody().contains(content));
		return this;
	}

	/**
	 * Prints the body.
	 *
	 * @return the rest validator
	 */
	public RestValidator printBody() {
		System.out.println(response.getResponseBody());
		return this;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public RestResponse getResponse() {
		return response;
	}

}
