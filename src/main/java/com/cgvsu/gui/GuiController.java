package com.cgvsu.gui;

import com.cgvsu.AlertProcessing;
import com.cgvsu.Scene;
import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.modelchange.DeletePolygonsAndVertices;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.DrawModes;
import com.cgvsu.triangulation.CalculationNormals;
import com.cgvsu.triangulation.Triangulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import com.cgvsu.math.vector.Vector3f;

import java.awt.*;
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
    public Button colorTextureButton;
    @FXML
    public TextField textureColorField;
    @FXML
    public TextField cameraX;
    @FXML
    public TextField cameraY;
    @FXML
    public TextField cameraZ;
    public Button createCameraButton;
    @FXML
    private Slider moveZ;
    @FXML
    private Slider moveY;
    @FXML
    private Slider moveX;
    @FXML
    private Slider scaleX;
    @FXML
    private Slider scaleY;
    @FXML
    private Slider scaleZ;
    @FXML
    private Slider rotateX;
    @FXML
    private Slider rotateY;
    @FXML
    private Slider rotateZ;
    @FXML
    private Button confirmTransformation;

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

    private boolean darkTheme = true;


    private final Map<KeyCode, Runnable> keyActions = new HashMap<>();

    private SliderController sliderController;

    @FXML
    private void initialize() {
        treeViewController.initialize();

        Camera camera1 = new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);
        scene.addCamera("camera", camera1);

        treeViewController.addItem(new TreeViewController.ItemWrap("camera", TreeViewController.ItemType.CAMERA));

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
                rotateModel((float) deltaY, (float) deltaX, 0, scene.getCurrentModelObject(), scene.getCurrentCamera());
            } else if (event.isSecondaryButtonDown()) {
                // Translate
//                camera.movePosition(new Vector3f((float) deltaX, (float) -deltaY, 0));
                rotateScene((float) deltaY, (float) deltaX, 0, scene, scene.getCurrentCamera());
            }

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        canvas.setOnScroll(scrollEvent -> {

            float newFov = scene.getCurrentCamera().getFov();

            if (scrollEvent.getDeltaY() < 0){
                if (newFov <= maxFov) {
                    newFov += fovDelta;
                }
            } else if (scrollEvent.getDeltaY() > 0) {
                if (newFov >= fovDelta) {
                    newFov -= fovDelta;
                }
            }

            scene.getCurrentCamera().setFov(newFov);
        });

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        
        KeyFrame frame = new KeyFrame(Duration.millis(120), event -> {

            double width = anchorPaneCanvas.getWidth();
            double height = anchorPaneCanvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            scene.getCurrentCamera().setAspectRatio((float) (width / height));
            if (scene.getCurrentModel() != null) {
                scene.render(canvas.getGraphicsContext2D(), scene.getCurrentCamera(), (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        topScrollPane.setContent(treeView);
        treeView.prefHeightProperty().bind(topScrollPane.heightProperty());
        treeView.prefWidthProperty().bind(topScrollPane.widthProperty());

        keyActions.put(KeyCode.UP, () -> scene.getCurrentCamera().movePosition(new Vector3f(0, TRANSLATION, 0)));
        keyActions.put(KeyCode.DOWN, () -> scene.getCurrentCamera().movePosition(new Vector3f(0, -TRANSLATION, 0)));
        keyActions.put(KeyCode.RIGHT, () -> scene.getCurrentCamera().movePosition(new Vector3f(-TRANSLATION, 0, 0)));
        keyActions.put(KeyCode.LEFT, () -> scene.getCurrentCamera().movePosition(new Vector3f(TRANSLATION, 0, 0)));
        keyActions.put(KeyCode.W, () -> scene.getCurrentCamera().movePosition(new Vector3f(0, 0, -TRANSLATION)));
        keyActions.put(KeyCode.S, () -> scene.getCurrentCamera().movePosition(new Vector3f(0, 0, TRANSLATION)));

        sliderController = new SliderController(moveX, moveY, moveZ, rotateX, rotateY, rotateZ, scaleX, scaleY, scaleZ);
        moveX.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.X, SliderEvent.Operation.MOVE, moveX, sliderController));
        moveY.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Y, SliderEvent.Operation.MOVE, moveX, sliderController));
        moveZ.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Z, SliderEvent.Operation.MOVE, moveX, sliderController));

        rotateX.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.X, SliderEvent.Operation.ROTATE, rotateX, sliderController));
        rotateY.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Y, SliderEvent.Operation.ROTATE, rotateY, sliderController));
        rotateZ.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Z, SliderEvent.Operation.ROTATE, rotateZ, sliderController));

        scaleX.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.X, SliderEvent.Operation.SCALE, scaleX, sliderController));
        scaleY.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Y, SliderEvent.Operation.SCALE, scaleY, sliderController));
        scaleZ.valueProperty().addListener(new SliderEvent(scene, SliderEvent.Direction.Z, SliderEvent.Operation.SCALE, scaleZ, sliderController));


        canvas.setOnKeyPressed(e -> {
            Runnable action = keyActions.get(e.getCode());
            if (action != null) {
                action.run();
            }
        });

        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(event -> canvas.requestFocus());

        treeView.setOnKeyPressed(event -> {
            TreeViewController.ItemWrap itemWrap = treeViewController.getSelectedItem().getValue();
            if (event.getCode() == KeyCode.DELETE) {
                if (itemWrap.getItemType() == TreeViewController.ItemType.CAMERA) {
                    scene.deleteCamera(itemWrap.getName());
                } else {
                    scene.deleteModel(itemWrap.getName());
                    sliderController.deleteModel(itemWrap.getName());
                }
                treeViewController.deleteSelectedItem();
            } else if (event.getCode() == KeyCode.ENTER) {
                if (itemWrap.getItemType() == TreeViewController.ItemType.CAMERA) {
                    scene.setCurrentCamera(itemWrap.getName());
                } else {
                    scene.setCurrentModel(itemWrap.getName());
                    slidersUpdate();
                }
            }
        });

        treeView.setFocusTraversable(false);
        treeView.setOnMouseClicked(mouseEvent -> treeView.requestFocus());
    }


    public void slidersUpdate() {
        sliderController.updateSliders(scene.getCurrentModel());
    }


    @FXML
    public void handleUploadModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpenModel(canvas);

        Model mesh = ObjReader.read(file.content());

        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);

        scene.deleteAllModels();
        scene.addModel(mesh, file.name());
        slidersUpdate();

        treeViewController.deleteAllObjects();
        treeViewController.addItem(new TreeViewController.ItemWrap(file.name(), TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleAddModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpenModel(canvas);
        Model mesh = ObjReader.read(file.content());
        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);

        scene.addModel(mesh, file.name());
        slidersUpdate();
        treeViewController.addItem(new TreeViewController.ItemWrap(file.name(), TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleExportModel() throws IOException {
        Model mesh = scene.getCurrentModelObject();
        String name = scene.getCurrentModel();

        FileUtils.DirInfo dir = new FileUtils().dirChooserOpen(canvas);
        TreeViewController.ItemWrap itemWrap = treeViewController.getItemByName(name);
        Path path = dir.path().resolve(itemWrap.getName());
        FileWriter fileWriter = new FileWriter(path.toString());
        ObjWriter.write(mesh, fileWriter);
    }

    @FXML
    public void handleDeleteVertices() {
        int[] vertices = new FileUtils().fileChooserOpenDeleteVertices(canvas);
        DeletePolygonsAndVertices.removeVertices(scene.getCurrentModelObject(), vertices);
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
        darkTheme = !darkTheme;
        if (!darkTheme) {
            canvas.getScene().getStylesheets().add(getClass().getResource("fxml/dark.css").toExternalForm());
        } else {
            canvas.getScene().getStylesheets().remove(getClass().getResource("fxml/dark.css").toExternalForm());
        }
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
        scene.deleteTexture(scene.getCurrentModel());
        DrawModes.disableTextureMode(scene.getCurrentModelObject());
    }

    @FXML
    public void handleCameraCreate(ActionEvent actionEvent) {
        float x = Float.parseFloat(cameraX.getText());
        float y = Float.parseFloat(cameraY.getText());
        float z = Float.parseFloat(cameraZ.getText());

        Camera camera =  new Camera(
                new Vector3f(x, y, z),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);

        String name = "camera" + scene.getCameraCount().toString();
        scene.addCamera(name, camera);
        treeViewController.addItem(new TreeViewController.ItemWrap(name, TreeViewController.ItemType.CAMERA));
    }

    @FXML
    public void handleColorTexture(ActionEvent actionEvent) {
        Texture texture = new Texture(Color.decode(textureColorField.getText()));
        scene.addTexture(scene.getCurrentModel(), texture);
    }
}
