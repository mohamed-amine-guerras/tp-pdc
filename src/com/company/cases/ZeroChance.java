package com.company.cases;

/**
 * Created by Amine on 17/04/2017.
 */
public class ZeroChance extends Case {
    private final int BONUS = 3;

    public ZeroChance(char valeur) {
        super(valeur);
    }

    @Override
    public boolean tentative(char lettre) {
        if(lettre == getValeur()){
            setScore(BONUS);
            setSuceces(true);
        }
        else setFail(true);
        System.out.println(isSuceces());
        return isFail();
    }
}
