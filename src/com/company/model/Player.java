package com.company.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Amine on 17/04/2017.
 */
public class Player implements Serializable {
    private String pseudonyme;
    private int meilleureScore;
    private transient ArrayList<Integer> scores;
    private transient int scoreActuel;

    public Player(String pseudonyme) {
        this.pseudonyme = pseudonyme;
        this.meilleureScore = 0;
        this.scores = new ArrayList<>() ;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public Player() {
    }

    public void setScoreActuel(int scoreActuel) {
        this.scoreActuel += scoreActuel;
    }
    public void ResetScore(){
        scoreActuel = 0 ;
    }
    public void AddScore(){
        scores.add(scoreActuel);

    }
}
