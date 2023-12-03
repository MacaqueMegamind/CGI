package com.cgvsu.transformation;

import com.cgvsu.math.Constants;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.render_engine.transformation.AffineTransformation;
import com.cgvsu.render_engine.transformation.DefaultRotate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RotationTest {
    @Test
    public void testRotate01(){
        AffineTransformation af = new AffineTransformation();
        af.setRotate(new DefaultRotate(0, 0, 90));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(1, 0, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(0, 0, 0));
        expected.add(new Vector3f(0, 1, 0));

        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 01 Failed");
            }
        }
    }

    @Test
    public void testRotate02(){
        AffineTransformation af = new AffineTransformation();
        af.setRotate(new DefaultRotate(0, 0, 0));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(1, 0, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(0, 0, 0));
        expected.add(new Vector3f(-1, 0, 0));

        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 02 Failed");
            }
        }
    }

    @Test
    public void testRotate03(){
        AffineTransformation af = new AffineTransformation();
        af.setRotate(new DefaultRotate(90, 0, 0));

        List<Vector3f> l = new ArrayList<>();
        l.add(new Vector3f(0, 0, 0));
        l.add(new Vector3f(0, 1, 0));

        af.calculate(l);

        List<Vector3f> expected = new ArrayList<>();
        expected.add(new Vector3f(0, 0, 0));
        expected.add(new Vector3f(0, 0, 1));



        for (int i = 0; i < l.size(); i++) {
            if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
                Vector3f e = l.get(1);
                System.err.println(e.x + " " + e.y + " " + e.z);
                throw new RuntimeException("Test 03 Failed");
            }
        }
    }



//    @Test
//    public void testRotateFull(){
//        AffineTransformation af = new AffineTransformation();
//
//
//        List<Vector3f> l = new ArrayList<>();
//        l.add(new Vector3f(0, 0, 0));
//        l.add(new Vector3f(1, 0, 0));
//
//
//        for(int angle = 1; angle < 360 ; angle++) {
//            af.setRotate(new DefaultRotate(0, 0, 1));
//            af.calculate(l);
//
//            List<Vector3f> expected = new ArrayList<>();
//            expected.add(new Vector3f(0, 0, 0));
//            expected.add(new Vector3f((float) Math.cos(Math.toRadians(angle)), (float) Math.sin(Math.toRadians(angle)), 0));
//
//
//            for (int i = 0; i < l.size(); i++) {
//                if(!l.get(i).epsEquals(expected.get(i), Constants.EPS)){
//                    Vector3f e = l.get(1);
//                    System.out.println(e.x + " " + e.y + " " + e.z);
//                    System.out.println(angle);
//                    throw new RuntimeException("Test Full Failed");
//                }
//            }
//        }
//    }

}
