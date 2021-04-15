/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hytaco;

import Entites.Commande;
import Entites.Produit;
import Entites.Utilisateur;
import Services.ServiceCommande;
import Services.ServiceUtilisateur;
import Services.UserSession;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author user
 */
public class PanierController implements Initializable {

    @FXML
    private VBox content;
    @FXML
    private Label total;
    @FXML
    private Button commander;
    private ObservableMap<Produit,Integer> observableMap;
    @FXML
    private Button modifier_i;
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
    private ImageView image;
    @FXML
    private Label e_mail;
    @FXML
    private Label e_telephone;
    @FXML
    private Label e_nom;
    @FXML
    private Label e_prenom;
    @FXML
    private Button importer_i;
    @FXML
    private TextField tf_image;
    /**
     * Initializes the controller class.
     */
    public void setUser(){
         nom_i.setText(UserSession.getInstace().getUtilisateur().getNom());
        prenom_i.setText(UserSession.getInstace().getUtilisateur().getPrenom());
        adresse_i.setText(UserSession.getInstace().getUtilisateur().getAdresse());
        telephone_i.setText(String.valueOf(UserSession.getInstace().getUtilisateur().getTelephone()));
        email_i.setText(UserSession.getInstace().getUtilisateur().getEmail());

        if (UserSession.getInstace().getUtilisateur().getImage_name()!=null){
File file = new File ("C:\\Users\\user\\Documents\\JAVA\\Hytaco\\src\\image\\"+UserSession.getInstace().getUtilisateur().getImage_name());
Image imageForFile = null;
                   try {
                       System.out.println(file.toURI().toURL());
                       imageForFile = new Image(file.toURI().toURL().toExternalForm());
                   } catch (MalformedURLException ex) {
                       Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                   }
            image.setImage(imageForFile);
        }
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
              setUser();
        UserSession.getInstace().AddLignePanier(new Produit(1,"Tent",89.0,"erer",1222));
        
UserSession.getInstace().AddLignePanier(new Produit(2,"Sleeping Bag",89.7,"ezer",1222));
 setPanier();
       
    }
       private void setPanier(){
           double totalp =0;
        if(UserSession.getInstace().getPanier() != null){
            observableMap = FXCollections.observableMap(UserSession.getInstace().getPanier());
            content.getChildren().clear();
            for (Produit i : observableMap.keySet()) {                         
                    Produit produit = i ;
                    Button libelle = new Button(produit.getNom());
                    libelle.setOnAction(event -> {
                  
                    });
                 

                    Label prix = new Label(String.format("%.2f",produit.getPrix())+" DNT");

                    VBox detail = new VBox();
                    detail.getStyleClass().add("detail_prod");
                    detail.getChildren().addAll(libelle,prix);

                    
                    TextField qte = new TextField();
                    qte.setText(String.valueOf(observableMap.get(i)));

  qte.setOnKeyTyped(event-> {
                         String ch = event.getCharacter();
                         char c = ch.charAt(0);

                         if(!Character.isDigit(c)){
                           event.consume();
                         }
                         qte.setOnMouseExited(e -> {
                             
                             if(!qte.getText().equals("") && Integer.valueOf(qte.getText()) > 0){
                                 quantiteEvent(i,Integer.parseInt(qte.getText()));
                             }else {
                              qte.setText(String.valueOf(i.getQuantite()));
  
                             }
                             
                         });
                    });                    
                    double tot_prod = produit.getPrix() * Integer.valueOf(qte.getText());
                    
                    Label total_prod = new Label(String.format("%.2f",tot_prod)+" DNT");
                    total_prod.getStyleClass().add("total_prix");
                    HBox item = new HBox();
                    item.getStyleClass().add("item_prod_panier");
                    item.getChildren().addAll(detail,qte,total_prod);
                    content.getChildren().add(item);
                    totalp+=observableMap.get(i)*i.getPrix();
                
            }
        
            if(observableMap.size() == 0) {
                Label no_items = new Label("Il n'y a plus d'articles dans votre panier");
                no_items.setStyle("-fx-font-size: 18");
                content.getChildren().add(no_items);
                commander.setDisable(true);
            }
            
           total.setText(String.format("%.2f",totalp)+" DNT");

        } else {
            Label no_items = new Label("Il n'y a plus d'articles dans votre panier");
            no_items.setStyle("-fx-font-size: 18");
            content.getChildren().add(no_items);
            commander.setDisable(true);
            total.setText("7.00 DNT"); 
        }
       }

   private void quantiteEvent(Produit produit,Integer qt){
       observableMap.put(produit, qt);
       setPanier();
       UserSession.getInstace().SetLignePanier(produit, qt);
    }
   
   @FXML
    private void Commande(ActionEvent event){
    Date date = new Date();
    Boolean state = true ;
    ServiceCommande sc = new ServiceCommande();
    for (Produit i : observableMap.keySet()) {
    Commande commande = new Commande(date,UserSession.getInstace().getUtilisateur().getId(),
          i.getId(),observableMap.get(i),i.getPrix()*observableMap.get(i));
     if (sc.AjouterCommande(commande)!=1){
        state = false ;
   }
    }
    if (state){
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login");
                    alert.setContentText("Les commandes sont ajouté avec succes");
                    alert.showAndWait();
    }
    
    }

    @FXML
    private void modifierUtilisateur(ActionEvent event) {
                if ( e_nom.getText().equals("")&& e_prenom.getText().equals("")&& e_telephone.getText().equals("")&& e_mail.getText().equals("") )
                    {
                       
        ServiceUtilisateur us = new ServiceUtilisateur();
      int state= 0; 
     state = us.ModifierUtilisateur(new Utilisateur(UserSession.getInstace().getUtilisateur().getId(),nom_i.getText(),prenom_i.getText(),Integer.parseInt(telephone_i.getText())
               ,adresse_i.getText(),email_i.getText(),tf_image.getText()));
          if (state==1){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login");
                    alert.setContentText("Vos infos sont modifié avec succes");
                    alert.showAndWait();
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
    
}
