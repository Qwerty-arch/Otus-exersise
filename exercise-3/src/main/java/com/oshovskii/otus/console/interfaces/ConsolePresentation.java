package com.oshovskii.otus.console.interfaces;

public interface ConsolePresentation {
    void setLanguagePresentation(String locale);
    void greeting();
    void presentationQuestions();
    void presentationAnswer();
}
