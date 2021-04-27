/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import projet.models.produits;
import projet.service.ProduitsService;
import projet.utils.SendEmail;
import static projet.utils.print.printNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javax.swing.JOptionPane;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
/**
 * FXML Controller class
 *
 * @author Taher
 */
public class AjoutProduitsGUIController implements Initializable {

    @FXML
    private TextField nom_produit;
    @FXML
    private TextField description_produit;
    @FXML
    private TextField prix_produit;
    @FXML
    private Button ajoutproduit;
    @FXML
    private TableView<produits> afficheprod;
    @FXML
    private TableColumn<produits, String> nomprod;
    @FXML
    private TableColumn<produits, String> descprod;
    @FXML
    private TextField recherchess;
    @FXML
    private TableColumn<produits, Integer> quantiteprod;
    @FXML
    private TableColumn<produits, Double> prixprod;
    @FXML
    private Button modifprod;
    @FXML
    private Button deleteprod;
    @FXML
    private Button Importer;
    private TableColumn<produits, String> imagee;
    @FXML
    private TextField image_name;
    private String fileName ,fcs;
    @FXML
    private TableColumn<produits, String> imagepp;
    @FXML
    private Button categories;
    @FXML
    private Button produits;
    @FXML
    private Button alerts;
    @FXML
    private Button publicite;
    @FXML
    private Button print;
    private AnchorPane AnO;
    @FXML
    private Button closep;
    @FXML
    private AnchorPane AnOs;
    @FXML
    private TextField quantite;
    @FXML
    private Button Statistiques;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    ProduitsService ps = new ProduitsService();
    ArrayList<produits> publiciteList =  (ArrayList<produits>) ps.afficherproduits() ;
    ObservableList<produits> data = FXCollections.observableArrayList(publiciteList); 
    afficheprod.setItems(data);
        nomprod.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        descprod.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        quantiteprod.setCellValueFactory(new PropertyValueFactory<>("quantite_produit"));
        prixprod.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        imagepp.setCellValueFactory(new PropertyValueFactory<>("image_name"));


    }
    @FXML
    private void ajoutproduit(ActionEvent event) 
    {
      
        String lp = nom_produit.getText();
        String np = description_produit.getText();
        String ip = image_name.getText();
        Integer qp = Integer.parseInt(quantite.getText());
        Double pp = Double.parseDouble(prix_produit.getText());

        produits a = new produits(5,lp,np,ip,qp,pp);
        ProduitsService sc = new ProduitsService();
        sc.ajouteproduits(a);
                    JOptionPane.showMessageDialog(null, "ajout avec succes");
        nom_produit.clear();
        description_produit.clear();      
        quantite.clear();
        prix_produit.clear();
        image_name.clear();
       
         ArrayList<produits> publiciteList =  (ArrayList<produits>) sc.afficherproduits() ; 
    ObservableList<produits> data = FXCollections.observableArrayList(publiciteList);
    
    afficheprod.setItems(data);
       nomprod.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        descprod.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        quantiteprod.setCellValueFactory(new PropertyValueFactory<>("quantite_produit"));
        prixprod.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        imagepp.setCellValueFactory(new PropertyValueFactory<>("image_name"));

        
    }

    private void handleMouseButton(MouseEvent event) {
        produits p = afficheprod.getSelectionModel().getSelectedItem();

        nom_produit.setText(p.getNom_produit());
        description_produit.setText(p.getDescription_produit());
        quantite.setText(String.valueOf(p.getQuantite_produit()));
        prix_produit.setText(String.valueOf(p.getPrix_produit()));
        image_name.setText(p.getImage_name());
    }


    @FXML
    private void modifprod(ActionEvent event) {

        produits r=afficheprod.getSelectionModel().getSelectedItem();
          int id =r.getId_produit();
          
        String noms = nom_produit.getText();
        String description = description_produit.getText();
        Integer quantitee = Integer.parseInt(quantite.getText());
        Double prix = Double.parseDouble(prix_produit.getText());
        String image_names = image_name.getText();

           
        ProduitsService sc = new ProduitsService();

        produits p = new produits(id, noms, description, image_names ,quantitee, prix);
           
            sc.modifierproduits(p);
            ArrayList<produits> publiciteList =  (ArrayList<produits>) sc.afficherproduits() ;
        ObservableList<produits> donnee = FXCollections.observableArrayList(publiciteList); 
     afficheprod.setItems(donnee);
       nomprod.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        descprod.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        quantiteprod.setCellValueFactory(new PropertyValueFactory<>("quantite_produit"));
        prixprod.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        imagepp.setCellValueFactory(new PropertyValueFactory<>("image_name"));

    }

    @FXML
    private void delete(ActionEvent event) {
           produits selectedItem = afficheprod.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Etes vous sure de supprimer cette element ?");   
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        ProduitsService ps = new ProduitsService() ; 
        afficheprod.getItems().remove(selectedItem);
        ps.supprimerproduits(selectedItem);
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
    private void Importer(ActionEvent event) {
        
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
        File destination = new File(System.getProperty("user.dir") + "" + fileName);
        String url = "" + fileName;
      
         image_name.setText(url);        
    
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
    private void produits(ActionEvent event) {
    }


    @FXML
    private void alerts(ActionEvent event) throws IOException {
        
          Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AlertsGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void publicite(ActionEvent event) throws IOException {
        
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/publicite.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void print(ActionEvent event) {
        
        try {
            printNode(AnOs);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(AjoutProduitsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AjoutProduitsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AjoutProduitsGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AjoutProduitsGUIController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void Statistiques(ActionEvent event) throws IOException {
        
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/statProduits.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
