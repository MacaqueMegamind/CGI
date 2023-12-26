package com.cgvsu.model;

import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;

import java.util.*;

public class Model {

    private boolean isMeshMode = true;
    private boolean isTextureMode = false;
    private boolean isLightedMode = false;

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public Model(Model model) {
    }

    public Model() {

    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setPolygons(ArrayList<Polygon> polygons) { this.polygons = polygons; }
    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public boolean isMeshMode() {
        return isMeshMode;
    }

    public void setMeshMode(boolean meshMode) {
        isMeshMode = meshMode;
    }

    public boolean isTextureMode() {
        return isTextureMode;
    }

    public void setTextureMode(boolean textureMode) {
        isTextureMode = textureMode;
    }

    public boolean isLightedMode() {
        return isLightedMode;
    }

    public void setLightedMode(boolean lightedMode) {
        isLightedMode = lightedMode;
    }
}
