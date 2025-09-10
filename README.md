# Expense Tracker - Backend

## Project Overview
This is the backend application for the Expense Tracker project, built with Spring Boot. It provides the API endpoints for user authentication, expense management, and data storage.

## Main Motive
The primary goal of this backend is to securely manage user data and financial records for the Expense Tracker application. It handles user authentication, stores expense details, and provides the necessary data for the frontend to display and analyze.

## Features
- User authentication (Signup, Login)
- RESTful API for managing expenses (CRUD operations)
- Secure password storage
- Session-based authentication
- Data persistence using MySQL

## Technologies Used
- **Spring Boot:** Framework for building robust, production-ready Spring applications.
- **Spring Security:** Provides authentication and authorization features.
- **Spring Data JPA:** Simplifies data access with JPA (Java Persistence API).
- **Hibernate:** ORM (Object-Relational Mapping) framework.
- **MySQL:** Relational database for data storage.
- **Maven:** Build automation tool.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven
- MySQL Database (running locally or accessible)

### Database Setup
1.  **Create a MySQL database:**
    ```sql
    CREATE DATABASE expense_tracker;
    ```
2.  **Configure database credentials:**
    The application uses environment variables for database connection. Ensure these are set in your environment before running the application:
    - `DB_URL`: Your MySQL JDBC URL (e.g., `jdbc:mysql://localhost:3306/expense_tracker?useSSL=false&serverTimezone=UTC`)
    - `DB_USER`: Your MySQL username
    - `DB_PASS`: Your MySQL password

### Installation
1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd ExpenseTracker/backend
    ```
2.  **Build the project:**
    ```bash
    mvn clean install
    ```

### Running the Application
1.  **Start the Spring Boot application:**
    ```bash
    mvn spring-boot:run
    ```
    The backend server will start on `http://localhost:8081`.

## API Endpoints

### Authentication
- `POST /api/auth/signup`: Register a new user.
- `POST /api/auth/login`: Authenticate a user and establish a session.

### Expenses (Requires Authentication)
- `GET /api/expenses`: Retrieve all expenses for the authenticated user.
- `POST /api/expenses`: Add a new expense.
- `DELETE /api/expenses/{id}`: Delete an expense by ID.

## Project Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/ExpenseTracker/ # Java source code
│   │   │   ├── AuthController.java
│   │   │   ├── ExpenseController.java
│   │   │   ├── SecurityConfig.java
│   │   │   └── ...
│   │   └── resources/ # Application properties, etc.
│   │       └── application.properties
│   └── test/ # Test code
├── pom.xml # Maven project file
└── README.md # This file
```

## Learn More
For more details on Spring Boot, Spring Security, or Spring Data JPA, refer to their official documentation.

---