<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Patient Feedback - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="feedback"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Patient Reviews & Ratings</h1>
                    <p style="color: var(--text-muted);">Real-time testimonials and satisfaction ratings submitted by your patients</p>
                </div>
                <div class="card" style="padding: 0.75rem 1.5rem; display: flex; align-items: center; gap: 1rem;">
                    <span class="stars" style="font-size: 1.5rem;"><i class="fa-solid fa-star"></i> <fmt:formatNumber value="${doctor.averageRating}" maxFractionDigits="1"/></span>
                    <span style="font-size: 0.875rem; color: var(--text-muted); font-weight: 600;">Overall Rating</span>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(350px, 1fr)); gap: 1.5rem;">
                <c:choose>
                    <c:when test="${not empty feedbackList}">
                        <c:forEach var="f" items="${feedbackList}">
                            <div class="card" style="border-left: 4px solid var(--warning);">
                                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.75rem;">
                                    <div style="display: flex; align-items: center; gap: 0.5rem;">
                                        <div class="avatar" style="width: 32px; height: 32px; font-size: 0.85rem;">${f.patientName.substring(0, 1)}</div>
                                        <strong>${f.patientName}</strong>
                                    </div>
                                    <div class="stars">
                                        <c:forEach begin="1" end="${f.rating}"><i class="fa-solid fa-star"></i></c:forEach>
                                    </div>
                                </div>
                                <p style="color: var(--text-main); font-size: 0.95rem; margin-bottom: 0.75rem;">
                                    "${f.comments}"
                                </p>
                                <small style="color: var(--text-muted); display: block;">Reviewed on ${f.createdAt}</small>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="card" style="grid-column: 1 / -1; text-align: center; padding: 3rem; color: var(--text-muted);">
                            <i class="fa-solid fa-star" style="font-size: 2.5rem; color: #cbd5e1; margin-bottom: 1rem;"></i>
                            <p>No patient reviews submitted yet.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
