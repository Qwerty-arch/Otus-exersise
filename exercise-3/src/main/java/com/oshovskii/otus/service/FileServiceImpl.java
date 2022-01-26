package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.interfaces.FileDao;
import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.service.interfaces.FileService;
import com.oshovskii.otus.utils.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDao dao;
    private final Parser parser;

    @Override
    public List<FileCsv> parseCsvFile() {
        return parser.parseCSV(dao.findFileCsvName());
    }

    @Override
    public int findCountToCompleteTest() {
        return dao.findCountToCompleteTest();
    }

}
