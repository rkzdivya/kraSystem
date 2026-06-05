# KRA System - Backend

A Spring Boot backend system for managing KRA (Key Result Area) forms, user authentication, and email notifications.

## Tech Stack
- Java / Spring Boot
- JWT Authentication
- Firebase Integration
- Email Service
- MySQL Database

## Features
-  User Management (Create, Update, Delete Users)
-  KRA Form Handling (Create, Update, Manage KRA Forms)
-  User Authentication & Authorization (JWT)
-  Email Notification Service
-  File Storage Service
-  REST APIs with Global Exception Handling

## Project Structure
src/
├── controller/
│   ├── AuthController
│   ├── UserController
│   ├── KraController
│   └── KraFormController
├── service/
│   ├── AuthService
│   ├── UserService
│   ├── KraService
│   ├── KraFormService
│   ├── EmailService
│   ├── StorageService
│   └── jwt/
│       ├── JwtService
│       └── JwtAuthenticationFilter
├── model/
│   ├── User
│   ├── Kra
│   ├── KraForm
│   ├── KraField
│   ├── KraCategory
│   └── Role
├── repository/
├── dto/
├── request/
├── response/
├── config/
│   ├── SecurityConfig
│   ├── ApplicationConfig
│   └── DatabaseConfig
└── exception/
└── GlobalExceptionHandler

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL

### Run the project
./mvnw spring-boot:run

### Build the project
./mvnw clean install

## API Testing
A Postman collection `KraSystem.postman_collection.json` is included in the repository.

### Import in Postman:
1. Open Postman
2. Click **Import**
3. Select `KraSystem.postman_collection.json`
4. All endpoints will be available to test

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login user |
| POST | `/auth/refresh-token` | Refresh JWT token |

### User
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/all` | Get all users |
| GET | `/user/{id}` | Get user by ID |
| PUT | `/user/update/{id}` | Update user |
| DELETE | `/user/delete/{id}` | Delete user |

### KRA
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/kra/create` | Create KRA |
| GET | `/kra/all` | Get all KRAs |
| PUT | `/kra/update/{id}` | Update KRA |
| DELETE | `/kra/delete/{id}` | Delete KRA |

### KRA Form
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/kra-form/save` | Save KRA Form |
| GET | `/kra-form/all` | Get all KRA Forms |
| GET | `/kra-form/{id}` | Get KRA Form by ID |

## Contributing
Pull requests are welcome. For major changes, please open an issue first.

## License
This project is for personal portfolio use only.
