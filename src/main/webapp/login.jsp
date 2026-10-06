<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Sign In - MetroCare Healthcare"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<div class="main-content" style="max-width: 520px; margin-top: 3rem;">
    <div class="card" style="padding: 2.5rem; box-shadow: var(--shadow-lg);">
        <div style="text-align: center; margin-bottom: 2rem;">
            <div class="stat-icon blue" style="margin: 0 auto 1rem auto; width: 60px; height: 60px; font-size: 1.8rem;">
                <i class="fa-solid fa-lock"></i>
            </div>
            <h2 style="font-size: 1.8rem;">Welcome Back</h2>
            <p style="color: var(--text-muted); font-size: 0.95rem;">Sign in to access your healthcare portal</p>
        </div>

        <jsp:include page="includes/alerts.jsp"/>

        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="form-group">
                <label class="form-label" for="emailInput">Email Address</label>
                <div style="position: relative;">
                    <input type="email" id="emailInput" name="email" class="form-control" required
                           value="${enteredEmail != null ? enteredEmail : ''}" placeholder="name@example.com">
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="passwordInput">Password</label>
                <input type="password" id="passwordInput" name="password" class="form-control" required placeholder="••••••••">
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem; padding: 0.85rem;">
                <i class="fa-solid fa-right-to-bracket"></i> Sign In to Account
            </button>
        </form>

        <!-- Quick Demo Fill Buttons -->
        <div style="margin-top: 2rem; padding-top: 1.5rem; border-top: 1px solid var(--border); text-align: center;">
            <p style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 0.75rem;">Quick Demo Autofill:</p>
            <div style="display: flex; gap: 0.5rem; justify-content: center; flex-wrap: wrap;">
                <button type="button" class="btn btn-outline btn-sm" onclick="autofill('admin@healthcare.com', 'admin123')">Admin</button>
                <button type="button" class="btn btn-outline btn-sm" onclick="autofill('dr.sharma@healthcare.com', 'doctor123')">Doctor</button>
                <button type="button" class="btn btn-outline btn-sm" onclick="autofill('rahul@gmail.com', 'patient123')">Patient</button>
            </div>
        </div>

        <div style="margin-top: 1.5rem; text-align: center; font-size: 0.9rem; color: var(--text-muted);">
            Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp" style="font-weight: 600;">Create Patient Account</a>
        </div>
    </div>
</div>

<script>
function autofill(email, pass) {
    document.getElementById('emailInput').value = email;
    document.getElementById('passwordInput').value = pass;
}
</script>

<jsp:include page="includes/footer.jsp"/>
