package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.transformation.AffineTransformation;
import com.cgvsu.render_engine.transformation.DefaultRotate;
import com.cgvsu.render_engine.transformation.DefaultScale;
import com.cgvsu.render_engine.transformation.DefaultTransition;
import com.cgvsu.triangulation.CalculationNormals;
import com.cgvsu.triangulation.Triangulation;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import com.cgvsu.math.vector.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private double mouseX, mouseY;


    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

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

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);

            Triangulation.triangulateModel(mesh);
            CalculationNormals.calculateNormals(mesh);

            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    // Обработка событий клавиатуры
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
}