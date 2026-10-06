<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Medical Records & Prescriptions - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="records"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Medical Records & Prescriptions</h1>
                    <p style="color: var(--text-muted);">Issue clinical diagnoses, medicine prescriptions, and follow-up schedules</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <c:if test="${not empty appointment}">
                <!-- Prescription Form for Selected Appointment -->
                <div class="card" style="margin-bottom: 2.5rem; border: 2px solid var(--primary-light);">
                    <h3 style="margin-bottom: 1.25rem; color: var(--primary);">
                        <i class="fa-solid fa-file-prescription"></i> Clinical Consultation for #${appointment.appointmentId} - ${appointment.patientName}
                    </h3>

                    <form action="${pageContext.request.contextPath}/doctor/medical-records" method="POST">
                        <input type="hidden" name="appointmentId" value="${appointment.appointmentId}"/>
                        <input type="hidden" name="patientId" value="${appointment.patientId}"/>

                        <div class="form-group">
                            <label class="form-label">Patient Complaint / Appointment Reason</label>
                            <input type="text" class="form-control" value="${appointment.reason}" readonly style="background: #f8fafc;">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="diagnosis">Clinical Diagnosis *</label>
                            <input type="text" id="diagnosis" name="diagnosis" class="form-control" required
                                   value="${existingRecord != null ? existingRecord.diagnosis : ''}"
                                   placeholder="e.g. Acute Bronchitis / Hypertension Stage 1">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="prescription">Rx: Medicines & Dosage *</label>
                            <textarea id="prescription" name="prescription" class="form-control" required style="min-height: 120px;"
                                      placeholder="1. Tab Amoxicillin 500mg - 1 tablet thrice daily after food (5 days)&#10;2. Paracetamol 650mg - SOS">${existingRecord != null ? existingRecord.prescription : ''}</textarea>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="clinicalNotes">Doctor's Clinical Notes</label>
                                <textarea id="clinicalNotes" name="clinicalNotes" class="form-control"
                                          placeholder="Dietary advice, BP/Pulse observations, lifestyle tips...">${existingRecord != null ? existingRecord.clinicalNotes : ''}</textarea>
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="followUpDate">Follow-up Consultation Date</label>
                                <input type="date" id="followUpDate" name="followUpDate" class="form-control"
                                       value="${existingRecord != null ? existingRecord.followUpDate : ''}">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            <i class="fa-solid fa-floppy-disk"></i> Save & Complete Consultation
                        </button>
                    </form>
                </div>
            </c:if>

            <!-- Past Medical Records Authored by Doctor -->
            <div class="card">
                <h3 style="margin-bottom: 1.25rem;">Authored Clinical Records</h3>
                <div class="table-container" style="border: none; box-shadow: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>Patient</th>
                                <th>Date</th>
                                <th>Diagnosis</th>
                                <th>Prescription</th>
                                <th>Follow-up</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty records}">
                                    <c:forEach var="r" items="${records}">
                                        <tr>
                                            <td><strong>${r.patientName}</strong></td>
                                            <td>${r.appointmentDate}</td>
                                            <td><strong style="color: var(--primary);">${r.diagnosis}</strong></td>
                                            <td><pre style="font-family: inherit; font-size: 0.85rem; white-space: pre-wrap; margin: 0;">${r.prescription}</pre></td>
                                            <td>${r.followUpDate != null ? r.followUpDate : 'None'}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="5" style="text-align: center; color: var(--text-muted); padding: 2rem;">No clinical records recorded yet.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
