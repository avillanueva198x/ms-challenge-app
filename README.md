# BCI - Registro de Usuarios (Reto Técnico)

API RESTful desarrollada en Java 17 con Spring Boot 3, orientada al registro de usuarios. Implementa arquitectura hexagonal (Domain-Driven Design), principios SOLID, Clean Code, y buenas prácticas de seguridad, escalabilidad y mantenibilidad.

## Tabla de contenidos

1. [Tecnologías utilizadas](#tecnologías-utilizadas)
2. [Estructura del proyecto](#estructura-del-proyecto)
3. [Instalación y ejecución](#instalación-y-ejecución)
4. [Accesos útiles](#accesos-útiles)
5. [Endpoint principal](#endpoint-principal)
6. [Validaciones](#validaciones)
7. [Pruebas](#pruebas)
8. [Principios y patrones aplicados](#principios-y-patrones-aplicados)


## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.2**
- **Gradle**
- **Spring Data JPA + H2 Database (en memoria)**
- **JWT (JSON Web Token)**
- **Spring Security con JWT (mínima implementación para generación de tokens)**
- **MapStruct (opcional, actualmente se usan mappers manuales)**
- **Lombok**
- **Swagger / OpenAPI 3**
- **JUnit 5 + Mockito**
- **Spring Boot Actuator (Observabilidad)**

## Arquitectura del Proyecto

La solución está estructurada bajo el enfoque Hexagonal / Ports & Adapters, cumpliendo principios de DDD (Domain-Driven Design):

```bash
┌────────────────────┐
│     Controller     │ ← REST API (input)
├────────────────────┤
│     Handler        │ ← Adaptador Input
├────────────────────┤
│ Application Layer  │ ← Casos de uso
├────────────────────┤
│     Domain         │ ← Entidades / lógica de negocio
├────────────────────┤
│ Persistence Layer  │ ← Adaptador Output (JPA / H2)
└────────────────────┘
```

## Instalación y ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/tu-usuario/bci-user-registration.git
cd bci-user-registration
```

2. Ejecuta con Gradle:

```bash
./gradlew bootRun
```

## Accesos útiles

- **Endpoint de salud**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Console H2**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
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

## Validaciones y errores

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


## Pruebas

- Para correr las pruebas unitarias:

```bash
./gradlew test
```

- O si usas Maven local:

```bash
./mvnw test
```
- Incluye pruebas de:

Casos de uso (CreateUserUseCase)
Adaptador de persistencia (UserPersistenceAdapter)
Generador JWT (JwtUtil)
Controlador REST (UserController con MockMvc)

- Puedes usar Postman o Swagger para probar. Incluye también el archivo .postman_collection.json si gustas.

## Principios y patrones aplicados

- Arquitectura **Hexagonal / Clean Architecture**
- Principios  **SOLID**
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


### Cobertura de pruebas

- Casos de uso (`CreateUserUseCase`)
- Adaptador de persistencia (`UserPersistenceAdapter`)
- Utilitario de JWT
- Controlador REST (`UserController` con MockMvc)
