package gui;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Created by Amine on 05/05/2017.
 */
public class UserCreationController {
    private UserLoginController userLoginController;
    private Pendu pendu;


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
            } catch (IOException|ClassNotFoundException e) {
                userLoginController.showDialogBox("ERREUR","Erreur lors de l'écriture dans le fichier des psudonymes");
            }
        }

    }
}
