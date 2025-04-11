# POC - Registro de Usuarios (Reto Técnico)

API RESTful desarrollada en Java 17 con Spring Boot 3, orientada al registro de usuarios. Implementa arquitectura
hexagonal (Domain-Driven Design), principios SOLID, Clean Code, y buenas prácticas de seguridad, escalabilidad y
mantenibilidad.

## Tabla de contenidos

1. [Tecnologías utilizadas](#tecnologías-utilizadas)
3. [Instalación y ejecución](#instalación-y-ejecución)
4. [Accesos útiles](#accesos-útiles)
5. [Endpoint principal](#endpoint-principal)
6. [Validaciones del API](#Validaciones-del-API)
7. [Verificación de calidad y pruebas](#Verificación-de-calidad-y-pruebas)
8. [Principios y patrones aplicados](#principios-y-patrones-aplicados)

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.2**
- **Gradle**
- **Spring Data JPA (usando Hibernate como implementación) + H2 Database (en memoria)**
- **JWT (JSON Web Token)** para autenticación
- **Spring Security con JWT** (mínima implementación para generación de tokens)
- **MapStruct** (opcional, actualmente se usan mappers manuales)
- **Lombok**
- **Swagger / OpenAPI 3** para documentación de la API
- **JUnit 5 + Mockito** para pruebas unitarias
- **Spring Boot Actuator** para **observabilidad** y monitoreo
- **Logback** para configuración de logging
- **MDC (Mapped Diagnostic Context)** para identificar correlación de logs entre peticiones
- **AOP (Aspect-Oriented Programming)** para auditoría con la anotación `@Aspect`
- **Checkstyle** y **PMD** para análisis estático de código y aseguramiento de calidad
- **SonarQube** para integración continua y análisis de calidad de código
- **Logstash y ELK** (ElasticSearch, Logstash, Kibana) para almacenamiento y visualización de logs
- **Repositorio en GitHub**

## Instalación y ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/avillanueva198x/ms-challenge-app.git.git
cd ms-challenge-app
```

2. Levanta la aplicación con Gradle utilizando el perfil deseado (por defecto se sugiere dev):

```bash
./gradlew bootRun -Dspring.profiles.active=dev --offline
```

## Accesos útiles

- **Endpoint de salud**: [http://localhost:9191/actuator/health](http://localhost:9191/actuator/health)
- **Swagger UI**: [http://localhost:9191/swagger-ui.html](http://localhost:9191/swagger-ui.html)
- **Console H2**: [http://localhost:9191/h2-console](http://localhost:9191/h2-console)
    - JDBC URL: `jdbc:h2:mem:testdb`
    - Usuario: `sa`
    - Contraseña: (vacío)

## Endpoint principal

- `Request (POST /api/v1/users)`:

```bash
{
	"name": "Juan Rodriguez",
	"email": "juan@rodriguez.org",
	"password": "Hunter2",
	"phones": [
		{
			"number": "1234567",
			"citycode": "1",
			"contrycode": "57"
		}
	]
}
```

- `Respuesta exitosa 201 Created`:

```bash
{
  "id": "uuid",
  "created": "fecha",
  "modified": "fecha",
  "lastLogin": "fecha",
  "token": "jwt",
  "isActive": true,
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [...]
}
```

## Validaciones del API

- Email debe tener formato válido (@Email)
- Password validado con expresión regular configurable vía application.yml
- Mensaje de error si el email ya existe:

```bash
{
  "mensaje": "El correo ya registrado"
}
```

- Todos los errores siguen este formato:

```bash
{
  "mensaje": "mensaje de error"
}
```

- Los tokens JWT son firmados en memoria (no expiran realmente ya que no hay autenticación implementada aún).

## Verificación de calidad y pruebas

`Cobertura de pruebas`

- Controlador REST (`UserControllerTest`)
- Handler (`CreateUserHandlerTest`)
- Casos de uso (`CreateUserUseCaseTest`)
- Adaptador de persistencia (`UserPersistenceAdapterTest`)


- Para verificar que todo el proyecto cumple con estándares de calidad y tiene pruebas completas, **puedes ejecutar**:

```bash
./gradlew check
```

- Si cuentas con SONAR, **puedes ejecutar** este comando (Recuerda actualizar tus datos del sonar en el build.gradle):

```bash
./gradlew check -Penv=local
```

o Solo

```bash
./gradlew sonar -Penv=local
```

- Cómo **ver los reportes**:

```bash
PMD: build/reports/pmd/main.html
Checkstyle: build/reports/checkstyle/main.html
Cobertura JaCoCo:  build/reports/jacoco/test/html/index.html
Pruebas unitarias: build/reports/tests/test/index.html
```

- **NOTA:** Puedes usar Postman o Swagger para probar. Incluye también el archivo .postman_collection.json si gustas.

## Principios y patrones aplicados

Esta solución fue diseñada aplicando los **principios de arquitectura SETO** (Seguridad, Escalabilidad, Trazabilidad y
Observabilidad), integrando además buenas prácticas modernas de desarrollo en Java 17 con Spring Boot 3.2, arquitectura
limpia y enfoque DDD.

- **Arquitectura Hexagonal / Clean Architecture**: separación clara entre dominio, casos de uso, infraestructura y
  adaptadores.
- **Principios SOLID** aplicados a clases, controladores, servicios y adaptadores.
- **Clean Code**: clases pequeñas, responsabilidades únicas, nomenclatura clara y consistente.
- **DTOs** para desacoplar la capa REST del modelo de dominio.
- **Bean Validation (Jakarta)** con anotaciones como `@Valid`, `@NotBlank`, `@Email` y validaciones personalizadas.
- **JWT como token de autenticación** (generado al registrar usuario; no hay endpoints protegidos todavía).
- **Manejo centralizado de errores** con `@RestControllerAdvice` y mensajes estructurados en
  JSON (`{"mensaje": "error"}`).
- **Observabilidad avanzada**:
    - `Spring Boot Actuator` habilitado (`/actuator/health`, `/metrics`, etc.).
    - Uso de **MDC (Mapped Diagnostic Context)** para incluir `X-Correlation-Id` en cada request y log.
    - Configuración de **Logback estructurado** para logs JSON (`logback-spring.xml` compatible con ELK/Logstash).
- **Auditoría con AOP (Aspect-Oriented Programming)**: aplicación transversal de logging o trazabilidad mediante
  aspectos (`@Aspect`).
- **Seguridad desacoplada y extensible** con configuración de `SecurityFilterChain` para pruebas y
  perfiles (`@TestConfiguration`).
- **Inyección de dependencias limpia** usando `@Component`, `@Service`, `@Configuration`.
- **Mappers manuales** (con posibilidad de reemplazo por MapStruct).
- **Pruebas unitarias y TDD** cubriendo:
    - Controlador REST (`UserControllerTest`)
    - Casos de uso (`CreateUserUseCaseTest`)
    - Adaptador de persistencia (`UserPersistenceAdapterTest`)
- **Patrones para escalabilidad y mantenibilidad**:
    - `Ports & Adapters`: permite cambiar implementación (ej. de H2 a PostgreSQL) sin afectar dominio.
    - `Domain-Driven Design`: desacopla lógica de negocio real del transporte y persistencia.
    - `Handlers` como punto de entrada para orquestar validaciones y lógica.
- **Análisis de calidad ejecutado exitosamente**:
    - `Checkstyle`: aprobado
    - `PMD`: sin errores ni warnings
    - `JaCoCo`: cobertura generada correctamente
    - Todos los tests pasan (`./gradlew check`) sin fallos ni advertencias
- **Análisis con Sonarqube**:
    - Cobertura del código **al 100%**.


