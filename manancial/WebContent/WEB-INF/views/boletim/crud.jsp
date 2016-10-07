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
<title>Boletins</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
	<div class="container">
		<c:import url="..\cabecalho.jsp" />
		<form:form commandName="boletim" class="form-horizontal" action="${path}/boletins/crudBoletim" method="post">
		<form:input id="id" path="id" type="hidden"/>
		
		<div class="panel panel-info">
   			<div class="panel-heading">
   				<div class="clearfix">
   					<c:if test="${not empty boletim.id}">
	   					<strong>Alterar Boletim</strong>
					</c:if>
   					<c:if test="${empty boletim.id}">
	   					<strong>Novo Boletim</strong>
					</c:if>  					
   				</div>
   			</div>
   			<br>
  			<div class="panel-body">

				<div class="form-group fs-required">
					<label for="sistema_id" class="col-sm-2 control-label">Sistema</label>
					<div class="col-sm-3">
						<select id="sistema_id" class="form-control input-sm pokemon">
							<option value=""></option>
							<c:forEach var="sistema" items="${sistemas}">
								<option value="${sistema.id}"
									${sistema.id==boletim.represa.sistema.id ? 'selected' : ''}>${sistema.nome}							
								</option>							
							</c:forEach>
						</select>
					</div>
				</div>


				<div class="form-group fs-required">
					<label for="represa" class="col-sm-2 control-label">Represa</label>
					<div class="col-sm-3">
						<form:select path="represa" class="form-control input-sm pokemon">
							<option value=""></option>
							<c:forEach var="represa" items="${represas}">
								<option value="${represa.id}"
									${represa.id==boletim.represa.id ? 'selected' : ''}>${represa.nome}
								</option>
							</c:forEach>
						</form:select>
						<form:errors path="represa" cssClass="error" />
					</div>
				</div>

				<div class="form-group fs-required">
					<label for="dataBoletim" class="col-sm-2 control-label">Data do boletim</label> 
					<div class="col-sm-3"> 
						<form:input path="dataBoletim" type="date" class="form-control input-sm"/>
						<form:errors path="dataBoletim" cssClass="error"/>
					</div>
				</div>
																
				<div class="form-group fs-required">
					<label for="volumeDia" class="col-sm-2 control-label">Volume do Dia</label> 
					<div class="col-sm-3"> 
						<form:input path="volumeDia" type="number" class="form-control input-sm" step="0.01"/>
						<form:errors path="volumeDia" cssClass="error"/>
					</div>
				</div>

				<div class="form-group fs-required">
					<label for="pluviometria" class="col-sm-2 control-label">Pluviometria do dia anterior</label> 
					<div class="col-sm-3"> 
						<form:input path="pluviometria" type="number" class="form-control input-sm" step="0.01"/>
						<form:errors path="pluviometria" cssClass="error"/>
					</div>
				</div>
									
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Salvar</button>
						<a href="${pageContext.request.contextPath}/boletins" class="btn btn-default">Cancelar</a>
   						<c:if test="${not empty boletim.id}">
 							<button type="submit" class="btn btn-primary clone">Incluir Novo</button>
						</c:if>  					
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
	<script type="text/javascript"> 
		$(document).ready(function() {
		  	$("#sistema_id").bind("change",function () {
	    		var id = $('#sistema_id').val();
				var url = '/manancial/boletins/represas/' + id;
				options = "<option value=''></option>";
				if (id != '') {
					$.get(url).success(function(data) {
						options = "";
						$.each(data, function(index, valor) {
	          	  		options += "<option value='" + valor.id +"''>" + valor.nome + "</option>";
	            	    });
	              	  $("#represa").html(options).fadeIn();
					}); 
				} else {
	                $("#represa").html(options).fadeIn();
				}
			});
		  	$(".clone").bind("click",function () {
		  		$('#id').val('');
			});
		});
	</script>
</body>
</html>