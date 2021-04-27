/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entites.CommandeAff;
import Services.ServiceCommande;
import Services.UserSession;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import Controller.AfficherUtilisateursController;
import Entites.Utilisateur;
import Services.ServiceUtilisateur;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hassene
 */
public class AfficherCommandeController implements Initializable {

    @FXML
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;
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
    private Button supprimer_c;
    @FXML
    private TableView<CommandeAff> commandes;
            ObservableList<CommandeAff> datacoms = FXCollections.observableArrayList();
    @FXML
    private TextField searchc;
    @FXML
    private LineChart<String, Number> stat;
    @FXML
    private Button to_utilisateurs;
    @FXML
    private TableColumn<?, ?> statue;
    @FXML
    private TableColumn<?, ?> adresse;
    @FXML
    private TableColumn<?, ?> dateliv;
    @FXML
    private Button paye;

    /**
     * Initializes the controller class.
     */
    
      public void initializeCommande(){
          datacoms.clear();
        ServiceCommande us = new ServiceCommande();
            produit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
            client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            statue.setCellValueFactory(new PropertyValueFactory<>("statue"));
            adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            dateliv.setCellValueFactory(new PropertyValueFactory<>("dateliv"));

            List<CommandeAff> lu = null;
            lu = us.getTCommandes();
      
            lu.forEach(e -> {
                datacoms.add(e);
            });
            commandes.setItems(datacoms);
            FilteredList<CommandeAff> filteredData = new FilteredList<>(datacoms,b-> true);
        
          searchc.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate((CommandeAff c)-> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				System.out.println(newValue);
                                System.out.println(c.getNom_client());
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (c.getNom_client().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (c.getNom_produit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (c.getPrix().toString().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (c.getQuantite().toString().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                               
				     else  
				    	 return false; // Does not match.
			});

		});
		SortedList<CommandeAff> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(commandes.comparatorProperty());
		commandes.setItems(sortedData);
    }
    public void  initializeStat(){
        ServiceCommande us = new ServiceCommande();
       HashMap<Date,Double> map =  us.getStatCommandes();
              XYChart.Series series1 = new XYChart.Series();
              series1.setName("Revenue par date");
            for (Date date : map.keySet()) {      
                series1.getData().add(new XYChart.Data(date.toString(),map.get(date)));
        }
            stat.getData().add(series1);
    }
        public void setImage() {
        File file = new File (System.getProperty("user.dir") + "\\src\\image\\" +UserSession.getInstace().getUtilisateur().getImage_name());
        try {
            circle.setFill(new ImagePattern(new Image(file.toURI().toURL().toExternalForm())));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
initializeCommande() ; 
    initializeStat();
//         nom_u.setText(UserSession.getInstace().getUtilisateur().getPrenom()+" "+UserSession.getInstace().getUtilisateur().getNom());
     //   setImage();
    }    

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void SupprimerCommande(ActionEvent event) {
         CommandeAff comSelect = commandes.getSelectionModel().getSelectedItem();
        if (comSelect == null) {
            return;
        }
        ServiceCommande us = new ServiceCommande();

        us.SupprimerCommandes(comSelect);
        datacoms.remove(comSelect);
        //pour refraichir le tableView
        initializeCommande();
    }


    @FXML
    private void ToUtilisateur(ActionEvent event) {
            Parent   root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/AfficherUtilisateur.fxml"));
        } catch (IOException ex) {
        }
                                        
                    Stage window = (Stage) to_utilisateurs.getScene().getWindow();
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
    private void ToPaye(ActionEvent event) {
          CommandeAff comSelect = commandes.getSelectionModel().getSelectedItem();
        if (comSelect == null) {
            return;
        }
        ServiceCommande us = new ServiceCommande();

        us.ToPayer(comSelect.getId());
        //pour refraichir le tableView
        initializeCommande();
    }

   
    
}
