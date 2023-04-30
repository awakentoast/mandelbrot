package com.mandelbrot.mandelbrot;

public class Mandelbrot {

    public int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    private int calc(double xPos, double yPos) {

        int maxIterations = 1000;
        double x = 0;
        double y = 0;
        double xTemp = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            xTemp = x * x - y * y + xPos;
            y = 2 * x * y + yPos;
            x = xTemp;
        }
//        while (x*x + y*y <= 2*2 && iteration < maxIterations) {
//            xTemp = x * x - y * y + xPos;
//            y = 2 * x * y + yPos;
//            x = xTemp;
//            iteration++;
//        }
        if ((x*x + y*y) <= 2*2) {
            return getIntFromColor(45, 45, 70);
        }
        return getIntFromColor(25, 255, 255);

    }


    private double pointScaling(double min, double max, int pixelPos, int size) {
        double step = (max + Math.abs(min)) / size;
        return min + step * pixelPos;
    }

    public int[] makeImage(int width, int height, double xMin, double xMax, double yMin, double yMax) {
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double xPoint = pointScaling(xMin, xMax, x, width);
                double yPoint = pointScaling(yMin, yMax, y, height);

                pixels[width * y + x] = calc(xPoint, yPoint);
            }
        }
        return pixels;
    }
}
