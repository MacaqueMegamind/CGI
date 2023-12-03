package com.cgvsu.objwriter;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReader;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjWriterTest {
    @Test
    public void testWriteObj() throws IOException {
        String fileContent = TestStrings.TEST_1;
        Model model = ObjReader.read(fileContent);
        StringWriter stringWriter = new StringWriter();
        ObjWriter.write(model, stringWriter);
        String objString = stringWriter.toString();
        assertEquals(fileContent, objString);
    }


    @Test
    public void testEmptyModel() throws IOException {
        String fileContent = TestStrings.TEST_2;
        Model model = ObjReader.read(fileContent);
        StringWriter stringWriter = new StringWriter();
        ObjWriter.write(model, stringWriter);
        String objString = stringWriter.toString();
        assertEquals(fileContent, objString);
    }

    @Test
    public void testCrashModel()  {
        assertThrows(AssertionError.class, () -> someMethod());
    }

    private static class TestStrings {
        private static final String TEST_1 = """
                   v 1.0 0.0 0.0
                   v 0.0 1.0 0.0
                   v 0.0 0.0 1.0
                   vt 0.0 0.0
                   vt 1.0 0.0
                   vt 0.0 1.0
                   vn 0.0 0.0 1.0
                   f 1/1/1 2/2/1 3/3/1
                   """;
        private static final String TEST_2 = "";
    }
    private void someMethod() throws IOException {
        Model model = new Model();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        Polygon polygon = new Polygon();
        polygon.setTextureVertexIndices(list);
        model.polygons.add(polygon);
        StringWriter stringWriter = new StringWriter();
        ObjWriter.write(model, stringWriter);
    }
}

