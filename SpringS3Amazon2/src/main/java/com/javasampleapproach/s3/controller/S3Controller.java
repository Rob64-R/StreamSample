package com.javasampleapproach.s3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javasampleapproach.s3.services.impl.S3ServicesImpl;

@Controller
public class S3Controller {

	@Autowired
	private S3ServicesImpl s3Service;
	
	@GetMapping(value = "/{bucketName}/download/{keyName}")
	public ResponseEntity<String> getFileFromBucket(@PathVariable String bucketName ,@PathVariable String keyName) {
		s3Service.downloadFileFromBucket(bucketName, keyName);
		return new ResponseEntity<String>("Success!", HttpStatus.OK);
	}
}
