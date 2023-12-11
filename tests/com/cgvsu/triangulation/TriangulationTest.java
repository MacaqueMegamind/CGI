package com.cgvsu.triangulation;

import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangulationTest {

    @Test
    void triangulation() {
        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 4, 2)));
        polygon1.setTextureVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 4, 2)));
        polygon1.setNormalIndices(new ArrayList<>(Arrays.asList(1, 3, 4, 2)));
        ArrayList<Polygon> resPolygons = Triangulation.triangulation(polygon1);

        ArrayList<Polygon> expectedPolygons = new ArrayList<>();
        Polygon expectedPolygon1 = new Polygon();
        expectedPolygon1.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 4)));
        expectedPolygon1.setTextureVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 4)));
        expectedPolygon1.setNormalIndices(new ArrayList<>(Arrays.asList(1, 3, 4)));
        expectedPolygons.add(expectedPolygon1);
        Polygon expectedPolygon2 = new Polygon();
        expectedPolygon2.setVertexIndices(new ArrayList<>(Arrays.asList(1, 4, 2)));
        expectedPolygon2.setTextureVertexIndices(new ArrayList<>(Arrays.asList(1, 4, 2)));
        expectedPolygon2.setNormalIndices(new ArrayList<>(Arrays.asList(1, 4, 2)));
        expectedPolygons.add(expectedPolygon2);

        for (int i = 0; i < resPolygons.size(); i++) {
            ArrayList<Integer> expected = expectedPolygons.get(i).getVertexIndices();
            ArrayList<Integer> actual = resPolygons.get(i).getVertexIndices();
            for (int j = 0; j < actual.size(); j++) {
                assertEquals(expected.get(j), actual.get(j));
            }
        }
    }

    @Test
    void getTriangulatedPolygon() {
        ArrayList<Integer> vertexIndices = new ArrayList<>(Arrays.asList(2, 9, 1, 4, 6, 7));
        int indexSecondVertex = 2;

        ArrayList<Integer> actualIndices = Triangulation.getTriangulatedPolygon(vertexIndices, indexSecondVertex);
        ArrayList<Integer> expectedIndices = new ArrayList<>(Arrays.asList(2, 1, 4));

        for (int i = 0; i < actualIndices.size(); i++) {
            assertEquals(expectedIndices.get(i), actualIndices.get(i));
        }
    }
}