<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Unauthorized Access - MetroCare"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<div class="main-content" style="max-width: 550px; text-align: center; margin-top: 4rem;">
    <div class="card" style="padding: 3rem;">
        <div class="stat-icon red" style="margin: 0 auto 1.5rem auto; width: 70px; height: 70px; font-size: 2rem;">
            <i class="fa-solid fa-hand"></i>
        </div>
        <h2 style="font-size: 2rem; margin-bottom: 0.75rem;">Access Denied</h2>
        <p style="color: var(--text-muted); margin-bottom: 2rem;">
            You do not have the required role permissions to view or perform actions in this area.
        </p>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
            <i class="fa-solid fa-arrow-left"></i> Return to Homepage
        </a>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
