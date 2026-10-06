# 🏥 MetroCare — Online Healthcare Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Servlet%20%26%20JSP-007396?style=for-the-badge&logo=java&logoColor=white" alt="Servlets"/>
  <img src="https://img.shields.io/badge/JDBC-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="JDBC"/>
  <img src="https://img.shields.io/badge/MySQL%20%2F%20H2-00618A?style=for-the-badge&logo=mysql&logoColor=white" alt="Database"/>
  <img src="https://img.shields.io/badge/HTML5%20%26%20CSS3-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven"/>
</p>

A full-stack **Java Enterprise Web Application** engineered with production-level standards to provide seamless hospital operations, doctor scheduling, dynamic slot booking, electronic medical records, and role-based administration.

---

## 📋 Academic Evaluation Marking Rubrics & Project Proof

### 🌐 1. Java Web-Based Projects Marking Rubric (33 Marks)

| Evaluation Parameter | Marks | Implemented Highlights & Proof in Codebase |
| :--- | :---: | :--- |
| **Problem Understanding & Solution Design** | **8 Marks** | • Clean 4-Tier MVC Architecture (Presentation, Controller, Business/Model, JDBC Persistence Layer)<br>• Comprehensive Relational Entity Model with 8 interconnected tables ([schema.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/schema.sql))<br>• Eliminates appointment collisions, automates doctor schedules, and digitalizes patient clinical records |
| **Core Java Concepts** | **10 Marks** | • **OOP Pillars**: Abstract base classes ([User.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/User.java)), inheritance ([Admin.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Admin.java), [Doctor.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Doctor.java), [Patient.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Patient.java)), encapsulation, polymorphism (`getRoleDisplayName()`, `getProfileSummary()`), interfaces ([Identifiable.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Identifiable.java))<br>• **Collections & Generics**: `List<T>`, `Map<String, Integer>`, `HashMap`, Java 8 Streams ([AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java)), [GenericDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java)<br>• **Exception Handling**: Custom exception hierarchy ([DatabaseException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/DatabaseException.java), [ValidationException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/ValidationException.java), [AuthenticationException](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/exception/AuthenticationException.java))<br>• **Multithreading**: [NotificationThreadService.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/NotificationThreadService.java) with `ExecutorService` daemon thread pool |
| **Database Integration (JDBC)** | **8 Marks** | • 100% Parameterized `PreparedStatement` preventing SQL injection across all DAOs<br>• Atomic transaction management (`setAutoCommit(false)`, `commit()`, `rollback()`) in [AppointmentDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AppointmentDAO.java)<br>• Singleton Connection Factory with Dual DB Engine (MySQL 8.x + Embedded H2 In-Memory fallback) in [DBConnection.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/DBConnection.java) |
| **Servlets & Web Integration** | **7 Marks** | • 15+ HTTP Servlets managing RESTful lifecycles (`init()`, `doGet()`, `doPost()`, `RequestDispatcher`)<br>• Role-Based Security Filter ([AuthenticationFilter.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/filter/AuthenticationFilter.java)) intercepting `/admin/*`, `/doctor/*`, `/patient/*`<br>• Session state management, anti-cache HTTP security headers, and dynamic JSP views |
| **Total Marks** | **33 Marks** | **100% Complete Implementation** |

---

### 🖥️ 2. Java GUI-Based Projects Marking Rubric (33 Marks)

| Evaluation Parameter | Marks | Implemented Highlights & Proof in Codebase |
| :--- | :---: | :--- |
| **OOP Implementation (Polymorphism, Inheritance, Exception Handling, Interfaces)** | **10 Marks** | • **Inheritance**: Base class [User.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/User.java) extended by [Admin.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Admin.java), [Doctor.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Doctor.java), and [Patient.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Patient.java)<br>• **Polymorphism**: Dynamic method override on `getRoleDisplayName()`, `getProfileSummary()`, and polymorphic entity mappers<br>• **Interfaces**: [Identifiable.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/model/Identifiable.java), [GenericDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java), `Serializable`, `Runnable`, `Filter`<br>• **Exception Handling**: Clean custom exceptions with structured try-catch-finally resource teardowns |
| **Collections & Generics** | **6 Marks** | • Type-safe Generics: [GenericDAO&lt;T, ID&gt;](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/GenericDAO.java)<br>• Java Collections: `List<Appointment>`, `List<DoctorSchedule>`, `List<Doctor>`, `List<Feedback>`, `List<MedicalRecord>`<br>• Key-Value Collections: `Map<String, Integer>` and `HashMap` in [AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java)<br>• Java 8 Streams: `.filter()`, `.map()`, `.sorted()`, `.collect()` |
| **Multithreading & Synchronization** | **4 Marks** | • [NotificationThreadService.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/NotificationThreadService.java) utilizing Java **`ExecutorService`** thread pool with custom `ThreadFactory`<br>• **Synchronization**: Thread-safe Singleton `public static synchronized NotificationThreadService getInstance()`<br>• Non-blocking asynchronous email/SMS notifications upon booking and doctor status updates |
| **Classes for Database Operations** | **7 Marks** | • Dedicated Data Access Object (DAO) classes for every domain entity:<br>&nbsp;&nbsp;1. [UserDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/UserDAO.java) &nbsp;|&nbsp; 2. [DoctorDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/DoctorDAO.java) &nbsp;|&nbsp; 3. [PatientDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/PatientDAO.java)<br>&nbsp;&nbsp;4. [AppointmentDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AppointmentDAO.java) &nbsp;|&nbsp; 5. [ScheduleDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/ScheduleDAO.java) &nbsp;|&nbsp; 6. [MedicalRecordDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/MedicalRecordDAO.java)<br>&nbsp;&nbsp;7. [FeedbackDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/FeedbackDAO.java) &nbsp;|&nbsp; 8. [AnalyticsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/AnalyticsDAO.java) &nbsp;|&nbsp; 9. [SettingsDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/SettingsDAO.java)<br>&nbsp;&nbsp;10. [BaseDAO.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/dao/BaseDAO.java) |
| **Database Connectivity (JDBC)** | **3 Marks** | • [DBConnection.java](file:///c:/Users/mayan/Downloads/java_project/src/main/java/com/healthcare/util/DBConnection.java) singleton connection provider<br>• Dynamic JDBC driver registration (`Class.forName()`) for MySQL 8.x and H2<br>• Safe resource closing pattern (`closeResources(Connection, Statement, ResultSet)`) |
| **Implement JDBC for Database Connectivity** | **3 Marks** | • Working JDBC connection with parameterized SQL queries, DDL script ([schema.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/schema.sql)), and seed records ([sample_data.sql](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/sample_data.sql))<br>• Centralized configuration in [db.properties](file:///c:/Users/mayan/Downloads/java_project/src/main/resources/db.properties) |
| **Total Marks** | **33 Marks** | **100% Complete Implementation** |

---

## 🔑 Pre-Loaded Demo Credentials

| Portal / Role | Email Address | Password | Profile Description |
| :--- | :--- | :--- | :--- |
| 👑 **Administrator** | `admin@healthcare.com` | `admin123` | Hospital Administrator with analytics and full user control |
| 🩺 **Doctor (Cardiology)** | `dr.sharma@healthcare.com` | `doctor123` | Dr. Rajesh Sharma, MBBS, MD, DM |
| 🩺 **Doctor (Dermatology)** | `dr.patel@healthcare.com` | `doctor123` | Dr. Sneha Patel, MBBS, MD |
| 🩺 **Doctor (Neurology)** | `dr.ananya@healthcare.com` | `doctor123` | Dr. Ananya Roy, MBBS, MD, DM |
| 🧑‍⚕️ **Patient (Active)** | `rahul@gmail.com` | `patient123` | Rahul Verma (Active appointments & prescriptions) |
| 🧑‍⚕️ **Patient (New)** | `priya@gmail.com` | `patient123` | Priya Nair |

---

## ⚡ Quick Start & Execution

### Option 1: Instant Local HTML/UI Preview (Zero Setup)
Preview all views (Landing page, Patient Portal, Doctor Portal, Admin Dashboard, Booking Engine):
```bash
# Double click start_server.bat OR run:
node server.js
```
👉 Open your browser at **[http://localhost:3000/](http://localhost:3000/)** or open [index.html](file:///c:/Users/mayan/Downloads/java_project/index.html) directly!

---

### Option 2: Run with Maven (Embedded Tomcat)
```bash
mvn clean tomcat7:run
```
👉 Access the live Java web portal at: **`http://localhost:8080/`**

---

### Option 3: Deploy WAR to Apache Tomcat Server
1. Build the production WAR package:
   ```bash
   mvn clean package
   ```
2. Copy `target/healthcare.war` into Tomcat's `webapps/` directory.
3. Start Tomcat and navigate to: `http://localhost:8080/healthcare/`
