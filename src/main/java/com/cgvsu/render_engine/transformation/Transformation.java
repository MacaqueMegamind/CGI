package com.cgvsu.render_engine.transformation;

import com.cgvsu.math.vector.Vector3f;

import java.util.List;

public interface Transformation {
    void calculate(List<Vector3f> vector);
}
