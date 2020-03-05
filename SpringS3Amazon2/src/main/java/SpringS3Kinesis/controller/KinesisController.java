package SpringS3Kinesis.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SpringS3Kinesis.services.KinesisService;

@Controller
public class KinesisController {

	@Autowired
	KinesisService kinesisService;

	@GetMapping(value = "/produce/{bucketName}/{fileName}/to/{stream}")
	public ResponseEntity<String> produceToStream(@PathVariable String bucketName, @PathVariable String fileName, @PathVariable String stream) {
		
		String path = "files/"+ bucketName +"/"+ fileName;
		File file = new File(path);
		
		try {
			kinesisService.putRecords(file, stream);
			return new ResponseEntity<String>("Success!", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "consume/{stream}/to/{bucketName}/{fileName}")
	public ResponseEntity<String> consumeFromStream( @PathVariable String stream, @PathVariable String bucketName, @PathVariable String fileName) {
		
		String path = "files/"+ bucketName +"/"+ fileName;
		File file = new File(path);
		
		try {
			kinesisService.getRecords(stream, file);
			return new ResponseEntity<String>("Success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
		
	}
		
}
