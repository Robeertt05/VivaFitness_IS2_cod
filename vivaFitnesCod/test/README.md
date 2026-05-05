# Pruebas JUnit - VivaFitness

## Teoría de Pruebas Orientadas a Objetos Aplicada

Las pruebas siguen los principios de pruebas OO:

1. **Pruebas de Estado** — Verifican que constructores y setters establecen correctamente el estado interno
2. **Pruebas de Comportamiento** — Verifican métodos como `toString()`, getters/setters
3. **Pruebas de Encapsulación** — Verifican que modificar un atributo no afecta a los demás
4. **Pruebas de Relaciones** — Verifican integridad referencial entre entidades (FK)
5. **Pruebas de Restricciones de Baja** — Verifican que no se puede eliminar una entidad con relaciones activas

## Estructura

```
test/
├── stubs/                                    # DAOs falsos (en memoria)
│   ├── DAOClienteStub.java
│   ├── DAOEntrenadorStub.java
│   ├── DAOSalaStub.java
│   ├── DAOSesionStub.java
│   ├── FactoriaIntegracionStub.java          # Factoría que devuelve stubs
│   └── TestHelper.java                       # Inyección por reflexión
├── Integracion/
│   ├── Cliente/TClienteTest.java             # 19 tests
│   ├── Entrenador/TEntrenadorTest.java       # 16 tests
│   ├── Sala/TSalaTest.java                   # 7 tests
│   └── Sesion/
│       ├── TSesionTest.java                  # 8 tests
│       └── TClienteSesionTest.java           # 8 tests
└── Negocio/
    ├── Relaciones/RelacionesEntidadesTest.java      # 10 tests
    └── Restricciones/RestriccionesBajaTest.java     # 18 tests
```

**Total: 86 tests**

## Relaciones verificadas

| Relación | Tipo | Tests |
|----------|------|-------|
| Sala → Sesion | 1:N | Varias sesiones misma sala, sala sin sesiones, salas distintas |
| Entrenador → Sesion | 1:N | Varias sesiones mismo entrenador, entrenador sin sesiones |
| Cliente ↔ Sesion | M:N | Un cliente varias sesiones, varios clientes una sesión |
| Grafo completo | Todas | Navegabilidad transitiva entre todas las entidades |

## Restricciones de baja verificadas

| Escenario | Resultado esperado |
|-----------|-------------------|
| Baja sala CON sesiones activas | Impedida (retorna 0) |
| Baja sala SIN sesiones | Permitida |
| Baja entrenador (baja lógica activo=0) | Funciona |
| Baja entrenador ya inactivo | Impedida (retorna -1) |
| Baja sesión existente | Permitida |
| Baja sesión inexistente | Excepción |
| Baja cliente SIN inscripciones | Permitida |
| Baja cliente inexistente | Impedida (retorna -1) |
| Alta sesión con sala inexistente | Excepción |
| Alta sesión con entrenador inexistente | Excepción |

## Cómo ejecutar

```bash
# Compilar
javac -d bin_test -cp "src;lib/junit-platform-console-standalone-1.10.2.jar" test/stubs/*.java test/Integracion/Cliente/*.java test/Integracion/Entrenador/*.java test/Integracion/Sala/*.java test/Integracion/Sesion/*.java test/Negocio/Relaciones/*.java test/Negocio/Restricciones/*.java

# Ejecutar
java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path "bin_test;src" --scan-class-path bin_test
```
