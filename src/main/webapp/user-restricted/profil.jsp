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
</br>
<div class="container">
    <div class="main-body">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <c:choose>
                                <c:when test="${empty user.image}">
                                    <img src="<%=request.getContextPath()%>/img/profile.jpg" alt="" width="300" height="300">
                                </c:when>
                                <c:otherwise>
                                    <img src="data:image/jpeg;base64,${user.image}" width="300" height="300">
                                </c:otherwise>
                            </c:choose>
                            <div class="mt-3">
                                <h4>${user.nom}</h4><h5>${user.prenom}</h5>
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
                                ${user.nom}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Prénom</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.prenom}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Pseudonyme</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.pseudo}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.email}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Date de Naissance</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.date}
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
                    <div class="col-sm-6 mb-3 mx-auto">
                        <div id="covidInfoCard" class="card h-100 ">
                            <c:choose>
                                <c:when test="${user.contamine}">
                                    <div class="row">
                                        <div class="col-sm-3 text-center my-auto">
                                            <h6>Positif au COVID-19.</h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                               Vous êtes déclaré positif au covid depuis le ${user.dateContamination}.<br/>
                                               Votre état sera réinitialisé au bout de 15 jours.
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="showConfirmation()" class="list-group-item list-group-item-action list-group-item-warning">Je suis positif au covid</button>
                                    <div id="confirmPositive" class="text-center text-info" style="display:none;">
                                        Cette action est irréversible pour 15 jours, êtes vous sûr de vouloir vous déclarer positif au COVID ?
                                        <div id="fail" class="text-center text-danger"></div>
                                        <div class="mb-1">
                                            <button onclick="sendCovidPositive()" class="btn btn-primary">Confirmer</button>
                                            <button onclick="hideConfirmation()" class="btn btn-danger">Annuler</button>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function showConfirmation() {
        $("#confirmPositive").show();
    }
    function hideConfirmation() {
        $("#confirmPositive").hide();
    }
    function sendCovidPositive() {
        $.post("profil",
            {
                covid: 1
            },
            function(data, status){
                if(status === "success") {
                    console.log(data);
                    jsonData = JSON.parse(data);
                    var covidInfoCard = $("#covidInfoCard");
                    if(jsonData.success) { // info from the server
                        covidInfoCard.empty();
                        covidInfoCard.prepend(
                               '<div class="row"><div class="col-sm-3 text-center my-auto"><h6>Positif au COVID-19.</h6></div><div class="col-sm-9 text-secondary">Vous êtes déclaré positif au covid depuis le '+jsonData.message+'.<br/>Votre état sera réinitialisé au bout de 15 jours.</div></div>'
                        )
                    } else {
                        var fail = $("#fail");
                        fail.empty();
                        fail.prepend(jsonData.message);
                    }
                }
            });
    }
</script>
<jsp:include page="../footer.jsp" />


