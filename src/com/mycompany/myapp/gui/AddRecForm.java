/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ReclamationService;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;


import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author user
 */
public class AddRecForm extends Form {
 
    public AddRecForm() {
        setTitle("Ajouter une réclamation");
        setLayout(BoxLayout.y());
        TextField tfType = new TextField("","Type",10,TextField.ANY);
        String pathImg = "";
        TextField tfObjet = new TextField("","Objet",10,TextField.ANY);
        TextField tfDescription = new TextField("","Description",20,TextField.ANY);
        tfDescription.setSingleLineTextArea(false);
        TextField tfEmail = new TextField("","Email",10,TextField.EMAILADDR);
        TextField tfNom = new TextField("","Nom",10,TextField.ANY);
        TextField tfPrenom = new TextField("","Prenom",10,TextField.ANY); 
        Button btnImage = new Button("Image");
        Label lbImage = new Label();
        Label lbNomImage = new Label();
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
        Button btnValider = new Button("Ajouter");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if((tfType.getText().length()==0)||(tfObjet.getText().length()==0)||(tfDescription.getText().length()==0)||(tfEmail.getText().length()==0)||(tfNom.getText().length()==0)||(tfPrenom.getText().length()==0))
                    Dialog.show("Alerte","Veuillez remplir tous les champs",new Command("OK"));
                else{
                    try {
                        InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Reclamation r = new Reclamation(tfType.getText(),tfObjet.getText(),tfDescription.getText(),tfEmail.getText(),lbNomImage.getText(),tfNom.getText(),tfPrenom.getText());
                    System.out.println(r);
                    if (ReclamationService.getInstance().AddRec(r)){
                       Dialog.show("Succès","La réclamation a été ajoutée.",new Command("OK"));
                        sendMail( r.getEmail());
                    }
                    iDialog.dispose();
                    
                   
                }catch (NumberFormatException e){
                    Dialog.show("Erreur","IdUser doit etre un nombre",new Command("OK"));
                }
            }
            }
        });
                Container content = BoxLayout.encloseY( new Label("Réclamation", "LogoLabel"),
                new FloatingHint(tfType),
                new FloatingHint(tfObjet),
                new FloatingHint(tfDescription),
                new FloatingHint(tfEmail),
                new FloatingHint(tfNom),
                new FloatingHint(tfPrenom)
                            
        );
                
        content.setScrollableY(true);
        content.add(btnImage);
        content.add(lbImage);
        content.add(btnValider);
        btnValider.requestFocus();
        setScrollVisible(true);
        addAll(content);
        
    }
     public void sendMail(String MailTo) {
        try {
            
            Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp"); //SMTP protocol
		props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication
             
            
            
            
            Session session = Session.getInstance(props,null);
            MimeMessage msg = new MimeMessage(session);
            
            msg.setFrom(new InternetAddress("Réclamation reçue <ali.boughnim@esprit.tn>"));
            msg.setRecipients(javax.mail.Message.RecipientType.TO,MailTo );
            msg.setSubject("SHARED");
            msg.setSentDate(new Date(System.currentTimeMillis()));
            
           String mp = "213JMT9300";//mp taw narj3lo
          
           
           
           msg.setText("Nous avons été notifié de votre réclamation, nous vous joindrons d'ici peu");
           
          SMTPTransport  st = (SMTPTransport)session.getTransport("smtps") ;
            
          st.connect("smtp.gmail.com",465,"ali.boughnim@esprit.tn","213JMT9300");
           
          st.sendMessage(msg, msg.getAllRecipients());
            
          System.out.println("server response : "+st.getLastServerResponse());
          
        }catch(Exception e ) {
            e.printStackTrace();
        }
    }
     public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    
}
