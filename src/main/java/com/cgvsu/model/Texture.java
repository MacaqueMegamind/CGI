package com.cgvsu.model;


import com.cgvsu.math.vector.Vector2f;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Texture {
    int[][] pixels;
    Path texturePath;
    int width = 0;
    int height = 0;

    public Texture(Path texturePath) {
        this.texturePath = texturePath;
        readPixels();
    }

    private void readPixels(){
        try {
            BufferedImage bufferedImage = ImageIO.read(texturePath.toFile());
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            pixels = new int[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    pixels[row][col] = bufferedImage.getRGB(col, row);
                }
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public int getPixel(Vector2f v){
        int width = (int) (this.width * v.x);
        int height = (int) (this.height * v.y);
        return pixels[height][width];
    }


}
