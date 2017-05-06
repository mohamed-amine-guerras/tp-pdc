package com.company.model;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Amine on 17/04/2017.
 */
public class LoginChecker {
    private String usersFile;
    private HashMap<String,Player> usersHashMap;
    public LoginChecker(){}

    public LoginChecker(String usersFile) {
        this.usersFile = usersFile;
    }
    /**
     * Cette méthode vérifie si un joueur existe ou pas dans le fichier des joueurs
     * **/
    public boolean Find(String pseudonyme) throws IOException, ClassNotFoundException {

        InitializeUsersHashMap();

        return usersHashMap.containsKey(pseudonyme);
    }
    /**
     * Cette méthode initialise la HashMap qui contient les joueurs à partir du fichier des joueurs
     * **/
    private void InitializeUsersHashMap()  {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(usersFile))));
            usersHashMap = (HashMap<String, Player>) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            usersHashMap = null;
        }
    }
    /**
    * *Cette méthode ajoute un joueur au fichier des joueurs
     * */
    public void Addpseudonyme(String pseudonyme) throws IOException, ClassNotFoundException {
        Player player = new Player(pseudonyme);
        InitializeUsersHashMap();
        if (usersHashMap == null) {
            usersHashMap = new HashMap<>();
        }
        if (!usersHashMap.containsKey(pseudonyme)) {
            usersHashMap.put(player.getPseudonyme(), player);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(usersFile))));
            objectOutputStream.writeObject(usersHashMap);
            objectOutputStream.close();
            }
        }



        }



