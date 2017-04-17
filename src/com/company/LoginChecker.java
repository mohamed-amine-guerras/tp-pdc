package com.company;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.Buffer;

/**
 * Created by Amine on 17/04/2017.
 */
public class LoginChecker {
    private String UsersFile;
    public LoginChecker(){}

    public LoginChecker(String usersFile) {
        UsersFile = usersFile;
    }
    public boolean Find(String pseudonyme){
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(UsersFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
