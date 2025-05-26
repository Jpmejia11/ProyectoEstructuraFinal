package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.*;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.*;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Controlador principal de la red social educativa
 */
public class RedSocialController {
    private static RedSocialController instancia;
    
    private ListaEnlazada<Usuario> usuarios;

    private ListaEnlazada<Estudiante> estudiantes;
    private ListaEnlazada<Moderador> moderadores;
    private ListaEnlazada<Contenido> contenidos;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    private ListaEnlazada<SolicitudAyuda> solicitudesAyuda;
    
    private ArbolBinarioBusqueda<Contenido> arbolContenidos;
    private Grafo<Estudiante> grafoAfinidad;
    private ColaPrioridad<SolicitudAyuda> colaSolicitudes;
    
    private Usuario usuarioActual;

    /**
     * Constructor privado para el patrón Singleton
     */
    RedSocialController() {
        inicializar();
    }

    /**
     * Obtiene la instancia única del controlador
     * @return Instancia del controlador
     */
    public static RedSocialController getInstancia() {
        if (instancia == null) {
            instancia = new RedSocialController();
        }
        return instancia;
    }
    public boolean agregarEstudiante(Estudiante estudiante) {
        if (estudiantes.contiene(estudiante)) {
            return false;
        }
        estudiantes.agregar(estudiante);
        grafoAfinidad.agregarVertice(estudiante);  // Aquí agregas al grafo
        return true;
    }

    public void construirGrafoAfinidad() {
        for (Estudiante e1 : estudiantes) {
            for (Estudiante e2 : estudiantes) {
                if (!e1.equals(e2) && !grafoAfinidad.existeArista(e1, e2)) {
                    int peso = calcularAfinidad(e1, e2);
                    if (peso > 0) {
                        grafoAfinidad.agregarArista(e1, e2);
                    }
                }
            }
        }
    }

    private int calcularAfinidad(Estudiante e1, Estudiante e2) {
        int comunes = 0;
        for (String interes : e1.getIntereses()) {
            if (e2.getIntereses().contiene(interes)) {
                comunes++;
            }
        }
        return comunes;
    }

    /**
     * Inicializa las estructuras de datos
     */
    private void inicializar() {
        usuarios = new ListaEnlazada<>();
        estudiantes = new ListaEnlazada<>();
        moderadores = new ListaEnlazada<>();
        contenidos = new ListaEnlazada<>();
        gruposEstudio = new ListaEnlazada<>();
        solicitudesAyuda = new ListaEnlazada<>();
        
        arbolContenidos = new ArbolBinarioBusqueda<>();
        grafoAfinidad = new Grafo<>();
        colaSolicitudes = new ColaPrioridad<>();
        
        usuarioActual = null;
        
        // Crear datos de prueba
        crearDatosPrueba();
    }

    /**
     * Crea datos de prueba para la aplicación
     */
    private void crearDatosPrueba() {
        // Crear moderador con todos los permisos necesarios
        Moderador moderador = new Moderador("mod1", "Administrador", "admin@redsocial.edu", "admin123", "Administrador");
        // Asegurar que tenga todos los permisos necesarios
        moderador.agregarPermiso("acceso_panel_moderador");
        moderador.agregarPermiso("gestionar_usuarios");
        moderador.agregarPermiso("gestionar_contenidos");
        moderador.agregarPermiso("gestionar_grupos");
        moderador.agregarPermiso("gestionar_solicitudes");
        moderador.agregarPermiso("visualizar_grafo");
        moderador.agregarPermiso("generar_reportes");
        agregarUsuario(moderador);
        moderadores.agregar(moderador);
        
        // Crear estudiantes
        Estudiante est1 = new Estudiante("est1", "Ana García", "ana@universidad.edu", "pass123", "Ingeniería de Sistemas", 3);
        est1.agregarInteres("Programación");
        est1.agregarInteres("Bases de Datos");
        est1.agregarInteres("Inteligencia Artificial");
        agregarUsuario(est1);
        
        Estudiante est2 = new Estudiante("est2", "Carlos Rodríguez", "carlos@universidad.edu", "pass123", "Ingeniería de Sistemas", 5);
        est2.agregarInteres("Programación");
        est2.agregarInteres("Desarrollo");
        est2.agregarInteres("Seguridad Informática");
        agregarUsuario(est2);
        
        Estudiante est3 = new Estudiante("est3", "María López", "maria@universidad.edu", "pass123", "Ingeniería de Software", 4);
        est3.agregarInteres("Bases de Datos");
        est3.agregarInteres("Desarrollo Móvil");
        est3.agregarInteres("Metodologías Ágiles");
        agregarUsuario(est3);
        
        // Crear contenidos
        Contenido cont1 = new Contenido("cont1", "Introducción a Java", "Guía básica de programación en Java", "Documento", "java_intro.pdf", est1);
        cont1.agregarTema("Programación");
        cont1.agregarTema("Java");
        agregarContenido(cont1);
        
        Contenido cont2 = new Contenido("cont2", "SQL Avanzado", "Técnicas avanzadas de consultas SQL", "Enlace", "https://sql-avanzado.edu", est2);
        cont2.agregarTema("Bases de Datos");
        cont2.agregarTema("SQL");
        agregarContenido(cont2);
        
        Contenido cont3 = new Contenido("cont3", "Desarrollo de Apps con Flutter", "Tutorial de desarrollo móvil con Flutter", "Video", "flutter_tutorial.mp4", est3);
        cont3.agregarTema("Desarrollo Móvil");
        cont3.agregarTema("Flutter");
        agregarContenido(cont3);
        
        // Crear valoraciones
        Valoracion val1 = new Valoracion(est2, cont1, 5, "Excelente tutorial, muy claro");
        cont1.agregarValoracion(val1);
        est2.agregarValoracion(val1);
        
        Valoracion val2 = new Valoracion(est3, cont1, 4, "Muy bueno, pero podría tener más ejemplos");
        cont1.agregarValoracion(val2);
        est3.agregarValoracion(val2);
        
        Valoracion val3 = new Valoracion(est1, cont2, 5, "Muy completo, me ayudó mucho");
        cont2.agregarValoracion(val3);
        est1.agregarValoracion(val3);
        
        // Crear grupos de estudio
        GrupoEstudio grupo1 = new GrupoEstudio("grupo1", "Programación Java", "Grupo para estudiar Java y sus frameworks", new ListaEnlazada<>());
        grupo1.agregarTema("Programación");
        grupo1.agregarTema("Java");
        grupo1.agregarMiembro(est1);
        grupo1.agregarMiembro(est2);
        agregarGrupoEstudio(grupo1);
        
        GrupoEstudio grupo2 = new GrupoEstudio("grupo2", "Bases de Datos", "Grupo para estudiar diseño y optimización de BD", new ListaEnlazada<>());
        grupo2.agregarTema("Bases de Datos");
        grupo2.agregarTema("SQL");
        grupo2.agregarMiembro(est1);
        grupo2.agregarMiembro(est3);
        agregarGrupoEstudio(grupo2);
        
        // Crear grupos adicionales para que los estudiantes puedan unirse
        GrupoEstudio grupo3 = new GrupoEstudio("grupo3", "Desarrollo Web", "Grupo para estudiar tecnologías web modernas", new ListaEnlazada<>());
        grupo3.agregarTema("Desarrollo Web");
        grupo3.agregarTema("JavaScript");
        grupo3.agregarTema("HTML/CSS");
        agregarGrupoEstudio(grupo3);
        
        GrupoEstudio grupo4 = new GrupoEstudio("grupo4", "Inteligencia Artificial", "Grupo para estudiar IA y aprendizaje automático", new ListaEnlazada<>());
        grupo4.agregarTema("Inteligencia Artificial");
        grupo4.agregarTema("Machine Learning");
        grupo4.agregarTema("Python");
        agregarGrupoEstudio(grupo4);
        
        GrupoEstudio grupo5 = new GrupoEstudio("grupo5", "Desarrollo Móvil", "Grupo para estudiar desarrollo de aplicaciones móviles", new ListaEnlazada<>());
        grupo5.agregarTema("Desarrollo Móvil");
        grupo5.agregarTema("Android");
        grupo5.agregarTema("iOS");
        agregarGrupoEstudio(grupo5);
        
        GrupoEstudio grupo6 = new GrupoEstudio("grupo6", "Seguridad Informática", "Grupo para estudiar ciberseguridad", new ListaEnlazada<>());
        grupo6.agregarTema("Seguridad Informática");
        grupo6.agregarTema("Ethical Hacking");
        grupo6.agregarTema("Criptografía");
        agregarGrupoEstudio(grupo6);
        
        // Crear solicitudes de ayuda
        SolicitudAyuda sol1 = est1.crearSolicitudAyuda("Programación Funcional", "Necesito ayuda con lambdas en Java", 3);
        agregarSolicitudAyuda(sol1);
        
        SolicitudAyuda sol2 = est3.crearSolicitudAyuda("Consultas SQL", "Tengo problemas con joins complejos", 4);
        agregarSolicitudAyuda(sol2);
        
        // Crear conexiones en el grafo de afinidad
        grafoAfinidad.agregarVertice(est1);
        grafoAfinidad.agregarVertice(est2);
        grafoAfinidad.agregarVertice(est3);
        
        grafoAfinidad.agregarArista(est1, est2); // Comparten interés en Programación
        grafoAfinidad.agregarArista(est1, est3); // Comparten interés en Bases de Datos
    }

    /**
     * Agrega un usuario al sistema
     * @param usuario Usuario a agregar
     * @return true si se agregó correctamente, false si ya existía
     */
    public boolean agregarUsuario(Usuario usuario) {
        if (buscarUsuarioPorId(usuario.getId()) != null) {
            return false;
        }
        
        usuarios.agregar(usuario);
        
        if (usuario instanceof Estudiante) {
            estudiantes.agregar((Estudiante) usuario);
            grafoAfinidad.agregarVertice((Estudiante) usuario);
        }

        return true;
    }
    public ListaEnlazada<Estudiante> obtenerEstudiantesRegistrados() {
        return estudiantes; // tu lista donde guardas los estudiantes
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Busca un usuario por su correo
     * @param correo Correo del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Autentica a un usuario en el sistema
     * @param correo Correo del usuario
     * @param contraseña Contraseña del usuario
     * @return true si la autenticación fue exitosa, false en caso contrario
     */
    public boolean autenticarUsuario(String correo, String contraseña) {
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            usuarioActual = usuario;
            return true;
        }
        
        return false;
    }

    /**
     * Cierra la sesión del usuario actual
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Registra un nuevo estudiante en el sistema
     * @param nombre Nombre del estudiante
     * @param correo Correo del estudiante
     * @param contraseña Contraseña del estudiante
     * @param carrera Carrera del estudiante
     * @param semestre Semestre del estudiante
     * @return Estudiante creado o null si el correo ya está en uso
     */
    public Estudiante registrarEstudiante(String nombre, String correo, String contraseña, String carrera, int semestre) {
        if (buscarUsuarioPorCorreo(correo) != null) {
            return null;
        }
        
        String id = "est-" + UUID.randomUUID().toString().substring(0, 8);
        Estudiante estudiante = new Estudiante(id, nombre, correo, contraseña, carrera, semestre);
        
        if (agregarUsuario(estudiante)) {
            return estudiante;
        }
        
        return null;
    }

    /**
     * Agrega un contenido al sistema
     * @param contenido Contenido a agregar
     * @return true si se agregó correctamente, false si ya existía
     */
    public boolean agregarContenido(Contenido contenido) {
        if (buscarContenidoPorId(contenido.getId()) != null) {
            return false;
        }
        
        contenidos.agregar(contenido);
        arbolContenidos.insertar(contenido);
        
        if (contenido.getAutor() instanceof Estudiante) {
            ((Estudiante) contenido.getAutor()).agregarContenido(contenido);
        }
        
        return true;
    }

    /**
     * Busca un contenido por su ID
     * @param id ID del contenido
     * @return Contenido encontrado o null si no existe
     */
    public Contenido buscarContenidoPorId(String id) {
        for (Contenido contenido : contenidos) {
            if (contenido.getId().equals(id)) {
                return contenido;
            }
        }
        return null;
    }

    /**
     * Busca contenidos por tema
     * @param tema Tema a buscar
     * @return Lista de contenidos que contienen el tema
     */
    public ListaEnlazada<Contenido> buscarContenidosPorTema(String tema) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        
        for (Contenido contenido : contenidos) {
            for (String t : contenido.getTemas()) {
                if (t.toLowerCase().contains(tema.toLowerCase())) {
                    resultado.agregar(contenido);
                    break;
                }
            }
        }
        
        return resultado;
    }

    /**
     * Agrega un grupo de estudio al sistema
     * @param grupo Grupo de estudio a agregar
     * @return true si se agregó correctamente, false si ya existía
     */
    public boolean agregarGrupoEstudio(GrupoEstudio grupo) {
        if (buscarGrupoEstudioPorId(grupo.getId()) != null) {
            return false;
        }
        
        gruposEstudio.agregar(grupo);
        return true;
    }

    /**
     * Busca un grupo de estudio por su ID
     * @param id ID del grupo
     * @return Grupo de estudio encontrado o null si no existe
     */
    public GrupoEstudio buscarGrupoEstudioPorId(String id) {
        for (GrupoEstudio grupo : gruposEstudio) {
            if (grupo.getId().equals(id)) {
                return grupo;
            }
        }
        return null;
    }

    /**
     * Agrega una solicitud de ayuda al sistema
     * @param solicitud Solicitud de ayuda a agregar
     * @return true si se agregó correctamente, false si ya existía
     */
    public boolean agregarSolicitudAyuda(SolicitudAyuda solicitud) {
        if (buscarSolicitudAyudaPorId(solicitud.getId()) != null) {
            return false;
        }
        
        solicitudesAyuda.agregar(solicitud);
        colaSolicitudes.insertar(solicitud, solicitud.calcularPrioridad());
        return true;
    }

    /**
     * Busca una solicitud de ayuda por su ID
     * @param id ID de la solicitud
     * @return Solicitud de ayuda encontrada o null si no existe
     */
    public SolicitudAyuda buscarSolicitudAyudaPorId(String id) {
        for (SolicitudAyuda solicitud : solicitudesAyuda) {
            if (solicitud.getId().equals(id)) {
                return solicitud;
            }
        }
        return null;
    }

    /**
     * Obtiene la siguiente solicitud de ayuda con mayor prioridad
     * @return Solicitud de ayuda con mayor prioridad o null si no hay solicitudes
     */
    public SolicitudAyuda obtenerSiguienteSolicitudAyuda() {
        if (colaSolicitudes.estaVacia()) {
            return null;
        }
        
        return colaSolicitudes.extraer();
    }

    /**
     * Actualiza la prioridad de una solicitud de ayuda
     * @param solicitud Solicitud de ayuda
     */
    public void actualizarPrioridadSolicitud(SolicitudAyuda solicitud) {
        colaSolicitudes.actualizarPrioridad(solicitud, solicitud.calcularPrioridad());
    }

    /**
     * Crea una conexión de afinidad entre dos estudiantes
     * @param estudiante1 Primer estudiante
     * @param estudiante2 Segundo estudiante
     * @return true si se creó la conexión, false si ya existía o algún estudiante no existe
     */
    public boolean crearConexionAfinidad(Estudiante estudiante1, Estudiante estudiante2) {
        return grafoAfinidad.agregarArista(estudiante1, estudiante2);
    }

    /**
     * Verifica si existe una conexión de afinidad entre dos estudiantes
     * @param estudiante1 Primer estudiante
     * @param estudiante2 Segundo estudiante
     * @return true si existe la conexión, false en caso contrario
     */
    public boolean existeConexionAfinidad(Estudiante estudiante1, Estudiante estudiante2) {
        return grafoAfinidad.existeArista(estudiante1, estudiante2);
    }

    /**
     * Encuentra el camino más corto entre dos estudiantes en el grafo de afinidad
     * @param origen Estudiante origen
     * @param destino Estudiante destino
     * @return Lista con el camino más corto, o lista vacía si no hay camino
     */
    public ListaEnlazada<Estudiante> encontrarCaminoMasCorto(Estudiante origen, Estudiante destino) {
        return grafoAfinidad.caminoMasCorto(origen, destino);
    }

    /**
     * Detecta comunidades de estudio en el grafo de afinidad
     * @return Lista de comunidades, donde cada comunidad es una lista de estudiantes
     */
    public ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades() {
        return grafoAfinidad.detectarComunidades();
    }

    /**
     * Encuentra estudiantes con intereses similares a un estudiante dado
     * @param estudiante Estudiante de referencia
     * @param cantidad Cantidad máxima de sugerencias
     * @return Lista de estudiantes con intereses similares
     */
    public ListaEnlazada<Estudiante> encontrarEstudiantesConInteresesSimilares(Estudiante estudiante, int cantidad) {
        ListaEnlazada<Estudiante> resultado = new ListaEnlazada<>();
        
        if (!estudiantes.contiene(estudiante)) {
            return resultado;
        }
        
        // Crear un mapa para contar intereses comunes
        class EstudianteAfinidad implements Comparable<EstudianteAfinidad> {
            Estudiante estudiante;
            int interesesComunes;
            
            EstudianteAfinidad(Estudiante estudiante, int interesesComunes) {
                this.estudiante = estudiante;
                this.interesesComunes = interesesComunes;
            }
            
            @Override
            public int compareTo(EstudianteAfinidad otro) {
                return Integer.compare(otro.interesesComunes, this.interesesComunes);
            }
        }
        
        ListaEnlazada<EstudianteAfinidad> afinidades = new ListaEnlazada<>();
        
        for (Estudiante otro : estudiantes) {
            if (!otro.equals(estudiante)) {
                int interesesComunes = 0;
                
                for (String interes : estudiante.getIntereses()) {
                    if (otro.getIntereses().contiene(interes)) {
                        interesesComunes++;
                    }
                }
                
                if (interesesComunes > 0) {
                    afinidades.agregar(new EstudianteAfinidad(otro, interesesComunes));
                }
            }
        }
        
        // Ordenar por número de intereses comunes (mayor a menor)
        ListaEnlazada<EstudianteAfinidad> ordenados = new ListaEnlazada<>();
        while (!afinidades.estaVacia()) {
            EstudianteAfinidad mejor = null;
            int mejorIndice = -1;
            
            for (int i = 0; i < afinidades.tamaño(); i++) {
                EstudianteAfinidad actual = afinidades.obtener(i);
                if (mejor == null || actual.interesesComunes > mejor.interesesComunes) {
                    mejor = actual;
                    mejorIndice = i;
                }
            }
            
            if (mejor != null) {
                ordenados.agregar(mejor);
                afinidades.eliminarEn(mejorIndice);
            }
        }
        
        // Tomar los primeros 'cantidad' estudiantes
        int contador = 0;
        for (EstudianteAfinidad afinidad : ordenados) {
            if (contador < cantidad) {
                resultado.agregar(afinidad.estudiante);
                contador++;
            } else {
                break;
            }
        }
        
        return resultado;
    }

    /**
     * Sugiere grupos de estudio para un estudiante basado en sus intereses
     * @param estudiante Estudiante
     * @param cantidad Cantidad máxima de sugerencias
     * @return Lista de grupos de estudio sugeridos
     */
    public ListaEnlazada<GrupoEstudio> sugerirGruposEstudio(Estudiante estudiante, int cantidad) {
        ListaEnlazada<GrupoEstudio> resultado = new ListaEnlazada<>();
        
        // Crear un mapa para calcular afinidad con cada grupo
        class GrupoAfinidad implements Comparable<GrupoAfinidad> {
            GrupoEstudio grupo;
            double afinidad;
            
            GrupoAfinidad(GrupoEstudio grupo, double afinidad) {
                this.grupo = grupo;
                this.afinidad = afinidad;
            }
            
            @Override
            public int compareTo(GrupoAfinidad otro) {
                return Double.compare(otro.afinidad, this.afinidad);
            }
        }
        
        ListaEnlazada<GrupoAfinidad> afinidades = new ListaEnlazada<>();
        
        for (GrupoEstudio grupo : gruposEstudio) {
            if (!grupo.esMiembro(estudiante) && grupo.isActivo()) {
                double afinidad = grupo.calcularAfinidad(estudiante);
                
                if (afinidad > 0) {
                    afinidades.agregar(new GrupoAfinidad(grupo, afinidad));
                }
            }
        }
        
        // Ordenar por afinidad (mayor a menor)
        ListaEnlazada<GrupoAfinidad> ordenados = new ListaEnlazada<>();
        while (!afinidades.estaVacia()) {
            GrupoAfinidad mejor = null;
            int mejorIndice = -1;
            
            for (int i = 0; i < afinidades.tamaño(); i++) {
                GrupoAfinidad actual = afinidades.obtener(i);
                if (mejor == null || actual.afinidad > mejor.afinidad) {
                    mejor = actual;
                    mejorIndice = i;
                }
            }
            
            if (mejor != null) {
                ordenados.agregar(mejor);
                afinidades.eliminarEn(mejorIndice);
            }
        }
        
        // Tomar los primeros 'cantidad' grupos
        int contador = 0;
        for (GrupoAfinidad afinidad : ordenados) {
            if (contador < cantidad) {
                resultado.agregar(afinidad.grupo);
                contador++;
            } else {
                break;
            }
        }
        
        return resultado;
    }

    /**
     * Obtiene el usuario actual
     * @return Usuario actual o null si no hay sesión iniciada
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Obtiene la lista de usuarios
     * @return Lista de usuarios
     */
    public ListaEnlazada<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Obtiene la lista de estudiantes
     * @return Lista de estudiantes
     */
    public ListaEnlazada<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    /**
     * Obtiene la lista de moderadores
     * @return Lista de moderadores
     */
    public ListaEnlazada<Moderador> getModeradores() {
        return moderadores;
    }

    /**
     * Obtiene la lista de contenidos
     * @return Lista de contenidos
     */
    public ListaEnlazada<Contenido> getContenidos() {
        return contenidos;
    }

    /**
     * Obtiene la lista de grupos de estudio
     * @return Lista de grupos de estudio
     */
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    /**
     * Obtiene la lista de solicitudes de ayuda
     * @return Lista de solicitudes de ayuda
     */
    public ListaEnlazada<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }

    /**
     * Obtiene el árbol de contenidos
     * @return Árbol de contenidos
     */
    public ArbolBinarioBusqueda<Contenido> getArbolContenidos() {
        return arbolContenidos;
    }

    /**
     * Obtiene el grafo de afinidad
     * @return Grafo de afinidad
     */
    public Grafo<Estudiante> getGrafoAfinidad() {
        return grafoAfinidad;
    }

    public Contenido crearContenido(String titulo, String descripcion, String tipo, Estudiante autor, List<String> etiquetas, File archivo) {

        ListaEnlazada<String> lista = new ListaEnlazada<>();
        for(String etiqueta: etiquetas){
            lista.agregar(etiqueta);
        }

        Contenido contenido = new Contenido( UUID.randomUUID().toString(), titulo, descripcion, tipo, autor, lista, archivo );
        agregarContenido(contenido);

        return contenido;
    }

    public GrupoEstudio crearGrupoEstudio(String nombre, String descripcion, Estudiante creador, List<String> temas) {

        ListaEnlazada<String> lista = new ListaEnlazada<>();
        for(String tema: temas){
            lista.agregar(tema);
        }

        GrupoEstudio grupoEstudio = new GrupoEstudio( UUID.randomUUID().toString(), nombre, descripcion, lista);
        agregarGrupoEstudio(grupoEstudio);
        grupoEstudio.agregarMiembro(creador);
        return grupoEstudio;
    }

    /**
     * Crea una nueva solicitud de ayuda y la agrega al sistema
     * @param titulo Título de la solicitud (tema)
     * @param descripcion Descripción detallada de la solicitud
     * @param prioridad Nivel de prioridad/urgencia (1-5)
     * @param estudiante Estudiante que crea la solicitud
     * @return La solicitud de ayuda creada o null si hubo un error
     */
    public SolicitudAyuda crearSolicitudAyuda(String titulo, String descripcion, Integer prioridad, Estudiante estudiante) {
        if (titulo == null || descripcion == null || prioridad == null || estudiante == null) {
            return null;
        }
        
        // Crear la solicitud usando el método del estudiante
        SolicitudAyuda solicitud = estudiante.crearSolicitudAyuda(titulo, descripcion, prioridad);
        
        // Agregar la solicitud a la lista general y a la cola de prioridad
        if (solicitud != null) {
            solicitudesAyuda.agregar(solicitud);
            colaSolicitudes.insertar(solicitud, solicitud.calcularPrioridad());
        }
        
        return solicitud;
    }
}

