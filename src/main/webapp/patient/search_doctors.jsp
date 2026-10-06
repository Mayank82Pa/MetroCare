<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Find Doctors - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="search"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="margin-bottom: 2rem;">
                <h1 style="font-size: 1.85rem;">Find Medical Specialists</h1>
                <p style="color: var(--text-muted);">Search by physician name, specialty, or hospital department</p>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <!-- Filter Search Bar -->
            <div class="card" style="margin-bottom: 2rem; padding: 1.25rem;">
                <form action="${pageContext.request.contextPath}/patient/search-doctors" method="GET" style="display: flex; gap: 1rem; flex-wrap: wrap;">
                    <div style="flex: 2; min-width: 250px;">
                        <input type="text" name="query" class="form-control" placeholder="Search doctor by name, qualification..." value="${enteredQuery != null ? enteredQuery : ''}">
                    </div>
                    <div style="flex: 1; min-width: 200px;">
                        <select name="specialization" class="form-control">
                            <option value="">All Specializations</option>
                            <c:forEach var="spec" items="${specializations}">
                                <option value="${spec}" ${selectedSpec == spec ? 'selected' : ''}>${spec}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary" style="height: 100%;">
                            <i class="fa-solid fa-magnifying-glass"></i> Search Doctors
                        </button>
                    </div>
                </form>
            </div>

            <!-- Doctor Profile Cards Grid -->
            <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 1.5rem;">
                <c:choose>
                    <c:when test="${not empty doctors}">
                        <c:forEach var="d" items="${doctors}">
                            <div class="card" style="display: flex; flex-direction: column; justify-content: space-between;">
                                <div>
                                    <div style="display: flex; gap: 1rem; align-items: flex-start; margin-bottom: 1rem;">
                                        <div class="avatar" style="width: 50px; height: 50px; font-size: 1.2rem; flex-shrink: 0;">${d.name.substring(0, 1)}</div>
                                        <div>
                                            <h3 style="font-size: 1.15rem; margin-bottom: 0.2rem;">${d.name}</h3>
                                            <span class="badge badge-confirmed">${d.specialization}</span>
                                        </div>
                                    </div>

                                    <div style="font-size: 0.875rem; color: var(--text-muted); margin-bottom: 0.75rem;">
                                        <div><i class="fa-solid fa-graduation-cap"></i> ${d.qualification}</div>
                                        <div><i class="fa-solid fa-briefcase-medical"></i> ${d.experienceYears} Years Experience</div>
                                        <div><i class="fa-solid fa-wallet"></i> <strong>$${d.consultationFee}</strong> Consultation Fee</div>
                                    </div>

                                    <c:if test="${not empty d.bio}">
                                        <p style="font-size: 0.85rem; color: var(--text-main); margin-bottom: 1rem; line-height: 1.4;">${d.bio}</p>
                                    </c:if>
                                </div>

                                <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 1rem; border-top: 1px solid var(--border);">
                                    <div>
                                        <span class="stars"><i class="fa-solid fa-star"></i> <fmt:formatNumber value="${d.averageRating}" maxFractionDigits="1"/></span>
                                        <small style="color: var(--text-muted);">(${d.totalReviews})</small>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/patient/book-appointment?doctorId=${d.doctorId}" class="btn btn-primary btn-sm">
                                        <i class="fa-solid fa-calendar-check"></i> Book Now
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="card" style="grid-column: 1 / -1; text-align: center; padding: 3rem; color: var(--text-muted);">
                            <i class="fa-solid fa-user-doctor" style="font-size: 2.5rem; color: #cbd5e1; margin-bottom: 1rem;"></i>
                            <p>No doctors matched your search criteria. Try removing some filters.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
