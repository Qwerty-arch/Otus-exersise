package com.oshovskii.otus.utils;

import com.oshovskii.otus.domain.FileCsv;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class Parser {

    public List<FileCsv> parseCSV(String fileName) {
        List<FileCsv> fileCsvs = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] splitedText = line.split(",");
                ArrayList<String> columnList = new ArrayList<>();
                Collections.addAll(columnList, splitedText);
                FileCsv fileCsv = new FileCsv(
                        columnList.get(0),
                        columnList.get(1),
                        columnList.get(2));

                fileCsvs.add(fileCsv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileCsvs;
    }
}
