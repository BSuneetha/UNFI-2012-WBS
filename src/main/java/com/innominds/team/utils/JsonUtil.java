package com.innominds.team.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.innominds.team.frameworkengine.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/*
 * Class - reading, manupulation, creating of json 
 * 
 * @Author :  Chaya Venkateswarlu
 * 
 */


/**
 * The Class JsonUtils.
 */
public class JsonUtil {
	
	
	
	
	/**
	 * Assert json client reponse.
	 *
	 * @param resourceAPI
	 *            the resource API
	 */
	/*
	 * Method:  gives Json Response status code
	 * @param: resourceAPI
	 */
	public void assertJsonClientReponse(String resourceAPI)
	{
		ClientResponse response = null;
		try
		{
			Client client = Client.create();
			WebResource webResource = client.resource(resourceAPI);
			response = webResource.accept("application/json").get(ClientResponse.class);
			Assert.assertEquals(response.getStatus(), Integer.parseInt(Constants.STATUS_OK), "Valid Status Code");
		}catch(Exception e)
		{
			throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}
	}
	
	
	/**
	 * Parses the json.
	 *
	 * @param jsonFile
	 *            the json file
	 * @return the JSON object
	 */
	/*
	 * Method:  parsing json
	 * @param: json api
	 */
	private JSONObject parseJson(String jsonFile)
	{
		JSONParser parser = new JSONParser();
		try
		{
			FileReader fileReader = new FileReader(jsonFile); 
			JSONObject json = (JSONObject) parser.parse(fileReader); 
			return json;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed to parse Json"+e.getMessage());
		}
	}
	
	
	/**
	 * Read json obj.
	 *
	 * @param jsonFile
	 *            the json file
	 * @param keys
	 *            the keys
	 * @return the map
	 */
	/*
	 * Method:  parse and read json data
	 * @param: resourceAPI
	 * @param: keys to get values from Json
	 */
	@SuppressWarnings("null")
	public Map<String, String> readJsonObj(String jsonFile, List<String> keys)
	{
		HashMap<String, String> jsonData = null;
		try
		{
			for(String key : keys)
			{
				jsonData.put(key, (String) parseJson(jsonFile).get(key));
			}
			return jsonData;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed to read Json"+e.getMessage());
		}
	}
	
	
	
	/**
	 * Read json array.
	 *
	 * @param jsonFile
	 *            the json file
	 * @param jsonArraykeys
	 *            the json arraykeys
	 * @return the map
	 */
	/*
	 * Method:  parse and read json data
	 * @param: resourceAPI
	 * @param: keys to get values from Json
	 */
	@SuppressWarnings("null")
	public Map<String, List<String>> readJsonArray(String jsonFile, List<String> jsonArraykeys)
	{
		JSONArray jsonArray = new JSONArray();
		HashMap<String, List<String>> jsonMap = null;
		List<String> jsonArrayList = null;
		try
		{
			for(String key : jsonArraykeys)
			{
				jsonArray = (JSONArray) parseJson(jsonFile).get(key);
				Iterator<?> i = jsonArray.iterator(); 
				while (i.hasNext()) 
				{ 
					jsonArrayList.add((String) i.next()); 
				}
				jsonMap.put(key, jsonArrayList);
			}
			return jsonMap;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed to read Json"+e.getMessage());
		}
	}
	
	
	/*
	 * Method:  write data to JsonObject
	 * @param: key-value pair to add to json
	 */
	
	/**
	 * Write json obj.
	 *
	 * @param map
	 *            the map
	 */
	public void writeJsonObj(HashMap<String, String> map)
	{
		JSONObject obj = new JSONObject();
		try
		{
			for (String key : map.keySet()) 
			{
				  obj.put(key, map.get(key));
			}
		
		}catch(Exception e)
		{
			throw new RuntimeException("Failed to read Json"+e.getMessage());
		}
	}
	
	/**
	 * Write json array.
	 *
	 * @param jsonFile
	 *            the json file
	 * @param jsonKey
	 *            the json key
	 * @param jsonArraykeys
	 *            the json arraykeys
	 */
	/*
	 * Method:  parse and read json data
	 * @param: resourceAPI
	 * @param: keys of json Array
	 * @Param: elements of Json Object array
	 */
	@SuppressWarnings({ "null", "unchecked" })
	public void writeJsonArray(String jsonFile, String jsonKey, List<String> jsonArraykeys)
	{
		JSONArray jsonArray = new JSONArray();
		HashMap<String, List<String>> jsonMap = null;
		List<String> jsonArrayList = null;
		try
		{
			for(String key : jsonArraykeys)
			{
				jsonArray = (JSONArray) parseJson(jsonFile).get(key);
				Iterator<?> i = jsonArray.iterator(); 
				while (i.hasNext()) 
				{ 
					jsonArrayList.add((String) i.next()); 
				}
				jsonMap.put(jsonKey, jsonArrayList);
			}
			jsonArray.add(jsonMap);
			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed to read Json"+e.getMessage());
		}
	}
	
	public static String getURL_Api(String key) throws FileNotFoundException {
		
		return PropertyFileUtils.getPropValuesFromConfig(Constants.API_PROPERTIES_FILE, key);
	}



	
	
	
	
	
}
