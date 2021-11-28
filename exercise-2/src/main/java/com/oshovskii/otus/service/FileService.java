package com.oshovskii.otus.service;

import com.oshovskii.otus.domain.FileCsv;

import java.util.List;

public interface FileService {
    List<FileCsv> parseCsvFile(String filePath);
    int numberToCompleteTest(String filePath);
}
