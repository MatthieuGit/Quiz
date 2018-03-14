package com.tuto.java;

import java.util.Scanner;
public class ConsoleQuiz extends Quiz {

    private Scanner clavier = new Scanner(System.in);
    public ConsoleQuiz(CapitalCityQuestionsGenerator capitalCityQuestionsGenerator) {
        super(capitalCityQuestionsGenerator);
    }

    @Override
    public void displayMessage(String question) {
        System.out.println(question);
    }

    @Override
    public String retrieveAnswer(String prompt) {
        displayMessage(prompt);
        return clavier.nextLine();
    }

}
