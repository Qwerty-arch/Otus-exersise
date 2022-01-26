package com.oshovskii.otus.service;

import com.oshovskii.otus.utils.LocaleManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test QuestionServiceImpl")
@ExtendWith(SpringExtension.class)
@Import(QuestionServiceImpl.class)
public class QuestionServiceImplTest {
    @MockBean
    private FileServiceImpl fileService;

    @MockBean
    private LocaleManager localeManager;

    @SpyBean
    private QuestionServiceImpl questionService;

    private static final String ANSWER_1 = "1";
    private static final String ANSWER_2 = "1";
    private static final String ANSWER_EXPECTED_RESULT = "John завершил тест корректно";
    private static final String USERNAME_EXPECTED = "John";
    private static final String LOCALE_MANAGER_INPUT_QUESTION_EXPECTED_RU = "question";
    private static final String LOCALE_MANAGER_INPUT_COMPLETED_EXPECTED_RU = "completed";
    private static final String LOCALE_MANAGER_OUTPUT_COMPLETED_EXPECTED_RU = "завершил тест корректно";
    private static final String LOCALE_MANAGER_OUTPUT_QUESTION_EXPECTED_RU = "Вопрос:";

    @DisplayName("Method printAnswers should return a message test")
    @Test
    public void printAnswers_validUsernameAndAnswer1AndAnswer2_shouldReturnExpectedMessage() {
        // Config
        when(localeManager.translate(LOCALE_MANAGER_INPUT_COMPLETED_EXPECTED_RU))
                .thenReturn(LOCALE_MANAGER_OUTPUT_COMPLETED_EXPECTED_RU);

        // Call
        final String result = questionService.printAnswers(USERNAME_EXPECTED, ANSWER_1, ANSWER_2);

        // Verify
        assertEquals(ANSWER_EXPECTED_RESULT, result);
    }

    @DisplayName("Method printQuestions test")
    @Test
    public void printQuestions_voidInput_shouldReturnSOUTMessage() {
        // Config
        when(localeManager.translate(LOCALE_MANAGER_INPUT_QUESTION_EXPECTED_RU))
                .thenReturn(LOCALE_MANAGER_OUTPUT_QUESTION_EXPECTED_RU);

        // Call
        questionService.printQuestions();

        // Verify
        verify(questionService, Mockito.times(1)).printQuestions();
    }
}