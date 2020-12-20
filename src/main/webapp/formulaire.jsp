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
    <h1>Inscription</h1>
    <form method="post" class="mx-auto" action="register">
        <fieldset>
            <legend>Inscription</legend>
            <p>Créez votre compte</p>
            <p>${form.erreurs['database']}</p>

            <label for="email">Adresse email <span class="requis">*</span></label>
            <input type="email" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
            <span class="erreur">${form.erreurs['email']}</span>
            <br />

            <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
            <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
            <span class="erreur">${form.erreurs['motdepasse']}</span>
            <br />

            <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
            <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
            <span class="erreur">${form.erreurs['confirmation']}</span>
            <br />

            <label for="nom">Nom</label>
            <input type="text" id="nom" name="nom" value="<c:out value="${user.nom}"/>" size="20" maxlength="20" />
            <span class="erreur">${form.erreurs['nom']}</span>
            <br />

            <label for="prenom">Prénom</label>
            <input type="text" id="prenom" name="prenom" value="<c:out value="${user.prenomn}"/>" size="20" maxlength="20" />
            <span class="erreur">${form.erreurs['prenom']}</span>
            <br />

            <input type="submit" value="Inscription" class="sansLabel" />
            <br />

            <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
        </fieldset>
    </form>
</div>
</body>
</html>