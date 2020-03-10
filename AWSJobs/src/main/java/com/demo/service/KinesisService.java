package com.demo.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;

@Service
public class KinesisService {
	
	@Value("${s3.stream}")
	private String stream;
	
	@Autowired
	AmazonKinesis kinesisClient;
	
	public PutRecordsResult produce(List<? extends String> records) throws IOException {
		PutRecordsRequest putRecordsRequest = new PutRecordsRequest()
				.withStreamName(stream);
		
		List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
		int partitionKey = 0;
		for (String record :records) {
			PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry()
					.withData(ByteBuffer.wrap(record.getBytes()))
					.withPartitionKey(String.format("partitionKey-%d", partitionKey));
			
			putRecordsRequestEntryList.add(putRecordsRequestEntry);
			partitionKey++;
		}

		putRecordsRequest.setRecords(putRecordsRequestEntryList);
		PutRecordsResult putRecordsResult = kinesisClient.putRecords(putRecordsRequest);
		
		return putRecordsResult;
	}
}
