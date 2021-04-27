/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import projet.models.Transporteur;
import projet.service.TransporteurServices;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;



/**
 * FXML Controller class
 *
 * @author Med Amine
 */
public class TransporteurfrontController implements Initializable {

    @FXML
    private TextField txttype;
    @FXML
    private Button btnajout;
    private TableView<Transporteur> tv;
    private TableColumn<Transporteur, Integer> colid;
    private TableColumn<Transporteur, String> coltype;
     private TableColumn<Transporteur, Integer> colnumero;
    private TableColumn<Transporteur, String> colnom;
    private TableColumn<Transporteur, String> coladresse;
    private TableColumn<Transporteur, String> colmail;
    private TextField txtdescprition;
    private TextField txtemail;
    @FXML
    private TextField txtnumero;
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtmail;
    @FXML
    private TextField txtadresse;
    private AnchorPane rootPane;
    @FXML
    private Button closeb;
    @FXML
    private Label Programmes1;
    @FXML
    private Label Categories1;
    @FXML
    private Label Evenements1;
    @FXML
    private Label Locaux1;
    @FXML
    private Label txttypee;
    @FXML
    private Label Panier1;
    @FXML
    private Label Deconnexion1;
    @FXML
    private AnchorPane brand;
    @FXML
    private ImageView emailCheckMark;
    Notifications n;
        String erreur;
    @FXML
    private ImageView telCheckMark;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] possibleWords = {"bus1","bus2","bus3"};
        TextFields.bindAutoCompletion(txttype, possibleWords);
    }    

    @FXML
    private void addtransporteur(ActionEvent event) {
         if(txttype.getText().isEmpty()||txtnumero.getText().isEmpty()||txtnom.getText().isEmpty()||txtadresse.getText().isEmpty()||txtmail.getText().isEmpty()|| !testSaisie())
        {
            JOptionPane.showMessageDialog(null, "verifer les champs");  
        }
        else
        {
        String type = txttype.getText();
        int numero = Integer.parseInt(txtnumero.getText());
         String nom = txtnom.getText();
         String adresse = txtadresse.getText();
         String mail = txtmail.getText();
        Transporteur t = new Transporteur(5,type,numero,nom,adresse,mail);
        TransporteurServices sc = new TransporteurServices();
        sc.ajouterTransporteur(t);
                    JOptionPane.showMessageDialog(null, "ajout avec succes");
        txttype.clear();
        txtnumero.clear();
        txtnom.clear();
        txtadresse.clear();
        txtmail.clear();    
       
         ArrayList<Transporteur> publiciteList =  (ArrayList<Transporteur>) sc.getTransporteur() ; 
    ObservableList<Transporteur> data = FXCollections.observableArrayList(publiciteList);
    
    tv.setItems(data);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colnumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
         }
    }

    @FXML
    private void Close(ActionEvent event) {
    }

    

    @FXML
    private void loadscreen(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/reclamationfront.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void load(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/tansback.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Boolean testMail() {

          String emailRegex;
       
                
        emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pat = Pattern.compile(emailRegex);
        if (txtmail.getText() == null) {
            return false;
        }

        if (pat.matcher(txtmail.getText()).matches() == false) {
            emailCheckMark.setImage(new Image("image/erreurCheckMark.png"));
//                erreur = erreur + ("Veuillez verifier votre adresse email\n");
            return false;
//

        } else {
            emailCheckMark.setImage(new Image("image/checkMark.png"));
        }
        return true;

    }
    private Boolean testSaisie() {
        erreur = "";
        if (!testMail()) {
            erreur = erreur + ("Veuillez verifier que votre adresse email est de la forme : ***@***.** \n");
        }
        
        else if 
                (!testTel()) {
            erreur = erreur + ("Veuillez verifier que votre numero est compris 8 chiffres \n");
        }
        
        if ( !testMail() || !testTel () ) {
            n = Notifications.create()
                    .title("Erreur de format")
                    .text(erreur)
                    .graphic(null)
                    .position(Pos.TOP_CENTER);
                    //.hideAfter(Duration.ofSeconds(6));
            n.showInformation();
        }

        return  testMail() && testTel() ;
    }
    
    private Boolean testTel() {
        if (txtnumero.getText().trim().length() == 8) {
            int nbChar = 0;
            for (int i = 1; i < txtnumero.getText().trim().length(); i++) {
                char ch = txtnumero.getText().charAt(i);
                if (Character.isLetter(ch)) {
                    nbChar++;
                }
            }

            if (nbChar == 0) {
                telCheckMark.setImage(new Image("image/checkMark.png"));
                return true;
            } else {
                telCheckMark.setImage(new Image("image/erreurcheckMark.png"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
                return false;

            }
        } else if (txtnumero.getText().trim().length() != 8) {
//            erreur = erreur + ("Il faut saisir 8 chiffres dans le numéro de telephone\n");
            telCheckMark.setImage(new Image("image/erreurcheckMark.png"));
            return false;
        }
             return true;

    }

    @FXML
    private void accueil(ActionEvent event) {
    }
    
}
