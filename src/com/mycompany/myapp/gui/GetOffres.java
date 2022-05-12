/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.mycompany.myapp.services.serviceOffres;
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.BubbleTransition;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.enities.Offres;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

/**
 *
 * @author user
 */
public class GetOffres extends Form {       
     ArrayList offres=serviceOffres.getInstance().getAllOffres();
private Resources theme;
private EncodedImage enc;
private Form current;
private String url="http://localhost:80/img/";
    public GetOffres() throws IOException {

        
            
        setTitle("Liste des Destinations");
        setScrollableY(true);
        getToolbar().addCommandToRightBar("back", null, ev->{
            try {
                new homeShared().show();
            } catch (IOException ex) {
                System.out.println("");
            }
        });
      
     //   getToolbar().addCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
        //Button addDestination = new Button("Add Destination");

        //add(addDestination);
        //addDestination.addActionListener(e-> new AddDestinationForm(this).show());

         for (int i=0;i<offres.size();i++){
             
                Container destinations=new Container(BoxLayout.yCenter());
               
                destinations.getUnselectedStyle().setBorder(Border.createLineBorder(2));
            
                destinations.getUnselectedStyle().setMarginTop(50);
                //destinations.setSize(getPreferredSize());
                Offres offre=(Offres)offres.get(i);
                
               
                
               enc = EncodedImage.create("/loading.jpg");
                Image img=URLImage.createToStorage(enc,url+offre.getImage(), url+offre.getImage());
                
                ImageViewer imgv=new ImageViewer(img);
            
                Label nom=new Label("Nom: "+offre.getNom());
                Label description=new Label("Description: "+offre.getDescription());
                Label adresse=new Label("Categorie: "+offre.getCateg());
                Label prix=new Label("Prix: "+offre.getPrix());
                
                
                
                
                
                
                Button showBubble = new Button("View Détails");
showBubble.setName("BubbleButton");
Style buttonStyle = showBubble.getAllStyles();
buttonStyle.setBorder(Border.createEmpty());
buttonStyle.setFgColor(0xffffff);
buttonStyle.setBgPainter((g, rect) -> {
    g.setColor(0xff);
    int actualWidth = rect.getWidth();
    int actualHeight = rect.getHeight();
    int xPos, yPos;
    int size;
    if(actualWidth > actualHeight) {
        yPos = rect.getY();
        xPos = rect.getX() + (actualWidth - actualHeight) / 2;
        size = actualHeight;
    } else {
        yPos = rect.getY() + (actualHeight - actualWidth) / 2;
        xPos = rect.getX();
        size = actualWidth;
    }
    g.setAntiAliased(true);
    g.fillArc(xPos, yPos, size, size, 0, 360);
});
          

showBubble.addActionListener((e) -> {
    Dialog dlg = new Dialog("\n");
    dlg.setLayout(new BorderLayout());
    SpanLabel sl = new SpanLabel(offre.getDescription(), "");
    sl.getTextUnselectedStyle().setFgColor(0xffffff);
    dlg.add(BorderLayout.CENTER, sl);
    dlg.setTransitionInAnimator(new BubbleTransition(500, "BubbleButton"));
    dlg.setTransitionOutAnimator(new BubbleTransition(200, "BubbleButton"));
    dlg.setDisposeWhenPointerOutOfBounds(true);
    dlg.getTitleStyle().setFgColor(0xffffff);

    Style dlgStyle = dlg.getDialogStyle();
    dlgStyle.setBorder(Border.createEmpty());
    dlgStyle.setBgColor(0xff);
    dlgStyle.setBgTransparency(0xff);
    dlg.showPacked(BorderLayout.NORTH, true);
    
    
});




                destinations.addAll(imgv,nom,description,adresse,prix,showBubble);
                
             
          
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
                String url=Statics.BASE_URL+"offres/deleteOffre/"+offre.getId_offre();
                SpanButton deleteButton=new SpanButton("Delete");
                 deleteButton.getTextAllStyles().setFgColor(0xFB0000);
                                          deleteButton.getAllStyles().setBorder(Border.createEmpty());

                 deleteButton.addActionListener((delete)->{
                     
                      ConnectionRequest cnxDelteDest = new ConnectionRequest(url);
                      cnxDelteDest.setPost(false);
                      cnxDelteDest.addArgument("id",String.valueOf(offre.getId_offre()));
                    
                       cnxDelteDest.addResponseListener(deleteEvent->{
                     
                   try {
                    Map<String,Object> deleteResult = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(cnxDelteDest.getResponseData()), "UTF-8"));
          
                   if(deleteResult.get("response").equals("Offre deleted successfully")){
                  
                     Dialog.show("Deleted", "dest Deleted","OK","");
                              new GetOffres().show();
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
 
    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new MyApplication().start());

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
