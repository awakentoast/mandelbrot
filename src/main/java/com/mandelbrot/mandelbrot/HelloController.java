package com.mandelbrot.mandelbrot;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class HelloController {


    @FXML
    private Canvas canvas;
    PixelWriter pixelWriter;
    GraphicsContext gc;
    WritableImage writableImage;

    public int canvasWidth;
    public int canvasHeight;
    Mandelbrot mandelbrot = new Mandelbrot();




    //    public Boolean calcOnePixel(double x, double y) {
    //        int iteration = 0;
    //        int maxIterations = 1000;
    //        while (x*x + y*y <= 2*2 && iteration < maxIterations) {
    //            int xTemp = x*x - y*y +
    //        }
    //    }
    //
    @FXML
    public void updateCanvas() {
        int[] pixels = mandelbrot.makeImage(canvasWidth, canvasHeight, -2.00, 0.47, -1.12, 1.12);
        pixelWriter.setPixels(0, 0, canvasWidth, canvasHeight, PixelFormat.getIntArgbInstance(), pixels, 0, canvasWidth);
        gc.drawImage(writableImage, 0, 0);
    }

    public void initialize() {
        canvasHeight = (int) canvas.getHeight();
        canvasWidth = (int) canvas.getWidth();

        gc = canvas.getGraphicsContext2D();
        writableImage = new WritableImage(canvasWidth, canvasHeight);
        pixelWriter = writableImage.getPixelWriter();
        //int[] pixels = mandelbrot.makeImage(5, 5, -2.00, 0.47, -1.12, 1.12);

        int[] pixels = mandelbrot.makeImage(canvasWidth, canvasHeight, -2.00, 0.47, -1.12, 1.12);
        pixelWriter.setPixels(0, 0, canvasWidth, canvasHeight, PixelFormat.getIntArgbInstance(), pixels, 0, canvasWidth);
        gc.drawImage(writableImage, 0, 0);
    }
}