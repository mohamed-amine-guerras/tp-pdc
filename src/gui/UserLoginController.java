package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;



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

    @FXML
    void onSignupButton(ActionEvent event) {

    }

    @FXML
    void onConnectButton(ActionEvent event) {

    }


    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}
