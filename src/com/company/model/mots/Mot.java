package com.company.model.mots;
;
import com.company.model.mots.cases.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Amine on 17/04/2017.
 */
public class Mot {

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

    public Mot(Indication indication, String valeur) {
        this.indication = indication;
        this.valeur = valeur;
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

    /*
     *Calcule le score obtenu par l'ensemble de tentative sur les cases
     */

    public void calculerScore(){
        for(Case cas : ensemblesCases){
            this.score = this.score + cas.getScore();
        }
        this.score = this.score* indication.getCoefition();
        for(Sanctionnable  caseSanctionnable : this.ensmblesCasesSanctionnables){
            this.score = this.score - caseSanctionnable.getMalus(this.motSanctionnabl);
        }
    }
    /*
    * Générer les cases formant les mots et met à jours l'attribut motSanctionnabl si nécessaire
     */
    public void genererCases(){
        char[] lettre = this.valeur.toCharArray();
        int i = 0;
        int choix;
        int nbCasesSanctionnabl = 0;
        int nbCasesLimite = 0;
        Case cas = null;
        Random random = new Random();
        boolean finished = false;
        while (!finished){
            choix = random.nextInt()%3;
            switch (choix){
                case 0 :
                    cas = new MultiChance(lettre[i]);
                    ensemblesCases.add(cas);
                    i++;
                    this.ensmblesCasesSanctionnables.add((Sanctionnable)cas);
                    nbCasesSanctionnabl++;
                    if (nbCasesSanctionnabl == NB_CASES_SANCTION) motSanctionnabl = true;
                    break;
                case 1 :
                  if(nbCasesLimite < NB_CASES_LIMITES){
                      cas = new Proposition(lettre[i]);
                      ensemblesCases.add(cas);
                      nbCasesLimite++;
                      this.ensmblesCasesSanctionnables.add((Sanctionnable) cas);
                      i++;
                      nbCasesSanctionnabl++;
                      if (nbCasesSanctionnabl == NB_CASES_SANCTION) motSanctionnabl = true;
                  }

                  break;
                case 2 :
                    if(nbCasesLimite < NB_CASES_LIMITES){
                        cas = new ZeroChance(lettre[i]);
                        i++;
                        nbCasesLimite++;
                        ensemblesCases.add(cas);
                    }
                    break;
        }
        if (i == lettre.length) finished = true;
    }
    }

    public boolean Verification(char c,int index){
        boolean stop = false;
        Case box = ensemblesCases.get(index);
        box.tentative(c);
        stop = ensemblesCases.get(index).isFail();//On s'arrete si le joueur échoue
        if(box.isSuceces()) nbSuccess++;// si le joueur entre le bon carractère on incrémente le nombre de succes
        System.out.println("nbSuccess = "+nbSuccess);
        if(nbSuccess == ensemblesCases.size()){// si le joueur réussi tous les case
            motTerminee = true; // donc le mot est terminé
            stop = true; // le joueur ne peut pas continuer
        }

        return stop;
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
}
