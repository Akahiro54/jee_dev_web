<%@ page import="java.util.Base64" %><%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 29/12/2020
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Mes amis"/>
<jsp:include page="../header.jsp" />

<div class="container">
    <form class="form-inline" method="post" action="search.jsp">
        <input type="text" name="roll_no" class="form-control" placeholder="Search roll no..">
        <button type="submit" name="save" class="btn btn-primary">Search</button>
    </form>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div id="content" class="content content-full-width">
                <!-- begin profile-content -->
                <div class="profile-content">
                    <!-- begin tab-content -->
                    <div class="tab-content p-0">
                        <!-- begin #profile-friends tab -->
                        <div class="tab-pane fade in active show" id="profile-friends">
                            <h4 class="m-t-0 m-b-20">Nombre d'amis (<c:out value="${listeamis.size()}" />)</h4>
                            <!-- begin row -->
                            <div class="row row-space-2">
                                <c:forEach items="${listeamis}" var="listeamis">
                                <!-- begin col-6 -->
                                <div class="col-md-6 m-b-2">
                                    <div class="p-10 bg-white">
                                        <div class="media media-xs overflow-visible">
                                            <a class="media-left" href="javascript:;">
                                                    <img src="data:image/jpeg;base64,${listeImage.get(listeamis.id)}" alt="" width="50" height="50">
                                            </a>
                                            <div class="media-body valign-middle">
                                                <b class="text-inverse"><c:out value="${listeamis.nom}" />  <c:out value="${listeamis.prenom}" /></b>
                                            </div>
                                            <div class="media-body valign-middle text-right overflow-visible">
                                                <div class="btn-group dropdown">
                                                    <a href="javascript:;" class="btn btn-default">Amis</a>
                                                    <a href="javascript:;" data-toggle="dropdown" class="btn btn-default dropdown-toggle" aria-expanded="false"></a>
                                                    <ul class="dropdown-menu dropdown-menu-right" x-placement="bottom-end" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(101px, 34px, 0px);">
                                                        <li><a href="javascript:;">Supprimer</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                            <!-- end row -->
                        </div>
                        <!-- end #profile-friends tab -->
                    </div>
                    <!-- end tab-content -->
                </div>
                <!-- end profile-content -->
            </div>
        </div>
    </div>
</div>
<div><p>Zone de test</p>
<button>Hello ! </button>
</div>
<script>
    $(document).ready(function(){
        $("button").click(function(){
            $.post("amis", function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });
        });
    });
</script>
<jsp:include page="../footer.jsp" />