<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 23/10/2020
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Accueil"/>
<jsp:include page="header.jsp" />
<div >
    <h1 class="text-center">Bienvenue sur l'application PreventCovid !</h1>
    <p>L'application PreventCovid vous permet de savoir immédiatement si vous êtes un cas contact du COVID-19. Lorsque vos amis ou des personnes que vous ne connaissez pas qui ont visité des même lieux que vous se déclarent positives, vous serez immédiatement informé afin de vous faire tester !</p>

</div>

<jsp:include page="footer.jsp" />