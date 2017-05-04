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
        usersHashMap = new HashMap<>();
    }
    public boolean Find(String pseudonyme) throws IOException {

        InitializeUsersHashMap();

        return usersHashMap.containsKey(pseudonyme);
    }
    private void InitializeUsersHashMap() throws FileNotFoundException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(usersFile))));
            Player player ;
            boolean exist = true;
            while (exist){
                player = (Player) objectInputStream.readObject();
                usersHashMap.put(player.getPseudonyme(),player);
            }

        }
        catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }
    public void Addpseudonyme(String pseudonyme) throws IOException {
        Player player = new Player(pseudonyme);
        InitializeUsersHashMap();
        if (!usersHashMap.containsKey(pseudonyme)){
            usersHashMap.put(player.getPseudonyme(),player);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(usersFile))));
            for (HashMap.Entry e: usersHashMap.entrySet()) {
                objectOutputStream.writeObject(e.getValue());

            }

            objectOutputStream.close();
        }

    }

}
