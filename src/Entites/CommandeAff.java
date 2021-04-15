/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.util.Date;

/**
 *
 * @author user
 */
public class CommandeAff extends Commande {
    String nom_produit ;
    String nom_client ;

    public CommandeAff() {
    }

    public CommandeAff(String nom_produit, String nom_client, Integer id, Date date, Integer quantite, Double prix) {
        super(id, date,0,0, quantite, prix);
        this.nom_produit = nom_produit;
        this.nom_client = nom_client;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }
    
}
