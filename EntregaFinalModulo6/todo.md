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
- [x] Crear interfaz `TokenService`
- [x] Extraer lógica de tokens de `JwtTokenProvider`
- [x] Implementar nuevo servicio de tokens
- [ ] Refactorizar `AutenticacionServicio`

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
- [ ] Verificar funcionalidad de autenticación
- [ ] Documentar cambios realizados

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
- [ ] Configurar Spring Security
- [ ] Implementar pruebas de seguridad

## 8. Revisión de Código
1. Actualizar JwtProperties
    - [ ] Modificar la generación de clave secreta
    - [ ] Agregar validaciones
    - [ ] Actualizar configuración en application.yml

2. Actualizar JwtAuthenticationFilter
    - [ ] Adaptar al nuevo JwtTokenProvider
    - [ ] Mejorar manejo de errores
    - [ ] Agregar logging detallado

3. Actualizar SecurityConfig
    - [ ] Configurar nuevos beans de seguridad
    - [ ] Actualizar configuración de filtros
    - [ ] Revisar políticas de seguridad

4. Actualizar AutenticacionServicio
    - [ ] Adaptar a nuevo manejo de tokens
    - [ ] Mejorar manejo de errores
    - [ ] Actualizar process de login/registro
