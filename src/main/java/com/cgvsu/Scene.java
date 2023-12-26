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
    private String currentModel;
    private String currentTexture;

    private String currentCamera;
    private HashMap<String, Texture> textures = new HashMap<>();
    private HashMap<String, Camera> cameras = new HashMap<>();

    private HashMap<String, Model> loadedModels = new HashMap<>();

    private List<Camera> camera = new ArrayList<>();

    public String getCurrentModel() {
        return currentModel;
    }

    public String getCurrentTexture() {
        return currentTexture;
    }

    public Texture getCurrentTextureObject() {
        return textures.get(currentTexture);
    }

    public Model getCurrentModelObject() {
        return loadedModels.get(currentModel);
    }

    public HashMap<String, Model> getLoadedModels() {
        return loadedModels;
    }

    public Camera getCamera(String name) {
        return cameras.get(name);
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }

    public void setCurrentModel(String currentModel) {
        this.currentModel = currentModel;
    }

    public void setLoadedModels(HashMap<String, Model> loadedModels) {
        this.loadedModels = loadedModels;
    }

    public void setCurrentCamera(String currentCamera) {
        this.currentCamera = currentCamera;
    }

    public void addTexture(String name, Texture texture) {
        currentTexture = name;
        textures.put(name, texture);
    }


    public void deleteTexture(String name) {
        textures.remove(name);
    }

    public void deleteAllTextures(){
        textures.clear();
    }

    public void addCamera(String name, Camera camera) {
        currentCamera = name;
        cameras.put(name, camera);
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

    public void deleteCamera(String name){
        cameras.remove(name);
    }
    public void deleteAllCameras(){
        cameras.clear();
    }


    public void render(GraphicsContext graphicsContext, Camera camera, int width, int height) {
        for (String key : loadedModels.keySet()) {
            Texture texture = textures.get(key);
            Model model = loadedModels.get(key);
            if (texture == null) {
                model.setTextureMode(false);
            }
            RenderEngine.render(graphicsContext, camera, loadedModels.get(key), texture, width, height);
        }
    }


}
