package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.model.Polygon;
import com.cgvsu.model.Texture;
import com.cgvsu.render_engine.rasterization.TextureTriangle;
import com.cgvsu.render_engine.rasterization.Triangle;
import com.cgvsu.render_engine.rasterization.TriangleRasterization;
import com.cgvsu.render_engine.screen.Screen;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import static com.cgvsu.render_engine.GraphicConveyor.*;
import com.cgvsu.math.vector.*;
import com.cgvsu.math.matrix.*;
import com.cgvsu.math.point.*;
public class RenderEngine {

    //Render with texture
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final Texture texture,
            final int width,
            final int height)
    {
        if(texture == null){
            render(graphicsContext, camera, mesh, width, height);
            return;
        }

        Screen screen = new Screen(graphicsContext.getPixelWriter(), width, height);

        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final Polygon currentPolygon = mesh.polygons.get(polygonInd);
            final ArrayList<Integer> polygonIndexes = currentPolygon.getVertexIndices();

            ArrayList<Vector3f> resultPoints = new ArrayList<>();

            for (int vertexInPolygonInd:polygonIndexes) {
                Vector3f vertex = mesh.vertices.get(vertexInPolygonInd);

                Vector3f vertexVecmath = new Vector3f(vertex.x, vertex.y, vertex.z);

                Vector3f resultPoint = vertexToVector(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }


            if(resultPoints.size() == 3){
                Triangle polygon = new Triangle(resultPoints.get(0),
                        resultPoints.get(1),
                        resultPoints.get(2));
                TextureTriangle polygonTexture = new TextureTriangle(mesh.textureVertices.get(polygonIndexes.get(0)),
                                mesh.textureVertices.get(polygonIndexes.get(1)),
                                mesh.textureVertices.get(polygonIndexes.get(2)));
                TriangleRasterization.drawTriangle(screen,
                        polygon, texture, polygonTexture);
            }
        }
//        screen.draw(graphicsContext.getPixelWriter());
//        screen.clear();

    }

    //draw model without texture
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height)
    {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                Vector3f vertexVecmath = new Vector3f(vertex.x, vertex.y, vertex.z);

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
        }
    }
}