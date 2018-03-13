package com.tuto.java;

/**
 * Classe représentant une question
 * avec un enoncé et une reponse.
 */
public class Question {

    private String text;
    private String response;

    public Question(final String enonce, final String reponse) {
        this.text = enonce;
        this.response = reponse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
