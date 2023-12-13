package com.cgvsu.gui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.canvas.Canvas;

public class FileUtils {
    public record FileInfo(String content, String name) {
    }

    public FileInfo fileChooserOpen(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return null;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            return new FileInfo(Files.readString(fileName), file.getName());
            // todo: обработка ошибок
        } catch (IOException exception) {return null;}
    }

}
