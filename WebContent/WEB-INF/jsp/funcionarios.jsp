<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Gerenciar Funcionários</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>
body {
    font-family: 'Roboto', sans-serif;
    background: #f5f5f5;
    display: flex;
}
.sidebar {
    width: 250px;
    background: #343a40;
    color: #fff;
    height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    padding: 15px;
}
.sidebar a {
    color: #fff;
    text-decoration: none;
    display: block;
    padding: 10px 0;
}
.sidebar a:hover {
    background: #495057;
    text-decoration: none;
}
.container-xl {
    margin-left: 260px;
    margin-top: 50px;
    width: calc(60% - 260px);
}
.table-wrapper {
    background: #fff;
    padding: 20px;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0,0,0,.05);
    margin-bottom: 30px;
    width: 130%; 
    
}
.table-title {
    background: #435d7d;
    color: #fff;
    padding: 16px 25px;
    border-radius: 3px 3px 0 0;
    margin-bottom: 15px;
}
.table-title h2 {
    margin: 5px 0 0;
    font-size: 24px;
}
.table-title .btn {
    color: #fff;
    float: right;
    font-size: 13px;
    min-width: 50px;
    border-radius: 2px;
    margin-left: 10px;
}
.table-title .btn i {
    margin-right: 5px;
}
.table-responsive {
    margin-top: 30px;
}
.table {
    margin-bottom: 0;
}
.pagination {
    float: right;
    margin: 0;
}
.pagination li a {
    border: none;
    font-size: 13px;
    min-width: 30px;
    min-height: 30px;
    color: #999;
    margin: 0 2px;
    line-height: 30px;
    border-radius: 2px !important;
    text-align: center;
    padding: 0 6px;
}
.pagination li a:hover {
    color: #666;
}    
.pagination li.active a, .pagination li.active a.page-link {
    background: #03A9F4;
}
.pagination li.active a:hover {        
    background: #0397d6;
}
.pagination li.disabled i {
    color: #ccc;
}
.pagination li i {
    font-size: 16px;
    padding-top: 6px
}
.hint-text {
    float: left;
    margin-top: 10px;
    font-size: 13px;
}
</style>
<script>
$(document).ready(function(){
    // Ativar tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Selecionar/Deselecionar checkboxes
    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function(){
        if(this.checked){
            checkbox.each(function(){
                this.checked = true;                        
            });
        } else{
            checkbox.each(function(){
                this.checked = false;                        
            });
        } 
    });
    checkbox.click(function(){
        if(!this.checked){
            $("#selectAll").prop("checked", false);
        }
    });

    // Paginação
    var rowsPerPage = 10;
    var rows = $('table tbody tr');
    var rowsCount = rows.length;
    var pageCount = Math.ceil(rowsCount / rowsPerPage);
    var pagination = $('#numbers');

    pagination.find('li').not(':first-child, :last-child').remove(); // Remove previous page numbers

    for (var i = 0; i < pageCount; i++) {
        pagination.find('li:last-child').before('<li class="page-item"><a href="#" class="page-link">' + (i + 1) + '</a></li>');
    }
    pagination.find('li:nth-child(2) a').addClass('active');
    displayRows(1);

    pagination.find('a').click(function(e) {
        e.preventDefault();
        var parentLi = $(this).parent();
        
        if (parentLi.hasClass('page-item')) {
            if (parentLi.index() === 0) {
                // Previous button clicked
                var activePage = pagination.find('a.active').text();
                if (activePage > 1) {
                    displayRows(parseInt(activePage) - 1);
                }
            } else if (parentLi.index() === pagination.find('li').length - 1) {
                // Next button clicked
                var activePage = pagination.find('a.active').text();
                if (activePage < pageCount) {
                    displayRows(parseInt(activePage) + 1);
                }
            } else {
                // Number button clicked
                var index = $(this).text();
                displayRows(index);
            }
        }
    });

    function displayRows(index) {
        var start = (index - 1) * rowsPerPage;
        var end = start + rowsPerPage;
        rows.hide();
        rows.slice(start, end).show();

        pagination.find('a').removeClass('active');
        pagination.find('li:nth-child(' + (parseInt(index) + 1) + ') a').addClass('active');
    }
});

function editEmployee(id, name) {
    $('#editEmployeeModal input[name="funcionario.id"]').val(id);
    $('#editEmployeeModal input[name="funcionario.name"]').val(name);
}

function deleteEmployee(id) {
    $('#deleteEmployeeModal input[name="id"]').val(id);
}
</script>
</head>
<body>
<div class="sidebar">
    <h2>Menu</h2>
    <a href="${pageContext.request.contextPath}/listarFuncionarios">Gerenciar Funcionários</a>
    <a href="${pageContext.request.contextPath}/listarExames">Gerenciar Exames</a>
    <a href="${pageContext.request.contextPath}/listarExamesRealizados">Exames Realizados</a>
    <%@ include file="header.jsp" %>
</div>
<div class="container-xl">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Gerenciar Funcionários</h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Adicionar Novo Funcionário</span></a>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>
                            <span class="custom-checkbox">
                                <input type="checkbox" id="selectAll">
                                <label for="selectAll"></label>
                            </span>
                        </th>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="funcionarios">
                        <tr>
                            <td>
                                <span class="custom-checkbox">
                                    <input type="checkbox" id="checkbox_<s:property value='id'/>" name="options[]" value="<s:property value='id'/>">
                                    <label for="checkbox_<s:property value='id'/>"></label>
                                </span>
                            </td>
                            <td><s:property value="id"/></td>
                            <td><s:property value="name"/></td>
                            <td>
                                <a href="#editEmployeeModal" class="edit" data-toggle="modal" onclick="editEmployee('<s:property value='id'/>', '<s:property value='name'/>')"><i class="material-icons" data-toggle="tooltip" title="Editar" style="color: #f9e41a;">&#xE254;</i></a>
                                <a href="#deleteEmployeeModal" class="delete" data-toggle="modal" onclick="deleteEmployee('<s:property value='id'/>')"><i class="material-icons" data-toggle="tooltip" title="Deletar" style="color: #ff1448;">&#xE872;</i></a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </div>
        <div class="clearfix">
            <div class="hint-text">Mostrando <b>10</b> de <b><s:property value="funcionarios.size()"/></b> entradas</div>
            <nav aria-label="Navegação de página exemplo">
                <ul class="pagination" id="numbers">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Anterior">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">Anterior</span>
                        </a>
                    </li>
                    <!-- Os itens da página serão adicionados dinamicamente aqui -->
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Próximo">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">Próximo</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
<!-- Adicionar Modal HTML -->
<div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="adicionarFuncionario" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Adicionar Funcionário</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Código</label>
                        <input type="text" class="form-control" name="funcionario.id" required>
                    </div>
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" class="form-control" name="funcionario.name" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
                    <input type="submit" class="btn btn-success" value="Adicionar">
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Editar Modal HTML -->
<div id="editEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="atualizarFuncionario" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Editar Funcionário</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" class="form-control" name="funcionario.name" required>
                    </div>
                    <input type="hidden" name="funcionario.id" id="editEmployeeId">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
                    <input type="submit" class="btn btn-info" value="Salvar">
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Deletar Modal HTML -->
<div id="deleteEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="deletarFuncionario" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Deletar Funcionário</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Você tem certeza que deseja deletar este registro?</p>
                    <p class="text-warning"><small>Esta ação não pode ser desfeita.</small></p>
                    <input type="hidden" name="id" id="deleteEmployeeId">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
                    <input type="submit" class="btn btn-danger" value="Deletar">
                </div>
            </form>
        </div>
    </div>
</div>
<script>
function editEmployee(id, name) {
    $('#editEmployeeModal input[name="funcionario.id"]').val(id);
    $('#editEmployeeModal input[name="funcionario.name"]').val(name);
}

function deleteEmployee(id) {
    $('#deleteEmployeeModal input[name="id"]').val(id);
}
</script>
</body>
</html>
