# 🎬 Moviee - Movie Reservation System

Moviee is a comprehensive backend system for a movie reservation service built with Spring Boot.  
The service allows users to sign up, log in, browse movies, reserve seats for specific showtimes,
and manage their reservations.  
The system features user authentication with OAuth2, role-based authorization, movie and showtime
management, seat reservation functionality, and reporting on reservations.

## 🚀 Technology Stack

![Java 21](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)  
![Spring Boot 3.4.6](https://img.shields.io/badge/Spring_Boot-3.4.6-green?logo=springboot&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql&logoColor=white)  
![Maven 3.9.9](https://img.shields.io/badge/Maven-3.9-red?logo=apachemaven&logoColor=white)  
![Docker](https://img.shields.io/badge/Docker-28-blue?logo=docker&logoColor=white)

## 🏗 Project Structure

- **📦 Controllers:** Handle HTTP requests and responses
- **⚙️ Services:** Contain business logic
- **📚 Repositories:** Handle data access operations
- **📝 DTOs:** Data transfer objects for API contracts
- **🏛 Entities:** Domain models mapped to database tables
- **🚨 Exceptions:** Custom exception handling
- **🔒 Security:** Authentication and authorization configuration
- **🛠 Utils:** Utility classes and validators

## ✨ Features

### 🔐 User Authentication and Authorization

- OAuth2-based authentication system
- Role-based access control (ADMIN, USER)
- Custom login page with username/password authentication
- Custom consent page for OAuth2 authorization flow
- Password validation with strong password requirements
- JWT token-based authentication

### 📚 API Documentation

- REST API with HAL (Hypertext Application Language) support
- HATEOAS implementation for API discoverability

### 🛠 Development and Deployment

- Docker support for containerized deployment
- Docker Compose configuration for orchestration
- Maven wrapper for easier build management
- H2 in-memory database for development
- PostgreSQL support for production

## 🚦 Getting Started

### 📋 Prerequisites

- Java 21 or higher
- Maven (or use the included mvnw wrapper)
- Docker and Docker Compose (optional, for containerized deployment)

### ▶️ Running the Application

**Using Docker (dev):**

```bash
docker compose -f docker-compose.dev.yml up -d
```

The application will be available at [https://localhost:443](https://localhost:443).

### 🔑 Default Credentials

* **Admin user:**

  * Username: `admin`
  * Password: `Admin1#@`

* **Regular user:**

  * Username: `user`
  * Password: `User1#@@`

## 🛣 API Endpoints

### 👥 User Management

* `GET /api/v1/users` - Get all users (ADMIN only)
* `GET /api/v1/users/{id}` - Get user by ID
* `POST /api/v1/users` - Create new user

### 🔐 Authentication

* `GET /login` - Login page
* `GET /oauth2/consent` - OAuth2 consent page

## 📈 Project Status

### ✅ Features Implemented

* [x] User authentication with OAuth2 and JWT
* [x] Role-based authorization (ADMIN, USER, MODERATOR)
* [x] User registration and management
* [x] Custom login page
* [x] Remember-me on login
* [x] Custom OAuth2 consent page
* [x] Strong password validation
* [x] Docker and Docker Compose support
* [x] HAL+JSON API with HATEOAS

### 🚧 Features To Be Implemented

* [ ] Login with Google
* [ ] Movie management (add, update, delete movies)
* [ ] Movie categorization by genre
* [ ] Showtime management
* [ ] Seat reservation system
* [ ] Booking management
* [ ] User reservation overview
* [ ] Reservation cancellation
* [ ] Admin reporting (reservations, capacity, revenue)
* [ ] Email notifications
* [ ] File upload for movie posters
* [ ] API documentation with OpenAPI/Swagger
* [ ] Integration tests
* [ ] CI/CD pipeline

## 📝 Notes on Login and Consent Pages

Although this project is primarily a backend system without a dedicated frontend, it includes a
custom login page (`/login`) and OAuth2 consent page (`/oauth2/consent`).

These pages are included intentionally **for learning and exploration purposes** to understand and
configure the full OAuth2 Authorization Server flow with Spring Authorization Server.

In a production backend-only API, these pages typically would not exist or would be handled by a
separate frontend client. The inclusion here is purely didactic and meant to demonstrate how to
customize these flows.

Future improvements may include separating the Authorization Server and Resource Server
responsibilities and removing these pages to align strictly with a backend API architecture.

## 📄 License

This project is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).