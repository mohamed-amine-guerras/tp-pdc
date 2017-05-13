package gui;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static gui.MainApp.HIGH_SCORES;
import static gui.MainApp.NEW_SESSION;

/**
 * Created by Amine on 04/05/2017.
 */
public class HomeController implements Controller {
    private static String usersFilePath = "users.dat";
    public static Pendu pendu = new Pendu(usersFilePath);

    public Pendu getPendu() {
        return pendu;
    }

    public static void setUsersFilePath(String usersFilePath) {
        HomeController.usersFilePath = usersFilePath;
    }

    private MainApp mainApp ;

    @FXML
    private GridPane gridPane;


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

    @FXML
    void onNewSessionButton(ActionEvent event) {
        setScene(NEW_SESSION);
        }



    @FXML
    void onHighScoreButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(HIGH_SCORES));
        try {
            Parent parent = loader.load();
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    @FXML
    void onOptionsButton(ActionEvent event) {
        }

    @FXML
    void onExitButton(ActionEvent event) {
        }

    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    @Override
    public void setPendu(Pendu pendu) {

    }

    public void setScene(String sceneName){
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource(sceneName));
       try {
           Parent parent = loader.load();
           ((Controller) loader.getController()).setMainApp(mainApp);
           ((Controller) loader.getController()).setPendu(pendu);
           gridPane.add(parent,0,1);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
