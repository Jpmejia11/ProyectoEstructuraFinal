package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

import java.util.NoSuchElementException;

/**
 * Implementación de una cola de prioridad genérica usando un montículo binario (heap)
 * @param <T> Tipo de dato de los elementos
 */
public class ColaPrioridad<T> {
    private ElementoPrioridad<T>[] elementos;
    private int tamaño;
    private static final int CAPACIDAD_INICIAL = 10;

    /**
     * Constructor de la cola de prioridad
     */
    @SuppressWarnings("unchecked")
    public ColaPrioridad() {
        this.elementos = (ElementoPrioridad<T>[]) new ElementoPrioridad[CAPACIDAD_INICIAL];
        this.tamaño = 0;
    }

    /**
     * Inserta un elemento con su prioridad
     * @param elemento Elemento a insertar
     * @param prioridad Prioridad del elemento (mayor valor = mayor prioridad)
     */
    public void insertar(T elemento, int prioridad) {
        if (tamaño == elementos.length) {
            expandirCapacidad();
        }

        ElementoPrioridad<T> nuevoElemento = new ElementoPrioridad<>(elemento, prioridad);
        elementos[tamaño] = nuevoElemento;
        subirElemento(tamaño);
        tamaño++;
    }

    /**
     * Expande la capacidad del arreglo
     */
    @SuppressWarnings("unchecked")
    private void expandirCapacidad() {
        ElementoPrioridad<T>[] nuevosElementos = (ElementoPrioridad<T>[]) new ElementoPrioridad[elementos.length * 2];
        System.arraycopy(elementos, 0, nuevosElementos, 0, elementos.length);
        elementos = nuevosElementos;
    }

    /**
     * Sube un elemento en el montículo para mantener la propiedad de heap
     * @param indice Índice del elemento a subir
     */
    private void subirElemento(int indice) {
        int indiceActual = indice;
        int indicePadre = (indiceActual - 1) / 2;

        while (indiceActual > 0 && elementos[indiceActual].getPrioridad() > elementos[indicePadre].getPrioridad()) {
            intercambiar(indiceActual, indicePadre);
            indiceActual = indicePadre;
            indicePadre = (indiceActual - 1) / 2;
        }
    }

    /**
     * Intercambia dos elementos en el arreglo
     * @param i Índice del primer elemento
     * @param j Índice del segundo elemento
     */
    private void intercambiar(int i, int j) {
        ElementoPrioridad<T> temp = elementos[i];
        elementos[i] = elementos[j];
        elementos[j] = temp;
    }

    /**
     * Extrae el elemento con mayor prioridad
     * @return Elemento con mayor prioridad
     * @throws NoSuchElementException si la cola está vacía
     */
    public T extraer() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola de prioridad está vacía");
        }

        T elementoMaximo = elementos[0].getElemento();
        elementos[0] = elementos[tamaño - 1];
        tamaño--;
        
        if (tamaño > 0) {
            bajarElemento(0);
        }

        return elementoMaximo;
    }

    /**
     * Baja un elemento en el montículo para mantener la propiedad de heap
     * @param indice Índice del elemento a bajar
     */
    private void bajarElemento(int indice) {
        int indiceActual = indice;
        int indiceHijoIzquierdo = 2 * indiceActual + 1;
        int indiceHijoDerecho = 2 * indiceActual + 2;
        int indiceMayor = indiceActual;

        // Compara con el hijo izquierdo
        if (indiceHijoIzquierdo < tamaño && 
            elementos[indiceHijoIzquierdo].getPrioridad() > elementos[indiceMayor].getPrioridad()) {
            indiceMayor = indiceHijoIzquierdo;
        }

        // Compara con el hijo derecho
        if (indiceHijoDerecho < tamaño && 
            elementos[indiceHijoDerecho].getPrioridad() > elementos[indiceMayor].getPrioridad()) {
            indiceMayor = indiceHijoDerecho;
        }

        // Si el mayor no es el actual, intercambia y continúa bajando
        if (indiceMayor != indiceActual) {
            intercambiar(indiceActual, indiceMayor);
            bajarElemento(indiceMayor);
        }
    }

    /**
     * Consulta el elemento con mayor prioridad sin extraerlo
     * @return Elemento con mayor prioridad
     * @throws NoSuchElementException si la cola está vacía
     */
    public T consultar() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola de prioridad está vacía");
        }
        return elementos[0].getElemento();
    }

    /**
     * Verifica si la cola está vacía
     * @return true si la cola está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return tamaño == 0;
    }

    /**
     * Obtiene el tamaño de la cola
     * @return Número de elementos en la cola
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * Vacía la cola
     */
    public void vaciar() {
        tamaño = 0;
    }

    /**
     * Actualiza la prioridad de un elemento
     * @param elemento Elemento a actualizar
     * @param nuevaPrioridad Nueva prioridad
     * @return true si se actualizó la prioridad, false si el elemento no está en la cola
     */
    public boolean actualizarPrioridad(T elemento, int nuevaPrioridad) {
        for (int i = 0; i < tamaño; i++) {
            if (elementos[i].getElemento().equals(elemento)) {
                int prioridadAnterior = elementos[i].getPrioridad();
                elementos[i].setPrioridad(nuevaPrioridad);
                
                if (nuevaPrioridad > prioridadAnterior) {
                    subirElemento(i);
                } else if (nuevaPrioridad < prioridadAnterior) {
                    bajarElemento(i);
                }
                
                return true;
            }
        }
        return false;
    }

    /**
     * Convierte la cola a una representación de cadena
     * @return Representación de cadena de la cola
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamaño; i++) {
            sb.append(elementos[i]);
            if (i < tamaño - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}