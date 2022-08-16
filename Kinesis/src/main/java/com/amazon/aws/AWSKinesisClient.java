package com.amazon.aws;

import org.springframework.stereotype.Repository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

@Repository
public class AWSKinesisClient {

	// AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	/*
	 * static BasicAWSCredentials awsCreds = new
	 * BasicAWSCredentials("AKIAUDF6DU62JPLCYJ63",
	 * "4PALSFdsXUnLPIEV7iRPWP606WvPLyO+TGvKlgy4");
	 * 
	 * static AmazonKinesis client = AmazonKinesisClientBuilder.standard()
	 * .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	 */

	static AmazonKinesis client = AmazonKinesisClientBuilder.standard().build();
	
	public static AmazonKinesis getAWSKinesisClient() {
		return client;
	}
}