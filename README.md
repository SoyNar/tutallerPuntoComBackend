# Tu Taller - Backend

Este es el repositorio del **backend** de Tu Taller, la plataforma que conecta a usuarios con talleres cercanos para agendar citas y hacer seguimiento de reparaciones.

## Tecnologías

- Java
- Spring Boot
- Gradle
- PostgreSQL / MySQL *(ajusta según tu base de datos)*

## Requisitos

- JDK 17 o superior *(ajusta a la versión que uses)*
- Gradle (opcional, el proyecto incluye el wrapper)
- Base de datos configurada

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/SoyNar/<nombre-del-repo>.git
   cd <nombre-del-repo>
   ```

2. Configura las variables de entorno o el archivo de propiedades local con los datos de conexión a la base de datos. No subas credenciales al repositorio.

3. Ejecuta la aplicación:
   ```bash
   ./gradlew bootRun
   ```
   En Windows usa `gradlew.bat bootRun`.

## Estructura del proyecto