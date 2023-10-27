package com.EstructurasDatos;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

/**
 * Clase que procesa la imagen y extrae el texto
 */
public class CapturaImagen {
    static {
        System.loadLibrary("opencv_java320");
        System.setProperty("jna.library.path", "Tess4J/dist");

    }
    private static final Tesseract tesseract = new Tesseract();

    /**
     * Inicia el tesseract
     */
    public static void inicarTesseract() {
        tesseract.setLanguage("eng+equ");
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789+-*/=()x^!%|");
        tesseract.setDatapath("Tess4J/tessdata");
    }

    /**
     * Procesa la imagen y extrae el texto
     * @param image imagen a procesar
     * @return texto extraido de la imagen
     */
    public static String procesarImagen(Mat image) {
        String filename = "temporal.jpg";
        Imgcodecs.imwrite(filename, image);

        try {                        // Leer el texto de la imagen usando el Tesseract
            String texto = tesseract.doOCR(new File(filename));
            return texto;
        } catch (TesseractException e) {
            System.err.println("Error al extraer el texto: " + e.getMessage());
        }
        return "0";
    }
}

