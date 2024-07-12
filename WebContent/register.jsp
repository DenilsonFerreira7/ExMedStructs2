<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro</title>
    <style>
        /* Reset de margens e preenchimentos */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Estilo para o corpo */
        body {
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #91b4b4;
        }

        /* Estilo para o container de registro */
        .register-container {
            background: #dad2c3;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 100%;
            max-width: 450px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        /* Estilo para o container do conteúdo de registro */
        .register-content {
            background: #94acb4;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 100%;
        }

        /* Estilo para o título */
        .register-content h2 {
            margin-bottom: 20px;
            font-size: 28px;
            color: #2c5c63;
        }

        /* Estilo para os campos do formulário */
        .register-content form {
            display: flex;
            flex-direction: column;
        }

        /* Estilo para os inputs */
        .register-content form input[type="text"],
        .register-content form input[type="password"] {
            margin: 10px 0;
            padding: 12px 15px;
            width: 100%;
            border: 1px solid #759291;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
            background-color: #5c7c84;
            color: #dad2c3;
        }

        /* Estilo para o input focado */
        .register-content form input[type="text"]:focus,
        .register-content form input[type="password"]:focus {
            border-color: #447478;
            outline: none;
        }

        /* Estilo para o botão de registro */
        .register-content form input[type="submit"] {
            margin-top: 20px;
            padding: 12px 15px;
            background: #2c5c63;
            color: #dad2c3;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        /* Estilo para o botão de registro ao passar o mouse */
        .register-content form input[type="submit"]:hover {
            background: #527474;
        }

        /* Estilo para mensagens de erro */
        .error {
            color: #446c74;
            margin-top: 10px;
            font-size: 14px;
        }

        /* Estilo para a logo */
        .register-container img.logo {
            max-width: 250px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <img src="img/logo1.png" alt="Logo" class="logo">
        <div class="register-content">
            <h2>Criar Conta</h2>
            <s:form action="register" method="post">
                <s:textfield name="username" label="Usuario"/>
                <s:password name="password" label="Senha"/>
                <s:submit value="Registrar"/>
            </s:form>
            <s:if test="hasActionErrors()">
                <div class="error">
                    <s:actionerror />
                </div>
            </s:if>
        </div>
    </div>
</body>
</html>
