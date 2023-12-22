package com.cgvsu.render_engine.screen;

import com.cgvsu.math.point.Point2f;
import javafx.scene.image.PixelWriter;

import javax.swing.*;
import java.util.*;

public class Screen {
    HashSet<Point2f> pixelsSet = new HashSet<>();
    HashMap<Point2f, Pixel> pixelsMap = new HashMap<>();

    public void add(Pixel p){
        if(!pixelsSet.contains(p.point)){
            pixelsSet.add(p.point);
            pixelsMap.put(p.point, p);
        }else {
            Pixel f = pixelsMap.get(p.point);
            if(f.compare(p) > 0){
                pixelsMap.put(f.point, p);
            }
        }
    }
    public void draw(PixelWriter px){
        for(Map.Entry<Point2f, Pixel> p:pixelsMap.entrySet()){
            Point2f point = p.getKey();
            Pixel pixel = p.getValue();
            px.setArgb((int) point.x, (int) point.y, pixel.rgb);
        }
    }

    public void clear(){
        pixelsSet.clear();
        pixelsMap.clear();
    }
}
