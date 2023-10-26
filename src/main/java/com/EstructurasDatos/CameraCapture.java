package com.EstructurasDatos;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.File;

public class CameraCapture {

    // Cargar las librerías nativas de OpenCV y Tesseract
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary("opencv_java320");
        System.setProperty("jna.library.path", "path/to/tess4j");
    }

    // Crear un objeto Tesseract para realizar el reconocimiento óptico de caracteres (OCR)
    private static Tesseract tesseract = new Tesseract();

    // Configurar el idioma y el directorio de datos del Tesseract
    private static void setupTesseract() {
        tesseract.setLanguage("spa");
        tesseract.setDatapath("path/to/tessdata");
    }

    // Procesar una imagen y extraer el texto en ella
    private static void processImage(Mat image) {
        // Guardar la imagen en un archivo temporal
        String filename = "temp.jpg";
        Imgcodecs.imwrite(filename, image);

        // Leer el texto de la imagen usando el Tesseract
        try {
            String text = tesseract.doOCR(new File(filename));
            System.out.println("Texto extraído: " + text);
        } catch (TesseractException e) {
            System.err.println("Error al extraer el texto: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Configurar el Tesseract
        setupTesseract();

        // Crear un objeto VideoCapture para acceder a la cámara de la laptop
        VideoCapture camera = new VideoCapture(0);

        // Verificar si la cámara está disponible
        if (!camera.isOpened()) {
            System.err.println("No se puede acceder a la cámara");
            return;
        }

        // Crear una matriz para almacenar la imagen capturada
        Mat frame = new Mat();

        // Capturar una imagen de la cámara
        camera.read(frame);

        // Procesar la imagen capturada
        processImage(frame);

        // Liberar los recursos de la cámara y la matriz
        camera.release();
        frame.release();
    }
}

