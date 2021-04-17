/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hytaco;

import Entites.Utilisateur;
import Services.ServiceUtilisateur;
import Services.UserSession;
import animatefx.animation.SlideInRight;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField password_lo;
    @FXML
    private TextField email_lo;
    private Button login;
    @FXML
    private TextField nom_i;
    @FXML
    private TextField prenom_i;
    @FXML
    private TextField adresse_i;
    @FXML
    private TextField telephone_i;
    @FXML
    private TextField email_i;
    @FXML
    private PasswordField password_i;
    @FXML
    private Button sign_up_l;
    @FXML
    private Button sign_in_l;
    @FXML
    private Button sign_up_s;
    @FXML
    private Button sign_in_s;
    @FXML
    private TextField tf_image;
    @FXML
    private Pane pane_signup;
    @FXML
    private Pane pane_signin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }    

    private void LoginUtilisateur(ActionEvent event) throws IOException {
         ServiceUtilisateur us = new ServiceUtilisateur();

            List<Utilisateur> lu =   us.getTtUtilisateur();
          
            lu.forEach(e -> {
            if (e.getEmail().equals(email_lo.getText()) &&  (us.testMotDePasse(password_lo.getText(), e.getPassword()))){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login");
                    alert.setContentText("Succes");
                    alert.showAndWait();
                    
                    UserSession.setInstace(e);
                                    try {
                                        Iterator iter = e.getRoles().iterator();
                                        Parent root = null ;
                                        if(iter.next().toString().equals("ROLE_ADMIN")){
                    root = FXMLLoader.load(getClass().getResource("AfficherUtilisateurs.fxml"));
                                        }else {
                      root = FXMLLoader.load(getClass().getResource("Panier.fxml"));
                                        }
                    Stage window = (Stage) login.getScene().getWindow();
                    window.setScene(new Scene(root,900,600));
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            });
                       
}
    private void InscriptionUtilisateur(ActionEvent event) {
                 ServiceUtilisateur us = new ServiceUtilisateur();
                 Utilisateur user = new Utilisateur(nom_i.getText(),prenom_i.getText(), Integer.parseInt(telephone_i.getText())
                         ,adresse_i.getText(),password_i.getText(),email_i.getText());
                 if(us.Inscription(user)==1){
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("login");
                 alert.setContentText("Succes");
                 alert.showAndWait();
            }else {
                         Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("login");
                 alert.setContentText("Echec");
                 alert.show();}
                 
    }

    @FXML
    private void To_signin(ActionEvent event) {
        new SlideInRight(pane_signin).play();
        pane_signin.toFront();
        
    }

    @FXML
    private void To_signup(ActionEvent event) {
         new SlideInRight(pane_signup).play();
        pane_signup.toFront();
    }
    
}