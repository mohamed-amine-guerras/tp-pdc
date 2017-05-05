package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;


/**
 * Created by Amine on 04/05/2017.
 */
public class UserLoginController implements Controller {
    private MainApp mainApp;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXButton connectButton;


    private JFXDialog dialog;


    @FXML
    void onSignupButton(ActionEvent event) {

    }

    @FXML
    void onConnectButton(ActionEvent event) {
        String username = userField.getText();
        if (username == null || username.isEmpty()){
            JFXDialog dialog = new JFXDialog();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/fxml/ConfirmationDialogBox.fxml"));
            Region region = null;
            try {
                region = loader.load();
                ((ConfirmationDialogBoxController) loader.getController()).setDialog("Erreur",
                        "Veuillez introduire votre pseudonyme!");
                ((ConfirmationDialogBoxController) loader.getController()).setDialog(dialog);
                dialog.setContent(region);
                dialog.show(rootStackPane);
            } catch(IOException e) {
                e.printStackTrace();
            }


        }
        else {

        }

    }


    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}
