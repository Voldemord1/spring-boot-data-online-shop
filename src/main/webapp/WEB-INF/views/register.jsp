<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    ${message}
    <form action="/admin/register" method="POST">
        <p>E-mail:<br><input type="email" name="email" value="${email}"></p>
        <p>Password:<br><input type="password" name="password"></p>
        <p>Repeat password:<br><input type="password" name="confirmPassword"></p>
        <label><input type="radio" name="role" value="ROLE_ADMIN">admin</label>
        <label><input type="radio" name="role" value="ROLE_USER" checked="true">user</label><br/>
        <p><button type="submit">Register</button></p>
    </form>
</div>
</body>
</html>
