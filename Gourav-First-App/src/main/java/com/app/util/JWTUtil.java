package com.app.util;

import java.net.SecureCacheResponse;
import java.util.Date;

import com.app.database.DatabaseConnection;
import com.app.logs.AppLogger;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

//there will be two functions in this class, one will be used to create the JWT token.
//The other function will be used to verify the JWT token.
public class JWTUtil {
	static String secret = "thiscodeistopsecret";
	//here we are doing the encryption using SHA 256 (Secure Hash Algorithm --> 256 bytes)
	static Algorithm algo = Algorithm.HMAC256(secret);
	
	static Date currentDateTime = new Date(); // this has today's date and current time
	static long expireDuration = 10* 60 * 1000;
	static long currentTime = currentDateTime.getTime();
	static Date expireDateTime = new Date(currentTime + expireDuration);
	public static String createJWT(String email, long phone, String name, int otp) {
		String token = JWT.create()
		.withSubject(email)
		.withClaim("otp", otp)
		.withClaim("userPhone", phone)
		.withClaim("userName", name)
		.withIssuedAt(currentDateTime)
		.withExpiresAt(expireDateTime)
		.withIssuer("Gourav WebApp")
		.sign(algo);
		
		return token;
	}	
 public static String verifyJWT(String token) {
	try {
		DatabaseConnection.updateTokenUseCount(token);
		DecodedJWT decodedToken = JWT.require(algo)	
			.build().verify(token);
		 return decodedToken.getSubject().toString();
//		System.out.println("TOKEN IS VALID");
//		System.out.println("The issuer is "+ decodedToken.getIssuer());
//		System.out.println("The email add is "+ decodedToken.getSubject());
//		System.out.println("The OTP is "+ decodedToken.getClaim("otp"));
//		System.out.println("The name is "+ decodedToken.getClaim("userName"));
//		System.out.println("The phone is "+ decodedToken.getClaim("userPhone"));
//		System.out.println("The issue time is "+ decodedToken.getIssuedAt());
//		System.out.println("The Expire time is "+ decodedToken.getExpiresAt());
//		
	} catch (Exception e) {
		System.out.println(AppLogger.INFO_LOG("TOKEN IS EXPIRED"));
		return null;
	}
 }
		
	public static void main(String a[]) {
		//createJWT("iamcoder3281@gmail.com", 9690136543L, "Gourav", 234567);
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpYW1jb2RlcjMyODFAZ21haWwuY29tIiwib3RwIjoyMzQ1NjcsInVzZXJQaG9uZSI6OTY5MDEyMzQsInVzZXJOYW1lIjoiR291cmF2IiwiaWF0IjoxNzYzOTYwMzg1LCJleHAiOjE3NjM5NjA1MDUsImlzcyI6IkdvdXJhdiBXZWJBcHAifQ.ZCpFNseI4fH52DdVhZa7sD7uEnZQp477MwVj3MFDoDk";
		verifyJWT(token);
	}
}