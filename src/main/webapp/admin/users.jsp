<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="User Management - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="users"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Users Management</h1>
                    <p style="color: var(--text-muted);">View, activate, deactivate, or remove user accounts across all roles</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div class="card">
                <div class="table-container" style="border: none; box-shadow: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name & Email</th>
                                <th>Role</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Registered</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#${u.userId}</td>
                                    <td>
                                        <strong>${u.name}</strong><br/>
                                        <small style="color: var(--text-muted);">${u.email}</small>
                                    </td>
                                    <td>
                                        <span class="badge ${u.admin ? 'badge-confirmed' : (u.doctor ? 'badge-pending' : 'badge-completed')}">
                                            ${u.role}
                                        </span>
                                    </td>
                                    <td>${u.phone != null && !u.phone.isEmpty() ? u.phone : '—'}</td>
                                    <td>
                                        <span class="badge ${u.status == 'ACTIVE' ? 'badge-active' : 'badge-inactive'}">${u.status}</span>
                                    </td>
                                    <td><small style="color: var(--text-muted);">${u.createdAt}</small></td>
                                    <td>
                                        <div style="display: flex; gap: 0.5rem;">
                                            <form action="${pageContext.request.contextPath}/admin/users" method="POST" style="display: inline;">
                                                <input type="hidden" name="action" value="toggleStatus"/>
                                                <input type="hidden" name="userId" value="${u.userId}"/>
                                                <input type="hidden" name="status" value="${u.status == 'ACTIVE' ? 'INACTIVE' : 'ACTIVE'}"/>
                                                <button type="submit" class="btn btn-outline btn-sm" title="Toggle Status">
                                                    <i class="fa-solid fa-power-off"></i> ${u.status == 'ACTIVE' ? 'Deactivate' : 'Activate'}
                                                </button>
                                            </form>
                                            <c:if test="${!u.admin}">
                                                <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.userId}"
                                                   class="btn btn-danger btn-sm" data-confirm="Are you sure you want to permanently delete this user?" title="Delete">
                                                    <i class="fa-solid fa-trash"></i>
                                                </a>
                                            </c:if>
                                        </div>
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

<jsp:include page="../includes/footer.jsp"/>
