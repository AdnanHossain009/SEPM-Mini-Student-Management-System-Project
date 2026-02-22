# 🎓 Student Management System

A comprehensive web-based Student Management System built with Spring Boot, featuring role-based access control, CRUD operations, and Docker support.

## 📋 Features

### **Two User Roles:**
1. **Teacher Role** - Full administrative access
2. **Student Role** - Limited read access

### **Core Functionality:**
- ✅ **CRUD Operations** for 4 entities:
  - Students
  - Teachers
  - Departments
  - Courses
  
- ✅ **Entity Relationships:**
  - Department → Student (One-to-Many)
  - Student ↔ Course (Many-to-Many)
  - Department → Course (One-to-Many)
  
- ✅ **Security Features:**
  - Spring Security authentication
  - Role-based access control
  - Students **cannot delete** their own information
  - Only teachers can **create/update/delete** student records
  
- ✅ **Docker Support:**
  - Multi-stage Dockerfile for optimized builds
  - Docker Compose with PostgreSQL integration
  - One-click deployment

## 🛠 Tech Stack

- **Backend:** Spring Boot 3.4.2
- **Security:** Spring Security 6
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose
- **Java Version:** 17

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/webapp/
│   │   ├── config/          # Security & App Configuration
│   │   ├── controller/      # MVC Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA Entities
│   │   ├── repository/      # Data Access Layer
│   │   └── service/         # Business Logic Layer
│   └── resources/
│       ├── application.yml  # Application Configuration
│       └── templates/       # Thymeleaf HTML Views
└── test/                    # Unit & Integration Tests
```

## 🚀 Quick Start

### **Option 1: Run with Docker (Recommended)**

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd WebApp-main
   ```

2. **Start the application:**
   ```bash
   docker-compose up --build
   ```

3. **Access the application:**
   - Open your browser: `http://localhost:8080`
   - The database will be automatically initialized with demo data

4. **Stop the application:**
   ```bash
   docker-compose down
   ```

### **Option 2: Run Locally**

**Prerequisites:**
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

1. **Setup PostgreSQL:**
   ```sql
   CREATE DATABASE admindb;
   CREATE USER admin WITH PASSWORD 'admin';
   GRANT ALL PRIVILEGES ON DATABASE admindb TO admin;
   ```

2. **Configure environment variables:**
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/admindb
   export DB_USER=admin
   export DB_PASS=admin
   ```

3. **Build and run:**
   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

4. **Access:** `http://localhost:8080`

## 👤 Demo Credentials

### Teacher Account (Full Access)
- **Username:** `teacher`
- **Password:** `teacher123`
- **Capabilities:**
  - Create, Read, Update, Delete all entities
  - Manage student enrollments
  - Full course management

### Student Account (Limited Access)
- **Username:** `student`
- **Password:** `student123`
- **Capabilities:**
  - View student list
  - View own courses
  - Cannot modify any data

## 📊 Database Schema

```
┌─────────────┐       ┌──────────────┐       ┌──────────┐
│ DEPARTMENTS │◄──────│   STUDENTS   │──────►│  COURSES │
└─────────────┘  1:M  └──────────────┘  M:M  └──────────┘
                            │
                            │ 1:1
                            ▼
                       ┌─────────┐
                       │  USERS  │
                       └─────────┘
                            │
                            │ 1:1
                            ▼
                       ┌──────────┐
                       │ TEACHERS │
                       └──────────┘
```

## 🔒 Security Configuration

- **Authentication:** Database-backed user authentication
- **Password Encryption:** BCrypt
- **Authorization:** Role-based (@PreAuthorize annotations)
- **Session Management:** Form-based login
- **CSRF Protection:** Enabled

### Access Control Matrix:

| Resource        | STUDENT | TEACHER |
|-----------------|---------|---------|
| View Students   | ✅      | ✅      |
| Add Student     | ❌      | ✅      |
| Edit Student    | ❌      | ✅      |
| Delete Student  | ❌      | ✅      |
| Manage Courses  | ❌      | ✅      |
| Manage Depts    | ❌      | ✅      |
| Enroll Student  | ❌      | ✅      |

## 🐳 Docker Configuration

### Dockerfile
- **Stage 1:** Maven build with dependency caching
- **Stage 2:** Lightweight runtime with Eclipse Temurin JRE
- **Image Size:** Optimized with multi-stage build

### Docker Compose Services:
1. **webapp:** Spring Boot application
2. **db:** PostgreSQL 13 database
3. **Networking:** Isolated bridge network

## 📝 API Endpoints

### Public Endpoints:
- `GET /login` - Login page
- `POST /login` - Authentication

### Protected Endpoints:

#### Students (All authenticated users):
- `GET /students` - List all students
- `GET /students/{id}/courses` - View student courses

#### Students (Teachers only):
- `POST /students/store` - Create student
- `GET /students/edit/{id}` - Edit form
- `POST /students/update/{id}` - Update student
- `GET /students/delete/{id}` - Delete student
- `POST /students/{sid}/enroll/{cid}` - Enroll in course

#### Departments (Teachers only):
- `GET /departments` - List departments
- `GET /departments/add` - Add form
- `POST /departments/store` - Create department
- `GET /departments/edit/{id}` - Edit form
- `POST /departments/update/{id}` - Update
- `GET /departments/delete/{id}` - Delete

#### Courses (Teachers only):
- `GET /courses` - List courses
- `GET /courses/add` - Add form
- `POST /courses/store` - Create course
- `GET /courses/edit/{id}` - Edit form
- `POST /courses/update/{id}` - Update
- `GET /courses/delete/{id}` - Delete

## 🧪 Testing

Run tests with:
```bash
./mvnw test
```

## 📦 Building for Production

```bash
# Build JAR
./mvnw clean package -DskipTests

# Build Docker image
docker build -t student-management-system .

# Run container
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/admindb \
  -e DB_USER=admin \
  -e DB_PASS=admin \
  student-management-system
```

## 🔧 Configuration

Key configuration properties in `application.yml`:

```yaml
spring:
  datasource:
    url: ${DB_URL}           # Database URL
    username: ${DB_USER}     # DB Username
    password: ${DB_PASS}     # DB Password
  jpa:
    hibernate:
      ddl-auto: update       # Auto-create/update schema

server:
  port: ${PORT:8080}         # Application port
```

## 🎨 UI Features

- 📱 Responsive design
- 🎨 Modern gradient-based styling
- ✨ Clean and intuitive interface
- 🔐 Role-based UI element visibility
- ⚡ Fast navigation with breadcrumbs

## 📚 Additional Features

1. **Auto Data Initialization:** Demo data loads automatically on first run
2. **Cascade Operations:** Related entities are handled automatically
3. **Transaction Management:** ACID compliance for all operations
4. **Lazy Loading:** Optimized database queries
5. **DTO Pattern:** Clean separation of concerns

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Created as a university project demonstrating enterprise-level Spring Boot application development.

---

**Happy Learning! 🎓**
