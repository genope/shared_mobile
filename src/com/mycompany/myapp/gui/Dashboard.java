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
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.enities.Offres;
import com.mycompany.myapp.services.serviceOffres;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author user
 */
public class Dashboard extends Form{
  ArrayList offres=serviceOffres.getInstance().getAllOffres();
  ArrayList<Offres> jj=serviceOffres.getInstance().getAllOffres();
       ArrayList of = new ArrayList();
         
    
     public Dashboard() {
        
         for (int i=0;i<offres.size();i++){
            Offres offre=(Offres)offres.get(i);
            of.add(offre.getCateg());
          
                    
              
             
         }
  
    
        setTitle("Statitcs");
        setLayout(BoxLayout.yCenter());
         
                
               double[] values = new double[]{Collections.frequency(of, "Chambre"), Collections.frequency(of, "Appartement"), Collections.frequency(of, "Maison"), Collections.frequency(of, "Velo"), Collections.frequency(of, "Moto"),Collections.frequency(of, "Voiture")};

    // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.YELLOW, ColorUtil.MAGENTA, ColorUtil.CYAN, ColorUtil.CYAN,ColorUtil.LTGRAY};
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
    PieChart chart = new PieChart(buildCategoryDataset("Statistiques Offres", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);
         addAll(c);
        
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
        series.add("Catégorie"+k, value);
        
        
    }

    return series;
}
}
