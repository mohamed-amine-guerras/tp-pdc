package com.company.model.mots.cases;


import java.util.HashSet;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Amine on 17/04/2017.
 */
public class Proposition extends Case implements Sanctionnable
{

    private char[] proposition;
    private final int NB_MAX_PROPSOTION = 4;
    private final int BONUS = 2;
    private final  int MALUS = 1;
    private int malus;


    public Proposition(char valeur) {
        super(valeur);
        this.proposition = new char[NB_MAX_PROPSOTION];
        remplirProposition();
    }

    private void remplirProposition(){
        String str = "abcdefghijklmnopqrstuvwxyz";
        HashSet<Character> caracters = new HashSet<>();
        caracters.add(getValeur());
        Random random = new Random();
        while (caracters.size()<NB_MAX_PROPSOTION){
            int i = abs(random.nextInt())%str.length();
            caracters.add(str.charAt(i));
        }
        int i = 0;
        for(Character c : caracters){
            proposition[i] = c;
            i++;
        }
    }

    public char[] getProposition() {
        return proposition;
    }

    public char choisirProposition(int i ){
        return proposition[i];
    }

    @Override
    public boolean tentative(char lettre) {
        if(lettre == getValeur()){
            setScore(BONUS);
            setSuceces(true);
            setFail(false);
        }
        else {
            this.malus = this.MALUS;
            setFail(true);
        }
        System.out.println(isSuceces());
        return isFail();
    }

    @Override
    public int getMalus(boolean motSenctionnabl) {
        if(motSenctionnabl) return malus;
        else return 0;
    }

    @Override
    public String toString() {
        System.out.println(proposition);
        return super.toString() + ": propsiotion";
    }
}
