<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Gerenciar Exames Realizados</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
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
    margin-left: 360px;
    margin-top: 40px;
    width: calc(60% - 260px);
}

.table-wrapper {
    background: #fff;
    padding: 20px;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    margin-bottom: 30px;
    width: 150%;
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
$(document).ready(function() {
    // Ativar tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Selecionar/Deselecionar checkboxes
    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function() {
        if (this.checked) {
            checkbox.each(function() {
                this.checked = true;
            });
        } else {
            checkbox.each(function() {
                this.checked = false;
            });
        }
    });
    checkbox.click(function() {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });

    // Configurar o calendário com seleção de intervalo
    $('input[name="daterange"]').daterangepicker({
        locale: {
            format: 'YYYY-MM-DD'
        },
        autoUpdateInput: false
    });

    $('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
        $('#startDate').val(picker.startDate.format('YYYY-MM-DD'));
        $('#endDate').val(picker.endDate.format('YYYY-MM-DD'));
    });

    $('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
        $('#startDate').val('');
        $('#endDate').val('');
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
</script>
</head>
<body>
    <div class="sidebar">
        <h2>Menu</h2>
        <a href="${pageContext.request.contextPath}/listarFuncionarios">Gerenciar Funcionários</a> 
        <a href="${pageContext.request.contextPath}/listarExames">Gerenciar Exames</a> 
        <a href="${pageContext.request.contextPath}/listarExamesRealizados">Exames Realizados</a>
        <%@ include file="header.jsp"%>
    </div>
    <div class="container-xl">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2>Gerenciar Exames Realizados</h2>
                    </div>
                    <div class="col-sm-6">
                        <button type="button" class="btn btn-primary float-right"
                            onclick="window.open('gerarExamesRealizadosPDF?searchFuncionarioId=${searchFuncionarioId}&searchFuncionarioNome=${searchFuncionarioNome}&searchExameId=${searchExameId}&searchDataRealizacao=${searchDataRealizacao}', '_blank')">Imprimir</button>
                    </div>
                </div>
            </div>
            <!-- Formulário de Filtro -->
            <form action="listarExamesRealizadosPorIntervalo" method="get"
                class="form-inline">
                <div class="form-group mx-sm-3 mb-2">
                    <label for="searchFuncionarioId" class="sr-only">ID do Funcionário</label> 
                    <input type="text" class="form-control" id="searchFuncionarioId" name="searchFuncionarioId" placeholder="ID do Funcionário" value="<s:property value='searchFuncionarioId'/>">
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <label for="searchExameId" class="sr-only">ID do Exame</label> 
                    <input type="text" class="form-control" id="searchExameId" name="searchExameId" placeholder="ID do Exame" value="<s:property value='searchExameId'/>">
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <label for="daterange" class="sr-only">Período</label> 
                    <input type="text" class="form-control" id="daterange" name="daterange" placeholder="Período">
                    <input type="hidden" id="startDate" name="startDate" value="<s:property value='startDate'/>">
                    <input type="hidden" id="endDate" name="endDate" value="<s:property value='endDate'/>">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Pesquisar</button>
            </form>

            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID Funcionário</th>
                            <th>Nome Funcionário</th>
                            <th>ID Exame</th>
                            <th>Data Realização</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="examesRealizados">
                            <tr>
                                <td><s:property value="funcionarioId" /></td>
                                <td><s:property value="funcionarioNome" /></td>
                                <td><s:property value="exameId" /></td>
                                <td><s:property value="dataRealizacao" /></td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </div>
            <div class="clearfix">
                <div class="hint-text">
                    Mostrando <b>10</b> de <b><s:property value="examesRealizados.size()" /></b> entradas
                </div>
                <nav aria-label="Navegação de página exemplo">
                    <ul class="pagination" id="numbers">
                        <li class="page-item"><a class="page-link" href="#" aria-label="Anterior"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">Anterior</span>
                        </a></li>
                        <!-- Os itens da página serão adicionados dinamicamente aqui -->
                        <li class="page-item"><a class="page-link" href="#" aria-label="Próximo"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Próximo</span>
                        </a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
