package com.company;

/**
 * Created by Amine on 17/04/2017.
 */
public class Pendu {
    private Session sessionActuel;
    private  String UsersFilePath;
    private String WordsFilePath;
    private boolean sessionTerminee;



    public void LoginCheck(String pseudonyme) throws LoginNotFoundException {
        LoginChecker loginChecker = new LoginChecker(UsersFilePath);
        if (!loginChecker.Find(pseudonyme)) throw new LoginNotFoundException();
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
}
