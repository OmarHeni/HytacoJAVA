/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.Reclamations;
import projet.service.ReclamationsServices;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Med Amine
 */
public class ReclamationfrontController implements Initializable {

    @FXML
    private TextField txttype;
    @FXML
    private Button btnajout;
    private TableView<Reclamations> tv;
    private TableColumn<Reclamations, Integer> colid;
    private TableColumn<Reclamations, String> coltype;
    private TableColumn<Reclamations, String> coldescription;
    private TableColumn<Reclamations, String> colemail;
    @FXML
    private TextField txtdescprition;
    @FXML
    private TextField txtemail;
    @FXML
    private AnchorPane brand;
    @FXML
    private Button closeb;
    @FXML
    private Label Reclamations1;
    @FXML
    private Label Alerts1;
    @FXML
    private Label Programmes1;
    @FXML
    private Label Categories1;
    @FXML
    private Label Evenements1;
    @FXML
    private Label Locaux1;
    @FXML
    private Label Panier1;
    @FXML
    private Label Deconnexion1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnajout(ActionEvent event) {
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
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
         colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
         }
    }

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void loadscreen(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/transporteurfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void load(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/reclamback.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
