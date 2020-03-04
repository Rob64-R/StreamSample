package SpringS3Kinesis.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amazonaws.AmazonServiceException;

import SpringS3Kinesis.services.impl.S3ServicesImpl;

@Controller
public class S3Controller {

	@Autowired
	private S3ServicesImpl s3Service;

	@GetMapping(value = "/download/{bucketName}/{keyName}")
	public ResponseEntity<String> getFileFromBucket(@PathVariable String bucketName, @PathVariable String keyName) {
		try {

			s3Service.downloadFileFromBucket(bucketName, keyName);
			return new ResponseEntity<String>("Successfully Downloaded " + keyName + " from " + bucketName,
					HttpStatus.OK);

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<String>("Failed to download " + keyName + " from " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<String>("Failed to download " + keyName + " from " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return new ResponseEntity<String>("Failed to download " + keyName + " from " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to download " + keyName + " from " + bucketName,
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/upload/{bucketName}/{keyName}")
	public ResponseEntity<String> putFileIntoBucket(@PathVariable String bucketName, @PathVariable String keyName) {
		try {

			s3Service.uploadFileToBucket(bucketName, keyName);
			return new ResponseEntity<String>("Success! File uploaded to " + bucketName, HttpStatus.OK);

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<String>("Failed to upload  (File not found)" + keyName + " to " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<String>("Failed to upload (IOException)" + keyName + " to " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return new ResponseEntity<String>("Failed to upload (Amazon Service Exception)" + keyName + " to " + bucketName,
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to upload (Generic Exception)" + keyName + " to " + bucketName,
					HttpStatus.NOT_FOUND);
		}

	}
}
