/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ToastBar;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout.Constraint;
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.services.serviceOffres;
import java.io.IOException;


/**
 *
 * @author user
 */
public class AddOffres extends Form{
           private EncodedImage enc;
   private Form current;
     public AddOffres() {
         getToolbar().addCommandToSideMenu("Liste des offres",enc,
                e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToRightBar("back", null, ev->{
             new homeShared().showBack();
        });
        getToolbar().addCommandToSideMenu("Liste des produits",enc,
                e->{
           try {
               new ProduitsListe().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
       
        setTitle("Add Offre");
        setLayout(BoxLayout.y());
        
        
        
        TextField nom = new TextField("","Nom");
        TextField description= new TextField("", "Description");
        TextField prix= new TextField("", "Prix");
      
        TextField ville= new TextField("", "Ville");
        ComboBox<String> combo= new ComboBox<>("Chambre","Maison","Appartement","Voiture","Moto","Vélo");
       
         
               
//           hhhh.addActionListener((evt) -> {
//                 Display.getInstance().openGallery(new ActionListener() {
//                    public void actionPerformed(ActionEvent ev) {
//                   if(ev != null && ev.getSource() != null) {
//                       String filePathToGalleryImageOrVideo = (String)ev.getSource();
//                       System.out.println(filePathToGalleryImageOrVideo);
//           
//        }
//    }z
//}, Display.GALLERY_ALL);
//           }
//           );
       
                
        Button btnValider = new Button("Ajouter");
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length()==0)||(description.getText().length()==0)||(prix.getText().length()==0)||(ville.getText().length()==0))
                    Dialog.show("Alert", "Merci de renseigner tout les champs","OK","");
                else
                {
                    Offres of= new Offres(nom.getText(), description.getText(),ville.getText(), combo.getSelectedItem(),Float.parseFloat(prix.getText()));
                    System.out.println(ville.getText());
                            System.out.println("hhhh");
                             System.out.println(of);
                        if( serviceOffres.getInstance().addDestination(of))
                        {
                                ToastBar.Status status = ToastBar.getInstance().createStatus();
                                status.setMessage("Offres Ajouté");
                                status.setExpires(5000);  
                                status.show();
                       
                        try {
                            new GetOffres().show();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        }else
                            Dialog.show("ERROR", "Server error", "OK","");
                    
                    
                }
                
                
            }
        });

         
    // Create a form and show it.
        addAll(nom,description,prix,ville,combo,btnValider);
    
                
    }
     

    
    
}
