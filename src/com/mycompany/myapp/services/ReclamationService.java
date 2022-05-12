/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class ReclamationService {
    public ArrayList<Reclamation> Reclamations;

    public static ReclamationService instance = null;
    public boolean resultOK = true;
    private ConnectionRequest req;

    private ReclamationService() {
        req = new ConnectionRequest();
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }
    
    public boolean AddRec(Reclamation r){
        String url = Statics.BASE_URL+"addReclamation?type="+r.getType()+"&objet="+r.getObjet()+"&description="+r.getDescription()+"&email="+r.getEmail()+"&image="+r.getImage()+"&nom="+r.getNom()+"&prenom="+r.getPrenom(); 
        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);
       
        req.addResponseListener( (e) -> {
            
           String str = new String(req.getResponseData());
            
            System.out.println("data ===>"+str);
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    }
//    public ArrayList<Reclamation> parseRec(String jsonText)  {
//
//        try {
//
//            Reclamations = new ArrayList<>();
//
//            JSONParser j = new JSONParser();
//            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
//          //  System.out.println(tasksListJson);
//            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
//            System.out.println("list"+list);
//                        System.out.println("list");
//
//            for (Map<String, Object> obj : list) {
//                
//                
//                Reclamation Recs = new Reclamation();
//                
//              //  System.out.println(obj.get("objet").toString());
//                Recs.setId((int)Float.parseFloat(obj.get("id").toString()));
//                Recs.setType(obj.get("type").toString());
//                Recs.setObjet(obj.get("objet").toString());
//                Recs.setDescription(obj.get("description").toString());
//               // Recs.setNom(obj.get("type").toString());
////                String dc = obj.get("datecreation").toString();
////                SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");  
////                Date dateC=formatter1.parse(dc);  
////                Recs.setDateCreation(dateC);
//                
//                
//                ;
//                
//               // float stock1=Float.parseFloat(obj.get("stock").toString());
//                
//
//                
//
//              //  System.out.println(Recs);
//                Reclamations.add(Recs);
//            }
//
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//
//        return Reclamations;
//    }
//
//    
//    public ArrayList<Reclamation> getAllRecs() {
//        String url = Statics.Base_URL +"showRec";
//        req.setUrl(url);
//        System.out.println(url);
//        System.out.println("ok01");
//        req.setPost(false);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//
//               
//                Reclamations = parseRec(new String(req.getResponseData()));
//                
//
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return Reclamations;
//    }
     public ArrayList<Reclamation> parseArticle(String jsonText){
        try {
            Reclamations=new ArrayList<>();
            JSONParser j = new JSONParser();
            System.out.println(jsonText);
            Map<String,Object> menusListJson =
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            List<Map<String,Object>> list = (List<Map<String,Object>>)menusListJson.get("root");
            for(Map<String,Object> obj : list){
                Reclamation m = new Reclamation();
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int)id);
                m.setObjet(obj.get("objet").toString());
                m.setDescription(obj.get("description").toString());            
                               

                Reclamations.add(m);
            }
           
           
        } catch (IOException ex) {
           
        }
        return Reclamations;
    }
   
    public ArrayList<Reclamation> getAllArticle(){
        req=new ConnectionRequest();
        String url = Statics.BASE_URL+ "showRec";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Reclamations = parseArticle(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Reclamations;
    }
      public boolean deleteRec(int id) {
        String url = Statics.BASE_URL + "deleteJSON/" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
     public void modifier(Reclamation r) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL + "updateJSON/"+r.getId()+"&type="+r.getType()+"&idUser="+r.getIdUser()+"&objet="+r.getObjet()+"&description="+r.getDescription()+"&statut="+r.getStatut()+"&email="+r.getEmail()+"&image="+r.getImage()+"&nom="+r.getNom()+"&prenom="+r.getPrenom()+"&prenom="+r.getVocal();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
