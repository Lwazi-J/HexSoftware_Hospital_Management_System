# 🏥 Rearview Hospital Management System

A comprehensive hospital management system built with **Spring Boot** and **JDBC** for backend operations and a beautiful **HTML/CSS/JavaScript** frontend for user interaction.

## 🌟 Features

### 🎯 Core Modules
- **Patient Management** - Complete CRUD operations for patient records
- **Doctor Management** - Manage doctor profiles and specializations
- **Department Management** - Hospital department organization
- **Appointment Scheduling** - Patient-doctor appointment system
- **Staff Management** - Hospital staff records and roles
- **Prescription Management** - Medication tracking and prescriptions

### 🎨 Frontend Features
- **Beautiful Welcome Screen** - Professional hospital branding
- **Responsive Design** - Works on desktop and mobile devices
- **Real-time Data Updates** - Instant refresh after operations
- **Modern UI/UX** - Gradient colors, animations, and smooth transitions
- **Secure Authentication** - Login system with session management

### 🔧 Technical Stack
- **Backend**: Spring Boot 3.5.3, Spring JDBC, Spring Security
- **Database**: MySQL 8.0
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher installed
- **MySQL 8.0** or higher installed and running
- **Maven 3.6+** installed
- **Git** (for cloning the repository)

## 🚀 Quick Start

### 1. Database Setup

```sql
-- Create database
CREATE DATABASE hospital_db;

-- Create user (optional)
CREATE USER 'hospital_user'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON hospital_db.* TO 'hospital_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Clone and Configure

```bash
# Clone the repository
git clone <repository-url>
cd HexSoftware_Hospital_Management_System

# Update database credentials in src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=1234
```

### 3. Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or using Maven Wrapper
./mvnw spring-boot:run

# Or run the JAR file
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application

- **Frontend**: http://localhost:8080/hospital/
- **API Base URL**: http://localhost:8080/hospital/api

## 🖥️ Using the Frontend

### Login Credentials
- **Username**: `admin`
- **Password**: `admin123`

### Navigation Flow
1. **Welcome Screen** - Beautiful landing page with hospital branding
2. **Click "Access System"** - Navigate to login screen
3. **Login** - Enter credentials to access dashboard
4. **Dashboard** - Full management system with 6 main modules

### Available Modules
- **Patients** - Add, view, edit, delete patient records
- **Doctors** - Manage doctor profiles and specializations
- **Appointments** - Schedule and manage appointments
- **Departments** - Organize hospital departments
- **Staff** - Manage hospital staff records
- **Prescriptions** - Track medications and prescriptions

## 🔌 API Endpoints

### Authentication
```
POST /hospital/api/auth/login
Body: {"username": "admin", "password": "admin123"}
```

### Patients
```
GET    /hospital/api/patients           # Get all patients
GET    /hospital/api/patients/{id}      # Get patient by ID
POST   /hospital/api/patients           # Create new patient
PUT    /hospital/api/patients/{id}      # Update patient
DELETE /hospital/api/patients/{id}      # Delete patient
GET    /hospital/api/patients/search?name=John&bloodType=O+  # Search patients
```

### Doctors
```
GET    /hospital/api/doctors                    # Get all doctors
GET    /hospital/api/doctors/{id}               # Get doctor by ID
POST   /hospital/api/doctors                    # Create new doctor
PUT    /hospital/api/doctors/{id}               # Update doctor
DELETE /hospital/api/doctors/{id}               # Delete doctor
GET    /hospital/api/doctors/specialization/{spec}  # Get doctors by specialization
```

### Departments
```
GET    /hospital/api/departments         # Get all departments
GET    /hospital/api/departments/{id}    # Get department by ID
POST   /hospital/api/departments         # Create new department
PUT    /hospital/api/departments/{id}    # Update department
DELETE /hospital/api/departments/{id}    # Delete department
GET    /hospital/api/departments/{id}/doctors  # Get doctors in department
```

### Appointments
```
GET    /hospital/api/appointments                    # Get all appointments
GET    /hospital/api/appointments/{id}               # Get appointment by ID
POST   /hospital/api/appointments                    # Create new appointment
PUT    /hospital/api/appointments/{id}               # Update appointment
DELETE /hospital/api/appointments/{id}               # Delete appointment
GET    /hospital/api/appointments/patient/{id}       # Get patient appointments
GET    /hospital/api/appointments/doctor/{id}        # Get doctor appointments
PUT    /hospital/api/appointments/{id}/status?status=CONFIRMED  # Update status
```

### Staff
```
GET    /hospital/api/staff           # Get all staff
GET    /hospital/api/staff/{id}      # Get staff by ID
POST   /hospital/api/staff           # Create new staff
PUT    /hospital/api/staff/{id}      # Update staff
DELETE /hospital/api/staff/{id}      # Delete staff
GET    /hospital/api/staff/role/{role}  # Get staff by role
```

### Prescriptions
```
GET    /hospital/api/prescriptions                # Get all prescriptions
GET    /hospital/api/prescriptions/{id}           # Get prescription by ID
POST   /hospital/api/prescriptions                # Create new prescription
PUT    /hospital/api/prescriptions/{id}           # Update prescription
DELETE /hospital/api/prescriptions/{id}           # Delete prescription
GET    /hospital/api/prescriptions/patient/{id}   # Get patient prescriptions
GET    /hospital/api/prescriptions/doctor/{id}    # Get doctor prescriptions
```

## 🧪 Testing with Postman

### 1. Import Collection
Create a new Postman collection with the base URL: `http://localhost:8080/hospital/api`

### 2. Authentication Test
```
POST /auth/login
Headers: Content-Type: application/json
Body: {
  "username": "admin",
  "password": "admin123"
}
Expected: 200 OK with token response
```

### 3. Sample Patient Creation
```
POST /patients
Headers: Content-Type: application/json
Body: {
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "gender": "Male",
  "address": "123 Main St",
  "phoneNumber": "555-0123",
  "email": "john.doe@email.com",
  "bloodType": "O+",
  "registrationDate": "2024-01-15",
  "insuranceProvider": "HealthCare Inc",
  "insurancePolicyNumber": "HC123456"
}
Expected: 201 Created
```

### 4. Sample Department Creation
```
POST /departments
Headers: Content-Type: application/json
Body: {
  "name": "Cardiology",
  "description": "Heart and cardiovascular care",
  "location": "Building A, Floor 2",
  "staffCount": 15,
  "contactNumber": "555-0789"
}
Expected: 201 Created
```

### 5. Testing Order
1. **Create Departments** first (needed for doctors)
2. **Create Doctors** (need department IDs)
3. **Create Patients**
4. **Create Staff**
5. **Create Appointments** (need patient and doctor IDs)
6. **Create Prescriptions** (need patient and doctor IDs)

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Database and Security configuration
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Custom exceptions and handlers
│   │   ├── model/          # Entity models
│   │   ├── repository/     # JDBC repositories
│   │   ├── service/        # Business logic services
│   │   └── HospitalManagementApplication.java
│   └── resources/
│       ├── static/         # Frontend files (HTML, CSS, JS)
│       ├── application.properties
│       ├── schema.sql      # Database schema
│       └── data.sql        # Sample data
```

## 🛠️ Configuration

### Database Configuration
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Server
server.port=8080
server.servlet.context-path=/hospital

# Database Initialization
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
```

### Security Configuration
- All API endpoints are accessible without authentication for testing
- Login system uses hardcoded credentials (admin/admin123)
- CSRF disabled for API testing

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials in `application.properties`
   - Verify database `hospital_db` exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or kill process using port 8080

3. **Frontend Not Loading**
   - Ensure you're accessing `http://localhost:8080/hospital/`
   - Check browser console for JavaScript errors

4. **API Endpoints Not Working**
   - Verify application started successfully
   - Check logs for any startup errors
   - Test with simple GET request first

### Logs Location
- Application logs appear in console
- Check for any ERROR or WARN messages during startup

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Support

For support or questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the API documentation above

---

**Built with ❤️ for Rearview Hospital**