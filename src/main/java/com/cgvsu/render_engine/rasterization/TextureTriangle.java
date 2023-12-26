package com.cgvsu.render_engine.rasterization;

import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TextureTriangle {

    public Vector2f v1, v2, v3;
    public boolean meshMode = true;
    public boolean textureMode = false;
    public Vector2f t1;
    public Vector2f t2;
    public Vector2f t3;

    public TextureTriangle(Vector2f t1, Vector2f t2, Vector2f t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public TextureTriangle(Vector2f t1, Vector2f t2, Vector2f t3, boolean meshMode, boolean textureMode) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.meshMode = meshMode;
        this.textureMode = textureMode;
    }

    public TextureTriangle(Vector2f t1, Vector2f t2, Vector2f t3, boolean meshMode, boolean textureMode, Vector2f v1, Vector2f v2, Vector2f v3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.meshMode = meshMode;
        this.textureMode = textureMode;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
        private static class V{
            public final Vector2f v;
            public final Vector2f t;

            public V(Vector2f v, Vector2f t) {
                this.v = v;
                this.t = t;
            }
        }

    Comparator<V> COMPARATOR = (a, b) -> {
        int cmp = Float.compare(a.v.y, b.v.y);
        if (cmp != 0) {
            return cmp;
        } else return Float.compare(a.v.x, b.v.x);
    };

    public void sort(){
        V[] vorp = new V[]{new V(v1, t1),
                new V(v2, t2),
                new V(v3, t3)};

        Arrays.sort(vorp, COMPARATOR);

        this.v1 = vorp[0].v;
        this.v2 = vorp[1].v;
        this.v3 = vorp[2].v;

        this.t1 = vorp[0].t;
        this.t2 = vorp[1].t;
        this.t3 = vorp[2].t;

    }
}
