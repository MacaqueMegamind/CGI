package com.cgvsu.render_engine.screen;

import com.cgvsu.math.point.Point2f;
import javafx.scene.image.PixelWriter;

import javax.swing.*;
import java.util.*;

public class Screen {
    HashSet<Point2f> pixelsSet = new HashSet<>();
    HashMap<Point2f, Pixel> pixelsMap = new HashMap<>();

    public void add(Pixel p){
        if(p.rgb == 0 || p.x * p.y < 0){
            return;
        }

        Point2f point = new Point2f(p.x, p.y);
        if(!pixelsSet.contains(point)){
            pixelsSet.add(point);
            pixelsMap.put(point, p);
        }else {
            Pixel f = pixelsMap.get(point);
            if(f.compare(p) > 0){
                pixelsMap.put(point, p);
            }
        }
    }
    public void draw(PixelWriter px){
        ArrayList<Pixel> a = new ArrayList<>(pixelsMap.values());

        System.out.println("Start frame");
        for(Pixel pixel: a){
            px.setArgb( pixel.x, pixel.y, pixel.rgb);
        }
        System.out.println("New frame");
    }

    public void clear(){
        pixelsSet.clear();
        pixelsMap.clear();
    }
}
