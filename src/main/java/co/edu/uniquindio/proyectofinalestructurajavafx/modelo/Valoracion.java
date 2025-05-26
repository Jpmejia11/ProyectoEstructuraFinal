package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa una valoración de contenido en la red social educativa
 */
public class Valoracion {
    private Usuario usuario;
    private Contenido contenido;
    private int puntuacion; // 1-5 estrellas
    private String comentario;
    private LocalDateTime fecha;

    /**
     * Constructor de la clase Valoracion
     * @param usuario Usuario que realiza la valoración
     * @param contenido Contenido valorado
     * @param puntuacion Puntuación (1-5 estrellas)
     * @param comentario Comentario opcional
     */
    public Valoracion(Usuario usuario, Contenido contenido, int puntuacion, String comentario) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.puntuacion = validarPuntuacion(puntuacion);
        this.comentario = comentario;
        this.fecha = LocalDateTime.now();
    }

    public Valoracion(Estudiante estudiante, int valoracion) {
        this.usuario = estudiante;
        this.puntuacion = validarPuntuacion(valoracion);
        this.fecha = LocalDateTime.now();
    }

    /**
     * Valida que la puntuación esté en el rango correcto (1-5)
     * @param puntuacion Puntuación a validar
     * @return Puntuación validada
     */
    private int validarPuntuacion(int puntuacion) {
        if (puntuacion < 1) {
            return 1;
        } else if (puntuacion > 5) {
            return 5;
        }
        return puntuacion;
    }

    /**
     * Obtiene el usuario que realizó la valoración
     * @return Usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó la valoración
     * @param usuario Nuevo usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el contenido valorado
     * @return Contenido
     */
    public Contenido getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido valorado
     * @param contenido Nuevo contenido
     */
    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene la puntuación
     * @return Puntuación (1-5 estrellas)
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Establece la puntuación
     * @param puntuacion Nueva puntuación (1-5 estrellas)
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = validarPuntuacion(puntuacion);
    }

    /**
     * Obtiene el comentario
     * @return Comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario
     * @param comentario Nuevo comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene la fecha de la valoración
     * @return Fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la valoración
     * @param fecha Nueva fecha
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Valoracion valoracion = (Valoracion) obj;
        return usuario.equals(valoracion.usuario) && contenido.equals(valoracion.contenido);
    }

    @Override
    public int hashCode() {
        return 31 * usuario.hashCode() + contenido.hashCode();
    }

    @Override
    public String toString() {
        return "Valoracion{" +
                "usuario=" + usuario.getNombre() +
                ", contenido=" + contenido.getTitulo() +
                ", puntuacion=" + puntuacion +
                ", fecha=" + fecha +
                '}';
    }
}