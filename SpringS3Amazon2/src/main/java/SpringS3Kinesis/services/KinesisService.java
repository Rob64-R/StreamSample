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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.Shard;

@Service
public class KinesisService {

	@Autowired
	AmazonKinesis kinesisClient;

	@Autowired
	@Qualifier("streams")
	Map<String, String> streams;

	public PutRecordsResult putRecords(File file, String stream) throws IOException {
		PutRecordsRequest putRecordsRequest = new PutRecordsRequest()
				.withStreamName(streams.get(stream));

		List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;
			int partitionKey = 0;
			while ((line = reader.readLine()) != null) {
				PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry()
						.withData(ByteBuffer.wrap(line.getBytes()))
						.withPartitionKey(String.format("partitionKey-%d", partitionKey));
				
				putRecordsRequestEntryList.add(putRecordsRequestEntry);
				partitionKey++;
			}
		}

		putRecordsRequest.setRecords(putRecordsRequestEntryList);
		PutRecordsResult putRecordsResult = kinesisClient
				.putRecords(putRecordsRequest);
		return putRecordsResult;
	}

	public List<Shard> getShards(String stream) {
		DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest()
				.withStreamName(streams.get(stream));
		
		List<Shard> shards = new ArrayList<>();
		
		String exclusiveStartShardId = null;
		do {
			describeStreamRequest.setExclusiveStartShardId(exclusiveStartShardId);
			
			DescribeStreamResult describeStreamResult = kinesisClient
					.describeStream(describeStreamRequest);
			
			shards.addAll(describeStreamResult.getStreamDescription().getShards());
			
			if (describeStreamResult.getStreamDescription().getHasMoreShards() && shards.size() > 0) {
				exclusiveStartShardId = shards.get(shards.size() - 1).getShardId();
			} else {
				exclusiveStartShardId = null;
			}
		} while (exclusiveStartShardId != null);
		
		return shards;
	}
	
	public String getShardIterator(String stream, Shard shard) {
		String shardIterator;
		GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest()
				.withStreamName(stream)
				.withShardId(shard.getShardId())
				.withShardIteratorType("TRIM_HORIZON");
		
		GetShardIteratorResult getShardIteratorResult = kinesisClient
				.getShardIterator(getShardIteratorRequest);
		
		shardIterator = getShardIteratorResult.getShardIterator();
		
		return shardIterator;
	}
	
	public void getRecords(String stream) throws InterruptedException {
		List<Shard> shards = getShards(stream);
		List<Record> records = new ArrayList<>();
		
		for (Shard shard : shards) {
			String shardIterator = getShardIterator(streams.get(stream), shard);
			GetRecordsRequest getRecordsRequest = new GetRecordsRequest()
					.withShardIterator(shardIterator)
					.withLimit(100);
			
			GetRecordsResult result = kinesisClient.getRecords(getRecordsRequest);
			records.addAll(result.getRecords());
			
			Thread.sleep(1000);
		}
		
		for (Record record: records) {
			String line = new String(record.getData().array());
			System.out.println(line);	
		}
	
	}
	
}
