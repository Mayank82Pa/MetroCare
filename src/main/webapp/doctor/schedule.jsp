<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="My Schedules - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="schedule"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Doctor Availability & Shift Manager</h1>
                    <p style="color: var(--text-muted);">Set your consulting dates, working hours, and slot intervals for patient bookings</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 2rem;">
                <!-- Add Schedule Form -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;"><i class="fa-solid fa-calendar-plus" style="color: var(--primary);"></i> Add Available Shift</h3>
                    <form action="${pageContext.request.contextPath}/doctor/schedule" method="POST">
                        <div class="form-group">
                            <label class="form-label" for="availDate">Consultation Date *</label>
                            <input type="date" id="availDate" name="availableDate" class="form-control" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="startTime">Start Time *</label>
                                <input type="time" id="startTime" name="startTime" class="form-control" required value="09:00">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="endTime">End Time *</label>
                                <input type="time" id="endTime" name="endTime" class="form-control" required value="13:00">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="duration">Slot Duration (Minutes)</label>
                            <select id="duration" name="slotDurationMins" class="form-control">
                                <option value="15">15 Minutes</option>
                                <option value="20">20 Minutes</option>
                                <option value="30" selected>30 Minutes (Recommended)</option>
                                <option value="45">45 Minutes</option>
                                <option value="60">60 Minutes</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary" style="width: 100%;">
                            <i class="fa-solid fa-plus"></i> Publish Availability Slot
                        </button>
                    </form>
                </div>

                <!-- Existing Schedules -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;">Configured Working Shifts</h3>
                    <div class="table-container" style="border: none; box-shadow: none;">
                        <table>
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Working Hours</th>
                                    <th>Slot Length</th>
                                    <th>Generated Slots</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty schedules}">
                                        <c:forEach var="s" items="${schedules}">
                                            <tr>
                                                <td><strong>${s.availableDate}</strong></td>
                                                <td>${s.startTime} - ${s.endTime}</td>
                                                <td><span class="badge badge-confirmed">${s.slotDurationMins} mins</span></td>
                                                <td>
                                                    <div class="slots-container" style="max-width: 250px;">
                                                        <c:forEach var="slot" items="${s.generateTimeSlots()}">
                                                            <span style="font-size: 0.75rem; background: #f1f5f9; padding: 0.2rem 0.45rem; border-radius: 4px; font-weight: 600;">${slot}</span>
                                                        </c:forEach>
                                                    </div>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/doctor/schedule?action=delete&id=${s.scheduleId}"
                                                       class="btn btn-danger btn-sm" data-confirm="Remove this availability shift?">
                                                        <i class="fa-solid fa-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="5" style="text-align: center; color: var(--text-muted); padding: 2rem;">No availability slots published yet.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
