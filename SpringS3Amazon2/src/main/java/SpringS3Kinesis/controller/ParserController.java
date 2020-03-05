package SpringS3Kinesis.controller;

import java.io.File;
import java.io.IOException;

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
			return new ResponseEntity<String>("Success! Converted file " + file + " to " +  file + "\n" + "Converted file can be found in " + jsonPath, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not convert file (IOException) " + file + " to JSON", HttpStatus.NOT_FOUND);
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
			return new ResponseEntity<String>("Success! Converted file " + file + " to " +  file + ".csv. \n" + "Converted file can be found in " + jsonPath, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not convert file (IOException) " + file + " to JSON", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value = "/encrypt/{input}/{file}/{output}")
	public ResponseEntity<String> encryptFile(@PathVariable String input, @PathVariable String file, @PathVariable String output){
		
		String rootPath = "files/"+ input +"/"+ file;
		File inputFile = new File(rootPath);
		
		String destination = "files/"+output +"/"+ file;
		File outputFile = new File(destination);
		try {
			parserService.encrypt(inputFile, outputFile);
			return new ResponseEntity<String>("Success! Encrypted located at: " + destination, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not encrypt file (IOException) " + file + " to JSON", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not encrypt file (Generic) " + file + " to JSON", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/decrypt/{input}/{file}/{output}")
	public ResponseEntity<String> decryptFile(@PathVariable String input, @PathVariable String file, @PathVariable String output){
		
		String rootPath = "files/"+ input +"/"+ file;
		File path = new File(rootPath);
		
		String destination = "files/"+output+"/"+ file;
		File outputPath = new File(destination);
		try {
			parserService.decrypt(path, outputPath);
			return new ResponseEntity<String>("Success! Decrypted file located at: " + destination, HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not decrypt file (IOException) " + file + " to JSON", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failure, could not decrypt file (Generic) " + file + " to JSON", HttpStatus.NOT_FOUND);
		}
	}
	
}
