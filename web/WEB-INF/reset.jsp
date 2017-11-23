<%-- 
    Document   : reset
    Created on : Nov 21, 2017, 1:41:25 PM
    Author     : 715583
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <p>Please enter your email address to reset your password.</p>
        <form action="reset?action=passEmail" method="post">
        <p>Email Address:<input type="text" name="passResetEmail"/></p>
        <input type="submit" value="Submit">
        </form>
        ${message}
    </body>
</html>
