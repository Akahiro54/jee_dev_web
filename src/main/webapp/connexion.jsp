<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</head>
<body>
<div class="mx-auto center">
    <h1>Connexion</h1>
    <form method="post" class="mx-auto" action="connexion">
        <fieldset>
            <legend>Connexion</legend>
            <p>Connectez vous</p>

            <label for="email">Adresse email <span class="requis">*</span></label>
            <input type="email" id="email" name="email" value="" size="20" maxlength="60" />
            <br />

            <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
            <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
            <br />


            <input type="submit" value="Connexion" class="sansLabel" />
            <br />
        </fieldset>
    </form>
</div>
</body>
</html>