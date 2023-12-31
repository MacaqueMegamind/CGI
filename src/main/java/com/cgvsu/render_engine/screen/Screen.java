package com.cgvsu.render_engine.screen;

import com.cgvsu.math.point.Point2f;
import javafx.scene.image.PixelWriter;

import java.util.*;

public class Screen {

    PixelWriter px;
    int width;
    int height;

    public Screen(PixelWriter px) {
        this.px = px;
    }

    public Screen(PixelWriter px, int width, int height){
        this.px = px;
        this.width = width;
        this.height = height;
        zBuffer = new Float[width][height];
    }

    HashSet<Point2f> pixelsSet = new HashSet<>();
    HashMap<Point2f, Float> pixelsMap = new HashMap<>(); //Z buffer

    Float zBuffer[][];

    public void add(Pixel p){
        if(p.rgb == 0 || p.x * p.y < 0){
            return;
        }

        Point2f point = new Point2f(p.x, p.y);
        if(!pixelsSet.contains(point)){
            pixelsSet.add(point);
            pixelsMap.put(point, p.z);
            px.setArgb(p.x, p.y, p.rgb);
        }else {
            Float f = pixelsMap.get(point);
            if(f > p.z){
                pixelsMap.put(point, p.z);
                px.setArgb(p.x, p.y, p.rgb);
            }
        }
    }

    public void draw(int x, int y, float z, int rgb){
        if(rgb == 0 || x < 0 || y < 0 || x >= width || y >= height){
            return;
        }

        if(zBuffer[x][y] == null || zBuffer[x][y] > z){
            px.setArgb(x, y, rgb);
            zBuffer[x][y] = z;
        }
    }
//    public void draw(PixelWriter px){
//        ArrayList<Pixel> a = new ArrayList<>(pixelsMap.values());
//
//        System.out.println("Start frame");
//        for(Pixel pixel: a){
//            px.setArgb( pixel.x, pixel.y, pixel.rgb);
//        }
//        System.out.println("New frame");
//    }

    public void clear(){
        pixelsSet.clear();
        pixelsMap.clear();
        zBuffer = new Float[width][height];
    }
}
