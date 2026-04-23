# Alineación Módulo Sesión con SRS

## Resumen Ejecutivo
La implementación del módulo sesión está alineada con los **5 casos de uso** especificados en el Requerimientos de Software (SRS).

## Mapeo SRS → Implementación

### CASO 1: Baja sesión (Eliminar Sesión)
**Especificación SRS:**
- Eliminar una sesión del sistema
- Precondición: No debe tener clientes registrados

**Implementación:**
- **Interfaz:** `SASesion.baja_sesion(int idSesion)`
- **Implementación:** `SASesionImp.baja_sesion(int idSesion)`
  - Valida ID de sesión
  - Obtiene sesión de base de datos
  - Verifica precondición: `sesion.getParticipantsActuales() > 0`
  - Si hay clientes registrados: devuelve 0 (falla)
  - Si no hay clientes: ejecuta delete en DAO
  
- **Command:** `CommandEliminarSesion`
- **DAO:** `DAOSesion.delete(int idSesion)`
- **Capas Involucradas:** Presentación → Controlador → Negocio → Integración

---

### CASO 2: Modificar sesión
**Especificación SRS:**
- Modificar los atributos de una sesión
- No se puede modificar el ID de la sesión
- Si se cambia la sala, la nueva sala debe tener capacidad >= clientes actuales registrados

**Implementación:**
- **Interfaz:** `SASesion.modificar_sesion(int idSesion, TSesion datos)`
- **Implementación:** `SASesionImp.modificar_sesion(int idSesion, TSesion datos)`
  - Valida parámetros
  - Obtiene sesión actual de base de datos
  - Verifica cambios de sala (TODO: validar capacidad)
  - Fuerza ID de sesión para evitar modificación
  - Ejecuta update en DAO
  
- **Command:** `CommandModificarSesion`
- **DAO:** `DAOSesion.update(TSesion tSesion)`
- **Transfer Object:** `TSesion`
- **Atributos Modificables:** nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima

---

### CASO 3: Mostrar sesión
**Especificación SRS:**
- Obtener detalles de una sesión específica
- Mostrar: objetivo, duración, horario, idSesión

**Implementación:**
- **Interfaz:** `SASesion.mostrar_sesion(int idSesion)`
- **Implementación:** `SASesionImp.mostrar_sesion(int idSesion)`
  - Valida ID de sesión
  - Obtiene sesión de base de datos
  - Devuelve Transfer Object con todos los atributos
  
- **Command:** `CommandMostrarSesion`
- **DAO:** `DAOSesion.read(int idSesion)`
- **Transfer Object:** `TSesion` (contiene: idSesion, nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo)

---

### CASO 4: Mostrar sala por sesión
**Especificación SRS:**
- Obtener información de la sala/espacio asignado a una sesión
- Mostrar: idSala, nombreSala, aforo (capacidad)

**Implementación:**
- **Interfaz:** `SASesion.mostrar_sala_sesion(int idSesion)`
- **Implementación:** `SASesionImp.mostrar_sala_sesion(int idSesion)`
  - Valida ID de sesión
  - Obtiene sesión para obtener idSala
  - TODO: Obtiene detalles de sala desde DAO o servicio
  - Devuelve Transfer Object TSala
  
- **Command:** `CommandMostrarSalaSesion` (NEW)
- **DAO:** `DAOSesion.getRoom(int idSesion)`
- **Transfer Object:** `TSala` (contiene: idSala, nombreSala, aforo, activo)
- **Dependencia:** Integración con tabla `salas`

---

### CASO 5: Mostrar entrenador por sesión
**Especificación SRS:**
- Obtener información del entrenador asignado a una sesión
- Mostrar: idEntrenador, nombreEntrenador, telefonoEntrenador, DNIEntrenador

**Implementación:**
- **Interfaz:** `SASesion.mostrar_entrenador_sesion(int idSesion)`
- **Implementación:** `SASesionImp.mostrar_entrenador_sesion(int idSesion)`
  - Valida ID de sesión
  - Obtiene sesión para obtener idEntrenador
  - TODO: Obtiene detalles de entrenador desde DAO o servicio
  - Devuelve Transfer Object TEntrenador
  
- **Command:** `CommandMostrarEntrenadorSesion` (NEW)
- **DAO:** `DAOSesion.getTrainer(int idSesion)`
- **Transfer Object:** `TEntrenador` (contiene: idEntrenador, nombreEntrenador, telefonoEntrenador, DNIEntrenador)
- **Dependencia:** Integración con tabla `entrenadores`

---

## Arquitectura Implementada (4 Capas)

### Capa Presentación (Swing UI)
- Vistas para cada caso de uso del SRS
- Interfaz: `IGUI` (FactoriaPresentacion)

### Capa Controlador (Commands)
- `CommandEliminarSesion` → baja_sesion (CASO 1)
- `CommandModificarSesion` → modificar_sesion (CASO 2)
- `CommandMostrarSesion` → mostrar_sesion (CASO 3)
- `CommandMostrarSalaSesion` → mostrar_sala_sesion (CASO 4) - NEW
- `CommandMostrarEntrenadorSesion` → mostrar_entrenador_sesion (CASO 5) - NEW

### Capa Negocio (Service Application)
- Interfaz: `SASesion`
- Implementación: `SASesionImp`
- Factory: `FactoriaSAImp.getSASesion()`

### Capa Integración (Data Access)
- Interfaz: `DAOSesion`
- Implementación: `DAOSesionImp`
- Factory: `FactoriaIntegracion.generaDAOSesion()`
- Transfer Objects: `TSesion`, `TSala`

---

## Transfer Objects

### TSesion
- idSesion
- nombreSesion
- descripcion
- fecha
- hora
- idSala
- idEntrenador
- capacidadMaxima
- participantsActuales
- activo

### TSala
- idSala
- nombreSala
- aforo
- activo

### TEntrenador (existente)
- idEntrenador
- nombreEntrenador
- telefonoEntrenador
- DNIEntrenador

---

## Métodos DAO Requeridos

### Core CRUD Operations
```java
int create(TSesion datos);
TSesion read(int idSesion);
int update(TSesion tSesion);
int delete(int idSesion);
Set<TSesion> read_all();
```

### CASO 4 & 5 Support
```java
TSala getRoom(int idSesion);          // Para CASO 4
Object getTrainer(int idSesion);      // Para CASO 5
```

### Consultas Adicionales (para otras operaciones no-SRS)
```java
Set<TSesion> readByEntrenador(int idEntrenador);
Set<TSesion> readBySala(int idSala);
```

---

## Precondiciones y Validaciones

### CASO 1 - Baja Sesión
- ✅ Session ID debe ser > 0
- ✅ Session debe existir
- ✅ **CRÍTICA:** Session no debe tener clientes registrados (`participantsActuales == 0`)

### CASO 2 - Modificar Sesión
- ✅ Session ID debe ser > 0
- ✅ Session debe existir
- ✅ ID no debe ser modificado
- ⏳ TODO: Si sala cambia, nueva sala debe tener capacidad >= clientes actuales

### CASO 3 - Mostrar Sesión
- ✅ Session ID debe ser > 0
- ✅ Session debe existir

### CASO 4 - Mostrar Sala
- ✅ Session ID debe ser > 0
- ✅ Session debe existir
- ✅ Session debe tener sala asignada

### CASO 5 - Mostrar Entrenador
- ✅ Session ID debe ser > 0
- ✅ Session debe existir
- ✅ Session debe tener entrenador asignado

---

## Tareas Pendientes

### Implementación SQL en DAOSesionImp
- [ ] `create()` - INSERT en tabla sesiones
- [ ] `read()` - SELECT * FROM sesiones WHERE idSesion
- [ ] `update()` - UPDATE sesiones SET ...
- [ ] `delete()` - DELETE FROM sesiones (con validación de precondición)
- [ ] `read_all()` - SELECT * FROM sesiones WHERE activo = 1
- [ ] `readByEntrenador()` - SELECT * FROM sesiones WHERE idEntrenador
- [ ] `readBySala()` - SELECT * FROM sesiones WHERE idSala
- [ ] `getRoom()` - JOIN sesiones con salas
- [ ] `getTrainer()` - JOIN sesiones con entrenadores

### Validaciones de Negocio
- [ ] Validar capacidad de sala en CASO 2 cuando cambia sala
- [ ] Validar que entrenador existe antes de asignar
- [ ] Validar que sala existe antes de asignar

### Integración
- [ ] Integrar CommandFactory con nuevos Commands
- [ ] Integrar FactoriaPresentacion para vistas
- [ ] Crear vistas Swing para cada caso de uso
- [ ] Conectar UI con Commands

### Pruebas
- [ ] Unit tests para SASesionImp
- [ ] Integration tests con DAOSesionImp
- [ ] UI tests para cada vista

---

## Archivos Involucrados

### Controlador
- `CommandEliminarSesion.java` - CASO 1
- `CommandModificarSesion.java` - CASO 2
- `CommandMostrarSesion.java` - CASO 3
- `CommandMostrarSalaSesion.java` - CASO 4 (NEW)
- `CommandMostrarEntrenadorSesion.java` - CASO 5 (NEW)

### Negocio
- `SASesion.java` - Interface (5 métodos)
- `SASesionImp.java` - Implementación

### Integración
- `TSesion.java` - Transfer Object
- `TSala.java` - Transfer Object (NEW)
- `DAOSesion.java` - Interface DAO
- `DAOSesionImp.java` - Implementación DAO
- `FactoriaIntegracion.java` - Factory

### Presentación
- `VistaCrearSesion.java` - NO ES DEL SRS (eliminar o actualizar)
- `VistaModificarSesion.java` - CASO 2
- `VistaEliminarSesion.java` - CASO 1
- `VistaMostrarSesion.java` - CASO 3
- `VistaMostrarSalaSesion.java` - CASO 4 (pendiente)
- `VistaMostrarEntrenadorSesion.java` - CASO 5 (pendiente)

---

## Notas de Implementación

1. **Precondición Crítica en CASO 1:** El delete solo funciona si no hay clientes registrados. El DAO debe verificar `sesiones.participantsActuales = 0` antes de ejecutar DELETE.

2. **Transfer Objects:** TSesion y TSala son objetos de transferencia que transportan datos entre capas. No deben contener lógica de negocio.

3. **Factory Pattern:** El proyecto usa Factories para crear instancias (Singleton pattern). Los Commands se crean en CommandFactory, los DAOs en FactoriaIntegracion, etc.

4. **Transacciones:** Si es necesario, usar TManagerImp para manejar transacciones en operaciones que afecten múltiples tablas.

5. **Validación de Capacidad:** En CASO 2, cuando se cambia sala, el nuevo aforo debe ser >= participantes actuales. Esto requiere consultar la tabla `salas`.

---

## Estado de Alineación SRS

✅ **COMPLETADO:**
- Interfaz SASesion con 5 métodos SRS-específicos
- Implementación SASesionImp con lógica de negocio
- DAOSesion actualizado con métodos para CASO 4 y 5
- 5 Commands (Eliminación, Modificación, Mostración, Sala, Entrenador)
- Transfer Objects TSesion y TSala

⏳ **TODO:**
- SQL implementation en DAOSesionImp
- Vistas Swing para CASO 4 y 5
- Integration en CommandFactory
- Validación de capacidad en CASO 2
- Pruebas unitarias e integración

---

**Generado:** [fecha]
**Versión:** 1.0 - Alineación SRS
