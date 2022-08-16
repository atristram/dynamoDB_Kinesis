/**
 * 
 */
package com.wissen.dynamoDB.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.wissen.dynamoDB.model.UserDetails;

@Repository
public class UserDetailsRepositoryV2 {

	private static Logger LOGGER = LoggerFactory.getLogger(UserDetailsRepositoryV2.class);

	//AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAUDF6DU62JPLCYJ63", 
			"4PALSFdsXUnLPIEV7iRPWP606WvPLyO+TGvKlgy4");

	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	
	
	/*
	 * UserDetailsRepositoryV2(){ AmazonDynamoDBClientBuilder
	 * clientBuilder=AmazonDynamoDBClientBuilder.standard();
	 * clientBuilder.setRegion("ap-south-1");
	 * 
	 * 
	 * clientBuilder.setCredentials(null); }
	 */
	
	
	
	DynamoDB dynamoDb = new DynamoDB(client);

	public UserDetails getUserDetails(String key) {
		Table table = dynamoDb.getTable("user");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_id", key);
		try {
			System.out.println("Attempting to read item");
			Item outcome = table.getItem(spec);
			if (Objects.nonNull(outcome)) {
				UserDetails user = new UserDetails();
				user.setUserId(outcome.get("user_id").toString());
				user.setFirstName(outcome.get("first_name").toString());
				user.setLastName(outcome.get("last_name").toString());
				return user;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred during getUserDetails : ", e);
		}
		return null;
	}

	public String addUserDetails(UserDetails user) {
		Table table = dynamoDb.getTable("user");
		try {
			final Map<String, String> addressMap = new HashMap<String, String>();
			addressMap.put("city", "Hyderabad");
			addressMap.put("pin", "500019");
			PutItemOutcome outcome = table.putItem(
					new Item().withPrimaryKey("user_id", user.getUserId()).with("first_name", user.getFirstName())
							.with("last_name", user.getLastName()).withMap("address", addressMap));
			if (Objects.nonNull(outcome))
				return "SUCCESS";
			else
				return "FAILURE";
		} catch (Exception e) {
			LOGGER.error("Exception occurred while adding record to the db : ", e);
			return null;
		}
	}

	
	public static void main(String[] args) {
		
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter user details");
		UserDetails user=new UserDetails();
		user.setUserId("4");
		user.setFirstName("Jane"); 
		user.setLastName("Doe");
		
		UserDetailsRepositoryV2 v2=new UserDetailsRepositoryV2();
		v2.addUserDetails(user);
	}
	
	
}