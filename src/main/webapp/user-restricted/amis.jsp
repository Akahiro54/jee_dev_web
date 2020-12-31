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
<div class="mt-2 mb-2">
    <div class="container">
        <div class="row">
         <div class="col text-center">
            <input type="text" name="roll_no" id="recherche" class="form-control" placeholder="Chercher des amis ...">
            <button name="save" class="btn btn-primary mx-auto">Rechercher des amis</button>
         </div>
        </div>
        <div class="row" id="resultatsRecherche"></div>
    </div>
    <div class="mx-auto text-center" id="resultatAjout"></div>
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
                                            <a class="media-left" href="#">
                                                    <c:choose>
                                                        <c:when test="${empty listeImage.get(listeamis.id)}">
                                                            <img src="<%=request.getContextPath()%>/img/profile.jpg" alt="" width="50" height="50">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="data:image/jpeg;base64,${listeImage.get(listeamis.id)}" alt="" width="50" height="50">
                                                        </c:otherwise>
                                                    </c:choose>
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
<script>
    $(document).ready(function(){
        $("button").click(function(){
           startSearch();
        });
        $("#recherche").keypress(function(keycode){
            if(keycode.which == 13) {
                startSearch();
            }
        });
    });

    function addFriend(id) {
        $.post("amis",
            {
                ami: id
            },
            function(data){
                var resAjout = $('#resultatAjout');
                resAjout.empty();
                resAjout.removeClass('text-info');
                resAjout.removeClass('text-danger');
                if(data.includes("Impossible")) {
                    resAjout.addClass('text-danger');
                } else {
                    resAjout.addClass('text-info');
                }
                $('#resultatsRecherche').empty();
                resAjout.prepend(data);
            });
    }
    function startSearch() {
        $.post("amis",
            {
                nickSearch: $("#recherche").val()
            },
            function(data,status){
                $('#resultatsRecherche').empty();
                if(data == "error") {
                    alert("Merci d'entrer une recherche valide : 3 à 255 caractères, lettres chiffres et tirets (- , _).");
                } else {
                    jQuery.each(jsonData = JSON.parse(data), function(index) {
                        idAmi = jsonData[index].id;
                        $('#resultatsRecherche').prepend(
                            '<div id="ami'+idAmi+'" class="resultat card col-12 col-sm-12 col-md-6 col-xl-4 pt-1 pb-1 text-center align-middle">'+jsonData[index].pseudo + '<button id="btnAmi'+idAmi+'" onclick="addFriend('+idAmi+')" class="btn btn-danger">Ajouter l\'ami</button>' +'</div>'
                        );
                    })
                }
                //console.log("Data: " + data + "\nStatus: " + status); //used for debug purposes
            });
    }
</script>
<jsp:include page="../footer.jsp" />