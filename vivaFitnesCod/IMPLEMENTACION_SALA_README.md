# Entidad Sala - Documentación de Implementación

## Resumen
Se ha creado una nueva entidad **Sala** (Room) completamente integrada en la arquitectura del proyecto con relación **1-N con Sesión** (una sala puede tener de 0 a N sesiones).

## Atributos de Sala
- `idSala` (int): Identificador único de la sala
- `nombreSala` (String): Nombre descriptivo de la sala
- `aforo` (int): Capacidad máxima de la sala
- `activo` (int): Estado de la sala (1 = activa, 0 = inactiva)

## Transfer Object (Ya existía)
**Archivo**: `src/Integracion/FactoriaIntegracion/TSala.java`
- Clase de transferencia de datos entre capas
- Contiene todos los getters y setters
- Constructor con parámetros y constructor vacío

## Capa de Integración (Datos)

### 1. DAOSala.java (Interfaz)
**Archivo**: `src/Integracion/FactoriaIntegracion/DAOSala.java`

Métodos definidos:
- `create(TSala datos)`: Crea una nueva sala
- `read(int idSala)`: Obtiene una sala por ID
- `update(TSala tSala)`: Modifica una sala existente
- `delete(int idSala)`: Elimina una sala (solo si no tiene sesiones)
- `read_all()`: Obtiene todas las salas activas
- `readSessionsByRoom(int idSala)`: Obtiene todas las sesiones de una sala (relación 1-N)

### 2. DAOSalaImp.java (Implementación)
**Archivo**: `src/Integracion/FactoriaIntegracion/DAOSalaImp.java`

- Implementa la interfaz DAOSala
- Contiene stubs con TODOs para la implementación de BD
- Incluye comentarios SQL para cada operación
- Validaciones de precondiciones documentadas

## Capa de Negocio (Lógica)

### 3. SASala.java (Interfaz)
**Archivo**: `src/Negocio/FactoriaNegocio/SASala.java`

Casos de uso implementados:
1. **alta_sala(TSala datos)**: Crear una nueva sala
   - Valida que el nombre no esté vacío
   - Valida que el aforo sea > 0
   
2. **baja_sala(int idSala)**: Eliminar una sala
   - Precondición: La sala no debe tener sesiones activas
   - Verifica existencia de la sala
   
3. **modificar_sala(int idSala, TSala datos)**: Actualizar datos de la sala
   - Valida datos antes de actualizar
   - Verifica que la sala exista
   
4. **mostrar_sala(int idSala)**: Obtener detalles de una sala
   - Retorna null si la sala no existe
   
5. **mostrar_todas_salas()**: Listar todas las salas activas
   - Retorna Set<TSala> con todas las salas
   
6. **obtener_sesiones_sala(int idSala)**: Obtener sesiones de una sala
   - Implementa la relación 1-N con Sesión
   - Retorna Set<TSesion> con todas las sesiones de esa sala

### 4. SASalaImp.java (Implementación)
**Archivo**: `src/Negocio/FactoriaNegocio/SASalaImp.java`

- Implementa SASala con lógica de negocio completa
- Inyecta DAOSala en constructor
- Validaciones de datos antes de operaciones
- Manejo de precondiciones y restricciones

## Capa de Presentación (Controlador)

### 5. Comandos Creados

#### CommandAltaSala.java
- Crea una nueva sala
- Entrada: TSala (datos de la sala)
- Salida: ID de la sala creada o error

#### CommandBajaSala.java
- Elimina una sala
- Entrada: Integer (idSala)
- Salida: Mensaje de éxito o error
- Precondición: Sin sesiones activas

#### CommandModificarSala.java
- Modifica datos de una sala
- Entrada: Object[] {Integer idSala, TSala datos}
- Salida: Mensaje de éxito o error

#### CommandMostrarSala.java
- Obtiene detalles de una sala
- Entrada: Integer (idSala)
- Salida: TSala con los datos

#### CommandMostrarTodasSalas.java
- Lista todas las salas
- Entrada: (ninguna)
- Salida: Set<TSala> con todas las salas

#### CommandObtenerSesionesSala.java
- Obtiene todas las sesiones de una sala
- Entrada: Integer (idSala)
- Salida: Set<TSesion> con las sesiones
- Implementa relación 1-N: Sala → Sesiones

## Relación 1-N: Sala → Sesión

La relación **1-N** se implementa a través de:

1. **En TSesion**: Contiene `idSala` como clave foránea (FK)
2. **En DAOSala**: Método `readSessionsByRoom(int idSala)` que consulta todas las sesiones de una sala
3. **En SASala**: Método `obtener_sesiones_sala(int idSala)` que maneja la lógica
4. **En CommandObtenerSesionesSala**: Command para acceder a las sesiones desde el controlador

### Cambios en Sesión (TSesion)
- Ya contiene `idSala` para establecer la FK
- En DAOSesionImp ya existe `readBySala(int idSala)`

## Arquitectura por Capas

```
Presentación (UI)
    ↓
Controlador (Commands)
    ↓
Negocio (Service Application)
    ↓
Integración (DAO)
    ↓
Base de Datos
```

## Validaciones Implementadas

### Alta Sala
- Nombre no vacío
- Aforo > 0

### Baja Sala
- Sala debe existir
- Sala no debe tener sesiones activas

### Modificar Sala
- Sala debe existir
- Nombre no vacío
- Aforo > 0

### Mostrar Sala
- Verifica que la sala exista y sea activa

## Próximos Pasos (TODOs)

Los siguientes archivos tienen TODOs para la implementación de base de datos:

1. **DAOSalaImp.java**
   - Implementar métodos CRUD con SQL
   - Conectar a la base de datos
   - Implementar transacciones

2. **Presentación** (Vista)
   - Crear VistaAltaSala.java
   - Crear VistaModificarSala.java
   - Crear VistaBajaSala.java
   - Crear VistaMostrarSala.java
   - Crear VistaMostrarTodasSalas.java
   - Crear VistaObtenerSesionesSala.java

3. **CommandFactory.java**
   - Registrar los nuevos Commands en la factoría

4. **Actualizar FactoriaIntegracion**
   - Inyectar DAOSala en la factoría

5. **Actualizar FactoriaSA**
   - Inyectar SASala en la factoría

## Archivos Modificados

| Archivo | Tipo | Cambio |
|---------|------|--------|
| DAOSala.java | Interfaz | Actualizada con métodos CRUD |
| DAOSalaImp.java | Implementación | Implementada con TODOs de BD |
| SASala.java | Interfaz | Implementada con 6 casos de uso |
| SASalaImp.java | Implementación | Implementada con lógica de negocio |
| CommandAltaSala.java | Nuevo | Crear sala |
| CommandBajaSala.java | Nuevo | Eliminar sala |
| CommandModificarSala.java | Nuevo | Modificar sala |
| CommandMostrarSala.java | Nuevo | Ver sala por ID |
| CommandMostrarTodasSalas.java | Nuevo | Listar todas salas |
| CommandObtenerSesionesSala.java | Nuevo | Obtener sesiones de sala |

## Notas Importantes

1. **Relación 1-N**: Una sala puede tener 0 a N sesiones. Esto se valida en `baja_sala()` que impide eliminar una sala que tiene sesiones activas.

2. **Transfer Objects**: TSala ya existía completamente implementada con todos los atributos requeridos.

3. **Patrón de Arquitectura**: Sigue el patrón de capas y patrón Command implementado en el proyecto.

4. **Validaciones**: Las validaciones de negocio se implementan en la capa de Negocio (SASalaImp) antes de llamar al DAO.

5. **Estado Activo**: El campo `activo` se usa para soft-delete (marcar como inactivo sin borrar de BD).
