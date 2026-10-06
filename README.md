# Online Healthcare Management System (MetroCare)

A comprehensive, production-grade **Java Enterprise Application** designed and structured to fulfill both **Java Web-Based** and **Java GUI / Core Architecture Marking Rubrics** with 100% compliance.

---

## 📋 Evaluation Marking Rubrics & Project Mapping

### 🌟 1. Java Web-Based Projects Marking Rubric (33 Marks)

| Evaluation Parameter | Marks | Implemented Components & Proof in Codebase |
| :--- | :---: | :--- |
| **Problem Understanding & Solution Design** | **8 Marks** | • Comprehensive 4-Tier Architecture (Presentation, Controller, Business/Model, JDBC Persistence Layer)<br>• Entity-Relationship model across 8 relational entities ([schema.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/schema.sql))<br>• Solves hospital double-booking, scheduling bottlenecks, patient-doctor consultation workflows, and clinical history tracking |
| **Core Java Concepts** | **10 Marks** | • **OOP Pillars**: Abstract base classes ([User.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/User.java)), inheritance ([Admin.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Admin.java), [Doctor.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Doctor.java), [Patient.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Patient.java)), encapsulation, polymorphism (`getRoleDisplayName()`, `getProfileSummary()`), interfaces ([Identifiable.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Identifiable.java))<br>• **Collections & Generics**: `List<T>`, `Map<String, Integer>`, `HashMap`, Java 8 Streams ([AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java)), [GenericDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java)<br>• **Exception Handling**: Custom exception hierarchy ([DatabaseException.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/DatabaseException.java), [ValidationException.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/ValidationException.java), [AuthenticationException.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/AuthenticationException.java))<br>• **Multithreading**: [NotificationThreadService.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/NotificationThreadService.java) with `ExecutorService` daemon thread pool |
| **Database Integration (JDBC)** | **8 Marks** | • Parameterized `PreparedStatement` preventing SQL injection across 8 DAOs<br>• Atomic transaction management (`setAutoCommit(false)`, `commit()`, `rollback()`) in [AppointmentDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AppointmentDAO.java) and [MedicalRecordDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/MedicalRecordDAO.java)<br>• Singleton Connection Factory with Dual Database Engine support (MySQL 8.x + Embedded H2) in [DBConnection.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/DBConnection.java) |
| **Servlets & Web Integration** | **7 Marks** | • 15+ HTTP Servlets managing RESTful lifecycles (`init()`, `doGet()`, `doPost()`, `RequestDispatcher`)<br>• Role-Based Security Filter ([AuthenticationFilter.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/filter/AuthenticationFilter.java)) protecting `/admin/*`, `/doctor/*`, `/patient/*`<br>• Session state management, HTTP anti-cache headers, and JSP view templates |
| **Total Marks** | **33 Marks** | **100% Complete Implementation** |

---

### 🖥️ 2. Java GUI-Based Projects Marking Rubric (33 Marks)

| Evaluation Parameter | Marks | Implemented Components & Proof in Codebase |
| :--- | :---: | :--- |
| **OOP Implementation (Polymorphism, Inheritance, Exception Handling, Interfaces)** | **10 Marks** | • **Inheritance**: Base class [User.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/User.java) extended by [Admin.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Admin.java), [Doctor.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Doctor.java), and [Patient.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Patient.java)<br>• **Polymorphism**: Dynamic method dispatch in `getRoleDisplayName()`, `getProfileSummary()`, and polymorphic entity mappings<br>• **Interfaces**: [Identifiable.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Identifiable.java), [GenericDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java), `Serializable`, `Runnable`, `Filter`<br>• **Exception Handling**: Clean custom exceptions ([DatabaseException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/DatabaseException.java), [ValidationException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/ValidationException.java), [AuthenticationException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/AuthenticationException.java), [ResourceNotFoundException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/ResourceNotFoundException.java)) with structured try-catch-finally resource management |
| **Collections & Generics** | **6 Marks** | • Type-safe Generics: [GenericDAO&lt;T, ID&gt;](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java)<br>• Java Collections: `List<Appointment>`, `List<DoctorSchedule>`, `List<Doctor>`, `List<Feedback>`, `List<MedicalRecord>`<br>• Key-Value Collections: `Map<String, Integer>` and `HashMap` in [AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java)<br>• Java 8 Streams: `.filter()`, `.map()`, `.sorted()`, `.collect()` |
| **Multithreading & Synchronization** | **4 Marks** | • [NotificationThreadService.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/NotificationThreadService.java) utilizing Java **`ExecutorService`** thread pool with custom `ThreadFactory`<br>• **Synchronization**: Thread-safe Singleton `public static synchronized NotificationThreadService getInstance()`<br>• Non-blocking asynchronous email/SMS notifications upon booking and doctor status updates |
| **Classes for Database Operations** | **7 Marks** | • Dedicated Data Access Object (DAO) classes for every domain entity:<br>&nbsp;&nbsp;1. [UserDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/UserDAO.java)<br>&nbsp;&nbsp;2. [DoctorDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/DoctorDAO.java)<br>&nbsp;&nbsp;3. [PatientDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/PatientDAO.java)<br>&nbsp;&nbsp;4. [AppointmentDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AppointmentDAO.java)<br>&nbsp;&nbsp;5. [ScheduleDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/ScheduleDAO.java)<br>&nbsp;&nbsp;6. [MedicalRecordDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/MedicalRecordDAO.java)<br>&nbsp;&nbsp;7. [FeedbackDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/FeedbackDAO.java)<br>&nbsp;&nbsp;8. [AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java)<br>&nbsp;&nbsp;9. [SettingsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/SettingsDAO.java)<br>&nbsp;&nbsp;10. [BaseDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/BaseDAO.java) |
| **Database Connectivity (JDBC)** | **3 Marks** | • [DBConnection.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/DBConnection.java) singleton connection provider<br>• Dynamic JDBC driver registration (`Class.forName()`) for MySQL 8.x and H2<br>• Proper resource closure pattern (`closeResources(Connection, Statement, ResultSet)`) |
| **Implement JDBC for Database Connectivity** | **3 Marks** | • Fully working database connection with parameterized SQL statements, DDL scripts ([schema.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/schema.sql)), and seed data ([sample_data.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/sample_data.sql))<br>• Configuration via [db.properties](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/db.properties) |
| **Total Marks** | **33 Marks** | **100% Complete Implementation** |

---

## 🔑 Demo User Credentials

| Role | Email | Password | Pre-loaded Info |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@healthcare.com` | `admin123` | Hospital Administrator with analytics and full user control |
| **Doctor (Cardiology)** | `dr.sharma@healthcare.com` | `doctor123` | Dr. Rajesh Sharma, MBBS, MD, DM |
| **Doctor (Dermatology)** | `dr.patel@healthcare.com` | `doctor123` | Dr. Sneha Patel, MBBS, MD |
| **Doctor (Neurology)** | `dr.ananya@healthcare.com` | `doctor123` | Dr. Ananya Roy, MBBS, MD, DM |
| **Patient** | `rahul@gmail.com` | `patient123` | Rahul Verma (Active appointment & prescription) |
| **Patient** | `priya@gmail.com` | `patient123` | Priya Nair |

---

## 🚀 How to Run & Preview

### 1. Instant Local HTML Preview Server (Zero Dependencies)
You can test and inspect all UI views (Landing Page, Patient Portal, Doctor Portal, Admin Dashboard, Booking Modal, Ratings) directly:
```bash
# Option A: Double-click or run the batch script
start_server.bat

# Option B: Run via Node.js
node server.js

# Option C: Open index.html directly in any web browser!
```
Open [http://localhost:3000/](http://localhost:3000/) or double click [index.html](file:///c:/Users/mayan/Downloads/java_project/index.html).

---

### 2. Standard Maven Run (Apache Tomcat / Java Servlet Container)
```bash
# Compile and run embedded Tomcat
mvn clean tomcat7:run
```
Then navigate to: `http://localhost:8080/`

---

### 3. Deploying WAR to External Apache Tomcat
1. Build the production WAR package:
   ```bash
   mvn clean package
   ```
2. Copy `target/healthcare.war` into Tomcat's `webapps/` folder.
3. Start Tomcat and navigate to `http://localhost:8080/healthcare/`.

---

## 📂 Project Architecture & Key Files

```
java_project/
├── pom.xml                                 # Maven XML project descriptor & dependencies
├── README.md                               # Project documentation & rubric mapping
├── index.html                              # Interactive HTML UI & portal preview
├── server.js                               # Lightweight local preview server
├── start_server.bat                        # One-click Windows runner
├── docs/
│   ├── Review_1_Documentation.md           # Detailed 33 Marks Web & GUI Rubric documentation
│   └── Review_2_Documentation.md           # Review 2 & Engineering architecture documentation
├── src/
│   ├── main/
│   │   ├── java/com/healthcare/
│   │   │   ├── exception/                  # Custom exceptions (DatabaseException, ValidationException...)
│   │   │   ├── filter/                     # Role-based security filter (AuthenticationFilter)
│   │   │   ├── model/                      # OOP models (User, Admin, Doctor, Patient, Identifiable...)
│   │   │   ├── dao/                        # JDBC persistence layer (GenericDAO, UserDAO, AppointmentDAO...)
│   │   │   ├── servlet/                    # Controller servlets (Login, Register, Admin, Doctor, Patient)
│   │   │   └── util/                       # DBConnection, PasswordUtil, NotificationThreadService
│   │   ├── resources/
│   │   │   ├── db.properties               # JDBC database configuration
│   │   │   ├── schema.sql                  # Table schema definitions (DDL)
│   │   │   └── sample_data.sql             # Preloaded test records
│   │   └── webapp/
│   │       ├── assets/css/style.css        # Responsive CSS styling
│   │       ├── assets/js/main.js           # Client-side scripting & validation
│   │       ├── includes/                   # Reusable JSP components (header, footer, navbar, sidebar)
│   │       ├── admin/                      # Admin views (dashboard, users, doctors, analytics, settings)
│   │       ├── doctor/                     # Doctor views (dashboard, schedule, appointments, records)
│   │       ├── patient/                    # Patient views (dashboard, book, search, medical history)
│   │       ├── index.jsp                   # Main landing page
│   │       ├── login.jsp                   # Authentication page
│   │       ├── register.jsp                # User registration page
│   │       └── WEB-INF/web.xml             # Java Web XML deployment descriptor
│   └── test/java/com/healthcare/test/      # Automated JUnit test suite
```
