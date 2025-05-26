package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa una solicitud de ayuda académica en la red social educativa
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private String id;
    private Estudiante solicitante;
    private String tema;
    private String descripcion;
    private int nivelUrgencia; // 1-5, siendo 5 el más urgente
    private LocalDateTime fechaSolicitud;
    private boolean resuelta;
    private Estudiante ayudante;
    private LocalDateTime fechaResolucion;

    /**
     * Constructor de la clase SolicitudAyuda
     * @param solicitante Estudiante que solicita ayuda
     * @param tema Tema de la solicitud
     * @param descripcion Descripción detallada de la solicitud
     * @param nivelUrgencia Nivel de urgencia (1-5, siendo 5 el más urgente)
     */
    public SolicitudAyuda(Estudiante solicitante, String tema, String descripcion, int nivelUrgencia) {
        this.id = generarId();
        this.solicitante = solicitante;
        this.tema = tema;
        this.descripcion = descripcion;
        this.nivelUrgencia = validarNivelUrgencia(nivelUrgencia);
        this.fechaSolicitud = LocalDateTime.now();
        this.resuelta = false;
        this.ayudante = null;
        this.fechaResolucion = null;
    }

    /**
     * Genera un identificador único para la solicitud
     * @return Identificador único
     */
    private String generarId() {
        return "SOL-" + System.currentTimeMillis();
    }

    /**
     * Valida que el nivel de urgencia esté en el rango correcto (1-5)
     * @param nivel Nivel a validar
     * @return Nivel validado
     */
    private int validarNivelUrgencia(int nivel) {
        if (nivel < 1) {
            return 1;
        } else if (nivel > 5) {
            return 5;
        }
        return nivel;
    }

    /**
     * Obtiene el identificador de la solicitud
     * @return Identificador único
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador de la solicitud
     * @param id Nuevo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el estudiante solicitante
     * @return Estudiante solicitante
     */
    public Estudiante getSolicitante() {
        return solicitante;
    }

    /**
     * Establece el estudiante solicitante
     * @param solicitante Nuevo solicitante
     */
    public void setSolicitante(Estudiante solicitante) {
        this.solicitante = solicitante;
    }

    /**
     * Obtiene el tema de la solicitud
     * @return Tema
     */
    public String getTema() {
        return tema;
    }

    /**
     * Establece el tema de la solicitud
     * @param tema Nuevo tema
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * Obtiene la descripción de la solicitud
     * @return Descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la solicitud
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nivel de urgencia
     * @return Nivel de urgencia (1-5)
     */
    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    /**
     * Establece el nivel de urgencia
     * @param nivelUrgencia Nuevo nivel de urgencia (1-5)
     */
    public void setNivelUrgencia(int nivelUrgencia) {
        this.nivelUrgencia = validarNivelUrgencia(nivelUrgencia);
    }

    /**
     * Obtiene la fecha de solicitud
     * @return Fecha de solicitud
     */
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Establece la fecha de solicitud
     * @param fechaSolicitud Nueva fecha de solicitud
     */
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * Verifica si la solicitud está resuelta
     * @return true si está resuelta, false en caso contrario
     */
    public boolean isResuelta() {
        return resuelta;
    }

    /**
     * Establece si la solicitud está resuelta
     * @param resuelta Nuevo estado
     */
    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
        
        if (resuelta && fechaResolucion == null) {
            fechaResolucion = LocalDateTime.now();
        } else if (!resuelta) {
            fechaResolucion = null;
        }
    }

    /**
     * Obtiene el estudiante ayudante
     * @return Estudiante ayudante
     */
    public Estudiante getAyudante() {
        return ayudante;
    }

    /**
     * Establece el estudiante ayudante
     * @param ayudante Nuevo ayudante
     */
    public void setAyudante(Estudiante ayudante) {
        this.ayudante = ayudante;
    }

    /**
     * Obtiene la fecha de resolución
     * @return Fecha de resolución
     */
    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    /**
     * Establece la fecha de resolución
     * @param fechaResolucion Nueva fecha de resolución
     */
    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    /**
     * Marca la solicitud como resuelta por un ayudante
     * @param ayudante Estudiante que resolvió la solicitud
     */
    public void marcarComoResuelta(Estudiante ayudante) {
        this.ayudante = ayudante;
        this.resuelta = true;
        this.fechaResolucion = LocalDateTime.now();
    }

    /**
     * Calcula el tiempo transcurrido desde la solicitud
     * @return Tiempo transcurrido en horas
     */
    public long calcularTiempoTranscurrido() {
        LocalDateTime ahora = LocalDateTime.now();
        return java.time.Duration.between(fechaSolicitud, ahora).toHours();
    }

    /**
     * Calcula la prioridad de la solicitud basada en el nivel de urgencia y el tiempo transcurrido
     * @return Valor de prioridad (mayor valor = mayor prioridad)
     */
    public int calcularPrioridad() {
        // La prioridad aumenta con el nivel de urgencia y el tiempo transcurrido
        long horasTranscurridas = calcularTiempoTranscurrido();
        
        // Fórmula simple: nivel de urgencia * 10 + horas transcurridas (hasta un máximo)
        int prioridadTiempo = (int) Math.min(horasTranscurridas, 20); // Máximo 20 puntos por tiempo
        
        return nivelUrgencia * 10 + prioridadTiempo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SolicitudAyuda solicitud = (SolicitudAyuda) obj;
        return id.equals(solicitud.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "SolicitudAyuda{" +
                "id='" + id + '\'' +
                ", solicitante=" + solicitante.getNombre() +
                ", tema='" + tema + '\'' +
                ", nivelUrgencia=" + nivelUrgencia +
                ", resuelta=" + resuelta +
                '}';
    }

    @Override
    public int compareTo(SolicitudAyuda otra) {
        // Comparar por prioridad (mayor prioridad primero)
        return Integer.compare(otra.calcularPrioridad(), this.calcularPrioridad());
    }

    public Estudiante getEstudiante() {
        return solicitante;
    }
}