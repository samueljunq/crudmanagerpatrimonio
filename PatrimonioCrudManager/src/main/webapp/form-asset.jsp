<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager - Inserir Patrimônio</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${action eq "insert" ? "Adicionar" : "Editar"}
			Patrimônio</h3>

		<form action="${pageContext.request.contextPath}/asset/${action}"
			method="POST">

			<input type="hidden" name="asset_id" value="${assetToEdit.getId()}">

			<div class="row">
				<div class="form-group col-md-4">
					<label for="content">Nome</label> <input type="text"
						class="form-control" id="asset_name" name="asset_name"
						autofocus="autofocus" placeholder="Nome do Patrimonio" required
						oninvalid="this.setCustomValidity('Por favor, informe o Nome do Patrimônio.')"
						oninput="setCustomValidity('')" value="${assetToEdit.getName()}">
				</div>

				<div class="form-group col-md-4">
					<label for="content">Categoria</label> <input type="text"
						class="form-control" id="asset_category" name="asset_category"
						autofocus="autofocus" placeholder="Categoria do Patrimonio"
						required
						oninvalid="this.setCustomValidity('Por favor, informe a categoria do Patrimonio')"
						oninput="setCustomValidity('')"
						value="${assetToEdit.getCategory()}">
				</div>

				<div class="form-group col-md-4">
					<label for="content">Localização</label> <input type="text"
						class="form-control" id="asset_location" name="asset_location"
						autofocus="autofocus" placeholder="localizacao do Patrimonio"
						required
						oninvalid="this.setCustomValidity('Por favor, informe a Localizacao do Patrimonio')"
						oninput="setCustomValidity('')"
						value="${assetToEdit.getLocation()}">
				</div>

				<div class="form-group col-md-4">
					<label for="content">Valor do Patrimõnio</label> <input type="text"
						class="form-control" id="asset_value" name="asset_value"
						autofocus="autofocus" placeholder="Valor do Patrimonio" required
						oninvalid="this.setCustomValidity('Por favor, informe a Localizacao do Patrimonio')"
						oninput="setCustomValidity('')"
						value="${assetToEdit.getAcquisitionValue()}">
				</div>



				<div class="form-group col-md-6">
					<label for="asset_company">Empresa</label> <select
						id="asset_company" class="form-control selectpicker"
						name="asset_company" required
						oninvalid="this.setCustomValidity('Por favor, informe a Empresa.')"
						oninput="setCustomValidity('')">
						<option value="" disabled ${not emptyassetToEdit ? "" : "selected"}>Selecione
							uma empresa</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}"
								${assetToEdit.getCompany().getId() eq company.getId() ? "selected" : ""}>
								${company.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/assets"
						class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${action eq "insert" ? "Criar" : "Editar"}
						Patrimonio</button>
				</div>
			</div>

		</form>
	</div>
</body>
</html>