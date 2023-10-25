package com.EstructurasDatos;
import java.util.Scanner;
import java.util.Stack;

public class MainArbol {
    public static boolean esOperador(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/");
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

        switch (raiz.valor) {
            case "+":
                return izquierdo + derecho;
            case "-":
                return izquierdo - derecho;
            case "*":
                return izquierdo * derecho;
            case "/":
                return izquierdo / derecho;
            default:
                return 0;
        }
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







