package SpringS3Kinesis.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	@Value("${aws.access_key_id}")
	private String awsId;

	@Value("${aws.secret_access_key}")
	private String awsKey;

	@Value("${s3.region}")
	private String region;

	@Value("${s3.sourceBucket}")
	private String sourceBucket;

	@Value("${s3.persistBucket}")
	private String persistBucket;

	@Value("${s3.outputBucket}")
	private String outputBucket;
	
	@Value("${s3.stream}")
	private String stream;
	
	
	@Bean
	public AWSCredentialsProvider credentialsProvider() {
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsId, awsKey));
		return credentialsProvider;
	}
	
	@Bean
	public AmazonS3 s3Client() {
		AmazonS3 s3Client = AmazonS3ClientBuilder
				.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(credentialsProvider())
				.disableChunkedEncoding()
				.build();

		return s3Client;
	}
	
	@Bean
	public AmazonKinesis kinesisClient() {
		AmazonKinesis kinesisClient = AmazonKinesisClientBuilder
				.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(credentialsProvider())
				//.setClientConfiguration(config)
				.build();
		
		return kinesisClient;
	}
	
	@Bean
	public Map<String, String> buckets() {

		Map<String, String> buckets = new HashMap<>();
		buckets.put("source", sourceBucket);
		buckets.put("persist", persistBucket);
		buckets.put("outputt", outputBucket);

		return buckets;
	}
	
	@Bean
	public Map<String, String> streams() {
		
		Map<String, String> streams = new HashMap<>();
		streams.put("stream", stream);

		return streams;
	}
	
}
