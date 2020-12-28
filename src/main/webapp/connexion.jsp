<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Connexion"/>
<jsp:include page="header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Connexion</h1>
    <p>Connectez-vous !</p>
    <form method="post" class="mx-auto text-center rounded formulaire pt-2" action="connexion">
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-2" for="email">Adresse email : </label>
                <input type="email" class="form-control my-auto col-10" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.errors['email']}</span>
            </div>
        </div>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-2" for="motdepasse">Mot de passe : </label>
                <input type="password" class="form-control my-auto col-10" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.errors['motdepasse']}</span>
            </div>
        </div>

        <input type="submit" value="Connexion" class="btn btn-dark mb-2" />
        <%-- Vérification de la présence d'un objet utilisateur en session --%>
        <c:if test="${!empty sessionScope.sessionUtilisateur}">
            <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            <p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.sessionUtilisateur.email}</p>
        </c:if>
    </form>
</div>
<jsp:include page="footer.jsp" />