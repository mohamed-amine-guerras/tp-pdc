package gui;

import com.company.model.Pendu;
import com.company.model.Player;
import com.company.model.mots.WordsGenerator;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import static gui.MainApp.NEW_SESSION;

/**
 * Created by Amine on 05/05/2017.
 */
public class UserCreationController {
    private UserLoginController userLoginController;
    private Pendu pendu;
    private GridPane gridPane;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void setPendu(Pendu pendu) {
        this.pendu = pendu;
    }

    public void setUserLoginController(UserLoginController userLoginController) {
        this.userLoginController = userLoginController;
    }

    @FXML
    private JFXTextField userField;

    @FXML
    void onConfirmButton(ActionEvent event) {
        String pseudonyme = userField.getText();
        if (pseudonyme == null || pseudonyme.isEmpty()){
            userLoginController.showDialogBox("ERREUR","Veuillez introduire un pseudonyme!");
        }
        else {
            try {
                pendu.AddPseudonyme(pseudonyme);
                userLoginController.hideAll();
                userLoginController.showDialogBox("Succés","Bienvenue "+pseudonyme+" dans le jeu!");
                pendu.StartSession(new Player(pseudonyme),new WordsGenerator().getMotsSeance());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(NEW_SESSION));
                try {
                    Parent parent = loader.load();
                    ((Controller) loader.getController()).setPendu(pendu);
                    ((SessionViewController)loader.getController()).setGridPane1(gridPane);
                    gridPane.add(parent,0,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException|ClassNotFoundException e) {
                userLoginController.showDialogBox("ERREUR","Erreur lors de l'écriture dans le fichier des psudonymes");
            }
        }

    }
}
