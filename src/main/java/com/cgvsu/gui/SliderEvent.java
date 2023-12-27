package com.cgvsu.gui;

import com.cgvsu.Scene;
import com.cgvsu.render_engine.transformation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class SliderEvent implements ChangeListener {
    private final Scene scene;
    private final Direction direction;
    private final Operation operation;
    private final Slider slider;
    private final SliderController sliderController;

    public enum Direction {
        X, Y, Z
    }

    public enum Operation {
        MOVE, SCALE, ROTATE
    }

    public SliderEvent(Scene scene, Direction direction, Operation operation, Slider slider,
                       SliderController sliderController) {
        super();
        this.scene = scene;
        this.direction = direction;
        this.operation = operation;
        this.slider = slider;
        this.sliderController = sliderController;
    }

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        String currentModel = scene.getCurrentModel();
        if (currentModel == null) {
            return;
        }

        Float newValue = Float.parseFloat(t1.toString());
        AffineTransformation affineTransformation = scene.getCurrentTransformation();
        Float lastValue = sliderController.getValue(currentModel, slider);
        if (lastValue == null) {
            return;
        }

        if (operation == Operation.MOVE) {
            DefaultTransition transition = (DefaultTransition) affineTransformation.getTransition();
            if (direction == Direction.X) {
                transition.setTransitionX(lastValue - newValue);
            }
            else if (direction == Direction.Y) {
                transition.setTransitionY(lastValue - newValue);
            }
            else if (direction == Direction.Z) {
                transition.setTransitionZ(lastValue - newValue);
            }
            affineTransformation.calculate(scene.getCurrentModelObject().vertices);
            transition.clear();
        } else if (operation == Operation.ROTATE) {
            DefaultRotate rotate = (DefaultRotate) affineTransformation.getRotate();
            if (direction == Direction.X) {
                rotate.setRotateX(lastValue - newValue);
            }
            else if (direction == Direction.Y) {
                rotate.setRotateY(lastValue - newValue);
            }
            else if (direction == Direction.Z) {
                rotate.setRotateZ(lastValue - newValue);
            }

            affineTransformation.calculate(scene.getCurrentModelObject().vertices);
            rotate.clear();
        } else if (operation == Operation.SCALE) {
            DefaultScale scale = (DefaultScale) affineTransformation.getScale();
            if (direction == Direction.X) {
                scale.setScaleX(1 + lastValue - newValue);
            }
            else if (direction == Direction.Y) {
                scale.setScaleY(1 + lastValue - newValue);
            }
            else if (direction == Direction.Z) {
                scale.setScaleZ(1 + lastValue - newValue);
            }

            affineTransformation.calculate(scene.getCurrentModelObject().vertices);
            scale.clear();
        }

        sliderController.setValue(currentModel, slider, newValue);
    }
}
