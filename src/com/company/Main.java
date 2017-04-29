package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WordsGenerator wordsGenerator = new WordsGenerator();
        try {
            wordsGenerator.genererListeMotsSeance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Mot mot : wordsGenerator.getMotsSeance()){
            System.out.println(mot);
            mot.genererCases();
        }
    }
}
