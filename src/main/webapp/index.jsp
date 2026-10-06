<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="MetroCare - Online Healthcare Management System"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<section class="hero-section">
    <div style="max-width: 900px; margin: 0 auto;">
        <span class="badge badge-confirmed" style="background: rgba(224, 242, 254, 0.2); color: #7dd3fc; margin-bottom: 1.5rem; padding: 0.5rem 1.25rem;">
            <i class="fa-solid fa-sparkles"></i> Next-Gen Healthcare Management
        </span>
        <h1 class="hero-title">Intelligent Healthcare at Your Fingertips</h1>
        <p class="hero-subtitle">
            Seamlessly connect with top-rated medical specialists, manage digital prescriptions, schedule consultations in real-time, and track your health journey.
        </p>
        <div style="display: flex; gap: 1rem; justify-content: center; flex-wrap: wrap;">
            <a href="${pageContext.request.contextPath}/patient/search-doctors" class="btn btn-primary btn-lg">
                <i class="fa-solid fa-magnifying-glass"></i> Find a Doctor
            </a>
            <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-secondary btn-lg" style="border: 1px solid rgba(255,255,255,0.2);">
                <i class="fa-solid fa-user-plus"></i> Patient Portal Sign Up
            </a>
        </div>
    </div>
</section>

<main class="main-content">
    <jsp:include page="includes/alerts.jsp"/>

    <div style="margin-bottom: 3.5rem;">
        <div style="text-align: center; margin-bottom: 2.5rem;">
            <h2 style="font-size: 2.2rem; margin-bottom: 0.5rem;">Comprehensive Care for Everyone</h2>
            <p style="color: var(--text-muted);">Empowering Administrators, Doctors, and Patients with dedicated workflows.</p>
        </div>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); gap: 2rem;">
            <!-- Admin Role Card -->
            <div class="card" style="border-top: 4px solid var(--primary);">
                <div class="stat-icon blue" style="margin-bottom: 1.25rem;"><i class="fa-solid fa-shield-halved"></i></div>
                <h3 style="margin-bottom: 0.75rem;">Admin Management</h3>
                <p style="color: var(--text-muted); font-size: 0.95rem; margin-bottom: 1.5rem;">
                    Full operational control: manage doctors and patient profiles, inspect all appointments, configure hospital parameters, and monitor live analytics.
                </p>
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-primary btn-sm">Admin Access &rarr;</a>
            </div>

            <!-- Doctor Role Card -->
            <div class="card" style="border-top: 4px solid var(--accent);">
                <div class="stat-icon" style="background-color: #cffafe; color: #0891b2; margin-bottom: 1.25rem;"><i class="fa-solid fa-stethoscope"></i></div>
                <h3 style="margin-bottom: 0.75rem;">Doctor Portal</h3>
                <p style="color: var(--text-muted); font-size: 0.95rem; margin-bottom: 1.5rem;">
                    Manage customizable consultation schedules, review upcoming patient queues, update diagnoses, prescribe medications, and inspect patient feedback.
                </p>
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-primary btn-sm">Doctor Access &rarr;</a>
            </div>

            <!-- Patient Role Card -->
            <div class="card" style="border-top: 4px solid var(--success);">
                <div class="stat-icon green" style="margin-bottom: 1.25rem;"><i class="fa-solid fa-heart-pulse"></i></div>
                <h3 style="margin-bottom: 0.75rem;">Patient Experience</h3>
                <p style="color: var(--text-muted); font-size: 0.95rem; margin-bottom: 1.5rem;">
                    Search specialists by medical department, view real-time open slots, book instant consultations, download medical histories, and rate doctors.
                </p>
                <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-outline-primary btn-sm">Get Started &rarr;</a>
            </div>
        </div>
    </div>

    <!-- Demo Credentials Helper Card -->
    <div class="card" style="background: linear-gradient(135deg, #f0fdf4 0%, #ffffff 100%); border: 1px solid #bbf7d0; margin-bottom: 3rem;">
        <div style="display: flex; gap: 1rem; align-items: flex-start;">
            <div class="stat-icon green" style="width: 44px; height: 44px; font-size: 1.2rem;"><i class="fa-solid fa-key"></i></div>
            <div style="flex: 1;">
                <h4 style="color: #166534; margin-bottom: 0.5rem;">Quick Evaluation Demo Credentials</h4>
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 1rem; font-size: 0.9rem; color: #14532d;">
                    <div>
                        <strong>Admin Portal:</strong><br/>
                        Email: <code>admin@healthcare.com</code><br/>
                        Password: <code>admin123</code>
                    </div>
                    <div>
                        <strong>Doctor Portal:</strong><br/>
                        Email: <code>dr.sharma@healthcare.com</code><br/>
                        Password: <code>doctor123</code>
                    </div>
                    <div>
                        <strong>Patient Portal:</strong><br/>
                        Email: <code>rahul@gmail.com</code><br/>
                        Password: <code>patient123</code>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="includes/footer.jsp"/>
