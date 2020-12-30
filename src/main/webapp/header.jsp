<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 28/12/2020
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>${pageTitle}</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datepicker.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/inscription.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/amis.css"/>

    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.fr.min.js"></script>

</head>
<body class="fond">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp">Accueil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${!empty sessionScope.sessionUtilisateur}">
                    <%-- Si l'utilisateur est connecté, il peut voir son profil et se déconnecter --%>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/profil">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/creer_activite">Nouvelle activité</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/creer_lieu">Nouveau lieu (TMP)</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/activites">Activités</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/amis">Amis</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="<%=request.getContextPath()%>/deconnexion">Déconnexion</a>
                    </li>

               </c:when>
                <c:otherwise>
                    <%-- Sinon il peut se connecter ou s'inscrire --%>
                    <li class="nav-item active">
                        <a class="nav-link" href="<%=request.getContextPath()%>/inscription">Inscription</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/connexion">Connexion</a>
                    </li>
                </c:otherwise>
            </c:choose>
<%--            <li class="nav-item dropdown">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--                    Dropdown--%>
<%--                </a>--%>
<%--                <div class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                    <a class="dropdown-item" href="#">Action</a>--%>
<%--                    <a class="dropdown-item" href="#">Another action</a>--%>
<%--                    <div class="dropdown-divider"></div>--%>
<%--                    <a class="dropdown-item" href="#">Something else here</a>--%>
<%--                </div>--%>
<%--            </li>--%>
        </ul>
    </div>
</nav>