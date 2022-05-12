/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.enities.Publication;

import com.mycompany.myapp.services.ServiceTask;
import java.io.IOException;

/**
 *
 * @author bhk
 */
public class AddTaskForm extends Form{

    public AddTaskForm() {
        setTitle("Add a new task");
        setLayout(BoxLayout.y());
        
        
        TextField tfnom= new TextField("", "nom");
        TextField tfdescription= new TextField("", "description");
        
        TextField tfadresse= new TextField("", "adresse");
         Label lbImage = new Label();
        Label lbNomImage = new Label();
        Button btnImage = new Button("Image");
        lbNomImage.setVisible(false);
        btnImage.addActionListener((e)->{
            String path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(),-1);
            
        
            if(path!=null){
                try {
                    Image img = Image.createImage(path);
                    lbImage.setIcon(img);
                    lbNomImage.setText(path);
                    refreshTheme();
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Button btnValider = new Button("Ajouter publication");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length()==0)||(tfdescription.getText().length()==0)||(tfadresse.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Publication t = new Publication( tfnom.getText().toString(),tfdescription.getText().toString(),lbNomImage.getText().toString(),tfadresse.getText().toString());
                        if( ServiceTask.getInstance().addTask(t))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfnom,tfdescription,tfadresse,lbImage,lbNomImage,btnImage,btnValider);
  
                
    }
    
    
}
