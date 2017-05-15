package gui;

import com.company.model.Pendu;
import com.company.model.mots.WordsGenerator;
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

import static gui.HomeController.getWordsFilePath;
import static gui.MainApp.Home;
import static gui.MainApp.SESSION_VIEW;

/**
 * Created by Amine on 15/05/2017.
 */
public class EndSessionController implements Controller {
    private Pendu pendu;
    private GridPane gridPane;

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

    @FXML
    void onReplayButton(ActionEvent event) {
        WordsGenerator generator = new WordsGenerator(getWordsFilePath());
        try {
            generator.genererListeMotsSeance();
            pendu.StartSession(pendu.getSessionActuel().getPlayer(), generator.getMotsSeance());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(SESSION_VIEW));
            Parent parent = loader.load();
            ((Controller) loader.getController()).setPendu(pendu);
            ((SessionViewController)loader.getController()).setGridPane1(gridPane);
            gridPane.add(parent,0,1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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



    @Override
    public void setPendu(Pendu pendu) {
        this.pendu = pendu;

    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
