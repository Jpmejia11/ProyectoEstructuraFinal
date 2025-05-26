package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class EstudianteController {

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblCarrera;

    @FXML
    private Label lblSemestre;

    @FXML
    private TabPane tabPaneEstudiante;

    // Tab de Perfil
    @FXML
    private ListView<String> lstIntereses;

    @FXML
    private TextField txtNuevoInteres;

    // Tab de Contenidos
    @FXML
    private TableView<Contenido> tblContenidos;

    @FXML
    private TableColumn<Contenido, String> colTitulo;

    @FXML
    private TableColumn<Contenido, String> colDescripcion;

    @FXML
    private TableColumn<Contenido, String> colTipo;

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

    // Tab de Solicitudes de Ayuda
    @FXML
    private TableView<SolicitudAyuda> tblSolicitudes;

    @FXML
    private TableColumn<SolicitudAyuda, String> colTituloSolicitud;

    @FXML
    private TableColumn<SolicitudAyuda, String> colDescripcionSolicitud;

    @FXML
    private TableColumn<SolicitudAyuda, Integer> colPrioridadSolicitud;

    // Tab de Recomendaciones
    @FXML
    private TableView<Estudiante> tblEstudiantesSimilares;

    @FXML
    private TableColumn<Estudiante, String> colNombreEstudiante;

    @FXML
    private TableColumn<Estudiante, String> colCarreraEstudiante;

    @FXML
    private TableColumn<Estudiante, Integer> colSemestreEstudiante;

    @FXML
    private TableView<GrupoEstudio> tblGruposRecomendados;

    @FXML
    private TableColumn<GrupoEstudio, String> colNombreGrupoRecomendado;

    @FXML
    private TableColumn<GrupoEstudio, String> colDescripcionGrupoRecomendado;

    private RedSocialController redSocialController;
    private Estudiante estudianteActual;

    @FXML
    public void initialize() {
        redSocialController = RedSocialController.getInstancia();

        // Verificar que el usuario actual sea un estudiante
        if (redSocialController.getUsuarioActual() instanceof Estudiante) {
            estudianteActual = (Estudiante) redSocialController.getUsuarioActual();

            // Configurar la información del estudiante
            configurarInformacionEstudiante();

            // Configurar las tablas
            configurarTablas();

            // Cargar los datos iniciales
            cargarDatos();
        } else {
            mostrarAlerta("Error", "El usuario actual no es un estudiante", Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }

    private void configurarInformacionEstudiante() {
        lblNombreUsuario.setText(estudianteActual.getNombre());
        lblCarrera.setText(estudianteActual.getCarrera());
        lblSemestre.setText("Semestre " + estudianteActual.getSemestre());
    }

    private void configurarTablas() {
        // Configurar tabla de contenidos
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Configurar tabla de grupos
        colNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionGrupo.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colMiembrosGrupo.setCellValueFactory(cellData -> {
            GrupoEstudio grupo = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(grupo.getMiembros().tamaño()).asObject();
        });

        // Configurar tabla de solicitudes
        colTituloSolicitud.setCellValueFactory(new PropertyValueFactory<>("tema"));
        colDescripcionSolicitud.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrioridadSolicitud.setCellValueFactory(new PropertyValueFactory<>("nivelUrgencia"));

        // Configurar tabla de estudiantes similares
        colNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCarreraEstudiante.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colSemestreEstudiante.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        // Configurar tabla de grupos recomendados
        colNombreGrupoRecomendado.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcionGrupoRecomendado.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    private void cargarDatos() {
        // Cargar intereses
        ObservableList<String> intereses = FXCollections.observableArrayList();
        for (String interes : estudianteActual.getIntereses()) {
            intereses.add(interes);
        }
        lstIntereses.setItems(intereses);

        // Cargar contenidos
        ObservableList<Contenido> contenidos = FXCollections.observableArrayList();
        for (Contenido contenido : redSocialController.getContenidos()) {
            contenidos.add(contenido);
        }
        tblContenidos.setItems(contenidos);

        // Cargar grupos de estudio del estudiante
        ObservableList<GrupoEstudio> grupos = FXCollections.observableArrayList();
        for (GrupoEstudio grupo : estudianteActual.getGruposEstudio()) {
            grupos.add(grupo);
        }
        tblGrupos.setItems(grupos);

        // Cargar solicitudes de ayuda
        ObservableList<SolicitudAyuda> solicitudes = FXCollections.observableArrayList();
        for (SolicitudAyuda solicitud : redSocialController.getSolicitudesAyuda()) {
            solicitudes.add(solicitud);
        }
        tblSolicitudes.setItems(solicitudes);

        // Cargar recomendaciones de estudiantes similares
        ListaEnlazada<Estudiante> estudiantesSimilares = redSocialController.encontrarEstudiantesConInteresesSimilares(estudianteActual, 5);
        ObservableList<Estudiante> estudiantesSimilaresObs = FXCollections.observableArrayList();
        for (Estudiante estudiante : estudiantesSimilares) {
            estudiantesSimilaresObs.add(estudiante);
        }
        tblEstudiantesSimilares.setItems(estudiantesSimilaresObs);

        // Cargar recomendaciones de grupos
        ListaEnlazada<GrupoEstudio> gruposRecomendados = redSocialController.sugerirGruposEstudio(estudianteActual, 5);
        ObservableList<GrupoEstudio> gruposRecomendadosObs = FXCollections.observableArrayList();
        
        // Si no hay grupos recomendados, mostrar todos los grupos disponibles a los que no pertenece el estudiante
        if (gruposRecomendados.estaVacia()) {
            for (GrupoEstudio grupo : redSocialController.getGruposEstudio()) {
                if (grupo.isActivo() && !grupo.esMiembro(estudianteActual)) {
                    gruposRecomendadosObs.add(grupo);
                }
            }
        } else {
            for (GrupoEstudio grupo : gruposRecomendados) {
                gruposRecomendadosObs.add(grupo);
            }
        }
        
        tblGruposRecomendados.setItems(gruposRecomendadosObs);
    }

    @FXML
    public void agregarInteres() {
        String nuevoInteres = txtNuevoInteres.getText().trim();

        if (!nuevoInteres.isEmpty()) {
            estudianteActual.agregarInteres(nuevoInteres);

            // Actualizar la lista de intereses
            ObservableList<String> intereses = FXCollections.observableArrayList();
            for (String interes : estudianteActual.getIntereses()) {
                intereses.add(interes);
            }
            lstIntereses.setItems(intereses);

            txtNuevoInteres.clear();
        }
    }

    @FXML
    public void eliminarInteres() {
        String interesSeleccionado = lstIntereses.getSelectionModel().getSelectedItem();

        if (interesSeleccionado != null) {
            estudianteActual.eliminarInteres(interesSeleccionado);

            // Actualizar la lista de intereses
            ObservableList<String> intereses = FXCollections.observableArrayList();
            for (String interes : estudianteActual.getIntereses()) {
                intereses.add(interes);
            }
            lstIntereses.setItems(intereses);
        }
    }

    @FXML
    public void buscarContenido() {
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
    public void crearContenido() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/crear-contenido-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Contenido");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recargar los contenidos
            cargarDatos();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de creación de contenido", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void verDetallesContenido() {
        Contenido contenidoSeleccionado = tblContenidos.getSelectionModel().getSelectedItem();

        if (contenidoSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/detalle-contenido-view.fxml"));
                Parent root = loader.load();

                DetalleContenidoController controller = loader.getController();
                controller.setContenido(contenidoSeleccionado);

                Stage stage = new Stage();
                stage.setTitle("Detalles del Contenido");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Recargar los contenidos
                cargarDatos();

            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo abrir la ventana de detalles del contenido", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Información", "Seleccione un contenido para ver sus detalles", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void crearSolicitudAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/crear-solicitud-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Solicitud de Ayuda");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recargar las solicitudes
            cargarDatos();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de creación de solicitud", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void unirseAGrupo() {
        GrupoEstudio grupoSeleccionado = tblGruposRecomendados.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado != null) {
            // Verificar si el estudiante ya es miembro del grupo
            if (grupoSeleccionado.esMiembro(estudianteActual)) {
                // Si ya es miembro, preguntar si desea salir del grupo
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Salir del grupo");
                confirmacion.setHeaderText(null);
                confirmacion.setContentText("Ya eres miembro del grupo " + grupoSeleccionado.getNombre() + ". ¿Deseas salir del grupo?");
                
                Optional<ButtonType> resultado = confirmacion.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    // Eliminar al estudiante del grupo
                    grupoSeleccionado.eliminarMiembro(estudianteActual);
                    estudianteActual.eliminarGrupoEstudio(grupoSeleccionado);
                    mostrarAlerta("Éxito", "Has salido del grupo " + grupoSeleccionado.getNombre(), Alert.AlertType.INFORMATION);
                }
            } else {
                // Si no es miembro, unirse al grupo
                if (grupoSeleccionado.isActivo()) {
                    grupoSeleccionado.agregarMiembro(estudianteActual);
                    estudianteActual.agregarGrupoEstudio(grupoSeleccionado);
                    mostrarAlerta("Éxito", "Te has unido al grupo " + grupoSeleccionado.getNombre(), Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No puedes unirte a este grupo porque está inactivo", Alert.AlertType.ERROR);
                }
            }

            // Recargar los datos
            cargarDatos();
        } else {
            // Si no hay grupo seleccionado, mostrar los grupos disponibles para unirse
            ListaEnlazada<GrupoEstudio> gruposDisponibles = redSocialController.getGruposEstudio();
            if (gruposDisponibles != null && !gruposDisponibles.estaVacia()) {
                // Cargar todos los grupos disponibles en la tabla
                ObservableList<GrupoEstudio> gruposObs = FXCollections.observableArrayList();
                for (GrupoEstudio grupo : gruposDisponibles) {
                    if (grupo.isActivo() && !grupo.esMiembro(estudianteActual)) {
                        gruposObs.add(grupo);
                    }
                }
                tblGruposRecomendados.setItems(gruposObs);
                mostrarAlerta("Información", "Se han cargado los grupos disponibles. Seleccione uno para unirse.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Información", "No hay grupos disponibles para unirse. Puede crear uno nuevo.", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void crearGrupoEstudio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalestructurajavafx/vista/crear-grupo-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Grupo de Estudio");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recargar los grupos
            cargarDatos();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de creación de grupo", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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

    public void setEstudiante(Usuario usuario) {
        
    }
}