<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome!</title>
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../css/welcome.css">

</head>

<body>
<div class="wrapper">

    <header class="header">
        <div id="sign_in_form">
            <form action="${pageContext.request.contextPath}/do/signIn" method="post" name="sign_in">
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="email_in" value="${param.email_in}" placeholder="Enter your email" required/>
                </div>
                <div class="field-wrap">
                    <span>Password: </span>
                    <input type="password" name="password_in" value="${param.password_in}" placeholder="Password" required/>
                    <div class="signError">${signInError}</div>
                </div>
                <%--<p class="forgot"><a href="#">Forgot Password?</a></p>--%>
                <button class="in_up"/>Sign in</button>
            </form>
        </div>
    </header><!-- .header-->

    <main class="content">
        <div id="sign_up_form">
            <h3>Sign in or create account to continue</h3>
            <form action="${pageContext.request.contextPath}/do/signUp" method="post" name="sign_up" onSubmit="return validate_form(this);">
                <div class="top-row">
                    <div class="field-wrap">
                        <input type="text" name="firstName" value="${param.firstName}" placeholder="First name*" required/>
                        <span class="violation">${firstNameViolation}</span>
                    </div>
                    <div class="field-wrap">
                        <input type="text" name="lastName"  value="${param.lastName}" placeholder="Last name*" required/>
                        <span class="violation">${lastNameViolation}</span>
                    </div>
                </div>
                <div class="field-wrap">
                    <input type="text" name="email"  value="${param.email}" placeholder="Email address*" required/>
                    <span class="violation">${emailViolation}</span>
                </div>
                <div class="field-wrap">
                    <input type="password" name="password" placeholder="Password*" id="password" required/>
                    <span class="violation">${passwordViolation}</span>
                </div>
                <div class="field-wrap">
                    <input type="password" name="repeatPassword" placeholder="Repeat password*" id="confirm_password" required/>
                    <span class="violation">${passwordViolation}</span>
                    <span class="violation">${signUpError}</span>
                </div>
                <button class="create" type="submit" class="button-block"/>Create account</button>
            </form>
        </div>
    </main><!-- .content -->

</div><!-- .wrapper -->

<footer class="footer">
</footer><!-- .footer -->

</body>
</html>