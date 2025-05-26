package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

import java.util.function.Consumer;

/**
 * Implementación de un árbol binario de búsqueda genérico
 * @param <T> Tipo de dato que almacena el árbol, debe ser comparable
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private NodoArbol<T> raiz;
    private int tamaño;

    /**
     * Constructor del árbol binario de búsqueda
     */
    public ArbolBinarioBusqueda() {
        this.raiz = null;
        this.tamaño = 0;
    }

    /**
     * Inserta un elemento en el árbol
     * @param dato Elemento a insertar
     */
    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
        tamaño++;
    }

    /**
     * Método auxiliar recursivo para insertar un elemento
     * @param nodo Nodo actual
     * @param dato Elemento a insertar
     * @return Nodo actualizado
     */
    private NodoArbol<T> insertarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return new NodoArbol<>(dato);
        }

        int comparacion = dato.compareTo(nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), dato));
        }

        return nodo;
    }

    /**
     * Elimina un elemento del árbol
     * @param dato Elemento a eliminar
     * @return true si se eliminó el elemento, false si no se encontró
     */
    public boolean eliminar(T dato) {
        int tamañoAnterior = tamaño;
        raiz = eliminarRecursivo(raiz, dato);
        return tamaño < tamañoAnterior;
    }

    /**
     * Método auxiliar recursivo para eliminar un elemento
     * @param nodo Nodo actual
     * @param dato Elemento a eliminar
     * @return Nodo actualizado
     */
    private NodoArbol<T> eliminarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = dato.compareTo(nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), dato));
        } else {
            // Caso 1: Nodo hoja
            if (nodo.esHoja()) {
                tamaño--;
                return null;
            }
            
            // Caso 2: Nodo con un solo hijo
            if (nodo.getIzquierdo() == null) {
                tamaño--;
                return nodo.getDerecho();
            }
            
            if (nodo.getDerecho() == null) {
                tamaño--;
                return nodo.getIzquierdo();
            }
            
            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor (el menor valor en el subárbol derecho)
            T sucesor = encontrarMinimo(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), sucesor));
        }

        return nodo;
    }

    /**
     * Encuentra el valor mínimo en un subárbol
     * @param nodo Raíz del subárbol
     * @return Valor mínimo
     */
    private T encontrarMinimo(NodoArbol<T> nodo) {
        T minimo = nodo.getDato();
        while (nodo.getIzquierdo() != null) {
            minimo = nodo.getIzquierdo().getDato();
            nodo = nodo.getIzquierdo();
        }
        return minimo;
    }

    /**
     * Busca un elemento en el árbol
     * @param dato Elemento a buscar
     * @return true si el elemento está en el árbol, false en caso contrario
     */
    public boolean contiene(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    /**
     * Método auxiliar recursivo para buscar un elemento
     * @param nodo Nodo actual
     * @param dato Elemento a buscar
     * @return true si el elemento está en el árbol, false en caso contrario
     */
    private boolean buscarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        }

        int comparacion = dato.compareTo(nodo.getDato());

        if (comparacion == 0) {
            return true;
        } else if (comparacion < 0) {
            return buscarRecursivo(nodo.getIzquierdo(), dato);
        } else {
            return buscarRecursivo(nodo.getDerecho(), dato);
        }
    }

    /**
     * Obtiene un elemento del árbol
     * @param dato Elemento a buscar
     * @return El elemento encontrado o null si no existe
     */
    public T obtener(T dato) {
        return obtenerRecursivo(raiz, dato);
    }

    /**
     * Método auxiliar recursivo para obtener un elemento
     * @param nodo Nodo actual
     * @param dato Elemento a buscar
     * @return El elemento encontrado o null si no existe
     */
    private T obtenerRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = dato.compareTo(nodo.getDato());

        if (comparacion == 0) {
            return nodo.getDato();
        } else if (comparacion < 0) {
            return obtenerRecursivo(nodo.getIzquierdo(), dato);
        } else {
            return obtenerRecursivo(nodo.getDerecho(), dato);
        }
    }

    /**
     * Recorre el árbol en orden (izquierda, raíz, derecha)
     * @param accion Acción a realizar con cada elemento
     */
    public void recorrerEnOrden(Consumer<T> accion) {
        recorrerEnOrdenRecursivo(raiz, accion);
    }

    /**
     * Método auxiliar recursivo para recorrer el árbol en orden
     * @param nodo Nodo actual
     * @param accion Acción a realizar con cada elemento
     */
    private void recorrerEnOrdenRecursivo(NodoArbol<T> nodo, Consumer<T> accion) {
        if (nodo != null) {
            recorrerEnOrdenRecursivo(nodo.getIzquierdo(), accion);
            accion.accept(nodo.getDato());
            recorrerEnOrdenRecursivo(nodo.getDerecho(), accion);
        }
    }

    /**
     * Recorre el árbol en preorden (raíz, izquierda, derecha)
     * @param accion Acción a realizar con cada elemento
     */
    public void recorrerPreOrden(Consumer<T> accion) {
        recorrerPreOrdenRecursivo(raiz, accion);
    }

    /**
     * Método auxiliar recursivo para recorrer el árbol en preorden
     * @param nodo Nodo actual
     * @param accion Acción a realizar con cada elemento
     */
    private void recorrerPreOrdenRecursivo(NodoArbol<T> nodo, Consumer<T> accion) {
        if (nodo != null) {
            accion.accept(nodo.getDato());
            recorrerPreOrdenRecursivo(nodo.getIzquierdo(), accion);
            recorrerPreOrdenRecursivo(nodo.getDerecho(), accion);
        }
    }

    /**
     * Recorre el árbol en postorden (izquierda, derecha, raíz)
     * @param accion Acción a realizar con cada elemento
     */
    public void recorrerPostOrden(Consumer<T> accion) {
        recorrerPostOrdenRecursivo(raiz, accion);
    }

    /**
     * Método auxiliar recursivo para recorrer el árbol en postorden
     * @param nodo Nodo actual
     * @param accion Acción a realizar con cada elemento
     */
    private void recorrerPostOrdenRecursivo(NodoArbol<T> nodo, Consumer<T> accion) {
        if (nodo != null) {
            recorrerPostOrdenRecursivo(nodo.getIzquierdo(), accion);
            recorrerPostOrdenRecursivo(nodo.getDerecho(), accion);
            accion.accept(nodo.getDato());
        }
    }

    /**
     * Recorre el árbol por niveles
     * @param accion Acción a realizar con cada elemento
     */
    public void recorrerPorNiveles(Consumer<T> accion) {
        if (raiz == null) {
            return;
        }

        ListaEnlazada<NodoArbol<T>> cola = new ListaEnlazada<>();
        cola.agregar(raiz);

        while (!cola.estaVacia()) {
            NodoArbol<T> nodo = cola.eliminarPrimero();
            accion.accept(nodo.getDato());

            if (nodo.getIzquierdo() != null) {
                cola.agregar(nodo.getIzquierdo());
            }

            if (nodo.getDerecho() != null) {
                cola.agregar(nodo.getDerecho());
            }
        }
    }

    /**
     * Verifica si el árbol está vacío
     * @return true si el árbol está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Obtiene el tamaño del árbol
     * @return Número de elementos en el árbol
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * Vacía el árbol
     */
    public void vaciar() {
        raiz = null;
        tamaño = 0;
    }

    /**
     * Obtiene la altura del árbol
     * @return Altura del árbol
     */
    public int altura() {
        return alturaRecursiva(raiz);
    }

    /**
     * Método auxiliar recursivo para calcular la altura
     * @param nodo Nodo actual
     * @return Altura del subárbol
     */
    private int alturaRecursiva(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaIzquierda = alturaRecursiva(nodo.getIzquierdo());
        int alturaDerecha = alturaRecursiva(nodo.getDerecho());

        return Math.max(alturaIzquierda, alturaDerecha) + 1;
    }

    /**
     * Obtiene la raíz del árbol
     * @return Nodo raíz
     */
    public NodoArbol<T> getRaiz() {
        return raiz;
    }
}