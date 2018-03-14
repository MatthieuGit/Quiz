package com.tuto.java;

import java.io.*;
import java.util.*;

public abstract class Quiz {

    private int score;
    private long timeElasped;
    private boolean done;
    private CapitalCityQuestionsGenerator generator;


    public Quiz(CapitalCityQuestionsGenerator capitalCityQuestionsGenerator) {
        this.generator = capitalCityQuestionsGenerator;
    }



    /**
     * Permet de donner la main à l'utilisateur pour écrire une réponse.
     */
    private static BufferedReader readerLine =
            new BufferedReader(new InputStreamReader(System.in));

    abstract  public void displayMessage(String text);

    abstract public String retrieveAnswer(String prompt);

    public void start() throws IOException {
        try {
            done = true;
            Long timeStart = System.currentTimeMillis();
            for (Question question : generator.generate()) {
                String userAnswer =  retrieveAnswer(question.getText());

                if (userAnswer.equalsIgnoreCase(question.getResponse())) {
                    score++;
                    displayMessage("Bonne réponse");
                } else {
                    score--;
                    String mauvaiseReponse = String.format("Mauvaise réponse, il fallait répondre: %s", question.getResponse());
                    displayMessage(mauvaiseReponse);
                }
            }
            Long endTime = System.currentTimeMillis();
            timeElasped = getTimeElapsedInSecond(endTime - timeStart);
        } catch (IllegalArgumentException e) {
            done = false;
            displayMessage(e.getMessage());
        }
    }

    private long getTimeElapsedInSecond(long timeElasped) {
        return timeElasped /1000;
    }

    public void displayResults() {
        if (done) {
            displayScore(score, generator.getNombreDeQuestion());
            displayTimeElapsed(timeElasped, generator.getNombreDeQuestion());
        }
    }

    private void displayTimeElapsed(long timeElasped, int nombreDeQuestion) {
        displayMessage(String.format("Il vous a fallu %d secondes pour répondre aux %d questions", timeElasped, nombreDeQuestion));
    }

    private void displayScore(int score, int nombreDeQuestion) {
        displayMessage(String.format("Votre score final est de %d/%d\n",score,nombreDeQuestion));
    }


}
