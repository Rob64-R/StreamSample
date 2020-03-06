package SpringS3Kinesis.services;

import static SpringS3Kinesis.common.methods.Parsers.readCsv;
import static SpringS3Kinesis.common.methods.Parsers.readJson;
import static SpringS3Kinesis.common.methods.Parsers.writeAsCsv;
import static SpringS3Kinesis.common.methods.Parsers.writeAsJson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ParserService {

	public void csvToJson(File csvFile, File jsonFile) throws IOException {
		List<Map<String, String>> listOfMaps = readCsv(csvFile);
		writeAsJson(listOfMaps, jsonFile);
	}
	
	public void jsonToCsv(File jsonFile, File csvFile) throws IOException {
		List<Map<String, String>> listOfMaps = readJson(jsonFile);
		writeAsCsv(listOfMaps, csvFile);
	}
}
