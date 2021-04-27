/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.Reclamations;
import projet.service.ReclamationsServices;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Med Amine
 */
public class ReclambackController implements Initializable {
  @FXML
    private TextField txttype;
    @FXML
    private Button btnajout;
    @FXML
    private TableView<Reclamations> tv;
    @FXML
    private TableColumn<Reclamations, String> coltype;
    @FXML
    private TableColumn<Reclamations, String> coldescription;
    @FXML
    private TableColumn<Reclamations, String> colemail;
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnmod;
    @FXML
    private TextField txtdescprition;
    @FXML
    private TextField txtemail;
    @FXML
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Label nom_u;
    @FXML
    private FontAwesomeIcon arrow;
    @FXML
    private Label test;
    @FXML
    private TableView<?> commande;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> produit;
    @FXML
    private TableColumn<?, ?> client;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> quantite;
    @FXML
    private TableColumn<?, ?> prix;
    @FXML
    private Button supprimercom;
    @FXML
    private TextField search;
    @FXML
    private Label e_nom;
    @FXML
    private Label e_mail;
    @FXML
    private Label e_password;
    @FXML
    private Label e_telephone;
    @FXML
    private Label e_prenom;
    @FXML
    private TextField searchu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ReclamationsServices ps = new ReclamationsServices();
        ArrayList<Reclamations> publiciteList =  (ArrayList<Reclamations>) ps.getReclamations() ;
    ObservableList<Reclamations> data = FXCollections.observableArrayList(publiciteList); 
    tv.setItems(data);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }    

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void supprimerCommande(ActionEvent event) {
    }

    @FXML
    private void addreclamations(ActionEvent event) {
        if(txttype.getText().isEmpty()||txtdescprition.getText().isEmpty()||txtemail.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "verifer les champs");  
        }
        else
        {
        String type = txttype.getText();
         String description = txtdescprition.getText();
         String email = txtemail.getText();
        Reclamations c = new Reclamations(5,type,description,email);
        ReclamationsServices sc = new ReclamationsServices();
        sc.ajouterReclamations(c);
                    JOptionPane.showMessageDialog(null, "ajout avec succes");
        txttype.clear();
        txtdescprition.clear();
        txtemail.clear();
       
         ArrayList<Reclamations> publiciteList =  (ArrayList<Reclamations>) sc.getReclamations() ; 
    ObservableList<Reclamations> data = FXCollections.observableArrayList(publiciteList);
    
    tv.setItems(data);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
         colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
         }
    }

    @FXML
    private void deletereclamations(ActionEvent event) {
        Reclamations selectedItem = tv.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Etes vous sure de supprimer cette element ?");   
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        ReclamationsServices ps = new ReclamationsServices() ; 
        tv.getItems().remove(selectedItem);
        ps.supprimerReclamations(selectedItem);
        }
        
        }
        else {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un element à supprimer.");

        alert.showAndWait();
        }
    }

    @FXML
    private void modofierreclam(ActionEvent event) {
        Reclamations r=tv.getSelectionModel().getSelectedItem();
          int id =r.getId();
          
         String type = txttype.getText();
         String description = txtdescprition.getText();
         String email = txtemail.getText();
           
            ReclamationsServices rm = new ReclamationsServices();
             Reclamations rl = new  Reclamations(id,type,description,email);
           
            rm.modifierreclamations(rl);
            ArrayList<Reclamations> publiciteList =  (ArrayList<Reclamations>) rm.getReclamations() ;
    ObservableList<Reclamations> donnee = FXCollections.observableArrayList(publiciteList); 
    tv.setItems(donnee);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    private void loadscreen(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/tansback.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void load(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/reclamationfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
