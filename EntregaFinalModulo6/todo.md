# Checklist Completo de Mejoras del Proyecto Universidad

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
- [x] Crear interfaz `TokenService`
- [x] Extraer lógica de tokens de `JwtTokenProvider`
- [x] Implementar nuevo servicio de tokens
- [x] Refactorizar `AutenticacionServicio`

## 2. Actualización de Dependencias

### 2.1 JJWT (Prioritario)
- [x] Actualizar `jjwt-api` a 0.12.3
- [x] Actualizar `jjwt-impl` a 0.12.3
- [x] Actualizar `jjwt-jackson` a 0.12.3
- [x] Revisar y adaptar código afectado
    - [x] Revisar `JwtTokenProvider`
    - [x] Actualizar métodos de generación de tokens
    - [x] Actualizar métodos de validación

### 2.2 Verificación de Compatibilidad
- [x] Verificar compatibilidad con Spring Boot 3.2.3
- [ ] Ejecutar pruebas de integración
- [x] Verificar funcionalidad de autenticación
- [x] Documentar cambios realizados

## 3. Consideraciones de Java Version

### 3.1 Evaluación de Migración
- [x] Documentar características de Java 23 en uso
- [x] Identificar uso de features preview
- [x] Evaluar impacto de migración a Java 21
- [ ] Crear plan de migración si se decide cambiar

### 3.2 Documentación
- [ ] Documentar decisión de versión de Java
- [ ] Listar características preview en uso
- [ ] Agregar advertencias necesarias
- [ ] Actualizar guía de desarrollo

## 4. Mejoras en Testing

### 4.1 Configuración de H2
- [x] Agregar dependencia H2
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
- [x] Crear sección `dependencyManagement`
- [x] Definir versiones en properties
- [x] Centralizar gestión de versiones
- [ ] Documentar gestión de dependencias

### 5.2 Seguridad
- [x] Verificar dependencias de seguridad
- [x] Agregar dependencias de testing faltantes
- [x] Configurar Spring Security
- [ ] Implementar pruebas de seguridad

## 6. Revisión de AutenticacionServicio

### 6.1 Problemas Identificados
- [x] Validar imports y dependencias
- [ ] Verificar manejo de excepciones
- [ ] Revisar ciclo de vida de las transacciones
- [x] Comprobar inyección de dependencias

### 6.2 Mejoras Necesarias
- [ ] Crear clases de soporte para excepciones
    - [ ] `AuthenticationException`
    - [ ] `TokenException`
    - [ ] `ValidationException`
- [ ] Implementar constantes para mensajes
    - [ ] Crear clase `SecurityMessages`
    - [ ] Crear clase `ValidationMessages`
- [ ] Actualizar modelo Usuario
    - [ ] Agregar builder
    - [ ] Mejorar validaciones
- [ ] Optimizar manejo de tokens
    - [ ] Implementar cache de tokens
    - [ ] Mejorar validación de expiración

### 6.3 Testing Específico
- [ ] Crear pruebas unitarias para AutenticacionServicio
- [ ] Implementar pruebas de integración
- [ ] Verificar escenarios de error
- [ ] Validar manejo de tokens

## 7. Revisión de Configuración

### 7.1 Actualizar JwtProperties
- [ ] Modificar la generación de clave secreta
- [ ] Agregar validaciones
- [ ] Actualizar configuración en application.yml

### 7.2 Actualizar JwtAuthenticationFilter
- [x] Adaptar al nuevo JwtTokenProvider
- [ ] Mejorar manejo de errores
- [ ] Agregar logging detallado

### 7.3 Actualizar SecurityConfig
- [x] Configurar nuevos beans de seguridad
- [x] Actualizar configuración de filtros
- [x] Revisar políticas de seguridad

## 8. Documentación

### 8.1 Actualización de Documentación
- [ ] Actualizar README.md
- [ ] Documentar configuración de seguridad
- [ ] Actualizar guías de desarrollo
- [ ] Documentar procedimientos de prueba

### 8.2 Guías de Contribución
- [ ] Crear guía de contribución
- [ ] Documentar estándares de código
- [ ] Establecer proceso de PR
- [ ] Definir flujo de trabajo Git

## 9. Monitoreo de Progreso

- [ ] Crear issues en el repositorio para cada tarea
- [ ] Asignar prioridades
- [ ] Establecer timeline
- [ ] Programar revisiones de código
- [ ] Validar cambios implementados

## Notas Importantes

1. **Prioridades Actualizadas**:
    - Alta: Implementación de clases de soporte para AutenticacionServicio
    - Media: Pruebas y documentación
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

4. **Siguiente Sprint**:
    - Completar implementación de clases de soporte
    - Realizar pruebas de integración
    - Actualizar documentación
    - Revisar seguridad general