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

## 🏗️ Technical Architecture & Design Highlights

- **4-Tier MVC Architecture**: Strict separation of concerns between Model POJOs, JDBC DAOs, Controller Servlets, and JSP/HTML views.
- **Object-Oriented Design**: Full inheritance hierarchy (`User` $\to$ `Admin`, `Doctor`, `Patient`), encapsulation, polymorphism, and custom interface contracts.
- **Robust JDBC Integration**: Parameterized `PreparedStatement` queries preventing SQL injection with atomic transaction management (`commit`/`rollback`).
- **Asynchronous Multithreading**: Background notification worker thread pool (`ExecutorService`) for non-blocking email/SMS alerts.
- **Dual Database Engine**: Out-of-the-box support for both **MySQL 8.x** and **Embedded H2 In-Memory DB** (zero setup required for live demo).
- **Role-Based Security**: Centralized `AuthenticationFilter` for session validation, access control, and anti-cache HTTP headers.

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
