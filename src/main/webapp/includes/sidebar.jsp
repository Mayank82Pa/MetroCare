<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="sidebar">
    <a href="${pageContext.request.contextPath}/index.jsp" class="sidebar-brand">
        <i class="fa-solid fa-heart-pulse"></i>
        <span>MetroCare</span>
    </a>

    <ul class="sidebar-nav">
        <c:choose>
            <%-- ADMIN MENU --%>
            <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-link ${param.active == 'dashboard' ? 'active' : ''}"><i class="fa-solid fa-house"></i> Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/users" class="sidebar-link ${param.active == 'users' ? 'active' : ''}"><i class="fa-solid fa-users"></i> Users Management</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/doctors" class="sidebar-link ${param.active == 'doctors' ? 'active' : ''}"><i class="fa-solid fa-user-doctor"></i> Doctor Rosters</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/appointments" class="sidebar-link ${param.active == 'appointments' ? 'active' : ''}"><i class="fa-solid fa-calendar-check"></i> Appointments</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/analytics" class="sidebar-link ${param.active == 'analytics' ? 'active' : ''}"><i class="fa-solid fa-chart-pie"></i> Analytics & Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/settings" class="sidebar-link ${param.active == 'settings' ? 'active' : ''}"><i class="fa-solid fa-sliders"></i> System Settings</a></li>
            </c:when>

            <%-- DOCTOR MENU --%>
            <c:when test="${sessionScope.currentUser.role == 'DOCTOR'}">
                <li><a href="${pageContext.request.contextPath}/doctor/dashboard" class="sidebar-link ${param.active == 'dashboard' ? 'active' : ''}"><i class="fa-solid fa-house"></i> Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/doctor/appointments" class="sidebar-link ${param.active == 'appointments' ? 'active' : ''}"><i class="fa-solid fa-calendar-days"></i> Appointments Queue</a></li>
                <li><a href="${pageContext.request.contextPath}/doctor/schedule" class="sidebar-link ${param.active == 'schedule' ? 'active' : ''}"><i class="fa-solid fa-clock"></i> My Schedules</a></li>
                <li><a href="${pageContext.request.contextPath}/doctor/medical-records" class="sidebar-link ${param.active == 'records' ? 'active' : ''}"><i class="fa-solid fa-file-medical"></i> Medical Records</a></li>
                <li><a href="${pageContext.request.contextPath}/doctor/feedback" class="sidebar-link ${param.active == 'feedback' ? 'active' : ''}"><i class="fa-solid fa-star"></i> Patient Reviews</a></li>
            </c:when>

            <%-- PATIENT MENU --%>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/patient/dashboard" class="sidebar-link ${param.active == 'dashboard' ? 'active' : ''}"><i class="fa-solid fa-house"></i> Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/patient/search-doctors" class="sidebar-link ${param.active == 'search' ? 'active' : ''}"><i class="fa-solid fa-user-doctor"></i> Find Doctors</a></li>
                <li><a href="${pageContext.request.contextPath}/patient/appointments" class="sidebar-link ${param.active == 'appointments' ? 'active' : ''}"><i class="fa-solid fa-calendar-check"></i> My Appointments</a></li>
                <li><a href="${pageContext.request.contextPath}/patient/medical-history" class="sidebar-link ${param.active == 'history' ? 'active' : ''}"><i class="fa-solid fa-notes-medical"></i> Medical History</a></li>
                <li><a href="${pageContext.request.contextPath}/patient/profile" class="sidebar-link ${param.active == 'profile' ? 'active' : ''}"><i class="fa-solid fa-user-gear"></i> My Profile</a></li>
            </c:otherwise>
        </c:choose>
    </ul>

    <div class="sidebar-user">
        <div class="sidebar-user-info">
            <div class="avatar">${sessionScope.currentUser.name.substring(0, 1).toUpperCase()}</div>
            <div>
                <div style="font-weight: 700; font-size: 0.95rem; color: #fff;">${sessionScope.currentUser.name}</div>
                <div style="font-size: 0.8rem; color: #94a3b8;">${sessionScope.currentUser.roleDisplayName}</div>
            </div>
        </div>
        <div style="margin-top: 1rem;">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline btn-sm" style="width: 100%; border-color: rgba(255,255,255,0.2); color: #cbd5e1;">
                <i class="fa-solid fa-right-from-bracket"></i> Sign Out
            </a>
        </div>
    </div>
</aside>
