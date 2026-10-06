<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Page Not Found - MetroCare"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<div class="main-content" style="max-width: 550px; text-align: center; margin-top: 4rem;">
    <div class="card" style="padding: 3rem;">
        <div class="stat-icon amber" style="margin: 0 auto 1.5rem auto; width: 70px; height: 70px; font-size: 2rem;">
            <i class="fa-solid fa-magnifying-glass"></i>
        </div>
        <h2 style="font-size: 2.2rem; margin-bottom: 0.75rem;">404 - Page Not Found</h2>
        <p style="color: var(--text-muted); margin-bottom: 2rem;">
            The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.
        </p>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
            <i class="fa-solid fa-house"></i> Back to Safety
        </a>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
