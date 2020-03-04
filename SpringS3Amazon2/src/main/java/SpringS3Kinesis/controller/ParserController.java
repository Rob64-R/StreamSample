package SpringS3Kinesis.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SpringS3Kinesis.services.ParserService;

@Controller
public class ParserController {
	
	@Autowired
	ParserService parserService;
	
	@GetMapping(value = "/parse/{input}/{file}/csvtojson/{output}")
	public ResponseEntity<String> csvToJson(@PathVariable String input, @PathVariable String file, @PathVariable String output) {
		
		String csvPath = "files/"+input +"/"+ file;
		File csvFile = new File(csvPath);
		
		String jsonPath = "files/"+output +"/"+ file;
		File jsonFile = new File(jsonPath);
		
		try {
			parserService.csvToJson(csvFile, jsonFile);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/parse/{input}/{file}/jsontocsv/{output}")
	public ResponseEntity<String> jsonToCsv(@PathVariable String input, @PathVariable String file, @PathVariable String output) {
		
		String jsonPath = "files/"+input +"/"+ file;
		File jsonFile = new File(jsonPath);
		
		String csvPath = "files/"+output +"/"+ file;
		File csvFile = new File(csvPath);
		
		try {
			parserService.jsonToCsv(jsonFile, csvFile);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
		
	}
}
