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
import com.mycompany.myapp.enities.Publication;


import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceTask {

    public ArrayList<Publication> tasks;
    
    public static ServiceTask instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceTask() {
         req = new ConnectionRequest();
    }

    public static ServiceTask getInstance() {
        if (instance == null) {
            instance = new ServiceTask();
        }
        return instance;
    }

    public boolean addTask(Publication t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "frontpublicationMobileadd";
    
       req.setUrl(url);
       
       req.addArgument("nom", t.getNom());
       req.addArgument("description", t.getDescription());
       req.addArgument("image", t.getImage());
       req.addArgument("adresse", t.getAdresse());
       
       
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    
    }

    public ArrayList<Publication> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println(list);
            for(Map<String,Object> obj : list){
                Publication t = new Publication();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
//                float id_guest = Float.parseFloat(obj.get("id_guest").toString());
//                t.setId_guest((int)id_guest);
t.setId_guest(12345697);
                t.setNom(obj.get("nom").toString());
                t.setDescription(obj.get("description").toString());
                t.setImage(obj.get("image").toString());
                t.setAdresse(obj.get("adresse").toString());
//                float region_id = Float.parseFloat(obj.get("region_id").toString());
//                t.setRegion_id((int)region_id);
t.setRegion_id(10);
                t.setDatecreation(obj.get("datecreation").toString());
                
               
                
                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<Publication> getAllTasks(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"PubfrontpublicationMobile";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    public boolean deletepub(int id) {
        String url = Statics.BASE_URL + "deletePubMobile/" + id;
        
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
}
