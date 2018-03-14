package com.tuto.java;

import java.io.IOException;

public class QuizDriver {

    public static void main(String[] args) throws IOException {
        Quiz quiz = new GuiQuiz(new CapitalCityQuestionsGenerator(2));
        quiz.start();
        quiz.displayResults();
    }
}
