package com.innominds.team.frameworkengine;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.Reporter;

import com.innominds.team.utils.AES;
import com.innominds.team.utils.PropertyFileUtils;

/**
 * Orchestrates Send Email class.
 * 
 * @author Praveen Gaddam
 * 
 */
public class SendEmail {

	private static final Logger LOGGER = Logger.getAnonymousLogger();
	private static String SMTP_SERVER;
	private static String SMTP_SERVER_PORT;
	private static String USER_NAME;
	private static String PASSWORD;
	private String from;
	private String to;
	private String cc;
	private String subject;

	/** The message content. */
	@SuppressWarnings("unused")
	private final String messageContent = "Hi,\n\nAttached is the Automation TestSuite Report for iFusion application.\n\nRegards,\nAutomation Team";
	
	public void sendEmailReport(String filename1) throws InterruptedException {
		PropertyFileUtils prop = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.SENDMAIL_PROPERTIES_FILE));
		SMTP_SERVER = prop.getDataFromPropertyFile("email.host");
		SMTP_SERVER_PORT = prop.getDataFromPropertyFile("sendemail.port");
		USER_NAME = prop.getDataFromPropertyFile("sendEmailFrom");
		PASSWORD = AES.decrypt(prop.getDataFromPropertyFile("sendEmailPwd"), "Innominds123$");
		from = prop.getDataFromPropertyFile("sendEmailFrom");
		to = prop.getDataFromPropertyFile("sendEmailTo");
		cc = prop.getDataFromPropertyFile("sendEmailCC");
		subject = "Automation Summary Report";

		final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSWORD);
			}

		});

		try {
			File fi1 = new File(filename1);
			if (fi1.exists()) {
				final Message message = new MimeMessage(session);
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				if (!cc.isEmpty()) 
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				message.setSentDate(new Date());
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(
						"Hi,\n\nAttached is the Automation Test Suite Report of iFusion application.\n\nRegards,\nAutomation Team");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				// Thread.sleep(15000);
//				System.out.println("Report file path = " + filename1); // Constants.REPORTS_FILE_PATH
//				System.out.println(filename1);
				
				DataSource source = new FileDataSource(filename1);
				
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("iFusion_Automation_Report.html");
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
				//System.out.println("Sending email report...........");
				Transport.send(message);
				System.out.println("Report sent...........");
			} else {
				//System.out.println("HTML Report File does not exist...........");
				Reporter.log("Send Mail failure: HTML Report File does not exist..");
			}
		} catch (final MessagingException ex) {
			LOGGER.log(Level.WARNING, "Error Message: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Gets the email properties.
	 * 
	 * @return the email properties
	 */
	public Properties getEmailProperties() {
		final Properties config = new Properties();
		config.put("mail.smtp.auth", "true");
		config.put("mail.smtp.starttls.enable", "true");
		config.put("mail.smtp.host", SMTP_SERVER);
		config.put("mail.smtp.port", SMTP_SERVER_PORT);
		return config;
	}
}