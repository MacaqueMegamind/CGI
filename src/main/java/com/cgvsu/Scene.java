package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene {
    String currentModel;

    public  ArrayList<Texture> textures = new ArrayList<>();

    public ArrayList<Model> loadedMeshes = new ArrayList<>();

    public HashMap<String, Model> loadedModels = new HashMap<>();

    private List<Camera> camera = new ArrayList<>();

    public String getCurrentModel() {
        return currentModel;
    }

    public HashMap<String, Model> getLoadedModels() {
        return loadedModels;
    }

    public List<Camera> getCamera() {
        return camera;
    }

    public void setCurrentModel(String currentModel) {
        this.currentModel = currentModel;
    }

    public void setLoadedModels(HashMap<String, Model> loadedModels) {
        this.loadedModels = loadedModels;
    }

    public void setCamera(List<Camera> camera) {
        this.camera = camera;
    }

    public List<Model> getLoadedMeshes() {
        return loadedMeshes;
    }

    public void addTexture(Texture texture){
        textures.remove(texture);
    }

    public void deleteTexture(Texture texture){
        textures.remove(texture);
    }


    public void render(GraphicsContext graphicsContext, Camera camera, int width, int height, Texture texture){
        for(Model model: loadedMeshes)
            RenderEngine.render(graphicsContext, camera, model, width, height);

    }


}
