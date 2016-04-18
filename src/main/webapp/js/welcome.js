var ck_name = /^[A-Za-z0-9 ]{3,20}$/;
var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
var ck_password =  /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;

function validate_form(sign_in){
    var firstName = sign_in.firstName.value;
    var lastName = sign_in.lastName.value;
    var email = sign_in.email.value;
    var password = sign_in.password.value;
    var errors = [];

    if (!ck_name.test(firstName)) {
        errors[errors.length] = "You valid first name.";
    }
    if (!ck_name.test(lastName)) {
        errors[errors.length] = "You valid last name.";
    }
    if (!ck_email.test(email)) {
        errors[errors.length] = "You must enter a valid email address.";
    }
    if (!ck_password.test(password)) {
        errors[errors.length] = "You must enter a valid password ";
    }
    if (errors.length > 0) {
        reportErrors(errors);
        return false;
    }
    return true;
}
function validate_form(sign_up){
    var firstName = sign_up.firstName.value;
    var lastName = sign_up.lastName.value;
    var email = sign_up.email.value;
    var password = sign_up.password.value;
    var errors = [];

    if (!ck_name.test(firstName)) {
        errors[errors.length] = "You valid first name.";
    }
    if (!ck_name.test(lastName)) {
        errors[errors.length] = "You valid last name.";
    }
    if (!ck_email.test(email)) {
        errors[errors.length] = "You must enter a valid email address.";
    }
    if (!ck_password.test(password)) {
        errors[errors.length] = "You must enter a valid password ";
    }
    if (errors.length > 0) {
        reportErrors(errors);
        return false;
    }
    return true;
}
function reportErrors(errors){
    var msg = "Please enter valid data...\n";
    for (var i = 0; i<errors.length; i++) {
        var numError = i + 1;
        msg += "\n" + numError + ". " + errors[i];
    }
    alert(msg);
}