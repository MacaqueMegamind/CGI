package com.cgvsu.render_engine;
import com.cgvsu.math.matrix.*;
import com.cgvsu.math.vector.*;
import com.cgvsu.math.point.*;

import static java.lang.Math.*;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }



    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ.sub(target, eye);
        resultX.crs(up, resultZ);
        resultY.crs(resultZ, resultX);

        resultX.nor();
        resultY.nor();
        resultZ.nor();

        float[] matrix = new float[]{
                resultX.x, resultX.y, resultX.z, -resultX.dot(eye),
                resultY.x, resultY.y, resultY.z, -resultY.dot(eye),
                resultZ.x, resultZ.y, resultZ.z, -resultZ.dot(eye),
                0, 0, 0, 1
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.val[Matrix4f.M11] = tangentMinusOnDegree / aspectRatio;
        result.val[Matrix4f.M22] = tangentMinusOnDegree;
        result.val[Matrix4f.M33] = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.val[Matrix4f.M34] = 1.0F;
        result.val[Matrix4f.M43] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f m, final Vector3f v) {

        final float x = (v.x * m.val[Matrix4f.M11]) + (v.y * m.val[Matrix4f.M21]) + (v.z * m.val[Matrix4f.M31]) + m.val[Matrix4f.M41];
        final float y = (v.x * m.val[Matrix4f.M12]) + (v.y * m.val[Matrix4f.M22]) + (v.z * m.val[Matrix4f.M32]) + m.val[Matrix4f.M42];
        final float z = (v.x * m.val[Matrix4f.M13]) + (v.y * m.val[Matrix4f.M23]) + (v.z * m.val[Matrix4f.M33]) + m.val[Matrix4f.M43];
        final float w = (v.x * m.val[Matrix4f.M14]) + (v.y * m.val[Matrix4f.M24]) + (v.z * m.val[Matrix4f.M34]) + m.val[Matrix4f.M44];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }


    public static Matrix4f rotateScaleTranslate(final Vector3f rotateVector, final Vector3f scaleVector, final Vector3f translationVector) {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };
        Matrix4f matrix4f = new Matrix4f(matrix);
        //Angles GRAD -> RAD
        float rotateAngleXRAD = (float) ((rotateVector.x) * PI / 180);
        float rotateAngleYRAD = (float) ((rotateVector.y) * PI / 180);
        float rotateAngleZRAD = (float) ((rotateVector.z) * PI / 180);

        //Rotation
        //x
        matrix4f.mul(new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, (float) cos(rotateAngleXRAD), (float) sin(rotateAngleXRAD), 0,
                0, (float) -sin(rotateAngleXRAD), (float) cos(rotateAngleXRAD), 0,
                0, 0, 0, 1
        }));
        //y
        matrix4f.mul(new Matrix4f(new float[]{
                (float) cos(rotateAngleYRAD), 0, (float) -sin(rotateAngleYRAD), 0,
                0, 1, 0, 0,
                (float) sin(rotateAngleYRAD), 0, (float) cos(rotateAngleYRAD), 0,
                0, 0, 0, 1
        }));
        //z
        matrix4f.mul(new Matrix4f(new float[]{
                (float) cos(rotateAngleZRAD), (float) sin(rotateAngleZRAD), 0, 0,
                -(float) sin(rotateAngleZRAD), (float) cos(rotateAngleZRAD), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        }));


        //Scaling
        matrix4f.val[Matrix4f.M11] *= scaleVector.x;
        matrix4f.val[Matrix4f.M22] *= scaleVector.y;
        matrix4f.val[Matrix4f.M33] *= scaleVector.z;
        //Translation
        matrix4f.val[Matrix4f.M41] = translationVector.x;
        matrix4f.val[Matrix4f.M42] = translationVector.y;
        matrix4f.val[Matrix4f.M43] = translationVector.z;


        return matrix4f;
    }
}
