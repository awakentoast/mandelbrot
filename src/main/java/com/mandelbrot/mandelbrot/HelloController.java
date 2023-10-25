package com.mandelbrot.mandelbrot;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.ScrollEvent;


public class HelloController {


    @FXML private Canvas canvas;
    @FXML private TextField xMiddle;
    @FXML private TextField yMiddle;
    @FXML private TextField scale;


    int canvasWidth;
    int canvasHeight;
    GraphicsContext gc;
    WritableImage writableImage;
    PixelWriter pixelWriter;


    double xMin;
    double xMax;
    double yMin;
    double yMax;

    public void draw(int[] pixels) {
        pixelWriter.setPixels(0, 0, canvasWidth, canvasHeight, PixelFormat.getIntArgbInstance(), pixels, 0, canvasWidth);
        gc.drawImage(writableImage, 0, 0);
    }

    public int[] makeImage() throws InterruptedException {
        int[] pixels = new int[canvasWidth * canvasHeight];
        int numThreads = Runtime.getRuntime().availableProcessors() - 1;
        System.out.println(numThreads);
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int startY = i * canvasHeight / numThreads;
            int endY = (i + 1) * canvasHeight / numThreads;
            Mandelbrot mandelbrot = new Mandelbrot(canvasWidth, canvasHeight, xMin, xMax, yMin, yMax, startY, endY, pixels);
            threads[i] = new Thread(mandelbrot);
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
        System.out.println("done mandel");
        return pixels;
    }

    @FXML
    public void updateCanvas(ScrollEvent scrollEvent) throws InterruptedException {
        double xMouse = scrollEvent.getX();

        double yMouse = scrollEvent.getY();

        if (scrollEvent.getDeltaY() > 0) {
            //zoom in
            double xDiff;
            if (xMouse > (double) canvasWidth / 2) {
                xDiff = xMax - Math.abs(xPos);
            } else {
                xDiff = Math.abs(Math.abs(xPos) - Math.abs(xMin));
            }
            xMin = xPos - xDiff;
            xMax = xPos + xDiff;

            double yDiff;
            if (xMouse > (double) canvasWidth / 2) {
                yDiff = yMax - Math.abs(yPos);
            } else {
                yDiff = Math.abs(Math.abs(yPos) - Math.abs(yMin));
            }
            yMin = yPos - yDiff;
            yMax = yPos + yDiff;
        } else {
            //zoom out
            xMin = xPos - xDelta;
            xMax = xPos + xDelta;

            yMin = yPos - yDelta;
            yMax = yPos + yDelta;
        }
        System.out.println(xMin + xMax + yMin + yMax);

        int[] pixels = makeImage();
        draw(pixels);
    }

    public void initialize() throws InterruptedException {
        canvasWidth = (int) canvas.getWidth();
        canvasHeight = (int) canvas.getHeight();
        xMin = -2.00;
        xMax = 0.47;
        yMin = -1.12;
        yMax = 1.12;


        gc = canvas.getGraphicsContext2D();

        writableImage = new WritableImage(canvasWidth, canvasHeight);
        pixelWriter = writableImage.getPixelWriter();

        int[] pixels = makeImage();
        draw(pixels);
    }
}