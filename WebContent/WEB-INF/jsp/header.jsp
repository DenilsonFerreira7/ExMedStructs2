<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>ExameMed</title>
    <style>
        .header {
            padding: 15px;
        }
        .status-dot {
            height: 10px;
            width: 10px;
            border-radius: 50%;
            display: inline-block;
            margin-left: 5px;
        }
        .status-online {
            background-color: #7FFF00;
        }
        .status-offline {
            background-color: red;
        }
        .status-away {
            background-color: yellow;
        }
    </style>
</head>
<body>
    <div class="header">
        <s:if test="#session.user != null">
            <p>
                LOGADO: <s:property value="#session.user" />
                <span class="status-dot status-online"></span> <!-- Altere a classe aqui para mudar o status visual -->
            </p>
        </s:if>
    </div>
</body>
</html>
