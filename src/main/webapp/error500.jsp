<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="System Error - MetroCare"/>
</jsp:include>
<jsp:include page="includes/navbar.jsp"/>

<div class="main-content" style="max-width: 650px; text-align: center; margin-top: 4rem;">
    <div class="card" style="padding: 3rem;">
        <div class="stat-icon red" style="margin: 0 auto 1.5rem auto; width: 70px; height: 70px; font-size: 2rem;">
            <i class="fa-solid fa-triangle-exclamation"></i>
        </div>
        <h2 style="font-size: 2.2rem; margin-bottom: 0.75rem;">500 - System Error</h2>
        <p style="color: var(--text-muted); margin-bottom: 1.5rem;">
            An unexpected error occurred while processing your request. Please try again or contact the administrator.
        </p>
        <div style="margin-bottom: 2rem;">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <i class="fa-solid fa-house"></i> Home
            </a>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
