package com.company;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Amine on 17/04/2017.
 */
public class Session {
    private Player player;
    private HashMap<String,Mot> mots;
    private Mot motActuel;
    private Iterator<Mot> iterator;
    private boolean sessionTerminee;


    public Session(Player player, HashMap<String, Mot> mots, Mot motActuel, Iterator<Mot> iterator) {
        this.player = player;
        this.mots = mots;
        this.motActuel = iterator.next();
        this.iterator = iterator;
        this.sessionTerminee = false;
    }

    public boolean isSessionTerminee() {
        return sessionTerminee;
    }

    public void VerificationCase(char c, int indexCase){
        if (motActuel.Verification(c,indexCase) == false){
            player.setScoreActuel(motActuel.getScore());
            if (iterator.hasNext()){
                motActuel = iterator.next();
            }
            else {
                sessionTerminee = true;
                player.AddScore();
                player.ResetScore();

            }

        }
        else player.setScoreActuel(motActuel.getScore());


    }





}
