<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp" />
<c:set var="pageTitle" scope="request" value="Panel Admin"/>
<div class="container-xl">
    <div class="table-responsive">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><b>Utilisateurs</b></h2>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Pseudo</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Date Inscription</th>
                    <th>Rôle</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listeUtilisateur}" var="listutil">
                    <tr>
                        <td><c:out value="${listutil.id}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${empty listutil.image}">
                                    <img src="<%=request.getContextPath()%>/img/profile.jpg" alt="" width="50" height="50"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="data:image/jpeg;base64,${listutil.image}" alt="" width="50" height="50"/>
                                </c:otherwise>
                            </c:choose>
                            <c:out value="${listutil.pseudo}" />
                        </td>
                        <td><c:out value="${listutil.nom}" />  <c:out value="${listutil.prenom}" /></td>
                        <td><c:out value="${listutil.email}" /> </td>
                        <td><c:out value="${listutil.date}" /> </td>
                        <td><c:out value="${listutil.role}" /> </td>
                        <td><span class="status text-success">&bull;</span> Active</td>
                        <td>
                            <a href="<%=request.getContextPath()%>/user-restricted/modifier_utilisateur?u=<c:out value="${listutil.id}"/>" class="settings" title="Settings" data-toggle="tooltip"><i class="material-icons">&#xE8B8;</i></a>
                            <a href="<%=request.getContextPath()%>/user-restricted/pannel_admin?delete=<c:out value="${listutil.id}"/>" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i>  </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><b>Activités</b></h2>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Nom</th>
                    <th>Utilisateur</th>
                    <th>Début</th>
                    <th>Fin</th>
                    <th>Lieu</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listeActivite}" var="listactivite">
                    <tr>
                        <td><c:out value="${listactivite.id}" /></td>
                        <td><c:out value="${listactivite.nom}" /></td>
                        <td><c:out value="${listactivite.idUtilisateur}" /> </td>
                        <td><c:out value="${listactivite.dateDebut}" /> à <c:out value="${listactivite.heureDebut}" /> </td>
                        <td><c:out value="${listactivite.dateFin}" /> à <c:out value="${listactivite.heureFin}" /></td>
                        <td><c:out value="${listactivite.idLieu}" /> </td>
                        <td>
                            <a href="<%=request.getContextPath()%>/user-restricted/modifier_activite?a=<c:out value="${listactivite.id}"/>" class="settings" title="Settings" data-toggle="tooltip"><i class="material-icons">&#xE8B8;</i></a>
                            <a href="<%=request.getContextPath()%>/user-restricted/pannel_admin?deleteActivite=<c:out value="${listactivite.id}"/>" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i>  </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <hr>
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><b>Lieux</b></h2>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Nom</th>
                    <th>Description</th>
                    <th>Adresse</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listeLieux}" var="listLieux">
                    <tr>
                        <td><c:out value="${listLieux.id}" /></td>
                        <td><c:out value="${listLieux.nom}" /></td>
                        <td><c:out value="${listLieux.description}" /> </td>
                        <td><c:out value="${listLieux.adresse}" /> </td>
                        <td>
                            <a href="<%=request.getContextPath()%>/user-restricted/modifier_utilisateur?u=<c:out value="${listutil.id}"/>" class="settings" title="Settings" data-toggle="tooltip"><i class="material-icons">&#xE8B8;</i></a>
                            <a href="<%=request.getContextPath()%>/user-restricted/pannel_admin?deleteLieu=<c:out value="${listLieux.id}"/>" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i>  </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>