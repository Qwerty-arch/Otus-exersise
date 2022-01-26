package com.oshovskii.otus.service;

import com.oshovskii.otus.domain.FileCsv;
import com.oshovskii.otus.service.interfaces.QuestionService;
import com.oshovskii.otus.utils.LocaleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final FileServiceImpl fileService;
    private final LocaleManager localeManager;

    private static final String QUESTION_LOCALE = "question";
    private static final String TEST_COMPLETED_LOCALE = "completed";
    private static final String TEST_FAILED_LOCALE = "failed";

    @Override
    public void printQuestions() {
        List<FileCsv> csvFiles = fileService.parseCsvFile();
        for (FileCsv csvFile : csvFiles) {
            System.out.println();
            System.out.println(localeManager.translate(QUESTION_LOCALE) + csvFile.getQwestion());
            System.out.println("1. " + csvFile.getRightAnswer());
            System.out.println("2. " + csvFile.getIncorrectAnswer());
        }
    }

    @Override
    public String printAnswers(String username, String answer1, String answer2) {
        int count = 0;
        int answer1Int = 0;
        int answer2Int = 0;

        try {
            answer1Int = Integer.parseInt(answer1);
            answer2Int = Integer.parseInt(answer2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (answer1Int == 1) {
            count++;
        }
        if (answer2Int == 1) {
            count++;
        }

        String completedTest = username + " " + localeManager.translate(TEST_COMPLETED_LOCALE);
        String failedTest = username + " " + localeManager.translate(TEST_FAILED_LOCALE);

        if (count == 2) {
            return completedTest;
        } else {
            return failedTest;
        }
    }
}
