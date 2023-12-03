package com.cgvsu.vector;

import com.cgvsu.math.MathUtils;
import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.math.vector.Vector4f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector4fTest {
    private Vector4f v = new Vector4f();

    @Test
    void zeroVectorHasZeroLength() {
        v = Vector4f.zero();

        assertEquals(0.0f, v.len());
        assertEquals(0.0f, v.len2());
    }

    @Test
    void zeroVectorIsZero() {
        v = Vector4f.zero();

        assertTrue(v.isZero());
    }

    @Test
    void zeroConstructorConstructsZeroVector() {
        v = new Vector4f();

        assertTrue(v.isZero());
    }

    @Test
    void unitVectorsHaveUnitLength() {
        Vector4f i = Vector4f.unitX();
        Vector4f j = Vector4f.unitY();
        Vector4f k = Vector4f.unitZ();
        Vector4f l = Vector4f.unitW();

        assertEquals(1.0f, i.len());
        assertEquals(1.0f, i.len2());
        assertEquals(1.0f, j.len());
        assertEquals(1.0f, j.len2());
        assertEquals(1.0f, k.len());
        assertEquals(1.0f, k.len2());
        assertEquals(1.0f, l.len());
        assertEquals(1.0f, l.len2());
    }

    @Test
    void unitVectorsAreUnit() {
        Vector4f i = Vector4f.unitX();
        Vector4f j = Vector4f.unitY();
        Vector4f k = Vector4f.unitZ();
        Vector4f l = Vector4f.unitW();

        assertTrue(i.isUnit());
        assertTrue(j.isUnit());
        assertTrue(k.isUnit());
        assertTrue(l.isUnit());
    }

    @Test
    void testComponentConstructor() {
        v = new Vector4f(5.0f, -2.0f, 1.0f, 9.0f);

        assertEquals(5.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(1.0f, v.z);
        assertEquals(9.0f, v.w);
    }

    @Test
    void testArrayConstructor() {
        float[] vector = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        v = new Vector4f(vector);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
        assertEquals(4.0f, v.w);
    }

    @Test
    void testCopyConstructor() {
        Vector4f other = new Vector4f(5.0f, -2.0f, 1.0f, 9.0f);
        v = new Vector4f(other);

        assertEquals(5.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(1.0f, v.z);
        assertEquals(9.0f, v.w);
    }

    @Test
    void testConstructorFromV2f() {
        Vector2f v2f = new Vector2f(2.0f, 3.0f);
        v = new Vector4f(v2f);
        Vector4f v1 = new Vector4f(v2f, 5.0f, 6.0f);

        assertEquals(2.0f, v.x);
        assertEquals(2.0f, v1.x);
        assertEquals(3.0f, v.y);
        assertEquals(3.0f, v1.y);

        assertEquals(0.0f, v.z);
        assertEquals(0.0f, v.w);

        assertEquals(5.0f, v1.z);
        assertEquals(6.0f, v1.w);
    }

    @Test
    void testConstructorFromV3f() {
        Vector3f v3f = new Vector3f(2.0f, 3.0f, 4.0f);
        v = new Vector4f(v3f);
        Vector4f v1 = new Vector4f(v3f, 5.0f);

        assertEquals(2.0f, v.x);
        assertEquals(2.0f, v1.x);
        assertEquals(3.0f, v.y);
        assertEquals(3.0f, v1.y);
        assertEquals(4.0f, v1.z);
        assertEquals(4.0f, v1.z);

        assertEquals(1.0f, v.w);
        assertEquals(5.0f, v1.w);
    }

    @Test
    void equalVectorsAreEqual() {
        Vector4f v1 = new Vector4f(2.0f, 1.0f, 3.0f, 10.0f);
        v.set(2.0f, 1.0f, 3.0f, 10.f);

        assertEquals(v1, v);
        assertEquals(v, v1);
    }

    @Test
    void equalVectorsAreIdentical() {
        Vector4f v1 = new Vector4f(2.0f, 1.0f, 3.0f, 10.0f);
        v.set(2.0f, 1.0f, 3.0f, 10.f);

        assertTrue(v1.idt(v));
        assertTrue(v.idt(v1));
    }

    @Test
    void zeroVectorIsEqualToZeroVector() {
        v = Vector4f.zero();
        Vector4f z = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);

        assertEquals(v, z);
    }

    @Test
    void nearlyEqualVectorsAreEpsEqual() {
        v = new Vector4f();
        v.x = 200.0f;
        Vector4f v1 = Vector4f.unitX();
        Vector4f a = Vector4f.zero();
        for (int i = 0; i < 200; i++) {
            a.add(v1);
        }

        assertTrue(v.epsEquals(a, MathUtils.EPSILON));
        assertTrue(a.epsEquals(v, MathUtils.EPSILON));
    }

    @Test
    void nearlyEqualVectorsAreEpsIdt() {
        v = new Vector4f();
        v.x = 200.0f;
        Vector4f v1 = Vector4f.unitX();
        Vector4f a = Vector4f.zero();
        for (int i = 0; i < 200; i++) {
            a.add(v1);
        }

        assertTrue(v.epsIdt(a, MathUtils.EPSILON));
        assertTrue(a.epsIdt(v, MathUtils.EPSILON));
    }

    @Test
    void testToString() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);

        assertEquals("v4f(x=1.0, y=2.0, z=3.0, w=4.0)", v.toString());
    }

    @Test
    void testCopy() {
        Vector4f v1 = new Vector4f(1.0f, 2.0f, 3.0f, 4.0f);
        v = v1.cpy();

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
        assertEquals(4.0f, v.w);
    }

    @Test
    void testSetZero() {
        v = new Vector4f(33.0f, 12.0f, 42.0f, 14.0f);
        v.setZero();

        assertTrue(v.isZero());
    }

    @Test
    void testSetValue() {
        v.set(10.0f);

        assertEquals(10.0f, v.x);
        assertEquals(10.0f, v.y);
        assertEquals(10.0f, v.z);
        assertEquals(10.0f, v.w);
    }

    @Test
    void testSetValues() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
        assertEquals(4.0f, v.w);
    }

    @Test
    void testSetVector() {
        Vector4f v1 = new Vector4f(11.0f, 20.0f, 35.0f, 82.0f);
        v.set(v1);

        assertEquals(11.0f, v.x);
        assertEquals(20.0f, v.y);
        assertEquals(35.0f, v.z);
        assertEquals(82.0f, v.w);
    }

    @Test
    void testSetFromArray() {
        float[] vector = {1.0f, 2.0f, 3.0f, 4.0f};
        v.set(vector);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
        assertEquals(4.0f, v.w);
    }

    @Test
    void testSetFromV2f() {
        Vector2f v2f = new Vector2f(3.0f, -5.0f);

        v.set(v2f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(0.0f, v.z);
        assertEquals(0.0f, v.w);

        v.set(v2f, 2.0f, 9.0f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(2.0f, v.z);
        assertEquals(9.0f, v.w);
    }

    @Test
    void testSetFromV3f() {
        Vector3f v3f = new Vector3f(3.0f, -5.0f, 4.0f);

        v.set(v3f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(4.0f, v.z);
        assertEquals(1.0f, v.w);

        v.set(v3f, -11.0f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(4.0f, v.z);
        assertEquals(-11.0f, v.w);
    }

    @Test
    void testLength() {
        v.set(1.0f, 1.0f, 3.0f, 5.0f);

        assertEquals(6.0f, v.len());
        assertEquals(6.0f, Vector4f.len(1.0f, 1.0f, 3.0f, 5.0f));
    }

    @Test
    void testLength2() {
        v.set(1.0f, 1.0f, 3.0f, 5.0f);

        assertEquals(36.0f, v.len2());
        assertEquals(36.0f, Vector4f.len2(1.0f, 1.0f, 3.0f, 5.0f));
    }

    @Test
    void length2IsSquareOfLength() {
        v.set(1.0f, 1.0f, 3.0f, 5.0f);
        float len = v.len();

        assertEquals(len * len, v.len2());
    }

    @Test
    void normalizeMakesVectorWithUnitLength() {
        v.set(1.0f, 1.0f, 7.0f, 0.0f);
        v.nor();

        assertEquals(1.0f, v.len());
        assertEquals(1.0f, v.len2());
        assertTrue(v.isUnit());
    }

    @Test
    void testLimitEffective() {
        v.set(420.0f, -421.0f, 1006.0f, 96.0f);
        v.limit(25.0f);
        assertTrue(MathUtils.epsEquals(25.0f, v.len()));
    }

    @Test
    void testLimitIneffective() {
        v = new Vector4f(1.0f, 3.0f, 5.0f, 1.0f);
        v.limit(10.0f);
        assertEquals(6.0f, v.len());
    }

    @Test
    void testLimit2Effective() {
        v.set(420.0f, -421.0f, 1006.0f, 96.0f);
        v.limit2(25.0f);
        assertTrue(MathUtils.epsEquals(25.0f, v.len2()));
    }

    @Test
    void testLimit2Ineffective() {
        v = new Vector4f(3.0f, 2.0f, 0.0f, 0.0f);
        v.limit2(200.0f);
        assertEquals(13.0f, v.len2());
    }

    @Test
    void testSetLength() {
        v.set(15.0f, 39.0f, -42.0f, 13.0f);
        v.setLength(14.0f);

        assertEquals(14.0f, v.len());
    }

    @Test
    void testSetLength2() {
        v.set(15.0f, 39.0f, -42.0f, 13.0f);
        v.setLength2(21.0f);

        assertEquals(21.0f, v.len2());
    }

    @Test
    void testClampGreater() {
        v.set(109.0f, 4.0f, 55.0f, -31.0f);
        v.clamp(20.f, 30.f);

        assertTrue(MathUtils.epsEquals(30.0f, v.len()));
    }

    @Test
    void testClampLess() {
        v.set(-2.0f, 3.0f, 4.0f, -3.0f);
        v.clamp(20.f, 30.f);

        assertEquals(20.0f, v.len());
    }

    @Test
    void testClampInBounds() {
        v.set(25.0f, 0.0f, 0.0f, 0.0f);
        v.clamp(20.f, 30.f);

        assertEquals(25.0f, v.len());
    }

    @Test
    void testAddVector() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v1 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        v.add(v1);

        assertEquals(6.0f, v.x);
        assertEquals(8.0f, v.y);
        assertEquals(10.0f, v.z);
        assertEquals(12.0f, v.w);
    }

    @Test
    void testAddValue() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.add(10.0f);

        assertEquals(11.0f, v.x);
        assertEquals(12.0f, v.y);
        assertEquals(13.0f, v.z);
        assertEquals(14.0f, v.w);
    }

    @Test
    void testAddComponents() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.add(3.0f, 4.0f, 5.0f, 6.0f);

        assertEquals(4.0f, v.x);
        assertEquals(6.0f, v.y);
        assertEquals(8.0f, v.z);
        assertEquals(10.0f, v.w);
    }

    @Test
    void testSubVector() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v1 = new Vector4f(5.0f, 6.0f, 7.0f, 8.0f);
        v.sub(v1);

        assertEquals(-4.0f, v.x);
        assertEquals(-4.0f, v.y);
        assertEquals(-4.0f, v.z);
        assertEquals(-4.0f, v.w);
    }

    @Test
    void testSubValue() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.sub(10.0f);

        assertEquals(-9.0f, v.x);
        assertEquals(-8.0f, v.y);
        assertEquals(-7.0f, v.z);
        assertEquals(-6.0f, v.w);
    }

    @Test
    void testSubComponents() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.sub(3.0f, 4.0f, 5.0f, 6.0f);

        assertEquals(-2.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(-2.0f, v.z);
        assertEquals(-2.0f, v.w);
    }

    @Test
    void testSclVector() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.scl(new Vector4f(2.0f, 5.0f, 10.0f, 100.0f));

        assertEquals(2.0f, v.x);
        assertEquals(10.0f, v.y);
        assertEquals(30.0f, v.z);
        assertEquals(400.0f, v.w);
    }

    @Test
    void testSclValue() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.scl(10.0f);

        assertEquals(10.0f, v.x);
        assertEquals(20.0f, v.y);
        assertEquals(30.0f, v.z);
        assertEquals(40.0f, v.w);
    }

    @Test
    void testSclComponents() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.scl(3.0f, 2.0f, 9.0f, 4.0f);

        assertEquals(3.0f, v.x);
        assertEquals(4.0f, v.y);
        assertEquals(27.0f, v.z);
        assertEquals(16.0f, v.w);
    }

    @Test
    void testDivVector() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.div(new Vector4f(4.0f, 8.0f, 12.0f, 16.0f));

        assertEquals(0.25f, v.x);
        assertEquals(0.25f, v.y);
        assertEquals(0.25f, v.z);
        assertEquals(0.25f, v.w);
    }

    @Test
    void testDivValue() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.div(2.0f);

        assertEquals(0.5f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(1.5f, v.z);
        assertEquals(2.0f, v.w);
    }

    @Test
    void testDivComponents() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        v.div(4.0f, 8.0f, 12.0f, 16.0f);

        assertEquals(0.25f, v.x);
        assertEquals(0.25f, v.y);
        assertEquals(0.25f, v.z);
        assertEquals(0.25f, v.w);
    }

    @Test
    void dstReturnsExpectedValue() {
        v.set(3.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v1 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);

        assertEquals(2.0f, v.dst(v1));
        assertEquals(2.0f, v.dst(1.0f, 0.0f, 0.0f, 0.0f));
        assertEquals(2.0f, Vector4f.dst(v, v1));
        assertEquals(2.0f, Vector4f.dst(3.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
    }

    @Test
    void dst2ReturnsExpectedValue() {
        v.set(3.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v1 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);

        assertEquals(4.0f, v.dst2(v1));
        assertEquals(4.0f, v.dst2(1.0f, 0.0f, 0.0f, 0.0f));
        assertEquals(4.0f, Vector4f.dst2(v, v1));
        assertEquals(4.0f, Vector4f.dst2(3.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
    }

    @Test
    void dstDoesNotDependOnOrder() {
        v.set(3.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v1 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);

        assertEquals(2.0f, v.dst(v1));
        assertEquals(2.0f, v1.dst(v));
    }

    @Test
    void dst2DoesNotDependOnOrder() {
        v.set(3.0f, 0.0f, 0.0f, 0.0f);
        Vector4f v1 = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);

        assertEquals(4.0f, v.dst2(v1));
        assertEquals(4.0f, v1.dst2(v));
    }

    @Test
    void dstToSelfIsZero() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);

        assertEquals(0.0f, v.dst(v));
        assertEquals(0.0f, v.dst2(v));
    }

    @Test
    void testLerp() {
        v.setZero();
        Vector4f v1 = new Vector4f(1.0f, 2.0f, 4.0f, 8.0f);

        v.lerp(v1, 0.0f);
        assertEquals(0.0f, v.x);
        assertEquals(0.0f, v.y);
        assertEquals(0.0f, v.z);
        assertEquals(0.0f, v.w);

        v.setZero();
        v.lerp(v1, 0.5f);
        assertEquals(0.5f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(2.0f, v.z);
        assertEquals(4.0f, v.w);

        v.setZero();
        v.lerp(v1, 1.0f);
        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(4.0f, v.z);
        assertEquals(8.0f, v.w);
    }

    @Test
    void testStaticLerp() {
        v.setZero();
        Vector4f v1 = new Vector4f(1.0f, 2.0f, 4.0f, 8.0f);

        Vector4f r = Vector4f.lerp(v, v1, 0.0f);
        assertEquals(0.0f, r.x);
        assertEquals(0.0f, r.y);
        assertEquals(0.0f, r.z);
        assertEquals(0.0f, r.w);

        r = Vector4f.lerp(v, v1, 0.5f);
        assertEquals(0.5f, r.x);
        assertEquals(1.0f, r.y);
        assertEquals(2.0f, r.z);
        assertEquals(4.0f, r.w);

        r = Vector4f.lerp(v, v1, 1.0f);
        assertEquals(1.0f, r.x);
        assertEquals(2.0f, r.y);
        assertEquals(4.0f, r.z);
        assertEquals(8.0f, r.w);
    }

    @Test
    void testAddMulScalar() {
        v.set(2.0f, -3.0f, 5.0f, 7.0f);
        Vector4f v1 = new Vector4f(4.0f, 2.0f, 1.0f, -2.0f);
        v.addMul(v1, -2.0f);

        assertEquals(-6.0f, v.x);
        assertEquals(-7.0f, v.y);
        assertEquals(3.0f, v.z);
        assertEquals(11.0f, v.w);
    }

    @Test
    void testAddMulVector() {
        v.set(2.0f, -3.0f, 5.0f, 7.0f);
        Vector4f v1 = new Vector4f(4.0f, 2.0f, 1.0f, -2.0f);
        Vector4f v2 = new Vector4f(0.0f, 2.0f, -1.0f, 3.0f);
        v.addMul(v1, v2);

        assertEquals(2.0f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(4.0f, v.z);
        assertEquals(1.0f, v.w);
    }

    @Test
    void testDotProduct() {
        v.set(2.0f, -3.0f, 5.0f, 7.0f);
        Vector4f v1 = new Vector4f(4.0f, 2.0f, 1.0f, -2.0f);

        assertEquals(-7.0f, v.dot(v1));
        assertEquals(-7.0f, v1.dot(v));
    }

    @Test
    void testTo() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v1 = new Vector4f(2.0f, -1.0f, 2.0f, 6.0f);

        Vector4f v2 = v.cpy().to(v1);
        assertEquals(1.0f, v2.x);
        assertEquals(-3.0f, v2.y);
        assertEquals(-1.0f, v2.z);
        assertEquals(2.0f, v2.w);

        v2 = v.to(2.0f, -1.0f, 2.0f, 6.0f);
        assertEquals(1.0f, v2.x);
        assertEquals(-3.0f, v2.y);
        assertEquals(-1.0f, v2.z);
        assertEquals(2.0f, v2.w);
    }

    @Test
    void vectorIsOnLineWithSelf() {
        v.set(255.0f, -2410.3214f, 542.4664f, 1546.0f);

        assertTrue(v.isOnLine(v));
        assertTrue(v.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void parallelVectorsAreOnLinePrecise() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);
        Vector4f v1 = new Vector4f(2.0f, 4.0f, 6.0f, 8.0f);

        assertTrue(v.isOnLine(v1));
        assertTrue(v1.isOnLine(v));
    }

    @Test
    void parallelVectorsAreOnLineApprox() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(4.999996f, -2.000003f, 17.000004f, 29.00008f);

        assertTrue(v.isOnLine(v1, MathUtils.EPSILON));
        assertTrue(v1.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelVectorsAreNotOnLinePrecise() {
        v = Vector4f.unitX();

        assertFalse(v.isOnLine(Vector4f.unitY()));
        assertFalse(v.isOnLine(Vector4f.unitZ()));
        assertFalse(v.isOnLine(Vector4f.unitW()));
    }

    @Test
    void nonParallelVectorsAreNotOnLineApprox() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(5.0f, -2.0f, 17.0f, 29.5f);

        assertFalse(v.isOnLine(v1, MathUtils.EPSILON));
        assertFalse(v1.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void vectorIsCollinearWithSelf() {
        v.set(255.0f, -2410.3214f, 542.4664f, 1546.0f);

        assertTrue(v.isCollinear(v));
        assertTrue(v.isCollinear(v, MathUtils.EPSILON));
    }

    @Test
    void collVecsAreCollPrecise() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = v.cpy().scl(412.931f);

        assertTrue(v.isCollinear(v1));
        assertTrue(v1.isCollinear(v));
    }

    @Test
    void collVecsAreCollApprox() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(95.000004f, -38.0000007f, 322.999996f, 550.9999992f);

        assertTrue(v.isCollinear(v1, MathUtils.EPSILON));
        assertTrue(v1.isCollinear(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelSameDirVecsAreNonColl() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(5.5f, -2.0f, 17.0f, 29.5f);

        assertFalse(v.isCollinear(v1));
        assertFalse(v1.isCollinear(v));
    }

    @Test
    void parallelDiffDirVecsAreNonColl() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(-5.0f, 2.0f, -17.0f, -29.0f);

        assertFalse(v.isCollinear(v1));
        assertFalse(v1.isCollinear(v));
    }

    @Test
    void collOppositeVecsAreCollOppositePrecise() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = v.cpy().scl(-5);

        assertTrue(v.isCollinearOpposite(v1));
        assertTrue(v1.isCollinearOpposite(v));
    }

    @Test
    void collOppositeVecsAreCollOppositeApprox() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(-95.000004f, 38.0000007f, -322.999996f, -550.9999992f);

        assertTrue(v.isCollinearOpposite(v1, MathUtils.EPSILON));
        assertTrue(v1.isCollinearOpposite(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelDiffDirVecsAreNonCollOpposite() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = new Vector4f(-5.5f, 2.0f, -17.0f, -29.5f);

        assertFalse(v.isCollinearOpposite(v1));
        assertFalse(v1.isCollinearOpposite(v));
    }

    @Test
    void parallelSameDirVecsAreNonCollOpposite() {
        v.set(5.0f, -2.0f, 17.0f, 29.0f);
        Vector4f v1 = v.cpy().scl(22.421f);

        assertFalse(v.isCollinearOpposite(v1));
        assertFalse(v1.isCollinearOpposite(v));
    }

    @Test
    void vectorIsNotCollinearOppositeWithItself() {
        v.set(255.0f, -2410.3214f, 542.4664f, 1546.0f);

        assertFalse(v.isCollinearOpposite(v));
        assertFalse(v.isCollinearOpposite(v, MathUtils.EPSILON));
    }

    @Test
    void vectorIsNotOrthogonalToItself() {
        v = Vector4f.unitX().scl(5.0f);

        assertFalse(v.isOrthogonal(v));
    }

    @Test
    void zeroIsOrthogonalToItself() {
        v.setZero();

        assertTrue(v.isOrthogonal(v));
    }

    @Test
    void vectorIsOrthogonalToZero() {
        v.set(1.0f, 2.0f, 3.0f, 4.0f);

        assertTrue(v.isOrthogonal(Vector4f.zero()));
        assertTrue(Vector4f.zero().isOrthogonal(v));
    }

    @Test
    void orthogonalVecsAreOrthogonal() {
        assertTrue(Vector4f.unitX().isOrthogonal(Vector4f.unitY()));
        assertTrue(Vector4f.unitY().isOrthogonal(Vector4f.unitZ()));
        assertTrue(Vector4f.unitX().isOrthogonal(Vector4f.unitZ()));
        assertTrue(Vector4f.unitX().isOrthogonal(Vector4f.unitW()));
    }

    @Test
    void testOrthogonalApprox() {
        v.set(5.0f, -1.0f, 0.0f, 0.0f);
        Vector4f v1 = new Vector4f(1.0f, 4.999992f, 0.0f, 0.0f);

        assertTrue(v.isOrthogonal(v1, MathUtils.EPSILON));
        assertTrue(v1.isOrthogonal(v, MathUtils.EPSILON));
    }

    @Test
    void nonOrthogonalAreNonOrthogonal() {
        v.set(5.0f, 10.f, 15.0f, 20.f);
        Vector4f v1 = new Vector4f(6.0f, 11.0f, 10.0f, 24.0f);

        assertFalse(v.isOrthogonal(v1));
        assertFalse(v1.isOrthogonal(v));
    }

    @Test
    void vectorHasSameDirectionWithItself() {
        v.set(5.0f, 10.0f, -125.0f, 42.0f);

        assertTrue(v.hasSameDirection(v));
    }

    @Test
    void sameDirectionVecsHaveSameDirection() {
        v.set(1.0f, 2.0f, 3.0f, 10.0f);
        Vector4f v1 = new Vector4f(5.0f, 7.0f, 11.0f, 29.0f);

        assertTrue(v.hasSameDirection(v1));
        assertTrue(v1.hasSameDirection(v));
    }

    @Test
    void oppositeDirectionVecsHaveOppositeDirection() {
        v.set(1.0f, 2.0f, 3.0f, 10.0f);
        Vector4f v1 = v.cpy().scl(-3);

        assertTrue(v.hasOppositeDirection(v1));
        assertTrue(v1.hasOppositeDirection(v));
    }
}
