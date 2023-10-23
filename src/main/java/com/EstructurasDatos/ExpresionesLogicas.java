package com.EstructurasDatos;
import java.util.Scanner;

public class ExpresionesLogicas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una expresión matemática: ");
        String expresion = scanner.nextLine();
        String expresionAlgebraica = convertirExpresionesLogicas(expresion);
        System.out.println("Expresión algebraica: " + expresionAlgebraica);
    }

    public static String convertirExpresionesLogicas(String expresion) {
        // Expresiones regulares para identificar las expresiones lógicas
        String regexAnd = "\\b&&\\b";
        String regexOr = "\\b\\|\\|\\b";
        String regexNot = "\\b!\\b";
        String regexXor = "\\b\\^\\b";

        // Variantes algebraicas de las expresiones lógicas
        String algebraicoAnd = "*";
        String algebraicoOr = "+";
        String algebraicoNot = "-";
        String algebraicoXor = "xor";

        // Reemplazar las expresiones lógicas por sus variantes algebraicas

        return expresion.replaceAll(regexAnd, algebraicoAnd)
                .replaceAll(regexOr, algebraicoOr)
                .replaceAll(regexNot, algebraicoNot)
                .replaceAll(regexXor, algebraicoXor);
    }
}


