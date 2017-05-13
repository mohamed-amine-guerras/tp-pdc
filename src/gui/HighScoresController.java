package gui;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import static gui.HomeController.getPendu;


/**
 * Created by Amine on 12/05/2017.
 */
public class HighScoresController implements Initializable,Controller{



    @FXML
    private JFXTreeTableView<Score> highScoresList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Création de la colonne des scores*/
        JFXTreeTableColumn<Score,String> scoreColumn = new JFXTreeTableColumn<>("Score");
        scoreColumn.setPrefWidth(150);
        scoreColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Score,String> param) ->{
            if(scoreColumn.validateValue(param)) return param.getValue().getValue().score;
            else return scoreColumn.getComputedValue(param);
        });

        /***
         * Création de la colonne des joueurs
         * */
        JFXTreeTableColumn<Score, String> playerColumn = new JFXTreeTableColumn<>("Joueur");
        playerColumn.setPrefWidth(150);
        playerColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Score, String> param) ->{
            if(playerColumn.validateValue(param)) return param.getValue().getValue().player;
            else return playerColumn.getComputedValue(param);
        });





// data
        /**
         * On importe les meilleures scores depuis la classe Pendu
         * */
        TreeMap<Integer, String> treeMap = getPendu().getHighScores();
        ObservableList<Score> Scores = FXCollections.observableArrayList();
        if(treeMap != null && !treeMap.isEmpty()){
            for (Map.Entry<Integer,String> e:treeMap.entrySet()) {
                Scores.add(new Score(e.getValue(),e.getKey()));

            }
        }




// build tree
        /** Création du TreeTableView avec les donneés importées
         * */
        final TreeItem<Score> root = new RecursiveTreeItem<Score>(Scores, RecursiveTreeObject::getChildren);
        highScoresList.setRoot(root);
        highScoresList.setShowRoot(false);
        highScoresList.getColumns().setAll(scoreColumn,playerColumn);


    }


    @Override
    public void setPendu(Pendu pendu) {


    }
}
