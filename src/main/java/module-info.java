module com.mandelbrot.mandelbrot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mandelbrot.mandelbrot to javafx.fxml;
    exports com.mandelbrot.mandelbrot;
}