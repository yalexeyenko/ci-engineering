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
    <div id="logo">
        <img src="../images/logo1.png" alt="Logotype">
    </div>
    <h4>Sign in or create account to continue</h4>
    <div class="form">
        <img src="../images/guest.png" alt="Guest">
        <ul class="tab-group">
            <li class="tab active"><a href="#login">Sign in</a></li>
            <li class="tab"><a href="#create">Create account</a></li>
        </ul>

        <div class="tab-content">
            <div id="create">
                <form action="${pageContext.request.contextPath}/do/signUp" method="post">
                    <div class="top-row">
                        <div class="field-wrap">
                            <input type="text" name="firstName" placeholder="First name*" required/>
                        </div>
                        <div class="field-wrap">
                            <input type="text" name="lastName" placeholder="Last name*" required/>
                        </div>
                    </div>
                    <div class="field-wrap">
                        <input type="text" name="email" placeholder="Email address*" required/>
                    </div>
                    <div class="field-wrap">
                        <input type="password" name="password" placeholder="Password*" id="password" required/>
                    </div>
                    <div class="field-wrap">
                        <input type="password" name="repeatPassword" placeholder="Repeat password*" id="confirm_password" required/>
                    </div>
                    <button type="submit" class="button-block"/>
                    Create</button>
                </form>
            </div>

            <div id="login">
                <form action="${pageContext.request.contextPath}/do/signIn" method="post">
                    <div class="field-wrap">
                        <input type="text" name="email" placeholder="Enter your email" required/>
                    </div>
                    <div class="field-wrap">
                        <input type="password" name="password" placeholder="Password" required/>
                    </div>
                    <p class="forgot"><a href="#">Forgot Password?</a></p>
                    <button class="button-block"/>
                    Sign in</button>
                </form>
            </div>
        </div>

    </div>
</div>


<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src="../js/welcome.js"></script>

</body>
</html>
