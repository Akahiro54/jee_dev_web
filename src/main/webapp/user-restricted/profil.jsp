<%--
  Created by IntelliJ IDEA.
  User: florian
  Date: 27/12/2020
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Mon profil"/>
<jsp:include page="../header.jsp" />
<div class="container">
    <div class="main-body">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="${utilisateur.image}" class="rounded-circle" width="150">
                            <div class="mt-3">
                                <h4>${utilisateur.nom}</h4><h5>${utilisateur.prenom}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Nom</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${utilisateur.nom}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Prénom</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${utilisateur.prenom}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${utilisateur.email}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Date de Naissance</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${utilisateur.date}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-9 text-secondary">
                                <a href="<%=request.getContextPath()%>/user-restricted/modifier_profil" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Modifier</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row gutters-sm">
                    <div class="col-sm-6 mb-3">
                        <div class="card h-100">
                            <a href="#" class="list-group-item list-group-item-action list-group-item-warning">Je suis positif au covid</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp" />


