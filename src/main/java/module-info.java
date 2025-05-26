module co.edu.uniquindio.proyectofinalestructurajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens co.edu.uniquindio.proyectofinalestructurajavafx to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalestructurajavafx;
    
    opens co.edu.uniquindio.proyectofinalestructurajavafx.controlador to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalestructurajavafx.controlador;
    
    opens co.edu.uniquindio.proyectofinalestructurajavafx.modelo to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalestructurajavafx.modelo;
}