package com.cgvsu.triangulation;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Triangulation {

    public static void triangulateModel(Model model) {
        model.setPolygons(triangulatePolygons(model.getPolygons()));
    }
    protected static ArrayList<Polygon> triangulatePolygons(ArrayList<Polygon> polygons) {
        ArrayList<Polygon> triangulatedPolygons = new ArrayList<>();

        for (Polygon p : polygons) {
            triangulatedPolygons.addAll(triangulation(p));
        }

        return triangulatedPolygons;
    }

    protected static ArrayList<Polygon> triangulation(Polygon polygon) {
        ArrayList<Polygon> triangulatedPolygons = new ArrayList<>();

        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        int quantityVertexes = vertexIndices.size();

        ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        checkForCorrectListSize(textureVertexIndices, quantityVertexes, "текстурных координат");

        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        checkForCorrectListSize(normalIndices, quantityVertexes, "нормалей");

        for (int index = 1; index < vertexIndices.size() - 1; index++) {
            Polygon triangularPolygon = new Polygon();
            triangularPolygon.setVertexIndices(getTriangulatedPolygon(vertexIndices, index));
            triangularPolygon.setTextureVertexIndices(getTriangulatedPolygon(textureVertexIndices, index));
            triangularPolygon.setNormalIndices(getTriangulatedPolygon(normalIndices, index));

            triangulatedPolygons.add(triangularPolygon);
        }
        return triangulatedPolygons;
    }

    private static void checkForCorrectListSize(List<Integer> list, int expectedSize, String listName) {
        if (list.size() != 0 && list.size() != expectedSize) {
            throw new IllegalArgumentException("Некорректное количество " + listName + " в полигоне");
        }
    }

    protected static ArrayList<Integer> getTriangulatedPolygon(List<Integer> list, int indexSecondVertex) {
        ArrayList<Integer> indices = new ArrayList<>();
        if (list.size() != 0) {
            indices.add(list.get(0));
            indices.add(list.get(indexSecondVertex));
            indices.add(list.get(indexSecondVertex + 1));
        }
        return indices;
    }

}
