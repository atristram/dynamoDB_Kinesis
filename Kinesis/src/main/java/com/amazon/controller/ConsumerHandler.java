package com.amazon.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;

public class ConsumerHandler implements RequestHandler<KinesisEvent, Void>{

	public Void handleRequest(KinesisEvent kinesisEvent, Context context) {

		LambdaLogger logger=context.getLogger();
		Map<String,String> data=new LinkedHashMap<String, String>();
		
		for(KinesisEventRecord r: kinesisEvent.getRecords()) {
			data.put(r.getKinesis().getPartitionKey(), new String(r.getKinesis().getData().array()));
		}
		
		logger.log("Data from producer: "+data.size());
		
		return null;
	}

	public void processing(Map<String,String> data,LambdaLogger logger) {
		
		int recordsProcessed=0;
		
		for(Map.Entry<String, String> entry:data.entrySet()) {
			logger.log("Key: "+entry.getKey()+" value: "+entry.getValue());
			++recordsProcessed;
		}
	}

}
