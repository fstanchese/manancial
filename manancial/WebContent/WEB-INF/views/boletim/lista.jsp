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
</style>
<meta charset="UTF-8">
<title>Cadastro de Boletins</title>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<div class="panel panel-group">
   		<div class="panel panel-primary">
   			<div class="panel-heading">
			<form class="form-horizontal" action="${path}/boletins" method="get">
				<div class="col-md-12">
					<div class="row">
					<div class="form-group col-md-3">
						<label for="sistemaid">Sistemas</label> 
						<select id="sistemaid" name="sistemaid" class="form-control input-sm">
							<option value=""></option>
							<c:forEach var="sistemaFiltro" items="${sistemasFiltro}">
								<option value="${sistemaFiltro.id}"
									${sistemaFiltro.id==sistemaid ? 'selected' : ''}>${sistemaFiltro.nome}							
								</option>							
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-3">
						<label for="represaid">Represas</label> 
						<select id="represaid" name="represaid" class="form-control input-sm">
							<option value=""></option>
							<c:forEach var="represaFiltro" items="${represasFiltro}">
								<option value="${represaFiltro.id}"
									${represaFiltro.id==represaid ? 'selected' : ''}>${represaFiltro.nome}							
								</option>							
							</c:forEach>						</select>
					</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="row">
					<div class="form-group col-md-2">
						<button id="btn-procurar" class="btn btn-primary" type="submit"	>
							<span class="glyphicon glyphicon-search"> Filtrar</span>
						</button>
					</div>			
					</div>
				</div>	
			</form>
			<a href="${pageContext.request.contextPath}/boletins/novo" class="btn btn-info" role="button">Novo Boletim</a>
   			</div>
			<c:if test="${not empty boletins}">
				<table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th align=center>Data</th>
						<th align=center>Sistema</th>
						<th align=center>Represa</th>
						<th align=center>Volume (x10<sup>6</sup>m3)</th>
						<th align=center>Pluviometria</th>
						<th align=center>Variação</th>
						<th width="12%">&nbsp;&nbsp;Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="boletim" items="${boletins}">
						<tr id="row${boletim.id}">
							<td><fmt:formatDate value="${boletim.dataBoletim}" pattern="dd/MM/yyyy" /></td>
							<td>&nbsp;${boletim.represa.sistema.nome}</td>
							<td>&nbsp;${boletim.represa.nome}</td>
							<td>&nbsp;${boletim.volumeDia}</td>
							<td>&nbsp;${boletim.pluviometria}</td>
							<td>&nbsp;${boletim.variacao}</td>
							<td width="12%">
								<a href="${pageContext.request.contextPath}/boletins/edit/${boletim.id}" class="btn btn-warning btn-xs" role="button">Alterar</a>
								<a href="${pageContext.request.contextPath}/boletins/delete/${boletim.id}" class="btn btn-danger btn-xs" role="button">Excluir</a>
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
    <script type="text/javascript"> 
	$(document).ready(function() {
	  	$("#sistemaid").bind("change",function () {
    		var id = $('#sistemaid').val();
    		var idRepresa = $('#represaid').val();
			var url = '/manancial/boletins/represas/' + id;
			options = "<option value=''></option>";
			if (id != '') {
				$.get(url).success(function(data) {
					options = "<option value=''>-- Selecione --</option>";
					$.each(data, function(index, valor) {
						if (idRepresa==valor.id) {
							options += "<option value='" + valor.id +"'' selected>" + valor.nome + "</option>"; 
						} else {
							options += "<option value='" + valor.id +"''>" + valor.nome + "</option>"; 
						}
              	  });
              	  $("#represaid").html(options).fadeIn();
           	 	  $("form").submit();         	 
         	   }); 
			} else {
                $("#represaid").html(options).fadeIn();
			}
		});
	});
	</script>
</body>
</html>