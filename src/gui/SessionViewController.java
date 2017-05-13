package gui;

import com.company.model.Pendu;
import com.company.model.Player;
import com.company.model.Session;
import com.company.model.mots.Mot;
import com.company.model.mots.WordsGenerator;
import com.company.model.mots.cases.Case;
import com.company.model.mots.cases.MultiChance;
import com.company.model.mots.cases.Proposition;
import com.company.model.mots.cases.ZeroChance;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import static gui.MainApp.SESSION_VIEW;

/**
 * Created by hamza on 08/05/2017.
 */
public class SessionViewController  implements Controller,Observer {

    private MainApp manApp;
    private Mot mot;
    private Pendu pendu;
    private Session session;
    @FXML
    private GridPane gridPane;

    @FXML
    private HBox generalInfoContainer;

    @FXML
    private HBox boxesContainer;

    @FXML
    private HBox indecationContainer;

    @FXML
    private Label pseudonymeLabel;

    @FXML
    private Label scoreLabel;


    @FXML
    private Label indecationTypeLabel;

    @FXML
    private Label indecationValueLable;

    public void setPendu(Pendu pendu) {
        this.pendu = pendu;
    }

    void setup(){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(SESSION_VIEW));
        boxesContainer.getStylesheets().add("resources/fxml/boxesStyles.css");
        WordsGenerator generator = new WordsGenerator();
        try {
            generator.genererListeMotsSeance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = new Session(new Player("Hamza"),generator.getMotsSeance());
        session.getPlayer().addObserver(this);
        ((Observable)session).addObserver(this);
        pseudonymeLabel.setText(session.getPlayer().getPseudonyme());
        mot = session.getMotActuel();
        scoreLabel.setText("0");
        indecationValueLable.setText(mot.getIndication().getValeur());
        indecationTypeLabel.setText(mot.getIndication().toString());
        genererCases();
    }

    private void genererCases(){
        int i =0;
        HashMap<Node,Integer> hashMap = new HashMap<>();
        boxesContainer.getChildren().clear();
        for(Case box : this.mot.getEnsemblesCases()){
            JFXButton button = new JFXButton();
            hashMap.put(button,i);
            i++;
            button.getStyleClass().add("boxe-class");
            button.setButtonType(JFXButton.ButtonType.RAISED);
            if(box instanceof ZeroChance){
                button.setId("zero-chance");
                button.setOnAction(e->{
                    EventHandler<KeyEvent> keyEvent = ke->{
                        KeyCode kc = ke.getCode();
                        button.setText(String.valueOf(kc));
                        button.setDisable(true);
                        session.VerificationCase(String.valueOf(kc).toLowerCase().charAt(0), hashMap.get(button));
                    };
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                });

                boxesContainer.getChildren().add(button);


            }else if(box instanceof MultiChance){
                button.setId("multi-chance");
                button.setOnAction(e->{
                    EventHandler<KeyEvent> keyEvent = ke->{
                        KeyCode kc = ke.getCode();
                        button.setText(String.valueOf(kc));
                        session.VerificationCase(button.getText().toLowerCase().charAt(0),hashMap.get(button));
                        if(box.isFail() || box.isSuceces()) button.setDisable(true);
                    };
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                });
                boxesContainer.getChildren().add(button);
            }


            else {
                VBox vBox = new VBox();
                button.setId("proposition");
                JFXNodesList propositions = new JFXNodesList();
                propositions.getStylesheets().add("resources/fxml/boxesStyles.css");
                propositions.addAnimatedNode(button);
                for(Character c : ((Proposition)box).getProposition()){
                    JFXButton prop = new JFXButton(String.valueOf(c).toUpperCase());
                    prop.setOnAction(e->{
                        button.setText(prop.getText());
                        propositions.animateList();
                        button.setDisable(true);
                        session.VerificationCase(button.getText().toLowerCase().charAt(0), hashMap.get(button));
                    });
                    prop.getStyleClass().add("boxe-class");
                    prop.setId("proposition");
                    propositions.addAnimatedNode(prop);
                }
                vBox.getChildren().add(propositions);
                propositions.setSpacing(10);
                boxesContainer.getChildren().add(vBox);
            }
        }
        boxesContainer.setSpacing(20);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.manApp = mainApp;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            scoreLabel.setText(String.valueOf((int)arg));
        } else if (o instanceof Session){
            mot = (Mot) arg;
            indecationValueLable.setText(mot.getIndication().getValeur());
            indecationTypeLabel.setText(mot.getIndication().toString());
            genererCases();
        }else if(o instanceof Pendu) {

        }
    }
}
