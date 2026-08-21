# Rollnexa

Rollnexa es una plataforma para encontrar grupos de rol de mesa, organizar campañas, coordinar disponibilidad y conversar con cada grupo. No es un tablero virtual: el foco está en que la mesa se forme y siga organizada.

## Arquitectura

- `frontend/`: React 19, TypeScript y Vite. SPA responsive servida por Nginx en producción.
- `backend/`: Java 21, Spring Boot, REST, JPA, Spring Security, STOMP/WebSocket y Flyway.
- PostgreSQL 17: fuente persistente de usuarios, salas, mensajes, votos, sesiones y amistades.
- Autenticación por sesión HTTP con cookie `HttpOnly`, BCrypt y CSRF. Las reglas GM/PLAYER se comprueban en los servicios del backend.

Las entidades intermedias (`RoomMember`, `SessionResponse` y `Friendship`) preservan roles y estado sin relaciones `ManyToMany` directas. Las migraciones están en `backend/src/main/resources/db/migration`.

## Inicio rápido con Docker

Requisitos: Docker Desktop con Compose v2.

```bash
cp .env.example .env
docker compose up --build
```

La aplicación queda disponible en `http://localhost:8081`; la API también se expone en `http://localhost:8080`. PostgreSQL usa un volumen persistente llamado `rollnexa_postgres`.

Para cargar dos usuarios y salas de demostración, establecé `SPRING_PROFILES_ACTIVE=dev`. Solo ocurre con ese perfil. Credenciales locales: `NightWizard / Rollnexa123!` y `PlayerOne / Rollnexa123!`. No uses el perfil `dev` en producción.

## Desarrollo local

Requisitos: Java 21, Maven 3.9+, Node 22+ y PostgreSQL 17.

1. Levantá únicamente la base: `docker compose up db`.
2. Backend: `cd backend && mvn spring-boot:run`.
3. Frontend: `cd frontend && npm install && npm run dev`.

Vite redirige `/api` y `/ws` a `localhost:8080`. El backend migra el esquema automáticamente con Flyway.

## Variables de entorno

Copiá `.env.example` a `.env`; `.env` está ignorado por Git.

| Variable | Uso |
|---|---|
| `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` | Base y credenciales de PostgreSQL |
| `DATABASE_URL` | JDBC URL del backend |
| `SPRING_PROFILES_ACTIVE` | `prod` o `dev` |
| `APP_CORS_ALLOWED_ORIGINS` | Orígenes permitidos, separados por coma |
| `SESSION_COOKIE_SECURE` | `true` detrás de HTTPS |
| `VITE_API_URL` | URL de API solo si no se usa el proxy del mismo origen |

## Pruebas y builds

```bash
cd backend && mvn test
cd frontend && npm install && npm run build
```

O validá todo con `docker compose build`. Los tests cubren creación de sala, ingreso abierto, solicitud de acceso, cupo, permisos GM, disponibilidad, BCrypt y niveles.

## API principal

- `/api/auth`: CSRF, registro, login, sesión y logout.
- `/api/rooms`: lobby paginado, detalle y creación.
- `/api/rooms/{id}/members|requests|availability|sessions|messages`: operación del grupo.
- `/api/users` y `/api/friends`: perfiles, salas propias y amistades.
- `/ws`: endpoint STOMP; publicación en `/app/rooms/{id}/chat` y suscripción a `/topic/rooms/{id}`.

## Despliegue en `rollnexa.online`

En una VPS, ejecutá los contenedores sin publicar PostgreSQL. Un Nginx frontal debe terminar TLS, servir/redirigir al frontend y conservar los encabezados `Upgrade`/`Connection` para `/ws`. Configurá:

```env
APP_CORS_ALLOWED_ORIGINS=https://rollnexa.online
SESSION_COOKIE_SECURE=true
SPRING_PROFILES_ACTIVE=prod
```

Después de apuntar DNS a la VPS, instalá Certbot y emití el certificado con `certbot --nginx -d rollnexa.online -d www.rollnexa.online`. No se requiere ni se realiza ningún cambio DNS desde este repositorio. Conservá backups del volumen PostgreSQL y rotá credenciales antes de publicar.

## Decisiones del MVP

- Paginación por botón “Cargar más”, simple y predecible.
- Sesión web en lugar de JWT; reduce exposición de tokens en el navegador.
- El cálculo de niveles vive en `LevelService` y puede cambiarse sin tocar perfiles.
- Actividad se acredita en bloques de hasta 15 minutos y se limita por sesión; no se suma solo por dejar una pestaña abierta.
