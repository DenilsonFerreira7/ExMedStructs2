<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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

        /* Estilo para o container de login */
        .login-container {
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

        /* Estilo para o container do conteúdo do login */
        .login-content {
            background: #94acb4;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 100%;
        }

        /* Estilo para o título */
        .login-content h2 {
            margin-bottom: 20px;
            font-size: 28px;
            color: #2c5c63;
        }

        /* Estilo para os campos do formulário */
        .login-content form {
            display: flex;
            flex-direction: column;
        }

        /* Estilo para os inputs */
        .login-content form input[type="text"],
        .login-content form input[type="password"] {
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
        .login-content form input[type="text"]:focus,
        .login-content form input[type="password"]:focus {
            border-color: #447478;
            outline: none;
        }

        /* Estilo para o botão de login */
        .login-content form input[type="submit"] {
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

        /* Estilo para o botão de login ao passar o mouse */
        .login-content form input[type="submit"]:hover {
            background: #527474;
        }

        /* Estilo para mensagens de erro */
        .error {
            color: #446c74;
            margin-top: 10px;
            font-size: 14px;
        }

        /* Estilo para a logo */
        .login-container img.logo {
            max-width: 250px;
            margin-bottom: 10px;
        }

        /* Estilo para o link de registro */
        .register-link {
            margin-top: 20px;
            font-size: 16px;
            color: #2c5c63;
            text-decoration: none;
        }

        .register-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <img src="img/logo1.png" alt="Logo" class="logo">
        <div class="login-content">
            <h2>Login</h2>
            <s:form action="login" method="post">
                <s:textfield name="username" label="Usuario"/>
                <s:password name="password" label="Senha"/>
                <s:submit value="ENTRAR"/>
            </s:form>
            <s:if test="hasActionErrors()">
                <div class="error">
                    <s:actionerror />
                </div>
            </s:if>
        </div>
        <a href="register.jsp" class="register-link">Registre-se</a>
    </div>
</body>
</html>
