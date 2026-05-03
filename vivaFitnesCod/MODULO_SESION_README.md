# Módulo de Sesión VivaFitness - Guía de Implementación

## 📋 Descripción General

Se ha implementado un módulo completo de **Gestión de Sesiones** para VivaFitness siguiendo la arquitectura de 4 capas del proyecto:
- **Presentación**: Interfaz gráfica
- **Controlador**: Commands (Patrón Command)
- **Negocio**: Lógica de aplicación (Service Application)
- **Integración**: Acceso a datos (DAO)

---

## 📁 Estructura de Carpetas

```
src/
├── Controlador/
│   ├── CommandCrearSesion.java
│   ├── CommandModificarSesion.java
│   ├── CommandEliminarSesion.java
│   ├── CommandMostrarSesion.java
│   ├── CommandMostrarTodasSesiones.java
│   ├── CommandApuntarSesion.java
│   ├── CommandDesapuntarSesion.java
│   └── CommandEspaciosSesion.java
├── Integracion/
│   └── FactoriaIntegracion/
│       ├── TSesion.java (Transfer Object)
│       ├── DAOSesion.java (Interface)
│       └── DAOSesionImp.java (Implementación)
├── Negocio/
│   ├── FactoriaNegocio/
│   │   ├── SASesion.java (Interface)
│   │   └── SASesionImp.java (Implementación)
│   └── Cliente/
│       └── SAClienteImp.java (ACTUALIZADO)
└── Presentacion/
    └── Entrenador/
        ├── VistaCrearSesion.java (ACTUALIZADA)
        ├── VistaModificarSesion.java
        ├── VistaEliminarSesion.java
        ├── VistaMostrarSesion.java
        ├── VistaMostrarTodasSesiones.java
        ├── VistaApuntarSesion.java
        └── VistaDesapuntarSesion.java
```

---

## 🔧 Componentes Principales

### TSesion (Transfer Object)
```java
// Atributos principales
idSesion              // int - ID único
nombreSesion          // String - Nombre de la sesión
descripcion           // String - Descripción
fecha                 // Date - Fecha de la sesión
hora                  // String - Hora (ej: "10:00")
idSala                // int - ID de la sala
idEntrenador          // int - ID del entrenador
capacidadMaxima       // int - Capacidad máxima
participantsActuales  // int - Participantes actuales
activo                // int - Estado (1=activo, 0=inactivo)
```

### SASesion (Service Application Interface)
Proporciona operaciones de negocio:
- `alta_sesion()` - Crear sesión
- `baja_sesion()` - Eliminar sesión
- `modificar_sesion()` - Actualizar sesión
- `mostrar_sesion()` - Obtener detalles
- `mostrar_todas_sesiones()` - Listar todas
- `apuntar_cliente_sesion()` - Registrar cliente
- `desapuntar_cliente_sesion()` - Desregistrar cliente
- `espacios_disponibles()` - Verificar capacidad

### DAOSesion (Data Access Object)
Operaciones CRUD básicas:
- `create()`, `read()`, `update()`, `delete()`
- `read_all()`
- `readByEntrenador()`
- `readBySala()`

---

## 🚀 Pasos para Completar la Implementación

### Paso 1: Implementar la Base de Datos

Crear las tablas necesarias:

```sql
-- Tabla de Sesiones
CREATE TABLE sesiones (
    idSesion INT AUTO_INCREMENT PRIMARY KEY,
    nombreSesion VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    idSala INT NOT NULL,
    idEntrenador INT NOT NULL,
    capacidadMaxima INT NOT NULL,
    participantsActuales INT DEFAULT 0,
    activo INT DEFAULT 1,
    FOREIGN KEY (idSala) REFERENCES salas(idSala),
    FOREIGN KEY (idEntrenador) REFERENCES entrenadores(idEntrenador)
);

-- Tabla de relación Cliente-Sesión
CREATE TABLE cliente_sesion (
    idClienteSesion INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    idSesion INT NOT NULL,
    fechaApunte DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
    FOREIGN KEY (idSesion) REFERENCES sesiones(idSesion)
);
```

### Paso 2: Implementar DAOSesionImp

Completar los métodos con lógica de BD:

```java
public class DAOSesionImp implements DAOSesion {
    private Connection connection;
    
    public DAOSesionImp() {
        // Obtener conexión a BD
        connection = TransactionFactory.getInstance().getTransaction();
    }
    
    @Override
    public int create(TSesion datos) {
        String sql = "INSERT INTO sesiones (...) VALUES (...)";
        // Ejecutar INSERT
        // Retornar ID generado
        return 0;
    }
    
    // Implementar resto de métodos...
}
```

### Paso 3: Integrar CommandFactory

Actualizar [CommandFactory.java](CommandFactory.java) para reconocer los nuevos Commands:

```java
public class CommandFactory {
    public static Command createCommand(String commandName) {
        switch(commandName) {
            case "crearSesion":
                return new CommandCrearSesion(saSesion);
            case "modificarSesion":
                return new CommandModificarSesion(saSesion);
            case "eliminarSesion":
                return new CommandEliminarSesion(saSesion);
            // ... más comandos
            default:
                return null;
        }
    }
}
```

### Paso 4: Conectar Vistas con Controller

En cada Vista, implementar listeners para botones:

```java
// En VistaCrearSesion
public void connectToController(CommandFactory factory) {
    btnCrear.addActionListener(e -> {
        Command cmd = factory.createCommand("crearSesion");
        Context ctx = cmd.execute(getSessionData());
        update(ctx);
    });
}
```

### Paso 5: Actualizar VistaEntrenador Principal

Agregar botones y eventos para acceder a las vistas de sesión:

```java
// En VistaEntrenador
private void createSessionMenuItems() {
    // Agregar menú o botones para:
    // - Crear Sesión
    // - Modificar Sesión
    // - Eliminar Sesión
    // - Ver Sesiones
    // - Apuntar Cliente
    // - Desapuntar Cliente
}
```

### Paso 6: Implementar Manejo de Transacciones

En DAOSesionImp, usar TManager para consistencia:

```java
public int create(TSesion datos) {
    TManager tManager = TransactionManager.getInstance();
    Transaction trans = tManager.beginTransaction();
    try {
        // Insertar sesión
        // Retornar resultado
        trans.commit();
        return resultado;
    } catch (Exception e) {
        trans.rollback();
        return 0;
    }
}
```

### Paso 7: Agregar Tests Unitarios

Crear pruebas para validar la funcionalidad:

```java
public class SASesionTest {
    private SASesion saSesion;
    
    @Before
    public void setUp() {
        DAOSesion daoSesion = new DAOSesionImp();
        saSesion = new SASesionImp(daoSesion);
    }
    
    @Test
    public void testAltaSesion() {
        TSesion sesion = new TSesion();
        sesion.setNombreSesion("Yoga");
        int resultado = saSesion.alta_sesion(sesion);
        assertTrue(resultado > 0);
    }
}
```

---

## 💡 Ejemplos de Uso

### Crear una Sesión
```java
// Desde la Vista
TSesion nuevaSesion = vistaCrearSesion.getSessionData();

// Command
Command cmd = new CommandCrearSesion(saSesion);
Context resultado = cmd.execute(nuevaSesion);

if (resultado.isSuccess()) {
    System.out.println("Sesión creada: " + resultado.getData());
}
```

### Apuntar Cliente a Sesión
```java
Object[] params = {idSesion, idCliente};
Command cmd = new CommandApuntarSesion(saSesion);
Context resultado = cmd.execute(params);
```

### Obtener Todas las Sesiones
```java
Command cmd = new CommandMostrarTodasSesiones(saSesion);
Context resultado = cmd.execute(null);

if (resultado.isSuccess()) {
    Set<TSesion> sesiones = (Set<TSesion>) resultado.getData();
    vistaMostrarTodas.displaySessions(formatSessions(sesiones));
}
```

---

## 🔍 Notas Importantes

1. **Validaciones**: La capa de negocio ya incluye validaciones básicas
2. **Capacidad**: Se controla automáticamente que no supere capacidad máxima
3. **Estado**: Las sesiones pueden estar activas (1) o inactivas (0)
4. **Transfer Object**: TSesion es serializable, seguro para transmitir
5. **Factorías**: Usan patrón Singleton para garantizar instancia única

---

## 🐛 Troubleshooting

### Error: "Cannot find symbol DAOSesion"
✅ Verificar que DAOSesion.java está en `Integracion/FactoriaIntegracion/`

### Error: NullPointerException en SASesionImp
✅ Verificar que se pasó DAOSesion válido en constructor

### Las vistas no se muestran
✅ Verificar que los botones tengan ActionListeners conectados

---

## 📚 Recursos Adicionales

- Documentación de patrones: ver `agent-customization` skill
- Estructura del proyecto: revisar otros módulos (Cliente, Entrenador)
- Transacciones: consultar `Integracion/Transaction/`

---

## ✅ Checklist de Implementación

- [ ] Base de datos creada (tablas sesiones y cliente_sesion)
- [ ] DAOSesionImp implementado con lógica de BD
- [ ] CommandFactory actualizado con nuevos Commands
- [ ] Vistas conectadas al Controller
- [ ] VistaEntrenador principal con menú de sesiones
- [ ] Transacciones configuradas
- [ ] Tests unitarios creados y pasados
- [ ] Documentación actualizada
- [ ] Sistema compilado sin errores

---

**Fecha de Creación**: 2026-04-23
**Versión**: 1.0
**Autor**: VivaFitness Development Team
