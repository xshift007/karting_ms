# karting_ms
tingeso proyect_II
# KartingRM - Sistema de Reservas para Pistas de Karting (Microservicios)

[![Estado](https://img.shields.io/badge/estado-beta-yellow)](https://github.com/xshift007/KartingRM)
\[![Java](https://img.shields.io/badge/Java-17-blue)]
\[![Spring\_Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green)]
\[![React](https://img.shields.io/badge/React-17-blue)]
\[![Docker](https://img.shields.io/badge/Docker-Sí-blue)]
\[![Jenkins](https://img.shields.io/badge/Jenkins-CI/CD-blueviolet)]
\[![Licencia](https://img.shields.io/badge/licencia-MIT-lightgrey)]

---

## 📖 Descripción

KartingRM es un sistema modular de microservicios para la gestión de reservas en pistas de karting. Permite registrar clientes, administrar pistas, calcular precios y descuentos dinámicos, y notificar a los usuarios el estado de sus reservas.

## 🏛 Arquitectura

![Arquitectura de Microservicios](docs/arquitectura.png)

1. **Config Server**: Centraliza la configuración de todos los servicios con Spring Cloud Config.
2. **Service Registry** (Eureka): Registro y descubrimiento de servicios.
3. **API Gateway**: Enrutamiento inteligente, balanceo de carga y seguridad (Spring Cloud Gateway).
4. **Cliente Service**: CRUD de clientes y gestión de perfiles.
5. **Pista Service**: CRUD de pistas de kart y disponibilidad.
6. **Reserva Service**: Creación y gestión de reservas, validación de solapamientos.
7. **Pricing Service**: Cálculo de precio base, aplicación de descuentos y promociones.
8. **Notification Service**: Envío de correos y notificaciones en tiempo real (SMTP/WebSocket).
9. **Frontend**: Aplicación React con Material‑UI y Axios para interacción de usuario.

## 🛠 Tecnologías

* **Backend**: Java 17, Spring Boot 3.1, Spring Cloud (Config, Eureka, Gateway)
* **Persistencia**: Spring Data JPA + MySQL
* **Mensajería**: RabbitMQ
* **Frontend**: React 17, Material‑UI, Axios
* **CI/CD**: Jenkins
* **Contenedores**: Docker & Docker Compose
* **Documentación de APIs**: OpenAPI / Swagger

## 📋 Requisitos Previos

* Git 2.30+
* Java 17
* Maven 3.6+
* Node.js 16+
* Docker & Docker Compose
* Jenkins (opcional para pruebas locales)

## 🚀 Instalación y Despliegue

1. Clona el repositorio:

   ```bash
   git clone https://github.com/xshift007/KartingRM.git
   cd KartingRM
   ```

2. Copia y edita variables de entorno en `env/.env` (basado en `env/.env.example`):

   ```ini
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/kartingrm
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=tu_contraseña
   RABBITMQ_HOST=localhost
   JWT_SECRET=tu_secreto_jwt
   ```

3. Levanta toda la plataforma con Docker Compose:

   ```bash
   docker-compose up -d --build
   ```

4. Accede a Eureka (Service Registry): [http://localhost:8761](http://localhost:8761)

5. Documentación de APIs en Swagger (Gateway): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

6. Frontend:

   ```bash
   cd frontend
   npm install
   npm start
   ```

   * La interfaz estará disponible en [http://localhost:3000](http://localhost:3000)

## 📡 Uso de Endpoints

> Se detallan algunos ejemplos básicos de uso:

* **Registrar Cliente**
  `POST /api/clients`

  ```json
  {
    "nombre": "María López",
    "email": "maria.lopez@example.com",
    "telefono": "+56 9 1234 5678"
  }
  ```

* **Listar Pistas**
  `GET /api/tracks`

* **Crear Reserva**
  `POST /api/reservations`

  ```json
  {
    "clienteId": 1,
    "pistaId": 2,
    "fechaHoraInicio": "2025-06-01T14:00:00",
    "duracionMinutos": 30
  }
  ```

* **Calcular Precio**
  `GET /api/pricing?trackId=2&duration=30`

## 🔧 Pruebas

* **Backend**:

  ```bash
  cd service-name
  mvn test
  ```
* **Frontend**:

  ```bash
  cd frontend
  npm test
  ```

## 🤝 Contribuciones

¡Bienvenidas! Para contribuir:

1. Haz fork del repositorio.
2. Crea una rama `feature/nombre-de-tu-feature`.
3. Añade tests y documentación.
4. Envía un Pull Request explicando tus cambios.

## 📄 Licencia

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.

---

**Desarrollado por Nicolás Sánchez**
