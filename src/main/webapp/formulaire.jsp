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
        <div class="col-4">
            <div class="form-group">
                <label for="email">Adresse email : </label>
                <input type="email" class="form-control" name="email" id="email" placeholder="Email">
            </div>
            <div class="form-group">
                <label for="pass">Mot de passe : </label>
                <input type="password" class="form-control" name="pass" id="pass" placeholder="Password">
            </div>
            <div class="form-group">
                <label for="confirmation">Confirmation du mot de passe : </label>
                <input type="password" class="form-control" name="pass" id="confirmation" placeholder="Password confirmation">
            </div>
            <button  type="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
</div>
</body>
</html>