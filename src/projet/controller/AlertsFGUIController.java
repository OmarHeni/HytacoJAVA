/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projet.models.Alerts;
import projet.service.AlertsService;
import projet.utils.SendEmail;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Taher
 */
public class AlertsFGUIController implements Initializable {

    @FXML
    private TextField localisation;
    @FXML
    private TextField mail;
    @FXML
    private TextField telephone;
    @FXML
    private TextArea rapport;
    @FXML
    private Button envoyeralert;
    @FXML
    private AnchorPane brand;
    @FXML
    private Button closeb;
    @FXML
    private Label Reclamations1;
    @FXML
    private Label Programmes1;
    @FXML
    private Label Evenements1;
    @FXML
    private Label Locaux1;
    @FXML
    private Label Propositions1;
    @FXML
    private Label Panier1;
    private TableColumn<Alerts, String> localisations;
    private TableColumn<Alerts, String> mails;
    private TableView<Alerts> affichealerts;
    private TableColumn<Alerts, String> rapports;
    private TableColumn<Alerts, Integer> telephones;
    private TableColumn<Alerts, Date> dates;
    private TableColumn<Alerts, Integer> programmesss;

    @FXML
    private Button Categories;
    @FXML
    private Button produits;
    @FXML
    private Button campi;
    @FXML
    private ListView<?> programme;
    @FXML
    private DatePicker date;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void envoyeralert(ActionEvent event)  {
        
          if(localisation.getText().isEmpty()||telephone.getText().isEmpty()||rapport.getText().isEmpty()||mail.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "verifer les champs");  
        }
        else
        {
        String localisationss = localisation.getText();
        String rapportss = rapport.getText();
        int telephoness = Integer.parseInt(telephone.getText());
        String mailss = mail.getText();
        Date datss=java.sql.Date.valueOf(date.getValue());
       
        Alerts a = new Alerts(5,localisationss,(java.sql.Date)datss,rapportss,telephoness,mailss);
        AlertsService sc = new AlertsService();
        sc.ajouteAlerts(a);
        String title = "succes ";
        String message = "Votre alert est bien reçu,"
                + "Verifier votre boite mail svp!Merci.";
         try { SendEmail.sendMail("Taher.bekri@esprit.tn");
                } catch (Exception ex) 
                
                {
                }
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait();             
        localisation.clear();
        rapport.clear();
        telephone.clear();
        mail.clear();    
       

        }
    }

    @FXML
    private void Close(ActionEvent event) {
        
    }

    @FXML
    private void Categories(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/accueilFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void produits(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ProduitsFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void campi(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/CategoriesGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
    

