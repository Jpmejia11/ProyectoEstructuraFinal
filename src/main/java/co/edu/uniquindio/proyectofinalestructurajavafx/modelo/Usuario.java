package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

/**
 * Clase que representa un usuario en la red social educativa
 */
public class    Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contraseña;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> contenidosPublicados;
    private ListaEnlazada<Valoracion> valoracionesRealizadas;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    private ListaEnlazada<Mensaje> mensajesRecibidos;

    /**
     * Constructor de la clase Usuario
     *
     * @param id            Identificador único del usuario
     * @param nombre        Nombre completo del usuario
     * @param correo        Correo electrónico del usuario
     * @param contraseña    Contraseña del usuario
     * @param administrador
     */
    public Usuario(String id, String nombre, String correo, String contraseña, String administrador) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.intereses = new ListaEnlazada<>();
        this.contenidosPublicados = new ListaEnlazada<>();
        this.valoracionesRealizadas = new ListaEnlazada<>();
        this.gruposEstudio = new ListaEnlazada<>();
        this.mensajesRecibidos = new ListaEnlazada<>();
    }


    /**
     * Obtiene el identificador del usuario
     * @return Identificador único
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario
     * @param id Nuevo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario
     * @return Nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario
     * @return Correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario
     * @param correo Nuevo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario
     * @return Contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario
     * @param contraseña Nueva contraseña
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene los intereses del usuario
     * @return Lista de intereses
     */
    public ListaEnlazada<String> getIntereses() {
        return intereses;
    }

    /**
     * Establece los intereses del usuario
     * @param intereses Nueva lista de intereses
     */
    public void setIntereses(ListaEnlazada<String> intereses) {
        this.intereses = intereses;
    }

    /**
     * Agrega un interés al usuario
     * @param interes Nuevo interés
     */
    public void agregarInteres(String interes) {
        if (!intereses.contiene(interes)) {
            intereses.agregar(interes);
        }
    }

    /**
     * Elimina un interés del usuario
     * @param interes Interés a eliminar
     * @return true si se eliminó el interés, false si no existía
     */
    public boolean eliminarInteres(String interes) {
        return intereses.eliminar(interes);
    }

    /**
     * Obtiene los contenidos publicados por el usuario
     * @return Lista de contenidos publicados
     */
    public ListaEnlazada<Contenido> getContenidosPublicados() {
        return contenidosPublicados;
    }

    /**
     * Establece los contenidos publicados por el usuario
     * @param contenidosPublicados Nueva lista de contenidos
     */
    public void setContenidosPublicados(ListaEnlazada<Contenido> contenidosPublicados) {
        this.contenidosPublicados = contenidosPublicados;
    }

    /**
     * Agrega un contenido publicado por el usuario
     * @param contenido Nuevo contenido
     */
    public void agregarContenido(Contenido contenido) {
        contenidosPublicados.agregar(contenido);
    }

    /**
     * Elimina un contenido publicado por el usuario
     * @param contenido Contenido a eliminar
     * @return true si se eliminó el contenido, false si no existía
     */
    public boolean eliminarContenido(Contenido contenido) {
        return contenidosPublicados.eliminar(contenido);
    }

    /**
     * Obtiene las valoraciones realizadas por el usuario
     * @return Lista de valoraciones
     */
    public ListaEnlazada<Valoracion> getValoracionesRealizadas() {
        return valoracionesRealizadas;
    }

    /**
     * Establece las valoraciones realizadas por el usuario
     * @param valoracionesRealizadas Nueva lista de valoraciones
     */
    public void setValoracionesRealizadas(ListaEnlazada<Valoracion> valoracionesRealizadas) {
        this.valoracionesRealizadas = valoracionesRealizadas;
    }

    /**
     * Agrega una valoración realizada por el usuario
     * @param valoracion Nueva valoración
     */
    public void agregarValoracion(Valoracion valoracion) {
        valoracionesRealizadas.agregar(valoracion);
    }

    /**
     * Elimina una valoración realizada por el usuario
     * @param valoracion Valoración a eliminar
     * @return true si se eliminó la valoración, false si no existía
     */
    public boolean eliminarValoracion(Valoracion valoracion) {
        return valoracionesRealizadas.eliminar(valoracion);
    }

    /**
     * Obtiene los grupos de estudio del usuario
     * @return Lista de grupos de estudio
     */
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    /**
     * Establece los grupos de estudio del usuario
     * @param gruposEstudio Nueva lista de grupos de estudio
     */
    public void setGruposEstudio(ListaEnlazada<GrupoEstudio> gruposEstudio) {
        this.gruposEstudio = gruposEstudio;
    }

    /**
     * Agrega un grupo de estudio al usuario
     * @param grupo Nuevo grupo de estudio
     */
    public void agregarGrupoEstudio(GrupoEstudio grupo) {
        if (!gruposEstudio.contiene(grupo)) {
            gruposEstudio.agregar(grupo);
        }
    }

    /**
     * Elimina un grupo de estudio del usuario
     * @param grupo Grupo de estudio a eliminar
     * @return true si se eliminó el grupo, false si no existía
     */
    public boolean eliminarGrupoEstudio(GrupoEstudio grupo) {
        return gruposEstudio.eliminar(grupo);
    }

    /**
     * Obtiene los mensajes recibidos por el usuario
     * @return Lista de mensajes
     */
    public ListaEnlazada<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    /**
     * Establece los mensajes recibidos por el usuario
     * @param mensajesRecibidos Nueva lista de mensajes
     */
    public void setMensajesRecibidos(ListaEnlazada<Mensaje> mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
    }

    /**
     * Agrega un mensaje recibido por el usuario
     * @param mensaje Nuevo mensaje
     */
    public void agregarMensaje(Mensaje mensaje) {
        mensajesRecibidos.agregar(mensaje);
    }

    /**
     * Elimina un mensaje recibido por el usuario
     * @param mensaje Mensaje a eliminar
     * @return true si se eliminó el mensaje, false si no existía
     */
    public boolean eliminarMensaje(Mensaje mensaje) {
        return mensajesRecibidos.eliminar(mensaje);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}