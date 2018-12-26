package gui;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.MainApp.*;

/**
 * Created by Amine on 04/05/2017.
 */
public class HomeController implements Initializable {
    private static String usersFilePath = "users.dat";
    private static String wordsFilePath = null;
    private static Pendu pendu ;
    private GridPane gridPane;

    public static String getWordsFilePath() {
        return wordsFilePath;
    }

    public static Pendu getPendu() {
        return pendu;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public static void setUsersFilePath(String usersFilePath) {
        HomeController.usersFilePath = usersFilePath;
    }


    @FXML
    private VBox container;

    @FXML
    private JFXButton newSessionButton;

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXButton highScoresButton;

    @FXML
    private JFXButton optionsButton;

    @FXML
    private JFXButton resumeSessionButton;

    /**
     * Ouvrir une nouvelle session
     * @param event
     */
    @FXML
    void onNewSessionButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(NEW_SESSION));
        try {
            Parent parent = loader.load();
            ((Controller) loader.getController()).setPendu(pendu);
            ((UserLoginController)loader.getController()).setGridPane(gridPane);
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
          }
        }


    /**
     * Visualiser les meilleurs scores
     * @param event
     */
    @FXML
    void onHighScoreButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(HIGH_SCORES));
        try {
            Parent parent = loader.load();
            ((HighScoresController)loader.getController()).setGridPane(gridPane);
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    /**
     * Configurer les options
     * @param event
     */
    @FXML
    void onOptionsButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(OPTIONS));
        try {
            Parent parent = loader.load();
            ((OptionsController)loader.getController()).setGridPane(gridPane);
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    /**
     * Quitter le jeu
     * @param event
     */
    @FXML
    void onExitButton(ActionEvent event) {

        ((Stage)exitButton.getScene().getWindow()).close();
        }

    /**
     * Modifier l'emplacement du fichier des mots
     * @param wordsFilePath
     */
    public static void setWordsFilePath(String wordsFilePath){
        if (wordsFilePath != null){
            ObjectOutputStream objectOutputStream = null;
            try { /** ouvrir le fichier contenant l'emplacement du fichier des mots*/
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("wordsPath.dat")));
                objectOutputStream.writeObject(wordsFilePath); /** Mis à jour*/
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Initialize le chemin vers le fichiers des mots
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File("wordsPath.dat")));
            wordsFilePath = (String) objectInputStream.readObject();
            objectInputStream.close();
            pendu =  new Pendu(usersFilePath, wordsFilePath) ;
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}


