<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Medical History - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="history"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">Personal Medical History & Prescriptions</h1>
                <p style="color: var(--text-muted);">Access complete digital clinical summaries, doctor notes, and prescribed medications</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: flex; flex-direction: column; gap: 1.5rem;">
                <c:choose>
                    <c:when test="${not empty records}">
                        <c:forEach var="r" items="${records}">
                            <div class="card" style="border-left: 4px solid var(--primary);">
                                <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 1rem;">
                                    <div>
                                        <h3 style="color: var(--primary); font-size: 1.2rem; margin-bottom: 0.25rem;">${r.diagnosis}</h3>
                                        <div style="font-size: 0.9rem; color: var(--text-muted);">
                                            Consulted with <strong>${r.doctorName}</strong> (${r.doctorSpecialization}) on ${r.appointmentDate}
                                        </div>
                                    </div>
                                    <span class="badge badge-completed"><i class="fa-solid fa-file-circle-check"></i> Verified Record</span>
                                </div>

                                <div style="background: #f8fafc; border-radius: var(--radius-md); padding: 1.25rem; margin-bottom: 1rem;">
                                    <h4 style="font-size: 0.95rem; margin-bottom: 0.5rem; color: var(--secondary);"><i class="fa-solid fa-pills"></i> Prescribed Medications & Dosage:</h4>
                                    <pre style="font-family: inherit; font-size: 0.9rem; white-space: pre-wrap; margin: 0; color: #1e293b;">${r.prescription}</pre>
                                </div>

                                <c:if test="${not empty r.clinicalNotes}">
                                    <div style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 0.75rem;">
                                        <strong>Doctor's Notes:</strong> ${r.clinicalNotes}
                                    </div>
                                </c:if>

                                <c:if test="${not empty r.followUpDate}">
                                    <div style="font-size: 0.85rem; color: #b45309; background: #fef3c7; padding: 0.5rem 0.75rem; border-radius: var(--radius-sm); display: inline-block;">
                                        <i class="fa-solid fa-calendar-day"></i> Recommended Follow-up: <strong>${r.followUpDate}</strong>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="card" style="text-align: center; padding: 3rem; color: var(--text-muted);">
                            <i class="fa-solid fa-notes-medical" style="font-size: 2.5rem; color: #cbd5e1; margin-bottom: 1rem;"></i>
                            <p>No medical records found. Completed consultations and prescriptions will appear here.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
