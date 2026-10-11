# Flota de Aviones – Microservicio REST (Spring Boot)

Microservicio para la gestión operacional de una flota de aviones: estado de mantenimiento,
combustible, limpieza y ubicación (`VOLANDO`, `EN_TIERRA`, `FUERA_DE_SERVICIO`).

**Stack:** Java 17 · Spring Boot 3.2 · Spring Data JPA/Hibernate · H2 (archivo) · Maven

## Modelo de datos
```
Aerolinea (1) ────< (N) Avion        Avion.aerolinea_id  (@ManyToOne / @OneToMany)
```
Primero se crea una **aerolínea** y luego se registran aviones asociados a ella.

## Arquitectura en capas
```
controller  →  service (interfaz + impl)  →  repository (JpaRepository)  →  BD H2
                       model (Entidad JPA + Enum)      exception (GlobalExceptionHandler)
```

## 1. Requisitos previos
- Java JDK 17 o superior (`java -version`)
- Apache Maven 3.8+ (`mvn -version`)
- Git (`git --version`)
- Postman (para probar la API)

## 2. Clonar el repositorio
```bash
git clone https://github.com/simramirez-192/mi-diagrama.git
cd mi-diagrama
```

## 3. Compilar, testear y empaquetar
```bash
mvn clean          # limpia compilaciones anteriores
mvn test           # ejecuta las pruebas unitarias
mvn clean package  # compila + testea + genera el .jar
# (opcional) mvn clean install  -> además instala el jar en el repositorio local Maven
```
Genera `target/flota-aviones-0.0.1-SNAPSHOT.jar`.

## 4. Ejecutar
```bash
java -jar target/flota-aviones-0.0.1-SNAPSHOT.jar
```
- API: http://localhost:8080/api/aviones
- Documentación Swagger: http://localhost:8080/swagger-ui.html
- Consola H2: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/flotadb` · Usuario: `sa` · Contraseña: (vacía)

## 5. Endpoints

**Aerolíneas**

| Método | URL | Descripción | Respuesta |
|--------|-----|-------------|-----------|
| GET | `/api/aerolineas` | Listar todas | 200 |
| GET | `/api/aerolineas/{id}` | Buscar por ID | 200 / 404 |
| POST | `/api/aerolineas` | Crear | 201 / 400 / 409 |
| PUT | `/api/aerolineas/{id}` | Actualizar | 200 / 404 / 400 |
| DELETE | `/api/aerolineas/{id}` | Eliminar (sin aviones) | 204 / 404 / 409 |
| GET | `/api/aerolineas/{id}/aviones` | Aviones de la aerolínea | 200 / 404 |

**Aviones**

| Método | URL | Descripción | Respuesta |
|--------|-----|-------------|-----------|
| GET | `/api/aviones` | Listar todos | 200 |
| GET | `/api/aviones/{id}` | Buscar por ID | 200 / 404 |
| POST | `/api/aviones` | Crear avión | 201 / 400 / 404 / 409 |
| PUT | `/api/aviones/{id}` | Actualizar completo | 200 / 404 / 400 |
| DELETE | `/api/aviones/{id}` | Eliminar | 204 / 404 |
| GET | `/api/aviones/ubicacion/{ubicacion}` | Filtrar por ubicación | 200 / 400 |
| GET | `/api/aviones/mantenimiento` | Aviones en mantenimiento | 200 |

### Ejemplo POST /api/aerolineas
```json
{ "nombre": "LATAM", "codigoIata": "LA" }
```

### Ejemplo POST /api/aviones
```json
{
  "matricula": "CC-BAA",
  "modelo": "Airbus A320",
  "aerolinea": { "id": 1 },
  "capacidad": 180,
  "enMantenimiento": false,
  "combustibleListo": true,
  "limpiezaLista": true,
  "ubicacion": "EN_TIERRA"
}
```

### Ejemplo PUT /api/aviones/1
```json
{
  "matricula": "CC-BAA",
  "modelo": "Airbus A320",
  "aerolinea": { "id": 1 },
  "capacidad": 180,
  "enMantenimiento": true,
  "combustibleListo": false,
  "limpiezaLista": false,
  "ubicacion": "FUERA_DE_SERVICIO"
}
```

### Ejemplo de error (404)
```json
{
  "timestamp": "2026-10-08T10:15:30",
  "status": 404,
  "error": "Not Found",
  "mensaje": "No existe un avión con id 999"
}
```

## 6. Cambiar a PostgreSQL
Ver el bloque comentado en `application.properties` y la dependencia comentada en `pom.xml`.
