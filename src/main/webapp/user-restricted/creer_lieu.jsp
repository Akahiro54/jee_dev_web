<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 28/12/2020
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Nouveau lieu"/>
<jsp:include page="../header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Créer un lieu</h1>
    <form method="post" class="mx-auto text-center rounded formulaire pt-1" accept-charset="UTF-8" action="creer_lieu">
        <p>${form.errors['database']}</p>
<%--        NOM--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="nom_lieu">Nom du lieu <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="nom_lieu" name="nom_lieu" value="<c:out value="${place.nom}"/>" placeholder="Entrez le nom du lieu"/>
                <span class="erreur text-danger text-center">${form.errors['nom_lieu']}</span>
            </div>
        </div>

<%--        DESCRIPTION MULTILINE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="description_lieu">Description du lieu : </label>
<%--                <input type="text" class="form-control my-auto col-8" id="description_lieu" name="description_lieu" value="<c:out value="${place.description}"/>" placeholder="Décrivez le lieu (optionnel)"/>--%>
                    <textarea class="form-control my-auto col-8" rows="3"  id="description_lieu" name="description_lieu" value="<c:out value="${place.description}"/>" placeholder="Décrivez le lieu (optionnel)"></textarea>
                <span class="erreur text-danger text-center">${form.errors['description_lieu']}</span>
            </div>
        </div>

<%--        ADRESSE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="adresse_lieu">Adresse du lieu <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="adresse_lieu" name="adresse_lieu" value="<c:out value="${place.adresse}"/>" placeholder="Entrez l'adresse du lieu"/>
                <span class="erreur text-danger text-center">${form.errors['adresse_lieu']}</span>
            </div>
        </div>

<%--        LATITUDE + LONGITUDE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="latitude">Latitude : </label>
                <input type="text" class="form-control my-auto col-8" id="latitude" name="latitude" value="<c:out value="${place.latitude}"/>" placeholder="Entrez la latitude (optionnel)"/>
                <span class="erreur text-danger text-center">${form.errors['latitude']}</span>
            </div>
        </div>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="longitude">Longitude : </label>
                <input type="text" class="form-control my-auto col-8" id="longitude" name="longitude" value="<c:out value="${place.longitude}"/>" placeholder="Entrez la longitude (optionnel)"/>
                <span class="erreur text-danger text-center">${form.errors['longitude']}</span>
            </div>
        </div>

        <p style="color:red"> TODO : button to add an image if we have time </p>
        <input type="submit" value="Créer le lieu" class="btn btn-dark mb-2"/>
    </form>
</div>
<jsp:include page="../footer.jsp" />
