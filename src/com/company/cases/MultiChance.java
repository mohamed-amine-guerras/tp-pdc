package com.company.cases;

import com.company.Sanctionnable;

/**
 * Created by Amine on 17/04/2017.
 */
public class MultiChance extends Case implements Sanctionnable {

    private int nbTentativesRestant;
    private final int NB_MAX_TENTATIVE = 2;
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
    public boolean tentative(char lettre) {
        nbTentativesRestant--;
        if(lettre == getValeur()){
            setScore(BONUS);
            setSuceces(true);
        }else if(nbTentativesRestant == 0){
            setFail(true);
        }else{
            this.malus = this.malus + this.MALUS;
        }
        System.out.println(isSuceces());
        return isFail();
    }
}