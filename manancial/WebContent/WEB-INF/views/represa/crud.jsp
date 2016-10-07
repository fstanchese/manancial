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
<title>Represas</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<form:form commandName="represa" class="form-horizontal" action="${path}/represas/crudRepresa" method="post">
		<form:input path="id" type="hidden" value="${represa.id}"/>
		
		<div class="panel panel-info">
   			<div class="panel-heading">
   				<div class="clearfix">
   					<c:if test="${not empty represa.id}">
	   					<strong>Alterar Represa</strong>
					</c:if>
   					<c:if test="${empty represa.id}">
	   					<strong>Nova Represa</strong>
					</c:if>  					
   				</div>
   			</div>
   			<br>
  			<div class="panel-body">
				<div class="form-group fs-required">
					<label for="nome" class="col-sm-2 control-label">Nome</label> 
					<div class="col-sm-8"> 
						<form:input path="nome" type="text" class="form-control input-sm"  maxlength="100" autofocus="autofocus"/>
 						<form:errors path="nome" cssClass="error"/>
					</div>
				</div>
				
				<div class="form-group fs-required">
					<label for="sistema" class="col-sm-2 control-label">Sistema</label>
					<div class="col-sm-3">
						<form:select path="sistema" tabindex="2" class="form-control input-sm pokemon">
							<option value=""></option>
							<c:forEach var="sistema" items="${sistemas}">
								<option value="${sistema.id}"
									${sistema.id==represa.sistema.id ? 'selected' : ''}>${sistema.nome}
								</option>
							</c:forEach>
						</form:select>
						<form:errors path="sistema" cssClass="error" />
					</div>
				</div>
	
				<div class="form-group fs-required">
					<label for="volumeUtil" class="col-sm-2 control-label">Volume Útil</label> 
					<div class="col-sm-3"> 
						<form:input path="volumeUtil" type="number" class="form-control input-sm" step="0.001"/>
						<form:errors path="volumeUtil" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label for="volumeReserva" class="col-sm-2 control-label">Volume Reserva</label> 
					<div class="col-sm-3"> 
						<form:input path="volumeReserva" type="number" class="form-control input-sm"  step="0.001"/>
					</div>
				</div>
						
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Salvar</button>
						<a href="${pageContext.request.contextPath}/represas" class="btn btn-default">Cancelar</a>
					</div>
				</div> 	
			</div> 	
   			<div class="panel-footing">
   				<div class="clearfix">
   					<h1 class="panel-title"></h1>
   				</div>
   			</div>			
		</div>	
		</form:form>
		<br>
	</div>
	<script src="${path}/resources/js/jquery.min.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/bootstrap.min.js" 	type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.js" 		type="text/javascript"></script>
	<script src="${path}/resources/js/zebra.dialog.src.js" 	type="text/javascript"></script>	
</body>
</html>