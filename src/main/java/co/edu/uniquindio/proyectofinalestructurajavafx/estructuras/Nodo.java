package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

/**
 * Clase que representa un nodo genérico para estructuras de datos enlazadas
 * @param <T> Tipo de dato que almacena el nodo
 */
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;

    /**
     * Constructor del nodo
     * @param dato Dato a almacenar en el nodo
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
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
     * Obtiene el nodo siguiente
     * @return Nodo siguiente
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el nodo siguiente
     * @param siguiente Nuevo nodo siguiente
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}