package com.cgvsu.vector;

import com.cgvsu.math.MathUtils;
import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Vector3fTest {
    private Vector3f v = new Vector3f();

    @Test
    void zeroVectorHasZeroLength() {
        v = Vector3f.zero();

        assertEquals(0.0f, v.len());
        assertEquals(0.0f, v.len2());
    }

    @Test
    void zeroVectorIsZero() {
        v = Vector3f.zero();

        assertTrue(v.isZero());
    }

    @Test
    void zeroConstructorConstructsZeroVector() {
        v = new Vector3f();

        assertTrue(v.isZero());
    }

    @Test
    void unitVectorsHaveUnitLength() {
        Vector3f i = Vector3f.unitX();
        Vector3f j = Vector3f.unitY();
        Vector3f k = Vector3f.unitZ();

        assertEquals(1.0f, i.len());
        assertEquals(1.0f, i.len2());
        assertEquals(1.0f, j.len());
        assertEquals(1.0f, j.len2());
        assertEquals(1.0f, k.len());
        assertEquals(1.0f, k.len2());
    }

    @Test
    void unitVectorsAreUnit() {
        Vector3f i = Vector3f.unitX();
        Vector3f j = Vector3f.unitY();
        Vector3f k = Vector3f.unitZ();

        assertTrue(i.isUnit());
        assertTrue(j.isUnit());
        assertTrue(k.isUnit());
    }

    @Test
    void testComponentConstructor() {
        v = new Vector3f(5.0f, -2.0f, 1.0f);

        assertEquals(5.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(1.0f, v.z);
    }

    @Test
    void testArrayConstructor() {
        float[] vector = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
        v = new Vector3f(vector);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
    }

    @Test
    void testCopyConstructor() {
        Vector3f other = new Vector3f(5.0f, -2.0f, 1.0f);
        v = new Vector3f(other);

        assertEquals(5.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(1.0f, v.z);
    }

    @Test
    void testConstructorFromV2f() {
        Vector2f v2f = new Vector2f(2.0f, 3.0f);
        v = new Vector3f(v2f);
        Vector3f v1 = new Vector3f(v2f, 5.0f);

        assertEquals(2.0f, v.x);
        assertEquals(2.0f, v1.x);
        assertEquals(3.0f, v.y);
        assertEquals(3.0f, v1.y);

        assertEquals(0.0f, v.z);
        assertEquals(5.0f, v1.z);
    }

    @Test
    void equalVectorsAreEqual() {
        Vector3f v1 = new Vector3f(2.0f, 1.0f, 3.0f);
        v.set(2.0f, 1.0f, 3.0f);

        assertEquals(v1, v);
        assertEquals(v, v1);
    }

    @Test
    void equalVectorsAreIdentical() {
        Vector3f v1 = new Vector3f(2.0f, 1.0f, 3.0f);
        v.set(2.0f, 1.0f, 3.0f);

        assertTrue(v1.idt(v));
        assertTrue(v.idt(v1));
    }

    @Test
    void zeroVectorIsEqualToZeroVector() {
        v = Vector3f.zero();
        Vector3f z = new Vector3f(0.0f, 0.0f, 0.0f);

        assertEquals(v, z);
    }

    @Test
    void nearlyEqualVectorsAreEpsEqual() {
        v = new Vector3f();
        v.x = 200.0f;
        Vector3f v1 = Vector3f.unitX();
        Vector3f a = Vector3f.zero();
        for (int i = 0; i < 200; i++) {
            a.add(v1);
        }

        assertTrue(v.epsEquals(a, MathUtils.EPSILON));
        assertTrue(a.epsEquals(v, MathUtils.EPSILON));
    }

    @Test
    void nearlyEqualVectorsAreEpsIdt() {
        v = new Vector3f();
        v.x = 200.0f;
        Vector3f v1 = Vector3f.unitX();
        Vector3f a = Vector3f.zero();
        for (int i = 0; i < 200; i++) {
            a.add(v1);
        }

        assertTrue(v.epsIdt(a, MathUtils.EPSILON));
        assertTrue(a.epsIdt(v, MathUtils.EPSILON));
    }

    @Test
    void testToString() {
        v.set(1.0f, 2.0f, 3.0f);

        assertEquals("v3f(x=1.0, y=2.0, z=3.0)", v.toString());
    }

    @Test
    void testCopy() {
        Vector3f v1 = new Vector3f(1.0f, 2.0f, 3.0f);
        v = v1.cpy();

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
    }

    @Test
    void testSetZero() {
        v = new Vector3f(33.0f, 12.0f, 42.0f);
        v.setZero();

        assertTrue(v.isZero());
    }

    @Test
    void testSetValue() {
        v.set(10.0f);

        assertEquals(10.0f, v.x);
        assertEquals(10.0f, v.y);
        assertEquals(10.0f, v.z);
    }

    @Test
    void testSetValues() {
        v.set(1.0f, 2.0f, 3.0f);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
    }

    @Test
    void testSetVector() {
        Vector3f v1 = new Vector3f(11.0f, 20.0f, 35.0f);
        v.set(v1);

        assertEquals(11.0f, v.x);
        assertEquals(20.0f, v.y);
        assertEquals(35.0f, v.z);
    }

    @Test
    void testSetFromArray() {
        float[] vector = {1.0f, 2.0f, 3.0f};
        v.set(vector);

        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(3.0f, v.z);
    }

    @Test
    void testSetFromV2f() {
        Vector2f v2f = new Vector2f(3.0f, -5.0f);

        v.set(v2f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(0.0f, v.z);

        v.set(v2f, 2.0f);
        assertEquals(3.0f, v.x);
        assertEquals(-5.0f, v.y);
        assertEquals(2.0f, v.z);
    }

    @Test
    void testLength() {
        v.set(2.0f, 3.0f, 6.0f);

        assertEquals(7.0f, v.len());
        assertEquals(7.0f, Vector3f.len(2.0f, 3.0f, 6.0f));
    }

    @Test
    void testLength2() {
        v.set(2.0f, 3.0f, 6.0f);

        assertEquals(49.0f, v.len2());
        assertEquals(49.0f, Vector3f.len2(2.0f, 3.0f, 6.0f));
    }

    @Test
    void length2IsSquareOfLength() {
        v.set(2.0f, 3.0f, 6.0f);
        float len = v.len();

        assertEquals(len * len, v.len2());
    }

    @Test
    void normalizeMakesVectorWithUnitLength() {
        v.set(1.0f, 1.0f, 7.0f);
        v.nor();

        assertEquals(1.0f, v.len());
        assertEquals(1.0f, v.len2());
        assertTrue(v.isUnit());
    }

    @Test
    void testLimitEffective() {
        v.set(420.0f, -421.0f, 1006.0f);
        v.limit(25.0f);
        assertTrue(MathUtils.epsEquals(25.0f, v.len()));
    }

    @Test
    void testLimitIneffective() {
        v = new Vector3f(2.0f, 3.0f, 6.0f);
        v.limit(10.0f);
        assertEquals(7.0f, v.len());
    }

    @Test
    void testLimit2Effective() {
        v.set(420.0f, -421.0f, 1006.0f);
        v.limit2(25.0f);
        assertTrue(MathUtils.epsEquals(25.0f, v.len2()));
    }

    @Test
    void testLimit2Ineffective() {
        v = new Vector3f(2.0f, 3.0f, 6.0f);
        v.limit2(80.0f);
        assertEquals(49.0f, v.len2());
    }

    @Test
    void testSetLength() {
        v.set(15.0f, 39.0f, -42.0f);
        v.setLength(14.0f);

        assertEquals(14.0f, v.len());
    }

    @Test
    void testSetLength2() {
        v.set(15.0f, 39.0f, -42.0f);
        v.setLength2(21.0f);

        assertEquals(21.0f, v.len2());
    }

    @Test
    void testClampGreater() {
        v.set(109.0f, -31.0f, 55.0f);
        v.clamp(20.f, 30.f);

        assertTrue(MathUtils.epsEquals(30.0f, v.len()));
    }

    @Test
    void testClampLess() {
        v.set(-2.0f, 3.0f, 4.0f);
        v.clamp(20.f, 30.f);

        assertEquals(20.0f, v.len());
    }

    @Test
    void testClampInBounds() {
        v.set(25.0f, 0.0f, 0.0f);
        v.clamp(20.f, 30.f);

        assertEquals(25.0f, v.len());
    }

    @Test
    void testAddVector() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = new Vector3f(5.0f, 6.0f, 7.0f);
        v.add(v1);

        assertEquals(6.0f, v.x);
        assertEquals(8.0f, v.y);
        assertEquals(10.0f, v.z);
    }

    @Test
    void testAddValue() {
        v.set(1.0f, 2.0f, 3.0f);
        v.add(10.0f);

        assertEquals(11.0f, v.x);
        assertEquals(12.0f, v.y);
        assertEquals(13.0f, v.z);
    }

    @Test
    void testAddComponents() {
        v.set(1.0f, 2.0f, 3.0f);
        v.add(3.0f, 4.0f, 5.0f);

        assertEquals(4.0f, v.x);
        assertEquals(6.0f, v.y);
        assertEquals(8.0f, v.z);
    }

    @Test
    void testSubVector() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = new Vector3f(5.0f, 6.0f, 7.0f);
        v.sub(v1);

        assertEquals(-4.0f, v.x);
        assertEquals(-4.0f, v.y);
        assertEquals(-4.0f, v.z);
    }

    @Test
    void testSubValue() {
        v.set(1.0f, 2.0f, 3.0f);
        v.sub(10.0f);

        assertEquals(-9.0f, v.x);
        assertEquals(-8.0f, v.y);
        assertEquals(-7.0f, v.z);
    }

    @Test
    void testSubComponents() {
        v.set(1.0f, 2.0f, 3.0f);
        v.sub(3.0f, 4.0f, 5.0f);

        assertEquals(-2.0f, v.x);
        assertEquals(-2.0f, v.y);
        assertEquals(-2.0f, v.z);
    }

    @Test
    void testSclVector() {
        v.set(1.0f, 2.0f, 3.0f);
        v.scl(new Vector3f(2.0f, 5.0f, 10.0f));

        assertEquals(2.0f, v.x);
        assertEquals(10.0f, v.y);
        assertEquals(30.0f, v.z);
    }

    @Test
    void testSclValue() {
        v.set(1.0f, 2.0f, 3.0f);
        v.scl(10.0f);

        assertEquals(10.0f, v.x);
        assertEquals(20.0f, v.y);
        assertEquals(30.0f, v.z);
    }

    @Test
    void testSclComponents() {
        v.set(1.0f, 2.0f, 3.0f);
        v.scl(3.0f, 2.0f, 9.0f);

        assertEquals(3.0f, v.x);
        assertEquals(4.0f, v.y);
        assertEquals(27.0f, v.z);
    }

    @Test
    void testDivVector() {
        v.set(1.0f, 2.0f, 3.0f);
        v.div(new Vector3f(4.0f, 8.0f, 12.0f));

        assertEquals(0.25f, v.x);
        assertEquals(0.25f, v.y);
        assertEquals(0.25f, v.z);
    }

    @Test
    void testDivValue() {
        v.set(1.0f, 2.0f, 3.0f);
        v.div(2.0f);

        assertEquals(0.5f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(1.5f, v.z);
    }

    @Test
    void testDivComponents() {
        v.set(1.0f, 2.0f, 3.0f);
        v.div(4.0f, 8.0f, 12.0f);

        assertEquals(0.25f, v.x);
        assertEquals(0.25f, v.y);
        assertEquals(0.25f, v.z);
    }

    @Test
    void dstReturnsExpectedValue() {
        v.set(3.0f, 0.0f, 0.0f);
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);

        assertEquals(2.0f, v.dst(v1));
        assertEquals(2.0f, v.dst(1.0f, 0.0f, 0.0f));
        assertEquals(2.0f, Vector3f.dst(v, v1));
        assertEquals(2.0f, Vector3f.dst(3.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
    }

    @Test
    void dst2ReturnsExpectedValue() {
        v.set(3.0f, 0.0f, 0.0f);
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);

        assertEquals(4.0f, v.dst2(v1));
        assertEquals(4.0f, v.dst2(1.0f, 0.0f, 0.0f));
        assertEquals(4.0f, Vector3f.dst2(v, v1));
        assertEquals(4.0f, Vector3f.dst2(3.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
    }

    @Test
    void dstDoesNotDependOnOrder() {
        v.set(3.0f, 0.0f, 0.0f);
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);

        assertEquals(2.0f, v.dst(v1));
        assertEquals(2.0f, v1.dst(v));
    }

    @Test
    void dst2DoesNotDependOnOrder() {
        v.set(3.0f, 0.0f, 0.0f);
        Vector3f v1 = new Vector3f(1.0f, 0.0f, 0.0f);

        assertEquals(4.0f, v.dst2(v1));
        assertEquals(4.0f, v1.dst2(v));
    }

    @Test
    void dstToSelfIsZero() {
        v.set(1.0f, 2.0f, 3.0f);

        assertEquals(0.0f, v.dst(v));
        assertEquals(0.0f, v.dst2(v));
    }

    @Test
    void testLerp() {
        v.setZero();
        Vector3f v1 = new Vector3f(1.0f, 2.0f, 4.0f);

        v.lerp(v1, 0.0f);
        assertEquals(0.0f, v.x);
        assertEquals(0.0f, v.y);
        assertEquals(0.0f, v.z);

        v.setZero();
        v.lerp(v1, 0.5f);
        assertEquals(0.5f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(2.0f, v.z);

        v.setZero();
        v.lerp(v1, 1.0f);
        assertEquals(1.0f, v.x);
        assertEquals(2.0f, v.y);
        assertEquals(4.0f, v.z);
    }

    @Test
    void testStaticLerp() {
        v.setZero();
        Vector3f v1 = new Vector3f(1.0f, 2.0f, 4.0f);

        Vector3f r = Vector3f.lerp(v, v1, 0.0f);
        assertEquals(0.0f, r.x);
        assertEquals(0.0f, r.y);
        assertEquals(0.0f, r.z);

        r = Vector3f.lerp(v, v1, 0.5f);
        assertEquals(0.5f, r.x);
        assertEquals(1.0f, r.y);
        assertEquals(2.0f, r.z);

        r = Vector3f.lerp(v, v1, 1.0f);
        assertEquals(1.0f, r.x);
        assertEquals(2.0f, r.y);
        assertEquals(4.0f, r.z);
    }

    @Test
    void testAddMulScalar() {
        v.set(2.0f, -3.0f, 5.0f);
        Vector3f v1 = new Vector3f(4.0f, 2.0f, 1.0f);
        v.addMul(v1, -2.0f);

        assertEquals(-6.0f, v.x);
        assertEquals(-7.0f, v.y);
        assertEquals(3.0f, v.z);
    }

    @Test
    void testAddMulVector() {
        v.set(2.0f, -3.0f, 5.0f);
        Vector3f v1 = new Vector3f(4.0f, 2.0f, 1.0f);
        Vector3f v2 = new Vector3f(0.0f, 2.0f, -1.0f);
        v.addMul(v1, v2);

        assertEquals(2.0f, v.x);
        assertEquals(1.0f, v.y);
        assertEquals(4.0f, v.z);
    }

    @Test
    void testDotProduct() {
        v.set(2.0f, -3.0f, 5.0f);
        Vector3f v1 = new Vector3f(4.0f, 2.0f, 1.0f);

        assertEquals(7.0f, v.dot(v1));
        assertEquals(7.0f, v1.dot(v));
    }

    @Test
    void testCrossProduct() {
        v.set(2.0f, -3.0f, 5.0f);
        Vector3f v1 = new Vector3f(4.0f, 2.0f, 1.0f);
        v.crs(v1);

        assertEquals(-13.0f, v.x);
        assertEquals(18.0f, v.y);
        assertEquals(16.0f, v.z);
    }

    @Test
    void testTo() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = new Vector3f(2.0f, -1.0f, 2.0f);

        Vector3f v2 = v.cpy().to(v1);
        assertEquals(1.0f, v2.x);
        assertEquals(-3.0f, v2.y);
        assertEquals(-1.0f, v2.z);

        v2 = v.to(2.0f, -1.0f, 2.0f);
        assertEquals(1.0f, v2.x);
        assertEquals(-3.0f, v2.y);
        assertEquals(-1.0f, v2.z);
    }

    @Test
    void vectorIsOnLineWithSelf() {
        v.set(255.0f, -2410.3214f, 542.4664f);

        assertTrue(v.isOnLine(v));
        assertTrue(v.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void parallelVectorsAreOnLinePrecise() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = new Vector3f(-2.0f, -4.0f, -6.0f);

        assertTrue(v.isOnLine(v1));
        assertTrue(v1.isOnLine(v));
    }

    @Test
    void parallelVectorsAreOnLineApprox() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(4.999996f, -2.000003f, 17.000004f);

        assertTrue(v.isOnLine(v1, MathUtils.EPSILON));
        assertTrue(v1.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelVectorsAreNotOnLinePrecise() {
        v = Vector3f.unitX();

        assertFalse(v.isOnLine(Vector3f.unitY()));
        assertFalse(v.isOnLine(Vector3f.unitZ()));
    }

    @Test
    void nonParallelVectorsAreNotOnLineApprox() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(5.0f, -2.0f, 17.3f);

        assertFalse(v.isOnLine(v1, MathUtils.EPSILON));
        assertFalse(v1.isOnLine(v, MathUtils.EPSILON));
    }

    @Test
    void vectorIsCollinearWithSelf() {
        v.set(255.0f, -2410.3214f, 542.4664f);

        assertTrue(v.isCollinear(v));
        assertTrue(v.isCollinear(v, MathUtils.EPSILON));
    }

    @Test
    void collVecsAreCollPrecise() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = v.cpy().scl(412.931f);

        assertTrue(v.isCollinear(v1));
        assertTrue(v1.isCollinear(v));
    }

    @Test
    void collVecsAreCollApprox() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(95.000004f, -38.0000007f, 322.999996f);

        assertTrue(v.isCollinear(v1, MathUtils.EPSILON));
        assertTrue(v1.isCollinear(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelSameDirVecsAreNonColl() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(5.5f, -2.0f, 17.0f);

        assertFalse(v.isCollinear(v1));
        assertFalse(v1.isCollinear(v));
    }

    @Test
    void parallelDiffDirVecsAreNonColl() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(-5.0f, 2.0f, -17.0f);

        assertFalse(v.isCollinear(v1));
        assertFalse(v1.isCollinear(v));
    }

    @Test
    void collOppositeVecsAreCollOppositePrecise() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = v.cpy().scl(-5);

        assertTrue(v.isCollinearOpposite(v1));
        assertTrue(v1.isCollinearOpposite(v));
    }

    @Test
    void collOppositeVecsAreCollOppositeApprox() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(-95.000004f, 38.0000007f, -322.999996f);

        assertTrue(v.isCollinearOpposite(v1, MathUtils.EPSILON));
        assertTrue(v1.isCollinearOpposite(v, MathUtils.EPSILON));
    }

    @Test
    void nonParallelDiffDirVecsAreNonCollOpposite() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = new Vector3f(-5.5f, 2.0f, -17.0f);

        assertFalse(v.isCollinearOpposite(v1));
        assertFalse(v1.isCollinearOpposite(v));
    }

    @Test
    void parallelSameDirVecsAreNonCollOpposite() {
        v.set(5.0f, -2.0f, 17.0f);
        Vector3f v1 = v.cpy().scl(22.421f);

        assertFalse(v.isCollinearOpposite(v1));
        assertFalse(v1.isCollinearOpposite(v));
    }

    @Test
    void vectorIsNotCollinearOppositeWithItself() {
        v.set(255.0f, -2410.3214f, 542.4664f);

        assertFalse(v.isCollinearOpposite(v));
        assertFalse(v.isCollinearOpposite(v, MathUtils.EPSILON));
    }

    @Test
    void vectorIsNotOrthogonalToItself() {
        v = Vector3f.unitX().scl(5.0f);

        assertFalse(v.isOrthogonal(v));
    }

    @Test
    void zeroIsOrthogonalToItself() {
        v.setZero();

        assertTrue(v.isOrthogonal(v));
    }

    @Test
    void vectorIsOrthogonalToZero() {
        v.set(1.0f, 2.0f, 3.0f);

        assertTrue(v.isOrthogonal(Vector3f.zero()));
        assertTrue(Vector3f.zero().isOrthogonal(v));
    }

    @Test
    void orthogonalVecsAreOrthogonal() {
        assertTrue(Vector3f.unitX().isOrthogonal(Vector3f.unitY()));
        assertTrue(Vector3f.unitY().isOrthogonal(Vector3f.unitZ()));
        assertTrue(Vector3f.unitX().isOrthogonal(Vector3f.unitZ()));
    }

    @Test
    void testOrthogonalApprox() {
        v.set(5.0f, -1.0f, 0.0f);
        Vector3f v1 = new Vector3f(1.0f, 4.999992f, 0.0f);

        assertTrue(v.isOrthogonal(v1, MathUtils.EPSILON));
        assertTrue(v1.isOrthogonal(v, MathUtils.EPSILON));
    }

    @Test
    void nonOrthogonalAreNonOrthogonal() {
        v.set(5.0f, 10.f, 15.0f);
        Vector3f v1 = new Vector3f(6.0f, 11.0f, 10.0f);

        assertFalse(v.isOrthogonal(v1));
        assertFalse(v1.isOrthogonal(v));
    }

    @Test
    void vectorHasSameDirectionWithItself() {
        v.set(5.0f, 10.0f, -125.0f);

        assertTrue(v.hasSameDirection(v));
    }

    @Test
    void sameDirectionVecsHaveSameDirection() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = new Vector3f(5.0f, 7.0f, 11.0f);

        assertTrue(v.hasSameDirection(v1));
        assertTrue(v1.hasSameDirection(v));
    }

    @Test
    void oppositeDirectionVecsHaveOppositeDirection() {
        v.set(1.0f, 2.0f, 3.0f);
        Vector3f v1 = v.cpy().scl(-3);

        assertTrue(v.hasOppositeDirection(v1));
        assertTrue(v1.hasOppositeDirection(v));
    }
}
