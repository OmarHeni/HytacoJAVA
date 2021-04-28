/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.sponsors;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javax.swing.JOptionPane;
import services.ServiceSponsors;
import utils.DataSource;
import views.JavaMailUtil;

/**
 * FXML Controller class
 *
 * @author Firqs
 */
public class SponsorController implements Initializable {
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
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Label nom_u;
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
            Logger.getLogger(SponsorController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void Close(ActionEvent event) {
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
            JOptionPane.showMessageDialog(null, "adresse est  obligatoire ");
        } else if (image_name.equals("")) {
            JOptionPane.showMessageDialog(null, "image est obligatoire ");
        } 
      
     
       
        else{
      sponsors s = new sponsors(0 , nom,adresse,mail,numero, image_name);
        ServiceSponsors sc = new ServiceSponsors();
               sc.ajouter(s);
           JavaMailUtil mail1 = new JavaMailUtil();
            mail1.sendMail(s.getMail());
               loadTable();
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
        
        
        
    }

    @FXML
    private void delete(ActionEvent event) {
        sponsors s=tv_sponsor.getSelectionModel().getSelectedItem();
     //  tv_sponsor.getItems().removeAll(tv_sponsor.getSelectionModel().getSelectedItem());
        ServiceSponsors sc = new ServiceSponsors();
               sc.deleteSponsor(s);
               loadTable();
    }
    
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
    private void importer(ActionEvent event) {
    }

 

    
}
