package com.company.model.mots;
;
import com.company.model.mots.cases.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Amine on 17/04/2017.
 */
public class Mot{

    private final int NB_CASES_SANCTION = 5;
    private final int NB_CASES_LIMITES = 3;

    private Indication indication;
    private String valeur;
    private ArrayList<Case> ensemblesCases = new ArrayList<>();
    private ArrayList<Sanctionnable> ensmblesCasesSanctionnables = new ArrayList<>();
    private int score = 0;
    private boolean motTerminee;
    private boolean motSanctionnabl;
    private int nbSuccess;
    private boolean correct = false;
    private ArrayList<Observer> observers = new ArrayList<>();

    public boolean isMotSanctionnabl() {
        return motSanctionnabl;
    }

    public Mot(Indication indication, String valeur) {
        this.indication = indication;
        this.valeur = valeur;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isMotTerminee() {
        return motTerminee;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Case> getEnsemblesCases() {
        return ensemblesCases;
    }

    public String getValeur() {
        return valeur;
    }

    /*
     *Met à jour le score d'un a l'issue d'un tentative
     */

    private void updateScore(Case box){
        this.score = indication.getCoefition()*box.getScore();
        if(ensmblesCasesSanctionnables.contains(box) && !box.isSuceces()) this.score = this.score - ((Sanctionnable)box).getMalus(motSanctionnabl);
    }
    /*
    * Générer les cases formant les mots et met à jours l'attribut motSanctionnabl si nécessaire
     */
    public void genererCases(){
        char[] lettre = this.valeur.toCharArray();// On convertit le String en un tableau de carractères
        int i = 0;
        int choix;
        int nbCasesLimite = 0;
        Case box = null;
        Random random = new Random();
        boolean finished = false;
        while (!finished){// Tant que les carractères ne sont pas converti en case
            choix = abs(random.nextInt())%3;// On choisis le type de la case à instancier selon le résultat
            switch (choix){
                case 0 :
                    box = new MultiChance(lettre[i]);
                    ensemblesCases.add(box);
                    i++;
                    this.ensmblesCasesSanctionnables.add((Sanctionnable)box); // Vu que c'est une case sanctionnalbe
                    break;
                case 1 :
                  if(nbCasesLimite < NB_CASES_LIMITES){ // On controle la limite de nombre des cases avant d'insatncier
                      box = new Proposition(lettre[i]);
                      ensemblesCases.add(box);
                      nbCasesLimite++;
                      this.ensmblesCasesSanctionnables.add((Sanctionnable) box);
                      i++;
                  }

                  break;
                case 2 :
                    if(nbCasesLimite < NB_CASES_LIMITES){
                        box = new ZeroChance(lettre[i]);
                        i++;
                        nbCasesLimite++;
                        ensemblesCases.add(box);
                    }
                    break;
        }
        if (i == lettre.length) {
                finished = true;
                if(ensmblesCasesSanctionnables.size()>NB_CASES_SANCTION) motSanctionnabl = true; // le mot est sanctionnable si la condition est verrifié
        }
    }
    }

    /**
     * Verrifie la tentative de l'utilisateur sur une case choisis du mot
     * @param c le carractère entré pa l'utilisateur
     * @param index le rang de la case dans le mot
     * @return false si le joueur ne peut pas continuer
     */
    public boolean Verification(char c, int index) {
        boolean stop = false;
        Case box = ensemblesCases.get(index);
        box.tentative(c);
        stop = ensemblesCases.get(index).isFail();//On s'arrete si le joueur échoue
        if (stop) {
            correct = false;
        }
        if (box.isSuceces()) nbSuccess++;// si le joueur entre le bon carractère on incrémente le nombre de succes
        System.out.println("nbSuccess = " + nbSuccess);
        if (nbSuccess == ensemblesCases.size()) {// si le joueur réussi tous les case
            motTerminee = true; // donc le mot est terminé
            System.out.println("Mot termine");
            correct = true;
            stop = true; // le joueur ne peut pas continuer
        }
        updateScore(box);
        System.out.println("score = "+this.score);
        return !stop;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mot)) return false;

        Mot mot = (Mot) o;

        return valeur != null ? valeur.equals(mot.valeur) : mot.valeur == null;
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }

    @Override
    public String toString() {
        return valeur  ;
    }

    public Indication getIndication() {
        return indication;
    }

}
