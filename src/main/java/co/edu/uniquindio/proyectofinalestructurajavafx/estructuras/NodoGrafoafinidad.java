package co.edu.uniquindio.proyectofinalestructurajavafx.estructuras;


import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;

public class NodoGrafoafinidad {

    public class NodoGrafoAfinidad {
        private Estudiante estudiante;
        private ListaEnlazada<NodoArista> aristas;

        public NodoGrafoAfinidad(Estudiante estudiante) {
            this.estudiante = estudiante;
            this.aristas = new ListaEnlazada<>();
        }

        public Estudiante getEstudiante() {
            return estudiante;
        }

        public ListaEnlazada<NodoArista> getAristas() {
            return aristas;
        }

        public void agregarArista(NodoGrafoAfinidad vecino, int peso) {
            if (!tieneArista(vecino)) {
                aristas.agregar(new NodoArista(vecino, peso));
            }
        }

        private boolean tieneArista(NodoGrafoAfinidad vecino) {
            for (int i = 0; i < aristas.size(); i++) {
                if (aristas.get(i).getNodoVecino().equals(vecino)) {
                    return true;
                }
            }
            return false;
        }
    }

}

