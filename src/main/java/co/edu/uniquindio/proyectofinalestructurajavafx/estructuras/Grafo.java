package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementación de un grafo no dirigido genérico
 * @param <T> Tipo de dato de los vértices
 */
public class Grafo<T> {
    private Map<T, ListaEnlazada<T>> adyacencias;

    /**
     * Constructor del grafo
     */
    public Grafo() {
        this.adyacencias = new HashMap<>();
    }

    /**
     * Agrega un vértice al grafo
     * @param vertice Vértice a agregar
     * @return true si se agregó el vértice, false si ya existía
     */
    public boolean agregarVertice(T vertice) {
        if (contieneVertice(vertice)) {
            return false;
        }
        adyacencias.put(vertice, new ListaEnlazada<>());
        return true;
    }

    /**
     * Elimina un vértice del grafo
     * @param vertice Vértice a eliminar
     * @return true si se eliminó el vértice, false si no existía
     */
    public boolean eliminarVertice(T vertice) {
        if (!contieneVertice(vertice)) {
            return false;
        }

        // Eliminar todas las aristas que apuntan a este vértice
        for (T v : adyacencias.keySet()) {
            adyacencias.get(v).eliminar(vertice);
        }

        // Eliminar el vértice y sus adyacencias
        adyacencias.remove(vertice);
        return true;
    }

    /**
     * Agrega una arista entre dos vértices
     * @param origen Vértice origen
     * @param destino Vértice destino
     * @return true si se agregó la arista, false si ya existía o algún vértice no existe
     */
    public boolean agregarArista(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return false;
        }

        if (existeArista(origen, destino)) {
            return false;
        }

        // Grafo no dirigido: agregar en ambas direcciones
        adyacencias.get(origen).agregar(destino);
        adyacencias.get(destino).agregar(origen);
        return true;
    }

    /**
     * Elimina una arista entre dos vértices
     * @param origen Vértice origen
     * @param destino Vértice destino
     * @return true si se eliminó la arista, false si no existía o algún vértice no existe
     */
    public boolean eliminarArista(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return false;
        }

        boolean eliminadoOrigen = adyacencias.get(origen).eliminar(destino);
        boolean eliminadoDestino = adyacencias.get(destino).eliminar(origen);
        
        return eliminadoOrigen && eliminadoDestino;
    }

    /**
     * Verifica si existe una arista entre dos vértices
     * @param origen Vértice origen
     * @param destino Vértice destino
     * @return true si existe la arista, false en caso contrario
     */
    public boolean existeArista(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return false;
        }
        
        return adyacencias.get(origen).contiene(destino);
    }

    /**
     * Verifica si un vértice existe en el grafo
     * @param vertice Vértice a verificar
     * @return true si el vértice existe, false en caso contrario
     */
    public boolean contieneVertice(T vertice) {
        return adyacencias.containsKey(vertice);
    }

    /**
     * Obtiene los vértices adyacentes a un vértice
     * @param vertice Vértice del que se quieren obtener los adyacentes
     * @return Lista de vértices adyacentes
     */
    public ListaEnlazada<T> obtenerAdyacentes(T vertice) {
        if (!contieneVertice(vertice)) {
            return new ListaEnlazada<>();
        }
        
        return adyacencias.get(vertice);
    }

    /**
     * Obtiene todos los vértices del grafo
     * @return Lista de vértices
     */
    public ListaEnlazada<T> obtenerVertices() {
        ListaEnlazada<T> vertices = new ListaEnlazada<>();
        for (T vertice : adyacencias.keySet()) {
            vertices.agregar(vertice);
        }
        return vertices;
    }

    /**
     * Obtiene el número de vértices del grafo
     * @return Número de vértices
     */
    public int numeroVertices() {
        return adyacencias.size();
    }

    /**
     * Obtiene el número de aristas del grafo
     * @return Número de aristas
     */
    public int numeroAristas() {
        int contador = 0;
        for (T vertice : adyacencias.keySet()) {
            contador += adyacencias.get(vertice).tamaño();
        }
        // Dividir por 2 porque cada arista se cuenta dos veces en un grafo no dirigido
        return contador / 2;
    }

    /**
     * Verifica si el grafo está vacío
     * @return true si el grafo está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return adyacencias.isEmpty();
    }

    /**
     * Vacía el grafo
     */
    public void vaciar() {
        adyacencias.clear();
    }

    /**
     * Recorre el grafo en amplitud (BFS) desde un vértice inicial
     * @param inicio Vértice inicial
     * @param accion Acción a realizar con cada vértice
     */
    public void recorridoAmplitud(T inicio, Consumer<T> accion) {
        if (!contieneVertice(inicio)) {
            return;
        }

        ListaEnlazada<T> cola = new ListaEnlazada<>();
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        
        cola.agregar(inicio);
        visitados.agregar(inicio);
        
        while (!cola.estaVacia()) {
            T actual = cola.eliminarPrimero();
            accion.accept(actual);
            
            for (T adyacente : adyacencias.get(actual)) {
                if (!visitados.contiene(adyacente)) {
                    cola.agregar(adyacente);
                    visitados.agregar(adyacente);
                }
            }
        }
    }

    /**
     * Recorre el grafo en profundidad (DFS) desde un vértice inicial
     * @param inicio Vértice inicial
     * @param accion Acción a realizar con cada vértice
     */
    public void recorridoProfundidad(T inicio, Consumer<T> accion) {
        if (!contieneVertice(inicio)) {
            return;
        }
        
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        recorridoProfundidadRecursivo(inicio, visitados, accion);
    }

    /**
     * Método auxiliar recursivo para el recorrido en profundidad
     * @param actual Vértice actual
     * @param visitados Lista de vértices visitados
     * @param accion Acción a realizar con cada vértice
     */
    private void recorridoProfundidadRecursivo(T actual, ListaEnlazada<T> visitados, Consumer<T> accion) {
        visitados.agregar(actual);
        accion.accept(actual);
        
        for (T adyacente : adyacencias.get(actual)) {
            if (!visitados.contiene(adyacente)) {
                recorridoProfundidadRecursivo(adyacente, visitados, accion);
            }
        }
    }

    /**
     * Encuentra el camino más corto entre dos vértices (algoritmo de Dijkstra simplificado para grafos no ponderados)
     * @param origen Vértice origen
     * @param destino Vértice destino
     * @return Lista con el camino más corto, o lista vacía si no hay camino
     */
    public ListaEnlazada<T> caminoMasCorto(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return new ListaEnlazada<>();
        }
        
        // Si origen y destino son iguales
        if (origen.equals(destino)) {
            ListaEnlazada<T> camino = new ListaEnlazada<>();
            camino.agregar(origen);
            return camino;
        }
        
        // Mapa para almacenar el predecesor de cada vértice en el camino más corto
        Map<T, T> predecesores = new HashMap<>();
        
        // Cola para BFS
        ListaEnlazada<T> cola = new ListaEnlazada<>();
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        
        cola.agregar(origen);
        visitados.agregar(origen);
        
        boolean encontrado = false;
        
        // BFS para encontrar el camino más corto
        while (!cola.estaVacia() && !encontrado) {
            T actual = cola.eliminarPrimero();
            
            for (T adyacente : adyacencias.get(actual)) {
                if (!visitados.contiene(adyacente)) {
                    cola.agregar(adyacente);
                    visitados.agregar(adyacente);
                    predecesores.put(adyacente, actual);
                    
                    if (adyacente.equals(destino)) {
                        encontrado = true;
                        break;
                    }
                }
            }
        }
        
        // Si no se encontró un camino
        if (!encontrado) {
            return new ListaEnlazada<>();
        }
        
        // Reconstruir el camino
        ListaEnlazada<T> camino = new ListaEnlazada<>();
        T actual = destino;
        
        while (actual != null) {
            camino.agregarAlInicio(actual);
            actual = predecesores.get(actual);
        }
        
        return camino;
    }

    /**
     * Detecta comunidades en el grafo usando un algoritmo simple basado en componentes conexas
     * @return Lista de comunidades, donde cada comunidad es una lista de vértices
     */
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        ListaEnlazada<ListaEnlazada<T>> comunidades = new ListaEnlazada<>();
        ListaEnlazada<T> noVisitados = obtenerVertices();
        
        while (!noVisitados.estaVacia()) {
            T vertice = noVisitados.primero();
            ListaEnlazada<T> comunidad = new ListaEnlazada<>();
            
            // Usar BFS para encontrar todos los vértices conectados
            ListaEnlazada<T> cola = new ListaEnlazada<>();
            cola.agregar(vertice);
            comunidad.agregar(vertice);
            noVisitados.eliminar(vertice);
            
            while (!cola.estaVacia()) {
                T actual = cola.eliminarPrimero();
                
                for (T adyacente : adyacencias.get(actual)) {
                    if (noVisitados.contiene(adyacente)) {
                        cola.agregar(adyacente);
                        comunidad.agregar(adyacente);
                        noVisitados.eliminar(adyacente);
                    }
                }
            }
            
            comunidades.agregar(comunidad);
        }
        
        return comunidades;
    }

    /**
     * Calcula el grado de un vértice (número de aristas conectadas)
     * @param vertice Vértice
     * @return Grado del vértice, o -1 si el vértice no existe
     */
    public int grado(T vertice) {
        if (!contieneVertice(vertice)) {
            return -1;
        }
        
        return adyacencias.get(vertice).tamaño();
    }

    /**
     * Encuentra los vértices con mayor grado
     * @param cantidad Número máximo de vértices a retornar
     * @return Lista de vértices con mayor grado
     */
    public ListaEnlazada<T> verticesConMayorGrado(int cantidad) {
        if (cantidad <= 0 || estaVacio()) {
            return new ListaEnlazada<>();
        }
        
        // Usar una cola de prioridad para mantener los vértices con mayor grado
        ColaPrioridad<T> colaPrioridad = new ColaPrioridad<>();
        
        for (T vertice : adyacencias.keySet()) {
            colaPrioridad.insertar(vertice, grado(vertice));
        }
        
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        int contador = 0;
        
        while (!colaPrioridad.estaVacia() && contador < cantidad) {
            resultado.agregar(colaPrioridad.extraer());
            contador++;
        }
        
        return resultado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo:\n");
        
        for (T vertice : adyacencias.keySet()) {
            sb.append(vertice).append(" -> ").append(adyacencias.get(vertice)).append("\n");
        }
        
        return sb.toString();
    }


}