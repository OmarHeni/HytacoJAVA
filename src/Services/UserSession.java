/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Produit;
import Entites.Utilisateur;
import java.util.HashMap;

/**
 *
 * @author user
 */

        public final class UserSession {

    private static UserSession instance;

    private Utilisateur utilisateur ;
    private HashMap<Produit,Integer> panier ;

    public UserSession(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.panier =  new HashMap<Produit,Integer>();
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

   public void AddLignePanier(Produit produit){
       panier.put(produit,1);
   }
     public void RemoveLignePanier(Produit produit){
       panier.remove(produit);
   }
     public void SetLignePanier(Produit produit,Integer quantite){
       panier.put(produit,quantite);
   }

    public HashMap<Produit, Integer> getPanier() {
        return panier;
    }

    public static UserSession getInstace() {
        return instance;
    }
    public static void setInstace(Utilisateur utilisateur) {
            instance = new UserSession(utilisateur);
        
    }
    
  
}
