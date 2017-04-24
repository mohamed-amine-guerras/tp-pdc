package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        LoginChecker loginChecker = new LoginChecker("users.dat");
        try {
            System.out.println(loginChecker.Find("raouf"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
