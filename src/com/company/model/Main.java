package com.company.model;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        LoginChecker loginChecker = new LoginChecker("users.dat");
        try {
            System.out.println(loginChecker.Find("Amine"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
