# Contribuir a Rollnexa

Gracias por tu interés en mejorar Rollnexa. Las contribuciones mediante reportes, propuestas, documentación y código son bienvenidas.

## Antes de comenzar

1. Revisá los issues existentes para evitar trabajo duplicado.
2. Para cambios importantes, abrí primero un issue explicando el problema y la solución propuesta.
3. Nunca incluyas contraseñas, tokens, archivos `.env`, datos personales ni credenciales reales.

## Preparar el entorno

Requisitos:

- Docker con Compose v2; o
- Java 21, Maven 3.9+, Node.js 22+ y PostgreSQL 17 para ejecutar cada servicio por separado.

La forma más sencilla de iniciar el proyecto es:

```bash
cp .env.example .env
docker compose up --build
```

La aplicación queda disponible en `http://localhost:8081`.

## Flujo de trabajo

1. Hacé un fork del repositorio.
2. Creá una rama descriptiva desde `main`, por ejemplo `feat/notificaciones` o `fix/chat-reconnect`.
3. Mantené cada cambio enfocado en un único objetivo.
4. Agregá o actualizá pruebas cuando modifiques comportamiento.
5. Verificá el proyecto antes de enviar el pull request.

## Verificaciones

Backend:

```bash
cd backend
mvn test
```

Frontend:

```bash
cd frontend
npm install
npm run lint
npm run build
```

También podés comprobar las imágenes de producción con:

```bash
docker compose build
```

## Commits y pull requests

- Usá mensajes breves y claros en modo imperativo.
- Explicá qué cambia el pull request, por qué es necesario y cómo se verificó.
- Incluí capturas para cambios visuales.
- Vinculá el issue relacionado cuando corresponda.
- Evitá mezclar reformateos masivos con cambios funcionales.
- Confirmá que no se agregaron secretos ni archivos generados.

Al contribuir aceptás que tu trabajo se distribuya bajo la [licencia MIT](LICENSE) del proyecto.
