<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 28/12/2020
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="beans.TypeUtilisateur" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>${pageTitle}</title>


    <link rel="icon" href="<%=request.getContextPath()%>/favicon.ico"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datepicker.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/site.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/amis.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin_panel.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/material-icons.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/material-icons.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ol.css">

    <script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-datepicker.fr.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/ol.js"></script>


</head>
<body class="fond" id="page">
<nav class="fond-element barre navbar navbar-expand-lg navbar-light bg-light">
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
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/activites">Mes activités</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/lieux">Lieux</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/amis">Mes amis</a>
                    </li>
                    <c:if test="${sessionUtilisateur.role eq TypeUtilisateur.ADMIN}">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/admin-restricted/panel_admin">Panel Admin</a>
                        </li>
                    </c:if>
                    <c:choose>
                        <c:when test="${hasNotifications == true}">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/notifications">Mes notifications <span class="badge badge-danger">!</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=request.getContextPath()%>/user-restricted/notifications">Mes notifications</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <li class="nav-item">
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
        </ul>
    </div>
</nav>
