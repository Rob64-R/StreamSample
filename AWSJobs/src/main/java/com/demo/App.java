package com.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBatchProcessing
public class App {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("producer")
	Job producer;
	
	@Autowired
	@Qualifier("consumer")
	Job consumer;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Scheduled(cron = "0 0 * * * ?")
	public void produce() throws Exception{
		
		JobParameters params = new JobParametersBuilder()
				.addString("jobId", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		
		jobLauncher.run(producer, params);
	}
	
	@Scheduled(cron = "0 0 * * * ?")
	public void consume() throws Exception{
		
		JobParameters params = new JobParametersBuilder()
				.addString("jobId", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		
		jobLauncher.run(consumer, params);
	}
}
