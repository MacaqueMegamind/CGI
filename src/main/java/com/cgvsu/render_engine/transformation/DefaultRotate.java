package com.cgvsu.render_engine.transformation;

import javax.vecmath.Matrix3f;

public class DefaultRotate implements Rotate{
    float rotateX = 0, rotateY = 0, rotateZ = 0;

    public DefaultRotate(float rotateX, float rotateY, float rotateZ) {
        this.rotateX = (float) Math.toRadians(rotateX);
        this.rotateY = (float) Math.toRadians(rotateY);
        this.rotateZ = (float) Math.toRadians(rotateZ);
    }

    public DefaultRotate() {
    }

    public float getRotateX() {
        return rotateX;
    }

    public void setRotateX(float rotateX) {
        this.rotateX = rotateX;
    }

    public float getRotateY() {
        return rotateY;
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
    }

    public float getRotateZ() {
        return rotateZ;
    }

    public void setRotateZ(float rotateZ) {
        this.rotateZ = rotateZ;
    }
    private Matrix3f rotateMatrixX(){
        float[] matrix = new float[]{
                1, 0, 0,
                0,  (float) Math.cos(rotateX),  (float) Math.sin(rotateX),
                0,  (float) -Math.sin(rotateX),  (float) Math.cos(rotateX)
        };
        return new Matrix3f(matrix);
    }
    private Matrix3f rotateMatrixY(){
        float[] matrix = new float[]{
                (float) Math.cos(rotateY),0,  (float) Math.sin(rotateY),
                0, 1, 0,
                (float) -Math.sin(rotateY),0,  (float) Math.cos(rotateY)
        };
        return new Matrix3f(matrix);
    }
    private Matrix3f rotateMatrixZ() {
        float[] matrix = new float[]{
                (float)Math.cos(rotateZ),  (float) Math.sin(rotateZ), 0,
                (float)-Math.sin(rotateZ),  (float) Math.cos(rotateZ), 0,
                0, 0, 1
        };
        return new Matrix3f(matrix);
    }
        
    @Override
    public Matrix3f getMatrix() {
            Matrix3f matrix = rotateMatrixZ();
            matrix.mul(rotateMatrixY());
            matrix.mul(rotateMatrixX());
            return matrix;
    }
}
