<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Bem-vindo, <s:property value="#session.user.login"/></h2>
    <!-- Conte�do da p�gina -->
    <a href="logout">Logout</a>
</body>
</html>
