/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.evenements;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceEvenement;
import javafx.scene.image.Image;


/**
 * FXML Controller class
 *
 * @author Firqs
 */
public class FrontController implements Initializable {

    @FXML
    private VBox affich_even;
    private ServiceEvenement cp = new ServiceEvenement();
      private evenements e;

     List<evenements> liste = new ArrayList <evenements>();
    @FXML
    private Button proposer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
   HBox item = new HBox();
                    affich_even.getChildren().add(item);
           liste=cp.listerPromotion();
int taille=liste.size();
          for( int i=0;i<taille;i++)
            {
               
                    try {
                        if(i % 4 == 0){
                            item = new HBox();
                            affich_even.getChildren().add(item);
                        }
                        VBox content = new VBox();
                           Image image = new Image( new FileInputStream("C:\\Users\\Firqs\\Documents\\NetBeansProjects\\Hytaco\\src\\"+liste.get(i).getImage_name()));
                        ImageView imageView = new ImageView(image);
                        Label title = new Label();
                        e=liste.get(i);
                        //Button btn1 =new Button("detail");
                        
                       
                        
                        Label prixpromo = new Label(liste.get(i).getNom());
                        prixpromo.setStyle("-fx-font-weight: bold");
                        imageView.setFitHeight(190);
                        imageView.setFitWidth(200);
                        
                        content.getChildren().addAll(imageView,title,prixpromo);
                        Button btn = new Button("",content);
                                                       System.out.println(e.getNom());
                          evenements e1 = new evenements(e.getId(),e.getNom(),e.getDate(),e.getImage_name(),e.getNbrplace(),e.getDatef(),e.getLieu(),e.getDescription());

                         btn.setOnAction(event -> {
                            try {
                                
                                //detailProduit(event);
                               

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/details.fxml"));
                               /* ImageController dp=loader.getController();
                                dp.setLb_idPatient(o.getId());*/
                                
                                Scene scene = new Scene(loader.load());
                                
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                
                                stage.setScene(scene);
                                
                                stage.show();
                                 DetailsController controller = loader.<DetailsController>getController();
       
                                  controller.initData(e1);
                                
                                
                                
                            } catch (IOException ex) {
                                Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                            }         
        
            });
                        btn.setPrefWidth(100);
                        item.getChildren().add(btn);
                        affich_even.setSpacing(40);
                        item.setSpacing(20);
                    }
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }

    
    
    }   

    @FXML
    private void proposer(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/propositionfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
