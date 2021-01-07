<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 28/12/2020
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Mes activités"/>
<jsp:include page="../header.jsp" />
    <div class="px-lg-5 px-sm-1 px-xl-5">
        <h1 class="text-center">Mes activités</h1>
        <div class="row">
            <c:forEach items="${activites}" var="activite">
                <div class="fond-element card col-12 col-sm-12 col-md-6 col-xl-4">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a class="desac-lien" href="<%=request.getContextPath()%>/user-restricted/activite?activite=<c:out value="${activite.key.id}"/>">
                                Nom : <c:out value="${activite.key.nom}"/>
                            </a>
                        </h5>
                        <h6 class="card-subtitle text-muted">
                            <a class="desac-lien" href="<%=request.getContextPath()%>/user-restricted/lieu?lieu=<c:out value="${activite.value.id}"/>">
                                Lieu : <c:out value="${activite.value.nom}"/>
                            </a>
                        </h6>
                        <p class="card-text"><br/>
                            Dates de l'activité :
                        <ul>
                            <li>Date de début : <c:out value="${activite.key.dateDebut}"/> à <c:out value="${activite.key.heureDebut}"/></li>
                            <li>Date de fin : <c:out value="${activite.key.dateFin}"/> à <c:out value="${activite.key.heureFin}"/></li>
                        </ul>
                        </p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
<jsp:include page="../footer.jsp" />
