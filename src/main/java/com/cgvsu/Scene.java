package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.transformation.AffineTransformation;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene {
    private String currentModel;

    private String currentCamera;
    private HashMap<String, Texture> textures = new HashMap<>();
    private HashMap<String, Camera> cameras = new HashMap<>();

    private HashMap<String, Model> loadedModels = new HashMap<>();

    private HashMap<String, AffineTransformation> affineTransformations = new HashMap<>();

    private List<Camera> camera = new ArrayList<>();

    public AffineTransformation getCurrentTransformation() {
        return affineTransformations.get(currentModel);
    }

    public void setAffineTransformation(String name, AffineTransformation affineTransformation) {
        this.affineTransformations.put(name, affineTransformation);
    }

    public String getCurrentModel() {
        return currentModel;
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

    public Integer getCameraCount() {
        return cameras.keySet().size();
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

    public Camera getCurrentCamera() {
        return cameras.get(currentCamera);
    }

    public void addModel(Model model, String name) {
        currentModel = name;
        loadedModels.put(name, model);
        this.setAffineTransformation(name, new AffineTransformation());
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
            model.setTextureMode(!(texture == null));
            RenderEngine.render(graphicsContext, camera, loadedModels.get(key), texture, width, height);
        }
    }


}
