package com.cgvsu.render_engine.transformation;

import javax.vecmath.Matrix3f;

public class DefaultScale implements Scale{
    float scaleX = 1, scaleY = 1, scaleZ = 1;

    public DefaultScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public DefaultScale() {
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }

    @Override
    public Matrix3f getMatrix() {
        return new Matrix3f(1/scaleX, 0, 0,
                0, 1/scaleY, 0,
                0, 0, 1/scaleZ);
    }
}
