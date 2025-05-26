package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.Grafo;
import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModeradorController {

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblRol;

    @FXML
    private TabPane tabPaneModerador;

    // Tab de Usuarios
    @FXML
    private TableView<Usuario> tblUsuarios;

    @FXML
    private TableColumn<Usuario, String> colIdUsuario;

    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;

    @FXML
    private TableColumn<Usuario, String> colCorreoUsuario;

    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;

    @FXML
    private TextField txtBuscarUsuario;

    // Tab de Contenidos
    @FXML
    private TableView<Contenido> tblContenidos;

    @FXML
    private TableColumn<Contenido, String> colTituloContenido;

    @FXML
    private TableColumn<Contenido, String> colAutorContenido;

    @FXML
    private TableColumn<Contenido, String> colTipoContenido;

    @FXML
    private TableColumn<Contenido, Double> colCalificacionContenido;

    @FXML
    private TextField txtBuscarContenido;

    // Tab de Grupos de Estudio
    @FXML
    private TableView<GrupoEstudio> tblGrupos;

    @FXML
    private TableColumn<GrupoEstudio, String> colNombreGrupo;

    @FXML
    private TableColumn<GrupoEstudio, String> colDescripcionGrupo;

    @FXML
    private TableColumn<GrupoEstudio, Integer> colMiembrosGrupo;

    @FXML
    private TableColumn<GrupoEstudio, Boolean> colActivoGrupo;

    // Tab de Solicitudes de Ayuda
    @FXML
    private TableView<SolicitudAyuda> tblSolicitudes;

    @FXML
    private TableColumn<SolicitudAyuda, String> colTituloSolicitud;

    @FXML
    private TableColumn<SolicitudAyuda, String> colEstudianteSolicitud;

    @FXML
    private TableColumn<SolicitudAyuda, Integer> colPrioridadSolicitud;

    @FXML
    private TableColumn<SolicitudAyuda, Boolean> colResueltaSolicitud;

    // Tab de Estadísticas
    @FXML
    private Label lblTotalUsuarios;

    @FXML
    private Label lblTotalEstudiantes;

    @FXML
    private Label lblTotalModeradores;

    @FXML
    private Label lblTotalContenidos;

    @FXML
    private Label lblTotalGrupos;

    @FXML
    private Label lblTotalSolicitudes;

    @FXML
    private Label lblSolicitudesPendientes;

    @FXML
    private Label lblSolicitudesResueltas;

    private RedSocialController redSocialController;

    private Moderador moderadorActual;
    private List<Estudiante> estudiantesRegistrados = new ArrayList<>();

    @FXML
    public void initialize() {
        this.redSocialController = RedSocialController.getInstancia();

        // Verificar que el usuario actual sea un moderador
        if (redSocialController.getUsuarioActual() instanceof Moderador) {
            this.moderadorActual = (Moderador) redSocialController.getUsuarioActual();
            configurarInformacionModerador();
            configurarTablas();
            cargarDatos();
        } else {
            // Si no es moderador, mostrar error y redirigir al login
            mostrarAlerta("Error de Acceso", "No tiene permisos para acceder al panel de moderador", Alert.AlertType.ERROR);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/login-view.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Iniciar Sesión");
                stage.setScene(new Scene(root));
                stage.show();

                // Cerrar la ventana actual
                cerrarVentana();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void configurarInformacionModerador() {
        if (moderadorActual != null) {
            lblNombreUsuario.setText(moderadorActual.getNombre());
            lblRol.setText("Rol: " + moderadorActual.getRol());

            // Verificar y configurar permisos
            if (!moderadorActual.tienePermiso("acceso_panel_moderador")) {
                tabPaneModerador.setDisable(true);
                mostrarAlerta("Error de Acceso", "No tiene permisos para acceder al panel de moderador", Alert.AlertType.ERROR);
                return;
            }

            // Configurar acceso a pestañas según permisos
            if (!moderadorActual.tienePermiso("gestionar_usuarios")) {
                tabPaneModerador.getTabs().removeIf(tab -> tab.getText().equals("Usuarios"));
            }
            if (!moderadorActual.tienePermiso("gestionar_contenidos")) {
                tabPaneModerador.getTabs().removeIf(tab -> tab.getText().equals("Contenidos"));
            }
            if (!moderadorActual.tienePermiso("gestionar_grupos")) {
                tabPaneModerador.getTabs().removeIf(tab -> tab.getText().equals("Grupos de Estudio"));
            }
            if (!moderadorActual.tienePermiso("gestionar_solicitudes")) {
                tabPaneModerador.getTabs().removeIf(tab -> tab.getText().equals("Solicitudes de Ayuda"));
            }
        } else {
            lblNombreUsuario.setText("Usuario no autorizado");
            lblRol.setText("Sin acceso");
            tabPaneModerador.setDisable(true);
        }
    }

    private void configurarTablas() {

        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreoUsuario.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTipoUsuario.setCellValueFactory( cell -> {
            if(cell.getValue() instanceof Estudiante){
                return new SimpleStringProperty("Estudiante");
            }else if(cell.getValue() instanceof Moderador){
                return new SimpleStringProperty("Moderador");
            }else{
                return new SimpleStringProperty("Usuario");
            }
        }  );

        // Configurar tabla de contenidos
        colTituloContenido.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutorContenido.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTipoContenido.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCalificacionContenido.setCellValueFactory( cell -> {
            double valoraciones = cell.getValue().calcularValoracionPromedio();
            return new SimpleDoubleProperty(valoraciones).asObject();
        });

        // Configurar tabla de grupos
        colNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionGrupo.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colMiembrosGrupo.setCellValueFactory(cellData -> {
            GrupoEstudio grupo = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(grupo.getMiembros().tamaño()).asObject();
        });
        colActivoGrupo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        // Configurar tabla de solicitudes
        colTituloSolicitud.setCellValueFactory(new PropertyValueFactory<>("tema"));
        colEstudianteSolicitud.setCellValueFactory(cellData -> {
            SolicitudAyuda solicitud = cellData.getValue();
            return new SimpleStringProperty(solicitud.getEstudiante().getNombre());
        });
        colPrioridadSolicitud.setCellValueFactory(new PropertyValueFactory<>("nivelUrgencia"));
        colResueltaSolicitud.setCellValueFactory(new PropertyValueFactory<>("resuelta"));
    }

    private void cargarDatos() {
        // Cargar usuarios
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        for (Usuario usuario : redSocialController.getUsuarios()) {
            usuarios.add(usuario);
        }
        tblUsuarios.setItems(usuarios);

        // Cargar contenidos
        ObservableList<Contenido> contenidos = FXCollections.observableArrayList();
        for (Contenido contenido : redSocialController.getContenidos()) {
            contenidos.add(contenido);
        }
        tblContenidos.setItems(contenidos);

        // Cargar grupos de estudio
        ObservableList<GrupoEstudio> grupos = FXCollections.observableArrayList();
        for (GrupoEstudio grupo : redSocialController.getGruposEstudio()) {
            grupos.add(grupo);
        }
        tblGrupos.setItems(grupos);

        // Cargar solicitudes de ayuda
        ObservableList<SolicitudAyuda> solicitudes = FXCollections.observableArrayList();
        for (SolicitudAyuda solicitud : redSocialController.getSolicitudesAyuda()) {
            solicitudes.add(solicitud);
        }
        tblSolicitudes.setItems(solicitudes);

        // Cargar estadísticas
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        lblTotalUsuarios.setText(String.valueOf(redSocialController.getUsuarios().tamaño()));
        lblTotalEstudiantes.setText(String.valueOf(redSocialController.getEstudiantes().tamaño()));
        lblTotalModeradores.setText(String.valueOf(redSocialController.getModeradores().tamaño()));
        lblTotalContenidos.setText(String.valueOf(redSocialController.getContenidos().tamaño()));
        lblTotalGrupos.setText(String.valueOf(redSocialController.getGruposEstudio().tamaño()));

        int totalSolicitudes = redSocialController.getSolicitudesAyuda().tamaño();
        lblTotalSolicitudes.setText(String.valueOf(totalSolicitudes));

        int solicitudesPendientes = 0;
        for (SolicitudAyuda solicitud : redSocialController.getSolicitudesAyuda()) {
            if (!solicitud.isResuelta()) {
                solicitudesPendientes++;
            }
        }
        lblSolicitudesPendientes.setText(String.valueOf(solicitudesPendientes));
        lblSolicitudesResueltas.setText(String.valueOf(totalSolicitudes - solicitudesPendientes));
    }

    @FXML
    public void cerrarSesion() {
        redSocialController.cerrarSesion();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            cerrarVentana();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la pantalla de login", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblNombreUsuario.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void buscarUsuario(ActionEvent actionEvent) {
        String termino = txtBuscarUsuario.getText().trim();

        if (!termino.isEmpty()) {
            ObservableList<Usuario> usuariosFiltrados = FXCollections.observableArrayList();

            for (Usuario usuario : redSocialController.getUsuarios()) {
                if (usuario.getNombre().toLowerCase().contains(termino.toLowerCase()) ||
                    usuario.getCorreo().toLowerCase().contains(termino.toLowerCase()) ||
                    usuario.getId().toLowerCase().contains(termino.toLowerCase())) {
                    usuariosFiltrados.add(usuario);
                }
            }

            tblUsuarios.setItems(usuariosFiltrados);
        } else {
            // Si no hay término de búsqueda, mostrar todos los usuarios
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
            for (Usuario usuario : redSocialController.getUsuarios()) {
                usuarios.add(usuario);
            }
            tblUsuarios.setItems(usuarios);
        }
    }

    public void buscarContenido(ActionEvent actionEvent) {
        String termino = txtBuscarContenido.getText().trim();

        if (!termino.isEmpty()) {
            ListaEnlazada<Contenido> contenidosEncontrados = redSocialController.buscarContenidosPorTema(termino);

            ObservableList<Contenido> contenidos = FXCollections.observableArrayList();
            for (Contenido contenido : contenidosEncontrados) {
                contenidos.add(contenido);
            }
            tblContenidos.setItems(contenidos);
        } else {
            // Si no hay término de búsqueda, mostrar todos los contenidos
            ObservableList<Contenido> contenidos = FXCollections.observableArrayList();
            for (Contenido contenido : redSocialController.getContenidos()) {
                contenidos.add(contenido);
            }
            tblContenidos.setItems(contenidos);
        }
    }

    @FXML
    public void activarDesactivarGrupo() {
        GrupoEstudio grupoSeleccionado = tblGrupos.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null) {
            // Cambiar el estado del grupo
            boolean nuevoEstado = !grupoSeleccionado.isActivo();
            grupoSeleccionado.setActivo(nuevoEstado);

            String mensaje = nuevoEstado ?
                "El grupo " + grupoSeleccionado.getNombre() + " ha sido activado." :
                "El grupo " + grupoSeleccionado.getNombre() + " ha sido desactivado.";

            mostrarAlerta("Grupo actualizado", mensaje, Alert.AlertType.INFORMATION);

            // Recargar los datos
            cargarDatos();
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione un grupo para activar/desactivar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void verMiembrosGrupo() {
        GrupoEstudio grupoSeleccionado = tblGrupos.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null) {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Miembros del grupo ").append(grupoSeleccionado.getNombre()).append(":\n\n");

            if (grupoSeleccionado.getMiembros().estaVacia()) {
                mensaje.append("El grupo no tiene miembros.");
            } else {
                int contador = 1;
                for (Estudiante miembro : grupoSeleccionado.getMiembros()) {
                    mensaje.append(contador).append(". ")
                           .append(miembro.getNombre())
                           .append(" (").append(miembro.getCarrera()).append(", Semestre ").append(miembro.getSemestre()).append(")")
                           .append("\n");
                    contador++;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Miembros del Grupo");
            alert.setHeaderText(null);
            alert.setContentText(mensaje.toString());

            // Hacer que el diálogo sea redimensionable
            alert.getDialogPane().setMinHeight(300);
            alert.getDialogPane().setMinWidth(400);

            alert.showAndWait();
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione un grupo para ver sus miembros.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void eliminarMiembroGrupo() {
        GrupoEstudio grupoSeleccionado = tblGrupos.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null) {
            // Crear una lista de opciones con los miembros del grupo
            ListaEnlazada<Estudiante> miembros = grupoSeleccionado.getMiembros();

            if (miembros.estaVacia()) {
                mostrarAlerta("Grupo vacío", "El grupo seleccionado no tiene miembros.", Alert.AlertType.INFORMATION);
                return;
            }

            // Crear un diálogo de elección
            ChoiceDialog<Estudiante> dialog = new ChoiceDialog<>();
            dialog.setTitle("Eliminar Miembro");
            dialog.setHeaderText("Seleccione el miembro a eliminar del grupo " + grupoSeleccionado.getNombre());
            dialog.setContentText("Miembro:");

            // Agregar los miembros como opciones
            ObservableList<Estudiante> opciones = FXCollections.observableArrayList();
            for (Estudiante miembro : miembros) {
                opciones.add(miembro);
            }
            dialog.getItems().addAll(opciones);

            // Mostrar el diálogo y procesar el resultado
            Optional<Estudiante> resultado = dialog.showAndWait();
            if (resultado.isPresent()) {
                Estudiante miembroSeleccionado = resultado.get();

                // Eliminar al miembro del grupo
                grupoSeleccionado.eliminarMiembro(miembroSeleccionado);
                miembroSeleccionado.eliminarGrupoEstudio(grupoSeleccionado);

                mostrarAlerta("Miembro eliminado",
                              miembroSeleccionado.getNombre() + " ha sido eliminado del grupo " + grupoSeleccionado.getNombre(),
                              Alert.AlertType.INFORMATION);

                // Recargar los datos
                cargarDatos();
            }
        } else {
            mostrarAlerta("Selección requerida", "Por favor, seleccione un grupo para eliminar miembros.", Alert.AlertType.WARNING);
        }
    }

    public void setModerador(Moderador usuario) {
        this.moderadorActual = usuario;
        this.redSocialController = RedSocialController.getInstancia();
        configurarInformacionModerador();
        configurarTablas();
        cargarDatos();
    }
    @FXML
    public void mostrarGrafoAfinidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/VentanaGrafoAfinidad.fxml"));
            Parent root = loader.load();

            GrafoAfinidadController controller = loader.getController();

            Grafo<Estudiante> grafo = new Grafo<>();

            RedSocialController redSocialController = RedSocialController.getInstancia();
            ListaEnlazada<Estudiante> estudiantes = redSocialController.obtenerEstudiantesRegistrados();

            if (estudiantes == null || estudiantes.tamaño() == 0) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText(null);
                alerta.setContentText("No hay estudiantes registrados para construir el grafo.");
                alerta.showAndWait();
                return;
            }

            for (Estudiante e : estudiantes) {
                grafo.agregarVertice(e);
            }

            for (int i = 0; i < estudiantes.tamaño(); i++) {
                Estudiante e1 = estudiantes.obtener(i);
                for (int j = i + 1; j < estudiantes.tamaño(); j++) {
                    Estudiante e2 = estudiantes.obtener(j);

                    if (e1.getCarrera().equals(e2.getCarrera()) || e1.getSemestre() == e2.getSemestre()) {
                        grafo.agregarArista(e1, e2);
                    }
                }
            }

            controller.setGrafo(grafo);
            controller.setEstudiantes(estudiantes);  // para que puedas usar info de colores

            Stage stage = new Stage();
            stage.setTitle("Grafo de Afinidad");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    public void marcarSolicitudComoResuelta() {
        SolicitudAyuda solicitudSeleccionada = tblSolicitudes.getSelectionModel().getSelectedItem();

        if (solicitudSeleccionada != null) {
            if (!solicitudSeleccionada.isResuelta()) {
                // Confirmar la acción
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar acción");
                confirmacion.setHeaderText(null);
                confirmacion.setContentText("¿Está seguro que desea marcar esta solicitud como resuelta?");

                Optional<ButtonType> resultado = confirmacion.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    // Marcar la solicitud como resuelta
                    solicitudSeleccionada.setResuelta(true);
                    
                    // Mostrar mensaje de éxito
                    mostrarAlerta("Solicitud actualizada", 
                                "La solicitud ha sido marcada como resuelta.", 
                                Alert.AlertType.INFORMATION);
                    
                    // Actualizar la tabla y las estadísticas
                    cargarDatos();
                }
            } else {
                mostrarAlerta("Solicitud ya resuelta", 
                            "Esta solicitud ya se encuentra marcada como resuelta.", 
                            Alert.AlertType.WARNING);
            }
        } else {
            mostrarAlerta("Selección requerida", 
                        "Por favor, seleccione una solicitud para marcarla como resuelta.", 
                        Alert.AlertType.WARNING);
        }
    }
}

