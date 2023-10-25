package com.Cliente;

public class Historial {
    private String usuario;
    private String expresion;
    private String resultado;
    private String fecha;

    // Constructor
    public Historial(String usuario, String expresion, String resultado, String fecha) {
        this.usuario = usuario;
        this.expresion = expresion;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    // Getter y setter de usuario
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Getter y setter de expresion
    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    // Getter y setter de resultado
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    // Getter y setter de fecha
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
