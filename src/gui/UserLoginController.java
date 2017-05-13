package gui;

import com.company.model.LoginNotFoundException;
import com.company.model.Pendu;
import com.company.model.Session;
import com.company.model.mots.WordsGenerator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;

import static gui.MainApp.CONFIRMATION_DIALOG_BOX;


/**
 * Created by Amine on 04/05/2017.
 */
public class UserLoginController implements Controller {
    private Pendu pendu;
    private ArrayList<JFXDialog> dialogs = new ArrayList<>();

    @Override
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
        JFXDialog dialog = new JFXDialog();
        dialogs.add(dialog);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/fxml/UserCreationView.fxml"));
        Region region = null;
        try {
            region = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
        ((UserCreationController) loader.getController()).setUserLoginController(this);
        dialog.setContent(region);
        ((UserCreationController)loader.getController()).setPendu(pendu);
        dialog.show(rootStackPane);


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
                    pendu.StartSession(pendu.getPlayer(pseudonyme),new WordsGenerator().getMotsSeance());
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
        dialogs.add(dialog);
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
    public void hideAll(){
        for (JFXDialog d:dialogs) {
            d.close();

        }
    }

}
