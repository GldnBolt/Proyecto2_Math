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

    void inorden() {
        inordenRec(raiz);
    }

    void inordenRec(Nodo raiz) {
        if (raiz != null) {
            inordenRec(raiz.izquierdo);
            System.out.print(raiz.valor + " ");
            inordenRec(raiz.derecho);
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
            arbol.insertar(valor);
        }

        System.out.println("Recorrido en orden del árbol:");
        arbol.inorden();

        scanner.close();
    }
}