package com.tuto.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Classe utilitaire permettant de transformer un fichier CSV.
 * avec les capitales et les régions.
 */
public class TransformeCSV {

    /**
     * Lieu d'emplacement du fichier à parser.
     */
    private static final String EMPLACEMENT_DU_FICHIER =
            "C:\\Users\\matth\\Downloads\\"
                    + "liste_des_capitales_nationales-1012j.csv";
    /**
     * REGEX POUR EXTRAIRE LA VILLE ET LA CAPITALE DU FICHIER.
     */
    private static final String REGEX_EXTRACTION_VILLE_CAPITALE =
            "[[a-zA-Z]\\s[a-zA-Z]\\p{javaLowerCase}\\s*][\\D]+";

    /**
     * TIRET POUR AFFICHAGE.
     */
    public static final String TIRET = " - ";
    /**
     * HashMap contenant les capitales et les villes.
     */
    private static HashMap<String, String> mapVilleEtCapitale = new HashMap<>();

    /**
     * Parcours un fichier en entrée, et retourne une map de capitale et ville.
     * @return mapCapitaleEtVille.
     */
    public static HashMap<String, String> getCapitaleEtVille() {
        //recuperer emplacement du fichier
        try {
            InputStream flux = new FileInputStream(EMPLACEMENT_DU_FICHIER);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            // lire chaque ligne du fichier
            while ((ligne = buff.readLine()) != null) {
                //extraire la capitale et le pays
                extraireCapitaleEtPays(ligne);
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return mapVilleEtCapitale;
    }

    /**
     * Parcourir la ligne et extrait via
     * une expression reguliere la capitale et le pays.
     * @param ligne ligne actuelle.
     */
    public static HashMap<String, String>  extraireCapitaleEtPays(final String ligne) {

        try {
            Pattern p = Pattern.compile(REGEX_EXTRACTION_VILLE_CAPITALE);
            Matcher m = p.matcher(ligne);
            while (m.find()) {
                String[] tableauVilleCapitale = m.group(0).split(",");
                String ville = tableauVilleCapitale[1].replace(",", "");
                String capitale = tableauVilleCapitale[0].replace(",", "");
                mapVilleEtCapitale.put(ville, capitale);
            }
        } catch (PatternSyntaxException pse) {
            System.out.println(pse.toString());
        }
        return mapVilleEtCapitale;
    }
}
