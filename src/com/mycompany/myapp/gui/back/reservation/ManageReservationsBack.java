package com.mycompany.myapp.gui.back.reservation;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.enities.Event;
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.enities.Reservation;
import com.mycompany.myapp.enities.User;
import com.mycompany.myapp.services.EventService;
import com.mycompany.myapp.services.OffreService;
import com.mycompany.myapp.services.ReservationService;
import com.mycompany.myapp.services.UserService;
import com.mycompany.myapp.utils.AlertUtils;

import java.util.*;

public class ManageReservationsBack extends Form {

    Reservation currentReservation;

    PickerComponent datedebutTF;
    PickerComponent datefinTF;

    ArrayList<User> listUsers;
    PickerComponent guestPC;
    User selectedUser = null;
    ArrayList<Event> listEvents;
    PickerComponent eventPC;
    Event selectedEvent = null;
    ArrayList<Offres> listOffress;
    PickerComponent offrePC;
    Offres selectedOffres = null;

    Button manageButton;

    Form previous;

    public ManageReservationsBack(Form previous) {
        super(ShowReservationsBack.currentReservation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReservation = ShowReservationsBack.currentReservation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] guestStrings;
        int guestIndex;
        guestPC = PickerComponent.createStrings("").label("User");
        listUsers = UserService.getInstance().getAll();
        guestStrings = new String[listUsers.size()];
        guestIndex = 0;
        for (User guest : listUsers) {
            guestStrings[guestIndex] = guest.getNom();
            guestIndex++;
        }
        if (listUsers.size() > 0) {
            guestPC.getPicker().setStrings(guestStrings);
            guestPC.getPicker().addActionListener(l -> selectedUser = listUsers.get(guestPC.getPicker().getSelectedStringIndex()));
        } else {
            guestPC.getPicker().setStrings("");
        }

        String[] eventStrings;
        int eventIndex;
        eventPC = PickerComponent.createStrings("").label("Event");
        listEvents = EventService.getInstance().getAll();
        eventStrings = new String[listEvents.size()];
        eventIndex = 0;
        for (Event event : listEvents) {
            eventStrings[eventIndex] = event.getNom();
            eventIndex++;
        }
        if (listEvents.size() > 0) {
            eventPC.getPicker().setStrings(eventStrings);
            eventPC.getPicker().addActionListener(l -> selectedEvent = listEvents.get(eventPC.getPicker().getSelectedStringIndex()));
        } else {
            eventPC.getPicker().setStrings("");
        }

        String[] offreStrings;
        int offreIndex;
        offrePC = PickerComponent.createStrings("").label("Offres");
        listOffress = OffreService.getInstance().getAll();
        offreStrings = new String[listOffress.size()];
        offreIndex = 0;
        for (Offres offre : listOffress) {
            offreStrings[offreIndex] = offre.getNom();
            offreIndex++;
        }
        if (listOffress.size() > 0) {
            offrePC.getPicker().setStrings(offreStrings);
            offrePC.getPicker().addActionListener(l -> selectedOffres = listOffress.get(offrePC.getPicker().getSelectedStringIndex()));
        } else {
            offrePC.getPicker().setStrings("");
        }

        datedebutTF = PickerComponent.createDate(null).label("Datedebut");

        datefinTF = PickerComponent.createDate(null).label("Datefin");

        if (currentReservation == null) {

            manageButton = new Button("Ajouter");
        } else {

            datedebutTF.getPicker().setDate(currentReservation.getDatedebut());
            datefinTF.getPicker().setDate(currentReservation.getDatefin());

            guestPC.getPicker().setSelectedString(currentReservation.getUser().getNom());
            selectedUser = currentReservation.getUser();
            eventPC.getPicker().setSelectedString(currentReservation.getEvent().getNom());
            selectedEvent = currentReservation.getEvent();
            offrePC.getPicker().setSelectedString(currentReservation.getOffres().getNom());
            selectedOffres = currentReservation.getOffres();

            manageButton = new Button("Modifier");
        }

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                datedebutTF,
                datefinTF,
                guestPC, eventPC, offrePC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentReservation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().add(
                            new Reservation(
                                    selectedUser,
                                    selectedEvent,
                                    selectedOffres,
                                    datedebutTF.getPicker().getDate(),
                                    datefinTF.getPicker().getDate()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReservationService.getInstance().edit(
                            new Reservation(
                                    currentReservation.getId(),
                                    selectedUser,
                                    selectedEvent,
                                    selectedOffres,
                                    datedebutTF.getPicker().getDate(),
                                    datefinTF.getPicker().getDate()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Reservation modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowReservationsBack) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (datedebutTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la datedebut", new Command("Ok"));
            return false;
        }

        if (datefinTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la datefin", new Command("Ok"));
            return false;
        }

        if (selectedUser == null) {
            Dialog.show("Avertissement", "Veuillez choisir un guest", new Command("Ok"));
            return false;
        }

        if (selectedEvent == null) {
            Dialog.show("Avertissement", "Veuillez choisir un event", new Command("Ok"));
            return false;
        }

        if (selectedOffres == null) {
            Dialog.show("Avertissement", "Veuillez choisir un offre", new Command("Ok"));
            return false;
        }

        return true;
    }
}
