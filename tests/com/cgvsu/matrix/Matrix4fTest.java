package com.cgvsu.matrix;


import com.cgvsu.math.MathUtils;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector4f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix4fTest {
    private static final float[] VALUES_1 = {
            1.0f, 2.0f, 3.0f, 4.0f,
            5.0f, 3.0f, 4.0f, 3.0f,
            -2.0f, 4.0f, 6.0f, -4.0f,
            -3.0f, 2.0f, -1.0f, 3.0f
    };

    private static final float DET_1 = 476.0f;

    private static final float[] VALUES_2 = {
            3.0f, 5.0f, -1.0f, 2.0f,
            4.0f, 7.0f, -4.0f, 3.0f,
            -1.0f, 2.0f, 8.0f, 6.0f,
            -3.0f, 5.0f, 4.0f, 9.0f
    };

    private static final float[] MULTIPLY_VALUES = {
            24.0f, 21.0f, 21.0f, 37.0f,
            38.0f, 19.0f, 13.0f, 62.0f,
            -25.0f, 48.0f, 47.0f, -12.0f,
            -13.0f, 43.0f, 26.0f, 14.0f
    };

    private static final float[] SUM_VALUES = {
            4.0f, 7.0f, 2.0f, 6.0f,
            9.0f, 10.0f, 0.0f, 6.0f,
            -3.0f, 6.0f, 14.0f, 2.0f,
            -6.0f, 7.0f, 3.0f, 12.0f
    };

    private static final float[] SUBTRACT_VALUES = {
            -2.0f, -3.0f, 4.0f, 2.0f,
            1.0f, -4.0f, 8.0f, 0.0f,
            -1.0f, 2.0f, -2.0f, -10.0f,
            0.0f, -3.0f, -5.0f, -6.0f
    };

    private static final float DET_2 = -212.0f;

    private static final float[] LINEAR_DEPENDENT_VALUES = {
            1.0f, 2.0f, 3.0f, 4.0f,
            -2.0f, 4.0f, 6.0f, -4.0f,
            2.0f, 4.0f, 6.0f, 8.0f,
            -3.0f, 2.0f, -1.0f, 3.0f
    };

    private static final float[] ZERO_VECTOR_VALUES = {
            1.0f, 0.0f, 3.0f, 4.0f,
            -2.0f, 0.0f, 6.0f, -4.0f,
            2.0f, 0.0f, 6.0f, 8.0f,
            -3.0f, 0.0f, -1.0f, 3.0f
    };

    private Matrix4f m1 = new Matrix4f();
    private Matrix4f m2 = new Matrix4f();

    @Test
    void zeroMatrixHasZeroDeterminant() {
        m1.setZero();

        assertEquals(0.0f, m1.det());
    }

    @Test
    void identityMatrixHasUnitDeterminant() {
        m1.setIdentity();

        assertEquals(1.0f, m1.det());
    }

    @Test
    void transposeDoesNotChangeDeterminant() {
        m1.set(VALUES_1);
        m2.set(m1.cpy().tra());

        assertEquals(m1.det(), m2.det());
    }

    @Test
    void linearDependentVectorsGiveZeroDeterminant() {
        m1.set(LINEAR_DEPENDENT_VALUES);

        assertEquals(0.0f, m1.det());
    }

    @Test
    void zeroVectorGivesZeroDeterminant() {
        m1.set(ZERO_VECTOR_VALUES);

        assertEquals(0.0f, m1.det());
    }

    @Test
    void equalMatricesAreEqual() {
        m1 = new Matrix4f(VALUES_1);
        m2 = new Matrix4f(VALUES_1);

        assertEquals(m1, m2);
        assertEquals(m2, m1);
    }

    @Test
    void determinantHasExpectedValue() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        assertEquals(DET_1, m1.det());
        assertEquals(DET_2, m2.det());
    }

    @Test
    void doublyTransposedMatrixIsEqualToTheOriginal() {
        m1.set(VALUES_1);
        m2.set(m1.cpy().tra().tra());

        assertEquals(m1, m2);
    }

    @Test
    void mulHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.mul(m2);

        assertEquals(m1, new Matrix4f(MULTIPLY_VALUES));
    }

    @Test
    void multiplicationIsAssociative() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        Matrix4f m3 = new Matrix4f(SUM_VALUES);

        Matrix4f r1 = m1.cpy().mul(m2).mul(m3);
        Matrix4f r2 = m1.cpy().mul(m2.cpy().mul(m3));

        assertEquals(r1, r2);
    }

    @Test
    void multiplicationTransposed() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        Matrix4f r1 = m1.cpy().mul(m2).tra();
        Matrix4f r2 = m2.cpy().tra().mul(m1.cpy().tra());

        assertEquals(r1, r2);
    }

    @Test
    void sumHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.add(m2);

        assertEquals(m1, new Matrix4f(SUM_VALUES));
    }

    @Test
    void subHasExpectedResults() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        m1.sub(m2);

        assertEquals(m1, new Matrix4f(SUBTRACT_VALUES));
    }

    @Test
    void componentMultiplicationIsCommutative() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        Matrix4f r1 = m1.cpy().comMul(m2);
        Matrix4f r2 = m2.cpy().comMul(m1);

        assertEquals(r1, r2);
    }

    @Test
    void componentMultiplicationIsAssociative() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        Matrix4f m3 = new Matrix4f(SUM_VALUES);

        Matrix4f r1 = m1.cpy().comMul(m2).comMul(m3);
        Matrix4f r2 = m1.cpy().comMul(m2.comMul(m3));

        assertEquals(r1, r2);
    }

    @Test
    void componentMultiplicationIdentity() {
        m1.set(VALUES_1);
        m2.fill(1.0f);
        Matrix4f r = m1.cpy().comMul(m2);

        assertEquals(r, m1);
    }

    @Test
    void componentMultiplicationIsDistributive() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        Matrix4f m3 = new Matrix4f(SUM_VALUES);

        Matrix4f r1 = m1.cpy().comMul(m2.cpy().add(m3));
        Matrix4f r2 = m1.cpy().comMul(m2).add(m1.cpy().comMul(m3));

        assertEquals(r1, r2);
    }

    @Test
    void componentMulAndDivAreInverse() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);
        Matrix4f r = m1.cpy().comDiv(m2).comMul(m2);

        assertEquals(r, m1);
    }

    @Test
    void mulAndDivByScalarDoesNotChangeTheMatrix() {
        m1.set(VALUES_1);
        m2.set(VALUES_1);
        m1.mul(22.435f).div(22.435f);

        assertEquals(m1, m2);
    }

    @Test
    void identityReturnsIdentity() {
        m1 = Matrix4f.identity();
        m2 = new Matrix4f();

        assertEquals(m1, m2);
    }

    @Test
    void zeroReturnsZero() {
        m1 = Matrix4f.zero();
        m2 = new Matrix4f().setZero();

        assertEquals(m1, m2);
    }

    @Test
    void identityMatrixProperties() {
        m1.set(VALUES_1);
        m2.set(m1.cpy()).inv();

        Matrix4f r1 = m1.cpy().mul(m2);
        Matrix4f r2 = m2.cpy().mul(m1);

        assertTrue(r1.epsEquals(r2, MathUtils.EPSILON));
        assertTrue(r1.epsEquals(Matrix4f.identity(), MathUtils.EPSILON));
        assertTrue(r2.epsEquals(Matrix4f.identity(), MathUtils.EPSILON));
    }

    @Test
    void identityFromUnitVectors() {
        m1 = new Matrix4f(Vector4f.unitX(), Vector4f.unitY(), Vector4f.unitZ(), Vector4f.unitW());

        assertEquals(m1, Matrix4f.identity());
    }

    @Test
    void identityDoesNotChangeVector() {
        m1.setIdentity();
        Vector4f v1 = new Vector4f(55.0f, -29.43f, 17.21f, -192.526f);
        Vector4f v2 = m1.mul(v1.cpy());

        assertEquals(v1, v2);
    }

    @Test
    void additionIsCommutative() {
        m1.set(VALUES_1);
        m2.set(VALUES_2);

        var r1 = m2.cpy().add(m1);
        var r2 = m1.cpy().add(m2);

        assertEquals(r1, r2);
    }
}
