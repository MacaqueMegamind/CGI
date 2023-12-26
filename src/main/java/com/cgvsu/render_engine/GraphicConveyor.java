package com.cgvsu.render_engine;
import com.cgvsu.math.matrix.*;
import com.cgvsu.math.vector.*;
import com.cgvsu.math.point.*;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.transformation.DefaultRotate;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static void rotateModel(float x, float y, float z, Model model, Camera camera){
        camera.af.setRotate(new DefaultRotate(x, y, z));
        camera.af.calculate(model.vertices);
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

    public static Vector3f vertexToVector(final Vector3f vertex, final int width, final int height){
        return new Vector3f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F, vertex.z);
    }


}
