package com.innominds.cucumber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MavenJunitRunner {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String mvnpath = "c:/app/apache-maven-3.5.0/bin/mvn.cmd";
		String args1[] ={"D:/streamlined/HarmonyCore/launchBrowsers.bat"};
		//Runtime.getRuntime().exec(args1);
		//ProcessBuilder pb = new ProcessBuilder("cmd.exe",mvnpath+args1);
		Process process = Runtime.getRuntime().exec(args1); //pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		ArrayList<String> alllines = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            alllines.add(line);
        }
//        try  {
//            process.waitFor();
//        }  catch (InterruptedException e)  {
//            System.out.println(e.getMessage());
//            alllines.add(e.getMessage());
//        }

        //String args2[] ={"D:/streamlined/HarmonyCore/launchFirefox.bat"};
		
		//pb1.start();
		//Runtime.getRuntime().exec(args2);
		//BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));

//        String line1;
//        while ((line1 = reader1.readLine()) != null) {
//            System.out.println("P1## "+line1);
//            alllines.add("P1###"+line1);
//        }
//        try  {
//            process1.waitFor();
//        }  catch (InterruptedException e)  {
//            System.out.println(e.getMessage());
//            alllines.add("P1###"+e.getMessage());
//        }
        
       // int k= 0;
		    
	}

}
