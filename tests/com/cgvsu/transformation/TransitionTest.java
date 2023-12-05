package com.cgvsu.transformation;

import com.cgvsu.math.Constants;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.render_engine.transformation.AffineTransformation;
import com.cgvsu.render_engine.transformation.DefaultTransition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TransitionTest {
    @Test
    public void testTransition01(){
        AffineTransformation af = new AffineTransformation();
        af.setTransition(new DefaultTransition(90, 0, 0));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(1, 0, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(90, 0, 0));
        expected.add(new Vector3f(91, 0, 0));

        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 01 Failed");
            }
        }
    }
}
