package com.cgvsu.triangulation;

import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cgvsu.math.vector.Vector3f.fromTwoPoints;

public class CalculationNormals {

    public static void calculateNormals(Model model) {
        model.normals.clear();

        HashMap<Integer, ArrayList<Vector3f>> verticesWithNormals = new HashMap<>();

        for (Polygon p : model.getPolygons()) {
            Vector3f polygonNormal = calculateNormalForPolygon(model, p);
            for (int i : p.getVertexIndices()) {
                if (!verticesWithNormals.containsKey(i)) {
                    verticesWithNormals.put(i, new ArrayList<Vector3f>());
                }
                verticesWithNormals.get(i).add(polygonNormal);
            }
        }
        for (int i = 0; i < model.vertices.size(); i++) {
            model.normals.add(calculateNormalForVertex(verticesWithNormals.get(i)));
        }
    }

    protected static Vector3f calculateNormalForVertex(ArrayList<Vector3f> polygonNormals) {
        Vector3f vertexNormal = new Vector3f();
        for (Vector3f v : polygonNormals) {
            vertexNormal.add(v);
        }
        return vertexNormal.div(polygonNormals.size());
    }

    protected static Vector3f calculateNormalForPolygon(Model model, Polygon polygon) {
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();

        Vector3f vector1 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(1)));
        Vector3f vector2 = fromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(vertexIndices.size() - 1)));

        Vector3f resultVector = new Vector3f();
        resultVector.crs(vector1, vector2);
        return resultVector;
    }
}
