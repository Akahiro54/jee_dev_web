<%--
  Created by IntelliJ IDEA.
  User: florian
  Date: 06/01/2021
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="AjoutUtil"/>
<jsp:include page="../header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Ajouter un utilisateur</h1>
    <hr>
    <form method="post" class="mx-auto text-center rounded fond-element pt-1" action="ajout_utilisateur">
        <p>${form.errors['database']}</p>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="email">Adresse email <span class="required">*</span> : </label>
                <input type="email" class="form-control my-auto col-8" id="email" name="email" value="<c:out value="${user.email}"/>" placeholder="Entrez votre adresse email" size="300px"/>
                <span class="erreur text-danger text-center">${form.errors['email']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="motdepasse">Mot de passe <span class="required">*</span> : </label>
                <input type="password" class="form-control my-auto col-8" id="motdepasse" placeholder="Entrez un mot de passe" name="motdepasse" value=""/>
                <span class="erreur text-danger text-center">${form.errors['motdepasse']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="confirmation">Confirmation du mot de passe <span class="required">*</span> : </label>
                <input type="password" class="form-control my-auto col-8" id="confirmation" name="confirmation" placeholder="Confirmez le mot de passe" value=""/>
                <span class="erreur text-danger text-center">${form.errors['confirmation']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="pseudo">Pseudonyme <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="pseudo" name="pseudo" value="<c:out value="${user.pseudo}"/>" placeholder="Entrez un pseudonyme" />
                <span class="erreur text-danger text-center">${form.errors['pseudo']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="nom">Nom <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="nom" name="nom" value="<c:out value="${user.nom}"/>" placeholder="Entrez votre nom" />
                <span class="erreur text-danger text-center">${form.errors['nom']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="prenom">Prénom <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="prenom" name="prenom" value="<c:out value="${user.prenom}"/>" placeholder="Entrez votre prénom" />
                <span class="erreur text-danger text-center">${form.errors['prenom']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="date_naissance">Date de naissance <span class="required">*</span> : </label>
                <input type="date" class="form-control my-auto col-8" id="date_naissance" name="date_naissance" class="form-control" value="<c:out value="${user.date}"/>">
                <span class="erreur text-danger text-center">${form.errors['date_naissance']}</span>
            </div>
        </div>

        <input type="submit" value="Ajouter" class="btn btn-dark mb-2"/>
    </form>

</div>
<jsp:include page="../footer.jsp" />