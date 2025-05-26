package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.SolicitudAyuda;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrearSolicitudController {
    
    @FXML
    private TextField txtTitulo;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private ComboBox<Integer> cmbPrioridad;
    
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
        
        // Inicializar el combo de prioridades (1-5)
        Integer[] prioridades = {1, 2, 3, 4, 5};
        cmbPrioridad.setItems(FXCollections.observableArrayList(prioridades));
        cmbPrioridad.getSelectionModel().select(2); // Prioridad media por defecto
    }
    
    @FXML
    public void guardarSolicitud(ActionEvent actionEvent) {
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        Integer prioridad = cmbPrioridad.getValue();
        
        // Validar campos
        if (titulo.isEmpty() || descripcion.isEmpty() || prioridad == null) {
            lblError.setText("Por favor, complete todos los campos");
            return;
        }
        
        // Obtener el usuario actual (debe ser un estudiante)
        if (!(redSocialController.getUsuarioActual() instanceof Estudiante)) {
            lblError.setText("Solo los estudiantes pueden crear solicitudes de ayuda");
            return;
        }
        
        Estudiante estudiante = (Estudiante) redSocialController.getUsuarioActual();
        
        // Crear la solicitud
        SolicitudAyuda nuevaSolicitud = redSocialController.crearSolicitudAyuda(titulo, descripcion, prioridad, estudiante);
        
        if (nuevaSolicitud != null) {
            cerrarVentana();
        } else {
            lblError.setText("Error al crear la solicitud");
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
}