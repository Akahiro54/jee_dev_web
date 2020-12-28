<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
  <!--  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    -->
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/bootstrap-datepicker.css"/>
    <link rel="stylesheet" href="css/inscription.css"/>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-datepicker.fr.min.js"></script>

    <script type='text/javascript'>
        $(function () {
            $('#datepicker').datepicker({
                language: "fr",
                inline: true,
                sideBySide: true,
                format: 'yyyy-mm-dd'
            });
        });
    </script>
</head>
<body class="fond">
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
    <h1>Inscription</h1>
    <p>Créez votre compte maintenant et soyez prévenu immédiatement si vous êtes un cas contact !</p>
        <form method="post" class="mx-auto text-center rounded formulaire pt-1" action="inscription">
            <p>${form.errors['database']}</p>
            <div class="form-group ml-1 mr-2 d-inline-flex">
                <div class="mx-auto row">
                    <label class="col-form-label col-4" for="email">Adresse email <span class="required">*</span> : </label>
                    <input type="email" class="form-control my-auto col-8" id="email" name="email" value="<c:out value="${user.email}"/>" placeholder="Entrez votre adresse email" size="300px"/>
                    <span class="erreur text-danger text-center">${form.errors['email']}</span>
                </div>
            </div>

            <div class="form-group ml-1 mr-2">
                <div class="mx-auto row">
                    <label class="col-form-label col-4" for="motdepasse">Mot de passe <span class="required">*</span> : </label>
                    <input type="password" class="form-control my-auto col-8" id="motdepasse" placeholder="Entrez un mot de passe" name="motdepasse" value=""/>
                    <span class="erreur text-danger text-center">${form.errors['motdepasse']}</span>
                </div>
            </div>
            <div class="form-group ml-1 mr-2">
                <div class="mx-auto row">
                    <label class="col-form-label col-4" for="confirmation">Confirmation du mot de passe <span class="required">*</span> : </label>
                    <input type="password" class="form-control my-auto col-8" id="confirmation" name="confirmation" placeholder="Confirmez le mot de passe" value=""/>
                    <span class="erreur text-danger text-center">${form.errors['confirmation']}</span>
                </div>
            </div>

            <div class="form-group ml-1 mr-2">
                <div class="mx-auto row">
                    <label class="col-form-label col-4" for="nom">Nom <span class="required">*</span> : </label>
                    <input type="text" class="form-control my-auto col-8" id="nom" name="nom" value="<c:out value="${user.nom}"/>" placeholder="Entrez votre nom" />
                    <span class="erreur text-danger text-center">${form.errors['nom']}</span>
                </div>
            </div>

            <div class="form-group ml-1 mr-2">
                <div class="mx-auto row">
                    <label class="col-form-label col-4" for="prenom">Prénom <span class="required">*</span> : </label>
                    <input type="text" class="form-control my-auto col-8" id="prenom" name="prenom" value="<c:out value="${user.prenom}"/>" placeholder="Entrez votre prénom" />
                    <span class="erreur text-danger text-center">${form.errors['prenom']}</span>
                </div>
            </div>

            <div class="form-group">
                <p style="word-wrap:break-word">Date de naissance (année/mois/jour) <span class="required">*</span> : </p>
                <div id="datepicker" class="d-flex justify-content-center" style="min-width:130px;">
                    <input type="hidden" name="date_naissance" id="my_hidden_input"  class="input text required" value="">
                </div>
                <span class="erreur text-danger text-center">${form.errors['date_naissance']}</span>
            </div>

            <input type="submit" value="Inscription" class="btn btn-dark mb-2"/>
        </form>
</div>
</body>
</html>