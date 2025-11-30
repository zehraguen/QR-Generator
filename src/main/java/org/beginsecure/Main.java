package org.beginsecure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int width = 300;
        int height = 300;

        // 1. Create the blank image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 2. Get Graphics2D to draw on it
        Graphics2D g = image.createGraphics();

        // 3. Fill background with white (or any color)
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.dispose();

        // 4. Save to file
        ImageIO.write(image, "png", new File("blank.png"));

        System.out.println("Created blank.png");
    }
}