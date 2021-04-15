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
public class Commande  {
    private Integer id ;
    private Date date ;
    private Integer idclient ;
    private Integer idproduit ;
    private Integer quantite ;
    private Double prix;

    public Commande() {
    }

    public Commande(Date date, Integer idclient, Integer idproduit, Integer quantite, Double prix) {
        this.date = date;
        this.idclient = idclient;
        this.idproduit = idproduit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Commande(Integer id, Date date, Integer idclient, Integer idproduit, Integer quantite, Double prix) {
        this.id = id;
        this.date = date;
        this.idclient = idclient;
        this.idproduit = idproduit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIdclient() {
        return idclient;
    }

    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
    }

    public Integer getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(Integer idproduit) {
        this.idproduit = idproduit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
}
