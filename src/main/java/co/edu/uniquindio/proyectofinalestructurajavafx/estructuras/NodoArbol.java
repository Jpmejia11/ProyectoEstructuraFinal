package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

/**
 * Clase que representa un nodo para un árbol binario de búsqueda
 * @param <T> Tipo de dato que almacena el nodo, debe ser comparable
 */
public class NodoArbol<T extends Comparable<T>> {
    private T dato;
    private NodoArbol<T> izquierdo;
    private NodoArbol<T> derecho;

    /**
     * Constructor del nodo
     * @param dato Dato a almacenar en el nodo
     */
    public NodoArbol(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    /**
     * Obtiene el dato almacenado en el nodo
     * @return Dato almacenado
     */
    public T getDato() {
        return dato;
    }

    /**
     * Establece el dato del nodo
     * @param dato Nuevo dato a almacenar
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Obtiene el nodo izquierdo
     * @return Nodo izquierdo
     */
    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el nodo izquierdo
     * @param izquierdo Nuevo nodo izquierdo
     */
    public void setIzquierdo(NodoArbol<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Obtiene el nodo derecho
     * @return Nodo derecho
     */
    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo derecho
     * @param derecho Nuevo nodo derecho
     */
    public void setDerecho(NodoArbol<T> derecho) {
        this.derecho = derecho;
    }

    /**
     * Verifica si el nodo es una hoja (no tiene hijos)
     * @return true si es una hoja, false en caso contrario
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}