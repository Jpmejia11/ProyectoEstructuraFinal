package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContraseña;
    
    @FXML
    private Label lblError;
    
    @FXML
    private Button btnIniciarSesion;
    
    @FXML
    private Button btnRegistrarse;
    
    @FXML
    private Button btnCargarDatos;
    
    private RedSocialController redSocialController;

    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();
    }
    
    @FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String correo = txtCorreo.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        
        if (correo.isEmpty() || contraseña.isEmpty()) {
            lblError.setText("Por favor, complete todos los campos");
            return;
        }
        
        boolean autenticado = redSocialController.autenticarUsuario(correo, contraseña);
        
        if (autenticado) {
            Usuario usuario = redSocialController.getUsuarioActual();
            
            // Verificar si es moderador y tiene los permisos necesarios
            if (usuario instanceof co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador) {
                co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador moderador = 
                    (co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador) usuario;
                
                // Verificar que tenga el permiso básico de acceso
                if (!moderador.tienePermiso("acceso_panel_moderador")) {
                    lblError.setText("El usuario no tiene los permisos necesarios para acceder como moderador");
                    redSocialController.cerrarSesion();
                    return;
                }
            }
            
            try {
                abrirPantallaPrincipal(usuario);
            } catch (Exception e) {
                lblError.setText("Error al abrir la pantalla principal: " + e.getMessage());
                e.printStackTrace();
                redSocialController.cerrarSesion();
            }
        } else {
            lblError.setText("Correo o contraseña incorrectos");
        }
    }

    @FXML
    public void mostrarRegistro(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/registro-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Registro de Usuario");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Cerrar la ventana actual
            Stage currentStage = (Stage) btnRegistrarse.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            lblError.setText("Error al cargar la pantalla de registro");
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarDatosPrueba(ActionEvent actionEvent) {
        // Los datos de prueba ya se cargan en el controlador
        lblError.setText("Datos de prueba cargados. Use admin@redsocial.edu / admin123 para iniciar sesión como moderador");
    }
    
    private void abrirPantallaPrincipal(Usuario usuario) {
        try {
            String vistaFXML;
            String titulo;

            // Determinar qué vista cargar según el tipo de usuario
            if (usuario instanceof co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador) {
                vistaFXML = "/co/edu/uniquindio/proyectofinalestructurajavafx/vista/moderador-view.fxml";
                titulo = "Panel de Moderador - " + usuario.getNombre();
            } else {
                vistaFXML = "/co/edu/uniquindio/proyectofinalestructurajavafx/vista/estudiante-view.fxml";
                titulo = "Red Social Educativa - " + usuario.getNombre();
            }

            // Verificar que el archivo FXML existe
            if (getClass().getResource(vistaFXML) == null) {
                throw new IOException("No se encontró el archivo de vista: " + vistaFXML);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(vistaFXML));
            Parent root;
            try {
                root = loader.load();
            } catch (Exception e) {
                throw new IOException("Error al cargar la vista: " + e.getMessage(), e);
            }

            // Configurar el controlador según el tipo de usuario
            if (usuario instanceof co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador) {
                ModeradorController controller = loader.getController();
                if (controller == null) {
                    throw new IOException("No se pudo obtener el controlador de moderador");
                }

                co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador moderador =
                    (co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador) usuario;

                // Verificar permisos básicos del moderador
                if (!moderador.tienePermiso("acceso_panel_moderador")) {
                    throw new IOException("El moderador no tiene los permisos necesarios para acceder al panel");
                }

                // Inicializar el controlador del moderador
                try {
                    controller.setModerador(moderador);
                } catch (Exception e) {
                    throw new IOException("Error al inicializar el controlador de moderador: " + e.getMessage(), e);
                }
            } else {
                EstudianteController controller = loader.getController();
                if (controller == null) {
                    throw new IOException("No se pudo obtener el controlador de estudiante");
                }

                try {
                    controller.setEstudiante(usuario);
                } catch (Exception e) {
                    throw new IOException("Error al inicializar el controlador de estudiante: " + e.getMessage(), e);
                }
            }

            // Crear y configurar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setMaximized(true);

            // Configurar el manejador de cierre para la ventana
            stage.setOnCloseRequest(event -> {
                try {
                    redSocialController.cerrarSesion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Mostrar la nueva ventana
            stage.show();

            // Cerrar la ventana de login
            Stage loginStage = (Stage) btnIniciarSesion.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            lblError.setText("Error al cargar la pantalla principal: " + e.getMessage());
            e.printStackTrace();
            // Asegurar que se cierre la sesión en caso de error
            redSocialController.cerrarSesion();
        } catch (Exception e) {
            lblError.setText("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            redSocialController.cerrarSesion();
        }
    }
}