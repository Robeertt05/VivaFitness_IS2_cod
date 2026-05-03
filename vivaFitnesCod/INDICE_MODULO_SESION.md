# 📑 ÍNDICE RÁPIDO - MÓDULO SESIÓN

## 📂 Ubicaciones de Archivos

### Presentación (UI)
```
src/Presentacion/Entrenador/
├── VistaCrearSesion.java
├── VistaModificarSesion.java
├── VistaEliminarSesion.java
├── VistaMostrarSesion.java
├── VistaMostrarTodasSesiones.java
├── VistaApuntarSesion.java
└── VistaDesapuntarSesion.java
```

### Controlador (Commands)
```
src/Controlador/
├── CommandCrearSesion.java
├── CommandModificarSesion.java
├── CommandEliminarSesion.java
├── CommandMostrarSesion.java
├── CommandMostrarTodasSesiones.java
├── CommandApuntarSesion.java
├── CommandDesapuntarSesion.java
└── CommandEspaciosSesion.java
```

### Negocio (Business Logic)
```
src/Negocio/
├── FactoriaNegocio/
│   ├── SASesion.java (Interface)
│   ├── SASesionImp.java (Implementación)
│   └── FactoriaSAImp.java (Factory actualizada)
└── Cliente/
    └── SAClienteImp.java (Actualizado)
```

### Integración (Data Access)
```
src/Integracion/FactoriaIntegracion/
├── TSesion.java (Transfer Object)
├── DAOSesion.java (Interface)
├── DAOSesionImp.java (Implementación)
└── FactoriaIntegracion.java (Factory actualizada)
```

---

## 🚀 Guía de Uso Rápida

### 1️⃣ Crear Sesión
```java
TSesion sesion = new TSesion();
sesion.setNombreSesion("Yoga");
sesion.setCapacidadMaxima(20);

DAOSesion dao = FactoriaIntegracion.getInstance().generaDAOSesion();
SASesion sa = new SASesionImp(dao);
int idNuevo = sa.alta_sesion(sesion);
```

### 2️⃣ Apuntar Cliente
```java
int resultado = sa.apuntar_cliente_sesion(idSesion, idCliente);
if (resultado > 0) {
    System.out.println("Cliente apuntado");
}
```

### 3️⃣ Verificar Espacios
```java
int espacios = sa.espacios_disponibles(idSesion);
if (espacios > 0) {
    System.out.println("Hay " + espacios + " lugares libres");
}
```

### 4️⃣ Listar Sesiones
```java
Set<TSesion> todas = sa.mostrar_todas_sesiones();
for (TSesion s : todas) {
    System.out.println(s.getNombreSesion());
}
```

---

## 📖 Documentación

| Archivo | Contenido |
|---------|-----------|
| `MODULO_SESION_README.md` | Guía completa de implementación |
| `EJEMPLOS_SESION.md` | 11 ejemplos prácticos con código |
| `IMPLEMENTACION_SESION_RESUMEN.md` | Resumen ejecutivo del proyecto |
| `INDICE_MODULO_SESION.md` | Este archivo (referencia rápida) |

---

## 🔑 Métodos Principales

### SASesion Interface
```java
public interface SASesion {
    int alta_sesion(TSesion datos);
    int baja_sesion(int idSesion);
    int modificar_sesion(int idSesion, TSesion datos);
    TSesion mostrar_sesion(int idSesion);
    Set<TSesion> mostrar_todas_sesiones();
    Set<TSesion> mostrar_sesiones_entrenador(int idEntrenador);
    Set<TSesion> mostrar_sesiones_sala(int idSala);
    int apuntar_cliente_sesion(int idSesion, int idCliente);
    int desapuntar_cliente_sesion(int idSesion, int idCliente);
    int espacios_disponibles(int idSesion);
}
```

### DAOSesion Interface
```java
public interface DAOSesion {
    int create(TSesion datos);
    TSesion read(int idSesion);
    int update(TSesion tSesion);
    int delete(int idSesion);
    Set<TSesion> read_all();
    Set<TSesion> readByEntrenador(int idEntrenador);
    Set<TSesion> readBySala(int idSala);
}
```

### TSesion Atributos
```java
int idSesion                    // ID único
String nombreSesion             // Nombre
String descripcion              // Descripción
Date fecha                      // Fecha
String hora                     // Hora (ej: "10:00")
int idSala                      // Sala
int idEntrenador                // Entrenador
int capacidadMaxima             // Máximo participantes
int participantsActuales        // Participantes actuales
int activo                      // Estado (1/0)
```

---

## ✅ Checklist de Implementación

- [ ] Leer MODULO_SESION_README.md
- [ ] Crear tablas en BD (SQL incluido)
- [ ] Implementar métodos en DAOSesionImp
- [ ] Conectar FactoriaIntegracion con DAOs
- [ ] Conectar FactoriaSAImp con servicios
- [ ] Integrar CommandFactory
- [ ] Conectar Vistas con Commands
- [ ] Probar CRUD completo
- [ ] Validar capacidad de sesiones
- [ ] Ejecutar tests
- [ ] Documentar cambios propios

---

## 🔗 Relaciones Entre Capas

```
VistaCrearSesion
    ↓
CommandCrearSesion
    ↓
SASesionImp (validaciones)
    ↓
DAOSesionImp (BD)
    ↓
TSesion
```

---

## 💡 Tips Importantes

✅ **Usar Factory**: 
```java
SASesion sa = FactoriaSAImp.getInstance().getSASesion();
```

✅ **Validar antes de usar**:
```java
if (sesion == null) return;
if (idSesion <= 0) return;
```

✅ **Manejar excepciones**:
```java
try {
    // código
} catch (Exception e) {
    log.error("Error", e);
}
```

⚠️ **No hacer**:
- ❌ Acceder a DAO desde Vista
- ❌ Lógica de negocio en Commands
- ❌ Hardcodear conexión BD
- ❌ Ignorar validaciones

---

## 🐛 Errores Comunes

| Error | Solución |
|-------|----------|
| NullPointerException en SA | Verificar que DAO se pasó al constructor |
| Vistas no se muestran | Agregar `setVisible(true)` |
| BD no conecta | Implementar lógica en DAOSesionImp |
| ComboBox vacío | Cargar datos después de crear Vista |

---

## 📊 Matriz CRUD

| Operación | Vista | Command | SA | DAO |
|-----------|-------|---------|-----|-----|
| Create | VistaCrearSesion | CommandCrearSesion | alta_sesion | create |
| Read | VistaMostrarSesion | CommandMostrarSesion | mostrar_sesion | read |
| Update | VistaModificarSesion | CommandModificarSesion | modificar_sesion | update |
| Delete | VistaEliminarSesion | CommandEliminarSesion | baja_sesion | delete |
| List | VistaMostrarTodasSesiones | CommandMostrarTodasSesiones | mostrar_todas_sesiones | read_all |

---

## 🎯 Próximas Fases

**Fase 1: BD** (Primera prioridad)
- Crear tablas SQL
- Implementar DAOSesionImp
- Probar conexión

**Fase 2: Integración** 
- Conectar factorías
- Integrar CommandFactory
- Conectar Vistas

**Fase 3: Testing**
- Tests unitarios
- Tests de integración
- Tests de UI

**Fase 4: Producción**
- Validación completa
- Deployment
- Monitoreo

---

## 📞 Soporte

Para entender el código:
1. Lee `MODULO_SESION_README.md`
2. Revisa `EJEMPLOS_SESION.md`
3. Consulta el código fuente directamente
4. Compara con módulo Cliente (estructura similar)

---

## 📅 Versiones

| Versión | Fecha | Estado |
|---------|-------|--------|
| 1.0 | 2026-04-23 | ✅ Código completado |
| 1.1 | TBD | ⏳ Integración BD |
| 1.2 | TBD | ⏳ Testing completo |
| 2.0 | TBD | ⏳ Funcionalidades avanzadas |

---

## 📝 Notas Finales

- ✅ Módulo completamente codificado
- ⏳ Awaiting BD implementation
- 📚 Documentación completa
- 🎯 Ready for integration

**Desarrollado**: April 23, 2026
**Listo para**: BD Integration Phase
