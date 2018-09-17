package com.innominds.team.apiutils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import com.innominds.team.utils.ScreenRecord;
import com.jcraft.jsch.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.testng.annotations.Test;

public class TelnetApiRequest {

	/**
	 * Gets the API Response.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void main() throws Exception {
		try {
			JSch jsch = new JSch();
			String host="ctdaywbsqa2.unfi.com";
		    String user="kwestberg";
		    String passwd = "lmnopkow3";
		    ScreenRecord scrrcd = new ScreenRecord();
		    
		    Security.insertProviderAt(new BouncyCastleProvider(), 1);
			Session session=jsch.getSession(user, host, 22);
            session.setPassword(passwd);
            Properties prop = new Properties();
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            UserInfo ui = new MyUserInfo(){
            };
            
            session.setUserInfo(ui);
            session.connect();   // making a connection with timeout.
            Channel channel = session.openChannel("shell");           //            --------------------------------------------------This was the original channel
            ((ChannelShell)channel).setPtyType("vt220");
            DataInputStream in = new DataInputStream(channel.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(channel.getOutputStream());
            channel.connect();
            
            
       /*     byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	        } 
		}*/
            
           /* System.out.println("Channel: " + channel.getInputStream().read());
            //System.out.println("Test: " + in.readLine());
            dataOut.writeBytes("newgrp mpw\r");
            while ((in.readLine()) != null) {

            	//((DataOutputStream) dataOut).println(in.readLine());
            	//System.out.println("Test1: " + in.re);	 

            	 }
            System.out.println("Test2: " + in.readUTF(in));
            dataOut.flush();*/
            
           try {
            	
                //WaitForText("kwestberg", null, in);
            	//Thread.sleep(2000);
        	   dataOut.flush();
        	   dataOut.write(("newgrp mpw\r\n").getBytes());
        	   dataOut.flush();
        	  // dataOut.write(("\r \n").getBytes());
        	   /*
                dataOut.writeBytes("newgrp mpw");//we are logged in and now need to change to the right wareouse ftp://biztalk@ctdaywbsqa2/RID/bakout
                dataOut.writeBytes("\r\n");
                System.out.println("Test: " + in.readLine());
                dataOut.flush();
                dataOut.writeUTF("newgrp mpw");
                dataOut.writeUTF("\r\n");*/
                System.out.println("Test1: " + in.readLine());
                //dataOut.writeUTF("\\r");
                //dataOut.flush();
                //dataOut.writeBytes("\\r");
                //dataOut.flush();
                //System.out.println("Test: " + in.readLine());
                
               // WaitForText("kwestberg", null, in);
            	/*Thread.sleep(2000);
                dataOut.writeBytes("hello");//we are logged in and now need to change to the right wareouse ftp://biztalk@ctdaywbsqa2/RID/bakout
                dataOut.flush();
                System.out.println("Test1: " + in.readLine());*/
                
                
                /*Thread.sleep(2000);
                dataOut.writeBytes("one\r\n");//start the WBS program
                dataOut.flush();
                WaitForText("kwestberg", null, in);
                Thread.sleep(2000);
                dataOut.writeBytes("6\n");// 1   ORDER ENTRY & INVOICING
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("4\n\\r");
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("01033");//[0;1m[0;1m[0;7m[12;25HBRECKENRIDGE ALE HOUSE[0;1m[0;1m[0;7m[12;76HN[
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("Auto \r\n");//please wait after this
                dataOut.flush();//we should be able to capture the sales order number here
                Thread.sleep(2000);
                dataOut.writeBytes("\r\n");//please wait after this
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("Y\r\n");//please wait after this
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("IN\r\n");//please wait after this
                dataOut.flush();
                Thread.sleep(2000);
                dataOut.writeBytes("\\u001b[T");//please wait after this
                dataOut.flush();
                Thread.sleep(10000);*/
                //scrrcd.stopRecording();
                
                

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[SSHClient] Thread interrupted");
            /*} finally {
                Thread.sleep(5000);
                dataOut.writeBytes("\u001b[18~");//send an F7 to abort this action
                dataOut.flush();
                Thread.sleep(5000);
                dataOut.writeBytes("a\n");
                dataOut.flush();*/
            }
		}
           
           catch (Exception e) {
               e.printStackTrace();
               System.out.println("[SSHClient] Thread interrupted");
           }
         
            
		 
	        
           

	}
	

    @Test(description="WaitingforTextWBS test")
    private String WaitForText(String text, String pattern, InputStream input) throws IOException, InterruptedException {
    	String inputBuffer = "";
        inputBuffer = "";
        String val = "";
        String newtext = "";
        long end = System.currentTimeMillis() + 3000;
        int i; byte[] tmp = new byte[1024];
        Pattern p = null;
        if (pattern != null) p = Pattern.compile(pattern);
        Matcher m;
        while (System.currentTimeMillis() < end) {
            {
                while (input.available() > 0) {
                    i = input.read(tmp, 0, 1024);//we are never geting here
                    if (i<0) break;
                    newtext = new String(tmp, 0, i);
                    System.out.print(newtext);
                    val += newtext;
                    inputBuffer = val;
                    if (pattern != null) {
                        m = p.matcher(val);
                        if (m.find()) {
                            //System.out.println("Found it");
                            return m.group(0);
                        }
                    } else if (val.toUpperCase().contains(text.toUpperCase())) {
                        System.out.println(val+newtext);
                        return val;
                    }
                }
                Thread.sleep(100);
                if (System.currentTimeMillis() > end) {
                    //Thread.CurrentThread.Abort();
                    if (pattern != null) {
                        System.out.println("Ken I Did not find '" + pattern + "'");
                    } else {
                    	System.out.println("Ken I Did not find '" + text + "'");
                    }
                }
            }
        }
        return null;
    }

		
		public static abstract class MyUserInfo
        implements UserInfo, UIKeyboardInteractive{
			public String getPassword(){ return null; }
			public boolean promptYesNo(String str){ return false; }
			public String getPassphrase(){ return null; }
			public boolean promptPassphrase(String message){ return false; }
			public boolean promptPassword(String message){ return false; }
			public void showMessage(String message){ }
			public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
        return null;
    }
		}


}
