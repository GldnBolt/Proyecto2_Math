package com.EstructurasDatos;

import java.util.Stack;

/**
 * Clase MainArbol: Clase principal que contiene el método main para ejecutar el programa.
 * @version 7.3, 26/10/2023
 */
public class MainArbol {
    public static boolean esOperador(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/") || valor.equals("%") || valor.equals("**") || valor.equals("!") || valor.equals("(+)");
    }

    /**
     * Método para construir el árbol de expresión.
     * @param expresion Expresión en notación postfija.
     * @return Nodo raíz del árbol de expresión.
     */
    public static Nodo construirArbol(String[] expresion) {
        Stack<Nodo> pila = new Stack<>();

        for (String token : expresion) {
            if (!esOperador(token)) {
                Nodo nodo = new Nodo(token);
                pila.push(nodo);
            } else {
                Nodo nodo = new Nodo(token);
                nodo.derecho = pila.pop();
                nodo.izquierdo = pila.pop();
                pila.push(nodo);
            }
        }

        return pila.pop();
    }

    /**
     * Método para evaluar el árbol de expresión.
     * @param raiz Nodo raíz del árbol de expresión.
     * @return Resultado de la evaluación del árbol de expresión.
     */
    public static int evaluarArbol(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }

        if (raiz.izquierdo == null && raiz.derecho == null) {
            System.out.println(raiz.valor);
            return Integer.parseInt(raiz.valor);
        }

        int izquierdo = evaluarArbol(raiz.izquierdo);
        int derecho = evaluarArbol(raiz.derecho);

        return switch (raiz.valor) {
            case "+" -> izquierdo + derecho; // Operador Suma
            case "-" -> izquierdo - derecho; // Operador Resta
            case "*" -> izquierdo * derecho; // Operador Multiplicación
            case "/" -> izquierdo / derecho; // Operador División
            case "%" -> izquierdo % derecho; // Operador Modulo
            case "!" -> izquierdo == 0 ? 1 : 0; // Operador Not
            case "^" -> izquierdo ^ derecho; // Operador Xor
            case "**" -> (int) Math.pow(izquierdo, derecho); // Operador Potencia
            default -> 0;

        };
    }

    /**
     * Método para convertir las expresiones lógicas a su forma algebraica.
     * @param expresion Expresión lógica.
     * @return Expresión algebraica.
     */
    public static String convertirExpresionesLogicas(String expresion) {
        // Reemplazar las expresiones lógicas por sus variantes algebraicas
        return expresion.replaceAll("&&", "*") // and
                .replaceAll("\\|", "+"); // or
    }
}







