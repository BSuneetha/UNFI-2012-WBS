package com.innominds.team.listeners;

import java.util.ArrayList;

public class ResultsHolder {

	static ArrayList<String> result = new ArrayList<String> ();
	static int scriptCount = 0;
	public static void ResultHolder() {
		result = new ArrayList<String> ();
	}
	
	public static void insert (String row) {
		result.add(row);
	}
	
	public static ArrayList<String> getResults() {
		return result;
	}
	
	public static void increment() {
		scriptCount++;
	}
	
	public static void decrement() {
		scriptCount--;
	}
	
	public static int getScriptCount() {
		return scriptCount;
	}
	
}