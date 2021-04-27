/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projet.controller.AjoutProduitsGUIController;
import projet.models.produits;
import projet.models.publicite;
import projet.service.ProduitsService;
import projet.service.PubliciteService;
/**
 * FXML Controller class
 *
 * @author Taher
 */
public class ProduitsFGUIController implements Initializable {

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

    @FXML
    private TextField Searchp;
    @FXML
    private VBox vboxx;
    @FXML
    private Button Categories;
    @FXML
    private Button Alerts;
    @FXML
    private Button campi;
    @FXML
    private VBox affich_produit;
    private ProduitsService cp = new ProduitsService();
      private produits o;

     List<produits> liste = new ArrayList <produits>();
    
     
     
     
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
    public void initialize(URL url, ResourceBundle rb) {
    Font.loadFont(getClass().getResourceAsStream("/css/SourceSansPro-Regular.ttf"), 14);
              setVBOXP();
HBox item = new HBox();
                    affich_produit.getChildren().add(item);
           liste=cp.listerPromotion();
int taille=liste.size();
          for( int i=0;i<taille;i++)
            {
               
                    try {
                        if(i % 4 == 0){
                            item = new HBox();
                            affich_produit.getChildren().add(item);
                        }
                        VBox content = new VBox();
                           Image image = new Image( new FileInputStream("C:/xampp/web/web/Hytaco/public/images/properties/"+liste.get(i).getImage_name()));
                        ImageView imageView = new ImageView(image);
                       
                        Label title = new Label();
                        o=liste.get(i);                        
                        Label description = new Label((liste.get(i).getDescription_produit()));
                        description.setStyle("-fx-strikethrough: true");
                        description.getStyleClass().add("barre");
                                                
                        
                        Label prixpromo = new Label(liste.get(i).getNom_produit());
                        prixpromo.setStyle("-fx-font-weight: bold");
                        imageView.setFitHeight(150);
                        imageView.setFitWidth(200);
                        
                        content.getChildren().addAll(imageView,title,prixpromo,description);
                        Button btn = new Button("",content);
                          produits o1 = new produits(o.getId_produit(), o.getNom_produit(),o.getDescription_produit(),o.getImage_name(),o.getQuantite_produit(),o.getPrix_produit());

                         btn.setOnAction(event -> {
                            try {  
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/DetailproduitsController.fxml"));
                               /* ImageController dp=loader.getController();
                                dp.setLb_idPatient(o.getId());*/
                                Scene scene = new Scene(loader.load());
                                
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                
                                stage.setScene(scene);
                                
                                stage.show();
                                 DetailproduitsController controller = loader.<DetailproduitsController>getController();
       
                                  controller.initData(o1);
                                
                                
                                
                            } catch (IOException ex) {
                                Logger.getLogger(ProduitsFGUIController.class.getName()).log(Level.SEVERE, null, ex);
                            }         
        
            });
                        btn.setPrefWidth(200);
                        item.getChildren().add(btn);
                        affich_produit.setSpacing(50);
                        item.setSpacing(20);
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(ProduitsFGUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

}