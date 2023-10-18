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
        if (esOperador(raiz.valor)) {
            raiz.derecho = insertarRec(raiz.derecho, valor);
        } else {
            raiz.izquierdo = insertarRec(raiz.izquierdo, valor);
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
        if (raiz.izquierdo == null && raiz.derecho == null) {
            return Double.parseDouble(raiz.valor);
        }
        double izquierdo = evaluarRec(raiz.izquierdo);
        double derecho = evaluarRec(raiz.derecho);
        if (esOperador(raiz.valor)) {
            switch (raiz.valor) {
                case "+":
                    return izquierdo + derecho;
                case "-":
                    return izquierdo - derecho;
                case "*":
                    return izquierdo * derecho;
                case "/":
                    return izquierdo / derecho;
            }
        }
        return 0;
    }
}
public class MainArbol {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la expresión matemática: ");
        String expresion = sc.nextLine();
        ArbolBinario arbol = new ArbolBinario();
        String[] valores = expresion.split(" ");
        for (String valor : valores) {
            if (valor != null && !valor.isEmpty()) {
                arbol.insertar(valor);
            }
        }
        double resultado = arbol.evaluar();
        System.out.println("El resultado es: " + resultado);
    }
}

