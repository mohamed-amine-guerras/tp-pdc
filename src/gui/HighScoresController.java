package gui;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.NavigableMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import static gui.HomeController.getPendu;
import static gui.MainApp.Home;


/**
 * Created by Amine on 12/05/2017.
 */
public class HighScoresController implements Initializable,Controller{

    private GridPane gridPane;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @FXML
    private JFXButton previousButton;


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
        NavigableMap<Integer, String> treeMap = getPendu().getHighScores();
        ObservableList<Score> Scores = FXCollections.observableArrayList();
        if(treeMap != null && !treeMap.isEmpty()){
            for (Map.Entry<Integer,String> e:treeMap.descendingMap().entrySet()) {
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



    @FXML
    void onPreviousButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Home));
        try {
            Parent parent = loader.load();
            ((HomeController)loader.getController()).setGridPane(gridPane);
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setPendu(Pendu pendu) {


    }
}
