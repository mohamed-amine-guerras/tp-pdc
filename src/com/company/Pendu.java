package com.company;

import org.omg.CORBA.Object;

import java.io.*;

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

    public void LoginCheck(String pseudonyme) throws LoginNotFoundException, IOException {
        LoginChecker loginChecker = new LoginChecker(UsersFilePath);
        if (!loginChecker.Find(pseudonyme)) throw new LoginNotFoundException("Ce pseudonyme n'existent pas");
    }
    public void VerificationCase(char c,int indexCase){
        if (!sessionActuel.isSessionTerminee()){
            sessionActuel.VerificationCase(c,indexCase);
            if (sessionActuel.isSessionTerminee()) EndSession();
        }

    }

    public void StartSession(){
        sessionActuel = new Session();
    }
    public void EndSession(){
        sessionTerminee = true;
    }
    public void AddPseudonyme(String pseudonyme) throws IOException {
       new LoginChecker(UsersFilePath).Addpseudonyme(pseudonyme);
    }
}
