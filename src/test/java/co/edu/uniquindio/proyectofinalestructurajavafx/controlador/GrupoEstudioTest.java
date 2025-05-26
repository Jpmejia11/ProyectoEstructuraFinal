package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.estructuras.ListaEnlazada;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.GrupoEstudio;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Moderador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la funcionalidad de gestión de grupos de estudio
 */
public class GrupoEstudioTest {

    private RedSocialController redSocialController;
    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Moderador moderador;
    private GrupoEstudio grupo1;
    private GrupoEstudio grupo2;
    private GrupoEstudio grupo3;

    @BeforeEach
    public void setUp() {
        // Inicializar el controlador
        redSocialController = new RedSocialController();
        
        // Crear usuarios de prueba
        estudiante1 = new Estudiante("est-test1", "Estudiante Test 1", "est1@test.com", "pass123", "Ingeniería de Sistemas", 3);
        estudiante1.agregarInteres("Programación");
        estudiante1.agregarInteres("Bases de Datos");
        estudiante2 = new Estudiante("est-test2", "Estudiante Test 2", "est2@test.com", "pass123", "Ingeniería de Software", 5);
        estudiante2.agregarInteres("Desarrollo Web");
        estudiante2.agregarInteres("Programación");
        moderador = new Moderador("mod-test", "Moderador Test", "mod@test.com", "pass123", "Administrador");
        
        // Agregar intereses a los estudiantes
        estudiante1.agregarInteres("Programación");
        estudiante1.agregarInteres("Bases de Datos");
        estudiante2.agregarInteres("Desarrollo Web");
        estudiante2.agregarInteres("Programación");
        
        // Crear grupos de estudio
        ListaEnlazada<String> temasGrupo1 = new ListaEnlazada<>();
        temasGrupo1.agregar("Programación");
        temasGrupo1.agregar("Java");
        
        ListaEnlazada<String> temasGrupo2 = new ListaEnlazada<>();
        temasGrupo2.agregar("Bases de Datos");
        temasGrupo2.agregar("SQL");
        
        ListaEnlazada<String> temasGrupo3 = new ListaEnlazada<>();
        temasGrupo3.agregar("Desarrollo Web");
        temasGrupo3.agregar("JavaScript");
        
        grupo1 = new GrupoEstudio("grupo-test1", "Grupo de Programación", "Grupo para estudiar programación", temasGrupo1);
        grupo2 = new GrupoEstudio("grupo-test2", "Grupo de Bases de Datos", "Grupo para estudiar bases de datos", temasGrupo2);
        grupo3 = new GrupoEstudio("grupo-test3", "Grupo de Desarrollo Web", "Grupo para estudiar desarrollo web", temasGrupo3);
    }

    @Test
    public void testUnirseAGrupo() {
        // Verificar que el estudiante no es miembro del grupo inicialmente
        assertFalse(grupo1.esMiembro(estudiante1));
        
        // Unir al estudiante al grupo
        grupo1.agregarMiembro(estudiante1);
        estudiante1.agregarGrupoEstudio(grupo1);
        
        // Verificar que el estudiante es miembro del grupo
        assertTrue(grupo1.esMiembro(estudiante1));
        assertTrue(estudiante1.getGruposEstudio().contiene(grupo1));
        
        // Verificar que el número de miembros del grupo es 1
        assertEquals(1, grupo1.obtenerNumeroMiembros());
    }

    @Test
    public void testSalirDeGrupo() {
        // Primero unir al estudiante al grupo
        grupo1.agregarMiembro(estudiante1);
        estudiante1.agregarGrupoEstudio(grupo1);
        
        // Verificar que el estudiante es miembro del grupo
        assertTrue(grupo1.esMiembro(estudiante1));
        
        // Hacer que el estudiante salga del grupo
        grupo1.eliminarMiembro(estudiante1);
        estudiante1.eliminarGrupoEstudio(grupo1);
        
        // Verificar que el estudiante ya no es miembro del grupo
        assertFalse(grupo1.esMiembro(estudiante1));
        assertFalse(estudiante1.getGruposEstudio().contiene(grupo1));
        
        // Verificar que el número de miembros del grupo es 0
        assertEquals(0, grupo1.obtenerNumeroMiembros());
    }

    @Test
    public void testActivarDesactivarGrupo() {
        // Verificar que el grupo está activo por defecto
        assertTrue(grupo1.isActivo());
        
        // Desactivar el grupo
        grupo1.setActivo(false);
        
        // Verificar que el grupo está inactivo
        assertFalse(grupo1.isActivo());
        
        // Activar el grupo
        grupo1.setActivo(true);
        
        // Verificar que el grupo está activo nuevamente
        assertTrue(grupo1.isActivo());
    }

    @Test
    public void testNoUnirseAGrupoInactivo() {
        // Desactivar el grupo
        grupo1.setActivo(false);
        
        // Intentar unir al estudiante al grupo (simulando la lógica del controlador)
        if (grupo1.isActivo()) {
            grupo1.agregarMiembro(estudiante1);
            estudiante1.agregarGrupoEstudio(grupo1);
        }
        
        // Verificar que el estudiante no se unió al grupo
        assertFalse(grupo1.esMiembro(estudiante1));
        assertFalse(estudiante1.getGruposEstudio().contiene(grupo1));
    }

    @Test
    public void testCalcularAfinidad() {
        // Calcular afinidad entre el grupo1 y el estudiante1
        double afinidad1 = grupo1.calcularAfinidad(estudiante1);
        
        // El estudiante1 tiene el interés "Programación" que coincide con el grupo1
        // La afinidad debería ser 50% (1 de 2 temas)
        assertEquals(50.0, afinidad1);
        
        // Calcular afinidad entre el grupo2 y el estudiante1
        double afinidad2 = grupo2.calcularAfinidad(estudiante1);
        
        // El estudiante1 tiene el interés "Bases de Datos" que coincide con el grupo2
        // La afinidad debería ser 50% (1 de 2 temas)
        assertEquals(50.0, afinidad2);
        
        // Calcular afinidad entre el grupo1 y el estudiante2
        double afinidad3 = grupo1.calcularAfinidad(estudiante2);
        
        // El estudiante2 tiene el interés "Programación" que coincide con el grupo1
        // La afinidad debería ser 50% (1 de 2 temas)
        assertEquals(50.0, afinidad3);
    }

    @Test
    public void testGestionGruposPorModerador() {
        // Agregar miembros al grupo
        grupo1.agregarMiembro(estudiante1);
        grupo1.agregarMiembro(estudiante2);
        
        // Verificar que ambos estudiantes son miembros del grupo
        assertTrue(grupo1.esMiembro(estudiante1));
        assertTrue(grupo1.esMiembro(estudiante2));
        assertEquals(2, grupo1.obtenerNumeroMiembros());
        
        // Simular que el moderador elimina a un miembro
        grupo1.eliminarMiembro(estudiante1);
        
        // Verificar que el estudiante1 ya no es miembro pero el estudiante2 sí
        assertFalse(grupo1.esMiembro(estudiante1));
        assertTrue(grupo1.esMiembro(estudiante2));
        assertEquals(1, grupo1.obtenerNumeroMiembros());
        
        // Simular que el moderador desactiva el grupo
        grupo1.setActivo(false);
        
        // Verificar que el grupo está inactivo
        assertFalse(grupo1.isActivo());
    }
    
    @Test
    public void testCrearGrupoConTemas() {
        // Crear un grupo con temas específicos
        List<String> temas = Arrays.asList("Java", "Spring", "Hibernate");
        GrupoEstudio grupo = redSocialController.crearGrupoEstudio("Grupo Java", "Grupo para estudiar Java y frameworks", estudiante1, temas);
        
        // Verificar que el grupo se creó correctamente
        assertNotNull(grupo);
        assertEquals("Grupo Java", grupo.getNombre());
        assertEquals("Grupo para estudiar Java y frameworks", grupo.getDescripcion());
        
        // Verificar que los temas se agregaron correctamente
        assertTrue(grupo.getTemas().contiene("Java"));
        assertTrue(grupo.getTemas().contiene("Spring"));
        assertTrue(grupo.getTemas().contiene("Hibernate"));
        
        // Verificar que el creador es miembro del grupo
        assertTrue(grupo.esMiembro(estudiante1));
    }
    
    @Test
    public void testCrearGrupoSinTemas() {
        // Crear un grupo sin temas (debería usar "General" como tema predeterminado)
        List<String> temas = new ArrayList<>();
        GrupoEstudio grupo = redSocialController.crearGrupoEstudio("Grupo Sin Temas", "Grupo sin temas específicos", estudiante1, temas);
        
        // Verificar que el grupo se creó correctamente
        assertNotNull(grupo);
        assertEquals("Grupo Sin Temas", grupo.getNombre());
        
        // Verificar que el creador es miembro del grupo
        assertTrue(grupo.esMiembro(estudiante1));
    }
    
    @Test
    public void testUnirseAMultiplesGrupos() {
        // Unir al estudiante a varios grupos
        grupo1.agregarMiembro(estudiante1);
        estudiante1.agregarGrupoEstudio(grupo1);
        
        grupo2.agregarMiembro(estudiante1);
        estudiante1.agregarGrupoEstudio(grupo2);
        
        grupo3.agregarMiembro(estudiante1);
        estudiante1.agregarGrupoEstudio(grupo3);
        
        // Verificar que el estudiante es miembro de todos los grupos
        assertTrue(grupo1.esMiembro(estudiante1));
        assertTrue(grupo2.esMiembro(estudiante1));
        assertTrue(grupo3.esMiembro(estudiante1));
        
        // Verificar que el estudiante tiene todos los grupos en su lista
        assertTrue(estudiante1.getGruposEstudio().contiene(grupo1));
        assertTrue(estudiante1.getGruposEstudio().contiene(grupo2));
        assertTrue(estudiante1.getGruposEstudio().contiene(grupo3));
        
        // Verificar que el número de grupos del estudiante es 3
        assertEquals(3, estudiante1.getGruposEstudio().tamaño());
    }
}