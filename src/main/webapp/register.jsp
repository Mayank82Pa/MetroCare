<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Patient Registration - MetroCare"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<div class="main-content" style="max-width: 750px; margin-top: 2rem;">
    <div class="card" style="padding: 2.5rem; box-shadow: var(--shadow-lg);">
        <div style="text-align: center; margin-bottom: 2rem;">
            <div class="stat-icon green" style="margin: 0 auto 1rem auto; width: 60px; height: 60px; font-size: 1.8rem;">
                <i class="fa-solid fa-user-plus"></i>
            </div>
            <h2 style="font-size: 1.8rem;">Create Patient Account</h2>
            <p style="color: var(--text-muted); font-size: 0.95rem;">Join MetroCare to book consultations and manage your health records</p>
        </div>

        <jsp:include page="includes/alerts.jsp"/>

        <form action="${pageContext.request.contextPath}/register" method="POST">
            <h4 style="margin-bottom: 1rem; color: var(--primary); font-size: 1.05rem;">
                <i class="fa-solid fa-id-card"></i> 1. Account Credentials
            </h4>
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="name">Full Name *</label>
                    <input type="text" id="name" name="name" class="form-control" required value="${formName != null ? formName : ''}" placeholder="e.g. John Doe">
                </div>
                <div class="form-group">
                    <label class="form-label" for="email">Email Address *</label>
                    <input type="email" id="email" name="email" class="form-control" required value="${formEmail != null ? formEmail : ''}" placeholder="john@example.com">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="password">Password (min 6 chars) *</label>
                    <input type="password" id="password" name="password" class="form-control" required placeholder="••••••••">
                </div>
                <div class="form-group">
                    <label class="form-label" for="confirmPassword">Confirm Password *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required placeholder="••••••••">
                </div>
            </div>

            <h4 style="margin: 1.5rem 0 1rem 0; color: var(--primary); font-size: 1.05rem;">
                <i class="fa-solid fa-notes-medical"></i> 2. Patient Profile & Demographics
            </h4>
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="phone">Phone Number *</label>
                    <input type="tel" id="phone" name="phone" class="form-control" required value="${formPhone != null ? formPhone : ''}" placeholder="+91 98765 43210">
                </div>
                <div class="form-group">
                    <label class="form-label" for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${formDob != null ? formDob : ''}">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="gender">Gender</label>
                    <select id="gender" name="gender" class="form-control">
                        <option value="">Select Gender</option>
                        <option value="Male" ${formGender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${formGender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${formGender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="form-label" for="bloodGroup">Blood Group</label>
                    <select id="bloodGroup" name="bloodGroup" class="form-control">
                        <option value="">Select Blood Group</option>
                        <option value="A+" ${formBlood == 'A+' ? 'selected' : ''}>A+</option>
                        <option value="A-" ${formBlood == 'A-' ? 'selected' : ''}>A-</option>
                        <option value="B+" ${formBlood == 'B+' ? 'selected' : ''}>B+</option>
                        <option value="B-" ${formBlood == 'B-' ? 'selected' : ''}>B-</option>
                        <option value="O+" ${formBlood == 'O+' ? 'selected' : ''}>O+</option>
                        <option value="O-" ${formBlood == 'O-' ? 'selected' : ''}>O-</option>
                        <option value="AB+" ${formBlood == 'AB+' ? 'selected' : ''}>AB+</option>
                        <option value="AB-" ${formBlood == 'AB-' ? 'selected' : ''}>AB-</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="emergencyContact">Emergency Contact</label>
                    <input type="tel" id="emergencyContact" name="emergencyContact" class="form-control" value="${formEmergency != null ? formEmergency : ''}" placeholder="Emergency contact number">
                </div>
                <div class="form-group">
                    <label class="form-label" for="address">Address</label>
                    <input type="text" id="address" name="address" class="form-control" value="${formAddress != null ? formAddress : ''}" placeholder="City, State">
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1.5rem; padding: 0.85rem;">
                <i class="fa-solid fa-user-check"></i> Complete Registration
            </button>
        </form>

        <div style="margin-top: 1.5rem; text-align: center; font-size: 0.9rem; color: var(--text-muted);">
            Already have an account? <a href="${pageContext.request.contextPath}/login.jsp" style="font-weight: 600;">Sign In</a>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
