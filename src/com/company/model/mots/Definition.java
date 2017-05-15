package com.company.model.mots;

/**
 * Created by Amine on 17/04/2017.
 */
public class Definition extends Indication {
    private final int COEFITENT = 3;
    public Definition(String valeur) {
        super(valeur);
    }

    @Override
    public int getCoefition() {
        return COEFITENT;
    }

    @Override
    public String toString() {
        return "Définition :";
    }
}
