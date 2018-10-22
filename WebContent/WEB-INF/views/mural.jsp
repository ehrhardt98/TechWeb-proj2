<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
	<head>
	<%@ page import="java.util.*,mvc.model.*,mvc.controller.*" %>
	<%@ page import="java.awt.image.BufferedImage.*,mvc.model.*,mvc.controller.*" %>
	<%@ page import="java.io.ByteArrayInputStream.*,mvc.model.*,mvc.controller.*" %>
	<%@ page import="javax.imageio.ImageIO.*,mvc.model.*,mvc.controller.*" %>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">

	    <title>Wall</title>

	    <style type="text/css">
			@import url("css/styles.css");
			.inputfile {
				width: 0.1px;
				height: 0.1px;
				opacity: 0;
				overflow: hidden;
				position: absolute;
				z-index: -1;
			}
		</style>
	</head>


	<body>
		<%-- <jsp:useBean id="dao" class="br.edu.Insper.DAO"/> --%>
		<div class="nav-bar">
			<div class="title">
			<% DAO dao2 = new DAO();
				Integer id_mural = (Integer)request.getAttribute("id_mural");
				Integer id_usuario = (Integer)request.getAttribute("id_usuario");
				%>
				<h2><%=dao2.getNomeMural(id_mural) %></h2>
			</div>
			
			<div class="menu-button">
				<h2>Menu</h2>
			</div>
			<form class="search">
				<input type="text" name="search" placeholder="Procurar..." style="border: none;
				    border-bottom: solid;
				    border-radius: 5px;
				    color: white;
				    border-color: #a0a0a0;
				    background-color: #5d5d5d;
				    height: 32px;
				    width: 32%;
				    min-width: 130px;
				    background-position: 9px 4px; 
				    background-image: url('img/search-icon.png');
				    background-repeat: no-repeat;
				    padding-left: 40px;">
			</form>
		</div>

		<div class="menu">
			<form action="voltaHome">
				<button type="submit" class="menu-item" name="home" value="<%=id_usuario%>">Meus murais</button>
			</form>
			<a href="inicio.jsp" class="menu-item">Logout</a>
		</div>
	<br/>
		<div class="canvas">
			<div class="create-note-div">
				<form class="create-note-form" action="criaNota">
					<input type="hidden" name="id_usuario" value="<%=id_usuario%>"/>
					<input type="text" name="create_note" placeholder="Crie uma nota" style="display: block;
	   				border-style: solid;
				    border-width: 2px;
				    border-color: #a0a0a0;
				    border-radius: 4px;
				    box-shadow: 1px 3px 3px grey;
				    height: 32px;
				    width: 48%;
				    margin: 0;
				    padding: 0px 0px 0px 12px;">
				    <button type="submit" name="id_mural" value="<%=id_mural%>"style="display: none;"></button>
				</form>
			</div>
			
			<%  ArrayList<Nota> notas = dao2.getListaNotas(id_mural);
				for (Nota nota : notas ) { %>
				<table style="display: inline;
			    margin: 0px 8px;
			    width: auto;
			    height: auto;
			    min-width: 100px;
			    min-height: 100px;">
			    
					<tr style="display: block;
				    background-color: #cccc00;
				    width: auto;
				    height: auto;
				    word-break: break-word;">
						<td name="id" style="display: inline-block;
					    max-width: 90%;
					    height: 100%;
					    word-break: break-word;">
							<%=nota.getId() %>
						</td>
						<td style="display: inline-block;
					    float: right;
					    width: auto;
					    word-break: break-word;
					    vertical-align: middle;
					    margin: auto;
					    height: 20px;
					    padding: 0px">
						<form action="deletaNota">
							<input type="hidden" name="id_usuario" value="<%=id_usuario%>">
							<input type="hidden" name="id_mural" value="<%=id_mural%>">
							<button type="submit" name="id_nota" value="<%=nota.getId() %>" style="display: block;
						    width: 20px;
						    height: 20px;
						    background-position: 0px 0px;
						    border: 0px;
						    padding: 1px;
						    cursor: pointer;
						    background-color: #cccc00;">
						    	<img src="img/oba.png" title="Apagar nota" style="display: block;
						    	height: 18px;						    	
							    margin-left: auto;
							    margin-right: auto;">
						    </button>
						</form>
					</td>
					</tr>
					
					<tr style="display: block;
				    background-color: #ffff99;
				    width: auto;
				    height: auto;
				    word-break: break-word;">
						<td class="bot-nota-cell">
						<form action="alteraNota" id="altera-nota">
							<input type="hidden" name="id_nota" value="<%=nota.getId() %>">
							<input type="hidden" name="tipo_nota" value="<%=nota.getTipo() %>">
							<input type="hidden" name="id_usuario" value="<%=id_usuario%>">
							<input type="text" name="edit_nota" value="<%=nota.getConteudo() %>"
							 style="outline: none;
							 background: transparent;
							 border-top: transparent;
							 border-right: transparent;
							 border-left: transparent;
							 border-bottom: transparent">
							<button type="submit" name="id_mural" value="<%=id_mural%>">
							    <img src="img/edit.png" title="Adicionar Imagem" style="display: block;
						    	height: 13px;						    	
							    margin-left: auto;
							    margin-right: auto;"></img>
							</button>
						</form>
						
						<form action="criaBlob" method="post" enctype="multipart/form-data">
							<input type="hidden" name="id_nota" value="<%=nota.getId() %>">
							<input type="hidden" name="id_mural" value="<%=id_mural%>">
							<input type="hidden" name="id_usuario" value="<%=id_usuario%>">
							<label for="lable" style="display: block;
							    width: 20px;
							    height: 20px;
							    background-position: 0px 0px;
							    border: 0px;
							    padding: 1px;
							    cursor: pointer;
							    background-color: #ffff99;">
							    <img src="img/add.png" title="Adicionar Imagem" style="display: block;
						    	height: 22px;						    	
							    margin-left: auto;
							    margin-right: auto;"></img>
							</label>
							<input type="file" onchange="this.form.submit()" id="lable" name="blob" required="required" class="inputfile">
						</form>
						
						<form action="mostraBlob" method="post">
							<input type="hidden" name="id_nota" value="<%=nota.getId() %>">
							<input type="hidden" name="id_mural" value="<%=id_mural %>">
							<input type="hidden" name="id_usuario" value="<%=id_usuario %>">
							<button type="submit">Mostrar Imagem </button>
						</form>
						</td>
					</tr>
					
				</table>
			<% } %>
			
		</div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/main.js"></script>
	</body>
</html>