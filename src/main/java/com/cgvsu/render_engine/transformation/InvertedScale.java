package com.cgvsu.render_engine.transformation;


import com.cgvsu.math.matrix.Matrix3f;

public class InvertedScale implements Scale{
    float scaleX = 1, scaleY = 1, scaleZ = 1;

    public InvertedScale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public InvertedScale() {
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
        return new Matrix3f(new float[]{scaleX, 0, 0,
                0, scaleY, 0,
                0, 0, scaleZ});
    }
}
