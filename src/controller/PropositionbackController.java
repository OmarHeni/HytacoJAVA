/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entities.proposition;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceProposition;
import static sun.net.www.MimeTable.loadTable;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author Firqs
 */
public class PropositionbackController implements Initializable {
    
    ObservableList<proposition> oblist = FXCollections.observableArrayList();
String path;
   


    @FXML
    private Button event;
    @FXML
    private Button sponsor;
    @FXML
    private TableView<proposition> tv_proposition;
    @FXML
    private TextField tf_nom;
    @FXML
    private DatePicker tf_date;
    @FXML
    private TextField tf_nb;
    @FXML
    private TextField tf_mail;
    @FXML
    private TableColumn<proposition, String> cl_nom;
    @FXML
    private TableColumn<proposition, Date> cl_date;
    @FXML
    private TableColumn<proposition, Integer> cl_nb;
    @FXML
    private TableColumn<proposition,String> cl_mail;
    @FXML
    private Button supprime;
    @FXML
    private Button btn_campi;
    @FXML
    private TextField txtRech;

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
            
            ResultSet rs = cnx.createStatement().executeQuery("Select id,nom,date,nombre_place,email,mail from proposition");
            
            while(rs.next()){
                
                oblist.add(new proposition(rs.getInt("id"),rs.getString("nom"),rs.getDate("date"),rs.getInt("nombre_place"),rs.getString("email"),rs.getString("mail")));
           
            }
            
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(EvenementsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //cl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cl_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cl_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        cl_nb.setCellValueFactory(new PropertyValueFactory<>("nombre_place"));
        cl_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
      
       
     
       tv_proposition.setItems(oblist);
       
        FilteredList<proposition> filteredData = new FilteredList<>(oblist,b-> true);
        
          txtRech.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(proposition -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (proposition.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
                                
                                   //tf_datedeb.setValue(o.getDatedeb().toLocalDate());
                                
                                else if (String.valueOf(proposition.getDate()).indexOf(lowerCaseFilter)!=-1) {
					return true; // Filter matches last name.
				}
                                  
                                
				else if (String.valueOf(proposition.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
          // 3. Wrap the FilteredList in a SortedList. 
		SortedList<proposition> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tv_proposition.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		
               tv_proposition.setItems(sortedData);


       
       
    
   
    }
    

    
    
    
    @FXML
    private void goEvent(ActionEvent event) throws IOException {
         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/evenements.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goSponsor(ActionEvent event) throws IOException {
         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/sponors.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMouseButton(MouseEvent event) {
         proposition  p= tv_proposition.getSelectionModel().getSelectedItem();
         
            
            tf_nom.setText(p.getNom());
             tf_date.setValue(p.getDate().toLocalDate());
            //tf_image.setText(e.getImage_name());
              //Date datedeb=java.sql.Date.valueOf(tf_datedeb.getValue());
        //Date datefin=java.sql.Date.valueOf(tf_datefin.getValue());
            //tf_datedeb.setDate(o.getDatedeb());
          // tf_datefin.setDate(o.getDatedeb());
           // p.setDate_debut(java.sql.Date.valueOf(date_debut.SetValue()));
        //p.setDate_fin(java.sql.Date.valueOf(date_fin.getValue()));
           // tf_nbrp.Integer.parseInt(e.getNbrplace().toString());
            //tf_datefin.setValue(e.getDatef().toLocalDate());
            //tf_lieu.setText(e.getLieu());
         
             tf_nb.setText(p.getNombre_place()+"");
             tf_mail.setText(p.getMail());
    }

    @FXML
    private void delete(ActionEvent event) {
          proposition p=tv_proposition.getSelectionModel().getSelectedItem();
       //tv_evenement.getItems().removeAll(tv_evenement.getSelectionModel().getSelectedItem());
        ServiceProposition sc = new ServiceProposition();
               sc.deletePropo(p);
               loadTable();
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
    private void rechercher(KeyEvent event) {
    }
    
}
