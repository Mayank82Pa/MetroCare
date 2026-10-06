<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Doctor Rosters - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="doctors"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Doctor Rosters & Profiles</h1>
                    <p style="color: var(--text-muted);">Manage physician credentials, specializations, consultation fees, and onboard new practitioners</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 2rem;">
                <!-- Onboard New Doctor Form -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;"><i class="fa-solid fa-user-doctor" style="color: var(--primary);"></i> Onboard Doctor</h3>
                    <form action="${pageContext.request.contextPath}/admin/doctors" method="POST">
                        <input type="hidden" name="action" value="addDoctor"/>

                        <div class="form-group">
                            <label class="form-label" for="docName">Doctor Full Name *</label>
                            <input type="text" id="docName" name="name" class="form-control" required placeholder="e.g. Dr. Ramesh Gupta">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docEmail">Email Address *</label>
                            <input type="email" id="docEmail" name="email" class="form-control" required placeholder="dr.gupta@healthcare.com">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docPassword">Temporary Password *</label>
                            <input type="password" id="docPassword" name="password" class="form-control" required placeholder="••••••••">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docPhone">Contact Phone</label>
                            <input type="tel" id="docPhone" name="phone" class="form-control" placeholder="+91 98000 00000">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docSpec">Specialization *</label>
                            <input type="text" id="docSpec" name="specialization" class="form-control" required placeholder="e.g. Cardiology, Orthopedics">
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docQual">Qualification *</label>
                            <input type="text" id="docQual" name="qualification" class="form-control" required placeholder="e.g. MBBS, MS (Orthopedics)">
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label" for="docExp">Experience (Years)</label>
                                <input type="number" id="docExp" name="experienceYears" class="form-control" value="5" min="0">
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="docFee">Fee ($ / ₹)</label>
                                <input type="number" id="docFee" name="consultationFee" class="form-control" value="500.00" step="10.00" min="0">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="docBio">Doctor Bio / Highlights</label>
                            <textarea id="docBio" name="bio" class="form-control" placeholder="Brief background of the physician..."></textarea>
                        </div>

                        <button type="submit" class="btn btn-primary" style="width: 100%;">
                            <i class="fa-solid fa-plus"></i> Save & Register Doctor
                        </button>
                    </form>
                </div>

                <!-- Doctors List -->
                <div class="card">
                    <h3 style="margin-bottom: 1.25rem;">Active Practitioners</h3>
                    <div class="table-container" style="border: none; box-shadow: none;">
                        <table>
                            <thead>
                                <tr>
                                    <th>Doctor</th>
                                    <th>Specialization & Qualification</th>
                                    <th>Experience & Fee</th>
                                    <th>Rating</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${doctors}">
                                    <tr>
                                        <td>
                                            <strong>${d.name}</strong><br/>
                                            <small style="color: var(--text-muted);">${d.email}</small>
                                        </td>
                                        <td>
                                            <span class="badge badge-confirmed">${d.specialization}</span><br/>
                                            <small style="color: var(--text-muted);">${d.qualification}</small>
                                        </td>
                                        <td>
                                            ${d.experienceYears} yrs<br/>
                                            <strong>$${d.consultationFee}</strong>
                                        </td>
                                        <td>
                                            <span class="stars"><i class="fa-solid fa-star"></i> <fmt:formatNumber value="${d.averageRating}" maxFractionDigits="1"/></span>
                                            <small style="color: var(--text-muted);">(${d.totalReviews})</small>
                                        </td>
                                        <td>
                                            <span class="badge ${d.status == 'ACTIVE' ? 'badge-active' : 'badge-inactive'}">${d.status}</span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
