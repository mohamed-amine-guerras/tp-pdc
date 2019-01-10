package controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static controller.MainApp.*;

/**
 * Created by hamza on 08/05/2017.
 */
public class SessionViewController  implements Observer,Initializable {

    private MainApp manApp;
    private Mot mot;
    private Pendu pendu = Pendu.getInstance();
    private GridPane gridPane1;
    private String[] imagePathes = {"resources/img/1.png","resources/img/2.png","resources/img/3.png",
            "resources/img/4.png","resources/img/5.png","resources/img/6.png","resources/img/7.png"};
    private int image = 0;

    public void setGridPane1(GridPane gridPane1) {
        this.gridPane1 = gridPane1;
    }



    @FXML
    private JFXButton previousButton;

    @FXML
    private ImageView imageView;
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

    @FXML
    private JFXButton homeButton;

    @FXML
    private Label highScoreLabel;

    private ArrayList<JFXDialog> dialogs = new ArrayList<>();





    @FXML
    void onHomeButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Home));
        try {
            Parent parent = loader.load();
            ((HomeController)loader.getController()).setGridPane(gridPane1);
            gridPane1.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPreviousButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(NEW_SESSION));
        try {
            Parent parent = loader.load();
            ((UserLoginController)loader.getController()).setGridPane(gridPane1);
            gridPane1.add(parent,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * Initialiser la scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(SESSION_VIEW));
        boxesContainer.getStylesheets().add("resources/fxml/boxesStyles.css"); // Charger le view
        pendu = Pendu.getInstance();// Utiliser l'effet de bord sur l'objet Pendu
        pendu.StartSession();
        showDialogBox("Bienvenue","Bienvenue dans le jeu "+pendu.getPlayerName());
        pendu.addObserver(this);//Ajouter ce controlleur comme observateur sur l'objet pendu affin d'etre notifié de la fin de la séance
        pendu.addPlayerObserver(this);//Ajouter ce controlleur comme observateur sur l'objet player affin d'etre notifié du changement du score
        pendu.addSessionObserver(this); // Ajouter un observateur sur l'objet seesion afin d'être notifié sur l'état du mot actuel
        pseudonymeLabel.setText(pendu.getPlayerName());
        scoreLabel.setText("0");
        highScoreLabel.setText(String.valueOf(pendu.getPlayerMeilleureScore()));
        updateWord();// On initailise le mot
    }


    /*
    *Générer le view des case ainsi que les actions effectuer aux cliques
     */
    private void genererCases(){
        int i =0;
        HashMap<Node,Integer> hashMap = new HashMap<>(); //  On met les cases dans une HashMap
        boxesContainer.getChildren().clear();// On vide le conteneur des cases
        for(Case box : this.mot.getEnsemblesCases()){
            JFXButton button = new JFXButton(); // On simule les cases par des bouttons
            hashMap.put(button,i);
            i++;
            button.getStyleClass().add("boxe-class");
            button.getStyleClass().removeAll("jfx-button"); //  Le view de boutton
            button.setButtonType(JFXButton.ButtonType.RAISED);

            if(box instanceof ZeroChance){
                button.setId("zero-chance");
                EventHandler<MouseEvent> mouseEvent = me->{ //On définie un EventHandler sur un MouseEvent
                    button.setId("zero-chance");// Actualiser le view
                };

                EventHandler<KeyEvent> keyEvent = ke->{ // On définie un EventHandler sur un KeyEvent
                    KeyCode kc = ke.getCode(); // On récupère le carractère tappé par l'utilisateur
                    button.setText(String.valueOf(kc)); // On l'écrit sur le boutton
                    button.setId("zero-chance");// on actualise le view
                    button.setDisable(true); // On désactive le boutton
                    pendu.VerificationCase(String.valueOf(kc).toLowerCase().charAt(0), hashMap.get(button)); // On verrifie le carractère
                };

                button.setOnAction(e->{
                    button.setId("on-action");//  Actualiser le view
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent); // On ajoute un EventFilter sur l'appuie d'un boutton sur le calavier affin d'activer l"EventHandler  définie
                    stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent); // Pour actualiser le view en cas de click sur un espace vide
                });


                button.removeEventFilter(KeyEvent.KEY_RELEASED,keyEvent);// On enleve l'EventFilter

                boxesContainer.getChildren().add(button); //  On ajoute ke boutton au conteneur des bouttons


            }else if(box instanceof MultiChance){// Voir ZeroChance
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
                        if(box.isSuceces()) button.setDisable(true);//  On ne désative le boutton que si le joueur réussit
                        else showDialogBox("Tentatives","Il vous reste "+((MultiChance)box).getNbTentativesRestant()+" tentatives");// Sinon on lui affiche le nombre de tentatives restantes
                    }
                };
                button.setOnAction(e->{// Voir ZeroChance
                    button.setId("on-action");
                    button.addEventFilter(KeyEvent.KEY_RELEASED,keyEvent);
                    stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent);
                });
                button.removeEventFilter(KeyEvent.KEY_RELEASED,keyEvent);

                boxesContainer.getChildren().add(button);
            }


            else {
                VBox vBox = new VBox();// Destiné à contenir les propositons
                vBox.getStyleClass().clear();
                vBox.getStylesheets().add("resources/fxml/boxesStyles.css");
                button.setId("proposition");
                JFXNodesList propositions = new JFXNodesList(); // On met les proposirions dans une NodeList
                propositions.getStylesheets().add("resources/fxml/boxesStyles.css");
                propositions.addAnimatedNode(button);// On ajoute le boutton principale au NodeList

                button.setOnAction(e->{// Au click on tourne l'animation du NodeList
                    propositions.animateList();
                });
                for(Character c : ((Proposition)box).getProposition()){ // Pour chaque proposition
                    JFXButton prop = new JFXButton(String.valueOf(c).toUpperCase()); //On affiche sur le boutton de proposition la propostion

                    prop.setOnAction(e->{// Voir ZeroChance
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
                vBox.getChildren().add(propositions); // On ajoute le boutton de proposition au NodeList
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
        if (o instanceof Player) {// A la notification d'un Player
            scoreLabel.setText(String.valueOf((int)arg)); // On actualise le score
        } else if (o instanceof Session){ // A la notification d'une Session
            if(mot.isCorrect()){ // On affiche l'état du mot avec correction eventuelle
                showDialogBox("BRAVO !","Mot terminé avec succes");
            }else{
                showDialogBox("DOMMAGE !","Le mot correct est : "+mot.getValeur()+
                        "\nil vous reste "+((pendu.getNombreEchecsActuel() < 5)
                        ? (6-pendu.getNombreEchecsActuel())+" tentatives" : "une seule tentative"));
                draw();// On decine le pendue
            }
            updateWord();// On met à  jours le mot
        }else if(o instanceof Pendu) { // A la notification d'un Objet Pendu
            FXMLLoader loader = new FXMLLoader(); // On termine la session
            loader.setLocation(getClass().getResource(END_SESSION));
            try {
                Parent parent = loader.load();
                ((EndSessionController)loader.getController()).setGridPane(gridPane1);
                ((EndSessionController)loader.getController()).setImageView(((image > 0) ? imagePathes[image-1] : imagePathes[6]));
                ((EndSessionController)loader.getController()).setScore(scoreLabel.getText());
                gridPane1.add(parent,0,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dessiner le pendu
     */
    private void draw(){
        Image image = new Image(imagePathes[this.image]);
        this.image++; // On affiche l'image d'indice @field image
        imageView.setImage(image);
    }

    private void updateWord(){
        mot = pendu.getMotActuel();
        indecationValueLable.setText(mot.getIndication().getValeur());
        indecationTypeLabel.setText(mot.getIndication().toString());
        genererCases();

    }
}
