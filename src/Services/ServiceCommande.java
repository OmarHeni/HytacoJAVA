/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Commande;
import Entites.CommandeAff;
import Utils.MaConnexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author user
 */
public class ServiceCommande {
      Connection cnx ;

    public ServiceCommande() {
        this.cnx = MaConnexion.getInstance().getCnx();
    }

     public  List<CommandeAff> getTCommandes() throws IOException {
        List<CommandeAff> list = new ArrayList<CommandeAff>();
        try {
            String sql = "select c.id as id, c.date_commande as date,c.quantite as quantite,c.prix as prix,cl.nom as cnom"
                    + ", p.nom as pnom "
                    + " from Commande c JOIN Utilisateur cl on (c.utilisateur_id=cl.id) "
                    + "JOIN Produits p on (c.produit_id=p.id) ";
            PreparedStatement preparedStatement = (PreparedStatement) cnx.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                             
                CommandeAff commande = new CommandeAff(resultSet.getString("pnom"),resultSet.getString("cnom"),resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getInt("quantite"),resultSet.getDouble("prix"));
        

                list.add(commande);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;

    }
     public  int SupprimerCommandes(CommandeAff commande) {
  
         
        int status = 0;
        String sql = "DELETE FROM commande where id = ?";
        try {
          PreparedStatement  preparedStatement = cnx.prepareStatement(sql);
          preparedStatement.setInt(1, commande.getId());
        status = preparedStatement.executeUpdate();
 } catch (SQLException e) {
            e.printStackTrace();
        }
        return status ;
     }
          public  int AjouterCommande(Commande commande) {
                      int status = 0;
        String sql = "INSERT INTO Commande(utilisateur_id,date_commande,produit_id,quantite,prix) VALUES(?,?,?,?,?)";
         try {
          PreparedStatement  preparedStatement = cnx.prepareStatement(sql);
          preparedStatement.setInt(1, commande.getIdclient());
          preparedStatement.setDate(2, new java.sql.Date(commande.getDate().getTime()) );
          preparedStatement.setInt(3, commande.getIdproduit());
          preparedStatement.setInt(4, commande.getQuantite());
          preparedStatement.setDouble(5,commande.getPrix());
          status = preparedStatement.executeUpdate();
 } catch (SQLException e) {
            e.printStackTrace();
        }
        return status ;

          }
}
