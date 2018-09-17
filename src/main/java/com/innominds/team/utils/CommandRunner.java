package com.innominds.team.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.testng.Reporter;

public class CommandRunner implements Runnable {
	public void runCommand(String... args) {
		Thread thread = null;
		try {
			Process p = new ProcessBuilder(args).start();
			thread = new Thread() {
				@Override
				public void run() {
					try {
						String line;
						BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while ((line = input.readLine()) != null) {
							System.out.println(line);
							Reporter.log(line);
						}
						input.close();
					} catch (IOException e) {
						System.out.println(" process not read" + e);
						Reporter.log(e.getMessage());
					}
				}
			};
			thread.start();
			int result = p.waitFor();
			thread.join();
			if (result != 0) {
				System.out.println("Process failed with status: " + result);
				Reporter.log("Process failed with status: " + result);
			}
		} catch (IOException e) {
			System.out.println(" process not read" + e);
			Reporter.log(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			Reporter.log(e.getMessage());
		}
	}	

    private String[] args;
    
    public CommandRunner(String...Args) {
          args = Args;
          
    }

    @Override 
    public void run() {
        try  {
        	
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            try  {
                process.waitFor();
            }  catch (InterruptedException e)  {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
        System.out.println("ending executeScript--Testing");
    }

    //Example
    public static void main(String[] args) {
    	CommandRunner c1 = new CommandRunner("D:/cmd.bat", "arg1");
            Thread t1 = new Thread(c1);
            t1.start();

            CommandRunner c2 = new CommandRunner("D:/cmd.bat", "arg2");
            Thread t2 = new Thread(c2);
            t2.start();

            CommandRunner c3 = new CommandRunner("D:/cmd.bat", "arg3");
            Thread t3 = new Thread(c3);
            t2.start();
    }
	
}
