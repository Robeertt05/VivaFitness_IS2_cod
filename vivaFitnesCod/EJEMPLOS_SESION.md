# Ejemplos de Uso - Módulo Sesión VivaFitness

## 1. Crear una Nueva Sesión

### 1.1 Desde la Vista
```java
// En VistaCrearSesion
VistaCrearSesion vista = new VistaCrearSesion();
vista.setVisible(true);

// Usuario completa el formulario y presiona "Crear"
```

### 1.2 Obtener datos de la Vista
```java
// El listener del botón obtiene los datos
TSesion sesion = vista.getSessionData();
// Retorna:
// {
//   nombreSesion: "Yoga Matutino",
//   descripcion: "Sesión de yoga relajante",
//   hora: "10:00",
//   capacidadMaxima: 20,
//   idSala: 1,
//   idEntrenador: 5,
//   ...
// }
```

### 1.3 Ejecutar el Command
```java
// Crear instancia de SASesion
DAOSesion dao = FactoriaIntegracion.getInstance().generaDAOSesion();
SASesion saSesion = new SASesionImp(dao);

// Crear y ejecutar Command
Command cmd = new CommandCrearSesion(saSesion);
Context resultado = cmd.execute(sesion);

// Procesar resultado
if (resultado.isSuccess()) {
    int nuevoIdSesion = (Integer) resultado.getData();
    System.out.println("Sesión creada con ID: " + nuevoIdSesion);
    vista.update(resultado); // Cierra ventana
} else {
    System.out.println("Error: " + resultado.getMessage());
}
```

---

## 2. Modificar una Sesión Existente

### 2.1 Flujo completo
```java
VistaModificarSesion vista = new VistaModificarSesion();

// Cargar lista de sesiones en ComboBox
Set<TSesion> todasSesiones = saSesion.mostrar_todas_sesiones();
for (TSesion s : todasSesiones) {
    vista.cbSesion.addItem(s.getIdSesion());
}

// Usuario selecciona sesión y edita campos
vista.addModifyButtonListener(e -> {
    int idSesion = vista.getSelectedSessionId();
    TSesion datosParciales = vista.getSessionData();
    
    Object[] params = {idSesion, datosParciales};
    Command cmd = new CommandModificarSesion(saSesion);
    Context resultado = cmd.execute(params);
    
    vista.update(resultado);
});

vista.setVisible(true);
```

---

## 3. Eliminar una Sesión

### 3.1 Implementación simple
```java
VistaEliminarSesion vista = new VistaEliminarSesion();

// Cargar sesiones disponibles
actualizarListaSesiones(vista);

// Listener para eliminar
vista.addDeleteButtonListener(e -> {
    int idSesion = vista.getSelectedSessionId();
    
    Command cmd = new CommandEliminarSesion(saSesion);
    Context resultado = cmd.execute(idSesion);
    
    if (resultado.isSuccess()) {
        actualizarListaSesiones(vista); // Refrescar lista
        JOptionPane.showMessageDialog(vista, "Sesión eliminada");
    }
});

vista.setVisible(true);
```

---

## 4. Mostrar Detalles de una Sesión

### 4.1 Con actualización de datos
```java
VistaMostrarSesion vista = new VistaMostrarSesion();

// Cargar lista de sesiones
cargarSesionesEnComboBox(vista);

// Listener para mostrar detalles
vista.addShowButtonListener(e -> {
    int idSesion = vista.getSelectedSessionId();
    
    Command cmd = new CommandMostrarSesion(saSesion);
    Context resultado = cmd.execute(idSesion);
    
    if (resultado.isSuccess()) {
        TSesion sesion = (TSesion) resultado.getData();
        String detalles = formatearDetallesSesion(sesion);
        vista.displaySessionDetails(detalles);
    }
});

vista.setVisible(true);
```

### 4.2 Método auxiliar para formatear
```java
private String formatearDetallesSesion(TSesion sesion) {
    StringBuilder sb = new StringBuilder();
    sb.append("ID Sesión: ").append(sesion.getIdSesion()).append("\n");
    sb.append("Nombre: ").append(sesion.getNombreSesion()).append("\n");
    sb.append("Descripción: ").append(sesion.getDescripcion()).append("\n");
    sb.append("Fecha: ").append(sesion.getFecha()).append("\n");
    sb.append("Hora: ").append(sesion.getHora()).append("\n");
    sb.append("Sala: ").append(sesion.getIdSala()).append("\n");
    sb.append("Entrenador: ").append(sesion.getIdEntrenador()).append("\n");
    sb.append("Capacidad: ").append(sesion.getCapacidadMaxima()).append("\n");
    sb.append("Participantes: ").append(sesion.getParticipantsActuales()).append("\n");
    sb.append("Espacios libres: ").append(
        sesion.getCapacidadMaxima() - sesion.getParticipantsActuales()
    ).append("\n");
    sb.append("Estado: ").append(sesion.getActivo() == 1 ? "Activa" : "Inactiva");
    return sb.toString();
}
```

---

## 5. Listar Todas las Sesiones

### 5.1 Mostrar lista completa
```java
VistaMostrarTodasSesiones vista = new VistaMostrarTodasSesiones();

vista.addRefreshButtonListener(e -> {
    Command cmd = new CommandMostrarTodasSesiones(saSesion);
    Context resultado = cmd.execute(null);
    
    vista.update(resultado); // Actualiza automáticamente
});

vista.setVisible(true);
```

### 5.2 Implementación en Vista
```java
@Override
public void update(Context context) {
    if (context != null && context.getData() instanceof Set) {
        Set<?> sesiones = (Set<?>) context.getData();
        StringBuilder sb = new StringBuilder();
        
        for (Object sesion : sesiones) {
            sb.append(sesion.toString()).append("\n");
            sb.append("-".repeat(50)).append("\n");
        }
        
        displaySessions(sb.toString());
    }
}
```

---

## 6. Apuntar Cliente a Sesión

### 6.1 Registrar cliente
```java
VistaApuntarSesion vista = new VistaApuntarSesion();

// Cargar opciones
cargarSesionesDisponibles(vista);
cargarClientes(vista);

vista.addRegisterButtonListener(e -> {
    int idSesion = vista.getSelectedSessionId();
    int idCliente = vista.getSelectedClientId();
    
    // Validar capacidad antes de apuntar
    Command cmdEspacios = new CommandEspaciosSesion(saSesion);
    Context ctxEspacios = cmdEspacios.execute(idSesion);
    
    if (ctxEspacios.isSuccess() && (Integer) ctxEspacios.getData() > 0) {
        // Hay espacio disponible
        Object[] params = {idSesion, idCliente};
        Command cmd = new CommandApuntarSesion(saSesion);
        Context resultado = cmd.execute(params);
        
        if (resultado.isSuccess()) {
            JOptionPane.showMessageDialog(vista, "Cliente apuntado correctamente");
            vista.update(resultado);
        } else {
            JOptionPane.showMessageDialog(vista, "Error: " + resultado.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(vista, "Sesión llena, no hay espacios disponibles");
    }
});

vista.setVisible(true);
```

---

## 7. Desapuntar Cliente de Sesión

### 7.1 Desregistrar cliente
```java
VistaDesapuntarSesion vista = new VistaDesapuntarSesion();

cargarSesionesActivas(vista);
cargarClientesApuntados(vista);

vista.addUnregisterButtonListener(e -> {
    int idSesion = vista.getSelectedSessionId();
    int idCliente = vista.getSelectedClientId();
    
    Object[] params = {idSesion, idCliente};
    Command cmd = new CommandDesapuntarSesion(saSesion);
    Context resultado = cmd.execute(params);
    
    if (resultado.isSuccess()) {
        JOptionPane.showMessageDialog(vista, "Cliente desapuntado");
        cargarClientesApuntados(vista); // Refrescar
        vista.update(resultado);
    }
});

vista.setVisible(true);
```

---

## 8. Verificar Espacios Disponibles

### 8.1 Consultar capacidad
```java
Command cmd = new CommandEspaciosSesion(saSesion);
Context resultado = cmd.execute(idSesion);

if (resultado.isSuccess()) {
    int espacios = (Integer) resultado.getData();
    System.out.println("Espacios disponibles: " + espacios);
    
    if (espacios == 0) {
        System.out.println("Sesión llena");
    } else if (espacios < 5) {
        System.out.println("Pocos espacios disponibles");
    }
}
```

---

## 9. Integración Completa en VistaEntrenador

### 9.1 Menú principal
```java
public class VistaEntrenador extends JFrame implements IGUI {
    
    private JButton btnCrearSesion;
    private JButton btnModificarSesion;
    private JButton btnEliminarSesion;
    private JButton btnVerSesiones;
    private JButton btnApuntarCliente;
    private JButton btnDesapuntarCliente;
    
    private SASesion saSesion;
    
    public VistaEntrenador(SASesion saSesion) {
        this.saSesion = saSesion;
        inicializarUI();
        conectarListeners();
    }
    
    private void conectarListeners() {
        btnCrearSesion.addActionListener(e -> {
            VistaCrearSesion vista = new VistaCrearSesion();
            conectarVista(vista, "crearSesion");
            vista.setVisible(true);
        });
        
        btnModificarSesion.addActionListener(e -> {
            VistaModificarSesion vista = new VistaModificarSesion();
            conectarVista(vista, "modificarSesion");
            vista.setVisible(true);
        });
        
        // ... más botones ...
    }
    
    private void conectarVista(IGUI vista, String commandType) {
        // Implementar lógica de conexión
    }
    
    @Override
    public void update(Context context) {
        if (context != null) {
            System.out.println("Resultado: " + context.getMessage());
        }
    }
}
```

---

## 10. Manejo de Errores

### 10.1 Try-Catch recomendado
```java
try {
    Command cmd = new CommandCrearSesion(saSesion);
    Context resultado = cmd.execute(sesion);
    
    if (!resultado.isSuccess()) {
        log.error("Error de negocio: " + resultado.getMessage());
        mostrarError("No se pudo crear la sesión: " + resultado.getMessage());
    } else {
        log.info("Sesión creada: " + resultado.getData());
        mostrarExito("Sesión creada correctamente");
    }
} catch (Exception e) {
    log.error("Error inesperado", e);
    mostrarError("Error inesperado: " + e.getMessage());
}
```

---

## 11. Testing

### 11.1 Test unitario
```java
@Test
public void testAltaSesion() {
    // Arrange
    TSesion sesion = new TSesion();
    sesion.setNombreSesion("Yoga");
    sesion.setCapacidadMaxima(20);
    sesion.setIdSala(1);
    sesion.setIdEntrenador(5);
    
    // Act
    int resultado = saSesion.alta_sesion(sesion);
    
    // Assert
    assertTrue(resultado > 0, "Debe retornar ID válido");
}

@Test
public void testCapacidadMáxima() {
    // Arrange
    int idSesion = 1;
    TSesion sesion = saSesion.mostrar_sesion(idSesion);
    
    // Act
    int espacios = saSesion.espacios_disponibles(idSesion);
    
    // Assert
    assertTrue(espacios >= 0, "Espacios no puede ser negativo");
    assertTrue(espacios <= sesion.getCapacidadMaxima(), 
               "Espacios no puede exceder capacidad");
}
```

---

## Tips y Buenas Prácticas

✅ **DO:**
- Validar datos en la capa de negocio
- Usar Commands para todas las operaciones
- Cerrar conexiones en finally
- Implementar logging
- Usar patrón Factory para crear objetos

❌ **DON'T:**
- Acceder directamente a DAOSesion desde Vistas
- Poner lógica de negocio en Presentación
- Olvidar validar capacidad al apuntar
- No documentar métodos públicos
- Hardcodear valores en el código

---

**Última actualización**: 2026-04-23
