<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 08/01/2021
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Lieux"/>
<jsp:include page="../header.jsp" />
<div class="fond-element rounded mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6 mt-2 pt-1 pb-2">
    <h1>Lieux</h1>
    <div class="list-group">
        <c:forEach items="${places}" var="lieu">
            <a href="<%=request.getContextPath()%>/user-restricted/lieu?lieu=<c:out value="${lieu.id}"/>" class="list-group-item list-group-item-action"><c:out value="${lieu.nom}"/></a>
        </c:forEach>
    </div>
</div>
<jsp:include page="../footer.jsp" />