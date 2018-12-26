package com.company.model;

import com.company.model.mots.Mot;

import java.util.Set;

public interface IWordsGenerator {
    Set<Mot> getMotsSeance();
}
