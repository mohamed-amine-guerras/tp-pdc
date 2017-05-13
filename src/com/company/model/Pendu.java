package com.company.model;

import com.company.model.mots.Mot;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by Amine on 17/04/2017.
 */
public class Pendu {
    private Session sessionActuel;
    private  String UsersFilePath;
    private String WordsFilePath;
    private boolean sessionTerminee;
    private String highScoresFilePath = "highScors.dat";
    private TreeMap<Integer,String> highScores;

    public TreeMap<Integer, String> getHighScores() {
        return highScores;
    }

    public Pendu(String usersFilePath) {
        UsersFilePath = usersFilePath;
    }

    public boolean LoginCheck(String pseudonyme) throws LoginNotFoundException, IOException, ClassNotFoundException {
        LoginChecker loginChecker = new LoginChecker(UsersFilePath);
        if (!loginChecker.Find(pseudonyme)) throw new LoginNotFoundException("Ce pseudonyme n'existent pas");
        return true;
    }
    public void VerificationCase(char c,int indexCase){
        if (!sessionActuel.isSessionTerminee()){
            sessionActuel.VerificationCase(c,indexCase);
            if (sessionActuel.isSessionTerminee()) EndSession();
        }

    }
    public Player getPlayer(String pseudonyme){
        return new LoginChecker(UsersFilePath).getPlayer(pseudonyme);
    }
    public void StartSession(Player player, HashSet<Mot> mots){
        sessionActuel = new Session(player,mots);
    }
    public void EndSession(){
        sessionTerminee = true;
    }


    public void AddPseudonyme(String pseudonyme) throws IOException, ClassNotFoundException {
       new LoginChecker(UsersFilePath).Addpseudonyme(pseudonyme);
    }
    public void InitializeHighScores(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(highScoresFilePath)));
            highScores = (TreeMap<Integer, String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException|ClassNotFoundException e) {
            highScores = null;
        }
    }
    public void storeHighScores(){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(highScoresFilePath)));
            objectOutputStream.writeObject(highScores.descendingMap());
            objectOutputStream.close();
        } catch (IOException e) {

        }
    }
    public void addHighScores(){
        ArrayList<Integer> scores = sessionActuel.getScores();
        String player = sessionActuel.getPlayer().getPseudonyme();
        InitializeHighScores();
        if (highScores == null) highScores = new TreeMap<>();
        for (int i :scores){
            highScores.put(i,player);
        }
        storeHighScores();
    }
}
