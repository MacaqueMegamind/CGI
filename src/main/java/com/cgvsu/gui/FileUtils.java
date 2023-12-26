package com.cgvsu.gui;

import com.cgvsu.AlertProcessing;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.canvas.Canvas;

public class FileUtils {
    public record FileInfo(String content, String name, String path) {}
    public record DirInfo(Path path) {}

    public FileInfo fileChooserOpenModel(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return null;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            return new FileInfo(Files.readString(fileName), file.getName(), file.getAbsolutePath());
            // todo: обработка ошибок
        } catch (IOException exception){
            AlertProcessing.showErrorDialog(exception, getClass().getSimpleName());
            return null;
        }
    }

    public Path fileChooserOpenTexture(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return null;
        }

        return Path.of(file.getAbsolutePath());
    }

    public DirInfo dirChooserOpen(Canvas canvas) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Load Directory");

        File directory = directoryChooser.showDialog((Stage) canvas.getScene().getWindow());
        if (directory == null) {
            return null;
        }

        return new DirInfo(Paths.get(directory.getAbsolutePath()));
    }

}
