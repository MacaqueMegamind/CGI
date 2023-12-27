package com.cgvsu.render_engine.transformation;
import com.cgvsu.math.matrix.Matrix4f;

public class DefaultTransition implements Transition {
    float transitionX = 0, transitionY = 0, transitionZ = 0;

    public DefaultTransition(float transitionX, float transitionY, float transitionZ) {
        this.transitionX = transitionX;
        this.transitionY = transitionY;
        this.transitionZ = transitionZ;
    }

    public DefaultTransition() {
    }

    public float getTransitionX() {
        return transitionX;
    }

    public void setTransitionX(float transitionX) {
        this.transitionX = transitionX;
    }

    public float getTransitionY() {
        return transitionY;
    }

    public void setTransitionY(float transitionY) {
        this.transitionY = transitionY;
    }

    public void clear() {
        this.transitionX = 0;
        this.transitionY = 0;
        this.transitionZ = 0;
    }

    public float getTransitionZ() {
        return transitionZ;
    }

    public void setTransitionZ(float transitionZ) {
        this.transitionZ = transitionZ;
    }

    @Override
    public Matrix4f getMatrix() {
        return new Matrix4f(new float[]{1, 0, 0, transitionX,
                                        0, 1, 0, transitionY,
                                        0, 0, 1, transitionZ,
                                        0, 0, 0, 1});
    }
}
