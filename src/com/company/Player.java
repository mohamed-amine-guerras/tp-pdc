package com.company;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Amine on 17/04/2017.
 */
public class Player implements Serializable {
    private String pseudonyme;
    private int meilleureScore;
    private ArrayList<Integer> scores;
    private int scoreActuel;

    public Player(String pseudonyme, int meilleureScore, Set<Integer> scores, int scoreActuel) {
        this.pseudonyme = pseudonyme;
        this.meilleureScore = 0;
        this.scores = new ArrayList<>() ;}}

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
