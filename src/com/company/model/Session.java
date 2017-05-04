package com.company.model;

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
    private final int nombreEchecsMax = 6;
    private int nombreEchecsActuel;


    public Session(Player player, HashMap<String, Mot> mots, Mot motActuel, Iterator<Mot> iterator) {
        this.player = player;
        this.mots = mots;
        this.motActuel = iterator.next();
        this.iterator = iterator;
        this.sessionTerminee = false;
        this.nombreEchecsActuel = 0 ;

    }

    public Session() {

    }

    public boolean isSessionTerminee() {
        return sessionTerminee;
    }

    public void VerificationCase(char c, int indexCase){
        if (motActuel.Verification(c,indexCase) == false){ /**la méthode Verification renvoie faux si le joueur ne peut pas continuer  */
            player.setScoreActuel(motActuel.getScore());   /**  ou le joueur a finie le mot en entier*/
            if (!motActuel.isMotTerminee()){ /** i.e. le jour a echoué  */
                nombreEchecsActuel++;
                if (nombreEchecsActuel >= nombreEchecsMax){
                    sessionTerminee = true;
                    player.AddScore(); /** on sauvegarde le score de la sesion*/
                    player.ResetScore();/** on reinitialise le compteur*/
                }
            }
            if (iterator.hasNext()){/** il reste des mots à deviner*/
                motActuel = iterator.next();
            }
            else {/** le joueur a finie tous les mots*/
                sessionTerminee = true;
                player.AddScore(); /** on sauvegarde le score de la sesion*/
                player.ResetScore();/** on reinitialise le compteur*/

            }

        }
        else player.setScoreActuel(motActuel.getScore());


    }





}
