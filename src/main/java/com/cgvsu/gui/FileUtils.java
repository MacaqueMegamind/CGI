package com.cgvsu.gui;

import com.cgvsu.Error_Processing;
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
    public record FileInfo(String content, String name) {}
    public record DirInfo(Path path) {}

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
        } catch (IOException exception){
            Error_Processing error_processing = new Error_Processing();
            error_processing.showErrorDialog(exception, getClass().getSimpleName());
            return null;
        }
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
