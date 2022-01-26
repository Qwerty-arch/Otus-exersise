package com.oshovskii.otus.factory;

import com.oshovskii.otus.domain.FileCsv;

import java.util.ArrayList;
import java.util.List;

public class TestFileCsvFactory {
    public static FileCsv createFileCsv(int index) {
        return new FileCsv(
                "Question " + index,
                "Right Answer " + index,
                "Incorrect Answer " + index);
    }

    public static List<FileCsv> createFileCsvList(int listSize) {
        List<FileCsv> listFileCsv = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            listFileCsv.add(createFileCsv(i));
        }
        return listFileCsv;
    }
}
