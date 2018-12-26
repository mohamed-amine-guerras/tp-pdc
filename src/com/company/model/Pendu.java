package com.company.model;

import com.company.model.mots.WordsGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Amine on 17/04/2017.
 */
public class Pendu extends Observable{
    private Session sessionActuel;
    private String UsersFilePath; /**le fichier contenant les utilisateurs*/
    private String highScoresFilePath = "highScors.dat"; /** le fichier contenant les meilleures scores*/
    private String wordsFilePath;
    private HighScoresManager highScoresManager;
    private ArrayList<Observer> observers = new ArrayList<>();
    private Player player;

    public Pendu(String usersFilePath, String wordsFilePath) {
        UsersFilePath = usersFilePath;
        this.wordsFilePath = wordsFilePath;
        highScoresManager = new HighScoresManager(highScoresFilePath);
    }
    public Session getSessionActuel() {return sessionActuel;}




    /**
     * Récupère les meilleurs scores depuis le fichier des meilleurs scores
     * @return TreeMap<Integer, String>
     */
    public Map<Integer, String> getHighScores() {
        return highScoresManager.getHighScores();
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
        player = loginChecker.getPlayer(pseudonyme);
        return true;
    }

    /**
     * Ajoute un nouveau joueur au jeu
     * @param pseudonyme
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void AddPlayer(String pseudonyme) throws IOException, ClassNotFoundException, IllegalNicknameException {
        player = new Player(pseudonyme);
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
     *
     */
    public void StartSession( ){
        WordsGenerator generator = new WordsGenerator(wordsFilePath);
        try {
            generator.genererListeMotsSeance();
            sessionActuel = new Session(player, generator.getMotsSeance());
        } catch (IOException e) {

        }

    }

    /**
     * Termine la session en cours et sauvegarde les scores
     */
    public void EndSession(){
        addHighScores();
        notifyObservers();

    }


    /**
     * Mis à jour les meilleurs scores et les sauvegarde
     */
    public void addHighScores(){
        ArrayList<Integer> scores = sessionActuel.getScores();
        String player = sessionActuel.getPlayer().getPseudonyme();
        highScoresManager.addHighScores(player,scores);
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
            o.update( this,sessionActuel.isSessionTerminee());
        }
    }
}

