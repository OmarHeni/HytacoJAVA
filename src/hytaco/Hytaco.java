/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hytaco;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Firqs
 */
public class Hytaco extends Application {
    
    private Stage primaryStage;
    private Parent parentPage;
    @Override
    public void start(Stage primaryStage)  {
        
        this.primaryStage = primaryStage;
        
        try { 
         //parentPage = FXMLLoader.load(getClass().getResource("/views/interface.fxml"));
        //parentPage = FXMLLoader.load(getClass().getResource("/views/sponsor.fxml"));
         // parentPage = FXMLLoader.load(getClass().getResource("/views/evenements.fxml"));
         //parentPage = FXMLLoader.load(getClass().getResource("/views/sponors.fxml"));
          //parentPage = FXMLLoader.load(getClass().getResource("/views/front.fxml"));
           parentPage = FXMLLoader.load(getClass().getResource("/views/propositionback.fxml"));
        Scene scene = new Scene(parentPage);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
    launch(args);
    }
    
}
