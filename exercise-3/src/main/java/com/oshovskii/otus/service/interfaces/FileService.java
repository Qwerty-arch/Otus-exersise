package com.oshovskii.otus.service.interfaces;

import com.oshovskii.otus.domain.FileCsv;

import java.util.List;

public interface FileService {
    List<FileCsv> parseCsvFile();
    int findCountToCompleteTest();
}
