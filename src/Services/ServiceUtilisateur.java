/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Utilisateur;
import Utils.MaConnexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author user
 */
public class ServiceUtilisateur {
    Connection cnx ;

    public ServiceUtilisateur() {
        this.cnx = MaConnexion.getInstance().getCnx();
    }
    
      public  int Inscription(Utilisateur utilisateur) {
        // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value en regarde le cryptage de fosuserbundle il utilise $2y$13$.
        int workload = 13;
        int status = 0;
        String sql = "INSERT INTO utilisateur(nom,password,email,roles,prenom,adresse,telephone,image_name) VALUES(?,?,?,?,?,?,?,?)";

        try {

            
          PreparedStatement  preparedStatement = cnx.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            String mdp = BCrypt.hashpw(utilisateur.getPassword(), BCrypt.gensalt(workload));
            preparedStatement.setString(2, mdp.replaceFirst("2a", "2y"));
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, "[\"ROLE_CLIENT\"]");
            preparedStatement.setString(5, utilisateur.getPrenom());
            preparedStatement.setString(6, utilisateur.getAdresse());
            preparedStatement.setInt(7, utilisateur.getTelephone());
            preparedStatement.setString(8, utilisateur.getImage_name());

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
      
       public  int ModifierUtilisateur(Utilisateur utilisateur) {
        // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value en regarde le cryptage de fosuserbundle il utilise $2y$13$.
        int workload = 13;
        int status = 0;
        System.out.println(utilisateur.toString());
        String sql = "UPDATE utilisateur SET nom =?,email=?,prenom=?,adresse=?,telephone=?,image_name=? WHERE id=?";

        try {
            PreparedStatement  preparedStatement = cnx.prepareStatement(sql);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getPrenom());
            preparedStatement.setString(4, utilisateur.getAdresse());
            preparedStatement.setInt(5, utilisateur.getTelephone());
             preparedStatement.setString(6, utilisateur.getImage_name());
            preparedStatement.setInt(7, utilisateur.getId());

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public  boolean testMotDePasse(String motDePasseGUI, String motDePasseBD) {
        boolean password_verified = false;

        if (null == motDePasseBD) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        // en remplaçant 2y par 2a le cryptage on obtient le cryptage par defaut pour que la methode checkpw puisse comparer
        password_verified = BCrypt.checkpw(motDePasseGUI, motDePasseBD.replaceFirst("2y", "2a"));

        return (password_verified);
    }

    public  List<Utilisateur> getTtUtilisateur() throws IOException {
        List<Utilisateur> list = new ArrayList<Utilisateur>();
        try {
            String sql = "select * from Utilisateur ";
            PreparedStatement preparedStatement = (PreparedStatement) cnx.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                             
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setAdresse(resultSet.getString("adresse"));
                utilisateur.setTelephone(resultSet.getInt("telephone"));
                utilisateur.setImage_name(resultSet.getString("image_name"));
               
ObjectMapper mapper = new ObjectMapper();

List<String> rolesList = new ArrayList<>();

rolesList = Arrays.asList(mapper.readValue(resultSet.getString("roles"), String[].class));
   Set<String>  rolesSet =  rolesList.stream().collect(Collectors.toSet());
utilisateur.setRoles(rolesSet);
                list.add(utilisateur);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;

    }

    public  Utilisateur getUtilisateur(String nomUtilisateur) {
        Utilisateur utilisateur = null;
        try {
            String sql = "select * from utilisateur where nom = ?";
            PreparedStatement preparedStatement =  cnx.prepareStatement(sql);
            preparedStatement.setString(1, nomUtilisateur);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setAdresse(resultSet.getString("adresse"));
                utilisateur.setTelephone(resultSet.getInt("telephone"));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return utilisateur;
    }
     public  int SupprimerUtilisateur(Utilisateur utilisateur) {
  
         
        int status = 0;
        String sql = "DELETE FROM utilisateur where id = ?";
        try {
          PreparedStatement  preparedStatement = cnx.prepareStatement(sql);
          preparedStatement.setInt(1, utilisateur.getId());
        status = preparedStatement.executeUpdate();
 } catch (SQLException e) {
            e.printStackTrace();
        }
        return status ;
     }
}
