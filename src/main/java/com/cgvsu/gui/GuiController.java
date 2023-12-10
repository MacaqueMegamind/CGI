package com.cgvsu.gui;

import com.cgvsu.model.Model;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import com.cgvsu.math.vector.Vector3f;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane topScrollPane;

    @FXML
    private ScrollPane bottomScrollPane;

    @FXML
    private Canvas canvas;

    private TreeView<TreeViewController.ItemWrap> treeView = new TreeView<>();

    private TreeViewController treeViewController = new TreeViewController(treeView);

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        borderPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        borderPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                //RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        topScrollPane.setContent(treeView);
        treeView.prefHeightProperty().bind(topScrollPane.heightProperty());
        treeView.prefWidthProperty().bind(topScrollPane.widthProperty());

        treeViewController.initialize();
    }

    @FXML
    public void handleUploadModel(ActionEvent actionEvent) {

    }

    @FXML
    public void handleAddModel(ActionEvent actionEvent) {

    }

    @FXML
    public void handleExportModel(ActionEvent actionEvent) {

    }

    @FXML
    public void handleDeleteVertices(ActionEvent actionEvent) {

    }

    @FXML
    public void handleShowMesh(ActionEvent actionEvent) {

    }

    @FXML
    public void handleUseLight(ActionEvent actionEvent) {

    }

    @FXML
    public void handleInterfaceMode(ActionEvent actionEvent) {

    }

    @FXML
    public void handleInfo(ActionEvent actionEvent) {

    }
}
