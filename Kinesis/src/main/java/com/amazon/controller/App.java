package com.amazon.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.amazon.aws.AWSKinesisClient;
import com.amazon.model.Order;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App {

	List<String> productList = new ArrayList<String>();
	Random random=new Random();
	
	public static void main(String[] args) {

		App app=new App();
		//1. Get Client
		AmazonKinesis kinesisClient=AWSKinesisClient.getAWSKinesisClient();
		
		//2. putRecordRequestEntry
		List<PutRecordsRequestEntry> requestEntryList = app.getRecordsRequestList();
		//3. putRecord or putRecords- 10 entries in single API call.. 
		
		PutRecordsRequest recordsRequest= new PutRecordsRequest();
		recordsRequest.setStreamName("demostream");
		recordsRequest.setRecords(requestEntryList);
		
		PutRecordsResult results= kinesisClient.putRecords(recordsRequest);
		
		if(results.getFailedRecordCount()>0) {
			System.out.println("Sending data failed. Fail count: "+results.getFailedRecordCount());
		}else {
			System.out.println("Data sent successfully");
		}
	}
	
	public void populateProductList() {
		productList.add("Shirt");
		productList.add("T-Shirt");
		productList.add("Jeans");
		productList.add("Socks");
		productList.add("Shoes");
		productList.add("Tie");
		productList.add("Belt");
	}
	
	
	public List<Order> getOrderList() {
		
		List<Order> orders=new ArrayList<Order>();
		populateProductList();
		
		for(int i=0;i<500;i++) {
			Order order=new Order();
			order.setOrderId(random.nextInt());
			order.setProduct(productList.get(random.nextInt(productList.size())));
			order.setQuantity(random.nextInt(20));
			orders.add(order);
		}
		return orders;
	}
	
	public List<PutRecordsRequestEntry> getRecordsRequestList() {
		List<PutRecordsRequestEntry> requestEntryList=new ArrayList<PutRecordsRequestEntry>();
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		
		for(Order order:getOrderList()) {
			PutRecordsRequestEntry entry=new PutRecordsRequestEntry();
			entry.setPartitionKey(UUID.randomUUID().toString());
			entry.setData(ByteBuffer.wrap(gson.toJson(order).getBytes()));
			
			requestEntryList.add(entry);
		}
		return requestEntryList;
	}
	
}
