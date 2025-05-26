package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Contenido;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Usuario;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Valoracion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class DetalleContenidoController {
    
    @FXML
    private Label lblTitulo;
    
    @FXML
    private Label lblAutor;
    
    @FXML
    private Label lblTipo;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private Slider sliderValoracion;
    
    @FXML
    private Button btnValorar;
    
    @FXML
    private TextArea txtComentarios;
    
    @FXML
    private TextArea txtNuevoComentario;
    
    @FXML
    private Button btnComentar;
    
    @FXML
    private Button btnCerrar;
    
    private Contenido contenido;
    private RedSocialController redSocialController;
    
    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();
    }
    
    public void setContenido(Contenido contenidoSeleccionado) {
        this.contenido = contenidoSeleccionado;
        
        // Cargar los datos del contenido en la interfaz
        lblTitulo.setText(contenido.getTitulo());
        lblAutor.setText(contenido.getAutor().getNombre());
        lblTipo.setText(contenido.getTipo());
        txtDescripcion.setText(contenido.getDescripcion());
        
        // Cargar comentarios
        cargarComentarios();
    }
    
    private void cargarComentarios() {
        StringBuilder sb = new StringBuilder();
        
        for (String comentario : contenido.getComentarios()) {
            sb.append(comentario).append("\n\n");
        }
        
        txtComentarios.setText(sb.toString());
    }
    
    @FXML
    public void valorarContenido(ActionEvent actionEvent) {
        int valoracion = (int) sliderValoracion.getValue();
        Usuario usuario = redSocialController.getUsuarioActual();
        
        if (usuario instanceof Estudiante) {
            System.out.println(usuario);
            Estudiante estudiante = (Estudiante) usuario;
            Valoracion nuevaValoracion = new Valoracion(estudiante, valoracion);
            contenido.agregarValoracion(nuevaValoracion);
        }
    }
    
    @FXML
    public void agregarComentario(ActionEvent actionEvent) {
        String comentario = txtNuevoComentario.getText().trim();

        System.out.println(comentario);
        
        if (!comentario.isEmpty()) {
            Usuario usuario = redSocialController.getUsuarioActual();
            String nombreUsuario = usuario.getNombre();
            
            String comentarioCompleto = nombreUsuario + ": " + comentario;
            System.out.println(comentarioCompleto);
            contenido.agregarComentario(comentarioCompleto);
            
            // Limpiar el campo de nuevo comentario
            txtNuevoComentario.clear();
            
            // Recargar los comentarios
            cargarComentarios();
        }
    }
    
    @FXML
    public void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    public void verArchivo(ActionEvent actionEvent) {
        try {
            if (this.contenido != null) {
                File archivo = contenido.getArchivo();
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (archivo.exists()) {
                        desktop.open(archivo);
                    } else {
                        System.out.println("El archivo no existe.");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}