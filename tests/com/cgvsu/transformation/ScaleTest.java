package com.cgvsu.transformation;

import com.cgvsu.math.Constants;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.render_engine.transformation.AffineTransformation;
import com.cgvsu.render_engine.transformation.DefaultScale;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ScaleTest {
    @Test
    public void testScale01(){
        AffineTransformation af = new AffineTransformation();
        af.setScale(new DefaultScale(1, 2, 1));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(0, 1, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(0, 0, 0));
        expected.add(new Vector3f(0, 2, 0));

        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 01 Failed");
            }
        }
    }

    @Test
    public void testScale02(){
        AffineTransformation af = new AffineTransformation();
        af.setScale(new DefaultScale(2, 1, 1));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(1, 0, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(0, 0, 0));
        expected.add(new Vector3f(2, 0, 0));

        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 02 Failed");
            }
        }
    }
}
