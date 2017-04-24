package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WordsGenerator wordsGenerator = new WordsGenerator();
        try {
            wordsGenerator.readWordsFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Mot mot : wordsGenerator.getMots()){
            System.out.println(mot);
            mot.genererCases();
        }
    }
}
