package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;

public class NodoArista {
    private NodoGrafoafinidad.NodoGrafoAfinidad nodoVecino;
    private int peso;

    public NodoArista(NodoGrafoafinidad.NodoGrafoAfinidad nodoVecino, int peso) {
        this.nodoVecino = nodoVecino;
        this.peso = peso;
    }

    public NodoGrafoafinidad.NodoGrafoAfinidad getNodoVecino() {
        return nodoVecino;
    }

    public int getPeso() {
        return peso;
    }
}


