package SpringS3Kinesis.common.methods;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Parsers {

	// CSV
	
    public static List<Map<String, String>> readCsv(File file) throws IOException {
    	CsvMapper csvMapper = new CsvMapper();
    	
        CsvSchema schema = CsvSchema
        		.builder()
        		.setUseHeader(true)
        		.build()
        		.withHeader();
        
        MappingIterator<Map<String, String>> mappingIterator = csvMapper
        		.readerFor(Map.class)
        		.with(schema)
        		.readValues(file);

        return mappingIterator.readAll();
    }
    
    public static void writeAsCsv(List<Map<String, String>> data, File file) throws IOException {
    	CsvMapper csvMapper = new CsvMapper();
    	CsvSchema schema;
    	
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        for (String col : data.get(0).keySet()) {
                schemaBuilder.addColumn(col);
            }
        schema = schemaBuilder
        		.build()
        		.withLineSeparator("\r")
        		.withHeader();
  
        csvMapper.writer(schema).writeValue(file, data);
    }

    // JSON
    
    public static List<Map<String, String>> readJson(File file) throws IOException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	List<Map<String, String>> data = objectMapper
    			.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
    	
    	return data;
    }
    
    public static void writeAsJson(List<Map<String, String>> data, File file) throws IOException {
    	ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, data);
    }
}
