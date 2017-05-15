package gui;/**
 * Created by Amine on 04/05/2017.
 */

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    /**
     * Les différentes scenes du jeu
     */
    static String HOME = "/resources/fxml/HomeView.fxml";
    static String Home = "/resources/fxml/Home.fxml";
    static final String NEW_SESSION = "/resources/fxml/UserLoginView.fxml";
    static final String CONFIRMATION_DIALOG_BOX = "/resources/fxml/ConfirmationDialogBox.fxml";
    static final String HIGH_SCORES = "/resources/fxml/HighScoresView.fxml";
    static final String OPTIONS = "/resources/fxml/OptionsView.fxml";
    static final String SESSION_VIEW = "/resources/fxml/SessionView.fxml";
    static final String END_SESSION = "/resources/fxml/EndSessionView.fxml";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(HOME));
        Parent home = loader.load();
        JFXDecorator decorator = new JFXDecorator(primaryStage,home);
        primaryStage.setTitle("Le Pendu");
        primaryStage.setScene(new Scene(decorator, 800, 700));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
