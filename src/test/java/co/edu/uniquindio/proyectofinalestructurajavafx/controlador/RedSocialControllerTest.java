package co.edu.uniquindio.proyectofinalestructurajavafx.controlador;

import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.Estudiante;
import co.edu.uniquindio.proyectofinalestructurajavafx.modelo.SolicitudAyuda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase RedSocialController
 */
public class RedSocialControllerTest {

    private RedSocialController redSocialController;
    private Estudiante estudiante;

    @BeforeEach
    public void setUp() {
        // Obtener la instancia del controlador
        redSocialController = RedSocialController.getInstancia();
        
        // Obtener un estudiante para las pruebas
        estudiante = (Estudiante) redSocialController.buscarUsuarioPorId("est1");
        
        // Si no existe el estudiante de prueba, creamos uno
        if (estudiante == null) {
            estudiante = new Estudiante("est-test", "Estudiante Test", "test@universidad.edu", "pass123", "Ingeniería de Sistemas", 3);
            redSocialController.agregarUsuario(estudiante);
        }
    }

    /**
     * Prueba para verificar que el método crearSolicitudAyuda funciona correctamente
     */
    @Test
    public void testCrearSolicitudAyuda() {
        // Datos de prueba
        String titulo = "Ayuda con Estructuras de Datos";
        String descripcion = "Necesito ayuda con árboles binarios de búsqueda";
        Integer prioridad = 3;
        
        // Crear la solicitud
        SolicitudAyuda solicitud = redSocialController.crearSolicitudAyuda(titulo, descripcion, prioridad, estudiante);
        
        // Verificar que la solicitud se creó correctamente
        assertNotNull(solicitud, "La solicitud no debería ser null");
        assertEquals(titulo, solicitud.getTema(), "El título/tema de la solicitud no coincide");
        assertEquals(descripcion, solicitud.getDescripcion(), "La descripción de la solicitud no coincide");
        assertEquals(prioridad.intValue(), solicitud.getNivelUrgencia(), "La prioridad de la solicitud no coincide");
        assertEquals(estudiante, solicitud.getSolicitante(), "El solicitante de la solicitud no coincide");
        
        // Verificar que la solicitud se agregó a la lista del estudiante
        assertTrue(estudiante.getSolicitudesAyuda().contiene(solicitud), 
                "La solicitud debería estar en la lista de solicitudes del estudiante");
        
        // Verificar que la solicitud se agregó a la lista general de solicitudes
        assertTrue(redSocialController.getSolicitudesAyuda().contiene(solicitud), 
                "La solicitud debería estar en la lista general de solicitudes");
    }
    
    /**
     * Prueba para verificar que el método crearSolicitudAyuda maneja correctamente los parámetros nulos
     */
    @Test
    public void testCrearSolicitudAyudaConParametrosNulos() {
        // Caso 1: Título nulo
        SolicitudAyuda solicitud1 = redSocialController.crearSolicitudAyuda(null, "Descripción", 3, estudiante);
        assertNull(solicitud1, "La solicitud debería ser null cuando el título es null");
        
        // Caso 2: Descripción nula
        SolicitudAyuda solicitud2 = redSocialController.crearSolicitudAyuda("Título", null, 3, estudiante);
        assertNull(solicitud2, "La solicitud debería ser null cuando la descripción es null");
        
        // Caso 3: Prioridad nula
        SolicitudAyuda solicitud3 = redSocialController.crearSolicitudAyuda("Título", "Descripción", null, estudiante);
        assertNull(solicitud3, "La solicitud debería ser null cuando la prioridad es null");
        
        // Caso 4: Estudiante nulo
        SolicitudAyuda solicitud4 = redSocialController.crearSolicitudAyuda("Título", "Descripción", 3, null);
        assertNull(solicitud4, "La solicitud debería ser null cuando el estudiante es null");
    }

    /**
     * Prueba para verificar la funcionalidad de marcar una solicitud como resuelta
     */
    @Test
    public void testMarcarSolicitudComoResuelta() {
        // Crear una solicitud de prueba
        String titulo = "Ayuda con Pruebas Unitarias";
        String descripcion = "Necesito ayuda con JUnit";
        Integer prioridad = 2;
        
        SolicitudAyuda solicitud = redSocialController.crearSolicitudAyuda(titulo, descripcion, prioridad, estudiante);
        assertNotNull(solicitud, "La solicitud no debería ser null");
        
        // Verificar que inicialmente no está resuelta
        assertFalse(solicitud.isResuelta(), "La solicitud no debería estar resuelta inicialmente");
        assertNull(solicitud.getFechaResolucion(), "La fecha de resolución debería ser null inicialmente");
        
        // Marcar la solicitud como resuelta
        solicitud.setResuelta(true);
        
        // Verificar que la solicitud está marcada como resuelta
        assertTrue(solicitud.isResuelta(), "La solicitud debería estar marcada como resuelta");
        assertNotNull(solicitud.getFechaResolucion(), "La fecha de resolución no debería ser null");
        
        // Marcar la solicitud como no resuelta
        solicitud.setResuelta(false);
        
        // Verificar que la solicitud vuelve a estar como no resuelta
        assertFalse(solicitud.isResuelta(), "La solicitud no debería estar resuelta");
        assertNull(solicitud.getFechaResolucion(), "La fecha de resolución debería ser null");
    }
}