package com.mandelbrot.mandelbrot;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class HelloController {

    @FXML private Canvas canvas;
    PixelWriter pixelWriter;
    GraphicsContext gc;

    int canvasWidth;
    int canvasHeight;
    Mandelbrot mandelbrot = new Mandelbrot();

    public int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }



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

    }

    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        canvasHeight = (int) canvas.getHeight();
        canvasWidth = (int) canvas.getWidth();
        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        int[] pixels = new int[canvasWidth * canvasHeight];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFF0000FF;
        }
        System.out.println(pixels[0]);
        System.out.println("as");
        System.out.println(pixels[1]);
        System.out.println(pixels[2]);
        System.out.println(pixels[3]);
        System.out.println(pixels[4]);
        

        WritableImage writableImage = new WritableImage(canvasWidth, canvasHeight);
        pixelWriter = writableImage.getPixelWriter();
        pixelWriter.setPixels( 0, 0, canvasWidth, canvasHeight, PixelFormat.getIntArgbInstance(), pixels,0, canvasWidth);
        gc.drawImage(writableImage, 0, 0);

    }
}