/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entites.Commande;
import Entites.Livraison;
import Services.ServiceCommande;
import Services.UserSession;
import animatefx.animation.SlideInDown;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import service.ServiceLivraison;

/**
 * FXML Controller class
 *
 * @author Hassene
 */
public class CommandeController implements Initializable {

    @FXML
    private Label article;
    @FXML
    private Label subtotal;
    @FXML
    private Label total;
    @FXML
    private Button closeb;
    @FXML
    private Label Reclamations;
    @FXML
    private Label Alerts;
    @FXML
    private Label Programmes;
    @FXML
    private Label Categories;
    @FXML
    private Label Evenements;
    @FXML
    private Label Locaux;
    @FXML
    private Label Propositions;
    @FXML
    private Button to_panier;
    @FXML
    private Button to_profile;
    @FXML
    private Pane fpane;
    @FXML
    private Label article1;
    @FXML
    private Pane form1;
    @FXML
    private Button modifier;
    @FXML
    private Button modifier1;
    @FXML
    private Label e_nom;
    @FXML
    private TextField cardnum;
    @FXML
    private Label e_mail;
    @FXML
    private TextField exp_month;
    @FXML
    private Label e_telephone;
    @FXML
    private Label e_prenom;
    @FXML
    private TextField cvc;
    @FXML
    private TextField exp_year;
    @FXML
    private Label article11;
    @FXML
    private Pane form2;
    @FXML
    private Label e_nom1;
    @FXML
    private Label e_mail1;
    @FXML
    private Label e_telephone1;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox1;
    static  Double stotal;
    static  Commande com ; 
    @FXML
    private Button ajouterliv;
    @FXML
    private TextField tfADRESSE;
    @FXML
    private Label e_password;
    @FXML
    private DatePicker tfDATELIVRAISON;
    @FXML
    private Pane pane_m;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;
    @FXML
    private Button to_menu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                  Font.loadFont(getClass().getResourceAsStream("/css/RopaSans-Regular.ttf"), 14);
          Font.loadFont(getClass().getResourceAsStream("/css/SourceSansPro-Regular.ttf"), 14);
subtotal.setText(String.valueOf(new DecimalFormat("##.##").format(stotal))+ " TND");
            total.setText(String.valueOf(new DecimalFormat("##.##").format(stotal+10))+ " TND");   

    }    
  public void Payement(){
        Stripe.apiKey = "sk_test_51IXl9nAyyifkJ2GTw02VQPccPVPzbU7UW382UezlP4Npm0ajBpy9eJMhiFk3PHdfvO7Co06fR2dzmXlqMei3CqPC00ZksblkBB";
        Map<String,Object> Param = new HashMap<String,Object>();
        Param.put("number", cardnum.getText());
        Param.put("exp_month",exp_month.getText());
        Param.put("exp_year", exp_year.getText());
        Param.put("cvc", cvc.getText());
        Map<String,Object> TokenParam = new HashMap<String,Object>();
        TokenParam.put("card",Param);
        Token token=null;
            ServiceCommande sc = new ServiceCommande();
        try {
            token = Token.create(TokenParam);
        } catch (StripeException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String,Object> chargeParam = new HashMap<String,Object>();
        chargeParam.put("amount", Math.round((stotal+10)*0.3*100));
        chargeParam.put("currency", "EUR");
        chargeParam.put("source", token.getId());
        try {
        Charge a =   Charge.create(chargeParam);
        if(a.getPaid()){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Payement");
                    alert.setContentText("Le Payement est fait avec succes");
                    alert.showAndWait(); 
                    sc.ToPayer(com.getId());
                        form1.setVisible(false);
        vbox1.setPrefHeight(71);
        form2.setVisible(true);
        vbox2.setPrefHeight(239);
        }
        } catch (StripeException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        
    }
    @FXML
    private void Close(ActionEvent event) {
    }

 @FXML
    private void To_Panier(ActionEvent event) {
         Parent   root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/Panier.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileBController.class.getName()).log(Level.SEVERE, null, ex);
        }
                                        
                    Stage window = (Stage) to_profile.getScene().getWindow();
                    window.setScene(new Scene(root));

    }

    @FXML
    private void To_Profile(ActionEvent event) {
                Parent   root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/Profile.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileBController.class.getName()).log(Level.SEVERE, null, ex);
        }
                                        
                    Stage window = (Stage) to_profile.getScene().getWindow();
                    window.setScene(new Scene(root));

    }
    @FXML
    private void Finalizer(ActionEvent event) {
        Payement();
    }

    @FXML
    private void PayerALivraison(ActionEvent event) {
        form1.setVisible(false);
        vbox1.setPrefHeight(71);
        form2.setVisible(true);
        vbox2.setPrefHeight(239);
    }

    @FXML
    private void AjouterLivraison(ActionEvent event) {
 
         ServiceLivraison us = new ServiceLivraison();
         ServiceCommande cs = new ServiceCommande();
                 Livraison liv;
        liv = new Livraison(java.sql.Date.valueOf(tfDATELIVRAISON.getValue()),tfADRESSE.getText());
              liv.setId(us.Ajouter(liv));  
              if (liv.getId()>0){
                       System.out.println(com.getId() + " "+liv.getId());

                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Livraison");
                 alert.setContentText("Succes");
                 alert.showAndWait();
                cs.setLivraison(com.getId(),liv.getId());
            }else {
                         Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Livraison");
                 alert.setContentText("Echec");
                 alert.show();
                       
                 }
    }

 
    @FXML
    private void ToMenu(ActionEvent event) {
        if (!(pane_m.isVisible())){
             new SlideInDown(pane_m).play();
                 pane_m.setVisible(true);
        }else {
                 pane_m.setVisible(false);
        }
    }

    @FXML
    private void Deconnexion(ActionEvent event) {
          Parent   root=null;
          UserSession.setInstace(null);
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileBController.class.getName()).log(Level.SEVERE, null, ex);
        }
                                        
                    Stage window = (Stage) to_profile.getScene().getWindow();
                    window.setScene(new Scene(root));

    }
}
