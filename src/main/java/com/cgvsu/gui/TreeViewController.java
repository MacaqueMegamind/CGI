package com.cgvsu.gui;

import com.cgvsu.model.Model;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.awt.*;

public class TreeViewController {

    private enum ItemType {
        CAMERA,
        OBJECT
    }

    public class ItemWrap {
        private Model model;
        private String name;
        private ItemType itemType;
        private Color color;

        public ItemWrap(Model model, String name, ItemType itemType) {
            this.model = model;
            this.name = name;
            this.itemType = itemType;
            this.color = null;
        }

        public ItemWrap(Model model, String name) {
            this.model = model;
            this.name = name;
            this.itemType = null;
            this.color = null;
        }

        public Model getModel() {
            return model;
        }

        public String getName() {
            return name;
        }

        public ItemType getItemType() {
            return itemType;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    private final TreeView<ItemWrap> treeView;

    private TreeItem<ItemWrap> cameraRoot;
    private TreeItem<ItemWrap> objectRoot;

    public TreeViewController(TreeView<ItemWrap> treeView) {
        this.treeView = treeView;
    }

    public void initialize() {
        TreeItem<ItemWrap> rootItem = new TreeItem<>(new ItemWrap(null, "Сцена", null));
        rootItem.setExpanded(true);

        // Инициализация разделов для CAMERA и OBJECT
        cameraRoot = new TreeItem<>(new ItemWrap(null, "Камеры", ItemType.CAMERA));
        objectRoot = new TreeItem<>(new ItemWrap(null, "Объекты", ItemType.OBJECT));

        rootItem.getChildren().addAll(cameraRoot, objectRoot);
        treeView.setRoot(rootItem);
    }

    public int getCameraCount() {
        return cameraRoot.getChildren().size();
    }

    public int getObjectCount() {
        return objectRoot.getChildren().size();
    }

    public void addItem(ItemWrap item) {
        TreeItem<ItemWrap> newItem = new TreeItem<>(item);
        if (item.getItemType() == ItemType.CAMERA) {
            cameraRoot.getChildren().add(newItem);
        } else if (item.getItemType() == ItemType.OBJECT) {
            objectRoot.getChildren().add(newItem);
        }
    }

}

