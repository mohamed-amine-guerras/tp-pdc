package com.company;

/**
 * Created by Amine on 17/04/2017.
 */
public class LoginNotFoundException extends Exception {
    public LoginNotFoundException() {
    }

    public LoginNotFoundException(String message) {

        super(message);
    }
}
