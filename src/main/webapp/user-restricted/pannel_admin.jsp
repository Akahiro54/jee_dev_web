<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp" />
<c:set var="pageTitle" scope="request" value="Panel Admin"/>
<div class="container-xl">
    <div class="table-responsive">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><b>Utilisateurs</b></h2>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Pseudo</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Date Inscription</th>
                    <th>Rôle</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listeUtilisateur}" var="listutil">
                    <tr>
                        <td><c:out value="${listutil.id}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${empty listutil.image}">
                                    <img src="<%=request.getContextPath()%>/img/profile.jpg" alt="" width="50" height="50"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="data:image/jpeg;base64,${listutil.image}" alt="" width="50" height="50"/>
                                </c:otherwise>
                            </c:choose>
                            <c:out value="${listutil.pseudo}" />
                        </td>
                        <td><c:out value="${listutil.nom}" />  <c:out value="${listutil.prenom}" /></td>
                        <td><c:out value="${listutil.email}" /> </td>
                        <td><c:out value="${listutil.date}" /> </td>
                        <td><c:out value="${listutil.role}" /> </td>
                        <td><span class="status text-success">&bull;</span> Active</td>
                        <td>
                            <a href="#" class="settings" title="Settings" data-toggle="tooltip"><i class="material-icons">&#xE8B8;</i></a>
                            <a href="<%=request.getContextPath()%>/user-restricted/pannel_admin?delete=<c:out value="${listutil.id}"/>" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i>  </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="clearfix">
                <div class="hint-text">Showing <b>5</b> out of <b>25</b> entries</div>
                <ul class="pagination">
                    <li class="page-item disabled"><a href="#">Previous</a></li>
                    <li class="page-item"><a href="#" class="page-link">1</a></li>
                    <li class="page-item"><a href="#" class="page-link">2</a></li>
                    <li class="page-item active"><a href="#" class="page-link">3</a></li>
                    <li class="page-item"><a href="#" class="page-link">4</a></li>
                    <li class="page-item"><a href="#" class="page-link">5</a></li>
                    <li class="page-item"><a href="#" class="page-link">Next</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>