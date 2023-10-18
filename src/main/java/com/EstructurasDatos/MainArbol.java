package com.EstructurasDatos;
import java.util.Scanner;
class Nodo {
    String valor;
    Nodo izquierdo, derecho;
    Nodo(String item) {
        valor = item;
        izquierdo = derecho = null;
    }
}
class ArbolBinario {
    Nodo raiz;
    ArbolBinario() {
        raiz = null;
    }
    void insertar(String valor) {
        raiz = insertarRec(raiz, valor);
    }
    Nodo insertarRec(Nodo raiz, String valor) {
        if (raiz == null) {
            raiz = new Nodo(valor);
            return raiz;
        }
        if (esOperador(valor)) {
            raiz.izquierdo = insertarRec(raiz.izquierdo, valor);
        } else {
            raiz.derecho = insertarRec(raiz.derecho, valor);
        }
        return raiz;
    }
    boolean esOperador(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/");
    }
    double evaluar() {
        return evaluarRec(raiz);
    }
    double evaluarRec(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        if (!esOperador(raiz.valor)) {
            return Double.parseDouble(raiz.valor);
        }
        double izquierdo = evaluarRec(raiz.izquierdo);
        double derecho = evaluarRec(raiz.derecho);
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
}
public class MainArbol {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la expresión matemática en notación de árbol binario:");
        String expresion = scanner.nextLine();
        String[] valores = expresion.split(" ");
        for (String valor : valores) {
            if (valor != null && !valor.isEmpty()) {
                arbol.insertar(valor);
            }
        }
        double resultado = arbol.evaluar();
        System.out.println("El resultado de la operación es: " + resultado);
        scanner.close();
    }
}