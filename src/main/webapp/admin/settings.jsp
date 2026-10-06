<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="System Settings - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="settings"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content" style="max-width: 800px;">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">System Configuration & Settings</h1>
                <p style="color: var(--text-muted);">Configure hospital parameters, contact hotlines, slot durations, and booking policies</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div class="card">
                <form action="${pageContext.request.contextPath}/admin/settings" method="POST">
                    <c:forEach var="s" items="${settings}">
                        <div class="form-group">
                            <label class="form-label" for="setting_${s.settingKey}">
                                <code>${s.settingKey}</code>
                                <small style="display: block; color: var(--text-muted); font-weight: 400; margin-top: 0.15rem;">${s.description}</small>
                            </label>
                            <input type="text" id="setting_${s.settingKey}" name="setting_${s.settingKey}"
                                   class="form-control" value="${s.settingValue}" required>
                        </div>
                    </c:forEach>

                    <button type="submit" class="btn btn-primary" style="margin-top: 1rem;">
                        <i class="fa-solid fa-floppy-disk"></i> Save System Settings
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
