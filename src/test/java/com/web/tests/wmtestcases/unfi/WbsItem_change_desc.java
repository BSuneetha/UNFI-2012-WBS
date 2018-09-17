package com.web.tests.wmtestcases.unfi;

       import com.jcraft.jsch.*;
       import static java.lang.System.out;
       import java.awt.*;
       import java.io.DataInputStream;
       import java.io.DataOutputStream;
       import java.io.IOException;
       import java.io.InputStream;
       import java.util.regex.Matcher;
       import java.util.regex.Pattern;
       import javax.swing.*;
       import org.testng.annotations.Test;

       public class WbsItem_change_desc {
       @Test
       public class WBSSalesOrder_openSSH_QA2 {
           JSch jsch = new JSch();
           String inputBuffer = "";
           
           //@Test(description="WaitForText")
           private String WaitForText(String text, String pattern, InputStream input) throws IOException, InterruptedException {
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
                           //if (i="_[6") return "\r\n"; 
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
                               out.println("Ken I Did not find '" + pattern + "'");
                           } else {
                               out.println("Ken I Did not find '" + text + "'");
                           }
                       }
                   }
               }
               return null;
           } 
           public void WBSSalesOrder_4_01_001_QA2() throws JSchException {
               try{
                    JSch jsch=new JSch();
                    String host="ctdaywbsqa2";
                    String user="kwestberg";
                    String passwd = "lmnopkow3";
                    String config =
                      "Port 22\n"+
                      "\n"+
                      "Host foo\n"+
                      "  User "+user+"\n"+
                      "  Hostname "+host+"\n"+
                      "Host *\n"+
                      "  ConnectTime 30000\n"+
                      "  PreferredAuthentications keyboard-interactive,password,publickey\n"+
                      "  #ForwardAgent yes\n"+
                      "  StrictHostKeyChecking no\n"+
                      "  #IdentityFile ~/.ssh/id_rsa\n"+
                      "  #UserKnownHostsFile ~/.ssh/known_hosts";

                    System.out.println("Generated configurations:");
                    System.out.println(config);

                    ConfigRepository configRepository =
                      com.jcraft.jsch.OpenSSHConfig.parse(config);
                      //com.jcraft.jsch.OpenSSHConfig.parseFile("~/.ssh/config");

                    jsch.setConfigRepository(configRepository);

                    // "foo" is from "Host foo" in the above config.
                    Session session=jsch.getSession("foo");
                    session.setPassword(passwd);

                    UserInfo ui = new MyUserInfo(){
                      public void showMessage(String message){
                        JOptionPane.showMessageDialog(null, message);
                      }
                      public boolean promptYesNo(String message){
                        Object[] options={ "yes", "no" };
                        int foo=JOptionPane.showOptionDialog(null,
                                                             message,
                                                             "Warning",
                                                             JOptionPane.DEFAULT_OPTION,
                                                             JOptionPane.WARNING_MESSAGE,
                                                             null, options, options[0]);
                        return foo==0;
                      }

                      // If password is not given before the invocation of Session#connect(),
                      // implement also following methods,
                      //   * UserInfo#getPassword(),
                      //   * UserInfo#promptPassword(String message) and
                      //   * UIKeyboardInteractive#promptKeyboardInteractive()

                    };

                    session.setUserInfo(ui);
                    String kex = session.getConfig("kex");
                    System.out.println("old kex:" + kex);
                    kex = kex.replace(",diffie-hellman-group-exchange-sha1", "");
                    session.setConfig("kex", kex);
                    System.out.println("new kex:" + session.getConfig("kex"));
                    session.connect(); // making a connection with timeout as defined above.

                    Channel channel=session.openChannel("shell");

                    channel.setInputStream(System.in);
                    
                    /*
                    // a hack for MS-DOS prompt on Windows.
                    channel.setInputStream(new FilterInputStream(System.in){
                        public int read(byte[] b, int off, int len)throws IOException{
                          return in.read(b, off, (len>1024?1024:len));
                        }
                      });
                    */

                    channel.setOutputStream(System.out);
                    DataOutputStream dataOut = new DataOutputStream(channel.getOutputStream());

                    
                    // Choose the pty-type "vt102".
                    ((ChannelShell)channel).setPtyType("vt201");
                    DataInputStream in = new DataInputStream(channel.getInputStream());
                    

                    /*
                    // Set environment variable "LANG" as "ja_JP.eucJP".
                    ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
                    */

                    //channel.connect();------------------------------At this point we are communicating with the server
                    channel.connect(3*1000);
                    WaitForText("kwestberg", null, in);
                    dataOut.flush();
                    Thread.sleep(3000);
                    dataOut.writeUTF("newgrp port\r\n");
                    dataOut.flush();
                    Thread.sleep(3000);
                    WaitForText("kwestberg", null, in);
                    dataOut.writeUTF("one\r\n");
                    dataOut.flush();
                    WaitForText("port", null, in);
                    Thread.sleep(2000);
                    WaitForText("INVOICING", null, in);
                    Thread.sleep(5000);
                    dataOut.writeBytes("6");//PRODUCT DATABASE MENU 
                     dataOut.flush();
                     WaitForText("PRODUCT DATABASE MENU", null, in);
                     Thread.sleep(2000);
                     dataOut.writeBytes("4\r");//here we are in the Product Screen
                     dataOut.flush();
                     Thread.sleep(2000);
                     WaitForText("PRODUCT", null, in);
                     Thread.sleep(5000);
                     dataOut.writeBytes("01033");
                     //dataOut.flush();
                     Thread.sleep(3000);
                     //WaitForText("TASTY", null, in);
                     dataOut.writeBytes("TASTY ENTREE PALLETsai\r");
                     dataOut.flush();
                     Thread.sleep(5000);
                     dataOut.writeBytes("\u001b[R");//F6
                     dataOut.flush();
                     Thread.sleep(1500);
                     dataOut.writeBytes("Y");
                     dataOut.flush();
                     Thread.sleep(1500);
                     dataOut.writeBytes("\u001b[Q");
                     dataOut.flush();
                     Thread.sleep(1500);
                     dataOut.writeBytes("88");// send an F8 to finish the order
                     dataOut.flush();
                     Thread.sleep(1500);
                     dataOut.writeBytes("9");//send a Y to confirm "ARE YOU REALLY FINISHED (Y/N)"
                     dataOut.flush();
                     Thread.sleep(1500);
                     
                  }
                  catch(Exception e){
                    System.out.println(e);
                  }
                }
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
              
           }//end WBSSalesOrder_4_01_001_QA2
              
       }
