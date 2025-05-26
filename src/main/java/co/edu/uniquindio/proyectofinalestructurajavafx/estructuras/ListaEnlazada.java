package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Implementación de una lista enlazada genérica
 * @param <T> Tipo de dato que almacena la lista
 */
public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;


    /**
     * Constructor de la lista enlazada
     */
    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public int size() {
        int contador = 0;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    public T get(int index) {
        if (index < 0) throw new IndexOutOfBoundsException();
        Nodo<T> actual = cabeza;
        int contador = 0;
        while (actual != null) {
            if (contador == index) {
                return actual.getDato();
            }
            contador++;
            actual = actual.getSiguiente();
        }
        throw new IndexOutOfBoundsException();
    }
    /**
     * Agrega un elemento al final de la lista
     * @param dato Elemento a agregar
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        
        tamaño++;
    }

    /**
     * Agrega un elemento al inicio de la lista
     * @param dato Elemento a agregar
     */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        }
        
        tamaño++;
    }

    /**
     * Agrega un elemento en una posición específica
     * @param dato Elemento a agregar
     * @param indice Posición donde agregar el elemento
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    public void agregarEn(T dato, int indice) {
        if (indice < 0 || indice > tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        
        if (indice == tamaño) {
            agregar(dato);
            return;
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.getSiguiente();
        }
        
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevoNodo);
        
        tamaño++;
    }

    /**
     * Elimina el primer elemento de la lista
     * @return El elemento eliminado
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        
        if (cabeza == null) {
            cola = null;
        }
        
        tamaño--;
        return dato;
    }

    /**
     * Elimina el último elemento de la lista
     * @return El elemento eliminado
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        if (cabeza == cola) {
            return eliminarPrimero();
        }
        
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != cola) {
            actual = actual.getSiguiente();
        }
        
        T dato = cola.getDato();
        cola = actual;
        cola.setSiguiente(null);
        
        tamaño--;
        return dato;
    }

    /**
     * Elimina un elemento en una posición específica
     * @param indice Posición del elemento a eliminar
     * @return El elemento eliminado
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    public T eliminarEn(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        
        if (indice == 0) {
            return eliminarPrimero();
        }
        
        if (indice == tamaño - 1) {
            return eliminarUltimo();
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.getSiguiente();
        }
        
        T dato = actual.getSiguiente().getDato();
        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        
        tamaño--;
        return dato;
    }

    /**
     * Elimina la primera ocurrencia de un elemento
     * @param dato Elemento a eliminar
     * @return true si se eliminó el elemento, false si no se encontró
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        if (cabeza.getDato().equals(dato)) {
            eliminarPrimero();
            return true;
        }
        
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }
        
        if (actual.getSiguiente() == null) {
            return false;
        }
        
        if (actual.getSiguiente() == cola) {
            cola = actual;
        }
        
        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        tamaño--;
        
        return true;
    }

    /**
     * Obtiene el elemento en una posición específica
     * @param indice Posición del elemento
     * @return El elemento en la posición especificada
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        
        return actual.getDato();
    }

    /**
     * Busca un elemento en la lista
     * @param dato Elemento a buscar
     * @return true si el elemento está en la lista, false en caso contrario
     */
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        
        return false;
    }

    /**
     * Busca elementos que cumplan con un criterio
     * @param criterio Predicado que define el criterio de búsqueda
     * @return Una nueva lista con los elementos que cumplen el criterio
     */
    public ListaEnlazada<T> buscar(Predicate<T> criterio) {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            if (criterio.test(actual.getDato())) {
                resultado.agregar(actual.getDato());
            }
            actual = actual.getSiguiente();
        }
        
        return resultado;
    }

    /**
     * Verifica si la lista está vacía
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Obtiene el tamaño de la lista
     * @return Número de elementos en la lista
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * Vacía la lista
     */
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    /**
     * Obtiene el primer elemento de la lista
     * @return El primer elemento
     * @throws NoSuchElementException si la lista está vacía
     */
    public T primero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return cabeza.getDato();
    }

    /**
     * Obtiene el último elemento de la lista
     * @return El último elemento
     * @throws NoSuchElementException si la lista está vacía
     */
    public T ultimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return cola.getDato();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;
            
            @Override
            public boolean hasNext() {
                return actual != null;
            }
            
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }
            actual = actual.getSiguiente();
        }
        
        sb.append("]");
        return sb.toString();
    }
}
