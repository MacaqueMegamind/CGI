package com.cgvsu.gui;

import com.cgvsu.AlertProcessing;
import com.cgvsu.Scene;
import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.DrawModes;
import com.cgvsu.triangulation.CalculationNormals;
import com.cgvsu.triangulation.Triangulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import com.cgvsu.math.vector.Vector3f;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.cgvsu.render_engine.GraphicConveyor.rotateModel;
import static com.cgvsu.render_engine.GraphicConveyor.rotateScene;

public class GuiController {

    final private float TRANSLATION = 0.5F;
    final private float fovDelta = 0.05f;
    final private float maxFov = 3f;

    @FXML
    private ScrollPane topScrollPane;

    @FXML
    private ScrollPane bottomScrollPane;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane anchorPaneCanvas;

    private final TreeView<TreeViewController.ItemWrap> treeView = new TreeView<>();

    private final TreeViewController treeViewController = new TreeViewController(treeView);

    private double mouseX, mouseY;

    private final Scene scene = new Scene();

    private final Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private final Map<KeyCode, Runnable> keyActions = new HashMap<>();

    @FXML
    private void initialize() {
        anchorPaneCanvas.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPaneCanvas.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        canvas.setOnMouseDragged(event -> {
            double deltaX = (event.getSceneX() - mouseX);
            double deltaY = (event.getSceneY() - mouseY);

            if (event.isPrimaryButtonDown()) {
                // Rotate
                rotateModel((float) deltaY, (float) deltaX, 0, scene.getCurrentModelObject(), camera);
            } else if (event.isSecondaryButtonDown()) {
                // Translate
//                camera.movePosition(new Vector3f((float) deltaX, (float) -deltaY, 0));
                rotateScene((float) deltaY, (float) deltaX, 0, scene, camera);
            }

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        canvas.setOnScroll(scrollEvent -> {

            float newFov = camera.getFov();

            if (scrollEvent.getDeltaY() < 0){
                if (newFov <= maxFov) {
                    newFov += fovDelta;
                }
            } else if (scrollEvent.getDeltaY() > 0) {
                if (newFov >= fovDelta) {
                    newFov -= fovDelta;
                }
            }

            camera.setFov(newFov);
        });

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(120), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            if (scene.getCurrentModel() != null) {
                scene.render(canvas.getGraphicsContext2D(), camera, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        topScrollPane.setContent(treeView);
        treeView.prefHeightProperty().bind(topScrollPane.heightProperty());
        treeView.prefWidthProperty().bind(topScrollPane.widthProperty());
        treeViewController.initialize();

        keyActions.put(KeyCode.UP, () -> camera.movePosition(new Vector3f(0, TRANSLATION, 0)));
        keyActions.put(KeyCode.DOWN, () -> camera.movePosition(new Vector3f(0, -TRANSLATION, 0)));
        keyActions.put(KeyCode.RIGHT, () -> camera.movePosition(new Vector3f(-TRANSLATION, 0, 0)));
        keyActions.put(KeyCode.LEFT, () -> camera.movePosition(new Vector3f(TRANSLATION, 0, 0)));
        keyActions.put(KeyCode.W, () -> camera.movePosition(new Vector3f(0, 0, -TRANSLATION)));
        keyActions.put(KeyCode.S, () -> camera.movePosition(new Vector3f(0, 0, TRANSLATION)));

        canvas.setOnKeyPressed(e -> {
            Runnable action = keyActions.get(e.getCode());
            if (action != null) {
                action.run();
            }
        });

        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(event -> canvas.requestFocus());

        treeView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                scene.deleteModel(treeViewController.getSelectedItem().getValue().getName());
                treeViewController.deleteSelectedItem();
            } else if (event.getCode() == KeyCode.ENTER) {
                TreeViewController.ItemWrap itemWrap = treeViewController.getSelectedItem().getValue();
                scene.setCurrentModel(itemWrap.getName());
            }
        });

        treeView.setFocusTraversable(false);
        treeView.setOnMouseClicked(mouseEvent -> treeView.requestFocus());

    }


    @FXML
    public void handleUploadModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpenModel(canvas);

        Model mesh = ObjReader.read(file.content());

        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);

        scene.deleteAllModels();
        scene.addModel(mesh, file.name());

        treeViewController.deleteAllObjects();
        treeViewController.addItem(new TreeViewController.ItemWrap(mesh, file.name(),
                TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleAddModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpenModel(canvas);
        Model mesh = ObjReader.read(file.content());
        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);

        scene.addModel(mesh, file.name());
        treeViewController.addItem(new TreeViewController.ItemWrap(mesh, file.name(),
                TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleExportModel() throws IOException {
        Model mesh = scene.getCurrentModelObject();

        FileUtils.DirInfo dir = new FileUtils().dirChooserOpen(canvas);
        TreeViewController.ItemWrap itemWrap = treeViewController.getItemByMesh(mesh);
        Path path = dir.path().resolve(itemWrap.getName());
        FileWriter fileWriter = new FileWriter(path.toString());
        ObjWriter.write(mesh, fileWriter);
    }

    @FXML
    public void handleDeleteVertices() {

    }

    @FXML
    public void handleShowMesh() {
        DrawModes.changeMeshMode(scene.getCurrentModelObject());
    }

    @FXML
    public void handleUseLight() {
        DrawModes.changeLightedMode(scene.getCurrentModelObject());
    }

    @FXML
    public void handleInterfaceMode() {

    }

    @FXML
    public void handleInfo() {
        AlertProcessing.showInfoDialog("Информация о проекте", "Инструкция по использованию", "Как пользоваться? Тут все интуитивно понятно");
    }

    @FXML
    public void handleUploadTexture() {
        Path path = new FileUtils().fileChooserOpenTexture(canvas);

        Texture texture = new Texture(path);

        scene.addTexture(scene.getCurrentModel(), texture);

        DrawModes.enableTextureMode(scene.getCurrentModelObject());
    }

    @FXML
    public void handleDeleteTexture() {

        DrawModes.disableTextureMode(scene.getCurrentModelObject());
    }
}
