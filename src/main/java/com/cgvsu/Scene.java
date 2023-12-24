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

    public HashMap<String, Model> loadedModels = new HashMap<>();

    private List<Camera> camera = new ArrayList<>();

    public String getCurrentModel() {
        return currentModel;
    }

    public Model getCurrentModelObject() {
        return loadedModels.get(currentModel);
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

    public void addTexture(Texture texture){
        textures.remove(texture);
    }

    public void deleteTexture(Texture texture){
        textures.remove(texture);
    }

    public void addModel(Model model, String name) {
        currentModel = name;
        loadedModels.put(name, model);
    }

    public void deleteAllModels() {
        loadedModels.clear();
    }

    public void deleteModel(String name) {
        loadedModels.remove(name);
    }


    public void render(GraphicsContext graphicsContext, Camera camera, int width, int height) {
        for (Model model: loadedModels.values()) {
            RenderEngine.render(graphicsContext, camera, model, width, height);
        }
    }


}
