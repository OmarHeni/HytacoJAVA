/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.sponsors;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.DataSource;

import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.Notifications;
import services.ServiceSponsors;
import views.JavaMailUtil;


/**
 * FXML Controller class
 *
 * @author Firqs
 */
public class SponorsController implements Initializable {
ObservableList<sponsors> oblist = FXCollections.observableArrayList();
String path;
    @FXML
    private TableColumn<sponsors, Integer> cl_id;
    @FXML
    private TableColumn<sponsors, String> cl_nom;
    @FXML
    private TableColumn<sponsors, String> cl_adresse;
    @FXML
    private TableColumn<sponsors, String> cl_mail;
    @FXML
    private TableColumn<sponsors,Integer> cl_num;
    @FXML
    private TableColumn<sponsors,String> cl_image;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_mail;
    @FXML
    private TextField tf_num;
    @FXML
    private TextField tf_image;
    @FXML
    private Button btn_import;
    @FXML
    private Button btn_ajout;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    @FXML
    private TableView<sponsors> tv_sponsor;
    private String fileName ,fcs;
    @FXML
    private TextField txtRech;
    private PreparedStatement preparedStatement;
    @FXML
    private Button btn_event;
    @FXML
    private Button campi;
    @FXML
    private Button proposition;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            loadTable();
    }    
    
    
     public void loadTable(){
        
        try {
            
          oblist.clear();
            Connection cnx = DataSource.getInstance().getCnx();
            
            ResultSet rs = cnx.createStatement().executeQuery("Select id,nom,adresse,mail,numero,image_name from sponsors");
            
            while(rs.next()){
                
                oblist.add(new sponsors(rs.getInt("id"),rs.getString("nom"),rs.getString("adresse"),rs.getString("mail"),rs.getInt("numero"),rs.getString("image_name")));
           
            }
            
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(EvenementsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        cl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cl_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
          cl_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            cl_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
              cl_num.setCellValueFactory(new PropertyValueFactory<>("numero"));
        cl_image.setCellValueFactory(new PropertyValueFactory<>("image_name"));
       
       
       
      
       
     
      tv_sponsor.setItems(oblist);
      
      FilteredList<sponsors> filteredData = new FilteredList<>(oblist,b-> true);
        
          txtRech.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(sponsors -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (sponsors.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (sponsors.getAdresse().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (sponsors.getImage_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                                
                                   //tf_datedeb.setValue(o.getDatedeb().toLocalDate());
                                
                                else if (String.valueOf(sponsors.getMail()).indexOf(lowerCaseFilter)!=-1) {
					return true; // Filter matches last name.
				}
                                  else if (String.valueOf(sponsors.getNumero()).indexOf(lowerCaseFilter)!=-1) {
					return true; // Filter matches last name.
				}
                                
				else if (String.valueOf(sponsors.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
          // 3. Wrap the FilteredList in a SortedList. 
		SortedList<sponsors> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tv_sponsor.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		
              tv_sponsor.setItems(sortedData);


    
    }
    
    
    
    
    
    
    

    @FXML
    private void importer(ActionEvent event) {
        
             FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
         fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", ".jpg", ".png"));
           //  e.setImage_event(SelectedFile.getAbsolutePath());
        if (SelectedFile != null) {

            fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) {
                fileName = fcs.substring(index + 1);
                //System.out.println(fileName);
            }

        }
     
        
            fcs=SelectedFile.getAbsolutePath();
        File source = new File(fcs);
        File destination = new File(System.getProperty("user.dir") + "\\src\\image\\" + fileName);
        String url = "/image/" + fileName;
        if (!destination.exists()) {
            try {
                FileUtils.copyFile(source, destination);
            } catch (Exception E) {

            }
         
            
               tf_image.setText(url);
            
       
        
        
        
    }
    }

    @FXML
    private void ajouter(ActionEvent event) throws Exception {
        
         String nom=tf_nom.getText();
         String adresse=tf_adresse.getText();
         String mail=tf_mail.getText();
      int numero = Integer.parseInt(tf_num.getText());
        String image_name =tf_image.getText();
      
      
     
       
if ((image_name.isEmpty())&& (nom.isEmpty())&& (mail.isEmpty()) && (adresse.isEmpty()) ) {
            JOptionPane.showMessageDialog(null, "il faut remplir tous les champs  ");
        }
        else if (nom.equals("")) {
            JOptionPane.showMessageDialog(null, "la saisie du nom  est obligatoire ");
        }  else if (mail.equals("")) {
            JOptionPane.showMessageDialog(null, "email est obligatoire  ");
        } else if (adresse.equals("")) {
            JOptionPane.showMessageDialog(null, "l'adresse est  obligatoire ");
        } else if (image_name.equals("")) {
            JOptionPane.showMessageDialog(null, "l'image est obligatoire ");
        } 
      
     
       
        else{
      sponsors s = new sponsors(0 , nom,adresse,mail,numero, image_name);
        ServiceSponsors sc = new ServiceSponsors();
               sc.ajouter(s);
           JavaMailUtil mail1 = new JavaMailUtil();
            mail1.sendMail(s.getMail());
               loadTable();
             double m = 1;
               
              
            //double h = 1;
               
             
  Notifications notificationBuilder =Notifications.create()
          .title("Le sponsor a ete ajoute")
          .text("")
           .graphic(null)
           .hideAfter(Duration.minutes(m))  
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
    private void modifier(ActionEvent event) {
        sponsors cl=tv_sponsor.getSelectionModel().getSelectedItem();
          int id =cl.getId();
           String nom=tf_nom.getText();
           String adresse=tf_adresse.getText();
            String mail=tf_mail.getText();
           
            int numero = Integer.parseInt(tf_num.getText());
            String image_name = tf_image.getText();
           
       
         
         
            ServiceSponsors sc = new ServiceSponsors();
           sponsors s = new sponsors(id,nom,adresse,mail,numero,image_name);
           //Cours cc = new Cours(id, instru , niveau, vid);
            sc.modifierSponsor(s);
            
            loadTable();
               double m = 1;
               
              
            //double h = 1;
               
             
  Notifications notificationBuilder =Notifications.create()
          .title("Le sponsor a ete modifie")
          .text("")
           .graphic(null)
           .hideAfter(Duration.minutes(m))  
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

    @FXML
    private void delete(ActionEvent event) {
        sponsors s=tv_sponsor.getSelectionModel().getSelectedItem();
     //  tv_sponsor.getItems().removeAll(tv_sponsor.getSelectionModel().getSelectedItem());
        ServiceSponsors sc = new ServiceSponsors();
               sc.deleteSponsor(s);
               loadTable();
                      double m = 1;
               
              
            //double h = 1;
               
             
  Notifications notificationBuilder =Notifications.create()
          .title("Le sponsor a ete supprime")
          .text("")
           .graphic(null)
           .hideAfter(Duration.minutes(m))  
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
    
    @FXML
    private void handleMouseButton(javafx.scene.input.MouseEvent event) {
        sponsors  s= tv_sponsor.getSelectionModel().getSelectedItem();
         
            
            tf_nom.setText(s.getNom());
            tf_adresse.setText(s.getAdresse());
            tf_mail.setText(s.getMail());
            tf_num.setText(s.getNumero()+"");
            tf_image.setText(s.getImage_name());
              //Date datedeb=java.sql.Date.valueOf(tf_datedeb.getValue());
        //Date datefin=java.sql.Date.valueOf(tf_datefin.getValue());
            //tf_datedeb.setDate(o.getDatedeb());
          // tf_datefin.setDate(o.getDatedeb());
           // p.setDate_debut(java.sql.Date.valueOf(date_debut.SetValue()));
        //p.setDate_fin(java.sql.Date.valueOf(date_fin.getValue()));
           // tf_nbrp.Integer.parseInt(e.getNbrplace().toString());
           
            
        }

   /* @FXML
    private void rechercher(KeyEvent event) {
         try {
            oblist.clear();
           //Connection cnx = DataSource.getInstance().getCnx();
            
           // ResultSet rs = cnx.prepareStatement().executeQuery("select * from evenements where nom='"+txtRech.getText()+"'");
            Connection cnx = DataSource.getInstance().getCnx();
           preparedStatement=cnx.prepareStatement("select * from sponsors where  id='"+txtRech.getText()+"'" );
           ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                
                oblist.add(new sponsors(rs.getInt("id"),rs.getString("nom"),rs.getString("adresse"),rs.getString("mail"),rs.getInt("numero"),rs.getString("image_name")));
           
            
                      
                        
                  }
             } catch (SQLException ex) {
            Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
tv_sponsor.setItems(oblist);
         if(txtRech.getText().trim().isEmpty())
         {
            loadTable();
         
         }
        
        
    }*/

    @FXML
    private void rechercher(KeyEvent event) {
    }

    @FXML
    private void onclick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/evenements.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goFront(ActionEvent event) throws IOException {
         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/front.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goProposition(ActionEvent event) throws IOException {
         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/propositionback.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
