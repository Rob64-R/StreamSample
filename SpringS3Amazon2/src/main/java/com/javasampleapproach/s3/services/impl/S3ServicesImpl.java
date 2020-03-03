package com.javasampleapproach.s3.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.javasampleapproach.s3.services.S3Services;

@Service
public class S3ServicesImpl implements S3Services {
	
	@Autowired
	private AmazonS3 s3client;

	@Autowired
	Map<String, String> buckets;
	

	@Override
	public void downloadFileFromBucket(String bucketName, String keyName) {
		try (S3Object object = s3client.getObject(buckets.get(bucketName), keyName);
				S3ObjectInputStream s3is = object.getObjectContent();
				FileOutputStream fos = new FileOutputStream(new File("files/"+bucketName+"/" + keyName))) {

			byte[] read_buf = new byte[1024];
			int read_len = 0;

			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
