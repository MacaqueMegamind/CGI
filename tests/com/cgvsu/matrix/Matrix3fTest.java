package com.cgvsu.matrix;


import com.cgvsu.math.MathUtils;
import com.cgvsu.math.matrix.Matrix3f;
import com.cgvsu.math.vector.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix3fTest {
    private static final float[] VALUES_1 = {
            3.0f, 5.0f, 7.0f,
            -2.0f, 1.0f, 4.0f,
            -4.0f, -2.0f, 5.0f
    };

    private static final float[] VALUES_2 = {
            2.0f, 4.0f, 1.0f,
            -3.0f, 5.0f, 2.0f,
            -1.0f, -2.0f, -3.0f
    };

    private static final float[] SUM_VALUES = {
            5.0f, 9.0f, 8.0f,
            -5.0f, 6.0f, 6.0f,
            -5.0f, -4.0f, 2.0f
    };

    private static final float[] SUB_VALUES = {
            1.0f, 1.0f, 6.0f,
            1.0f, -4.0f, 2.0f,
            -3.0f, 0.0f, 8.0f
    };

    private static final float[] MUL_VALUES = {
            -6.0f, 12.0f, 35.0f,
            -27.0f, -14.0f, 9.0f,
            13.0f, -1.0f, -30.0f
    };

    private static final float DET_1 = 65.0f;
    private static final float DET_2 = -55.0f;

    private Matrix3f m1 = new Matrix3f();
    private Matrix3f m2 = new Matrix3f();

    @Test
    void unitMatrixDeterminantIsOne() {
        m1.setIdentity();

        assertEquals(1.0f, m1.det());
    }

    @Test
    void detReturnsExpectedValue() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        assertEquals(DET_1, m1.det());
        assertEquals(DET_2, m2.det());
    }

    @Test
    void transposeDoesNotAffectDeterminant() {
        m1.set(VALUES_1);
        m2.set(m1.cpy().tra());

        assertEquals(m1.det(), m2.det());
    }

    @Test
    void transposedMultiplication() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        Matrix3f r1 = m1.cpy().mul(m2).tra();
        Matrix3f r2 = m2.cpy().tra().mul(m1.cpy().tra());

        assertEquals(r1, r2);
    }

    @Test
    void inverseMatrixProperties() {
        m1.set(VALUES_1);
        m2 = m1.cpy().inv();

        Matrix3f r1 = m1.cpy().mul(m2);
        Matrix3f r2 = m2.cpy().mul(m1);

        assertTrue(r1.epsEquals(r2, MathUtils.EPSILON));
        assertTrue(r1.epsEquals(Matrix3f.identity(), MathUtils.EPSILON));
        assertTrue(r2.epsEquals(Matrix3f.identity(), MathUtils.EPSILON));
    }

    @Test
    void sumHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.add(m2);

        assertEquals(new Matrix3f(SUM_VALUES), m1);
    }

    @Test
    void additionIsCommutative() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        var r1 = m2.cpy().add(m1);
        var r2 = m1.cpy().add(m2);

        assertEquals(r1, r2);
    }

    @Test
    void subHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.sub(m2);

        assertEquals(new Matrix3f(SUB_VALUES), m1);
    }

    @Test
    void mulHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.mul(m2);

        assertEquals(new Matrix3f(MUL_VALUES), m1);
    }

    @Test
    void compMulAndDivGivesOriginalMatrix() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.comMul(m2).comDiv(m2);

        assertEquals(new Matrix3f(VALUES_1), m1);
    }

    @Test
    void identityDoesNotChangeVector() {
        m1.setIdentity();
        Vector3f v = new Vector3f(-21.256f, 18.444f, -134.523f);
        var r = m1.mul(v);

        assertEquals(v, r);
    }
}
