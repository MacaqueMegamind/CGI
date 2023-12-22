package com.cgvsu.render_engine.rasterization;

import com.cgvsu.math.vector.Vector3f;

public class Triangle {

    public final Vector3f v1;
    public final Vector3f v2;
    public final Vector3f v3;

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
}
