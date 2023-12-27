package com.cgvsu.gui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.awt.*;

public class TreeViewController {

    public enum ItemType {
        CAMERA,
        OBJECT
    }

    public static class ItemWrap {
        private final String name;
        private final ItemType itemType;
        private Color color;

        public ItemWrap(String name, ItemType itemType) {
            this.name = name;
            this.itemType = itemType;
            this.color = null;
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
        TreeItem<ItemWrap> rootItem = new TreeItem<>(new ItemWrap("Сцена", null));
        rootItem.setExpanded(true);

        // Инициализация разделов для CAMERA и OBJECT
        cameraRoot = new TreeItem<>(new ItemWrap("Камеры", ItemType.CAMERA));
        objectRoot = new TreeItem<>(new ItemWrap("Объекты", ItemType.OBJECT));

        rootItem.getChildren().addAll(cameraRoot, objectRoot);
        treeView.setRoot(rootItem);
    }

    public void deleteSelectedItem() {
        TreeItem<ItemWrap> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getParent() != null) {
            // Удаляем элемент из родительского элемента
            selectedItem.getParent().getChildren().remove(selectedItem);
        }
    }

    public void deleteAllObjects() {
        objectRoot.getChildren().clear();
    }

    public void deleteAllCameras() {
        cameraRoot.getChildren().clear();
    }

    public TreeItem<ItemWrap> getSelectedItem() {
        return treeView.getSelectionModel().getSelectedItem();
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

    public ItemWrap getItemByName(String name) {
        for (TreeItem<ItemWrap> item : objectRoot.getChildren()) {
            if (item.getValue().getName() != null && item.getValue().getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }

}

