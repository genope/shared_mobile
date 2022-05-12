/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author user
 */
public class Reclamation {
    private int id,idUser;
    private String type,objet,description,statut,email,image,nom,prenom,vocal;
    private Date dateCreation,dateTraitement;

    public Reclamation(int id, String type, String objet, String description) {
        this.id = id;
        this.type = type;
        this.objet = objet;
        this.description = description;
    }

    public Reclamation(String type, String objet, String description) {
        this.type = type;
        this.objet = objet;
        this.description = description;
    }

    public Reclamation(String type, String objet, String description, String email,String image, String nom, String prenom) {
        this.type = type;
        this.objet = objet;
        this.description = description;
        this.email = email;
        this.image= image;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Reclamation(int id, int idUser, String type, String objet, String description, String statut, String email, String image, String nom, String prenom, String vocal, Date dateCreation, Date dateTraitement) {
        this.id = id;
        this.idUser = idUser;
        this.type = type;
        this.objet = objet;
        this.description = description;
        this.statut = "EnAttente";
        this.email = email;
        this.image = image;
        this.nom = nom;
        this.prenom = prenom;
        this.vocal = vocal;
        this.dateCreation = dateCreation;
        this.dateTraitement = dateTraitement;
    }

    public Reclamation(int idUser, String type, String objet, String description, String statut, String email, String image, String nom, String prenom, String vocal, Date dateCreation, Date dateTraitement) {
        this.idUser = idUser;
        this.type = type;
        this.objet = objet;
        this.description = description;
        this.statut = "EnAttente";
        this.email = email;
        this.image = image;
        this.nom = nom;
        this.prenom = prenom;
        this.vocal = vocal;
        this.dateCreation = dateCreation;
        this.dateTraitement = dateTraitement;
    }

    public Reclamation(int idUser, String type, String objet, String description, String email, String nom, String prenom) {
        this.idUser = idUser;
        this.type = type;
        this.objet = objet;
        this.description = description;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.statut = "EnAttente";
        
    }

    public Reclamation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVocal() {
        return vocal;
    }

    public void setVocal(String vocal) {
        this.vocal = vocal;
    }

    public Date getDateCreation() {
    return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(Date dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id +  ", type=" + type + ", objet=" + objet + ", description=" + description +'}';
    }
     
    
}
