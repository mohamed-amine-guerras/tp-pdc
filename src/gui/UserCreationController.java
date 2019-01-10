package gui;

import com.company.model.IllegalNicknameException;
import com.company.model.Pendu;
import com.company.model.Player;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import static gui.MainApp.SESSION_VIEW;

/**
 * Created by Amine on 05/05/2017.
 */
public class UserCreationController {
    private UserLoginController userLoginController;
    private Pendu pendu = Pendu.getInstance();
    private GridPane gridPane;

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }


    public void setUserLoginController(UserLoginController userLoginController) {
        this.userLoginController = userLoginController;
    }

    @FXML
    private JFXTextField userField;

    /**
     *Création d'un nouveau joueur
     * @param event
     */
    @FXML
    void onConfirmButton(ActionEvent event) {
        String pseudonyme = userField.getText();
        if (pseudonyme == null || pseudonyme.isEmpty()){
            userLoginController.showDialogBox("ERREUR","Veuillez introduire un pseudonyme!");
        }
        else {
            try {
                pendu.AddPlayer(pseudonyme);
                userLoginController.hideAll();
                try {
                    pendu.StartSession();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(SESSION_VIEW));
                    Parent parent = loader.load();
                    ((SessionViewController) loader.getController()).setGridPane1(gridPane);
                    gridPane.add(parent, 0, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                } catch (IllegalNicknameException e) {
                userLoginController.showDialogBox("ERREUR","Le pseudonyme doit commencer par une lettre!");
            }
        }
        }

    }

