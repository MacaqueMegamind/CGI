package com.cgvsu.render_engine;

import com.cgvsu.model.Model;

public class DrawModes {
    public static void ChangeMeshMode(Model mesh) {
        mesh.setMeshMode(!mesh.isMeshMode());
    }
    public static void EnableMeshMode(Model mesh) {
        mesh.setMeshMode(true);
    }
    public static void DisableMeshMode(Model mesh) {
        mesh.setMeshMode(false);
    }

    public static void ChangeTextureMode(Model mesh) {
        mesh.setTextureMode(!mesh.isTextureMode());
    }
    public static void EnableTextureMode(Model mesh) {
        mesh.setTextureMode(true);
    }
    public static void DisableTextureMode(Model mesh) {
        mesh.setTextureMode(false);
    }

    public static void ChangeLightedMode(Model mesh) {
        mesh.setLightedMode(!mesh.isLightedMode());
    }
    public static void EnableLightedMode(Model mesh) {
        mesh.setLightedMode(true);
    }
    public static void DisableLightedMode(Model mesh) {
        mesh.setLightedMode(false);
    }
}
