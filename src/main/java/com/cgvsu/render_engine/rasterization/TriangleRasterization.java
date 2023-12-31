package com.cgvsu.render_engine.rasterization;

import com.cgvsu.math.MathUtils;
import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.model.Texture;
import com.cgvsu.render_engine.screen.Screen;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class TriangleRasterization {
    /**
     * A comparator used to sort the vertices of a triangle.
     */

    final static int MESH_COLOR = -16777216; //black

    /**
     * A mutable vector used to speed up calculations.
     */
    private static final Vector2f p = new Vector2f();

    /**
     * Draws the specified triangle.
     * @param screen add  screen to draw them.
     * @param trig The triangle to draw.
     */
    public static void drawTriangle(Screen screen,
                                    Triangle trig,
                                    Texture texture,
                                    TextureTriangle textureTrig) {
        drawTriangle(screen, trig.v1, trig.v2, trig.v3, texture, textureTrig);
    }

    /**
     * Draws the specified triangle. The order of the vertices is irrelevant.
     * @param screen add pixels to draw them.
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @param v3 The third vertex.
     */
    public static void drawTriangle(
            final Screen screen,
            final Vector3f v1,
            final Vector3f v2,
            final Vector3f v3,
            final Texture texture,
            final TextureTriangle textureTriangle
    ) {
        textureTriangle.sort();

        final int x1 = (int) textureTriangle.v1.x;
        final int x2 = (int) textureTriangle.v2.x;
        final int x3 = (int) textureTriangle.v3.x;
        final int y1 = (int) textureTriangle.v1.y;
        final int y2 = (int) textureTriangle.v2.y;
        final int y3 = (int) textureTriangle.v3.y;

        // Double the area of the triangle. Used to calculate the barycentric coordinates later.
//        final float area = Math.abs(v1.cut().to(v2.cut()).crs(v1.cut().to(v3.cut())));
        final float area = (1.0f / (float) ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)));


        drawTopTriangle(screen, x1, y1, x2, y2, x3, y3,
                v1, v2, v3, texture, textureTriangle, area);
        drawBottomTriangle(screen, x1, y1, x2, y2, x3, y3,
                v1, v2, v3, texture, textureTriangle, area);
    }

    /**
     * Draws the top triangle as part of the bigger triangle.
     */
    private static void drawTopTriangle(
            final Screen screen,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector3f v1,
            final Vector3f v2,
            final Vector3f v3,
            final Texture texture,
            final TextureTriangle textureTriangle,
            final float area
    ) {
        final int x2x1 = x2 - x1;
        final int x3x1 = x3 - x1;
        final int y2y1 = y2 - y1;
        final int y3y1 = y3 - y1;

        for (int y = y1; y < y2; y++) {
            // No need to check if the divisors are null, because the loop will not execute if y1 == y2.
            int l = x2x1 * (y - y1) / y2y1 + x1; // Edge 1-2.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) { // Swap.
                int tmp = l;
                l = r;
                r = tmp;
            }
            float z = (v1.z + v2.z + v3.z)/3 ; //! formula
            for (int x = l; x <= r; x++) {
                int colorBits = MESH_COLOR;
                boolean meshDrawn = false;

                final Vector2f tp = getTexturePoint(x, y,
                        x1, y1, x2, y2, x3, y3,
                        textureTriangle.t1, textureTriangle.t2, textureTriangle.t3, area);

                if(textureTriangle.meshMode &&
                        (isOnLine(x, y, x1, y1, x2, y2) ||
                                isOnLine(x, y, x1, y1, x3, y3) ||
                                isOnLine(x, y, x3, y3, x2, y2))){
                    meshDrawn = true;
                }else {
                    colorBits = 0;
                }

                if(textureTriangle.textureMode && !meshDrawn) {
                    colorBits = texture.getPixel(tp);
                }
                screen.draw(x, y, z, colorBits);
            }
        }
    }

    /**
     * Draws the bottom triangle as part of the bigger triangle.
     */
    private static void drawBottomTriangle(
            final Screen screen,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector3f v1,
            final Vector3f v2,
            final Vector3f v3,
            final Texture texture,
            final TextureTriangle textureTriangle,
            final float area
    ) {
        final int x3x2 = x3 - x2;
        final int x3x1 = x3 - x1;
        final int y3y2 = y3 - y2;
        final int y3y1 = y3 - y1;

        // Draw the separating line and bottom triangle.
        if (y3y2 == 0 || y3y1 == 0) return; // Stop now if the bottom triangle is degenerate (avoids div by zero).
        for (int y = y2; y <= y3; y++) {
            int l = x3x2 * (y - y2) / y3y2 + x2; // Edge 2-3.
            int r = x3x1 * (y - y1) / y3y1 + x1; // Edge 1-3.
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            float z = (v1.z + v2.z + v3.z)/3 ;
            for (int x = l; x <= r; x++) {
                int colorBits = MESH_COLOR;
                boolean meshDrawn = false;

                final Vector2f tp = getTexturePoint(x, y,
                        x1, y1, x2, y2, x3, y3,
                        textureTriangle.t1, textureTriangle.t2, textureTriangle.t3, area);

                if(textureTriangle.meshMode &&
                    (isOnLine(x, y, x1, y1, x2, y2) ||
                        isOnLine(x, y, x1, y1, x3, y3) ||
                        isOnLine(x, y, x3, y3, x2, y2))){
                    meshDrawn = true;
                }else {
                    colorBits = 0;
                }

                if(textureTriangle.textureMode && !meshDrawn) {
                    colorBits = texture.getPixel(tp);
                }
                screen.draw(x, y, z, colorBits);
            }
        }
    }

    /**
     * Interpolates the color for the given coordinate.
     * @return The interpolated color bits in the ARGB format.
     */
    private static Vector2f getTexturePoint(
            final int x, final int y,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector2f t1,
            final Vector2f t2,
            final Vector2f t3,
            final float area
    ) {
        p.set(x, y);
//        final float w1 = Math.abs(v2.cpy().to(p)
//                .crs(v2.cpy().to(v3.cpy()))) / area;
//        final float w2 = Math.abs(v1.cpy().to(p).crs(v1.cpy().to(v3.cpy()))) / area;
//        final float w3 = Math.abs(v1.cpy().to(p).crs(v1.cpy().to(v2.cpy()))) / area;

        float w1 = (float) ((x2 - x) * (y3 - y) - (y2 - y) * (x3 - x)) * area;
        float w2 = (float) ((x3 - x) * (y1 - y) - (y3 - y) * (x1 - x)) * area;
        float w3 = 1.0f - (w1 + w2);

        return new Vector2f(
                clamp(w1 * t1.x + w2 * t2.x + w3 * t3.x),
                clamp(w1 * t1.y + w2 * t2.y + w3 * t3.y)
        );
    }

    private static boolean isOnLine(int x, int y, int x1, int y1, int x2, int y2){
        return  Math.abs(sideLenght(x1, y1, x2, y2) - (sideLenght(x, y, x1, y1) + sideLenght(x, y, x2, y2))) < 0.1;
    }

    private static double sideLenght(int x1, int y1, int x2, int y2){
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    /**
     * Clamps the given float value between 0 and 1.
     * @param v The value to clamp.
     * @return The clamped value.
     */
    private static float clamp(float v) {
        if (v < (float) 0.0) return (float) 0.0;
        if (v > (float) 1.0) return (float) 1.0;
        return v;
    }
}
