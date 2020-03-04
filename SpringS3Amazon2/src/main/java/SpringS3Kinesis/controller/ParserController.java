package SpringS3Kinesis.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static SpringS3Kinesis.common.methods.Parsers.*;

@Controller
public class ParserController {
	@GetMapping(value = "/{bucketName}/parse/{keyName}")
	public ResponseEntity<String> parseCsvToJson(@PathVariable String bucketName, @PathVariable String keyName) throws Exception {
		String csvPath = "files/"+bucketName +"/"+ keyName + ".csv";
		File csvFile = new File(csvPath);
		
		String jsonPath = "files/persist/" + keyName + ".json";
		File jsonFile = new File(jsonPath);
		
		try {
			List<Map<?, ?>> data = readCsv(csvFile);
			writeAsJson(data, jsonFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Success!", HttpStatus.OK);
	}
}
