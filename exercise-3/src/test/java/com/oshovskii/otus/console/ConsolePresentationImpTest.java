package com.oshovskii.otus.console;

import com.oshovskii.otus.service.QuestionServiceImpl;
import com.oshovskii.otus.utils.LocaleManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Test ConsolePresentationImp")
@SpringBootTest
public class ConsolePresentationImpTest {

    @MockBean
    private QuestionServiceImpl questionService;

    @MockBean
    private LocaleManager localeManager;

    @Autowired
    private ConsolePresentationImp consolePresentationImp;

    private static final String INPUT_NAME = "name-request";
    private static final String GREETING = "greeting";
    private static final String TASKS = "task";
    private static final String INCORRECT_INPUT = "fail";
    private static final String EXPECTED_OUTPUT = "Test";
    private static final String EXPECTED_LOCALE = "ru-RU";
    private static final String TEST_USERNAME = "John";
    private static final String TEST_ANSWER_1 = "1";
    private static final String TEST_ANSWER_2 = "1";

    @DisplayName("Method presentationQuestions test")
    @Test
    public void presentationQuestions_voidInput_voidOutput() {
        // Config
        when(localeManager.translate(GREETING)).thenReturn(EXPECTED_OUTPUT);
        when(localeManager.translate(TASKS)).thenReturn(EXPECTED_OUTPUT);

        // Call
        consolePresentationImp.presentationQuestions();

        // Verify
        verify(localeManager, Mockito.times(2)).translate(any());
    }

    @DisplayName("Method greeting test")
    @Test
    public void greeting_voidInput_voidOutput() {
        // Config
        ByteArrayInputStream in = new ByteArrayInputStream("Username".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);

        when(localeManager.translate(INPUT_NAME)).thenReturn(EXPECTED_OUTPUT);

        // Call
        consolePresentationImp.greeting();

        // Verify
        verify(localeManager, Mockito.times(1)).translate(any());
        System.setIn(inputStream);
    }

    @DisplayName("Method presentationAnswer test")
    @Test
    public void presentationAnswer_voidInput_voidOutput() {
        // Config
        ByteArrayInputStream in = new ByteArrayInputStream("1,1".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);

        when(localeManager.translate(INCORRECT_INPUT)).thenReturn(EXPECTED_OUTPUT);
        when(questionService.printAnswers(TEST_USERNAME, TEST_ANSWER_1, TEST_ANSWER_2)).thenReturn(EXPECTED_OUTPUT);
        // Call
        consolePresentationImp.presentationAnswer();

        // Verify
        verify(localeManager, Mockito.times(0)).translate(any());
        System.setIn(inputStream);
    }
}