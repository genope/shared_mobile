/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.mycompany.myapp.enities.Publication;

import com.mycompany.myapp.services.ServiceTask;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ListTasksForm extends Form {
private String url="http://localhost:80/img/";
private EncodedImage enc;
    public ListTasksForm() throws IOException {
        setTitle("Liste des publications");

//        SpanLabel sp = new SpanLabel();
//        sp.setText(ServiceTask.getInstance().getAllTasks().toString());
//        add(sp);
        ArrayList<Publication> list = ServiceTask.getInstance().getAllTasks();

        for (Publication p : list) {
     
            MultiButton fourLinesIcon = new MultiButton(p.getNom());
            fourLinesIcon.setTextLine2("Description : " + p.getDescription()+ "\n");
            fourLinesIcon.setTextLine3("Date creation : " + p.getDatecreation()+ "\n");
            fourLinesIcon.setTextLine4("Adresse : " + p.getAdresse()+ "\n");

            enc = EncodedImage.create("/loading.jpg");
                Image img=URLImage.createToStorage(enc,url+p.getImage(), url+p.getImage());
                
                ImageViewer imgv=new ImageViewer(img);
            //supprimer button
            Button btnSuppub = new Button("Supprimer");
            btnSuppub.setUIID("LoginButton");
            
         
            
            //click sup button
           btnSuppub.addPointerPressedListener(l -> {

                Dialog dig = new Dialog("Suppression");
                Dialog digf = new Dialog("Publication");

                if (dig.show("Suppression", "Vous voulez supprimer cet Publication ?", "Annuler", "Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();
                }
                if (ServiceTask.getInstance().deletepub(p.getId())) {
                    digf.show("Publication","supprimé",new Command("OK"));
                    dig.dispose();

                }
                //current.show();

            });
            
            addAll(imgv,fourLinesIcon,btnSuppub);
        }

        

  
    }

}
