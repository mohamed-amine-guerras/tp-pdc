package com.company;

/**
 * Created by Amine on 17/04/2017.
 */
public abstract class Case {

    private char valeur;
    private int score;

    public Case(char valeur) {
        this.valeur = valeur;
        this.score = 0;
    }

    public char getValeur() {
        return valeur;
    }

    public int getScore() {
        return score;
    }

    protected void setScore(int score){this.score = score;}

    public abstract boolean tentative(char lettre);

    @Override
    public String toString() {
        return String.valueOf(this.valeur);
    }
}
