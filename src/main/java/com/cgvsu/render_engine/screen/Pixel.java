package com.cgvsu.render_engine.screen;

import com.cgvsu.math.point.Point2f;
import com.cgvsu.math.vector.Vector3f;

import java.util.Comparator;

public class Pixel {
    int x;
    int y;
    float z;
    int rgb;

    private static final Comparator<Pixel> COMPARATOR = (a, b) ->{
        return Float.compare(a.z, b.z);
    };

    public Pixel(Point2f v, float z, int rgb) {
        this.x = (int) v.x;
        this.y = (int) v.y;
        this.z = z;
        this.rgb = rgb;
    }
    public Pixel(Vector3f v, int rgb) {
        this.x = (int) v.x;
        this.y = (int) v.y;
        this.z = v.z;
        this.rgb = rgb;
    }

    public int compare(Pixel other){
        return COMPARATOR.compare(this, other);
    }
}
