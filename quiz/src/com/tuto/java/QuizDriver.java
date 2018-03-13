package com.tuto.java;

import java.io.IOException;

public class QuizDriver {

    public static void main(String[] args) throws IOException {
        Quiz quiz = new Quiz(220);
        quiz.start();
        quiz.displayResults();
    }
}
