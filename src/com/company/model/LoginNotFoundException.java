package com.company.model;

/**
 * Created by Amine on 17/04/2017.
 */

/**
 * C'est un exception levée lorsque le pesudonyme entré par le joueur est inexistant
 */
public class LoginNotFoundException extends Exception {
    public LoginNotFoundException() {
    }

    public LoginNotFoundException(String message) {

        super(message);
    }
}
