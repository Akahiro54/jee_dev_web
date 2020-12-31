<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 31/12/2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Mes notifications"/>
<jsp:include page="../header.jsp" />
<h1 class="text-center">Mes notifications</h1>

<div class="list-group">
    <c:forEach items="${notifications}" var="notification">
        <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">Titre de la notification</h5>
                <small><c:out value="${notification.date}"/></small>
            </div>
            <p class="mb-1"><c:out value="${notification.message}"/></p>
            <div>
                <button class="btn btn-primary">Accepter</button>
                <button class="btn btn-danger">Refuser</button>
            </div>
        </a>
    </c:forEach>
</div>
<jsp:include page="../footer.jsp" />