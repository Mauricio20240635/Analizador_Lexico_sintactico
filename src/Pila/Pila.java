package Pila;


import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauri
 */
public class Pila {

    private Nodo tope; // Referencia al nodo en el tope de la pila

    public Pila() {
        this.tope = null;
    }

    public boolean empty() {
        return tope == null;
    }

    public void push(String dato) {
        Nodo nuevoNodo = new Nodo(dato);

        if (empty()) {
            tope = nuevoNodo; // Si la pila está vacía, el nuevo nodo se convierte en el tope
        } else {
            nuevoNodo.siguiente = tope; // El nuevo nodo se enlaza con el nodo actual en el tope
            tope = nuevoNodo; // El nuevo nodo se convierte en el nuevo tope de la pila
        }
    }

    public String pop() {
        if (empty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        String datoDesapilado = tope.dato; // Almacenar el dato del tope de la pila a ser desapilado
        tope = tope.siguiente; // El siguiente nodo se convierte en el nuevo tope de la pila
        return datoDesapilado; // Retornar el dato desapilado
    }

    public String peek() {
        if (empty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        return tope.dato; // Retornar el dato del tope de la pila sin desapilarlo
    }

    public void imprimirPila() {
        if (empty()) {
            System.out.println("La pila está vacía");
        } else {
            Nodo actual = tope; // Iniciar desde el tope de la pila
            while (actual != null) { // Recorrer la pila hasta llegar al final (null)
                System.out.println(actual.dato); // Imprimir el dato del nodo actual
                actual = actual.siguiente; // Mover al siguiente nodo
            }
        }
    }
}


