/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.Transporteur;
import projet.service.TransporteurServices;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
public class TansbackController implements Initializable {

  @FXML
    private TextField txttype;
    @FXML
    private Button btnajout;
    @FXML
    private TableView<Transporteur> tv1;
    @FXML
    private TableColumn<Transporteur, String> coltype;
    @FXML
     private TableColumn<Transporteur, Integer> colnumero;
    @FXML
    private TableColumn<Transporteur, String> colnom;
    @FXML
    private TableColumn<Transporteur, String> coladresse;
    @FXML
    private TableColumn<Transporteur, String> colmail;
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnmod;
    private TextField txtdescprition;
    private TextField txtemail;
    @FXML
    private TextField txtnumero;
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtmail;
    @FXML
    private TextField txtadresse;
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
        TransporteurServices ps = new TransporteurServices();
        ArrayList<Transporteur> publiciteList =  (ArrayList<Transporteur>) ps.getTransporteur() ;
    ObservableList<Transporteur> data = FXCollections.observableArrayList(publiciteList); 
    tv1.setItems(data);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colnumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
    }    

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void supprimerCommande(ActionEvent event) {
    }

    @FXML
    private void addtransporteur(ActionEvent event) {
        if(txttype.getText().isEmpty()||txtnumero.getText().isEmpty()||txtnom.getText().isEmpty()||txtadresse.getText().isEmpty()||txtmail.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "verifer les champs");  
        }
        else
        {
        String type = txttype.getText();
        int numero = Integer.parseInt(txtnumero.getText());
         String nom = txtnom.getText();
         String adresse = txtadresse.getText();
         String mail = txtmail.getText();
        Transporteur t = new Transporteur(5,type,numero,nom,adresse,mail);
        TransporteurServices sc = new TransporteurServices();
        sc.ajouterTransporteur(t);
                    JOptionPane.showMessageDialog(null, "ajout avec succes");
        txttype.clear();
        txtnumero.clear();
        txtnom.clear();
        txtadresse.clear();
        txtmail.clear();    
       
         ArrayList<Transporteur> publiciteList =  (ArrayList<Transporteur>) sc.getTransporteur() ; 
    ObservableList<Transporteur> data = FXCollections.observableArrayList(publiciteList);
    
    tv1.setItems(data);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colnumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
         }
    }

    @FXML
    private void deletetransporteur(ActionEvent event) {
         Transporteur selectedItem = tv1.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Etes vous sure de supprimer cette element ?");   
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        TransporteurServices ps = new TransporteurServices() ; 
        tv1.getItems().remove(selectedItem);
        ps.supprimerTransporteur(selectedItem);
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
    private void modifiertrans(ActionEvent event) {
        Transporteur cl=tv1.getSelectionModel().getSelectedItem();
          int id =cl.getId();
          
         String type = txttype.getText();
         int numero = Integer.parseInt(txtnumero.getText());
         String nom = txtnom.getText();
         String adresse = txtadresse.getText();
         String mail = txtmail.getText();
           
            TransporteurServices sc = new TransporteurServices();
             Transporteur t = new  Transporteur(id,type,numero,nom,adresse,mail);
           
            sc.modifiertransporteur(t);
            ArrayList<Transporteur> publiciteList =  (ArrayList<Transporteur>) sc.getTransporteur() ;
    ObservableList<Transporteur> donnee = FXCollections.observableArrayList(publiciteList); 
    tv1.setItems(donnee);
        //colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colnumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    @FXML
    private void loadscreen(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/reclamback.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void load(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/transporteurfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void produits(ActionEvent event) {
    }

    @FXML
    private void categorie(ActionEvent event) {
    }
    
}
