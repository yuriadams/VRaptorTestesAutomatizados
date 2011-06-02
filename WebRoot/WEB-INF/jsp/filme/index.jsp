<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Cadastro de Filmes</title>
	</head>
	<body>
		<span id="erros">${erros}</span>
		<span id="notice">${notice}</span>
		<form action="<c:url value="/filme/pesquisa" />" name="form" method="get">
			<label>TITULO:</label>
			<input type="text" id="titulo" name="filme.titulo" value=""/>
			<label>ANO:</label>
			<input type="text" id="ano" name="filme.ano" value=""/>
			<input type="submit" value="Pesquisar" title="Pesquisar"/>
		</form>
		
		<table border="1" width="100%" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td width="100">
								Codigo
							</td>
							<td>
								Titulo
							</td>
							<td width="100">
								Ano
							</td>
							<td>
								Acoes
							</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty filmes}">
							<tr>
								<td colspan="5" style="text-align: center;">
									Nenhum membro encontrado
								</td>
							</tr>
						</c:if>
						<c:forEach var="f" items="${filmes}" varStatus="s">
							<tr>
								<td>
									${f.codigo}
								</td>
								<td style="text-align: left;">
									${f.titulo}
								</td>
								<td>
									${f.ano}
								</td>
								<td>
									<a href="<c:url value='/filme/${f.codigo}'/>">Visualizar</a>
									---
									<a href="<c:url value='/filme/${f.codigo}/alterar'/>">alterar</a>
									---
									<a href="<c:url value='/filme/deletar/${f.codigo}'/>">excluir</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<a href="${ctx}/filme/novo">Inserir um novo Filme a lista</a>
	</body>
</html>