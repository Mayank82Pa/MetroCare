<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand">
        <i class="fa-solid fa-heart-pulse logo-icon"></i>
        <span>MetroCare</span>
    </a>

    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/index.jsp" class="nav-link">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/patient/search-doctors" class="nav-link">Find Doctors</a></li>
        
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                <c:choose>
                    <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary btn-sm"><i class="fa-solid fa-chart-line"></i> Admin Portal</a></li>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.role == 'DOCTOR'}">
                        <li><a href="${pageContext.request.contextPath}/doctor/dashboard" class="btn btn-primary btn-sm"><i class="fa-solid fa-user-doctor"></i> Doctor Portal</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/patient/dashboard" class="btn btn-primary btn-sm"><i class="fa-solid fa-user"></i> My Dashboard</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-outline btn-sm"><i class="fa-solid fa-right-from-bracket"></i> Logout</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline btn-sm">Sign In</a></li>
                <li><a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-primary btn-sm">Register</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
