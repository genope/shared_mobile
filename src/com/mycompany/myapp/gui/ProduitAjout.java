/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.enities.Produits;
import com.mycompany.myapp.services.ProduitService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;import 
com.sun.mail.smtp.SMTPTransport;
//import java.nio.file.CopyOption;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author yeekt
 */
public class ProduitAjout extends Form {
           private Resources theme;
         private EncodedImage enc;
         private String file;
        public void notif (){
            LocalNotification n = new LocalNotification();
        n.setId("demo-notification");
        n.setAlertBody("It's time to take a break and look at me");
        n.setAlertTitle("Break Time!");
        n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound
            System.out.println("NOTIF");

        Display.getInstance().scheduleLocalNotification(
                n,
                System.currentTimeMillis() + 3 * 1000, // fire date/time
                LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
        );
      
        }
         public void sendMail(String str) {
        try {
            
            Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp"); //SMTP protocol
		props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication
             
            Session session = Session.getInstance(props,null); 
            
            
            MimeMessage msg = new MimeMessage(session);
            
            msg.setRecipients(Message.RecipientType.TO,"TNSharedInc@gmail.com");
            msg.setSubject("Application nom  : Confirmation du ");
            msg.setSentDate(new Date(System.currentTimeMillis()));
            
           String txt = "Produit "+str +" Ajouté";
           
           
           msg.setText(txt);
           
          SMTPTransport  st = (SMTPTransport)session.getTransport("smtps") ;
            
          st.connect("smtp.gmail.com",465,"TNSharedInc@gmail.com","shared2022");
           
          st.sendMessage(msg, msg.getAllRecipients());
            
          System.out.println("server response : "+st.getLastServerResponse());
          
        }catch(Exception e ) {
            e.printStackTrace();
        }
    }
    public ProduitAjout() throws IOException {
        
            
        setTitle("Ajouter Produit");
        setUIID("Ajout");
        setScrollableY(true);
        getToolbar().addCommandToSideMenu("Accueil",enc,
                e->{
            try {
                new homeShared().show();
            } catch (IOException ex) {
             
            }
        });
        getToolbar().addCommandToSideMenu("Liste des offres",enc,
                e->{
           try {
               new GetOffres().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        getToolbar().addCommandToSideMenu("Liste des produits",enc,
                e->{
           try {
               new ProduitsListe().show();
           } catch (IOException ex) {
               System.out.println(ex.getMessage());           }
        });
        
        getToolbar().addCommandToRightBar("back", null, ev->{
            try {
                new homeShared().show();
            } catch (IOException ex) {
         
            }
        });
        
        
        Container FormAjout =new Container(BoxLayout.yCenter());
        TextComponent refProd = new TextComponent().labelAndHint("refProd");
        FontImage.setMaterialIcon(refProd.getField().getHintLabel(), FontImage.MATERIAL_PERSON);
        
        TextComponent designation = new TextComponent().labelAndHint("designation");
        FontImage.setMaterialIcon(designation.getField().getHintLabel(), FontImage.MATERIAL_EMAIL);
        
        TextComponent qteStock = new TextComponent().labelAndHint("qteStock");
        FontImage.setMaterialIcon(qteStock.getField().getHintLabel(), FontImage.MATERIAL_LOCK);
        
        TextComponent prix = new TextComponent().labelAndHint("prix").constraint(TextArea.NUMERIC);
        FontImage.setMaterialIcon(prix.getField().getHintLabel(), FontImage.MATERIAL_LIBRARY_BOOKS);
        
        Button save = new Button("Save");
//        save.addActionListener(e -> {
//            ToastBar.showMessage("Save pressed...", FontImage.MATERIAL_INFO);
//            
//        });
 
        Button avatar = new Button("");
        avatar.setUIID("InputAvatar");
        Image defaultAvatar = FontImage.createMaterial(FontImage.MATERIAL_CAMERA, "InputAvatarImage", 8);
        
        Image circleMaskImage =EncodedImage.create("/FileChooser.png");
        defaultAvatar = defaultAvatar.scaled(circleMaskImage.getWidth(), circleMaskImage.getHeight());
        defaultAvatar = ((FontImage)defaultAvatar).toEncodedImage();
        Object circleMask = circleMaskImage.createMask();
        defaultAvatar = defaultAvatar.applyMask(circleMask);
        avatar.setIcon(defaultAvatar);
        
        
        Button upload = new Button("Upload Image Produit");
        upload.setUIID("fileC");
        
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
  
        String picture = Capture.capturePhoto(1024, -1);
        if(picture!=null){
        String filestack = "https://www.filestackapi.com/api/store/S3?key=MY_KEY&filename=myPicture.jpg";
        MultipartRequest request = new MultipartRequest() {
           protected void readResponse(InputStream input) throws IOException  {
              JSONParser jp = new JSONParser();
              Map<String, Object> result = jp.parseJSON(new InputStreamReader(input, "UTF-8"));
              String url = (String)result.get("url");
//               System.out.println(picture.toURI();
//                String to1 = picture;
//                //           to2 = Paths.get("src\\"+path+"\\"+file.getName()+".png");
//                CopyOption[] options = new CopyOption[]{
////                    StandardCopyOption.REPLACE_EXISTING,
////                    StandardCopyOption.COPY_ATTRIBUTES
//                };
//                Files.copy(from, to1, options);
           }
        };
        request.setUrl(filestack);
        try {
            request.addData("fileUpload", picture, "image/jpeg");
            request.setFilename("fileUpload", "myPicture.jpg");
            NetworkManager.getInstance().addToQueue(request);
        } catch(IOException err) {
            err.printStackTrace();
        }
    }
                                        
            }
        });
        
              save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(((refProd.getText().length()==0)||(designation.getText().length()==0)||(prix.getText().length()==0)||(qteStock.getText().length()==0)))
                Dialog.show("Alert", "Remplir les champs", "OK","");
                 else
                {
                    Produits prod= new Produits(Integer.parseInt(qteStock.getText()), refProd.getText(), designation.getText() ,Float.parseFloat(prix.getText()));
                    
                        if( ProduitService.getInstance().addProd(prod))
                        {
                            sendMail(refProd.getText());
                            notif();
//                           Dialog.show("Success","Offre Ajouté","OK","");
                           
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
              
        Button Share=new Button("Share");
        ButtonGroup barGroup = new ButtonGroup();

        RadioButton liste = RadioButton.createToggle("Partager", barGroup);
        liste.setUIID("Button");
        liste.addPointerPressedListener(l->{
        Display.getInstance().execute("https://www.facebook.com/sharer/sharer.php?kid_directed_site=0&sdk=joey&u=http%3A%2F%2F127.0.0.1%3A8000%2Fequipe_mobile&display=popup&ref=plugin&src=share_button");
        
        });
        FormAjout.addAll(refProd,designation,qteStock,prix,upload,save,liste);
        add(FormAjout);
        
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
    


