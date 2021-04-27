/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import projet.models.Alerts;
import projet.models.produits;
import projet.service.AlertsService;
import projet.service.ProduitsService;
import projet.utils.SendEmail;
import static projet.utils.print.printNode;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Taher
 */
public class AlertsController implements Initializable {

    private TableColumn<Alerts, String> id;
    private TableColumn<Alerts, String> mail;
    @FXML
    private Button categories;
    @FXML
    private Button produits;
    @FXML
    private Button publicite;
    @FXML
    private Button print;
    @FXML
    private Button closep;
    @FXML
    private TextField recherchealert;
    @FXML
    private Button delete;
    @FXML
    private TableView<Alerts> affichealert;
    @FXML
    private TableColumn<Alerts, String> localisations;
    @FXML
    private TableColumn<Alerts, String> rapports;
    @FXML
    private TableColumn<Alerts, String> mails;
    private TableColumn<Alerts, Integer> programmes;
    @FXML
    private TableColumn<Alerts, Date> dates;
    @FXML
    private TableColumn<Alerts, Integer> telephones;
    @FXML
    private AnchorPane AnO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    AlertsService ps = new AlertsService();
    ArrayList<Alerts> publiciteList =  (ArrayList<Alerts>) ps.afficherAlerts() ;
    ObservableList<Alerts> data = FXCollections.observableArrayList(publiciteList); 
    affichealert.setItems(data);
        localisations.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
        rapports.setCellValueFactory(new PropertyValueFactory<>("rapport"));
        telephones.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        mails.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
    }    
    
    
  
    @FXML
    private void delete(ActionEvent event) {
        

          Alerts selectedItem = affichealert.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Etes vous sure de supprimer cette element ?");   
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        AlertsService ps = new AlertsService() ; 
        affichealert.getItems().remove(selectedItem);
        ps.supprimerAlerts(selectedItem);
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
    private void categories(ActionEvent event) throws IOException {
                        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/CategoriesGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void produits(ActionEvent event) throws IOException {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AjoutproduitsGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @FXML
    private void publicite(ActionEvent event) throws IOException {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/PubliciteGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void print(ActionEvent event) {
        try {
            printNode(AnO);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(AjoutCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AjoutCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AjoutCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AjoutCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void campi(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ProduitsFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }


    
}
