/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projet.models.categories;
import projet.service.CategorieService;

/**
 * FXML Controller class
 *
 * @author Taher
 */
public class AccueilFController implements Initializable {

    @FXML
    private AnchorPane brand;
    @FXML
    private VBox vbox;
    @FXML
    private Label Evenements1;
    @FXML
    private Label Locaux1;
    @FXML
    private Label Propositions1;
    @FXML
    private Label Panier1;
    @FXML
    private Button Categories;
    @FXML
    private Button Alerts;
    @FXML
    private ImageView img;
    @FXML
    private Button campi;
    @FXML
    private Button produitss;
    @FXML
    private Button Transporteur;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              Font.loadFont(getClass().getResourceAsStream("/css/SourceSansPro-Regular.ttf"), 14);

        try {
            setVBOX();
        } catch (SQLException ex) {
            Logger.getLogger(AccueilFController.class.getName()).log(Level.SEVERE, null, ex);
        }
 }    

    
        public ImageView setImage(String  nom){
        ImageView   image = new ImageView ();
        File file = new File ("C:/xampp/web/web/Hytaco/public/images/properties/"+nom);
        Image imageForFile = null;
        try {
             System.out.println(file.toURI().toURL());
            imageForFile = new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProduitsFGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
            image.setImage(imageForFile);
            return image ;
    }
    
    public void setVBOX() throws SQLException{
int count =0 ;
int coun =0 ;
int cou =0 ;
HBox hbel = new HBox();

CategorieService us = new CategorieService();
           List<categories> lu = null;
           lu = us.affichercategories();

    for (int i = 0; i < lu.size(); i++) {     
        count++;  coun++;cou++;

     VBox vbel1 = new VBox();
     Label nom = new Label();
     nom.setText(lu.get(i).getNom_categorie());

     Label desc = new Label();
     desc.setText(lu.get(i).getDescription_categorie());
               vbel1.setAlignment(Pos.CENTER);

ImageView image = setImage(lu.get(i).getImage_name());
image.setFitHeight(200);
image.setFitWidth(200);
vbel1.getChildren().addAll(image,nom,desc);

hbel.getChildren().add(vbel1);
hbel.setMargin(vbel1, new Insets(40, 0, 0, 40));
//    hbel.getChildren().add(image);
if ((count % 4)==0)
{
vbox.getChildren().add(hbel);
hbel = new HBox();
}

}
    if ((coun % 3)==0)
{
vbox.getChildren().add(hbel);
hbel = new HBox();
}
    
        if ((cou % 1)==0)
{
vbox.getChildren().add(hbel);
hbel = new HBox();
}
       
    }
    

    @FXML
    private void Categories(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ProduitsFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Alerts(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AlertsFGUI.fxml"));/* Exception */
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


    @FXML
    private void produitss(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ProduitsFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Reclamation(ActionEvent event) throws IOException {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/reclamationfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Transporteur(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/transporteurfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
