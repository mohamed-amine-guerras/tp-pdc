package gui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Amine on 12/05/2017.
 */
public class Score extends RecursiveTreeObject<Score> {
    StringProperty player;
    StringProperty score;

    public Score(String player, int score) {
        this.player = new SimpleStringProperty(player);
        this.score = new SimpleStringProperty(String.valueOf(score));
    }
}

