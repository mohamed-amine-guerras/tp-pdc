package gui;

import com.company.model.Pendu;
import com.company.model.Player;
import com.company.model.Session;
import com.company.model.mots.Mot;
import com.company.model.mots.cases.Case;
import com.company.model.mots.cases.MultiChance;
import com.company.model.mots.cases.Proposition;
import com.company.model.mots.cases.ZeroChance;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static gui.HomeController.getPendu;
import static gui.MainApp.CONFIRMATION_DIALOG_BOX;
import static gui.MainApp.NEW_SESSION;
import static gui.MainApp.SESSION_VIEW;

/**
 * Created by hamza on 08/05/2017.
 */
public class SessionViewController  implements Controller,Observer,Initializable {

    private MainApp manApp;
    private Mot mot;
    private Pendu pendu;
    private Session session;
    private GridPane gridPane1;

    public void setGridPane1(GridPane gridPane1) {
        this.gridPane1 = gridPane1;
    }

    @FXML
    private JFXButton previousButton;

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

    @FXML AnchorPane anchorPane;
    private ArrayList<JFXDialog> dialogs = new ArrayList<>();

    public void setPendu(Pendu pendu) {
        this.pendu = pendu;
    }


    @FXML
    void onPreviousButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(NEW_SESSION));
        try {
            Parent parent = loader.load();
            ((Controller) loader.getController()).setPendu(pendu);
            ((UserLoginController)loader.getController()).setGridPane(gridPane1);
            gridPane1.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(SESSION_VIEW));
        boxesContainer.getStylesheets().add("resources/fxml/boxesStyles.css");
        pendu = getPendu();
        session = pendu.getSessionActuel();
        pendu.addObserver(this);
        session.getPlayer().addObserver(this);
        ((Observable)session).addObserver(this);
        pseudonymeLabel.setText(session.getPlayer().getPseudonyme());
        scoreLabel.setText("0");
        updateWord();
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
                EventHandler<MouseEvent> mouseEvent = me->{
                    if(me.getClickCount() >= 1) button.setId("zero-chance");
                };

                EventHandler<KeyEvent> keyEvent = ke->{
                    KeyCode kc = ke.getCode();
                    button.setText(String.valueOf(kc));
                    button.setId("zero-chance");
                    button.setDisable(true);
                    pendu.VerificationCase(String.valueOf(kc).toLowerCase().charAt(0), hashMap.get(button));
                };

                button.setOnAction(e->{
                    button.setId("on-action");
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                    stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent);
                });


                button.removeEventFilter(KeyEvent.KEY_RELEASED,keyEvent);

                boxesContainer.getChildren().add(button);


            }else if(box instanceof MultiChance){
                EventHandler<MouseEvent> mouseEvent = me->{
                if(me.getClickCount() >= 1) button.setId("multi-chance");
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
                vBox.getStyleClass().clear();
                vBox.getStylesheets().add("resources/fxml/boxesStyles.css");
                button.setId("proposition");
                JFXNodesList propositions = new JFXNodesList();
                propositions.getStylesheets().add("resources/fxml/boxesStyles.css");
                propositions.addAnimatedNode(button);

                button.setOnAction(e->{
                    button.setId("on-action");
                    propositions.animateList();
                });
                for(Character c : ((Proposition)box).getProposition()){
                    JFXButton prop = new JFXButton(String.valueOf(c).toUpperCase());

                    prop.setOnAction(e->{
                        button.setText(prop.getText());
                        button.setId("proposition");
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
                propositions.setSpacing(6);
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
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            scoreLabel.setText(String.valueOf((int)arg));
        } else if (o instanceof Session){
            updateWord();
        }else if(o instanceof Pendu) {
            showDialogBox("Fin de session","La session est terminé");
            System.out.println(((Pendu) o).isSessionTerminee());
        }else if(o instanceof Mot){
            if((boolean)arg){
                showDialogBox("BRAVO !","Mot terminé avec succes");
            }else if(!(boolean)arg){
                showDialogBox("DOMMAGE !","Le mot correct est : "+((Mot)o).getValeur());
            }
        }
    }

    private void updateWord(){
        mot = session.getMotActuel();
        mot.addObserver(this);
        indecationValueLable.setText(mot.getIndication().getValeur());
        indecationTypeLabel.setText(mot.getIndication().toString());
        genererCases();

    }
}
