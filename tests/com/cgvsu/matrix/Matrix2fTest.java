package com.cgvsu.matrix;

import com.cgvsu.math.MathUtils;
import com.cgvsu.math.matrix.Matrix2f;
import com.cgvsu.math.vector.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2fTest {
    private static final float[] VALUES_1 = {
            3.0f, 5.0f,
            -2.0f, 1.0f
    };

    private static final float[] VALUES_2 = {
            2.0f, 4.0f,
            -3.0f, 5.0f
    };

    private static final float[] SUM_VALUES = {
            5.0f, 9.0f,
            -5.0f, 6.0f
    };

    private static final float[] SUB_VALUES = {
            1.0f, 1.0f,
            1.0f, -4.0f
    };

    private static final float[] MUL_VALUES = {
            -2.0f, 14.0f,
            -19.0f, -10.0f
    };

    private static final float DET_1 = 13.0f;
    private static final float DET_2 = 22.0f;

    private Matrix2f m1 = new Matrix2f();
    private Matrix2f m2 = new Matrix2f();

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

        Matrix2f r1 = m1.cpy().mul(m2).tra();
        Matrix2f r2 = m2.cpy().tra().mul(m1.cpy().tra());

        assertEquals(r1, r2);
    }

    @Test
    void inverseMatrixProperties() {
        m1.set(VALUES_1);
        m2 = m1.cpy().inv();

        Matrix2f r1 = m1.cpy().mul(m2);
        Matrix2f r2 = m2.cpy().mul(m1);

        assertTrue(r1.epsEquals(r2, MathUtils.EPSILON));
        assertTrue(r1.epsEquals(Matrix2f.identity(), MathUtils.EPSILON));
        assertTrue(r2.epsEquals(Matrix2f.identity(), MathUtils.EPSILON));
    }

    @Test
    void sumHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.add(m2);

        assertEquals(new Matrix2f(SUM_VALUES), m1);
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

        assertEquals(new Matrix2f(SUB_VALUES), m1);
    }

    @Test
    void mulHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.mul(m2);

        assertEquals(new Matrix2f(MUL_VALUES), m1);
    }

    @Test
    void compMulAndDivGivesOriginalMatrix() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.comMul(m2).comDiv(m2);

        assertEquals(new Matrix2f(VALUES_1), m1);
    }

    @Test
    void identityDoesNotChangeVector() {
        m1.setIdentity();
        Vector2f v = new Vector2f(-21.256f, 18.444f);
        var r = m1.mul(v);

        assertEquals(v, r);
    }
}
