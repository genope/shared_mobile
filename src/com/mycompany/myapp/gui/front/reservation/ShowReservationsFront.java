package com.mycompany.myapp.gui.front.reservation;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.enities.Reservation;
import com.mycompany.myapp.services.ReservationService;
import com.mycompany.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ShowReservationsFront extends Form {

    Form previous; 
    
    public static Reservation currentReservation = null;
    Button addBtn;

    
    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public ShowReservationsFront(Form previous) {
        super("Reservations", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        this.add(addBtn);
        

        ArrayList<Reservation> listReservations = ReservationService.getInstance().getAll();
        
        componentModels = new ArrayList<>();
        
        sortPicker = PickerComponent.createStrings("Guest", "Event", "Offre", "Datedebut", "Datefin").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listReservations);
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);
        
        if (listReservations.size() > 0) {
            for (Reservation reservation : listReservations) {
                Component model = makeReservationModel(reservation);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReservation = null;
            new ManageReservationsFront(this).show();
        });
        
    }
    Label guestLabel   , eventLabel   , offreLabel   , datedebutLabel   , datefinLabel  ;
    

    private Container makeModelWithoutButtons(Reservation reservation) {
        Container reservationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reservationModel.setUIID("containerRounded");
        
        
        guestLabel = new Label("Guest : " + reservation.getUser());
        guestLabel.setUIID("labelDefault");
        
        eventLabel = new Label("Event : " + reservation.getEvent());
        eventLabel.setUIID("labelDefault");
        
        offreLabel = new Label("Offre : " + reservation.getOffres());
        offreLabel.setUIID("labelDefault");
        
        datedebutLabel = new Label("Datedebut : " + new SimpleDateFormat("dd-MM-yyyy").format(reservation.getDatedebut()));
        datedebutLabel.setUIID("labelDefault");
        
        datefinLabel = new Label("Datefin : " + new SimpleDateFormat("dd-MM-yyyy").format(reservation.getDatefin()));
        datefinLabel.setUIID("labelDefault");
        
        guestLabel = new Label("Guest : " + reservation.getUser().getNom());
        guestLabel.setUIID("labelDefault");
        
        eventLabel = new Label("Event : " + reservation.getEvent().getNom());
        eventLabel.setUIID("labelDefault");
        
        offreLabel = new Label("Offre : " + reservation.getOffres().getNom());
        offreLabel.setUIID("labelDefault");
        

        reservationModel.addAll(
                
                guestLabel, eventLabel, offreLabel, datedebutLabel, datefinLabel
        );

        return reservationModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeReservationModel(Reservation reservation) {

        Container reservationModel = makeModelWithoutButtons(reservation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.addActionListener(action -> {
            currentReservation = reservation;
            new ManageReservationsFront(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reservation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReservationService.getInstance().delete(reservation.getId());

                if (responseCode == 200) {
                    currentReservation = null;
                    dlg.dispose();
                    reservationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reservation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        reservationModel.add(btnsContainer);

        return reservationModel;
    }
    
}