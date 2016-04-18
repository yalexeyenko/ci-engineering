<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../css/welcome.css">

</head>

<body>
<div class="wrapper">
    <div class="header">
        <h3>
            Welcome, ${user.firstName}!
        </h3>
        <a href="${pageContext.request.contextPath}/do/signOut">Sign out</a>
    </div>

    <div class="content">

    </div>
    <div class="footer">

    </div>
</div>

</body>
</html>
