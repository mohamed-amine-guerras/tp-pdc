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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static gui.MainApp.CONFIRMATION_DIALOG_BOX;
import static gui.MainApp.SESSION_VIEW;

/**
 * Created by hamza on 08/05/2017.
 */
public class SessionViewController  implements Controller,Observer,Initializable {

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

    @FXML
    private StackPane stackPane;
    private ArrayList<JFXDialog> dialogs = new ArrayList<>();

    public void setPendu(Pendu pendu) {
        this.pendu = pendu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(SESSION_VIEW));
        boxesContainer.getStylesheets().add("resources/fxml/boxesStyles.css");
        WordsGenerator generator = new WordsGenerator();
        try {
            generator.genererListeMotsSeance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pendu = new Pendu("hhhh");
        pendu.StartSession(new Player("Hamza"),generator.getMotsSeance());
        session = pendu.getSessionActuel();
        pendu.addObserver(this);
        session.getPlayer().addObserver(this);
        ((Observable)session).addObserver(this);
        pseudonymeLabel.setText(session.getPlayer().getPseudonyme());
        mot = session.getMotActuel();
        mot.addObserver(this);
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
            button.getStyleClass().removeAll("jfx-button");
            button.setButtonType(JFXButton.ButtonType.RAISED);

            if(box instanceof ZeroChance){
                button.setId("zero-chance");
                EventHandler<KeyEvent> keyEvent = ke->{
                    KeyCode kc = ke.getCode();
                    button.setText(String.valueOf(kc));
                    button.setDisable(true);
                    pendu.VerificationCase(String.valueOf(kc).toLowerCase().charAt(0), hashMap.get(button));
                };
                button.setOnAction(e->{
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                });
                button.removeEventFilter(KeyEvent.KEY_RELEASED,keyEvent);

                boxesContainer.getChildren().add(button);


            }else if(box instanceof MultiChance){
                EventHandler<MouseEvent> mouseEvent = me->{
                if(me.getClickCount() >= 1) button.setId("multi-chance");
                System.out.println(me.getClickCount());
            };
                button.setId("multi-chance");
                EventHandler<KeyEvent> keyEvent = ke->{
                    KeyCode kc = ke.getCode();
                    button.setText(String.valueOf(kc));
                    button.setId("multi-chance");
                    pendu.VerificationCase(button.getText().toLowerCase().charAt(0),hashMap.get(button));
                    if(!box.isFail()){
                        if(box.isSuceces()) button.setDisable(true);
                        else showDialogBox("Tentatives","Il vous reste "+((MultiChance)box).getNbTentativesRestant()+" tentatives");
                    }
                };
                button.setOnAction(e->{
                    button.setId("on-action");
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                    stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent);

                });
                button.removeEventFilter(KeyEvent.KEY_RELEASED,keyEvent);

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
                        pendu.VerificationCase(button.getText().toLowerCase().charAt(0), hashMap.get(button));
                    });
                    prop.getStyleClass().clear();
                    prop.getStyleClass().add("boxe-class");
                    prop.setId("proposition");
                    propositions.addAnimatedNode(prop);
                }
                vBox.getChildren().add(propositions);
                vBox.setAlignment(Pos.CENTER);
                propositions.setSpacing(10);
                boxesContainer.getChildren().add(vBox);
            }
        }
        boxesContainer.setSpacing(20);
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
            dialog.show(stackPane);
        } catch(IOException e) {
            e.printStackTrace();
        }
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
            mot.addObserver(this);
            indecationValueLable.setText(mot.getIndication().getValeur());
            indecationTypeLabel.setText(mot.getIndication().toString());
            genererCases();
        }else if(o instanceof Pendu) {
            showDialogBox("Fin de session","La session est terminé");
        }else if(o instanceof Mot){
            if((boolean)arg){
                showDialogBox("BRAVO !","Mot terminé avec succes");
            }else if(!(boolean)arg){
                showDialogBox("DOMMAGE !","Le mot correct est : "+((Mot)o).getValeur());
            }
        }
    }
}
