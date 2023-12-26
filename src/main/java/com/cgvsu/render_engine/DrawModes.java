package com.cgvsu.render_engine;

import com.cgvsu.model.Model;

public class DrawModes {
    public static void changeMeshMode(Model mesh) {
        mesh.setMeshMode(!mesh.isMeshMode());
    }
    public static void enableMeshMode(Model mesh) {
        mesh.setMeshMode(true);
    }
    public static void disableMeshMode(Model mesh) {
        mesh.setMeshMode(false);
    }

    public static void changeTextureMode(Model mesh) {
        mesh.setTextureMode(!mesh.isTextureMode());
    }
    public static void enableTextureMode(Model mesh) {
        mesh.setTextureMode(true);
    }
    public static void disableTextureMode(Model mesh) {
        mesh.setTextureMode(false);
    }

    public static void changeLightedMode(Model mesh) {
        mesh.setLightedMode(!mesh.isLightedMode());
    }
    public static void enableLightedMode(Model mesh) {
        mesh.setLightedMode(true);
    }
    public static void disableLightedMode(Model mesh) {
        mesh.setLightedMode(false);
    }
}
