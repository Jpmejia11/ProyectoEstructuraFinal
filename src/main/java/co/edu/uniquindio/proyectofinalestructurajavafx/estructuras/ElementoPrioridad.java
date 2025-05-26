package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

/**
 * Clase que representa un elemento con prioridad para la cola de prioridad
 * @param <T> Tipo de dato del elemento
 */
public class ElementoPrioridad<T> implements Comparable<ElementoPrioridad<T>> {
    private T elemento;
    private int prioridad;

    /**
     * Constructor del elemento con prioridad
     * @param elemento Elemento a almacenar
     * @param prioridad Prioridad del elemento (mayor valor = mayor prioridad)
     */
    public ElementoPrioridad(T elemento, int prioridad) {
        this.elemento = elemento;
        this.prioridad = prioridad;
    }

    /**
     * Obtiene el elemento
     * @return Elemento almacenado
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento
     * @param elemento Nuevo elemento
     */
    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    /**
     * Obtiene la prioridad
     * @return Prioridad del elemento
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Establece la prioridad
     * @param prioridad Nueva prioridad
     */
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(ElementoPrioridad<T> otro) {
        // Mayor prioridad primero (orden descendente)
        return Integer.compare(this.prioridad, otro.prioridad);
    }

    @Override
    public String toString() {
        return "ElementoPrioridad{" +
                "elemento=" + elemento +
                ", prioridad=" + prioridad +
                '}';
    }
}