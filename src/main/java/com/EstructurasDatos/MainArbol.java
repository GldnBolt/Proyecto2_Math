package com.EstructurasDatos;
import java.util.Scanner;
import java.util.Stack;

public class MainArbol {
    public static boolean esOperador(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/") || valor.equals("%") || valor.equals("**") || valor.equals("!") || valor.equals("(+)");
    }

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

    public static String convertirExpresionesLogicas(String expresion) {
        // Reemplazar las expresiones lógicas por sus variantes algebraicas
        return expresion.replaceAll("&&", "*") // and
                .replaceAll("\\|", "+"); // or
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la expresión en notación postfija:");
        String expresion = sc.nextLine();
        String[] tokens = expresion.split(" ");

        Nodo raiz = construirArbol(tokens);
        int resultado = evaluarArbol(raiz);

        System.out.println("El resultado es: " + resultado);
    }
}







