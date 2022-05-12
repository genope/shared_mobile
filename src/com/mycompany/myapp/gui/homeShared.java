/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.mycompany.myapp.gui.GetOffres;
import java.io.IOException;

/**
 *
 * @author user
 */
public class homeShared extends Form{
    Form current;
    private Resources theme;
         private EncodedImage enc;

      public homeShared() throws IOException {
         
        setTitle("Bienvenue chez shared");
        setScrollableY(true);
        setUIID("HOMEPAGE");
        
        
        getToolbar().addCommandToSideMenu("Liste des offres", null, e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToSideMenu("Liste des produits", null, e->{
           try {
               new ProduitsListe().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToSideMenu("Ajouter Produit", null, e->{
           try {
               new ProduitAjout().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
        });
        getToolbar().addCommandToSideMenu("Ajouter Offre", null, e->{
            new AddOffres().show();
        });
        getToolbar().addCommandToSideMenu("Login", null, e->{
            new SignInForm().show();
        });
        getToolbar().addCommandToSideMenu("Profile", null, e->{
            new ProfileForm().show();
            
        });
        getToolbar().addCommandToSideMenu("Logout", null, e->{
            new SignInForm().show();
        SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
            System.out.println(SessionManager.getEmail());   
            
        });
        
        
        
   
        
        enc = EncodedImage.create("/shared.png");
                Image img=URLImage.createToStorage(enc,"http://localhost/img/shared.png","http://localhost/img/shared.png");
           
                ImageViewer imgv = new ImageViewer(img);
        
        add(imgv);
}
}