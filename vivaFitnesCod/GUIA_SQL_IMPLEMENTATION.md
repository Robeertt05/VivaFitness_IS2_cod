# Guía SQL Implementation - Módulo Sesión

## ⚡ Inicio Rápido

**Objetivo:** Implementar 9 métodos SQL en `DAOSesionImp.java`

**Archivo a editar:** `src/Integracion/FactoriaIntegracion/DAOSesionImp.java`

**Duración estimada:** 2-3 horas

---

## 📋 Lista de Métodos a Implementar

### 1️⃣ `create()` - Crear nueva sesión
**SQL:**
```sql
INSERT INTO sesiones (nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo)
VALUES (?, ?, ?, ?, ?, ?, ?, 0, 1)
```
**Retorna:** ID de sesión (lastInsertId) o 0

---

### 2️⃣ `read()` - Obtener sesión por ID
**SQL:**
```sql
SELECT idSesion, nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo
FROM sesiones
WHERE idSesion = ? AND activo = 1
```
**Retorna:** TSesion o null

---

### 3️⃣ `update()` - Actualizar sesión existente
**SQL:**
```sql
UPDATE sesiones 
SET nombreSesion=?, descripcion=?, fecha=?, hora=?, idSala=?, idEntrenador=?, capacidadMaxima=?
WHERE idSesion = ?
```
**Retorna:** 1 si éxito, 0 si fallo

---

### 4️⃣ `delete()` - Eliminar sesión (con precondición)
**SQL:**
```sql
-- Paso 1: Verificar que NO tenga clientes registrados
SELECT COUNT(*) FROM cliente_sesion 
WHERE idSesion = ? AND activo = 1

-- Si COUNT = 0, entonces:
DELETE FROM sesiones WHERE idSesion = ?
```
**Retorna:** 1 si éxito, 0 si fallo o tiene clientes

---

### 5️⃣ `read_all()` - Obtener todas las sesiones activas
**SQL:**
```sql
SELECT idSesion, nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo
FROM sesiones
WHERE activo = 1
```
**Retorna:** Set<TSesion>

---

### 6️⃣ `readByEntrenador()` - Sesiones de un entrenador
**SQL:**
```sql
SELECT idSesion, nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo
FROM sesiones
WHERE idEntrenador = ? AND activo = 1
```
**Retorna:** Set<TSesion>

---

### 7️⃣ `readBySala()` - Sesiones en una sala
**SQL:**
```sql
SELECT idSesion, nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo
FROM sesiones
WHERE idSala = ? AND activo = 1
```
**Retorna:** Set<TSesion>

---

### 8️⃣ `getRoom()` - Obtener sala de una sesión (CASO 4)
**SQL:**
```sql
SELECT sa.idSala, sa.nombreSala, sa.aforo, sa.activo
FROM sesiones s 
JOIN salas sa ON s.idSala = sa.idSala
WHERE s.idSesion = ? AND s.activo = 1
```
**Retorna:** TSala o null

---

### 9️⃣ `getTrainer()` - Obtener entrenador de una sesión (CASO 5)
**SQL:**
```sql
SELECT e.idEntrenador, e.nombreEntrenador, e.telefonoEntrenador, e.DNIEntrenador
FROM sesiones s 
JOIN entrenadores e ON s.idEntrenador = e.idEntrenador
WHERE s.idSesion = ? AND s.activo = 1
```
**Retorna:** TEntrenador o null

---

## 📊 Esquema de Base de Datos

### Tabla: `sesiones`
```sql
CREATE TABLE sesiones (
    idSesion INT PRIMARY KEY AUTO_INCREMENT,
    nombreSesion VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    idSala INT NOT NULL,
    idEntrenador INT NOT NULL,
    capacidadMaxima INT NOT NULL,
    participantsActuales INT DEFAULT 0,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (idSala) REFERENCES salas(idSala),
    FOREIGN KEY (idEntrenador) REFERENCES entrenadores(idEntrenador)
);
```

### Tabla: `cliente_sesion` (Relación)
```sql
CREATE TABLE cliente_sesion (
    idClienteSesion INT PRIMARY KEY AUTO_INCREMENT,
    idCliente INT NOT NULL,
    idSesion INT NOT NULL,
    fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
    FOREIGN KEY (idSesion) REFERENCES sesiones(idSesion)
);
```

---

## 🔌 Patrón de Implementación

### Template para cada método:
```java
@Override
public [RETURN_TYPE] [METHOD_NAME]([PARAMETERS]) {
    // begin-user-code
    
    // Paso 1: Validar parámetros
    if (param <= 0) {
        return null/0;
    }
    
    // Paso 2: Crear conexión y statement
    // Use TManagerImp para transacciones si necesario
    
    // Paso 3: Ejecutar SQL
    // PreparedStatement con parámetros
    
    // Paso 4: Procesar resultados
    // Mapear resultados a Transfer Objects
    
    // Paso 5: Retornar resultado
    return result;
    
    // end-user-code
}
```

---

## 💾 Patrón de Conexión

```java
// Usando TManagerImp (recomendado)
TManagerImp tManager = new TManagerImp();
Connection conn = tManager.getConnection();

try {
    PreparedStatement pstmt = conn.prepareStatement("SELECT ...");
    pstmt.setInt(1, idSesion);
    ResultSet rs = pstmt.executeQuery();
    
    // Procesar resultados
    
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    tManager.close();
}
```

---

## 📝 Patrón de Mapeo a Transfer Objects

### Crear TSesion desde ResultSet:
```java
TSesion sesion = new TSesion();
sesion.setIdSesion(rs.getInt("idSesion"));
sesion.setNombreSesion(rs.getString("nombreSesion"));
sesion.setDescripcion(rs.getString("descripcion"));
sesion.setFecha(rs.getDate("fecha"));
sesion.setHora(rs.getTime("hora"));
sesion.setIdSala(rs.getInt("idSala"));
sesion.setIdEntrenador(rs.getInt("idEntrenador"));
sesion.setCapacidadMaxima(rs.getInt("capacidadMaxima"));
sesion.setParticipantsActuales(rs.getInt("participantsActuales"));
sesion.setActivo(rs.getBoolean("activo"));
```

### Crear TSala desde ResultSet:
```java
TSala sala = new TSala();
sala.setIdSala(rs.getInt("idSala"));
sala.setNombreSala(rs.getString("nombreSala"));
sala.setAforo(rs.getInt("aforo"));
sala.setActivo(rs.getBoolean("activo"));
```

### Crear TEntrenador desde ResultSet:
```java
TEntrenador entrenador = new TEntrenador();
entrenador.setIdEntrenador(rs.getInt("idEntrenador"));
entrenador.setNombreEntrenador(rs.getString("nombreEntrenador"));
entrenador.setTelefonoEntrenador(rs.getString("telefonoEntrenador"));
entrenador.setDNIEntrenador(rs.getString("DNIEntrenador"));
```

---

## ⚠️ Precondiciones Críticas

### CASO 1: `delete()` con precondición
**Debe verificar:** `participantsActuales = 0`

```sql
-- INCORRECTO:
DELETE FROM sesiones WHERE idSesion = ?;

-- CORRECTO:
DELETE FROM sesiones WHERE idSesion = ? AND participantsActuales = 0;
```

### CASO 4 y 5: `getRoom()` y `getTrainer()` con activo
**Debe filtrar:** `WHERE s.activo = 1`

---

## 🧪 Casos de Prueba Mínimos

### Test 1: Create y Read
```java
TSesion sesion = new TSesion();
sesion.setNombreSesion("Yoga");
// ... llenar datos
int id = daoSesion.create(sesion);
TSesion retrieved = daoSesion.read(id);
assert retrieved != null;
```

### Test 2: Update
```java
TSesion sesion = daoSesion.read(1);
sesion.setNombreSesion("Yoga Avanzado");
int result = daoSesion.update(sesion);
assert result == 1;
```

### Test 3: Delete con precondición
```java
// Si hay clientes registrados:
int result = daoSesion.delete(1);
assert result == 0; // Fallo esperado

// Si NO hay clientes:
int result = daoSesion.delete(1);
assert result == 1; // Éxito
```

### Test 4: GetRoom
```java
TSala sala = daoSesion.getRoom(1);
assert sala != null;
assert sala.getIdSala() > 0;
```

### Test 5: GetTrainer
```java
TEntrenador entrenador = daoSesion.getTrainer(1);
assert entrenador != null;
assert entrenador.getIdEntrenador() > 0;
```

---

## 🔗 Referencias Útiles

| Documento | Propósito |
|-----------|----------|
| ALINEACION_SRS_SESION.md | Mapeo SRS → Código |
| ESTADO_MODULO_SESION.md | Estado general del proyecto |
| MODULO_SESION_README.md | Guía completa de implementación |
| EJEMPLOS_SESION.md | Ejemplos de código |

---

## ✅ Checklist de Implementación

- [ ] Tabla `sesiones` creada
- [ ] Tabla `cliente_sesion` creada
- [ ] Método `create()` implementado
- [ ] Método `read()` implementado
- [ ] Método `update()` implementado
- [ ] Método `delete()` implementado (con precondición)
- [ ] Método `read_all()` implementado
- [ ] Método `readByEntrenador()` implementado
- [ ] Método `readBySala()` implementado
- [ ] Método `getRoom()` implementado
- [ ] Método `getTrainer()` implementado
- [ ] Todos los métodos probados
- [ ] Sin errores de compilación
- [ ] Documentación actualizada

---

## 🚀 Próximo Paso Después de SQL

Una vez SQL esté implementado:
1. Integrar Commands en CommandFactory
2. Integrar Vistas en FactoriaPresentacion
3. Realizar pruebas de integración
4. Pruebas de UI

---

**Creado para:** SQL Implementation Phase
**Versión:** 1.0
**Última actualización:** [fecha actual]
