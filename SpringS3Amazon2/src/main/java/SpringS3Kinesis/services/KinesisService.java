package SpringS3Kinesis.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;


@Service
public class KinesisService {
	
	@Autowired
	AmazonKinesis kinesisClient;
	
	@Autowired
	Map<String, String> streams;
	
	public PutRecordsResult putRecordsfromJson(File file, String stream) throws IOException {
		PutRecordsRequest putRecordsRequest  = new PutRecordsRequest()
				.withStreamName(streams.get(stream));
		
		List <PutRecordsRequestEntry> putRecordsRequestEntryList  = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)))) {
			String line;
			int partitionKey = 0;
			while ((line = reader.readLine()) != null) {
				PutRecordsRequestEntry putRecordsRequestEntry  = new PutRecordsRequestEntry();
				putRecordsRequestEntry.setData(ByteBuffer.wrap(line.getBytes()));
				putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", partitionKey));
				putRecordsRequestEntryList.add(putRecordsRequestEntry);
				partitionKey++;
			}
		}

        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult  = kinesisClient.putRecords(putRecordsRequest);
        return putRecordsResult;
	}
	
	public void getRecords() {
		
	}
}
