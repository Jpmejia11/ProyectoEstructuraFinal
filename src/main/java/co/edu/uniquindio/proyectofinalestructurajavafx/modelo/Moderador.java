package co.edu.uniquindio.proyectofinalestructurajavafx.modelo;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;

/**
 * Clase que representa un moderador en la red social educativa
 */
public class Moderador extends Usuario {
    private String rol;
    private ListaEnlazada<String> permisos;

    /**
     * Constructor de la clase Moderador
     * @param id Identificador único del moderador
     * @param nombre Nombre completo del moderador
     * @param correo Correo electrónico del moderador
     * @param contraseña Contraseña del moderador
     * @param rol Rol del moderador
     */
    public Moderador(String id, String nombre, String correo, String contraseña, String rol) {
        super(id, nombre, correo, contraseña, "Administrador");
        this.rol = rol;
        this.permisos = new ListaEnlazada<>();
        inicializarPermisos();
    }

    /**
     * Inicializa los permisos por defecto del moderador
     */
    private void inicializarPermisos() {
        // Permisos básicos que todo moderador debe tener
        permisos.agregar("gestionar_usuarios");
        permisos.agregar("gestionar_contenidos");
        permisos.agregar("visualizar_grafo");
        permisos.agregar("generar_reportes");
        permisos.agregar("acceso_panel_moderador");
        permisos.agregar("gestionar_grupos");
        permisos.agregar("gestionar_solicitudes");
    }

    /**
     * Obtiene el rol del moderador
     * @return Rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del moderador
     * @param rol Nuevo rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene los permisos del moderador
     * @return Lista de permisos
     */
    public ListaEnlazada<String> getPermisos() {
        return permisos;
    }

    /**
     * Establece los permisos del moderador
     * @param permisos Nueva lista de permisos
     */
    public void setPermisos(ListaEnlazada<String> permisos) {
        this.permisos = permisos;
    }

    /**
     * Agrega un permiso al moderador
     * @param permiso Nuevo permiso
     */
    public void agregarPermiso(String permiso) {
        if (!permisos.contiene(permiso)) {
            permisos.agregar(permiso);
        }
    }

    /**
     * Elimina un permiso del moderador
     * @param permiso Permiso a eliminar
     * @return true si se eliminó el permiso, false si no existía
     */
    public boolean eliminarPermiso(String permiso) {
        return permisos.eliminar(permiso);
    }

    /**
     * Verifica si el moderador tiene un permiso específico
     * @param permiso Permiso a verificar
     * @return true si tiene el permiso, false en caso contrario
     */
    public boolean tienePermiso(String permiso) {
        return permisos.contiene(permiso);
    }

    /**
     * Genera un reporte de contenidos más valorados
     * @param contenidos Lista de contenidos
     * @param cantidad Cantidad máxima de contenidos a incluir en el reporte
     * @return Lista de contenidos más valorados
     */
    public ListaEnlazada<Contenido> generarReporteContenidosMasValorados(ListaEnlazada<Contenido> contenidos, int cantidad) {
        // Implementación simple: ordenar por valoración promedio y tomar los primeros 'cantidad'
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        
        // Crear una copia de la lista para no modificar la original
        ListaEnlazada<Contenido> copia = new ListaEnlazada<>();
        for (Contenido contenido : contenidos) {
            copia.agregar(contenido);
        }
        
        // Ordenar por valoración promedio (implementación simple de ordenamiento por selección)
        int n = copia.tamaño();
        for (int i = 0; i < n && i < cantidad; i++) {
            Contenido mejorContenido = null;
            double mejorValoracion = -1;
            int mejorIndice = -1;
            
            for (int j = 0; j < copia.tamaño(); j++) {
                Contenido actual = copia.obtener(j);
                double valoracionActual = actual.calcularValoracionPromedio();
                
                if (valoracionActual > mejorValoracion) {
                    mejorValoracion = valoracionActual;
                    mejorContenido = actual;
                    mejorIndice = j;
                }
            }
            
            if (mejorContenido != null) {
                resultado.agregar(mejorContenido);
                copia.eliminarEn(mejorIndice);
            }
        }
        
        return resultado;
    }

    /**
     * Genera un reporte de estudiantes con más conexiones
     * @param estudiantes Lista de estudiantes
     * @param grafoAfinidad Grafo de afinidad entre estudiantes
     * @param cantidad Cantidad máxima de estudiantes a incluir en el reporte
     * @return Lista de estudiantes con más conexiones
     */
    public ListaEnlazada<Estudiante> generarReporteEstudiantesConMasConexiones(
            ListaEnlazada<Estudiante> estudiantes, 
            co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.Grafo<Estudiante> grafoAfinidad, 
            int cantidad) {
        
        ListaEnlazada<Estudiante> resultado = new ListaEnlazada<>();
        
        // Crear una copia de la lista para no modificar la original
        ListaEnlazada<Estudiante> copia = new ListaEnlazada<>();
        for (Estudiante estudiante : estudiantes) {
            copia.agregar(estudiante);
        }
        
        // Ordenar por número de conexiones (implementación simple de ordenamiento por selección)
        int n = copia.tamaño();
        for (int i = 0; i < n && i < cantidad; i++) {
            Estudiante mejorEstudiante = null;
            int mayorConexiones = -1;
            int mejorIndice = -1;
            
            for (int j = 0; j < copia.tamaño(); j++) {
                Estudiante actual = copia.obtener(j);
                int conexionesActual = grafoAfinidad.grado(actual);
                
                if (conexionesActual > mayorConexiones) {
                    mayorConexiones = conexionesActual;
                    mejorEstudiante = actual;
                    mejorIndice = j;
                }
            }
            
            if (mejorEstudiante != null) {
                resultado.agregar(mejorEstudiante);
                copia.eliminarEn(mejorIndice);
            }
        }
        
        return resultado;
    }

    @Override
    public String toString() {
        return "Moderador{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}