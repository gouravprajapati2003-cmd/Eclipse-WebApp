package com.app.services;

import java.util.Properties;

import com.app.config.AppConfig;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class OTPService {
	
	public static boolean sendVerificationLink(String to, String userName, String token) {
		String senderEmail = "iamcoder3281@gmail.com";
		String senderPassword = AppConfig.getSecretData("EMAIL_PASSWORD");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		};
	
		Session session = Session.getInstance(properties, auth);
		
		Message msg = new MimeMessage(session);
		String link = "http://localhost:8080/Gourav-First-App/VerifyOTPServlet?token="+token;
		
		
		try {
			msg.setFrom(new InternetAddress(senderEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSubject("Account Verification LINK ✔️");	
//			msg.setText("Dear, "+ userName + "\n\n"
//						+"Your One Time Password (OTP) is : "+OTP+"\n\n"
//						+"This OTP will expire in next 10 mins. \n\n"
//						+"Please do not share this OTP with anyone. \n\n"
  
	                  
			msg.setText("Hello, "+ userName + "\n\n"
					+"Follow this link to verify for your "+to+" account.\n\n"
					+"<a href="+">"+link+"</a>. \n\n"
					+"This Link will expire in next 10 mins. \n\n"
					+"If you didn’t ask to verify your account, you can ignore this email. \n\n"
						+"Thanks and Regards, \n"
						+"Customer Support \n"
						+"Team Gourav WebApp.");
                

                    
                    
			Transport.send(msg);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

}



//public static boolean sendVerificationLink(String to, String userName, String token) {
//    String senderEmail = "iamcoder3281@gmail.com";
//    String senderPassword = AppConfig.getSecretData("EMAIL_PASSWORD");
//
//    Properties properties = new Properties();
//    properties.put("mail.smtp.host", "smtp.gmail.com");
//    properties.put("mail.smtp.port", "587");
//    properties.put("mail.smtp.auth", "true");
//    properties.put("mail.smtp.starttls.enable", "true");
//
//    Authenticator auth = new Authenticator() {
//        @Override
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(senderEmail, senderPassword);
//        }
//    };
//
//    Session session = Session.getInstance(properties, auth);
//
//    String verifyLink = "http://localhost:8080/Gourav-First-App/VerifyOTPServlet?token=" + token;
//
//    try {
//        Message msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress(senderEmail));
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//        msg.setSubject("Account Verification Link ✔");
//
//        // HTML email message
//        String htmlContent =
//                "<div style='font-family: Arial; font-size: 16px;'>"
//                        + "Hello <b>" + userName + "</b>,<br><br>"
//                        + "Click the button below to verify your account:<br><br>"
//
//                        + "<a href='" + verifyLink + "' "
//                        + "style='background: #4CAF50; color: white; padding: 10px 20px; "
//                        + "text-decoration: none; border-radius: 5px; font-size: 16px;'>"
//                        + "Verify Account</a><br><br>"
//
//                        + "If the button does not work, copy and paste this link in your browser:<br>"
//                        + "<a href='" + verifyLink + "'>" + verifyLink + "</a><br><br>"
//
//                        + "This link will expire in <b>10 minutes</b>.<br><br>"
//                        + "If you didn't request verification, please ignore this email.<br><br>"
//                        + "Thanks & Regards,<br>"
//                        + "<b>Team Gourav WebApp</b>"
//                        + "</div>";
//
//        msg.setContent(htmlContent, "text/html");
//
//        Transport.send(msg);
//        return true;
//
//    } catch (MessagingException e) {
//        e.printStackTrace();
//        return false;
//    }
//}
//}

