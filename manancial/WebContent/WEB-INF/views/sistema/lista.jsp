<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<style type="text/css">
	<c:set var="path" value="${pageContext.request.contextPath}" scope="request"/>
	@IMPORT url("${path}/resources/css/bootstrap.min.css");
	@IMPORT url("${path}/resources/css/bootstrap-theme.min.css");
	@IMPORT url("${path}/resources/css/custom.css");
	@IMPORT url("${path}/resources/css/style.css");
	@IMPORT url("${path}/resources/css/zebra.dialog.css");
</style>
<meta charset="UTF-8">
<title>Cadastro de Sistemas</title>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
				<a href="${pageContext.request.contextPath}/sistemas/novo" class="btn btn-info" role="button">Novo Sistema</a>
  				<a href="${pageContext.request.contextPath}/sistemas/relatorio" class="btn btn-info pull-right" role="button">Gerar PDF</a>
   			</div>
			<c:if test="${not empty sistemas}">
				<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th align=center>Nome</th>
						<th align=center>Municipios atendidos</th>
						<th width="12%">&nbsp;&nbsp;Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sistema" items="${sistemas}">
						<tr id="row${sistema.id}">
							<td>&nbsp;${sistema.nome}</td>
							<c:if test="${not empty sistema.municipios}">
							<td>
							<c:forEach var="municipio" items="${sistema.municipios}">
								&nbsp;${municipio.nome},
							</c:forEach>
							</td>
							</c:if>
							<c:if test="${empty sistema.municipios}">
								<td>&nbsp;</td>
							</c:if>							
							<td width="12%">
								<a href="${pageContext.request.contextPath}/sistemas/edit/${sistema.id}" class="btn btn-warning btn-xs" role="button">Alterar</a>
								<a href="${pageContext.request.contextPath}/sistemas/delete/${sistema.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</c:if>
		</div>
		</div>
	</div>
	<script src="${path}/resources/js/jquery.min.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>
</body>
</html>