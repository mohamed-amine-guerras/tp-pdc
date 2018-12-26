package com.company.model;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class HighScoresManager {
    private TreeMap<Integer,String> highScores;
    private String highScoresFilePath ;

    public HighScoresManager(String highScoresFilePath) {
        this.highScoresFilePath = highScoresFilePath;
    }

    public TreeMap<Integer, String> getHighScores() {
        InitializeHighScores();
        return highScores;
    }

    public void InitializeHighScores(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(highScoresFilePath)));
            highScores = (TreeMap<Integer, String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException |ClassNotFoundException e) {
            highScores = null;
        }
    }

    public void storeHighScores(){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(highScoresFilePath)));
            objectOutputStream.writeObject(highScores);
            objectOutputStream.close();
        } catch (IOException e) {

        }
    }

    public void addHighScores(String player,ArrayList<Integer> scores){
        if (highScores == null) highScores = new TreeMap<>();
        for (int i :scores){
            highScores.put(i,player);
        }
        storeHighScores();
    }
}
