package com.company.model.mots;

/**
 * Created by Amine on 17/04/2017.
 */
public class Synonyme extends Indication {

    private final int COEFITIENT = 2;
    public Synonyme(String valeur) {
        super(valeur);
    }

    @Override
    public int getCoefition() {
        return COEFITIENT;
    }


}

