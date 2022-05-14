package com.mycompany.myapp.gui.front.event;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.mycompany.myapp.enities.Event;
import com.mycompany.myapp.services.EventService;
import com.mycompany.myapp.utils.Statics;

import java.text.SimpleDateFormat;
import java.util.*;

public class ShowEventsFront extends Form {

    Form previous; 
    
    Resources theme = UIManager.initFirstTheme("/theme");
    
    public static Event currentEvent = null;
    

    TextField searchTF;
    ArrayList<Component> componentModels;
    

    public ShowEventsFront(Form previous) {
        super("Events", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Event> listEvents = EventService.getInstance().getAll();
        componentModels = new ArrayList<>();
        
        searchTF = new TextField("", "Chercher event par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Event event : listEvents) {
                if (event.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeEventModel(event);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);
        
        
        if (listEvents.size() > 0) {
            for (Event event : listEvents) {
                Component model = makeEventModel(event);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        
    }
    Label nomLabel   , dateDebutLabel   , dateFinLabel   , imageLabel   , nbParticipantsLabel   , descriptionLabel   , lieuLabel  ;
    
    ImageViewer imageIV;
    

    private Container makeModelWithoutButtons(Event event) {
        Container eventModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        eventModel.setUIID("containerRounded");
        
        
        nomLabel = new Label("Nom : " + event.getNom());
        nomLabel.setUIID("labelDefault");
        
        dateDebutLabel = new Label("DateDebut : " + new SimpleDateFormat("dd-MM-yyyy").format(event.getDateDebut()));
        dateDebutLabel.setUIID("labelDefault");
        
        dateFinLabel = new Label("DateFin : " + new SimpleDateFormat("dd-MM-yyyy").format(event.getDateFin()));
        dateFinLabel.setUIID("labelDefault");
        
        imageLabel = new Label("Image : " + event.getImage());
        imageLabel.setUIID("labelDefault");
        
        nbParticipantsLabel = new Label("NbParticipants : " + event.getNbParticipants());
        nbParticipantsLabel.setUIID("labelDefault");
        
        descriptionLabel = new Label("Description : " + event.getDescription());
        descriptionLabel.setUIID("labelDefault");
        
        lieuLabel = new Label("Lieu : " + event.getLieu());
        lieuLabel.setUIID("labelDefault");
        
        if (event.getImage() != null) {
            String url = Statics.EVENT_IMAGE_URL + event.getImage();
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

        eventModel.addAll(
                imageIV,
                nomLabel, dateDebutLabel, dateFinLabel, nbParticipantsLabel, descriptionLabel, lieuLabel
        );

        return eventModel;
    }
    
    
    Container btnsContainer;

    private Component makeEventModel(Event event) {

        Container eventModel = makeModelWithoutButtons(event);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        
        eventModel.add(btnsContainer);

        return eventModel;
    }
    
}