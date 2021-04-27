/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entites.Admin;
import Entites.Client;
import Entites.Utilisateur;
import Services.ServiceUtilisateur;
import Services.UserSession;
import animatefx.animation.BounceInDown;
import animatefx.animation.FadeOutUp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AfficherUtilisateursController implements Initializable {
    @FXML
    private TableView<Utilisateur> utilisateurs;
    @FXML
    private TableColumn<Utilisateur, String> nom;
    @FXML
    private TableColumn<Utilisateur, String> prenom;
    @FXML
    private TableColumn<Utilisateur, String> mail;
    @FXML
    private TableColumn<Utilisateur, String> adresse;
    @FXML
    private TableColumn<Utilisateur, Integer> telephone;
    @FXML
    private TableColumn<Utilisateur, String> roles;
    
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
            ObservableList<Utilisateur> observableList = FXCollections.observableArrayList();

    @FXML
    private TextField searchu;
    @FXML
    private TextField tf_image;
    @FXML
    private Label e_mail;
    @FXML
    private Label e_telephone;
    @FXML
    private Label e_password;
    @FXML
    private Label e_nom;
    @FXML
    private Label e_prenom;
    @FXML
    private Button supprimer_u;
    @FXML
    private PasswordField confirm_i;
    @FXML
    private Button importb;
    @FXML
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;
    @FXML
    private Button inscription;
    @FXML
    private ComboBox<String> roles_i;
    @FXML
    private Button to_commande;
    @FXML
    private Button ajouteru;
    @FXML
    private Pane blacks;
    @FXML
    private Pane form_i;

    /**
     * Initializes the controller class.
     */

    public void setImage() {
        File file = new File (System.getProperty("user.dir") +"\\src\\image\\"+UserSession.getInstace().getUtilisateur().getImage_name());
 
        try {
            circle.setFill(new ImagePattern(new Image(file.toURI().toURL().toExternalForm())));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void initializeUtilisateurs(){
roles_i.setItems(FXCollections.observableArrayList("Admin", "Client", "Fournissuer"));
roles_i.setValue("Client");
        nom_u.setText(UserSession.getInstace().getUtilisateur().getPrenom()+" "+UserSession.getInstace().getUtilisateur().getNom());
        setImage();
      
        observableList.clear();
        ServiceUtilisateur us = new ServiceUtilisateur();
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            mail.setCellValueFactory(new PropertyValueFactory<>("email"));
            adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            roles.setCellValueFactory(new PropertyValueFactory<>("role"));

            List<Utilisateur> lu = null;
        try {
            lu = us.getTtUtilisateur();
        } catch (IOException ex) {
            Logger.getLogger(AfficherUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
            lu.forEach(e -> {
                observableList.add(e);
            });
            utilisateurs.setItems(observableList);
                        FilteredList<Utilisateur> filteredData = new FilteredList<>(observableList,b-> true);
          searchu.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((Utilisateur c)-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
                                System.out.println(lowerCaseFilter);
                                System.out.println(c.getNom());
				if (c.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (c.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (c.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                                else if (c.getAdresse().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				     else  
				    	 return false; // Does not match.
			});

		});
		SortedList<Utilisateur> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(utilisateurs.comparatorProperty());
		utilisateurs.setItems(sortedData);
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
             password_i.textProperty().addListener((observable, oldValue, newValue) -> {
               if (newValue.length()>=5){
                   e_password.setText("");
               }else {
e_password.setText("Votre mot de passe est invalid");
               }

                });
             
    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        initializeUtilisateurs();

    }   
    
    @FXML
    private void InscriptionUtilisateur(ActionEvent event) {
        Utilisateur user = null ;
        if (e_password.getText().equals("")&& e_nom.getText().equals("")&& e_prenom.getText().equals("")&& e_telephone.getText().equals("")&& e_mail.getText().equals("") ){
                 ServiceUtilisateur us = new ServiceUtilisateur();
                 if (roles_i.getValue().equals("Admin")){
                  user = new Admin(nom_i.getText(),prenom_i.getText(), Integer.parseInt(telephone_i.getText())
                         ,adresse_i.getText(),password_i.getText(),email_i.getText(),tf_image.getText(),UUID.randomUUID().toString());
                 }else if (roles_i.getValue().equals("Fournisseur")){
                     user = new Admin(nom_i.getText(),prenom_i.getText(), Integer.parseInt(telephone_i.getText())
                         ,adresse_i.getText(),password_i.getText(),email_i.getText(),tf_image.getText(),UUID.randomUUID().toString()); 
                 }else if (roles_i.getValue().equals("Client")) {
                       user = new Client(nom_i.getText(),prenom_i.getText(), Integer.parseInt(telephone_i.getText())
                         ,adresse_i.getText(),password_i.getText(),email_i.getText(),tf_image.getText(),UUID.randomUUID().toString());
                 }
                 if(us.Inscription(user)==1){
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Inscription");
                 alert.setContentText("Succes");
                 alert.showAndWait();
                 initializeUtilisateurs();
            }else {
                         Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Inscription");
                 alert.setContentText("Echec");
                 alert.show();
                 }           
        }else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Inscription");
                 alert.setContentText("Veuillez voir vos parametres");
                 alert.show();
        }
        blacks.toBack();
        new FadeOutUp(form_i).play();
        
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
    private void SupprimerUtilisateur(ActionEvent event) {
          Utilisateur comSelect = utilisateurs.getSelectionModel().getSelectedItem();
        if (comSelect == null) {
           
            return;
        }
        ServiceUtilisateur us = new ServiceUtilisateur();

        us.SupprimerUtilisateur(comSelect);
        //pour refraichir le tableView
        observableList.remove(comSelect);
        initializeUtilisateurs();
    }

    @FXML
    private void Close(ActionEvent event) {
                Stage window = (Stage) closeb.getScene().getWindow();
        window.close();
    }



    @FXML
    private void To_Commande(ActionEvent event) {
           Parent   root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/AfficherCommandes.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileBController.class.getName()).log(Level.SEVERE, null, ex);
        }                            
                    Stage window = (Stage) to_commande.getScene().getWindow();
                    window.setScene(new Scene(root));
    }

    @FXML
    private void To_ProfileB(ActionEvent event) {
          Parent   root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/ProfileB.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileBController.class.getName()).log(Level.SEVERE, null, ex);
        }                            
                    Stage window = (Stage) nom_u.getScene().getWindow();
                    window.setScene(new Scene(root));
    }

    @FXML
    private void InForm(ActionEvent event) {
        blacks.toFront();
        new BounceInDown(form_i).play();
        form_i.toFront();
        
    }
}
