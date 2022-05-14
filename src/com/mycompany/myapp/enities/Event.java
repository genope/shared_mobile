package com.mycompany.myapp.enities;

import java.util.Date;

public class Event {

    private int id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private String image;
    private int nbParticipants;
    private String description;
    private String lieu;

    public Event() {
    }

    public Event(int id, String nom, Date dateDebut, Date dateFin, String image, int nbParticipants, String description, String lieu) {
        this.id = id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.nbParticipants = nbParticipants;
        this.description = description;
        this.lieu = lieu;
    }

    public Event(String nom, Date dateDebut, Date dateFin, String image, int nbParticipants, String description, String lieu) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.nbParticipants = nbParticipants;
        this.description = description;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(int nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }


}