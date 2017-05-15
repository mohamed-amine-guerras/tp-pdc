package com.company.model;

import com.company.model.mots.Mot;

import java.io.*;
import java.util.*;

/**
 * Created by Amine on 17/04/2017.
 */
public class Pendu extends Observable{
    private Session sessionActuel;
    private  String UsersFilePath; /**le fichier contenant les utilisateurs*/
    private boolean sessionTerminee;
    private String highScoresFilePath = "highScors.dat"; /** le fichier contenant les meilleures scores*/
    private TreeMap<Integer,String> highScores;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Pendu(String usersFilePath) {
        UsersFilePath = usersFilePath;
    }
    public Player getPlayer(String pseudonyme){
        return new LoginChecker(UsersFilePath).getPlayer(pseudonyme);
    }
    public Session getSessionActuel() {return sessionActuel;}
    public boolean isSessionTerminee() {
        return sessionTerminee;
    }




    /**
     * Récupère les meilleurs scores depuis le fichier des meilleurs scores
     * @return TreeMap<Integer, String>
     */
    public TreeMap<Integer, String> getHighScores() {
        InitializeHighScores();
        return highScores;
    }




    /**
     * Vérifie si un pseudonyme existe ou pas
     * @param pseudonyme
     * @return boolean
     * @throws LoginNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean LoginCheck(String pseudonyme) throws LoginNotFoundException, IOException, ClassNotFoundException {
        LoginChecker loginChecker = new LoginChecker(UsersFilePath);
        if (!loginChecker.Find(pseudonyme)) throw new LoginNotFoundException("Ce pseudonyme n'existent pas");
        return true;
    }

    /**
     * Ajoute un nouveau joueur au jeu
     * @param player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void AddPlayer(Player player) throws IOException, ClassNotFoundException, IllegalNicknameException {
        new LoginChecker(UsersFilePath).AddPlayer(player);
    }
    /**
     * Vérifie si le caractère écrit par l'utilisateur est correcte ou pas
     * @param c
     * @param indexCase
     */
    public void VerificationCase(char c,int indexCase){
        if (!sessionActuel.isSessionTerminee()){
            sessionActuel.VerificationCase(c,indexCase);
            if (sessionActuel.isSessionTerminee()) EndSession();
        }

    }

    /**
     * Commence une nouvelle session du jeu pour un joueur donné
     * @param player
     * @param mots
     */
    public void StartSession(Player player, HashSet<Mot> mots){
        sessionActuel = new Session(player,mots);
    }

    /**
     * Termine la session en cours et sauvegarde les scores
     */
    public void EndSession(){
        sessionTerminee = true;
        addHighScores();
        notifyObservers();
        try {
            AddPlayer(sessionActuel.getPlayer());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalNicknameException e) {
            e.printStackTrace();
        }

    }


    /**
     * Récupère les meilleurs scores depuis le fichier
     */
    public void InitializeHighScores(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(highScoresFilePath)));
            highScores = (TreeMap<Integer, String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException|ClassNotFoundException e) {
            highScores = null;
        }
    }

    /**
     * Sauvegarde les meilleurs scores dans le fichier des meilleurs scores
     */
    public void storeHighScores(){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(highScoresFilePath)));
            objectOutputStream.writeObject(highScores);
            objectOutputStream.close();
        } catch (IOException e) {

        }
    }

    /**
     * Mis à jour les meilleurs scores et les sauvegarde
     */
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

    /** Des méthodes de notification (principalement utilisées avec l'interface)*/


    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers){
            o.update((Observable) this,sessionTerminee);
        }
    }
}

