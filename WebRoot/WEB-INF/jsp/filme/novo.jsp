<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<html>
	<head>
		<title>Cadastrar Filme</title>
	</head>
	<body>		
			<span id="error" >
				<c:forEach var="error" items="${errors}"><li>${error.message}</li></c:forEach>
			</span>
			<span id="erros">${erros}</span>
			<span id="notice">${notice}</span>
		<form action="<c:url value="/filme/novo/salvar" />" name="form" method="post">
					<div>
						<label>
							Titulo
						</label>						
						<input type="text" name="filme.titulo" id="titulo" size="50" maxlength="50"
							value="" />
					</div>
					<div>
						<label>
							Ano
						</label>						
						<input type="text" name="filme.ano" size="50" maxlength="50"
							value=""/>
					</div>
					<input type="submit" value="Salvar" name="Salvar" />
				</form>
	</body>
</html>