package com.mycompany.myapp.gui.back.event;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.enities.Event;
import com.mycompany.myapp.services.EventService;
import com.mycompany.myapp.utils.AlertUtils;
import com.mycompany.myapp.utils.Statics;

import java.io.IOException;

public class ManageEventsBack extends Form {

    
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;
    

    Event currentEvent;

    TextField nomTF;TextField imageTF;TextField nbParticipantsTF;TextField descriptionTF;TextField lieuTF;
    Label nomLabel;Label imageLabel;Label nbParticipantsLabel;Label descriptionLabel;Label lieuLabel;
    PickerComponent dateDebutTF;PickerComponent dateFinTF;
    
    
    
    ImageViewer imageIV;
    Button selectImageButton;
    
    Button manageButton;

    Form previous;

    public ManageEventsBack(Form previous) {
        super(ShowEventsBack.currentEvent == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentEvent = ShowEventsBack.currentEvent;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        
        
        dateDebutTF = PickerComponent.createDate(null).label("DateDebut");
        
        
        dateFinTF = PickerComponent.createDate(null).label("DateFin");
        
        
        
        
        
        
        
        nbParticipantsLabel = new Label("NbParticipants : ");
        nbParticipantsLabel.setUIID("labelDefault");
        nbParticipantsTF = new TextField();
        nbParticipantsTF.setHint("Tapez le nbParticipants");
        
        
        
        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");
        
        
        
        lieuLabel = new Label("Lieu : ");
        lieuLabel.setUIID("labelDefault");
        lieuTF = new TextField();
        lieuTF.setHint("Tapez le lieu");
        
        
        
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentEvent == null) {
            
            imageIV = new ImageViewer(theme.getImage("FileChooser.png").fill(1100, 500));
            
            
            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentEvent.getNom());
            dateDebutTF.getPicker().setDate(currentEvent.getDateDebut());
            dateFinTF.getPicker().setDate(currentEvent.getDateFin());
            
            nbParticipantsTF.setText(String.valueOf(currentEvent.getNbParticipants()));
            descriptionTF.setText(currentEvent.getDescription());
            lieuTF.setText(currentEvent.getLieu());
            
            
            
            if (currentEvent.getImage() != null) {
                selectedImage = currentEvent.getImage();
                String url = Statics.EVENT_IMAGE_URL + currentEvent.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("FileChooser.png").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("FileChooser.png").fill(1100, 500));
            }
            imageIV.setFocusable(false);
            

            manageButton = new Button("Modifier");
        }

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            imageLabel, imageIV,
            selectImageButton,
            nomLabel, nomTF,
            dateDebutTF,
            dateFinTF,
            
            nbParticipantsLabel, nbParticipantsTF,
            descriptionLabel, descriptionTF,
            lieuLabel, lieuTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });
        
        if (currentEvent == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = EventService.getInstance().add(
                            new Event(
                                    
                                    
                                    nomTF.getText(),
                                    dateDebutTF.getPicker().getDate(),
                                    dateFinTF.getPicker().getDate(),
                                    selectedImage,
                                    (int) Float.parseFloat(nbParticipantsTF.getText()),
                                    descriptionTF.getText(),
                                    lieuTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Event ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de event. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = EventService.getInstance().edit(
                            new Event(
                                    currentEvent.getId(),
                                    
                                    
                                    nomTF.getText(),
                                    dateDebutTF.getPicker().getDate(),
                                    dateFinTF.getPicker().getDate(),
                                    selectedImage,
                                    (int) Float.parseFloat(nbParticipantsTF.getText()),
                                    descriptionTF.getText(),
                                    lieuTF.getText()

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Event modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de event. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((ShowEventsBack) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (dateDebutTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateDebut", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (dateFinTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la dateFin", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        if (nbParticipantsTF.getText().equals("")) {
            Dialog.show("Avertissement", "NbParticipants vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nbParticipantsTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nbParticipantsTF.getText() + " n'est pas un nombre valide (nbParticipants)", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (lieuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Lieu vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }
        
             
        return true;
    }
}