package com.tuto.java;

import java.io.*;
import java.util.*;

public class Quiz {

    private int score;
    private long timeElasped;
    private int nombreDeQuestion;
    private boolean done;

    public int getNombreDeQuestion() {
        return nombreDeQuestion;
    }

    public Quiz(int nombreDeQuestion) {
        this.nombreDeQuestion = nombreDeQuestion;
    }

    /**
     * Lieu d'emplacement du fichier à parser.
     */
    private static final String EMPLACEMENT_DU_FICHIER = "liste_des_capitales_nationales-1012j.csv";

    /**
     * HashMap contenant les capitales et les villes.
     */
    private static HashMap<String, String> mapVilleEtCapitale = new HashMap<>();

    /**
     * Permet de donner la main à l'utilisateur pour écrire une réponse.
     */
    private static BufferedReader readerLine =
            new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        try {
            done = true;
            Long timeStart = System.currentTimeMillis();
            for (Question question : generate()) {
                System.out.println(question.getText());
                String userAnswer = readerLine.readLine();
                if (userAnswer.equalsIgnoreCase(question.getResponse())) {
                    score++;
                    System.out.println("Bonne réponse");
                } else {
                    score--;
                    String mauvaiseReponse = String.format("Mauvaise réponse, il fallait répondre: %s", question.getResponse());
                    System.out.println(mauvaiseReponse);
                }

            }
            Long endTime = System.currentTimeMillis();
            timeElasped = getTimeElapsedInSecond(endTime - timeStart);
        } catch (IllegalArgumentException e) {
            done = false;
            System.out.println(e.getMessage());
        }
    }

    private long getTimeElapsedInSecond(long timeElasped) {
        return timeElasped /1000;
    }

    public void displayResults() {
        if (done) {
            displayScore(score, nombreDeQuestion);
            displayTimeElapsed(timeElasped, nombreDeQuestion);
        }
    }

    private static void displayTimeElapsed(long timeElasped, int nombreDeQuestion) {
        System.out.printf("Il vous a fallu %d secondes pour répondre aux %d questions",timeElasped,nombreDeQuestion);
    }

    private static void displayScore(int score, int nombreDeQuestion) {
        System.out.printf("Votre score final est de %d/%d\n",score,nombreDeQuestion);
    }

    private ArrayList<Question> generate(){
        HashMap<String, String> listeDeCapitaleEtDePays = initValue();
        ArrayList<Question> listeDeQuestions = new ArrayList<>();

        for (Map.Entry<String, String> mapCapitaleVille : listeDeCapitaleEtDePays.entrySet()) {
            String pays = mapCapitaleVille.getKey();
            String capitale = mapCapitaleVille.getValue();
            String questionText = String.format("Quelle est la capitale de ce pays: %s?\n",pays);
            Question question = new Question(questionText, mapCapitaleVille.getValue());
            listeDeQuestions.add(question);
        }
        return listeDeQuestions;
    }

    /**
     * Retourne une hashmap de {Pays,Capital}. Dont la taille est choisie par l'utilisateur.
     *
     * @return paysCapital
     */
    private HashMap<String, String> initValue() {

        if(nombreDeQuestion > getCapitaleEtVille().size()) {
            throw new IllegalArgumentException("Il n'y a que " + getCapitaleEtVille().size() + " questions maximum");
        }
        HashMap<String, String> paysCapital = new HashMap<>();
        int compteur = 0;

        List keys = new ArrayList<>(getCapitaleEtVille().keySet());
        List value = new ArrayList(getCapitaleEtVille().values());

        for (int i = 0; i < keys.size(); i++) {
            while (compteur <= nombreDeQuestion) {
                int indice = genererIndiceAleatoire();
                Object pays = keys.get(indice);
                Object capitale = value.get(indice);
                paysCapital.put(pays.toString(), capitale.toString());
                compteur++;
                break;
            }
            if (compteur >= nombreDeQuestion) {
                break;
            }
        }
        return paysCapital;
    }

    public int genererIndiceAleatoire() {
        Random random = new Random();
        return random.nextInt(getCapitaleEtVille().size());
    }

    /**
     * Parcours un fichier en entrée, et retourne une map de capitale et ville.
     * @return mapCapitaleEtVille.
     */
    public HashMap<String, String> getCapitaleEtVille() {
        //recuperer emplacement du fichier
        try {
            InputStream flux = new FileInputStream(Quiz.class.getResource(EMPLACEMENT_DU_FICHIER).getFile());
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            // lire chaque ligne du fichier
            while ((ligne = buff.readLine()) != null) {
                //extraire la capitale et le pays
                mapVilleEtCapitale =   TransformeCSV.extraireCapitaleEtPays(ligne);
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return mapVilleEtCapitale;
    }
}
