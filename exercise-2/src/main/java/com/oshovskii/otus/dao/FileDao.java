package com.oshovskii.otus.dao;

public interface FileDao {
    String findPathToCsvFile(String fileName);

    int findCountToCompleteTest(String fileName);
}
