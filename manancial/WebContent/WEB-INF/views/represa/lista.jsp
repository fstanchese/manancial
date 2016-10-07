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
<title>Cadastro de Represas</title>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
				<a href="${pageContext.request.contextPath}/represas/novo" class="btn btn-info" role="button">Nova Represa</a>
   				<a href="${pageContext.request.contextPath}/represas/relatorio" class="btn btn-info pull-right" role="button">Gerar PDF</a>
   			</div>
			<c:if test="${not empty represas}">
				<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th align=center>Sistema</th>
						<th align=center>Nome</th>
						<th align=center>Volume Util (x10<sup>6</sup>m3)</th>
						<th align=center>Volume Reserva</th>
						<th width="12%">&nbsp;&nbsp;Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="represa" items="${represas}">
						<tr id="row${represa.id}">
							<td>&nbsp;${represa.sistema.nome}</td>
							<td>&nbsp;${represa.nome}</td>
							<td>&nbsp;${represa.volumeUtil}</td>
							<td>&nbsp;${represa.volumeReserva}</td>
							<td width="12%">
								<a href="${pageContext.request.contextPath}/represas/edit/${represa.id}" class="btn btn-warning btn-xs" role="button">Alterar</a>
								<a href="${pageContext.request.contextPath}/represas/delete/${represa.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
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