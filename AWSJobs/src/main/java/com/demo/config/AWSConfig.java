package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

@Configuration
public class AWSConfig {

	@Value("${aws.access_key_id}")
	private String awsId;

	@Value("${aws.secret_access_key}")
	private String awsKey;

	@Value("${s3.region}")
	private String region;
	
	@Value("${s3.stream}")
	private String stream;
	
	@Bean
	public AWSCredentialsProvider credentialsProvider() {
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsId, awsKey));
		return credentialsProvider;
	}
	
	@Bean
	public AmazonKinesis kinesisClient() {
		AmazonKinesis kinesisClient = AmazonKinesisClientBuilder
				.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(credentialsProvider())
				.build();
		
		return kinesisClient;
	}
}
