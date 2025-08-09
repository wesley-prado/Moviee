# Moviee - Movie Reservation System

Moviee is a comprehensive backend system for a movie reservation service built with Spring Boot. The
service allows users to sign up, log in, browse movies, reserve seats for specific showtimes, and
manage their reservations. The system features user authentication with OAuth2, role-based
authorization, movie and showtime management, seat reservation functionality, and reporting on
reservations.

## Technology Stack

- **Backend**: Java 21 with Spring Boot 3.x
- **Security**: OAuth2 Authorization Server & Resource Server
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Containerization**: Docker

## Project Structure

The application follows a layered architecture:

- **Controllers**: Handle HTTP requests and responses
- **Services**: Contain business logic
- **Repositories**: Handle data access operations
- **DTOs**: Data transfer objects for API contracts
- **Entities**: Domain models mapped to database tables
- **Exceptions**: Custom exception handling
- **Security**: Authentication and authorization configuration
- **Utils**: Utility classes and validators

## Features

### User Authentication and Authorization

- OAuth2-based authentication system
- Role-based access control (ADMIN, USER)
- Custom login page with username/password authentication
- Custom consent page for OAuth2 authorization flow
- Password validation with strong password requirements
- JWT token-based authentication

### API Documentation

- REST API with HAL (Hypertext Application Language) support
- HATEOAS implementation for API discoverability

### Development and Deployment

- Docker support for containerized deployment
- Docker Compose configuration for orchestration
- Maven wrapper for easier build management
- H2 in-memory database for development
- PostgreSQL support for production

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven (or use the included mvnw wrapper)
- Docker and Docker Compose (optional, for containerized deployment)

### Running the Application

**Using Maven:**

```bash
./mvnw spring-boot:run
```

**Using Docker (dev):**

```bash
docker compose -f docker-compose.dev.yml up -d
```

The application will be available at https://localhost:443.

### Default Credentials

The application is pre-configured with the following users in development mode:

- Admin user:
  - Username: admin
  - Password: Admin1#@

- Regular user:
  - Username: user
  - Password: User1#@@

## API Endpoints

### User Management

- `GET /api/v1/users` - Get all users (ADMIN only)
- `GET /api/v1/users/{id}` - Get user by ID
- `POST /api/v1/users` - Create new user

### Authentication

- `GET /login` - Login page
- `GET /oauth2/consent` - OAuth2 consent page

## Project Status

### Features Implemented

- ✅ User authentication with OAuth2 and JWT
- ✅ Role-based authorization (ADMIN, USER, MODERATOR)
- ✅ User registration and management
- ✅ Custom login page
- ✅ Remember-me on login
- ✅ Custom OAuth2 consent page
- ✅ Strong password validation
- ✅ Docker and Docker Compose support
- ✅ HAL+JSON API with HATEOAS

### Features To Be Implemented

- ⬜ Login with Google
- ⬜ Movie management (add, update, delete movies)
- ⬜ Movie categorization by genre
- ⬜ Showtime management
- ⬜ Seat reservation system
- ⬜ Booking management
- ⬜ User reservation overview
- ⬜ Reservation cancellation
- ⬜ Admin reporting (reservations, capacity, revenue)
- ⬜ Email notifications
- ⬜ File upload for movie posters
- ⬜ API documentation with OpenAPI/Swagger
- ⬜ Integration tests
- ⬜ CI/CD pipeline

## Notes on Login and Consent Pages

Although this project is primarily a backend system without a dedicated frontend, it includes a
custom login page (`/login`) and OAuth2 consent page (`/oauth2/consent`).

These pages are included intentionally **for learning and exploration purposes** to understand and
configure the full OAuth2 Authorization Server flow with Spring Authorization Server.

In a production backend-only API, these pages typically would not exist or would be handled by a
separate frontend client. The inclusion here is purely didactic and meant to demonstrate how to
customize these flows.

Future improvements may include separating the Authorization Server and Resource Server
responsibilities and removing these pages to align strictly with a backend API architecture.

## License

This project is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
