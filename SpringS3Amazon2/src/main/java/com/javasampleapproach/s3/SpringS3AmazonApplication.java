package com.javasampleapproach.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringS3AmazonApplication{

	
	public static void main(String[] args) {
		SpringApplication.run(SpringS3AmazonApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("---------------- START DOWNLOAD FILE ----------------");
//		s3Services.downloadFile("LotrWallppaer.jpg");
//	}
}
