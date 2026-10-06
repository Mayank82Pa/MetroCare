<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Book Appointment - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="search"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content" style="max-width: 900px;">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">Schedule Medical Consultation</h1>
                <p style="color: var(--text-muted);">Select consultation date, choose from verified open slots, and submit booking</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 2rem;">
                <!-- Doctor Summary Card -->
                <div class="card" style="height: fit-content;">
                    <div style="text-align: center; margin-bottom: 1rem;">
                        <div class="avatar" style="width: 65px; height: 65px; font-size: 1.6rem; margin: 0 auto 0.75rem auto;">
                            ${doctor.name.substring(0, 1)}
                        </div>
                        <h3 style="font-size: 1.25rem; margin-bottom: 0.2rem;">${doctor.name}</h3>
                        <span class="badge badge-confirmed">${doctor.specialization}</span>
                    </div>

                    <div style="font-size: 0.9rem; color: var(--text-muted); border-top: 1px solid var(--border); padding-top: 1rem;">
                        <div style="margin-bottom: 0.5rem;"><i class="fa-solid fa-graduation-cap"></i> ${doctor.qualification}</div>
                        <div style="margin-bottom: 0.5rem;"><i class="fa-solid fa-briefcase-medical"></i> ${doctor.experienceYears} Years Experience</div>
                        <div style="margin-bottom: 0.5rem;"><i class="fa-solid fa-wallet"></i> <strong>$${doctor.consultationFee}</strong> Consultation Fee</div>
                        <div><i class="fa-solid fa-star stars"></i> <strong><fmt:formatNumber value="${doctor.averageRating}" maxFractionDigits="1"/></strong> (${doctor.totalReviews} Reviews)</div>
                    </div>
                </div>

                <!-- Booking Slot Engine -->
                <div class="card">
                    <form action="${pageContext.request.contextPath}/patient/book-appointment" method="GET" id="datePickerForm" style="margin-bottom: 1.5rem;">
                        <input type="hidden" name="doctorId" value="${doctor.doctorId}"/>
                        <div class="form-group">
                            <label class="form-label" for="dateSelect">1. Choose Consultation Date</label>
                            <input type="date" id="dateSelect" name="date" class="form-control"
                                   value="${selectedDateStr != null ? selectedDateStr : ''}"
                                   onchange="document.getElementById('datePickerForm').submit()">
                            <small style="color: var(--text-muted); display: block; margin-top: 0.35rem;">
                                Select a date to dynamically load verified available time slots.
                            </small>
                        </div>
                    </form>

                    <c:if test="${not empty selectedDateStr}">
                        <form action="${pageContext.request.contextPath}/patient/book-appointment" method="POST">
                            <input type="hidden" name="doctorId" value="${doctor.doctorId}"/>
                            <input type="hidden" name="scheduleId" value="${selectedSchedule != null ? selectedSchedule.scheduleId : ''}"/>
                            <input type="hidden" name="appointmentDate" value="${selectedDateStr}"/>
                            <input type="hidden" name="appointmentTime" id="appointmentTimeInput" value=""/>

                            <div class="form-group">
                                <label class="form-label">2. Select an Available Time Slot</label>
                                <c:choose>
                                    <c:when test="${not empty availableSlots}">
                                        <div class="slots-container">
                                            <c:forEach var="slot" items="${availableSlots}">
                                                <button type="button" class="slot-btn" data-time="${slot}">${slot}</button>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-danger" style="margin-top: 0.5rem;">
                                            No open slots available for this date. Please pick another date or contact the doctor.
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="form-group" style="margin-top: 1.5rem;">
                                <label class="form-label" for="reason">3. Reason for Consultation / Symptoms *</label>
                                <textarea id="reason" name="reason" class="form-control" required
                                          placeholder="Describe symptoms, medical concerns, or primary complaint..."></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary btn-lg" style="width: 100%; margin-top: 1rem;">
                                <i class="fa-solid fa-calendar-check"></i> Confirm & Book Consultation
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
