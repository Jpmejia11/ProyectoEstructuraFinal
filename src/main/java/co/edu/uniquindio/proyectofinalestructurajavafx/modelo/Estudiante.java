package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

/**
 * Clase que representa un estudiante en la red social educativa
 */
public class Estudiante extends Usuario {
    private String carrera;
    private int semestre;
    private ListaEnlazada<SolicitudAyuda> solicitudesAyuda;

    /**
     * Constructor de la clase Estudiante
     * @param id Identificador único del estudiante
     * @param nombre Nombre completo del estudiante
     * @param correo Correo electrónico del estudiante
     * @param contraseña Contraseña del estudiante
     * @param carrera Carrera que estudia
     * @param semestre Semestre actual
     */



    public Estudiante(String id, String nombre, String correo, String contraseña, String carrera, int semestre) {
        super(id, nombre, correo, contraseña, "Administrador");
        this.carrera = carrera;
        this.semestre = semestre;
        this.solicitudesAyuda = new ListaEnlazada<>();
    }

    /**
     * Obtiene la carrera del estudiante
     * @return Carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * Establece la carrera del estudiante
     * @param carrera Nueva carrera
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * Obtiene el semestre del estudiante
     * @return Semestre
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * Establece el semestre del estudiante
     * @param semestre Nuevo semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /**
     * Obtiene las solicitudes de ayuda del estudiante
     * @return Lista de solicitudes de ayuda
     */
    public ListaEnlazada<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }

    /**
     * Establece las solicitudes de ayuda del estudiante
     * @param solicitudesAyuda Nueva lista de solicitudes de ayuda
     */
    public void setSolicitudesAyuda(ListaEnlazada<SolicitudAyuda> solicitudesAyuda) {
        this.solicitudesAyuda = solicitudesAyuda;
    }

    /**
     * Agrega una solicitud de ayuda
     * @param solicitud Nueva solicitud de ayuda
     */
    public void agregarSolicitudAyuda(SolicitudAyuda solicitud) {
        solicitudesAyuda.agregar(solicitud);
    }

    /**
     * Elimina una solicitud de ayuda
     * @param solicitud Solicitud de ayuda a eliminar
     * @return true si se eliminó la solicitud, false si no existía
     */
    public boolean eliminarSolicitudAyuda(SolicitudAyuda solicitud) {
        return solicitudesAyuda.eliminar(solicitud);
    }

    /**
     * Crea una nueva solicitud de ayuda
     * @param tema Tema de la solicitud
     * @param descripcion Descripción de la solicitud
     * @param nivelUrgencia Nivel de urgencia (1-5, siendo 5 el más urgente)
     * @return La solicitud de ayuda creada
     */
    public SolicitudAyuda crearSolicitudAyuda(String tema, String descripcion, int nivelUrgencia) {
        SolicitudAyuda solicitud = new SolicitudAyuda(this, tema, descripcion, nivelUrgencia);
        agregarSolicitudAyuda(solicitud);
        return solicitud;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", carrera='" + carrera + '\'' +
                ", semestre=" + semestre +
                '}';
    }
}