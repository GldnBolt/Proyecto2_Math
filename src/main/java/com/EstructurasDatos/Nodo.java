package com.EstructurasDatos;

public class Nodo {
    String valor;
    Nodo izquierdo;
    Nodo derecho;

    /**
     * Constructor de la clase Nodo.
     * @param valor Valor del nodo.
     */
    public Nodo(String valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}
