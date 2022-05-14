package com.mycompany.myapp.enities;

import com.mycompany.myapp.utils.DateUtils;
import com.mycompany.myapp.utils.Statics;

import java.util.Date;

public class Reservation implements Comparable<Reservation> {

    private int id;
    private User guest;
    private Event event;
    private Offres offre;
    private Date datedebut;
    private Date datefin;

    public Reservation() {
    }

    public Reservation(int id, User guest, Event event, Offres offre, Date datedebut, Date datefin) {
        this.id = id;
        this.guest = guest;
        this.event = event;
        this.offre = offre;
        this.datedebut = datedebut;
        this.datefin = datefin;
    }

    public Reservation(User guest, Event event, Offres offre, Date datedebut, Date datefin) {
        this.guest = guest;
        this.event = event;
        this.offre = offre;
        this.datedebut = datedebut;
        this.datefin = datefin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return guest;
    }

    public void setUser(User guest) {
        this.guest = guest;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Offres getOffres() {
        return offre;
    }

    public void setOffres(Offres offre) {
        this.offre = offre;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }


    @Override
    public int compareTo(Reservation reservation) {
        switch (Statics.compareVar) {
            case "User":
                return this.getUser().getNom().compareTo(reservation.getUser().getNom());
            case "Event":
                return this.getEvent().getNom().compareTo(reservation.getEvent().getNom());
            case "Offres":
                return this.getOffres().getNom().compareTo(reservation.getOffres().getNom());
            case "Datedebut":
                DateUtils.compareDates(this.getDatedebut(), reservation.getDatedebut());
            case "Datefin":
                DateUtils.compareDates(this.getDatefin(), reservation.getDatefin());

            default:
                return 0;
        }
    }

}