package com.cgvsu.math;

import javax.vecmath.Matrix4f;

import static com.cgvsu.math.Constants.EPS;

public class Vector4f {
    public float x, y, z, w;
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public boolean equals(Vector4f other) {
        return Math.abs(x - other.x) < EPS &&
                Math.abs(y - other.y) < EPS &&
                Math.abs(z - other.z) < EPS &&
                Math.abs(w - other.w) < EPS;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public void sub(Matrix4f m){
        float v0 = m.m00*x+m.m01*y+m.m02*z+m.m03*w;
        float v1 = m.m10*x+m.m11*y+m.m12*z+m.m13*w;
        float v2 = m.m20*x+m.m21*y+m.m22*z+m.m13*w;
        float v3 = m.m30*x+m.m31*y+m.m32*z+m.m33*w;

        this.x = v0;
        this.y = v1;
        this.z = v2;
        this.w = v3;
    }
}
