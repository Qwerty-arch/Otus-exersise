package com.oshovskii.otus.console;

import com.oshovskii.otus.console.interfaces.ConsolePresentation;
import com.oshovskii.otus.service.QuestionServiceImpl;
import com.oshovskii.otus.utils.LocaleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsolePresentationImp implements ConsolePresentation{
    private final QuestionServiceImpl questionService;
    private final LocaleManager localeManager;

    private static final String INPUT_NAME = "name-request";
    private static final String GREETING = "greeting";
    private static final String TASKS = "task";
    private static final String INCORRECT_INPUT = "fail";
    private String username;

    public void setLanguagePresentation(String locale) {
        localeManager.setLocal(locale);
    }

    public void greeting() {
        Scanner scan = new Scanner(System.in);
        System.out.println(localeManager.translate(INPUT_NAME));
        username = scan.nextLine();
    }

    public void presentationQuestions() {
        System.out.println(localeManager.translate(GREETING));
        questionService.printQuestions();
        System.out.println(localeManager.translate(TASKS));
    }

    public void presentationAnswer() {
        Scanner scan = new Scanner(System.in);
        String inputAnswer = scan.nextLine();
        if (inputAnswer.isBlank()) {
            System.out.println(localeManager.translate(INCORRECT_INPUT));
            return;
        }

        String[] splitedText = inputAnswer.split(",");
        System.out.println(questionService.printAnswers(username, splitedText[0], splitedText[1]));
    }
}
