<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 28/12/2020
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Nouvelle activité"/>
<jsp:include page="../header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Créer une activité</h1>
    <form method="post" class="mx-auto text-center rounded formulaire pt-1" action="creer_activite">
        <p>${form.errors['database']}</p>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="nom_activite">Nom de l'activité <span class="required">*</span> : </label>
                <input type="text" class="form-control my-auto col-8" id="nom_activite" name="nom_activite" value="<c:out value="${activity.nom}"/>" placeholder="Entrez le nom de l'activité"/>
                <span class="erreur text-danger text-center">${form.errors['nom_activite']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="date_debut">Date de début <span class="required">*</span> : </label>
                <input type="date" class="form-control my-auto col-8" id="date_debut" name="date_debut" class="form-control" value="<c:out value="${activity.dateDebut}"/>">
                <span class="erreur text-danger text-center">${form.errors['date_debut']}</span>
            </div>
        </div>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="heure_debut">Heure de début <span class="required">*</span> : </label>
                <input type="time" class="form-control my-auto col-8" id="heure_debut" name="heure_debut" class="form-control" value="<c:out value="${activity.heureDebut}"/>">
                <span class="erreur text-danger text-center">${form.errors['heure_debut']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="date_fin">Date de fin <span class="required">*</span> : </label>
                <input type="date" class="form-control my-auto col-8" id="date_fin" name="date_fin" class="form-control" value="<c:out value="${activity.dateFin}"/>">
                <span class="erreur text-danger text-center">${form.errors['date_fin']}</span>
            </div>
        </div>
        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="heure_fin">Heure de fin <span class="required">*</span> : </label>
                <input type="time" class="form-control my-auto col-8" id="heure_fin" name="heure_fin" class="form-control" value="<c:out value="${activity.heureFin}"/>">
                <span class="erreur text-danger text-center">${form.errors['heure_fin']}</span>
            </div>
        </div>

        <div class="form-group ml-1 mr-2">
            <div class="mx-auto row">
                <label class="col-form-label col-4" for="lieu">Choisissez un lieu <span class="required">*</span> : </label>
                <select id="lieu" class="form-control my-auto col-8" name="lieu">
                    <c:forEach items="${lieux}" var="lieu">
                        <c:choose>
                            <c:when test="${lieu.id==activity.idLieu}">
                                <option selected="selected" value="<c:out value="${lieu.id}"/>"><c:out value="${lieu.nom}"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="<c:out value="${lieu.id}"/>"><c:out value="${lieu.nom}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <span class="erreur text-danger text-center">${form.errors['lieu']}</span>
            </div>
        </div>

        <p style="color:red"> TODO : button to add a place</p>

        <input type="submit" value="Ajouter l'activité" class="btn btn-dark mb-2"/>
    </form>
</div>
<jsp:include page="../footer.jsp" />