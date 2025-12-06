# 🎁 Gift Planner --- Java Spring Backend

A simple, modern, and reliable backend service for organizing gift ideas
for friends and family. Built with **Spring Boot**, **Gradle**,
**MariaDB**, and **JWT authentication**.

## ✨ Overview

This backend powers the full-stack Gift Planner application. It provides
secure endpoints for:

-   Managing user accounts
-   Adding and editing people
-   Adding gift ideas for each person
-   Marking gifts as purchased
-   Importing people and gifts from JSON
-   Returning clean, minimal DTO responses

## 🛠️ Tech Stack

-   **Java 17**
-   **Spring Boot 3.4.x**
-   **Spring Security (JWT)**
-   **Spring Data JPA**
-   **MariaDB**
-   **Gradle**

## 📂 Project Structure

    src/main/java/com/mcckyle/giftplanner/
    ├── controller    # Thin controllers, no business logic
    ├── dto           # Clean request/response DTOs
    ├── model         # Entities: User, Person, Gift, Role
    ├── repository    # Spring Data repositories
    ├── security      # JWT authentication + user details
    └── service       # Core business logic

## 🚀 Getting Started

### 1. Clone the backend repository

``` bash
git clone https://github.com/mcckyle/giftService-java-backend.git
cd giftService-java-backend
```

### 2. Configure MariaDB

Create the database and user:

``` sql
CREATE DATABASE gift_planner;
CREATE USER 'gift_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON gift_planner.* TO 'gift_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Environment Variables

Set the JWT secret before running:

**PowerShell**

``` powershell
$env:GIFT_TOKEN="your_very_long_jwt_secret_key"
```

**Command Prompt**

``` cmd
set GIFT_TOKEN=your_very_long_jwt_secret_key
```

## ⚙️ Run the Backend

``` bash
./gradlew bootRun
```

## 🔑 Authentication

This backend uses **JWT**. Each authenticated request must include:

    Authorization: Bearer <token>

Tokens are returned on successful login.

## 📡 API Summary

### **User**

-   POST /api/auth/register
-   POST /api/auth/login

### **People**

-   POST /api/person/add
-   GET /api/person/my
-   PUT /api/person/{id}
-   DELETE /api/person/{id}

### **Gifts**

-   POST /api/gift/add
-   GET /api/gift/by-person/{personId}
-   PUT /api/gift/{id}
-   DELETE /api/gift/{id}

## 🧪 Testing

Run tests:

``` bash
./gradlew test
```

## 🧱 Build for Production

``` bash
./gradlew clean build
```

## 📄 License

MIT License.