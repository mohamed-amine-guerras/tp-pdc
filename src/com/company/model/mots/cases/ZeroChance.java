package com.company.model.mots.cases;

/**
 * Created by Amine on 17/04/2017.
 */
public class ZeroChance extends Case {
    private final int BONUS = 3;

    public ZeroChance(char valeur) {
        super(valeur);
    }

    @Override
    public void tentative(char lettre) {//Voir MultiChance
        if(lettre == getValeur()){
            setScore(BONUS);
            setSuceces(true);
            setFail(false);
        }
        else setFail(true);
    }

    @Override
    public String toString() {
        return super.toString() + " : zerochance";
    }
}
