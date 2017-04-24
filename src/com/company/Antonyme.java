package com.company;

/**
 * Created by Amine on 17/04/2017.
 */
public class Antonyme extends Indication {
    private final int COEFITIENT = 1;

    public Antonyme(String valeur) {
        super(valeur);
    }

    @Override
    public int getCoefition() {
        return COEFITIENT;
    }


}
