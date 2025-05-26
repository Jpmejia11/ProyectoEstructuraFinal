package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.GrupoEstudio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrearGrupoController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private TextField txtTemas;
    
    @FXML
    private Label lblError;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnGuardar;
    
    private RedSocialController redSocialController;
    
    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();
    }
    
    @FXML
    public void guardarGrupo(ActionEvent actionEvent) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String temasTexto = txtTemas.getText().trim();
        
        // Validar campos
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            lblError.setText("Por favor, complete todos los campos obligatorios");
            return;
        }
        
        // Obtener el usuario actual (debe ser un estudiante)
        if (!(redSocialController.getUsuarioActual() instanceof Estudiante)) {
            lblError.setText("Solo los estudiantes pueden crear grupos de estudio");
            return;
        }
        
        Estudiante creador = (Estudiante) redSocialController.getUsuarioActual();
        
        // Procesar temas
        List<String> temas = new ArrayList<>();
        if (!temasTexto.isEmpty()) {
            String[] temasArray = temasTexto.split(",");
            for (String tema : temasArray) {
                String temaTrimmed = tema.trim();
                if (!temaTrimmed.isEmpty()) {
                    temas.add(temaTrimmed);
                }
            }
        }
        
        // Si no hay temas, agregar al menos uno predeterminado
        if (temas.isEmpty()) {
            temas.add("General");
        }
        
        // Crear el grupo
        GrupoEstudio nuevoGrupo = redSocialController.crearGrupoEstudio(nombre, descripcion, creador, temas);
        
        if (nuevoGrupo != null) {
            // Mostrar mensaje de éxito
            mostrarAlerta("Éxito", "El grupo de estudio ha sido creado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            lblError.setText("Error al crear el grupo");
        }
    }
    
    @FXML
    public void cancelar(ActionEvent actionEvent) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}