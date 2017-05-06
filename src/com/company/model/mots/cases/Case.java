package com.company.model.mots.cases;

/**
 * Created by Amine on 17/04/2017.
 */
public abstract class Case {

    private char valeur;
    private int score = 0;
    private boolean suceces = false;
    private boolean fail = false;

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

    void setSuceces(boolean suceces) {
        this.suceces = suceces;
    }

     void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isSuceces() {
        return suceces;
    }

    public boolean isFail() {
        return fail;
    }

    protected void setScore(int score){this.score = score;}

    public abstract boolean tentative(char lettre);

    @Override
    public String toString() {
        return String.valueOf(this.valeur);
    }
}
