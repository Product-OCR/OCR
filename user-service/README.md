# Spring Boot 3 Base Project (OCR System)

## Tech Stack

### Core
- Java 21
- Spring Boot 3.3.5

### Security
- Spring Security
- Password Encoder (BCrypt)

### Authentication
- JWT RS256 (RSA – Asymmetric Encryption)

### Database
- PostgreSQL
- Spring Data JPA

### API Documentation
- Swagger UI (OpenAPI 2.5)

## Security Setup (RSA Keys)

Dự án sử dụng **RSA Public / Private Key** để ký và xác thực JWT.

### Generate Keys
- RSA 2048-bit
---


### Database Configuration

Đảm bảo PostgreSQL đang chạy và đã tạo database.

---

### Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

---

### API Documentation



http://localhost:8080/swagger-ui/index.html

---


## Project Structre
```
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── base
│   │   │           └── demo
│   │   │               ├── configuration
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller
│   │   │               │   ├── auth
│   │   │               │   │   └── AuthController.java
│   │   │               │   └── UserController.java
│   │   │               ├── dto
│   │   │               │   ├── request
│   │   │               │   │   ├── CreateUserRequest.java
│   │   │               │   │   ├── DisableUserRequest.java
│   │   │               │   │   ├── GetUserRequest.java
│   │   │               │   │   ├── LoginRequest.java
│   │   │               │   │   └── UpdateUserRequest.java
│   │   │               │   ├── response
│   │   │               │   │   ├── ApiResponse.java
│   │   │               │   │   ├── CreateUserResponse.java
│   │   │               │   │   ├── DisableUserResponse.java
│   │   │               │   │   ├── GetUserResponse.java
│   │   │               │   │   ├── TokenResponse.java
│   │   │               │   │   └── UpdateUserResponse.java
│   │   │               │   └── ErrorResponse.java
│   │   │               ├── enums
│   │   │               │   ├── ErrorCode.java
│   │   │               │   ├── Role.java
│   │   │               │   └── UserStatus.java
│   │   │               ├── exception
│   │   │               │   ├── DataNotFoundException.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── model
│   │   │               │   ├── BaseEntity.java
│   │   │               │   └── Users.java
│   │   │               ├── repository
│   │   │               │   └── UsersRepository.java
│   │   │               ├── security
│   │   │               │   ├── jwt
│   │   │               │   │   ├── JwtAuthenticationFilter.java
│   │   │               │   │   └── JwtUtils.java
│   │   │               │   └── service
│   │   │               │       └── CustomUserDetailsService.java
│   │   │               ├── service
│   │   │               │   ├── implement
│   │   │               │   │   └── UserServiceImpl.java
│   │   │               │   └── UserService.java
│   │   │               ├── utils
│   │   │               └── OcrApplication.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       ├── application-dev.yaml
│   │       └── application-prod.yaml
│   └── test
│       └── java
│           └── com
│               └── base
│                   └── demo
│                       └── OcrApplicationTests.java
├── .gitattributes
├── .gitignore
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```


---
