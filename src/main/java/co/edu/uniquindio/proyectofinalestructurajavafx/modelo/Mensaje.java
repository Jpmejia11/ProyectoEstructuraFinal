package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa un mensaje entre usuarios en la red social educativa
 */
public class Mensaje {
    private String id;
    private Usuario remitente;
    private Usuario destinatario;
    private String asunto;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    /**
     * Constructor de la clase Mensaje
     * @param remitente Usuario que envía el mensaje
     * @param destinatario Usuario que recibe el mensaje
     * @param asunto Asunto del mensaje
     * @param contenido Contenido del mensaje
     */
    public Mensaje(Usuario remitente, Usuario destinatario, String asunto, String contenido) {
        this.id = generarId();
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fechaEnvio = LocalDateTime.now();
        this.leido = false;
    }

    /**
     * Genera un identificador único para el mensaje
     * @return Identificador único
     */
    private String generarId() {
        return "MSG-" + System.currentTimeMillis();
    }

    /**
     * Obtiene el identificador del mensaje
     * @return Identificador único
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del mensaje
     * @param id Nuevo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el remitente del mensaje
     * @return Usuario remitente
     */
    public Usuario getRemitente() {
        return remitente;
    }

    /**
     * Establece el remitente del mensaje
     * @param remitente Nuevo remitente
     */
    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    /**
     * Obtiene el destinatario del mensaje
     * @return Usuario destinatario
     */
    public Usuario getDestinatario() {
        return destinatario;
    }

    /**
     * Establece el destinatario del mensaje
     * @param destinatario Nuevo destinatario
     */
    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Obtiene el asunto del mensaje
     * @return Asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Establece el asunto del mensaje
     * @param asunto Nuevo asunto
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * Obtiene el contenido del mensaje
     * @return Contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del mensaje
     * @param contenido Nuevo contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene la fecha de envío del mensaje
     * @return Fecha de envío
     */
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * Establece la fecha de envío del mensaje
     * @param fechaEnvio Nueva fecha de envío
     */
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * Verifica si el mensaje ha sido leído
     * @return true si ha sido leído, false en caso contrario
     */
    public boolean isLeido() {
        return leido;
    }

    /**
     * Establece si el mensaje ha sido leído
     * @param leido Nuevo estado
     */
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    /**
     * Marca el mensaje como leído
     */
    public void marcarComoLeido() {
        this.leido = true;
    }

    /**
     * Crea un mensaje de respuesta a este mensaje
     * @param contenido Contenido de la respuesta
     * @return Nuevo mensaje de respuesta
     */
    public Mensaje responder(String contenido) {
        String asuntoRespuesta = "RE: " + this.asunto;
        return new Mensaje(this.destinatario, this.remitente, asuntoRespuesta, contenido);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mensaje mensaje = (Mensaje) obj;
        return id.equals(mensaje.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id='" + id + '\'' +
                ", remitente=" + remitente.getNombre() +
                ", destinatario=" + destinatario.getNombre() +
                ", asunto='" + asunto + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", leido=" + leido +
                '}';
    }
}