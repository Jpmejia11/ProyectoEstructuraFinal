package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Clase que representa un contenido educativo en la red social
 */
public class Contenido implements Comparable<Contenido> {
    private String id;
    private String titulo;
    private String descripcion;
    private String tipo; // archivo, enlace, video, etc.
    private String url;
    private ListaEnlazada<String> temas;
    private Usuario autor;
    private LocalDateTime fechaPublicacion;
    private ListaEnlazada<Valoracion> valoraciones;
    private ListaEnlazada<String> etiquetas;
    private File archivo;
    private ListaEnlazada<String> comentarios;

    /**
     * Constructor de la clase Contenido
     * @param id Identificador único del contenido
     * @param titulo Título del contenido
     * @param descripcion Descripción del contenido
     * @param tipo Tipo de contenido (archivo, enlace, video, etc.)
     * @param url URL o ruta del contenido
     * @param autor Usuario que publicó el contenido
     */
    public Contenido(String id, String titulo, String descripcion, String tipo, String url, Usuario autor) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.url = url;
        this.autor = autor;
        this.fechaPublicacion = LocalDateTime.now();
        this.temas = new ListaEnlazada<>();
        this.valoraciones = new ListaEnlazada<>();
        this.comentarios = new ListaEnlazada<>();
    }

    public Contenido(String id, String titulo, String descripcion, String tipo, Usuario autor, ListaEnlazada<String> etiquetas, File archivo ){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etiquetas = etiquetas;
        this.autor = autor;
        this.archivo = archivo;
        this.fechaPublicacion = LocalDateTime.now();
        this.temas = new ListaEnlazada<>();
        this.valoraciones = new ListaEnlazada<>();
        this.comentarios = new ListaEnlazada<>();
    }

    /**
     * Obtiene el identificador del contenido
     * @return Identificador único
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del contenido
     * @param id Nuevo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el título del contenido
     * @return Título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del contenido
     * @param titulo Nuevo título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción del contenido
     * @return Descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del contenido
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el tipo de contenido
     * @return Tipo de contenido
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de contenido
     * @param tipo Nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la URL o ruta del contenido
     * @return URL o ruta
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL o ruta del contenido
     * @param url Nueva URL o ruta
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene los temas relacionados con el contenido
     * @return Lista de temas
     */
    public ListaEnlazada<String> getTemas() {
        return temas;
    }

    /**
     * Establece los temas relacionados con el contenido
     * @param temas Nueva lista de temas
     */
    public void setTemas(ListaEnlazada<String> temas) {
        this.temas = temas;
    }

    /**
     * Agrega un tema al contenido
     * @param tema Nuevo tema
     */
    public void agregarTema(String tema) {
        if (!temas.contiene(tema)) {
            temas.agregar(tema);
        }
    }

    /**
     * Elimina un tema del contenido
     * @param tema Tema a eliminar
     * @return true si se eliminó el tema, false si no existía
     */
    public boolean eliminarTema(String tema) {
        return temas.eliminar(tema);
    }

    /**
     * Obtiene el autor del contenido
     * @return Usuario autor
     */
    public Usuario getAutor() {
        return autor;
    }

    /**
     * Establece el autor del contenido
     * @param autor Nuevo autor
     */
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    /**
     * Obtiene la fecha de publicación del contenido
     * @return Fecha de publicación
     */
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación del contenido
     * @param fechaPublicacion Nueva fecha de publicación
     */
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene las valoraciones del contenido
     * @return Lista de valoraciones
     */
    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    /**
     * Establece las valoraciones del contenido
     * @param valoraciones Nueva lista de valoraciones
     */
    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    /**
     * Agrega una valoración al contenido
     * @param valoracion Nueva valoración
     */
    public void agregarValoracion(Valoracion valoracion) {
        // Verificar si el usuario ya ha valorado este contenido
        for (Valoracion v : valoraciones) {
            if (v.getUsuario().equals(valoracion.getUsuario())) {
                // Actualizar la valoración existente
                v.setPuntuacion(valoracion.getPuntuacion());
                v.setComentario(valoracion.getComentario());
                v.setFecha(LocalDateTime.now());
                return;
            }
        }
        
        // Si no existe una valoración previa, agregar la nueva
        valoraciones.agregar(valoracion);
    }

    /**
     * Elimina una valoración del contenido
     * @param valoracion Valoración a eliminar
     * @return true si se eliminó la valoración, false si no existía
     */
    public boolean eliminarValoracion(Valoracion valoracion) {
        return valoraciones.eliminar(valoracion);
    }

    /**
     * Calcula la valoración promedio del contenido
     * @return Valoración promedio (0-5)
     */
    public double calcularValoracionPromedio() {
        if (valoraciones.estaVacia()) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (Valoracion valoracion : valoraciones) {
            suma += valoracion.getPuntuacion();
        }
        
        return suma / valoraciones.tamaño();
    }

    public ListaEnlazada<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ListaEnlazada<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     * Obtiene el número de valoraciones del contenido
     * @return Número de valoraciones
     */
    public int obtenerNumeroValoraciones() {
        return valoraciones.tamaño();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contenido contenido = (Contenido) obj;
        return id.equals(contenido.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", autor=" + autor.getNombre() +
                ", valoración=" + calcularValoracionPromedio() +
                '}';
    }

    @Override
    public int compareTo(Contenido otro) {
        // Ordenar por título
        return this.titulo.compareTo(otro.titulo);
    }

    public ListaEnlazada<String> getComentarios() {
        return this.comentarios;
    }

    public void agregarComentario(String comentarioCompleto) {
        this.comentarios.agregar(comentarioCompleto);
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}