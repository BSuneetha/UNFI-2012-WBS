package com.innominds.team.pjython;

import java.util.Properties;

import org.python.util.PythonInterpreter;

public class PythonJythonHandler {
	
	private static int scriptCounter = 0;
	private static Boolean isTestRun = null;
	protected PythonInterpreter interpreter;
	
	public PythonInterpreter init(String configfile) {
		scriptCounter++;
		isTestRun = true;
		Properties props = new Properties();
		props.put("python.path","D:/streamlined/HarmonyCoreiFusion/target/test-classes/");
		props.put("python.home","D:/poc/jython/jython2.7.0");
		props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
		props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
		props.put("python.import.site","false");

		//Properties preprops = System.getProperties();
		String args1[] = {"--input_file",configfile}; 
		PythonInterpreter.initialize(System.getProperties(), props, args1);
		interpreter = new PythonInterpreter();
		return interpreter;
	}
	
	public static int getCounter() {
		return scriptCounter;
	}
	
	public static void setCounter( int count) {
		scriptCounter = count;
	}
	
	public static void decrementCounter() {
		scriptCounter--;
	}
}
