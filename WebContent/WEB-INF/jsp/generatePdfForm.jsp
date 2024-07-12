<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gerar PDF de Exame</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: #f5f5f5;
        }
        .container {
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Gerar PDF de Exame</h2>
        <s:form action="generatePdf" method="post" target="_blank">
            <s:hidden name="exameId" value="%{#exame.cd_exame}"/>
            <div class="form-group">
                <s:textfield name="exame.name" label="Nome do Exame" cssClass="form-control"/>
            </div>
            <div class="form-group">
                <s:checkbox name="exame.active" label="Ativo" cssClass="form-check-input"/>
            </div>
            <div class="form-group">
                <s:textarea name="exame.detail" label="Detalhes" cssClass="form-control"/>
            </div>
            <div class="form-group">
                <s:textarea name="exame.detail1" label="Detalhes 1" cssClass="form-control"/>
            </div>
            <div class="form-group">
                <s:submit value="Gerar PDF" cssClass="btn btn-primary"/>
            </div>
        </s:form>
    </div>
</body>
</html>
