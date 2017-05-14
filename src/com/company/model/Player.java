package com.company.model;

import javafx.fxml.Initializable;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * Created by Amine on 17/04/2017.
 */
public class Player extends Observable implements Serializable  {
    private String pseudonyme;
    private int meilleureScore;
    private  transient ArrayList<Integer> scores;
    private  transient int scoreActuel;
    private ArrayList<Observer> observers = new ArrayList<>();

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public int getScoreActuel() {
        return scoreActuel;
    }

    public Player(String pseudonyme) {
        this.pseudonyme = pseudonyme;
        initialize();
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public Player() {
        initialize();
    }

    public void setScoreActuel(int scoreActuel) {
        this.scoreActuel += scoreActuel;
        notifyObservers();
    }
    public void ResetScore(){
        scoreActuel = 0 ;
    }
    public void AddScore(){
        if (scores == null) scores = new ArrayList<>();
        scores.add(scoreActuel);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers){
            o.update((Observable) this,scoreActuel);
        }
    }

    public void initialize() {
        this.meilleureScore = 0;
        this.scores = new ArrayList<>() ;
    }
}
