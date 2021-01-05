<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 05/01/2021
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Erreur 404"/>
<jsp:include page="header.jsp" />
<div class="text-center" style="position:absolute; top:50%; left:50%; transform: translate(-50%,-50%);">
        <h1>Oups ... &#128531;</h1>
        <h5>
            Vous vous êtes visiblement perdu ... Cette page n'est pas disponible ou n'existe pas.
        </h5>
</div>
<jsp:include page="footer.jsp" />
