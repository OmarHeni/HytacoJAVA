/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

/**
 *
 * @author Hassene
 */
public class LigneCommande {
        private Produit produit ;
        private Commande commande ;
    private Integer quantite ;
    private Double prix ;

    public LigneCommande(Produit produit, Commande commande, Integer quantite, Double prix) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

 

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommande{" + "produit=" + produit + ", commande=" + commande + ", quantite=" + quantite + ", prix=" + prix + '}';
    }

   

}