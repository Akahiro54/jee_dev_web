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

<div class="mt-2">
    <h1 class="text-center mb-2">Bienvenue sur l'application PreventCovid !</h1>
   <c:choose>
        <c:when test="${!empty sessionScope.sessionUtilisateur}">
            <div class="fond-element rounded mx-auto col-auto col-sm-12 col-md-9 col-xl-6 mt-2 pt-1 pb-2">
                <h3 class="text-center">Bienvenue, <c:out value="${sessionScope.sessionUtilisateur.pseudo}"/> !</h3>
                <p>Utilisez le menu en haut de page pour effectuer les différentes actions souhaitées : </p>
                <ul>
                    <li>Accueil : accédez à cette page d'information</li>
                    <li>Mon compte : accédez à vos informations de profil et modifiez les</li>
                    <li>Mes activités : accédez à l'ensemble de vos activitées triées par ordre chronologique</li>
                    <li>Lieux : accédez à la liste des lieux existants et créez en de nouveaux si nécessaire</li>
                    <li>Mes amis : accédez à votre liste d'amis et gérez la en supprimant ou ajoutant de nouveaux amis</li>
                    <li>Mes notifications : accédez à l'ensemble de vos notifications</li>
                    <li>Déconnexion : déconnectez vous de votre compte</li>
                </ul>
                <p>Si vous avez des notifications, le menu notification sera marqué d'un badge rouge !</p>
            </div>
        </c:when>
        <c:otherwise>
        <div class="fond-element rounded mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6 mt-2 pt-1 pb-2">
                <h3>Qu'est-ce que l'application PreventCovid ?</h3>
                <p>L'application PreventCovid a pour objectif de limiter la propagation du Covid en permettant à ses utilisateurs de savoir au plus tôt si ils sont des
                cas contacts du virus Covid-19.</p>
                <p>Dans l'application PreventCovid, vous pouvez déclarer les activités que vous effectuez dans leur lieux respectifs et être prévenu immédiatement
                dès qu'une personne présente dans le même lieu !</p>
                <p>Vous pouvez également ajouter vos amis sur le site et recevrez une notification dans le cas où ils seraient positifs !</p>
                <p>Grâce à cette application, vous serez prévenu le plus rapidement possible si vous êtes un cas contact et pourrez donc vous isoler au plus tôt.</p>
                <p>Pour commencer, vous pouvez  <a>créer un compte</a> ou bien vous <a>connecter</a>. </p>
        </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp" />