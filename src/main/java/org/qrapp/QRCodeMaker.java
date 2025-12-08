package org.qrapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class QRCodeMaker {
    Boolean[][] qrMatrix;
    BufferedImage image;
    Graphics2D g;
    private int width;
    private int height;
    int moduleSize;

    public QRCodeMaker() {
        this.width = 210;
        this.height = 210;
        this.qrMatrix = new Boolean[21][21];
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                qrMatrix[i][j] = false;
            }
        }

        moduleSize = width/qrMatrix.length;

        // 1. Create the blank image
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 2. Get Graphics2D to draw on it
        g = image.createGraphics();

        // 3. Fill background with white (or any color)
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    public void createPNG() throws IOException {
        convertMatrixToDrawer();
        g.dispose();

        // 4. Save to file
        ImageIO.write(image, "png", new File("blank.png"));

        System.out.println("Created blank.png");
    }

    public void finder(int x, int y) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                int aX = x + i;
                int aY = y + j;

                if (i == 0 || i == 6 || j == 0 || j == 6) qrMatrix[aX][aY] = true;
                else if (i == 1 || i == 5 || j == 1 || j == 5) qrMatrix[aX][aY] = false;
                else qrMatrix[aX][aY] = true;
            }
        }
    }

    public void placeFinder() {
        finder(0, 0);
        finder(0, qrMatrix.length - 7);
        finder(qrMatrix.length - 7, 0);
    }

    private void convertMatrixToDrawer() {
        g.setColor(Color.BLACK);
        for (int row = 0; row < qrMatrix.length; row++) {
            for (int col = 0; col < qrMatrix.length; col++) {
                if (qrMatrix[row][col]) {
                    int x = col * moduleSize;   // col → x
                    int y = row * moduleSize;   // row → y
                    g.fillRect(x, y, moduleSize, moduleSize);
                }
            }
        }
    }
}


