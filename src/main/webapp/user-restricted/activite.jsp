<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 07/01/2021
  Time: 00:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Mes activités"/>
<jsp:include page="../header.jsp"/>
<div class="fond-element rounded mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6 mt-2 pt-1 pb-2">
    <h1><c:out value="${activite.nom}"/></h1>
    <dl class="row">
        <dt class="col-sm-3">Lieu : </dt>
        <dd class="col-sm-9">
            <a class="desac-lien"
               href="<%=request.getContextPath()%>/user-restricted/lieu?lieu=<c:out value="${lieu.id}"/>">
                Lieu : <c:out value="${lieu.nom}"/>
            </a>
        </dd>
    </dl>
    <dl class="row">
        <dt class="col-sm-3">Date de début : </dt>
        <dd class="col-sm-9"><c:out value="${activite.dateDebut}"/> à <c:out value="${activite.heureDebut}"/></dd>
    </dl>
    <dl class="row">
        <dt class="col-sm-3">Date de fin : </dt>
        <dd class="col-sm-9"><c:out value="${activite.dateFin}"/> à <c:out value="${activite.heureFin}"/></dd>
    </dl>
</div>
<jsp:include page="../footer.jsp"/>