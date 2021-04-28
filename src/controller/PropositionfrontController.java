/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.proposition;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import services.ServiceProposition;
import views.JavaMailUtil;

/**
 * FXML Controller class
 *
 * @author Firqs
 */
public class PropositionfrontController implements Initializable {

    @FXML
    private TextField tf_nom;
    @FXML
    private DatePicker tf_date;
    @FXML
    private TextField tf_nb;
    @FXML
    private TextField tf_mail;
    @FXML
    private Button proposer;
    @FXML
    private Button btn_event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void proposer(ActionEvent event) throws Exception {
        
            String nom=tf_nom.getText();
        Date date;
        date = java.sql.Date.valueOf(tf_date.getValue());
        
      
       int nombre_place = Integer.parseInt(tf_nb.getText());
        
       String mail=tf_mail.getText();
       
  if ( (nom.isEmpty())&& (mail.isEmpty())) {
            JOptionPane.showMessageDialog(null, "il faut remplir tous les champs  ");
        }
        else if (nom.equals("")) {
            JOptionPane.showMessageDialog(null, "la saisi du nom est obligatoire ");
        } else if (mail.equals("")) {
            JOptionPane.showMessageDialog(null, "Vous devez saisir votre adresse mail");
        } 
       else{
        proposition p = new proposition(0 , nom, date , nombre_place ,"" , mail);
        ServiceProposition sc = new ServiceProposition();
               sc.ajouter(p);
               JavaMailUtil mail1 = new JavaMailUtil();
            mail1.sendMail1(p.getMail());
              // loadTable();
            double s = 1;
            //double h = 1;
               
             
  Notifications notificationBuilder =Notifications.create()
          .title("La proposition a ete ajoute")
          .text("")
           .graphic(null)
           .hideAfter(Duration.minutes(s))  
           .position(Pos.BOTTOM_RIGHT)     
           .onAction(new EventHandler<ActionEvent>(){
       @Override
        public void handle(ActionEvent event){
            System.out.println("clicked on notification");
        }
    });
  //notificationBuilder.darkStyle();
notificationBuilder.showInformation();
               
               }
    }

    @FXML
    private void goEvent(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/front.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
}
