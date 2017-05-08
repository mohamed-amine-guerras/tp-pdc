package com.company.model;

import com.company.model.mots.Mot;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Amine on 17/04/2017.
 */
public class Pendu {
    private Session sessionActuel;
    private  String UsersFilePath;
    private String WordsFilePath;
    private boolean sessionTerminee;

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
}
