package com.cgvsu.gui;

import javafx.scene.control.Slider;

import java.util.HashMap;

public class SliderController {
    private Slider[] sliders;

    private final HashMap<String, HashMap<Slider, Float>> hashMap = new HashMap();

    public SliderController(Slider ...sliders) {
        this.sliders = sliders;
    }

    public void addModelSlides(String modelName, Slider ...sliders) {
        this.sliders = sliders;
        HashMap<Slider, Float> slidersHashmap = new HashMap<>();
        for (Slider slider1: sliders) {
            slidersHashmap.put(slider1, slider1.valueProperty().floatValue());
        }
        this.hashMap.put(modelName, slidersHashmap);
    }

    public Float getValue(String modelName, Slider slider) {
        if (this.hashMap.get(modelName) != null)
            return this.hashMap.get(modelName).get(slider);
        return null;
    }

    public void setValue(String modelName, Slider slider, float value) {
        this.hashMap.get(modelName).put(slider, value);
    }

    public void updateSlider(String modelName, Slider slider) {
        slider.valueProperty().setValue(this.hashMap.get(modelName).get(slider));
    }

    public void updateSliders(String modelName) {
        if (hashMap.get(modelName) == null) {
            HashMap<Slider, Float> hashMap1 = new HashMap<>();
            for (Slider slider: sliders) {
                slider.valueProperty().setValue(1);
                hashMap1.put(slider, 1F);
            }
            hashMap.put(modelName, hashMap1);
        } else {
            for (Slider slider : hashMap.get(modelName).keySet()) {
                updateSlider(modelName, slider);
            }
        }
    }

    public void deleteModel(String modelName){
        hashMap.remove(modelName);
    }
}
