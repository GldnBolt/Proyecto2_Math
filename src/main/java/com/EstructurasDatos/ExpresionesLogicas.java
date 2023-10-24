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
        // Reemplazar las expresiones lógicas por sus variantes algebraicas
        return expresion.replaceAll("&&", "*")
                .replaceAll("\\|\\|", "+")
                .replaceAll("!", "~")
                .replaceAll("\\^", "xor");
    }
}





