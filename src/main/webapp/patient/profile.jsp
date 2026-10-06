<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Patient Profile - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="profile"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content" style="max-width: 850px;">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">Account & Health Profile</h1>
                <p style="color: var(--text-muted);">Manage your personal details, emergency contact information, and account security</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: grid; grid-template-columns: 1fr; gap: 2rem;">
                <!-- Profile Information Card -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;"><i class="fa-solid fa-user" style="color: var(--primary);"></i> Personal Details</h3>
                    <form action="${pageContext.request.contextPath}/patient/profile" method="POST">
                        <input type="hidden" name="action" value="updateProfile"/>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="name">Full Name *</label>
                                <input type="text" id="name" name="name" class="form-control" value="${patient.name}" required>
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="email">Email Address (Read-only)</label>
                                <input type="email" id="email" class="form-control" value="${patient.email}" readonly style="background: #f8fafc;">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="phone">Phone Number</label>
                                <input type="tel" id="phone" name="phone" class="form-control" value="${patient.phone}">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="dob">Date of Birth</label>
                                <input type="date" id="dob" name="dateOfBirth" class="form-control" value="${patient.dateOfBirth}">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="gender">Gender</label>
                                <select id="gender" name="gender" class="form-control">
                                    <option value="Male" ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
                                    <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
                                    <option value="Other" ${patient.gender == 'Other' ? 'selected' : ''}>Other</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="bloodGroup">Blood Group</label>
                                <select id="bloodGroup" name="bloodGroup" class="form-control">
                                    <option value="A+" ${patient.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
                                    <option value="A-" ${patient.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
                                    <option value="B+" ${patient.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
                                    <option value="B-" ${patient.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
                                    <option value="O+" ${patient.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
                                    <option value="O-" ${patient.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
                                    <option value="AB+" ${patient.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
                                    <option value="AB-" ${patient.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="emergency">Emergency Contact</label>
                                <input type="tel" id="emergency" name="emergencyContact" class="form-control" value="${patient.emergencyContact}">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="address">Residential Address</label>
                                <input type="text" id="address" name="address" class="form-control" value="${patient.address}">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary" style="margin-top: 0.5rem;">
                            <i class="fa-solid fa-floppy-disk"></i> Update Profile
                        </button>
                    </form>
                </div>

                <!-- Password Change Card -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;"><i class="fa-solid fa-shield-keyhole" style="color: var(--warning);"></i> Security & Password</h3>
                    <form action="${pageContext.request.contextPath}/patient/profile" method="POST">
                        <input type="hidden" name="action" value="changePassword"/>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="oldPassword">Current Password</label>
                                <input type="password" id="oldPassword" name="oldPassword" class="form-control" required placeholder="••••••••">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="newPassword">New Password (min 6 chars)</label>
                                <input type="password" id="newPassword" name="newPassword" class="form-control" required placeholder="••••••••">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="confirmPassword">Confirm New Password</label>
                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required placeholder="••••••••">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-outline-primary">
                            <i class="fa-solid fa-key"></i> Update Password
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
