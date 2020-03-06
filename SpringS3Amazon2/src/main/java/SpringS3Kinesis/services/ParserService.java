package SpringS3Kinesis.services;

import static SpringS3Kinesis.common.methods.Parsers.readCsv;
import static SpringS3Kinesis.common.methods.Parsers.readJson;
import static SpringS3Kinesis.common.methods.Parsers.writeAsCsv;
import static SpringS3Kinesis.common.methods.Parsers.writeAsJson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

	@Autowired
	TextEncryptor encryptor;

	public void csvToJson(File csvFile, File jsonFile) throws IOException {
		File temp = new File("files/temp/json_aux");
		
		List<Map<String, String>> listOfMaps = readCsv(csvFile);
		writeAsJson(listOfMaps, temp);
		
		csvFile.delete();
		temp.renameTo(jsonFile);
	}

	public void jsonToCsv(File jsonFile, File csvFile) throws IOException {
		File temp = new File("files/temp/csv_aux");
		
		List<Map<String, String>> listOfMaps = readJson(jsonFile);
		writeAsCsv(listOfMaps, temp);
		
		jsonFile.delete();
		temp.renameTo(csvFile);
	}

	public void encrypt(File inputFile, File outputFile) throws Exception {
		File temp = new File("files/temp/encrypt_aux");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp)))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				line = encryptor.encrypt(line);
				writer.write(line);
				writer.newLine();
			}
			inputFile.delete();
			temp.renameTo(outputFile);
		}
		
	}

	public void decrypt(File inputFile, File outputFile) throws Exception, IOException {
		File temp = new File("files/temp/decrypt_aux");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp)))) {

			String line;
			while ((line = reader.readLine()) != null) {
				line = encryptor.decrypt(line);
				writer.write(line);
				writer.newLine();
			}
			inputFile.delete();
			temp.renameTo(outputFile);
		}
		
		
		
		
	}

}
