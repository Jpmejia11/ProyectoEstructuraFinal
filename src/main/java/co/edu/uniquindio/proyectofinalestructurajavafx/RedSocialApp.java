package co.edu.uniquindio.proyectofinalestructurajavafx;

import co.edu.uniquindio.proyectofinalestructurajavafx.controlador.RedSocialController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación de red social educativa
 */
public class RedSocialApp extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar el controlador
        RedSocialController.getInstancia();
        
        // Cargar la vista de inicio de sesión
        FXMLLoader fxmlLoader = new FXMLLoader(RedSocialApp.class.getResource("vista/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        
        // Configurar la ventana principal
        stage.setTitle("Red Social Educativa");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        
        // Mostrar la ventana
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
