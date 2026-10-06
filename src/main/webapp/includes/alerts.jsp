<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.successMessage}">
    <div class="alert alert-success">
        <div><i class="fa-solid fa-circle-check"></i> ${sessionScope.successMessage}</div>
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
    <div class="alert alert-danger">
        <div><i class="fa-solid fa-triangle-exclamation"></i> ${sessionScope.errorMessage}</div>
    </div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        <div><i class="fa-solid fa-triangle-exclamation"></i> ${errorMessage}</div>
    </div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        <div><i class="fa-solid fa-circle-check"></i> ${successMessage}</div>
    </div>
</c:if>
