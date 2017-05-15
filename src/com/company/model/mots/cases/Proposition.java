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
    private final int NB_PROPSOTION = 4;
    private final int BONUS = 2;
    private final  int MALUS = 1;
    private int malus;


    public Proposition(char valeur) {
        super(valeur);
        this.proposition = new char[NB_PROPSOTION];
        remplirProposition();
    }

    /*
    * Remplie les propositions avec NB_PROPOSITIONS distinctes dont une est valide
     */
    private void remplirProposition(){
        String str = "abcdefghijklmnopqrstuvwxyz";
        HashSet<Character> caracters = new HashSet<>();// On utilise une HashSet afin d'éviter les doublons en plus c'est une collection non ordonnée
        caracters.add(getValeur());// On ajoute la lettre juste
        Random random = new Random();
        while (caracters.size()< NB_PROPSOTION){//Tanque le nombre de lettre inséré est inferieur a NB_PROPOSITION
            int i = abs(random.nextInt())%str.length();
            caracters.add(str.charAt(i)); //On ajoute un caractère aléatoire
        }
        int i = 0;
        for(Character c : caracters){ // On remlie le tableau à partire du HashSet obtenue
            proposition[i] = c;
            i++;
        }
    }

    public char[] getProposition() {
        return proposition;
    }

    @Override
    public void tentative(char lettre) {// Voir MultiChance
        if(lettre == getValeur()){
            setScore(BONUS);
            setSuceces(true);
            setFail(false);
        }
        else {
            this.malus = this.MALUS;
            setFail(true);
        }
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
