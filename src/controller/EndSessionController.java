package controller;

import com.company.model.Pendu;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

import static controller.MainApp.Home;
import static controller.MainApp.SESSION_VIEW;

/**
 * Created by Amine on 15/05/2017.
 */
public class EndSessionController {
    private Pendu pendu;
    private GridPane gridPane;



    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
    public void setScore(String score) {
        this.score.setText(score);
    }

    @FXML
    private ImageView imageView;

    public void setImageView(String imagePath) {
        this.imageView.setImage(new Image(imagePath));
    }

    @FXML
    private Text score;

    @FXML
    private JFXButton replayButton;

    @FXML
    private JFXButton homeButton;

    /**
     * Rejouer une nouvelle session
     * @param event
     */
    @FXML
    void onReplayButton(ActionEvent event) {
        try {
            pendu.StartSession();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(SESSION_VIEW));
            Parent parent = loader.load();
            ((SessionViewController)loader.getController()).setGridPane1(gridPane);
            gridPane.add(parent,0,1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aller à l'accueil
     * @param event
     */
    @FXML
    void onHomeButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Home));
        try {
            Parent parent = loader.load();
            ((HomeController)loader.getController()).setGridPane(gridPane);
            gridPane.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
