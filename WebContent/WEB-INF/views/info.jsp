<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>Inicio</title>
	
	<style type="text/css">
		@import url("css/styles.css");
	</style>
</head>

<body>
	<table class="login-table">
		<tr>
			<td class="login-col-l">
				<div class="login-box">
					<div class="login-titulo">Login</div>
					<form id="login-form" action="Login">
						<p class="info-text">Nome usuário</p>
						<input type="text" name="username-login"
							placeholder="Nome usuário"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">

						<p class="info-text">Senha</p>
						<input type="password" name="senha-login" placeholder="Senha"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">
					</form>
					<button type="submit" name="login" form="login-form" class="botao">Entrar</button>
				</div>
			</td>
			<td class="login-col-r">
				<div class="login-box">
					<div class="login-titulo">Cadastre-se</div>
					<form id="create-form" action="CriaUsuario">
						<p class="info-text">Nome usuário</p>
						<input type="text" name="username-create"
							placeholder="Nome usuário"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">

						<p class="info-text">E-mail</p>
						<input type="text" name="email-create" placeholder="E-mail"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">

						<p class="info-text">Senha</p>
						<input type="password" name="senha-create" placeholder="Senha"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">

						<p class="info-text">Confirme a senha</p>
						<input type="password" name="senha-confirm" placeholder="Senha"
							style="border: none; border-bottom: solid; border-radius: 5px; color: white; border-color: #a0a0a0; background-color: #5d5d5d; height: 26px; width: 99%; padding-left: 1%;">
						<button type="submit" class="botao">Cadastrar</button>
					</form>
				</div>
			</td>
		</tr>
	</table>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="js/main.js">
	</script>

</body>
</html>