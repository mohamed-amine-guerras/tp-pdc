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

    private static String Home = "/resources/fxml/Home.fxml";
    static final String NEW_SESSION = "/resources/fxml/UserLoginView.fxml";
    static final String CONFIRMATION_DIALOG_BOX = "/resources/fxml/ConfirmationDialogBox.fxml";
    private Stage primary;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Home));
        Parent home = loader.load();

        ((Controller) loader.getController()).setMainApp(this);

        JFXDecorator decorator = new JFXDecorator(primaryStage,home);
        primaryStage.setTitle("Le Pendu");
        primaryStage.setScene(new Scene(decorator, 600, 500));
        primary = primaryStage;
        primaryStage.show();

    }

}
