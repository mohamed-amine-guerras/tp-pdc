package com.company.model.mots;

import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Amine on 17/04/2017.
 */
public class WordsGenerator {

    private String wordsFilePath;
    private ArrayList<Mot> mots = new ArrayList<>();
    private HashSet<Mot> motsSeance =  new HashSet<>();
    private final int NB_MOTS_SEANCE = 10;

    public WordsGenerator(String wordsFilePath) {
        this.wordsFilePath = wordsFilePath;
    }
    /*
    * Récupérer la liste de touts les mots à partir du fichier
     */
    private void readWordsFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(wordsFilePath));
        String ligne = bufferedReader.readLine();
        while (ligne != null){
            StringTokenizer tokenizer = new StringTokenizer(ligne,";");
            String type = tokenizer.nextToken();
            String question = tokenizer.nextToken();
            String reponse = tokenizer.nextToken();
            Indication ind;
            if(type.equals("SYNONYME")) {
                ind = new Synonyme(question);
            } else if(type.equals("ANTONYME")){
                ind = new Antonyme(question);
            }else{
                ind = new Definition(question);
            }

            Mot mot = new Mot(ind,reponse);
            mot.genererCases();
            this.mots.add(mot);
            ligne = bufferedReader.readLine();
        }
    }

    /*
    * Génrer une liste de dix mots choisis aléatoirement à partire du fichier
     */
    public void genererListeMotsSeance() throws IOException {
        readWordsFile();
        motsSeance = new HashSet<>();
        Random random = new Random();
        while (motsSeance.size()<NB_MOTS_SEANCE){
            int a = abs(random.nextInt());
            int b = mots.size();
            int i = a%b;
            motsSeance.add(mots.get(i));
        }
    }


    public HashSet<Mot> getMotsSeance() {
        return motsSeance;
    }
}
