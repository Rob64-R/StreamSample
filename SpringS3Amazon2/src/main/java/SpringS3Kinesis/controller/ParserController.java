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
	ParserService service;
	
	@GetMapping(value = "/parse/{input}/{file}/csvtojson/{output}")
	public ResponseEntity<String> csvToJson(@PathVariable String input, @PathVariable String file, @PathVariable String output) {
		
		String csvPath = "files/"+input +"/"+ file + ".csv";
		File csvFile = new File(csvPath);
		
		String jsonPath = "files/"+output +"/"+ file + ".json";
		File jsonFile = new File(jsonPath);
		
		try {
			service.csvToJson(csvFile, jsonFile);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/parse/{input}/{file}/jsontocsv/{output}")
	public ResponseEntity<String> jsonToCsv(@PathVariable String input, @PathVariable String file, @PathVariable String output) {
		
		String jsonPath = "files/"+input +"/"+ file + ".json";
		File jsonFile = new File(jsonPath);
		
		String csvPath = "files/"+output +"/"+ file + ".csv";
		File csvFile = new File(csvPath);
		
		try {
			service.jsonToCsv(jsonFile, csvFile);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
		}
		
	}
}
