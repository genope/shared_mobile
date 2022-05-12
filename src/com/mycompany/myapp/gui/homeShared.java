/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

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
import com.codename1.ui.Toolbar;
import com.mycompany.myapp.gui.GetOffres;
import java.io.IOException;

/**
 *
 * @author user
 */
public class homeShared extends Form{
    Form current;
    private Resources theme;
      public homeShared() {
         
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
   
}
}