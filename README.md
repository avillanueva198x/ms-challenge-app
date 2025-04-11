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

- Arquitectura **Hexagonal / Clean Architecture**
- Principios **SOLID**
- **Clean Code** (responsabilidades únicas, nombres claros, clases pequeñas)
- DTOs para desacoplar capa REST y dominio
- Bean Validation (@Valid, @NotBlank, @Email, etc.)
- JWT como token de autenticación (aún no se usa en endpoints protegidos)
- Centralización de errores con **@RestControllerAdvice**
- Observabilidad con **Spring Boot Actuator**
- Posibilidad de extender seguridad con **Spring Security**
- Mappers manuales (reemplazables por MapStruct si se desea mayor mantenibilidad)
- Inversión de dependencias con **@Component / @Service**
- Validación con **Jakarta Bean Validation**
- Pruebas unitarias con **TDD** (Test Driven Development)
