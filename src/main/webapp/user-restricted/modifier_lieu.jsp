<%--
  Created by IntelliJ IDEA.
  User: florian
  Date: 07/01/2021
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Modifier lieu"/>
<jsp:include page="../header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Modifier le lieu</h1>
    <form method="post" class="mx-auto text-center rounded fond-element pt-1" accept-charset="UTF-8" action="modifier_lieu">
        <p>${form.errors['database']}</p>
        <%--        NOM--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="nom_lieu">Nom du lieu <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="nom_lieu" name="nom_lieu" value="<c:out value="${place.nom}"/>"/>
                <span class="erreur text-danger text-center">${form.errors['nom_lieu']}</span>
            </div>
        </div>

        <%--        DESCRIPTION MULTILINE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="description_lieu">Description du lieu : </label>
                <%--                <input type="text" class="form-control my-auto col-8" id="description_lieu" name="description_lieu" value="<c:out value="${place.description}"/>" placeholder="Décrivez le lieu (optionnel)"/>--%>
                <textarea class="form-control my-auto col-8" rows="3"  id="description_lieu" name="description_lieu" placeholder="Décrivez le lieu (optionnel)"><c:out value="${place.description}"/></textarea>
                <span class="erreur text-danger text-center">${form.errors['description_lieu']}</span>
            </div>
        </div>

        <%--        ADRESSE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="adresse_lieu">Adresse du lieu <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="adresse_lieu" name="adresse_lieu" value="<c:out value="${place.adresse}"/>"/>
                <span class="erreur text-danger text-center">${form.errors['adresse_lieu']}</span>
            </div>
        </div>

        <%--        LATITUDE + LONGITUDE--%>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="latitude">Latitude : </label>
                <input type="text" class="form-control my-auto col-8" id="latitude" name="latitude" value="<c:out value="${place.latitude}"/>"/>
                <span class="erreur text-danger text-center">${form.errors['latitude']}</span>
            </div>
        </div>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="longitude">Longitude : </label>
                <input type="text" class="form-control my-auto col-8" id="longitude" name="longitude" value="<c:out value="${place.longitude}"/>"/>
                <span class="erreur text-danger text-center">${form.errors['longitude']}</span>
            </div>
        </div>
        <input type="submit" value="Modifier le lieu" class="btn btn-dark mb-2"/>
    </form>
</div>
<jsp:include page="../footer.jsp" />
