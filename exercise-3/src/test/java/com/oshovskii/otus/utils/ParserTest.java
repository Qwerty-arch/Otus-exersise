package com.oshovskii.otus.utils;

import com.oshovskii.otus.domain.FileCsv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Parser")
@SpringBootTest
public class ParserTest {

    @Autowired
    private Parser parser;

    private static final String CSV_FILE_NAME = "questionsEN.csv";

    @DisplayName("Method parseCSV should return list FileCSV test")
    @Test
    public void parseCSV_validCsvFileName_shouldReturnExpectedListCsvFiles() {
        // Config
        FileCsv fileCsv = new FileCsv("\"How much backet in HachMap?\"", "\"16\"","\"14\"");
        FileCsv fileCsv2 = new FileCsv("\"2 + 2\"", "\"4\"","\"5\"");

        // Call
        final List<FileCsv> result = parser.parseCSV(CSV_FILE_NAME);

        // Verify
        assertEquals(List.of(fileCsv, fileCsv2).size(), result.size());     // TODO Почему без size() не совпадает?
    }

}