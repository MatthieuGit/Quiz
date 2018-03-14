package com.tuto.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by matthieu.bailly on 14/03/2018.
 */
public class CapitalCityQuestionsGenerator {

    private int nombreDeQuestion;

    public int getNombreDeQuestion() {
        return nombreDeQuestion;
    }

    /**
     * Lieu d'emplacement du fichier à parser.
     */
    private static final String EMPLACEMENT_DU_FICHIER = "liste_des_capitales_nationales-1012j.csv";

    /**
     * HashMap contenant les capitales et les villes.
     */
    private static HashMap<String, String> mapVilleEtCapitale = new HashMap<>();

    public CapitalCityQuestionsGenerator(int nbreQuestions) {
        this.nombreDeQuestion = nbreQuestions;
    }

    public ArrayList<Question> generate(){
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
    public HashMap<String, String> initValue() {

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
            InputStream flux = new FileInputStream(ConsoleQuiz.class.getResource(EMPLACEMENT_DU_FICHIER).getFile());
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
