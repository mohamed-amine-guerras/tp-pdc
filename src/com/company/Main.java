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
            for(Case box : mot.getEnsemblesCases()) System.out.println(box);
            System.out.println(mot.getEnsemblesCases().size());
            boolean stop =false;
            while (!mot.isMotTerminee() && !stop){
                Scanner scanner = new Scanner(System.in);
                System.out.print("i = ");
                int i = scanner.nextInt();
                scanner = new Scanner(System.in);
                System.out.println("c = ");
                char car = scanner.nextLine().charAt(0);
                stop = mot.Verification(car,i);
            }
        }
    }
}
