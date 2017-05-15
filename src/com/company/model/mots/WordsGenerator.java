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
        while (ligne != null){ //On lit le fichier ligne par ligne
            StringTokenizer tokenizer = new StringTokenizer(ligne,";"); //On découpe la ligne selon le point virgule
            String type = tokenizer.nextToken(); //on récupère le Type qui le premier champs
            String question = tokenizer.nextToken();// on récupère la valeur de l'indication
            String reponse = tokenizer.nextToken(); // on récupère la valeur du mot
            Indication ind;
            if(type.equals("SYNONYME")) { // On instancie une indication selon le type
                ind = new Synonyme(question);
            } else if(type.equals("ANTONYME")){
                ind = new Antonyme(question);
            }else{
                ind = new Definition(question);
            }

            Mot mot = new Mot(ind,reponse);
            mot.genererCases();    // On sauvgarde le mot dans la mémoir centrale
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
        while (motsSeance.size()<NB_MOTS_SEANCE){ // Tant que nombre des mots est inferieur à NB_MOT_SEANCE
            int a = abs(random.nextInt());
            int b = mots.size();  // On ajoute un mot aléatoire de la liste des mot tiré du fichier
            int i = a%b;
            motsSeance.add(mots.get(i));
        }
    }


    public HashSet<Mot> getMotsSeance() {
        return motsSeance;
    }
}
