/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.enities;

import java.util.Date;

/**
 *
 * @author Fatma
 */
public class Publication {
    private int id;
    private int id_guest;
    private String nom;
    private String description;
    private String image;
    private String adresse;
    private int region_id;
    private String datecreation;
    

    public Publication() {
        
    }

    public Publication(int id, int id_guest, String nom, String description, String image, String adresse,int region_id, String datecreation) {
        this.id = id;
        this.id_guest = id_guest;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.adresse = adresse;
        this.region_id = region_id;
        this.datecreation = datecreation;
       
    }

    public Publication(String nom, String description, String image, String adresse, int region_id, String datecreation) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.adresse = adresse;
        this.region_id = region_id;
        this.datecreation = datecreation;
    }

    public Publication(int id_guest, String nom, String description, String image, String adresse, int region_id, String datecreation) {
        this.id_guest = id_guest;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.adresse = adresse;
        this.region_id = region_id;
        this.datecreation = datecreation;
    }
    

    public Publication(int id, int id_guest, String nom, String description, String image, String adresse, int region_id) {
        this.id = id;
        this.id_guest = id_guest;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.adresse = adresse;
        this.region_id = region_id;
    }

    public Publication(String nom, String description, String image, String adresse) {
        
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.adresse = adresse;
        
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_guest() {
        return id_guest;
    }

    public void setId_guest(int id_guest) {
        this.id_guest = id_guest;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }


    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

 
    
     @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", id_guest=" + id_guest + ", nom=" + nom + ", description=" + description + ", image=" + image + ", adresse=" + adresse + ", region_id=" + region_id + ",  datecreation=" + datecreation +  '}';
    }
}
