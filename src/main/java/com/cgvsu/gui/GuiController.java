package com.cgvsu.gui;

import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.DrawModes;
import com.cgvsu.render_engine.RenderEngine;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import com.cgvsu.math.vector.Vector3f;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class GuiController {

    final private float TRANSLATION = 0.5F;

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


    private Model mesh = null;
    private Texture texture = null;

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
                camera.moveRotation(new Vector3f((float) deltaX, (float) deltaY, 0));
            } else if (event.isSecondaryButtonDown()) {
                // Translate
                camera.movePosition(new Vector3f((float) deltaX, (float) -deltaY, 0));
            }

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        // Обработка событий клавиатуры
        canvas.setOnKeyPressed(this::handleKeyPress);


        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(60), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, texture, (int) width, (int) height);
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
                treeViewController.deleteSelectedItem();
                mesh = null;
            } else if (event.getCode() == KeyCode.ENTER) {
                mesh = treeViewController.getSelectedItem().getValue().getModel();
            }
        });

        treeView.setFocusTraversable(false);
        treeView.setOnMouseClicked(mouseEvent -> treeView.requestFocus());

    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
                break;
            case S:
                camera.movePosition(new Vector3f(0, 0, TRANSLATION));
                break;
            case A:
                camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
                break;
            case D:
                camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
                break;
            case Q:
                camera.movePosition(new Vector3f(0, TRANSLATION, 0));
                break;
            case E:
                camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
                break;
        }
    }


    @FXML
    public void handleUploadModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpen(canvas);

        mesh = ObjReader.read(file.content());

//        AffineTransformation af = new AffineTransformation();
//        af.setScale(new DefaultScale(10, 10, 10));
//        af.calculate(mesh.vertices);

        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);
        treeViewController.deleteAllObjects();
        treeViewController.addItem(new TreeViewController.ItemWrap(mesh, file.name(),
                TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleAddModel() {
        FileUtils.FileInfo file = new FileUtils().fileChooserOpen(canvas);

        mesh = ObjReader.read(file.content());
        Triangulation.triangulateModel(mesh);
        CalculationNormals.calculateNormals(mesh);
        treeViewController.addItem(new TreeViewController.ItemWrap(mesh, file.name(),
                TreeViewController.ItemType.OBJECT));
    }

    @FXML
    public void handleExportModel() throws IOException {
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
        DrawModes.ChangeMeshMode(mesh);
    }

    @FXML
    public void handleUseLight() {
        DrawModes.ChangeLightedMode(mesh);
    }

    @FXML
    public void handleInterfaceMode() {

    }

    @FXML
    public void handleInfo() {

    }

    @FXML
    public void handleUploadTexture() {
        texture = new Texture(Path.of("E:\\Admin\\DevAndProgs\\ProjectsJava\\ComputerGraphics\\FinalTask\\CGI\\assets\\models\\AlexWithTexture\\NeutralWrapped.jpg")) ;
//        texture = new Texture(Color.CYAN);
//        texture = new Texture(Path.of("C:\\Users\\MaxOmenes\\Documents\\!Root\\Development\\Java\\CGI\\CGI_task4\\assets\\models\\Cube\\texture.png"));

        DrawModes.EnableTextureMode(mesh);
    }

    @FXML
    public void handleDeleteTexture() {
        texture = null;

        DrawModes.DisableTextureMode(mesh);
    }
}
