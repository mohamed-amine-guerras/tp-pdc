package com.company.model.mots.cases;


/**
 * Created by Amine on 17/04/2017.
 */
public class MultiChance extends Case implements Sanctionnable {

    private int nbTentativesRestant;
    private final int NB_MAX_TENTATIVE = 3;
    private final int BONUS = 1;
    private final int MALUS = 2;
    private int malus;

    public MultiChance(char valeur) {
        super(valeur);
        this.nbTentativesRestant = NB_MAX_TENTATIVE;
    }

    public int getNbTentativesRestant() {
        return nbTentativesRestant;
    }

    @Override
    public int getMalus(boolean motSenctionnabl) {
        if(motSenctionnabl) return malus;
        else return 0;
    }

    @Override
    public void tentative(char lettre) {//Quand le joueur tente une lettre
        nbTentativesRestant--;//On decrémente le de tentatives restantes
        if(lettre == getValeur()){//ie. la lettre est correcte
            setScore(BONUS);//La case à comme score le bonus
            setSuceces(true);//La case se termine avec suces
            setFail(false);
        }else if(nbTentativesRestant == 0){//Si le joueur echoue dans toutes ses tentatives
            setFail(true);// il echoue la case
            this.malus = this.MALUS; //la case retourne un malus
        }else{
            this.malus = this.MALUS;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " : multichance";
    }
}
