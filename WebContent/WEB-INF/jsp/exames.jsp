<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Gerenciar Exames</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>
/* Estilos CSS para a página */
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
    margin-left: 360px;
    margin-top: 40px;
    width: calc(60% - 260px);
}
.table-wrapper {
    background: #fff;
    padding: 20px;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0,0,0,.05);
    margin-bottom: 30px;
    position: relative;
    width: 160%;
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
/* Custom checkbox */
.custom-checkbox {
    position: relative;
}
.custom-checkbox input[type="checkbox"] {    
    opacity: 0;
    position: absolute;
    margin: 5px 0 0 3px;
    z-index: 9;
}
.custom-checkbox label:before{
    width: 18px;
    height: 18px;
}
.custom-checkbox label:before {
    content: '';
    margin-right: 10px;
    display: inline-block;
    vertical-align: text-top;
    background: white;
    border: 1px solid #bbb;
    border-radius: 2px;
    box-sizing: border-box;
    z-index: 2;
}
.custom-checkbox input[type="checkbox"]:checked + label:after {
    content: '';
    position: absolute;
    left: 6px;
    top: 3px;
    width: 6px;
    height: 11px;
    border: solid #000;
    border-width: 0 3px 3px 0;
    transform: inherit;
    z-index: 3;
    transform: rotateZ(45deg);
}
.custom-checkbox input[type="checkbox"]:checked + label:before {
    border-color: #03A9F4;
    background: #03A9F4;
}
.custom-checkbox input[type="checkbox"]:checked + label:after {
    border-color: #fff;
}
.custom-checkbox input[type="checkbox"]:disabled + label:before {
    color: #b8b8b8;
    cursor: auto;
    box-shadow: none;
    background: #ddd;
}
/* Modal styles */
.modal .modal-dialog {
    max-width: 400px;
}
.modal .modal-header, .modal .modal-body, .modal .modal-footer {
    padding: 20px 30px;
}
.modal .modal-content {
    border-radius: 3px;
}
.modal .modal-footer {
    background: #ecf0f1;
    border-radius: 0 0 3px 3px;
}
.modal .modal-title {
    display: inline-block;
}
.modal .form-control {
    border-radius: 2px;
    box-shadow: none;
    border-color: #dddddd;
}
.modal textarea.form-control {
    resize: vertical;
}
.modal .btn {
    border-radius: 2px;
    min-width: 100px;
}    
.modal form label {
    font-weight: normal;
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

    // Bloquear/Desbloquear campo ID do Funcionário
    $('#addExameModal input[name="exame.active"], #editExameModal input[name="exame.active"]').change(function() {
        var isActive = $(this).is(':checked');
        var funcionarioIdField = $(this).closest('form').find('input[name="funcionarioId"]');
        funcionarioIdField.prop('disabled', !isActive);
        if (!isActive) {
            funcionarioIdField.val('');
        }
    }).change();
});

// Função para preencher os dados no modal de edição
function editExame(id, name, active, detail, detail1, funcionarioId) {
    $('#editExameModal input[name="exame.id"]').val(id);
    $('#editExameModal input[name="exame.name"]').val(name);
    $('#editExameModal input[name="exame.active"]').prop('checked', active == 'true').change();
    $('#editExameModal textarea[name="exame.detail"]').val(detail);
    $('#editExameModal textarea[name="exame.detail1"]').val(detail1);
    $('#editExameModal input[name="funcionarioId"]').val(funcionarioId);
}

// Função para passar o ID para o modal de deletar
function deleteExame(id) {
    $('#deleteExameModal input[name="id"]').val(id);
}

// Função para passar o ID do exame para o modal de associar funcionário
function associateFuncionario(id) {
    $('#associateExameId').val(id);
}
</script>
</head>
<body>
<!-- Sidebar com o menu -->
<div class="sidebar">
    <h2>Menu</h2>
    <a href="${pageContext.request.contextPath}/listarFuncionarios">Gerenciar Funcionários</a>
    <a href="${pageContext.request.contextPath}/listarExames">Gerenciar Exames</a>
    <a href="${pageContext.request.contextPath}/listarExamesRealizados">Exames Realizados</a>
    <%@ include file="header.jsp" %>
</div>
<!-- Container principal para o gerenciamento de exames -->
<div class="container-xl">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Gerenciar Exames</h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addExameModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Adicionar Novo Exame</span></a>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <span class="badge badge-success">Exames Ativos: <s:property value="totalExamesAtivos"/></span>
                    <span class="badge badge-danger">Exames Inativos: <s:property value="totalExamesInativos"/></span>
                </div>
            </div>
        </div>

        <!-- Formulário de Filtro -->
        <form action="listarExames" method="get" class="form-inline">
            <div class="form-group mx-sm-3 mb-2">
                <label for="searchId" class="sr-only">ID do Exame</label>
                <input type="text" class="form-control" id="searchId" name="searchId" placeholder="ID do Exame" value="<s:property value='searchId'/>">
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <label for="searchActive" class="sr-only">Ativo</label>
                <select class="form-control" id="searchActive" name="searchActive">
                    <option value="" <s:if test="searchActive == null">selected</s:if>>Todos</option>
                    <option value="true" <s:if test="searchActive == 'true'">selected</s:if>>Ativos</option>
                    <option value="false" <s:if test="searchActive == 'false'">selected</s:if>>Inativos</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary mb-2">Pesquisar</button>
        </form>

        <!-- Tabela de Exames -->
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
                <th>Ativo</th>
                <th>Detalhes</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="exames">
                <tr>
                    <td>
                        <span class="custom-checkbox">
                            <input type="checkbox" id="checkbox_<s:property value='id'/>" name="options[]" value="<s:property value='id'/>">
                            <label for="checkbox_<s:property value='id'/>"></label>
                        </span>
                    </td>
                    <td><s:property value="id"/></td>
                    <td>
                        <s:if test="name.length() > 16">
                            <s:property value="name.substring(0, 16)"/>...
                        </s:if>
                        <s:else>
                            <s:property value="name"/>
                        </s:else>
                    </td>
                    <td>
                        <s:if test="active">Ativo</s:if>
                        <s:else>Inativo</s:else>
                    </td>
                    <td>
                        <s:if test="detail.length() > 15">
                            <s:property value="detail.substring(0, 15)"/>...
                        </s:if>
                        <s:else>
                            <s:property value="detail"/>
                        </s:else>
                    </td>
                    <td>
                        <a href="#editExameModal" class="edit" data-toggle="modal" onclick="editExame('<s:property value="id"/>', '<s:property value="name"/>', '<s:property value="active"/>', '<s:property value="detail"/>', '<s:property value="detail1"/>', '<s:property value="funcionarioId"/>')"><i class="material-icons" data-toggle="tooltip" title="Editar" style="color: #f9e41a;">&#xE254;</i></a>
                        <a href="#deleteExameModal" class="delete" data-toggle="modal" onclick="deleteExame('<s:property value="id"/>')"><i class="material-icons" data-toggle="tooltip" title="Deletar" style="color: #ff1448;">&#xE872;</i></a>
                        <a href="generatePdf.action?exameId=<s:property value="id"/>" class="print" target="_blank"><i class="material-icons" data-toggle="tooltip" title="Imprimir" style="color: #4caf50;">&#xE8AD;</i></a>
                        <s:if test="active">
                            <a href="#associateFuncionarioModal" class="associate" data-toggle="modal" onclick="associateFuncionario('<s:property value="id"/>')"><i class="material-icons" data-toggle="tooltip" title="Associar Funcionário" style="color: #007bff;">&#xE7EF;</i></a>
                        </s:if>
                    </td>
                </tr>
            </s:iterator>
        </tbody>
     </table>
  </div>
        <div class="clearfix">
            <div class="hint-text">Mostrando <b><s:property value="exames.size()"/></b> de <b><s:property value="totalExames"/></b> entradas</div>
            <ul class="pagination">
                <li class="page-item <s:if test='page == 1'>disabled</s:if>">
                    <a class="page-link" href="?page=1&searchId=<s:property value='searchId'/>&searchActive=<s:property value='searchActive'/>" aria-label="First">
                        <span aria-hidden="true">««</span>
                    </a>
                </li>
                <li class="page-item <s:if test='page == 1'>disabled</s:if>">
                    <a class="page-link" href="?page=<s:property value='page - 1'/>&searchId=<s:property value='searchId'/>&searchActive=<s:property value='searchActive'/>" aria-label="Previous">
                        <span aria-hidden="true">«</span>
                    </a>
                </li>
                <s:iterator value="totalPages" status="stat">
                    <li class="page-item <s:if test='page == stat.index + 1'>active</s:if>">
                        <a class="page-link" href="?page=<s:property value='stat.index + 1'/>&searchId=<s:property value='searchId'/>&searchActive=<s:property value='searchActive'/>"><s:property value="stat.index + 1"/></a>
                    </li>
                </s:iterator>
                <li class="page-item <s:if test='page == totalPages'>disabled</s:if>">
                    <a class="page-link" href="?page=<s:property value='page + 1'/>&searchId=<s:property value='searchId'/>&searchActive=<s:property value='searchActive'/>" aria-label="Next">
                        <span aria-hidden="true">»</span>
                    </a>
                </li>
                <li class="page-item <s:if test='page == totalPages'>disabled</s:if>">
                    <a class="page-link" href="?page=<s:property value='totalPages'/>&searchId=<s:property value='searchId'/>&searchActive=<s:property value='searchActive'/>" aria-label="Last">
                        <span aria-hidden="true">»»</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>        
</div>

<!-- Adicionar Modal HTML -->
<div id="addExameModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="adicionarExame" method="post">
                <div class="modal-header">                       
                    <h4 class="modal-title">Adicionar Exame</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" class="form-control" name="exame.name" required>
                    </div>
                    <div class="form-group">
                        <label>Ativo</label>
                        <input type="checkbox" class="form-control" name="exame.active" value="true">
                    </div>
                    <div class="form-group">
                        <label>Detalhes</label>
                        <textarea class="form-control" name="exame.detail" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Detalhes 1</label>
                        <textarea class="form-control" name="exame.detail1" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>ID do Funcionário (opcional)</label>
                        <input type="text" class="form-control" name="funcionarioId" disabled>
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
<div id="editExameModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="atualizarExame" method="post">
                <div class="modal-header">                       
                    <h4 class="modal-title">Editar Exame</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" class="form-control" name="exame.name" required>
                    </div>
                    <div class="form-group">
                        <label>Ativo</label>
                        <input type="checkbox" class="form-control" name="exame.active" value="true">
                    </div>
                    <div class="form-group">
                        <label>Detalhes</label>
                        <textarea class="form-control" name="exame.detail" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Detalhes 1</label>
                        <textarea class="form-control" name="exame.detail1" required></textarea>
                    </div>
                    <input type="hidden" name="exame.id">
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
<div id="deleteExameModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="deletarExame" method="post">
                <div class="modal-header">                       
                    <h4 class="modal-title">Deletar Exame</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">                    
                    <p>Você tem certeza que deseja deletar este registro?</p>
                    <p class="text-warning"><small>Esta ação não pode ser desfeita.</small></p>
                    <input type="hidden" name="id" id="deleteExameId">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
                    <input type="submit" class="btn btn-danger" value="Deletar">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Associar Funcionário Modal HTML -->
<div id="associateFuncionarioModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="associarFuncionario" method="post">
                <div class="modal-header">                       
                    <h4 class="modal-title">Associar Funcionário</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">                    
                    <div class="form-group">
                        <label>ID do Funcionário</label>
                        <input type="text" class="form-control" name="funcionarioId" required>
                    </div>
                    <input type="hidden" name="id" id="associateExameId">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancelar">
                    <input type="submit" class="btn btn-primary" value="Associar">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
