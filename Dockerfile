# Etapa de construcción
FROM gradle:7.4.2-jdk17 AS build

# Establecer directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar archivos de Gradle y de la aplicación
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Otorgar permisos de ejecución a gradlew
RUN chmod +x ./gradlew

# Descargar dependencias para evitar reconstrucciones repetidas
RUN ./gradlew --no-daemon dependencies

# Copiar el código fuente de la aplicación
COPY src ./src

# Obtener fecha y hora de compilación y BUILD_ID de Jenkins
ARG BUILD_DATE
ARG VERSION
ARG BUILD_ENV

# Asignar un entorno predeterminado si BUILD_ENV está vacío
RUN if [ -z "$BUILD_ENV" ]; then export BUILD_ENV="dev"; fi

# Mostrar valores de compilación
RUN echo "Compilando para el entorno: $BUILD_ENV" && \
    echo "Build Date: $BUILD_DATE" && \
    echo "Version: $VERSION"

# Compilar y empaquetar la aplicación
RUN ./gradlew --no-daemon clean build -Pprofile=$BUILD_ENV

# Crear un archivo de versión
RUN echo "version=${VERSION}" > /app/version.txt

# Etapa de ejecución
FROM eclipse-temurin:17-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Copiar archivo de versión
COPY --from=build /app/version.txt .

# Definir variables de entorno
ARG BUILD_DATE
ARG VERSION
ARG BUILD_ENV
ARG JOB_NAME
ENV BUILD_DATE=${BUILD_DATE}
ENV VERSION=${VERSION}
ENV APP_NAME=${JOB_NAME}
ENV SPRING_PROFILES_ACTIVE=${BUILD_ENV}

# Exponer puerto
EXPOSE 9091

# Ejecutar la aplicación
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
