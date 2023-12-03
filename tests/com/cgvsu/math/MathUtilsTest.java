package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    @Test
    void lerp() {
        assertEquals(0.0f, MathUtils.lerp(0.0f, 10.0f, 0.0f));
        assertEquals(2.0f, MathUtils.lerp(0.0f, 10.0f, 0.2f));
        assertEquals(5.0f, MathUtils.lerp(0.0f, 10.0f, 0.5f));
        assertEquals(8.0f, MathUtils.lerp(0.0f, 10.0f, 0.8f));
        assertEquals(10.0f, MathUtils.lerp(0.0f, 10.0f, 1.0f));
    }

    @Test
    void clampFloat() {
        assertEquals(-2.0f, MathUtils.clamp(-10.0f, -2.0f, 5.0f));
        assertEquals(1.0f, MathUtils.clamp(1.0f, -2.0f, 5.0f));
        assertEquals(5.0f, MathUtils.clamp(12.0f, -2.0f, 5.0f));
    }

    @Test
    void clampInt() {
        assertEquals(-2, MathUtils.clamp(-10, -2, 5));
        assertEquals(1, MathUtils.clamp(1, -2, 5));
        assertEquals(5, MathUtils.clamp(12, -2, 5));
    }

    @Test
    void clampLong() {
        assertEquals(-2L, MathUtils.clamp(-10L, -2L, 5L));
        assertEquals(1L, MathUtils.clamp(1L, -2L, 5L));
        assertEquals(5L, MathUtils.clamp(12L, -2L, 5L));
    }

    @Test
    void clampDouble() {
        assertEquals(-2.0, MathUtils.clamp(-10.0, -2.0, 5.0));
        assertEquals(1.0, MathUtils.clamp(1.0, -2.0, 5.0));
        assertEquals(5.0, MathUtils.clamp(12.0, -2.0, 5.0));
    }

    @Test
    void map() {
        assertEquals(0.0f, MathUtils.map(-1.0f, -1.0f, 1.0f, 0.0f, 1.0f));
        assertEquals(0.5f, MathUtils.map(0.0f, -1.0f, 1.0f, 0.0f, 1.0f));
        assertEquals(1.0f, MathUtils.map(1.0f, -1.0f, 1.0f, 0.0f, 1.0f));
    }
}
