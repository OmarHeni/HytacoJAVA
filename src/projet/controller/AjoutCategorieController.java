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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.search;
import org.apache.commons.io.FileUtils;
import projet.models.categories;
import projet.models.produits;
import projet.service.CategorieService;
import projet.service.ProduitsService;
import projet.utils.Connexionjdbc;
import projet.utils.SendEmail;
import static projet.utils.print.printNode;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 * FXML Controller class
 *
 * @author Taher
 */
public class AjoutCategorieController implements Initializable {

    ObservableList<categories> oblist = FXCollections.observableArrayList();
    String path;
    @FXML
    private TextField nom_categorie;
    @FXML
    private TextField description_categorie;
    @FXML
    private TableView<categories> affiche;
    @FXML
    private TextField recherche;
    @FXML
    private Button categories;
    @FXML
    private Button produits;
    @FXML
    private Button alerts;
    @FXML
    private Button publicite;
    @FXML
    private TableColumn<categories, String> nomcateg;
    @FXML
    private TableColumn<categories, String> desccateg;
    @FXML
    private TextField image_name;
    @FXML
    private Button print;
    @FXML
    private Button Importer;
    
    private String fileName ,fcs;
    @FXML
    private TableColumn<categories,String> image_names;
    private AnchorPane AnOs;
    @FXML
    private AnchorPane AnO;
    @FXML
    private Button deletecategorie;
    @FXML
    private Button ajoutcategorie;
    @FXML
    private Button modifiercategorie;
    @FXML
    private Button closep;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    affcateg();
    }    
    
    
    public void affcateg() {
        ObservableList<categories> observableList = FXCollections.observableArrayList();
        CategorieService us = new CategorieService();
        nomcateg.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        desccateg.setCellValueFactory(new PropertyValueFactory<>("description_categorie"));
        image_names.setCellValueFactory(new PropertyValueFactory<>("image_name"));

        List<categories> lu = null;

        try {
            lu = us.affichercategories();

        } catch (SQLException ex) {
            Logger.getLogger(AjoutCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

        lu.forEach(e -> {
            System.out.println(e);
            observableList.add(e);
        });
        affiche.setItems(observableList);

        FilteredList<categories> filteredData = new FilteredList<>(observableList, b -> true);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(categories -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (categories.getNom_categorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (categories.getDescription_categorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }else if (String.valueOf(categories.getImage_name()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } 
                 else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<categories> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(affiche.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        affiche.setItems(sortedData);
    }

    @FXML
    private void ajoutercategorie(ActionEvent event) 
    {
        CategorieService us = new CategorieService();
        categories pr = new categories(nom_categorie.getText(), description_categorie.getText(), image_name.getText());
        if (us.ajoutercategories(pr) == 1) {
            affcateg();
            nom_categorie.clear();
            description_categorie.clear();      
            image_name.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("login");
            alert.setContentText("Succes");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("login");
            alert.setContentText("Echec");
            alert.show();

        }
        
        try { SendEmail.sendMail("Taher.bekri@esprit.tn");
                } catch (Exception ex) 
                
                {
                    //Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
                }
        String title = "succes ";
        String message = "produit ajouté! ";
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait();     
    }
    
    @FXML
    private void modifiercategorie(ActionEvent event) 
    {
         categories pl = affiche.getSelectionModel().getSelectedItem();
        int id = pl.getId_categorie();
        String noms = nom_categorie.getText();
        String description = description_categorie.getText();
        String image_names = image_name.getText();
        CategorieService sc = new CategorieService();
        categories p = new categories(id, noms, description, image_names);
        sc.modifiercategories(p);
        affcateg();
    }
       
    private void handleMouseButton(MouseEvent event) {
        
        categories p = affiche.getSelectionModel().getSelectedItem();
        nom_categorie.setText(p.getNom_categorie());
        description_categorie.setText(p.getDescription_categorie());
        image_name.setText(p.getImage_name());
    }



    @FXML
    private void produits(ActionEvent event) throws IOException 
    {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AjoutproduitsGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void alerts(ActionEvent event) throws IOException 
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AlertsGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void publicite(ActionEvent event) throws IOException 
    {
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
    private void Importer(ActionEvent event) {
        
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
         fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", ".jpg", ".png"));
        if (SelectedFile != null) {

            fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) 
            {
                fileName = fcs.substring(index + 1);
            }
        }
        fcs=SelectedFile.getAbsolutePath();
        File source = new File(fcs);
        File destination = new File(System.getProperty("user.dir") + "\\src\\image\\" + fileName);
        String url = "" + fileName;
         image_name.setText(url);        
    
    }

    @FXML
    private void delete(ActionEvent event) {
        categories p = affiche.getSelectionModel().getSelectedItem();
        CategorieService sc = new CategorieService();
        sc.supprimercategories(p);
        affcateg();
    }

    @FXML
    private void Close(ActionEvent event) {
    }
    
    @FXML
    private void categories(ActionEvent event) {
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
