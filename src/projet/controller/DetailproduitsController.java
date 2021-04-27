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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projet.models.produits;
import projet.models.publicite;
import projet.service.PubliciteService;

/**
 * FXML Controller class
 *
 * @author Taher
 */
public class DetailproduitsController implements Initializable {

    @FXML
    private ImageView image;
    private produits o;
    @FXML
    private AnchorPane brand;
    @FXML
    private Button closeb;
    @FXML
    private Button Categories;
    @FXML
    private Button Alerts;
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
    @FXML
    private Button campi;
    @FXML
    private Label nom;
    @FXML
    private Label description;
    @FXML
    private Label prix;
    @FXML
    private Label quantite;
    @FXML
    private Button ajouter;
    @FXML
    private Button Retour;
    @FXML
    private VBox vboxx;
    @FXML
    private Button dislike;
    @FXML
    private Button like;
    /**
     * Initializes the controller class.
     */
    
         
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
 
    
    public void setVBOXP(){
int count =0 ;
int coun =0 ;
int cou =0 ;
HBox hbell = new HBox();

PubliciteService us = new PubliciteService();
           List<publicite> lu = null;
           lu = us.afficherpublicite();

    for (int i = 0; i < lu.size(); i++) {     
        count++;  coun++;cou++;

     VBox vbell1 = new VBox();
     Label nom = new Label();
     nom.setText(lu.get(i).getNom_publicite());

     Label desc = new Label();
     desc.setText(lu.get(i).getLien_publicite());
               vbell1.setAlignment(Pos.CENTER);

ImageView image = setImage(lu.get(i).getImage_name());
image.setFitHeight(60);
image.setFitWidth(60);
vbell1.getChildren().addAll(image,nom,desc);

hbell.getChildren().add(vbell1);
hbell.setMargin(vbell1, new Insets(50, 0, 0, 50));
//    hbell.getChildren().add(image);
if ((count % 4)==0)
{
                   vbell1.setAlignment(Pos.CENTER);

vboxx.getChildren().add(hbell);
hbell = new HBox();
}

}
    if ((coun % 3)==0)
{
vboxx.getChildren().add(hbell);
hbell = new HBox();
}
    
        if ((cou % 1)==0)
{
vboxx.getChildren().add(hbell);
hbell = new HBox();
}
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              setVBOXP();
    }    
    
    
    public void initData (produits o){
    
    this.o=o;

        
     File file = new File ("C:/xampp/web/web/Hytaco/public/images/properties/"+o.getImage_name());
    Image imageForFile = null ;
        try {
            
         //   cl_mail.setText(o.getMailen());
          nom.setText(o.getNom_produit());
      nom.setWrapText(true);

          description.setText(o.getDescription_produit());
          description.setWrapText(true);
          prix.setText(o.getPrix_produit()+"dt");
          quantite.setText(o.getQuantite_produit()+"Pieces");
            System.out.println(file.toURI().toURL());
            imageForFile = new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(DetailproduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
   image.setImage(imageForFile);}

    @FXML
    private void Close(ActionEvent event) {
    }

    @FXML
    private void Categories(ActionEvent event) {
    }

    @FXML
    private void Alerts(ActionEvent event) {
    }

    @FXML
    private void campi(ActionEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event) {
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/ProduitsFGUI.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void dislike(ActionEvent event) {
    }

    @FXML
    private void like(ActionEvent event) {
    }

    
}
