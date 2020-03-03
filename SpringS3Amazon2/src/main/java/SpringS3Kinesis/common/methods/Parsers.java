package SpringS3Kinesis.common.methods;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Parsers {

    public static List<Map<?, ?>> readCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema
        		.builder()
        		.addColumn("year", CsvSchema.ColumnType.NUMBER)
        		.addColumn("score", CsvSchema.ColumnType.NUMBER)
        		.addColumn("title", CsvSchema.ColumnType.STRING)
        		.build()
        		.withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper
        		.readerFor(Map.class)
        		.with(bootstrap)
        		.readValues(file);

        return mappingIterator.readAll();
    }

    public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, data);
    }
}
