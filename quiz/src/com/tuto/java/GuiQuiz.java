package com.tuto.java;

import javax.swing.*;

/**
 * Created by matthieu.bailly on 14/03/2018.
 */
public class GuiQuiz  extends Quiz{

    public GuiQuiz(CapitalCityQuestionsGenerator capitalCityQuestionsGenerator) {
        super(capitalCityQuestionsGenerator);
    }

    @Override
    public void displayMessage(String question) {
        JOptionPane.showMessageDialog(null, question);
    }

    @Override
    public String retrieveAnswer(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

}