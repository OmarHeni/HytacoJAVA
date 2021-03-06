/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entites.Client;
import Services.ServiceUtilisateur;
import Services.UserSession;
import Controller.AfficherUtilisateursController;
import Controller.PanierController;
import animatefx.animation.SlideInDown;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author Hassene
 */
public class ProfileController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private Label e_nom;
    @FXML
    private TextField nom_i;
    @FXML
    private Label e_mail;
    @FXML
    private TextField email_i;
    @FXML
    private Label e_telephone;
    @FXML
    private TextField telephone_i;
    @FXML
    private Label e_prenom;
    @FXML
    private TextField prenom_i;
    @FXML
    private TextField adresse_i;
    @FXML
    private TextField tf_image;
    @FXML
    private Button importb;
    @FXML
    private Label Nomc;
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
    private Button modifier;
    @FXML
    private Button to_profile;
    @FXML
    private Circle circle1;
    @FXML
    private Button nom_u;
    @FXML
    private Button to_menu;
    @FXML
    private Pane pane_m;
    @FXML
    private Button to_panier;

    /**
     * Initializes the controller class.
     */
     public void setImage(String name) {
        File file = new File (System.getProperty("user.dir") +"\\src\\image\\"+name);
        circle.setRadius(55);
        try {
           System.out.println(new Image(file.toURI().toURL().toExternalForm()));
            circle.setFill(new ImagePattern(new Image(file.toURI().toURL().toExternalForm())));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void setUser(){
         nom_i.setText(UserSession.getInstace().getUtilisateur().getNom());
        prenom_i.setText(UserSession.getInstace().getUtilisateur().getPrenom());
        adresse_i.setText(UserSession.getInstace().getUtilisateur().getAdresse());
        telephone_i.setText(String.valueOf(UserSession.getInstace().getUtilisateur().getTelephone()));
        email_i.setText(UserSession.getInstace().getUtilisateur().getEmail());
        tf_image.setText(UserSession.getInstace().getUtilisateur().getImage_name());
        if (UserSession.getInstace().getUtilisateur().getImage_name()!=null){
            setImage(UserSession.getInstace().getUtilisateur().getImage_name());
        }
        Nomc.setText(prenom_i.getText()+" "+nom_i.getText());
        email_i.textProperty().addListener((observable, oldValue, newValue) -> {
                String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                        + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
Pattern pattern = Pattern.compile(masque);
Matcher controler = pattern.matcher(newValue);
if (controler.matches()){
e_mail.setText("");
 
}else{
e_mail.setText("mail invalide ");
}
                });
             
             telephone_i.textProperty().addListener((observable, oldValue, newValue) -> {
                String masque = "[0-9]*";
Pattern pattern = Pattern.compile(masque);
Matcher controler = pattern.matcher(newValue);
if (controler.matches()){
    if (newValue.length()==8){
e_telephone.setText("");
    }
 
}else{
e_telephone.setText("le numero du telephone est invalid");
}
                });
             nom_i.textProperty().addListener((observable, oldValue, newValue) -> {
                String masque = "[a-zA-Z]*";
Pattern pattern = Pattern.compile(masque);
Matcher controler = pattern.matcher(newValue);
if (controler.matches()){
e_nom.setText(""); 
}else{
e_nom.setText("votre nom est invalid");
}
                });
             prenom_i.textProperty().addListener((observable, oldValue, newValue) -> {
                String masque = "[a-zA-Z]*";
Pattern pattern = Pattern.compile(masque);
Matcher controler = pattern.matcher(newValue);
if (controler.matches()){
e_prenom.setText("");
 
}else{
e_prenom.setText("votre prenom est invalid");
}
                });
            
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
setUser() ;
    }    

    @FXML
  private void modifierUtilisateur(ActionEvent event) {
                if ( e_nom.getText().equals("")&& e_prenom.getText().equals("")&& e_telephone.getText().equals("")&& e_mail.getText().equals("") )
                    {
                       
        ServiceUtilisateur us = new ServiceUtilisateur();
      int state= 0; 
      Client client = new Client(UserSession.getInstace().getUtilisateur().getId(),nom_i.getText(),prenom_i.getText(),Integer.parseInt(telephone_i.getText())
               ,adresse_i.getText(),email_i.getText(),tf_image.getText());
     state = us.ModifierUtilisateur(client);
          if (state==1){
              UserSession.setInstace(client);
              System.out.println(client);
              System.out.println(UserSession.getInstace().getUtilisateur());
            
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login");
                    alert.setContentText("Vos infos sont modifi?? avec succes");
                    alert.showAndWait();
                    setUser();
    }
                    }
                    else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Inscription");
                 alert.setContentText("Veuillez voir vos parametres");
                 alert.show();
                    }
     }

    @FXML
   private void importer(ActionEvent event) {
       String fileName = null ;
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
         fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", ".jpg", ".png"));
           //  e.setImage_event(SelectedFile.getAbsolutePath());
        if (SelectedFile != null) {

            String fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) {
                 fileName = fcs.substring(index + 1);
            }

        }
     String key =  UUID.randomUUID().toString();
        String fcs = SelectedFile.getAbsolutePath();
        File source = new File(fcs);
        File destination = new File(System.getProperty("user.dir") + "\\src\\image\\" +key+ fileName);
        String url =  destination.getAbsolutePath();
                        System.out.println(url);

        if (!destination.exists()) {
            try {
                FileUtils.copyFile(source, destination);
            } catch (Exception E) {

            }
               tf_image.setText(key+ fileName);
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
