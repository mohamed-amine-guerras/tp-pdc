package com.company.cases;

import com.company.Sanctionnable;

/**
 * Created by Amine on 17/04/2017.
 */
public class Proposition extends Case implements Sanctionnable
{

    private char[] proposition;
    private final int NB_MAX_PROPSOTION = 4;
    private final int BONUS = 1;
    private final  int MALUS = 1;
    private int malus;


    public Proposition(char valeur) {
        super(valeur);
        this.proposition = new char[NB_MAX_PROPSOTION];
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
        return super.toString() + ": propsiotion";
    }
}
