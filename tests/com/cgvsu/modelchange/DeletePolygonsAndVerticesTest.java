package com.cgvsu.modelchange;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeletePolygonsAndVerticesTest {

        @Test
        public void testDeletePolygonsAndVertices() throws IOException {
            String fileContent = TestStrings.TEST_CUBE;
            Model model = ObjReader.read(fileContent);
            StringWriter stringWriter = new StringWriter();
            int[] vertexIndicesToRemove = new int[2];
            vertexIndicesToRemove[0] = 5;
            vertexIndicesToRemove[1] = 1;
            DeletePolygonsAndVertices.removeVertices(model, vertexIndicesToRemove);
            ObjWriter.write(model, stringWriter);
            String objString = stringWriter.toString();
            assertEquals(objString, TestStrings.TEST_CUBE_CHANGE);
        }

    @Test
    public void testIndexOutOfBounds() throws IOException {
        assertThrows(DeletePolygonsAndVerticesException.class, () -> someMethod());
    }






    private static class TestStrings {
        private static final String TEST_CUBE = """
                  
                o Cube
                v 1.000000 -1.000000 -1.000000
                v 1.000000 -1.000000 1.000000
                v -1.000000 -1.000000 1.000000
                v -1.000000 -1.000000 -1.000000
                v 1.000000 1.000000 -0.999999
                v 0.999999 1.000000 1.000001
                v -1.000000 1.000000 1.000000
                v -1.000000 1.000000 -1.000000
                vt 1.000000 0.333333
                vt 1.000000 0.666667
                vt 0.666667 0.666667
                vt 0.666667 0.333333
                vt 0.666667 0.000000
                vt 0.000000 0.333333
                vt 0.000000 0.000000
                vt 0.333333 0.000000
                vt 0.333333 1.000000
                vt 0.000000 1.000000
                vt 0.000000 0.666667
                vt 0.333333 0.333333
                vt 0.333333 0.666667
                vt 1.000000 0.000000
                vn 0.000000 -1.000000 0.000000
                vn 0.000000 1.000000 0.000000
                vn 1.000000 0.000000 0.000000
                vn -0.000000 0.000000 1.000000
                vn -1.000000 -0.000000 -0.000000
                vn 0.000000 0.000000 -1.000000
                s off
                f 2/1/1 3/2/1 4/3/1
                f 8/1/2 7/4/2 6/5/2
                f 5/6/3 6/7/3 2/8/3
                f 6/8/4 7/5/4 3/4/4
                f 3/9/5 7/10/5 8/11/5
                f 1/12/6 4/13/6 8/11/6
                f 1/4/1 2/1/1 4/3/1
                f 5/14/2 8/1/2 6/5/2
                f 1/12/3 5/6/3 2/8/3
                f 2/12/4 6/8/4 3/4/4
                f 4/13/5 3/9/5 8/11/5
                f 5/6/6 1/12/6 8/11/6
                   """;
        private static final String TEST_CUBE_CHANGE = """
                v 1.0 -1.0 -1.0
                v -1.0 -1.0 1.0
                v -1.0 -1.0 -1.0
                v 1.0 1.0 -0.999999
                v -1.0 1.0 1.0
                v -1.0 1.0 -1.0
                vt 1.0 0.333333
                vt 1.0 0.666667
                vt 0.666667 0.666667
                vt 0.666667 0.333333
                vt 0.666667 0.0
                vt 0.0 0.333333
                vt 0.0 0.0
                vt 0.333333 0.0
                vt 0.333333 1.0
                vt 0.0 1.0
                vt 0.0 0.666667
                vt 0.333333 0.333333
                vt 0.333333 0.666667
                vt 1.0 0.0
                vn 0.0 -1.0 0.0
                vn 0.0 1.0 0.0
                vn 1.0 0.0 0.0
                vn -0.0 0.0 1.0
                vn -1.0 -0.0 -0.0
                vn 0.0 0.0 -1.0
                f 2/9/5 5/10/5 6/11/5
                f 1/12/6 3/13/6 6/11/6
                f 3/13/5 2/9/5 6/11/5
                f 4/6/6 1/12/6 6/11/6
                    """;
    }

    private void someMethod() throws IOException {
        String fileContent = TestStrings.TEST_CUBE;
        Model model = ObjReader.read(fileContent);
        StringWriter stringWriter = new StringWriter();
        int[] vertexIndicesToRemove = new int[2];
        vertexIndicesToRemove[0] = 10;
        vertexIndicesToRemove[1] = 1;
        DeletePolygonsAndVertices.removeVertices(model, vertexIndicesToRemove);
        ObjWriter.write(model, stringWriter);
        String objString = stringWriter.toString();
        assertEquals(objString, TestStrings.TEST_CUBE_CHANGE);
    }

}
