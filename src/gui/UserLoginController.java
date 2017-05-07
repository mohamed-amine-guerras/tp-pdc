package gui;

import com.company.model.LoginNotFoundException;
import com.company.model.Pendu;
import com.company.model.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import static gui.MainApp.CONFIRMATION_DIALOG_BOX;


/**
 * Created by Amine on 04/05/2017.
 */
public class UserLoginController implements Controller {
    private MainApp mainApp;
    private Pendu pendu;

    public void setPendu(Pendu pendu) {
        this.pendu = pendu;
    }

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
        String pseudonyme = userField.getText();
        if (pseudonyme == null || pseudonyme.isEmpty()){
           showDialogBox("ERREUR","Veuillez introduire votre pseudonyme!");

        }
        else {
            try {
                boolean exist = pendu.LoginCheck(pseudonyme);
                if (exist){
                    pendu.StartSession();
                    //TODO switch to another scene (playing scene)
                }
            } catch (LoginNotFoundException e) {
                showDialogBox("ERREUR","Pseudonyme inexistant!");

            } catch (IOException | ClassNotFoundException e) {
                showDialogBox("ERREUR","Erreur lors de la lecture du fichier des pseudonymes");
            }

        }

    }

    public void showDialogBox(String title,String content){
        JFXDialog dialog = new JFXDialog();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(CONFIRMATION_DIALOG_BOX));
        Region region = null;
        try {
            region = loader.load();
            ((ConfirmationDialogBoxController) loader.getController()).setDialog(title,
                    content);
            ((ConfirmationDialogBoxController) loader.getController()).setDialog(dialog);
            dialog.setContent(region);
            dialog.show(rootStackPane);
        } catch(IOException e) {
            e.printStackTrace();
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
