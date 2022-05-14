/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanButton;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.enities.Produits;
import com.mycompany.myapp.services.ProduitService;
import com.mycompany.myapp.services.serviceOffres;
import com.mycompany.myapp.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author yeekt
 */
public class ProduitsListe extends Form{
     ArrayList ListProd =ProduitService.getInstance().getAllProduits();
     private Resources theme;
     private EncodedImage enc;
        private Form current;

     private String url = "http://127.0.0.1/img/";
     
    public ProduitsListe() throws IOException {
        
            
        setTitle("Liste des Produits");
        setScrollableY(true);
        setUIID("Listp");
        
        getToolbar().addCommandToSideMenu("Liste des offres",enc,
                e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToRightBar("back", null, ev->{
            try {
                new homeShared().showBack();
            } catch (IOException ex) {
                System.out.println("");
                }
        });
        getToolbar().addCommandToSideMenu("Liste des produits",enc,
                e->{
           try {
               new ProduitsListe().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
           Button Share=new Button("Share");
        ButtonGroup barGroup = new ButtonGroup();

        RadioButton liste = RadioButton.createToggle("Partager", barGroup);
        liste.setUIID("SelectBar");
        liste.addPointerPressedListener(l->{
        Display.getInstance().execute("https://www.facebook.com/sharer/sharer.php?kid_directed_site=0&sdk=joey&u=http%3A%2F%2F127.0.0.1%3A8000%2Fequipe_mobile&display=popup&ref=plugin&src=share_button");
        });
         for (int i=0;i<ListProd.size();i++){
                Container destinations=new Container(BoxLayout.y());
               
                destinations.getUnselectedStyle().setBorder(Border.createLineBorder(1));
                destinations.getUnselectedStyle().setPadding(50, 50, 250, 250);
              
                Produits produit=(Produits)ListProd.get(i);
                enc = EncodedImage.create("/loading.jpg");
                Image img=URLImage.createToStorage(enc,url+produit.getImage(),url+produit.getImage());
           
                ImageViewer imgv = new ImageViewer(img);
                Label nom=new Label("RefProd: "+produit.getRefProd());
                Label description=new Label("Designation: "+produit.getDesignation());
                Label prix=new Label("Prix: "+produit.getPrix());
                destinations.addAll(nom,description,imgv,prix);
          
             Container modifydestContainer=new Container(BoxLayout.x());
              SpanButton updateButton=new SpanButton("Update");
                 updateButton.getTextAllStyles().setFgColor(0xF37217);
                         updateButton.getAllStyles().setBorder(Border.createEmpty());

//                 updateButton.addActionListener(updatedest->{
//                    new InfiniteProgress().showInifiniteBlocking();
//                    Offres desti=new Offres();
//                    desti.setId(dest.getId());
//                    desti.setNom(dest.getNom());
//                    desti.setDecription(dest.getDecription());
//                    desti.setAdresse(dest.getAdresse());
//                    desti.setEmail(dest.getEmail());
//                    desti.setNumTel(dest.getNumTel());
//                    
//                    new UpdateDestinationForm(desti).show();
//                });
                 
                modifydestContainer.add(updateButton);
                String url=Statics.BASE_URL+"produit/deleteProduit/"+produit.getIdProd();
                SpanButton deleteButton=new SpanButton("Delete");
                 deleteButton.getTextAllStyles().setFgColor(0xFB0000);
                                          deleteButton.getAllStyles().setBorder(Border.createEmpty());

                 deleteButton.addActionListener((delete)->{
                     
                      ConnectionRequest cnxDelteDest = new ConnectionRequest(url);
                      cnxDelteDest.setPost(false);
                      cnxDelteDest.addArgument("id",String.valueOf(produit.getIdProd()));
                    
                       cnxDelteDest.addResponseListener(deleteEvent->{
                     
                   try {
                    Map<String,Object> deleteResult = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(cnxDelteDest.getResponseData()), "UTF-8"));
                       System.out.println(deleteResult.get("response"));
                   if(deleteResult.get("response").equals("Produit Supprimé")){
                  
                    Dialog.show("Deleted", "Produit supprimé","OK","");
                    new ProduitsListe().show();
                         }
                        } catch(Exception err) {
                    Dialog.show("Error", "Error parsing result", "OK","");
                    System.out.println(err);
                      }
                               });
                       
                        NetworkManager.getInstance().addToQueueAndWait(cnxDelteDest);
                 });
               
                 
                modifydestContainer.add(deleteButton);

                
               destinations.add(modifydestContainer);

                add(destinations);
 


         }
            
    }
    
    
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("displaydestes");
        setName("displaydestes");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!

    
}
    

