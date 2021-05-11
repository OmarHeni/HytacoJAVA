/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Hassene
 */
public class AccueilBackController implements Initializable {

    @FXML
    private Button to_utilisateurs;
    @FXML
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;
    @FXML
    private LineChart<?, ?> stat;
    @FXML
    private Label datef;
    @FXML
    private Label degree;
    @FXML
    private Label description;
    @FXML
    private Label humidity;
    @FXML
    private Label vent;
    @FXML
    private ImageView imagedes;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    getWeather();
    }    
  public void setImage(String name) {
        File file = new File (System.getProperty("user.dir") +"\\src\\image\\"+name);
        try {
          imagedes.setImage(new Image(file.toURI().toURL().toExternalForm()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AfficherUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
    public void getWeather(){
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder()
	.url("https://community-open-weather-map.p.rapidapi.com/find?q=Tunis&cnt=2&mode=null&lon=0&type=link%2C%20accurate&lat=0&units=metric")
	.get()
	.addHeader("x-rapidapi-key", "643ea76e0fmshd890ee71291cc92p16d32bjsn8368bbc55737")
	.addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
	.build();
Date date = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("E dd ");  
  String   strDate = formatter.format(date);  
  datef.setText(strDate);
        try {
            Response response = client.newCall(request).execute();
            JSONObject obj = new JSONObject(response.body().string());
                        System.out.println(obj.toString());

            JSONObject obj1 = new JSONObject(obj.getJSONArray("list").getJSONObject(1).get("main").toString());
            JSONObject objwind = new JSONObject(obj.getJSONArray("list").getJSONObject(1).get("wind").toString());
            degree.setText(obj1.get("temp").toString()+" °C");
            vent.setText(String.valueOf(objwind.get("speed"))+" Mph");
            humidity.setText(String.valueOf(obj1.get("humidity").toString()+" g.m-3"));
            JSONObject obj2 = new JSONObject(obj.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).toString());
          
            if (obj2.get("description").toString().toUpperCase().contains("RAIN")){
                  setImage("Rain.png");
            }else if (obj2.get("description").toString().toUpperCase().contains("SUN")){
                setImage("Sunny.png");
            }
            else if (obj2.get("description").toString().toUpperCase().contains("CLOUD")){
    setImage("Sunny cloudy.png");
    
}else  {
                setImage("Sunny.png");
        }
            description.setText(obj2.get("description").toString());
        } catch (IOException ex) {
            Logger.getLogger(AccueilBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void Close(ActionEvent event) {
    }

   
}
