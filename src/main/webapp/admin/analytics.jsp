<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Analytics & Reports - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="analytics"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">System Analytics & Insights</h1>
                <p style="color: var(--text-muted);">Real-time performance indicators, department distribution, and revenue summary</p>
            </div>

            <!-- KPI Summary Grid -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div>
                        <div class="stat-val">$<fmt:formatNumber value="${analytics.totalRevenue}" maxFractionDigits="2"/></div>
                        <div class="stat-label">Estimated Realized Revenue</div>
                    </div>
                    <div class="stat-icon green"><i class="fa-solid fa-money-bill-trend-up"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.completedAppointments}</div>
                        <div class="stat-label">Completed Consultations</div>
                    </div>
                    <div class="stat-icon blue"><i class="fa-solid fa-circle-check"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.pendingAppointments}</div>
                        <div class="stat-label">Pending Reviews</div>
                    </div>
                    <div class="stat-icon amber"><i class="fa-solid fa-hourglass-half"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val"><fmt:formatNumber value="${analytics.overallDoctorRating}" maxFractionDigits="1"/> <i class="fa-solid fa-star stars" style="font-size: 1.2rem;"></i></div>
                        <div class="stat-label">Average Patient Satisfaction</div>
                    </div>
                    <div class="stat-icon" style="background: #fef3c7; color: #d97706;"><i class="fa-solid fa-award"></i></div>
                </div>
            </div>

            <!-- Analytics Visual Cards -->
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 2rem;">
                <!-- Appointments Status Breakdown -->
                <div class="card">
                    <h3 style="margin-bottom: 1.5rem;">Appointments Distribution</h3>
                    <div style="display: flex; flex-direction: column; gap: 1rem;">
                        <div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem; font-size: 0.9rem;">
                                <span>Confirmed (${analytics.confirmedAppointments})</span>
                                <strong><fmt:formatNumber value="${(analytics.confirmedAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}" maxFractionDigits="1"/>%</strong>
                            </div>
                            <div style="height: 10px; background: #e2e8f0; border-radius: var(--radius-full); overflow: hidden;">
                                <div style="height: 100%; width: ${(analytics.confirmedAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}%; background: var(--primary);"></div>
                            </div>
                        </div>

                        <div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem; font-size: 0.9rem;">
                                <span>Completed (${analytics.completedAppointments})</span>
                                <strong><fmt:formatNumber value="${(analytics.completedAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}" maxFractionDigits="1"/>%</strong>
                            </div>
                            <div style="height: 10px; background: #e2e8f0; border-radius: var(--radius-full); overflow: hidden;">
                                <div style="height: 100%; width: ${(analytics.completedAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}%; background: var(--success);"></div>
                            </div>
                        </div>

                        <div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem; font-size: 0.9rem;">
                                <span>Pending (${analytics.pendingAppointments})</span>
                                <strong><fmt:formatNumber value="${(analytics.pendingAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}" maxFractionDigits="1"/>%</strong>
                            </div>
                            <div style="height: 10px; background: #e2e8f0; border-radius: var(--radius-full); overflow: hidden;">
                                <div style="height: 100%; width: ${(analytics.pendingAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}%; background: var(--warning);"></div>
                            </div>
                        </div>

                        <div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem; font-size: 0.9rem;">
                                <span>Cancelled (${analytics.cancelledAppointments})</span>
                                <strong><fmt:formatNumber value="${(analytics.cancelledAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}" maxFractionDigits="1"/>%</strong>
                            </div>
                            <div style="height: 10px; background: #e2e8f0; border-radius: var(--radius-full); overflow: hidden;">
                                <div style="height: 100%; width: ${(analytics.cancelledAppointments * 100.0) / (analytics.totalAppointments > 0 ? analytics.totalAppointments : 1)}%; background: var(--danger);"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Department Distribution -->
                <div class="card">
                    <h3 style="margin-bottom: 1.5rem;">Medical Specializations</h3>
                    <div style="display: flex; flex-direction: column; gap: 0.75rem;">
                        <c:forEach var="entry" items="${analytics.specializationCounts}">
                            <div style="display: flex; justify-content: space-between; align-items: center; padding: 0.75rem 1rem; border-radius: var(--radius-md); background: #f8fafc;">
                                <span style="font-weight: 600;">${entry.key}</span>
                                <span class="badge badge-confirmed">${entry.value} Doctors</span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
