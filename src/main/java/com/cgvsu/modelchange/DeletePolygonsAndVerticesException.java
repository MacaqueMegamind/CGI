package com.cgvsu.modelchange;

public class DeletePolygonsAndVerticesException extends ArrayIndexOutOfBoundsException {
    public DeletePolygonsAndVerticesException(String errorMessage) {
        super("Error DeletePolygonsAndVertices: "  + errorMessage);
    }
}
