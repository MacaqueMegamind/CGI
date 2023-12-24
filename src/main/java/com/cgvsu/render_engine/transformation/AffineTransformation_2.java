package com.cgvsu.render_engine.transformation;


import com.cgvsu.math.matrix.Matrix3f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.math.vector.Vector4f;

import java.util.List;

public class AffineTransformation_2 implements Transformation{

    Rotate rotate;
    Scale scale;
    Transition transition;

    public AffineTransformation_2(Rotate rotate, Scale scale, Transition transition) {
        this.rotate = rotate;
        this.scale = scale;
        this.transition = transition;
    }

    public AffineTransformation_2() {
        this.rotate = new InvertedRotate();
        this.scale = new InvertedScale();
        this.transition = new InvertedTransition();
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
        return new Matrix4f(new float[]{m.val[0], m.val[3], m.val[6], 0,
                m.val[1], m.val[4], m.val[7], 0,
                m.val[2], m.val[5], m.val[8], 0,
                0, 0, 0, 1});
    }
    @Override
    public void calculate (List<Vector3f> vector){
//        Matrix4f matrix = transition();
//        matrix.sub(translateMatrix3To4(rotate()));
//        matrix.sub(translateMatrix3To4(scale()));

//        Matrix4f matrix = translateMatrix3To4(scale());
//        matrix.sub(translateMatrix3To4(rotate()));
//        matrix.sub(transition());

        Matrix4f matrix = translateMatrix3To4(scale.getMatrix())
                .mul(translateMatrix3To4(rotate.getMatrix()))
                .mul(transition.getMatrix());

        for(Vector3f v:vector){
            Vector4f v4 = new Vector4f(v.getX(), v.getY(), v.getZ(), 1);
            v4.mul(matrix);
            v.setX(v4.getX());
            v.setY(v4.getY());
            v.setZ(v4.getZ());
        }
    }
}
