package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

import java.time.LocalDateTime;

/**
 * Clase que representa un grupo de estudio en la red social educativa
 */
public class GrupoEstudio {
    private String id;
    private String nombre;
    private String descripcion;
    private ListaEnlazada<String> temas;
    private ListaEnlazada<Estudiante> miembros;
    private LocalDateTime fechaCreacion;
    private boolean activo;

    /**
     * Constructor de la clase GrupoEstudio
     * @param id Identificador único del grupo
     * @param nombre Nombre del grupo
     * @param descripcion Descripción del grupo
     */
    public GrupoEstudio(String id, String nombre, String descripcion, ListaEnlazada<String> temas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.temas = temas;
        this.miembros = new ListaEnlazada<>();
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    /**
     * Obtiene el identificador del grupo
     * @return Identificador único
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del grupo
     * @param id Nuevo identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del grupo
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del grupo
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del grupo
     * @return Descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del grupo
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene los temas del grupo
     * @return Lista de temas
     */
    public ListaEnlazada<String> getTemas() {
        return temas;
    }

    /**
     * Establece los temas del grupo
     * @param temas Nueva lista de temas
     */
    public void setTemas(ListaEnlazada<String> temas) {
        this.temas = temas;
    }

    /**
     * Agrega un tema al grupo
     * @param tema Nuevo tema
     */
    public void agregarTema(String tema) {
        if (!temas.contiene(tema)) {
            temas.agregar(tema);
        }
    }

    /**
     * Elimina un tema del grupo
     * @param tema Tema a eliminar
     * @return true si se eliminó el tema, false si no existía
     */
    public boolean eliminarTema(String tema) {
        return temas.eliminar(tema);
    }

    /**
     * Obtiene los miembros del grupo
     * @return Lista de estudiantes miembros
     */
    public ListaEnlazada<Estudiante> getMiembros() {
        return miembros;
    }

    /**
     * Establece los miembros del grupo
     * @param miembros Nueva lista de miembros
     */
    public void setMiembros(ListaEnlazada<Estudiante> miembros) {
        this.miembros = miembros;
    }

    /**
     * Agrega un miembro al grupo
     * @param estudiante Nuevo miembro
     * @return true si se agregó el miembro, false si ya era miembro
     */
    public boolean agregarMiembro(Estudiante estudiante) {
        if (miembros.contiene(estudiante)) {
            return false;
        }
        
        miembros.agregar(estudiante);
        estudiante.agregarGrupoEstudio(this);
        return true;
    }

    /**
     * Elimina un miembro del grupo
     * @param estudiante Miembro a eliminar
     * @return true si se eliminó el miembro, false si no era miembro
     */
    public boolean eliminarMiembro(Estudiante estudiante) {
        if (!miembros.contiene(estudiante)) {
            return false;
        }
        
        miembros.eliminar(estudiante);
        estudiante.eliminarGrupoEstudio(this);
        return true;
    }

    /**
     * Obtiene la fecha de creación del grupo
     * @return Fecha de creación
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del grupo
     * @param fechaCreacion Nueva fecha de creación
     */
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Verifica si el grupo está activo
     * @return true si el grupo está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece si el grupo está activo
     * @param activo Nuevo estado
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene el número de miembros del grupo
     * @return Número de miembros
     */
    public int obtenerNumeroMiembros() {
        return miembros.tamaño();
    }

    /**
     * Verifica si un estudiante es miembro del grupo
     * @param estudiante Estudiante a verificar
     * @return true si es miembro, false en caso contrario
     */
    public boolean esMiembro(Estudiante estudiante) {
        return miembros.contiene(estudiante);
    }

    /**
     * Calcula la afinidad entre el grupo y un estudiante basado en intereses comunes
     * @param estudiante Estudiante a evaluar
     * @return Porcentaje de afinidad (0-100)
     */
    public double calcularAfinidad(Estudiante estudiante) {
        if (temas.estaVacia() || estudiante.getIntereses().estaVacia()) {
            return 0.0;
        }
        
        int temasComunes = 0;
        
        for (String tema : temas) {
            if (estudiante.getIntereses().contiene(tema)) {
                temasComunes++;
            }
        }
        
        return (double) temasComunes / temas.tamaño() * 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GrupoEstudio grupo = (GrupoEstudio) obj;
        return id.equals(grupo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "GrupoEstudio{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", miembros=" + obtenerNumeroMiembros() +
                ", activo=" + activo +
                '}';
    }
}