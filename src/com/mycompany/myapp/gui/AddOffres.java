/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ToastBar;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout.Constraint;
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.services.serviceOffres;
import java.io.IOException;


/**
 *
 * @author user
 */
public class AddOffres extends Form{
    
   private Form current;
     public AddOffres(Form previous) {
        setTitle("Add Offre");
        setLayout(BoxLayout.y());
        
        
        
        TextField nom = new TextField("","Nom");
        TextField description= new TextField("", "Description");
        TextField prix= new TextField("", "Prix");
      
        TextField ville= new TextField("", "Ville");
        ComboBox<String> combo= new ComboBox<>("Chambre","Maison","Appartement","Voiture","Moto","Vélo");
       
         
               
//           hhhh.addActionListener((evt) -> {
//                 Display.getInstance().openGallery(new ActionListener() {
//                    public void actionPerformed(ActionEvent ev) {
//                   if(ev != null && ev.getSource() != null) {
//                       String filePathToGalleryImageOrVideo = (String)ev.getSource();
//                       System.out.println(filePathToGalleryImageOrVideo);
//           
//        }
//    }z
//}, Display.GALLERY_ALL);
//           }
//           );
       
                
        Button btnValider = new Button("Ajouter");
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length()==0)||(description.getText().length()==0)||(prix.getText().length()==0)||(ville.getText().length()==0))
                    Dialog.show("Alert", "Merci de renseigner tout les champs","OK","");
                else
                {
                    Offres of= new Offres(nom.getText(), description.getText(),ville.getText(), combo.getSelectedItem(),Float.parseFloat(prix.getText()));
                    System.out.println(ville.getText());
                            System.out.println("hhhh");
                             System.out.println(of);
                        if( serviceOffres.getInstance().addDestination(of))
                        {
                                ToastBar.Status status = ToastBar.getInstance().createStatus();
                                status.setMessage("Offres Ajouté");
                                status.setExpires(5000);  
                                status.show();
                       
                        try {
                            new GetOffres(current).show();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        }else
                            Dialog.show("ERROR", "Server error", "OK","");
                    
                    
                }
                
                
            }
        });
       double[] values = new double[]{50, 14, 11, 10, 19};

    // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);
Location position = LocationManager.getLocationManager().getCurrentLocationSync();
         System.out.println(position);
    // Create a form and show it.
        addAll(nom,description,prix,ville,combo,btnValider,c);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
     
     private DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}
     protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
        series.add("Catégorie "+k, value);
        
        
    }

    return series;
}
    
    
}
