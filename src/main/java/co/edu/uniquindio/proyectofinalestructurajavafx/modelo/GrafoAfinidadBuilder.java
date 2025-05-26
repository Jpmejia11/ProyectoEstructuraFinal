package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.Grafo;
import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

public class GrafoAfinidadBuilder {

    /**
     * Construye un grafo de afinidad conectando estudiantes que compartan carrera o semestre
     * @param estudiantes Lista de estudiantes
     * @return Grafo con los estudiantes como nodos y conexiones según afinidad
     */
    public static Grafo<Estudiante> construirGrafoAfinidad(ListaEnlazada<Estudiante> estudiantes) {
        Grafo<Estudiante> grafo = new Grafo<>();

        // Agregar todos los estudiantes como nodos
        for (Estudiante e : estudiantes) {
            grafo.agregarVertice(e);
        }

        // Comparar pares para conectar según afinidad
        for (int i = 0; i < estudiantes.tamaño(); i++) {
            Estudiante e1 = estudiantes.obtener(i);
            for (int j = i + 1; j < estudiantes.tamaño(); j++) {
                Estudiante e2 = estudiantes.obtener(j);

                boolean mismaCarrera = e1.getCarrera().equalsIgnoreCase(e2.getCarrera());
                boolean mismoSemestre = e1.getSemestre() == e2.getSemestre();

                if (mismaCarrera || mismoSemestre) {
                    grafo.agregarArista(e1, e2);
                }
            }
        }

        return grafo;
    }
}
