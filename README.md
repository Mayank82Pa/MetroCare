# 🏥 MetroCare — Online Healthcare Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Servlet%20%26%20JSP-007396?style=for-the-badge&logo=java&logoColor=white" alt="Servlets"/>
  <img src="https://img.shields.io/badge/JDBC-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="JDBC"/>
  <img src="https://img.shields.io/badge/MySQL%20%2F%20H2-00618A?style=for-the-badge&logo=mysql&logoColor=white" alt="Database"/>
  <img src="https://img.shields.io/badge/HTML5%20%26%20CSS3-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven"/>
</p>

A full-stack **Java Enterprise Web Application** engineered to provide seamless hospital operations, doctor availability scheduling, conflict-free dynamic appointment booking, digital prescriptions, and role-based administration portals.

---

## 👥 Project Team

| Role | Name |
| :--- | :--- |
| 👑 **Team Leader** | **Mayank Patel** |
| 👨‍💻 **Team Member** | **Kabir Kumar** |
| 👨‍💻 **Team Member** | **Abhinav Kumar** |
| 👨‍💻 **Team Member** | **Shashank Mishra** |

---

## 🏛️ Code & System Architecture

MetroCare follows an enterprise-grade **4-Tier MVC (Model-View-Controller) Architecture** ensuring high cohesion, loose coupling, and clear separation of concerns:

```mermaid
graph TD
    subgraph Presentation_Layer ["1. Presentation Layer (View)"]
        UI1["JSP Dynamic Templates (Admin, Doctor, Patient)"]
        UI2["Interactive HTML5 / CSS3 / JavaScript (Assets)"]
    end

    subgraph Security_Controller_Layer ["2. Security & Controller Layer"]
        F1["AuthenticationFilter (Role-Based Access & Anti-Cache)"]
        S1["Admin Servlets (Users, Appointments, Analytics, Settings)"]
        S2["Doctor Servlets (Schedule, Consultations, Records)"]
        S3["Patient Servlets (Booking, Search, Feedback, History)"]
    end

    subgraph Business_Model_Layer ["3. Business & Domain Layer"]
        M1["OOP Models (User, Admin, Doctor, Patient, Appointment)"]
        M2["Validation & Security (ValidationUtil, PasswordUtil SHA-256)"]
        M3["Async Workers (NotificationThreadService ExecutorPool)"]
    end

    subgraph Persistence_Layer ["4. Data Access Layer (DAO)"]
        D1["GenericDAO Interface & BaseDAO Abstraction"]
        D2["Entity DAOs (UserDAO, DoctorDAO, PatientDAO, AppointmentDAO...)"]
        D3["Atomic Transactions (commit / rollback) & PreparedStatements"]
    end

    subgraph Database_Layer ["5. Database Infrastructure"]
        DB1["DBConnection Factory (Singleton Driver Manager)"]
        DB2[("MySQL 8.x Database / Embedded H2 In-Memory DB")]
    end

    Presentation_Layer --> Security_Controller_Layer
    Security_Controller_Layer --> Business_Model_Layer
    Security_Controller_Layer --> Persistence_Layer
    Persistence_Layer --> Database_Layer
```

### 🧩 Architectural Layers & Design Patterns

| Layer | Primary Responsibilities | Design Patterns & Technologies |
| :--- | :--- | :--- |
| **Presentation (View)** | Renders dynamic role-tailored dashboards, consultation booking UI, and interactive feedback forms | JSP, JSTL, HTML5, Custom Responsive CSS, Vanilla JS |
| **Controller & Security** | Intercepts HTTP requests, verifies session authorization, validates inputs, and coordinates responses | `HttpServlet` (`doGet`/`doPost`), `Filter`, Front-Controller dispatching |
| **Domain & Business** | Implements core business logic, user inheritance hierarchy, validation, and async tasks | OOP Inheritance, Polymorphism, Singleton (`NotificationThreadService`) |
| **Persistence (DAO)** | Handles parameterized database queries, prevents SQL injection, and manages atomic transactions | Data Access Object (DAO) Pattern, `GenericDAO<T, ID>`, PreparedStatements |
| **Database (JDBC)** | Dual-database connectivity supporting production MySQL and zero-config in-memory H2 | JDBC Connection Factory, Driver Manager, Connection Pooling |

---

## 🌟 Key Features & Modules

### 👑 Administrator Portal
- **User Management**: Activate/deactivate accounts and assign doctor/patient profiles.
- **Master Appointment Hub**: View and manage all hospital consultations across departments.
- **Analytics Dashboard**: Real-time KPI summaries, department workloads, and revenue metrics.
- **System Settings**: Configure hospital parameters, business rules, and operating hours.

### 🩺 Doctor Portal
- **Shift & Availability Manager**: Define consultation schedules, custom hours, and slot intervals (15m, 30m, 60m).
- **Patient Queue**: View upcoming consultations, accept/cancel requests, and track daily caseload.
- **Digital Medical Records**: Issue diagnosis reports, electronic prescriptions, and follow-up plans.
- **Patient Reviews**: Read verified patient feedback and track overall performance ratings.

### 🧑‍⚕️ Patient Portal
- **Smart Doctor Search**: Filter specialists by department, experience, fee, and availability.
- **Dynamic Slot Booking**: Real-time conflict-free appointment booking with automatic slot calculation.
- **Medical History**: Access past clinical notes, diagnosis records, and download prescriptions.
- **Doctor Feedback**: Submit star ratings and clinical reviews after consultation completion.

---

## 🔑 Demo User Credentials

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
Preview all interactive views (Landing page, Patient Portal, Doctor Portal, Admin Dashboard, Booking Modal):
```bash
# Double click start_server.bat OR run:
node server.js
```
👉 Open your browser at **[http://localhost:3000/](http://localhost:3000/)** or double click [index.html](file:///c:/Users/mayan/Downloads/java_project/index.html).

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
