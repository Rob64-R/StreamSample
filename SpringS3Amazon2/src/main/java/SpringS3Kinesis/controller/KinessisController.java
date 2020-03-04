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
public class KinessisController {

	@Autowired
	KinesisService kinesisService;

	@GetMapping(value = "/upload/{input}/{file}/tostream/{stream}")
	public ResponseEntity<String> csvToJson(@PathVariable String input, @PathVariable String fileName, @PathVariable String stream) {
		
		String path = "files/"+input +"/"+ fileName;
		File file = new File(path);
		
		try {
			kinesisService.putRecordsfromJson(file, stream);
			return new ResponseEntity<String>("Success!", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
	}
}
