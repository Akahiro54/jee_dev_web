<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mon compte</title>
</head>
<body>
Vos informations : <br/>
<%
    beans.Utilisateur user = (beans.Utilisateur)request.getAttribute("user");
    if(user != null) {
        out.println("Votre adresse mail : " + user.getEmail());
        out.println("<br/>Votre mot de passe : " + user.getPass());
    }
%>
</body>
</html>