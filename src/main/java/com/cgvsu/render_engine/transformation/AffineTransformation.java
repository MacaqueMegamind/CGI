package com.cgvsu.render_engine.transformation;


import com.cgvsu.Main;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;

import javax.vecmath.*;
import java.util.List;

public class AffineTransformation implements Transformation{

    Rotate rotate;
    Scale scale;
    Transition transition;

    public AffineTransformation(Rotate rotate, Scale scale, Transition transition) {
        this.rotate = rotate;
        this.scale = scale;
        this.transition = transition;
    }

    public AffineTransformation() {
        this.rotate = new DefaultRotate();
        this.scale = new DefaultScale();
        this.transition = new DefaultTransition();
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    private Matrix4f translateMatrix3To4(Matrix3f m){
        return new Matrix4f(m.m00, m.m01, m.m02, 0,
                            m.m10, m.m11, m.m12, 0,
                            m.m20, m.m21, m.m22, 0,
                            0, 0, 0, 1);
    }
    @Override
    public void calculate (List<Vector3f> vector){
//        Matrix4f matrix = transition();
//        matrix.sub(translateMatrix3To4(rotate()));
//        matrix.sub(translateMatrix3To4(scale()));

//        Matrix4f matrix = translateMatrix3To4(scale());
//        matrix.sub(translateMatrix3To4(rotate()));
//        matrix.sub(transition());

        Matrix4f matrix = translateMatrix3To4(scale.getMatrix());
        matrix.sub(translateMatrix3To4(rotate.getMatrix()));
        matrix.sub(transition.getMatrix());

        for(Vector3f v:vector){
            Vector4f v4 = new Vector4f(v.getX(), v.getY(), v.getZ(), 1);
            v4.sub(matrix);
            v.setX(v4.getX());
            v.setY(v4.getY());
            v.setZ(v4.getZ());
        }
    }
}
