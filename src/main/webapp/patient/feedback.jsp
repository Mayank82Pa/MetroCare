<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Doctor Review & Feedback - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="appointments"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content" style="max-width: 650px;">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">Review & Feedback</h1>
                <p style="color: var(--text-muted);">Share your experience to help us maintain the highest medical standards</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <c:choose>
                <c:when test="${alreadySubmitted}">
                    <div class="card" style="text-align: center; padding: 3rem;">
                        <div class="stat-icon green" style="margin: 0 auto 1.5rem auto; width: 60px; height: 60px; font-size: 1.8rem;">
                            <i class="fa-solid fa-circle-check"></i>
                        </div>
                        <h3>Feedback Already Submitted</h3>
                        <p style="color: var(--text-muted); margin-bottom: 1.5rem;">
                            Thank you for evaluating your consultation with <strong>${appointment.doctorName}</strong>.
                        </p>
                        <a href="${pageContext.request.contextPath}/patient/appointments" class="btn btn-primary">
                            <i class="fa-solid fa-arrow-left"></i> Back to Appointments
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card">
                        <div style="padding-bottom: 1.25rem; margin-bottom: 1.5rem; border-bottom: 1px solid var(--border);">
                            <h3 style="font-size: 1.15rem; margin-bottom: 0.25rem;">Consultation with ${appointment.doctorName}</h3>
                            <p style="color: var(--text-muted); font-size: 0.9rem;">
                                Department: <strong>${appointment.doctorSpecialization}</strong> &bull; Date: ${appointment.appointmentDate}
                            </p>
                        </div>

                        <form action="${pageContext.request.contextPath}/patient/feedback" method="POST">
                            <input type="hidden" name="appointmentId" value="${appointment.appointmentId}"/>
                            <input type="hidden" name="doctorId" value="${appointment.doctorId}"/>

                            <div class="form-group">
                                <label class="form-label" for="rating">Rating (1 to 5 Stars) *</label>
                                <select id="rating" name="rating" class="form-control" required style="font-size: 1.05rem;">
                                    <option value="5" selected>⭐⭐⭐⭐⭐ (5 - Exceptional Care)</option>
                                    <option value="4">⭐⭐⭐⭐ (4 - Very Good)</option>
                                    <option value="3">⭐⭐⭐ (3 - Satisfactory / Average)</option>
                                    <option value="2">⭐⭐ (2 - Needs Improvement)</option>
                                    <option value="1">⭐ (1 - Poor Experience)</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="form-label" for="comments">Your Comments & Experience *</label>
                                <textarea id="comments" name="comments" class="form-control" required style="min-height: 120px;"
                                          placeholder="How was the doctor's communication, diagnosis explanation, and consultation quality?"></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem;">
                                <i class="fa-solid fa-paper-plane"></i> Submit Doctor Review
                            </button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
