# Checklist de Mejoras del Proyecto Universidad

## 1. Resolución de Dependencias Cíclicas

### 1.1 Servicios y DTOs
- [ ] Crear paquete `interfaces.mapper`
- [ ] Definir interfaces base para mappers
    - [ ] `IAlumnoMapper`
    - [ ] `IMateriaMapper`
    - [ ] `IUsuarioMapper`
- [ ] Refactorizar mappers existentes para implementar interfaces
- [ ] Mover lógica de mapeo compleja a clases de utilidad

### 1.2 Seguridad
- [ ] Crear interfaz `TokenService`
- [ ] Extraer lógica de tokens de `JwtTokenProvider`
- [ ] Implementar nuevo servicio de tokens
- [ ] Refactorizar `AutenticacionServicio`

## 2. Actualización de Dependencias

### 2.1 JJWT (Prioritario)
- [x] Actualizar `jjwt-api` a 0.12.3
- [x] Actualizar `jjwt-impl` a 0.12.3
- [x] Actualizar `jjwt-jackson` a 0.12.3
- [ ] Revisar y adaptar código afectado
    - [ ] Revisar `JwtTokenProvider`
    - [ ] Actualizar métodos de generación de tokens
    - [ ] Actualizar métodos de validación

### 2.2 Verificación de Compatibilidad
- [ ] Verificar compatibilidad con Spring Boot 3.2.3
- [ ] Ejecutar pruebas de integración
- [ ] Verificar funcionalidad de autenticación
- [ ] Documentar cambios realizados

## 3. Consideraciones de Java Version

### 3.1 Evaluación de Migración
- [ ] Documentar características de Java 23 en uso
- [ ] Identificar uso de features preview
- [ ] Evaluar impacto de migración a Java 21
- [ ] Crear plan de migración si se decide cambiar

### 3.2 Documentación
- [ ] Documentar decisión de versión de Java
- [ ] Listar características preview en uso
- [ ] Agregar advertencias necesarias
- [ ] Actualizar guía de desarrollo

## 4. Mejoras en Testing

### 4.1 Configuración de H2
- [ ] Agregar dependencia H2
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```
- [ ] Configurar perfil de pruebas
- [ ] Crear archivo `application-test.yml`
- [ ] Configurar datasource de pruebas

### 4.2 Implementación de Pruebas
- [ ] Crear pruebas unitarias para servicios
- [ ] Crear pruebas para mappers
- [ ] Crear pruebas de integración
- [ ] Implementar pruebas de seguridad

## 5. Gestión de Dependencias

### 5.1 Implementación de BOM
- [ ] Crear sección `dependencyManagement`
- [ ] Definir versiones en properties
- [ ] Centralizar gestión de versiones
- [ ] Documentar gestión de dependencias

### 5.2 Seguridad
- [ ] Verificar dependencias de seguridad
- [ ] Agregar dependencias de testing faltantes
- [ ] Configurar Spring Security
- [ ] Implementar pruebas de seguridad

## 6. Documentación

### 6.1 Actualización de Documentación
- [ ] Actualizar README.md
- [ ] Documentar configuración de seguridad
- [ ] Actualizar guías de desarrollo
- [ ] Documentar procedimientos de prueba

### 6.2 Guías de Contribución
- [ ] Crear guía de contribución
- [ ] Documentar estándares de código
- [ ] Establecer proceso de PR
- [ ] Definir flujo de trabajo Git

## 7. Monitoreo de Progreso

- [ ] Crear issues en el repositorio para cada tarea
- [ ] Asignar prioridades
- [ ] Establecer timeline
- [ ] Programar revisiones de código
- [ ] Validar cambios implementados

## Notas Importantes

1. **Prioridades**:
    - Alta: Actualización JJWT y resolución de dependencias cíclicas
    - Media: Implementación de pruebas y documentación
    - Baja: Migración de versión de Java

2. **Precauciones**:
    - Realizar backups antes de actualizaciones
    - Mantener rama principal estable
    - Documentar todos los cambios
    - Validar en ambiente de desarrollo

3. **Validación**:
    - Ejecutar suite completa de pruebas
    - Verificar funcionalidad de seguridad
    - Validar rendimiento
    - Revisar logs y mensajes de error