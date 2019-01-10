package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import static controller.HomeController.setWordsFilePath;
import static controller.MainApp.Home;

/**
 * Created by Amine on 13/05/2017.
 */
public class OptionsController {


    private GridPane gridPane;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @FXML
    private JFXButton previousButton;

    @FXML
    private JFXButton executeButton;

    @FXML
    private JFXButton browseButton;

    @FXML
    private JFXTextField textField;

    /**
     * Ouvrir le selectionneur des fichier
     * @param event
     */
    @FXML
    void onBrowsButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichiers Texte","*"));
        chooser.setTitle("Séléctionner le fichier des mots...");
        File result = chooser.showOpenDialog(null);
        if(result != null) {
            textField.setText(result.getAbsolutePath());
        }

    }


    /**
     * Retourner à l'accueil
     * @param event
     */
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

    /**
     * Modifier l'emplacement du fichier des mots
     * @param event
     */
    @FXML
    void onExecuteButton(ActionEvent event) {
        setWordsFilePath(textField.getText());

    }

}
