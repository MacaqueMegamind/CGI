package com.cgvsu.render_engine.rasterization;

import com.cgvsu.math.vector.Vector2f;

public class TextureTriangle {
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
}
