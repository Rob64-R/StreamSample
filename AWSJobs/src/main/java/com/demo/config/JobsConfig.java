package com.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.io.CoordinatesReader;
import com.demo.io.CoordinatesWriter;
import com.demo.io.KinesisReader;
import com.demo.io.KinesisWriter;

@Configuration
@EnableBatchProcessing
public class JobsConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	CoordinatesReader coordinatesReader;
	
	@Autowired
	KinesisReader kinesisReader;
	
	@Autowired
	KinesisWriter kinesisWriter;
	
	@Autowired
	CoordinatesWriter coordinatesWriter;
	
	
	@Bean
	@Qualifier("producer")
	public Job producer() {
		return jobBuilderFactory.get("coordinates")
				.start(produce())
				.build();
	}
	
	@Bean
	@Qualifier("consumer")
	public Job consumer() {
		return jobBuilderFactory.get("coordinates")
				.start(consume())
				.build();
	}

	@Bean
	public Step produce() {
		return stepBuilderFactory.get("produce")
				.<String, String> chunk(10)
				.reader(coordinatesReader)
				.writer(kinesisWriter)
				.build();
	}
	
	@Bean
	public Step consume() {
		return stepBuilderFactory.get("produce")
				.<String, String> chunk(10)
				.reader(kinesisReader)
				.writer(coordinatesWriter)
				.build();
	}
	
}
