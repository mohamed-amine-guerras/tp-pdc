package com.company;

import com.company.model.mots.cases.Case;
import com.company.model.mots.Mot;
import com.company.model.mots.WordsGenerator;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WordsGenerator wordsGenerator = new WordsGenerator();
        try {
            wordsGenerator.genererListeMotsSeance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Mot mot : wordsGenerator.getMotsSeance()){
            System.out.println(mot.isMotSanctionnabl());
            for(Case box : mot.getEnsemblesCases()) System.out.println(box);
            Scanner scanner = new Scanner(System.in);
            String reponse = scanner.next();
            test(reponse,mot);
        }
    }

    private static void test(String reponse, Mot mot){
        boolean succes = true;
        char[] lettres = reponse.toCharArray();
        for(int i = 0; i<lettres.length;i++){
            mot.Verification(lettres[i],i);
            System.out.println(mot.getScore());
        }
    }
}

