package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.Grafo;
import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.HashMap;
import java.util.Map;



    public class GrafoAfinidadController {

        private ListaEnlazada<Estudiante> estudiantes;

        public void setEstudiantes(ListaEnlazada<Estudiante> estudiantes) {
            this.estudiantes = estudiantes;
        }


        @FXML
        private Pane pane;

        private Grafo<Estudiante> grafo;

        // Mapa para guardar las posiciones de los nodos y poder dibujar las aristas
        private Map<Estudiante, Circle> nodosCirculos = new HashMap<>();

        // Método para inicializar o recibir el grafo
        public void setGrafo(Grafo<Estudiante> grafo) {
            this.grafo = grafo;
            pintarGrafo();
        }

        private void pintarGrafo() {
            pane.getChildren().clear();
            nodosCirculos.clear();

            // Leyenda de colores
            Text leyenda = new Text(
                    "Colores de afinidad:\n" +
                            "Verde oscuro = misma carrera y mismo semestre (afinidad fuerte)\n" +
                            "Azul = misma carrera, diferente semestre\n" +
                            "Naranja = mismo semestre, diferente carrera\n"
            );
            leyenda.setX(10);
            leyenda.setY(20);
            leyenda.setFill(Color.BLACK);
            leyenda.setStyle("-fx-font-size: 14px;");
            pane.getChildren().add(leyenda);

            // Obtener todos los nodos
            ListaEnlazada<Estudiante> nodos = grafo.obtenerVertices();

            if (nodos == null || nodos.tamaño() == 0) {
                return;
            }

            // Posicionar nodos en círculo
            int radio = 150;
            int centroX = 300;
            int centroY = 300;
            int totalNodos = nodos.tamaño();
            int index = 0;

            for (Estudiante estudiante : nodos) {
                double angulo = 2 * Math.PI * index / totalNodos;
                double x = centroX + radio * Math.cos(angulo);
                double y = centroY + radio * Math.sin(angulo);

                Circle nodoCirculo = new Circle(x, y, 15, Color.CORNFLOWERBLUE);
                pane.getChildren().add(nodoCirculo);

                nodosCirculos.put(estudiante, nodoCirculo);

                // Etiqueta con nombre del estudiante
                Text etiqueta = new Text(x - 20, y - 20, estudiante.getNombre());
                etiqueta.setFill(Color.BLACK);
                etiqueta.setStyle("-fx-font-size: 12px;");
                pane.getChildren().add(etiqueta);

                index++;
            }

            // Dibujar aristas con colores por afinidad
            for (Estudiante origen : nodos) {
                ListaEnlazada<Estudiante> adyacentes = grafo.obtenerAdyacentes(origen);
                Circle cOrigen = nodosCirculos.get(origen);

                if (cOrigen == null || adyacentes == null) continue;

                for (Estudiante destino : adyacentes) {
                    Circle cDestino = nodosCirculos.get(destino);

                    if (cDestino == null) continue;

                    // Evitar duplicados
                    if (origen.hashCode() < destino.hashCode()) {
                        Line arista = new Line(
                                cOrigen.getCenterX(), cOrigen.getCenterY(),
                                cDestino.getCenterX(), cDestino.getCenterY()
                        );

                        // Determinar color por afinidad
                        if (origen.getCarrera().equals(destino.getCarrera()) && origen.getSemestre() == destino.getSemestre()) {
                            arista.setStroke(Color.DARKGREEN); // Afinidad fuerte
                        } else if (origen.getCarrera().equals(destino.getCarrera())) {
                            arista.setStroke(Color.BLUE);
                        } else if (origen.getSemestre() == destino.getSemestre()) {
                            arista.setStroke(Color.ORANGE);
                        }
                        arista.setStrokeWidth(2);
                        pane.getChildren().add(arista);
                    }
                }
            }
        }
    }


