<%--
  Created by IntelliJ IDEA.
  User: florian
  Date: 28/12/2020
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Modifier mon profil"/>
<jsp:include page="../header.jsp" />
<div class="container">
    <div class="view-account">
        <section class="module">
            <div class="module-inner">
                <div class="content-panel">
                    <form method="post" action="<%=request.getContextPath()%>/user-restricted/modifprofil" class="form-horizontal">
                        <fieldset class="fieldset">
                            <div class="form-group avatar">
                                <figure class="figure col-md-2 col-sm-3 col-xs-12">
                                    <img class="img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                                </figure>
                                <div class="form-inline col-md-10 col-sm-9 col-xs-12">
                                    <input type="file" class="file-uploader pull-left">
                                    <button type="submit" class="btn btn-sm btn-default-alt pull-left">Valider</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Prénom</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifprenom" type="text" class="form-control" value="${utilisateur.prenom}">
                                    <span class="erreur text-danger text-center">${form.errors['prenom']}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Nom</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifnom" type="text" class="form-control" value="${utilisateur.nom}">
                                    <span class="erreur text-danger text-center">${form.errors['nom']}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2  col-sm-3 col-xs-12 control-label">Email</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifemail" type="email" class="form-control" value="${utilisateur.email}">
                                    <span class="erreur text-danger text-center">${form.errors['email']}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Date de naissance</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifdate" type="date" class="form-control" value="${utilisateur.date}">
                                    <span class="erreur text-danger text-center">${form.errors['date_naissance']}</span>
                                </div>
                            </div>
                        </fieldset>
                        <hr>
                        <div class="form-group">
                            <div class="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                                <input class="btn btn-primary" type="submit" value="Valider">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</div>
<jsp:include page="../footer.jsp" />