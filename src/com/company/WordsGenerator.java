package com.company;

import jdk.internal.util.xml.impl.ReaderUTF8;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Amine on 17/04/2017.
 */
public class WordsGenerator {

    private HashSet<Mot> mots = new HashSet<>();
    public void readWordsFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("mots.poo"));
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
            this.mots.add(mot);
            ligne = bufferedReader.readLine();
        }
    }

    public HashSet<Mot> getMots() {
        return mots;
    }
}
