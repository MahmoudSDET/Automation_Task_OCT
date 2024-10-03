package utils;


import com.creditdatamw.zerocell.Reader;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DataReaders {

    @SneakyThrows
    public static <T> List<T> getDataFromJson(String filePath, Class<T>dataModelClass) {

        ObjectMapper mapper = new ObjectMapper();
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + dataModelClass.getName() + ";");
        T[] objects = mapper.readValue(new File(filePath), arrayClass);
        return Arrays.asList(objects);
    }

    @SneakyThrows
    public static <T> List<T> getDataFromExcel(String filePath, String sheet,Class<T>dataModelClass) {
        // Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + dataModelClass.getName() + ";")
        return Reader.of(dataModelClass)
                .from(new File(filePath))
                .sheet(sheet)
                .skipHeaderRow(true)
                .list();

    }


    @SneakyThrows
    public static <T> List<T> getDataFromCSV(String filePath,Class<T>dataModelClass) {

        CsvMapper objectMapper = new CsvMapper();
        CsvSchema schema = objectMapper.schemaFor(dataModelClass).withHeader().withoutQuoteChar();
        MappingIterator<T> iterator = objectMapper.readerFor(dataModelClass).with(schema).readValues(new File(filePath));
        return iterator.readAll();

    }




}
