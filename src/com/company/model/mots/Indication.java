package com.company.model.mots;

/**
 * Created by Amine on 17/04/2017.
 */
public abstract class Indication {
    /*
    * Une Indication est carractérisé par sa valeur, et son coéfitient qui dépend de son Type
     */
    private String valeur;

    public Indication(String valeur) {
        this.valeur = valeur;
    }


    public String getValeur() {
        return valeur;
    }

    public abstract int getCoefition();

    @Override
    public String toString() {
        return valeur;
    }
}
