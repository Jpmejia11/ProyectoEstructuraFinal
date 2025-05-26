package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContraseña;
    
    @FXML
    private PasswordField txtConfirmarContraseña;
    
    @FXML
    private TextField txtCarrera;
    
    @FXML
    private ComboBox<Integer> cmbSemestre;
    
    @FXML
    private Label lblError;
    
    @FXML
    private Button btnRegistrar;
    
    @FXML
    private Button btnVolver;
    
    private RedSocialController redSocialController;
    
    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();
        
        // Inicializar el combo de semestres
        for (int i = 1; i <= 10; i++) {
            cmbSemestre.getItems().add(i);
        }
        cmbSemestre.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void registrarUsuario(ActionEvent actionEvent) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contraseña = txtContraseña.getText();
        String confirmarContraseña = txtConfirmarContraseña.getText();
        String carrera = txtCarrera.getText().trim();
        Integer semestre = cmbSemestre.getValue();
        
        // Validar campos
        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || carrera.isEmpty() || semestre == null) {
            lblError.setText("Por favor, complete todos los campos");
            return;
        }
        
        if (!contraseña.equals(confirmarContraseña)) {
            lblError.setText("Las contraseñas no coinciden");
            return;
        }
        
        // Validar formato de correo (simple)
        if (!correo.contains("@") || !correo.contains(".")) {
            lblError.setText("El correo electrónico no es válido");
            return;
        }
        
        // Intentar registrar al estudiante
        Estudiante estudiante = redSocialController.registrarEstudiante(nombre, correo, contraseña, carrera, semestre);
        
        if (estudiante != null) {
            mostrarAlerta("Registro exitoso", "El usuario ha sido registrado correctamente", Alert.AlertType.INFORMATION);
            volverALogin();
        } else {
            lblError.setText("El correo electrónico ya está en uso");
        }
    }
    
    @FXML
    public void volverALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/login-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Cerrar la ventana actual
            Stage currentStage = (Stage) btnVolver.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            lblError.setText("Error al cargar la pantalla de login");
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}