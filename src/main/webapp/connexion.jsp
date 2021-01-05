<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Connexion"/>
<jsp:include page="header.jsp" />
<c:if test="${!empty sessionScope.sessionUtilisateur}">
    <%-- Si l'utilisateur existe en session, alors on le redirige sur son profil. --%>
    <c:redirect url="user-restricted/profil"/>
</c:if>
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Connexion</h1>
    <p>Connectez-vous !</p>
    <form method="post" class="mx-auto text-center rounded fond-element pt-2" action="connexion">
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-2" for="email">Adresse email : </label>
                <input type="email" class="form-control my-auto col-10" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
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
    </form>
</div>
<jsp:include page="footer.jsp" />