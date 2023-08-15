package Pila;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Nodo {
    public String dato; // Dato almacenado en el nodo
    public Nodo siguiente; // Referencia al siguiente nodo

    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = null;
    }

}