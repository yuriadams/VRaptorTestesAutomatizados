<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<html>
	<head>
		<title>Cadastrar Filme</title>
	</head>
	<body>		
			<span id="erros">${erros}</span>
			<span id="notice">${notice}</span>
		<c:if test="${editar!=true}">
				<div>
					<label>Codigo</label>
					<li>${filme.codigo} </li>						
				</div>
				<div>
					<label>Titulo</label>
					<li>${filme.titulo} </li>						
				</div>
				<div>
					<label>Ano</label>						
					<li>${filme.ano}</li>	
				</div>
				<a href="<c:url value='/filme/${filme.codigo}/alterar'/>">Editar</a>
		</c:if>
		<c:if test="${editar==true}">	
			<form action="<c:url value="/filme/alterar/edita"/>" name="form" method="post">
				<div>
					<label>
						Codigo
					</label>						
					<input type="text" name="filme.codigo" value="${filme.codigo}" readonly="readonly"/>
				</div>
				<div>
					<label>
						Titulo
					</label>						
					<input type="text" name="filme.titulo" id="nome" size="50" maxlength="50"
						value="${filme.titulo}" />
				</div>
				<div>
					<label>
						Ano
					</label>						
					<input type="text" name="filme.ano" size="50" maxlength="50"
						value="${filme.ano}"/>
				</div>
				<input type="submit" value="Salvar" name="Salvar" />
			</form>
		</c:if>	
	</body>
</html>