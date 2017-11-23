<%-- 
    Document   : resetNewPassword
    Created on : Nov 21, 2017, 1:56:56 PM
    Author     : 715583
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Set new Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form action="reset?action=setnewpassword" method="post">
            <input type="text" name="newpassword">
            <input type="hidden" name="uuid" value="${uuid}">
            <input type="submit" value="Submit">
        </form>        
        ${message}
    </body>
</html>
