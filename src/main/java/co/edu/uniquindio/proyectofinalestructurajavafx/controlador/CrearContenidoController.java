package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Contenido;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CrearContenidoController {
    
    @FXML
    private TextField txtTitulo;
    
    @FXML
    private ComboBox<String> cmbTipo;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private TextField txtEtiquetas;
    
    @FXML
    private Label lblError;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnGuardar;

    private File archivoSubido;

    private RedSocialController redSocialController;
    
    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();
        
        // Inicializar el combo de tipos de contenido
        List<String> tiposContenido = Arrays.asList("Artículo", "Video", "Ejercicio", "Cuestionario", "Otro");
        cmbTipo.setItems(FXCollections.observableArrayList(tiposContenido));
        cmbTipo.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void guardarContenido(ActionEvent actionEvent) {
        String titulo = txtTitulo.getText().trim();
        String tipo = cmbTipo.getValue();
        String descripcion = txtDescripcion.getText().trim();
        String etiquetasTexto = txtEtiquetas.getText().trim();
        
        // Validar campos
        if (titulo.isEmpty() || descripcion.isEmpty()) {
            lblError.setText("Por favor, complete todos los campos obligatorios");
            return;
        }
        
        // Obtener el usuario actual (debe ser un estudiante)
        if (!(redSocialController.getUsuarioActual() instanceof Estudiante)) {
            lblError.setText("Solo los estudiantes pueden crear contenido");
            return;
        }

        if(this.archivoSubido == null){
            lblError.setText("El archivo no fue cargado");
            return;
        }
        
        Estudiante autor = (Estudiante) redSocialController.getUsuarioActual();
        
        // Procesar etiquetas
        String[] etiquetasArray = etiquetasTexto.split(",");
        List<String> etiquetas = Arrays.asList(etiquetasArray);
        
        // Crear el contenido
        Contenido nuevoContenido = redSocialController.crearContenido(titulo, descripcion, tipo, autor, etiquetas, archivoSubido);
        
        if (nuevoContenido != null) {
            cerrarVentana();
        } else {
            lblError.setText("Error al crear el contenido");
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


    public void subirArchivo(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Subir archivo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.docx", "*.txt", "*.zip", "*.rar")
        );
        File archivo = fileChooser.showOpenDialog(null);
        if(archivo!=null){
            this.archivoSubido = archivo;
        }

    }
}
