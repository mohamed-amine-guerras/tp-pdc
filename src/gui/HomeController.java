package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Created by Amine on 04/05/2017.
 */
public class HomeController implements Controller {
    private MainApp mainApp ;

    @FXML
    private VBox container;


    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}
