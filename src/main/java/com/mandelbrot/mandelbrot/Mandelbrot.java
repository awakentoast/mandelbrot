package com.mandelbrot.mandelbrot;

import javafx.scene.paint.Color;

public class Mandelbrot implements Runnable {

    private final int width;
    private final int height;
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final int startY;
    private final int endY;
    private final int[] pixels;

    public Mandelbrot(int width, int height, double xMin, double xMax, double yMin, double yMax, int startY, int endY, int[] pixels) {
        this.width = width;
        this.height = height;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.startY = startY;
        this.endY = endY;
        this.pixels = pixels;
    }

    @Override
    public void run() {
        for (int y = startY; y < endY; y++) {
            for (int x = 0; x < width; x++) {
                double xPoint = pointScaling(xMin, xMax, x, width);
                double yPoint = pointScaling(yMin, yMax, y, height);

                pixels[width * y + x] = calc(xPoint, yPoint);
            }
        }
    }

    private double pointScaling(double min, double max, int pixelPos, int size) {
        double step = (max + Math.abs(min)) / size;
        return min + step * pixelPos;
    }

    private int calc(double xPos, double yPos) {

        int maxIterations = 1000;
        double x = 0;
        double y = 0;
        double xTemp = 0;
        int iteration = 0;


        while (x*x + y*y <= 2*2 && iteration < maxIterations) {
            xTemp = x * x - y * y + xPos;
            y = 2 * x * y + yPos;
            x = xTemp;
            iteration++;
        }
        if (iteration == maxIterations) {
            return getIntFromColor(0, 0, 0);
        } else if (iteration % 2 == 0) {
            return getIntFromColor(125, 125, 125);
        } else {
            return getIntFromColor(255, 255, 255);
        }
    }

    private int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

}
