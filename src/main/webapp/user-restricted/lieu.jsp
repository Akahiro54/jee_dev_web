<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 29/12/2020
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Lieu"/>
<jsp:include page="../header.jsp" />
<div class="fond-element rounded mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6 mt-2 pb-2">
    <h1><c:out value="${lieu.nom}"/></h1>
    <c:if test="${not empty lieu.description}">
        <dl class="row">
            <dt class="col-sm-3">Description : </dt>
            <dd class="col-sm-9"><c:out value="${lieu.description}"/></dd>
        </dl>
    </c:if>
    <dl class="row">
        <dt class="col-sm-3">Adresse : </dt>
        <dd class="col-sm-9"><c:out value="${lieu.adresse}"/></dd>
    </dl>
    <dl class="row">
        <dt class="col-sm-3">Coordonnées : </dt>
        <dd class="col-sm-9"><c:out value="${lieu.latitude}"/> (latitude) , <c:out value="${lieu.longitude}"/> (longitude) </dd>
    </dl>
    <div id="map" class="map"></div>
    <script type="text/javascript">
        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([<c:out value="${lieu.longitude}"/>, <c:out value="${lieu.latitude}"/>]),
                zoom: 12
            }),
        });
        var feature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.fromLonLat([<c:out value="${lieu.longitude}"/>, <c:out value="${lieu.latitude}"/>]))
        });
        feature.setStyle(
                new ol.style.Style({
                    image: new ol.style.Circle({
                        fill: new ol.style.Fill({ color: [0,0,200,0.5] }),
                        stroke: new ol.style.Stroke({ color: [0,0,255,1] }),
                        radius: 5
                    })
                })
            );
        var layer = new ol.layer.Vector({
            source: new ol.source.Vector({
                features: [feature],
            }),
        });
        map.addLayer(layer);
    </script>
</div>
<jsp:include page="../footer.jsp" />
