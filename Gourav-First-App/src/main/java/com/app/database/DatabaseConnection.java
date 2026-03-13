package com.app.database;


import com.app.config.AppConfig;
import com.app.logs.AppLogger;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import shadow.org.bson.Document;

public class DatabaseConnection {
	static String userName =  AppConfig.getSecretData("MONGO_DB_USERNAME");
	static String dbPassword =  AppConfig.getSecretData("MONGO_DB_PASSWORD");
	static String connectionString =  "mongodb+srv://"+userName+":"+dbPassword+"@cluster0.7weletg.mongodb.net/?appName=Cluster0";

     static ServerApi serverApi = ServerApi.builder()
             .version(ServerApiVersion.V1)
             .build();

     static MongoClientSettings settings = MongoClientSettings.builder()
             .applyConnectionString(new ConnectionString(connectionString))
             .serverApi(serverApi)
             .build();
     // Create a new client and connect to the server
     static MongoClient mongoClient = MongoClients.create(settings);
       
     static MongoDatabase database = mongoClient.getDatabase("gourav_data");
     static MongoCollection<Document> c = database.getCollection("mydata");
     static MongoCollection<Document> c2 = database.getCollection("token");
    
      
     public static boolean insertToken(String token) {
    	 try {
 			c2.insertOne(new Document("token", token)
 	 				.append("useCount", "0"));
 			return true;
 		} catch (Exception e) {
 			return false;
 		}
     }
     
     public static boolean checkTokenUseCount(String token) {
    	 try {
    		 Document tokenToBeSeached = new Document("token", token);
        	 Document found =  c2.find(tokenToBeSeached).first();
 			if(Integer.parseInt(found.getString("useCount")) <= 0) {
 				return true;
 			}
 			return false;
 		} catch (Exception e) {
 			return false;
 		}
     }
     
     
     public static String updateTokenUseCount(String token) {
    	 // search token
    	 Document userToBeSearched = new Document("token", token);
    	 Document found =  c2.find(userToBeSearched).first();
    	 if(found == null) {
    		 System.out.println(AppLogger.INFO_LOG("NO TOKEN FOUND"));
    	 }
    	 try {
    		 int currentUseCount = Integer.parseInt(found.getString("useCount"));
    		 currentUseCount = currentUseCount + 1;
    		 String update = String.valueOf(currentUseCount);
    		 Document updatedToken = new Document("$set", new Document("useCount", update));
        	 c2.findOneAndUpdate(found, updatedToken);
        	 

        	 return "";
		} catch (Exception e) {
			
			return "";
		}
    	
     }
     
     
     
     public static Document loginUser(String email) {
    	 Document userToBeSearched = new Document("userEmail", email);
    	 Document found =  c.find(userToBeSearched).first();
    	 if(found == null) {
    		 System.out.println(AppLogger.INFO_LOG("ACCOUNT DOES NOT EXIST"));
    		 return null;
    	 }
    	 return found;
     }
     
     public static boolean verifyUser(String email) {
    	 // search user by email add
    	 Document userToBeSearched = new Document("userEmail", email);
    	 Document found =  c.find(userToBeSearched).first();
    	 if(found == null) {
    		 System.out.println(AppLogger.ERROR_LOG("NO USER FOUND TO BE VERIFIED"));
    	 }
    	 try {
    		 Document updatedUser = new Document("$set", new Document("isVerified", true));
        	 c.findOneAndUpdate(found, updatedUser);
        	 return true;
		} catch (Exception e) {
			return false;
		}
    	
     }
	
	public static boolean insertUserData(String firstName, String lastName, long phone, String userMail, String userPwd) {
		try {
			c.insertOne(new Document("firstName", firstName)
	 				.append("lastName", lastName)
	 				.append("mobileNum", phone)
	 				.append("userEmail", userMail)
	 				.append("userPassword", userPwd)
	 				.append("isVerified", false));
			return true;
		} catch (Exception e) {
			return false;
		}
		 
	}
	
}