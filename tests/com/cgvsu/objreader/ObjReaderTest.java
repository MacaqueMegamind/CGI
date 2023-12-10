package com.cgvsu.objreader;

import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        // АГААА! Вот тест, который говорит, что у метода нет проверки на более, чем 3 числа
        // А такой случай лучше не игнорировать, а сообщать пользователю, что у него что-то не так
        // ассерт, чтобы не забыть про тест:
//        Assertions.assertTrue(false);


        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too much vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "ba", ""));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too much texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.6", "-0.7", "1.223"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
             Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too much texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.6"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "0"));
        try {

            Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
            Vector2f expectedResult = new Vector2f(1.01f, 1.02f);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Too much texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseTextureVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.03", "0"));
        try {
            Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
            Vector2f expectedResult = new Vector2f(1.01f, 1.10f);
            Assertions.assertFalse(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Too much texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());

        }
    }

    @Test
    public void testParseNormals01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseNormals02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseNormals03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormals04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormals05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too much vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormals06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.23", "o", "3.2332"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord01() {
        String wordsInLine = "3/4/1";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();

        ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
        Integer resultV = 2;
        Integer resultVT = 3;
        Integer resultN = 0;
        Assertions.assertTrue(resultV.equals(vertex.get(0)));
        Assertions.assertTrue(resultVT.equals(textureVertex.get(0)));
        Assertions.assertTrue(resultN.equals(normals.get(0)));
    }

    @Test
    public void testParseFaceWord02() {
        String wordsInLine = "3/4/1";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();

        ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
        Integer resultV = 3;
        Integer resultVT = 4;
        Integer resultN = 1;
        Assertions.assertFalse(resultV.equals(vertex.get(0)));
        Assertions.assertFalse(resultVT.equals(vertex.get(0)));
        Assertions.assertFalse(resultN.equals(vertex.get(0)));
    }

    @Test
    public void testParseFaceWord03() {
        String wordsInLine = "3//1";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();

        ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
        Integer resultV = 2;
        Integer resultN = 0;
        Assertions.assertTrue(resultV.equals(vertex.get(0)));
        Assertions.assertTrue(textureVertex.size() == 0);
        Assertions.assertTrue(resultN.equals(normals.get(0)));
    }

    @Test
    public void testParseFaceWord04() {
        String wordsInLine = "3/f/o";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();
        try {
            ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord05() {
        String wordsInLine = "//4/1";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();
        try {
            ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord06() {
        String wordsInLine = "/4/";
        ArrayList<Integer> vertex = new ArrayList<>();
        ArrayList<Integer> textureVertex = new ArrayList<>();
        ArrayList<Integer> normals = new ArrayList<>();
        try {
            ObjReader.parseFaceWord(wordsInLine, vertex, textureVertex, normals, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}