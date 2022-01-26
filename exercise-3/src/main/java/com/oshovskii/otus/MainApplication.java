package com.oshovskii.otus;

import com.oshovskii.otus.console.ConsolePresentationImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
		ConsolePresentationImp consolePresentationImp = ctx.getBean(ConsolePresentationImp.class);
		consolePresentationImp.setLanguagePresentation("ru-RU");
		consolePresentationImp.greeting();
		consolePresentationImp.presentationQuestions();
		consolePresentationImp.presentationAnswer();
	}
}
