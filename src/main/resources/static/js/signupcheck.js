let check = false;
let nameCheck = false;
let surnameCheck = false;
let usernameCheck = false;
let passCheck = false;
let passconfCheck = false;
let registerForm = document.getElementById("registerForm");

function checkName(){
    let input = document.getElementById("floatingNameInput");
    let name = input.value;
    let pattern = /^[a-zA-Z]+$/;
    let feedback = document.getElementById("invalid-name-feedback");
    showError(pattern, name, feedback, input);
    nameCheck = check;
    showSubmit();
}

function checkSurname(){
    let input = document.getElementById("floatingSurnameInput");
    let surname = input.value;
    let pattern = /^[a-zA-Z]+$/;
    let feedback = document.getElementById("invalid-surn-feedback");
    showError(pattern, surname, feedback, input);
    surnameCheck = check;
    showSubmit();
}

function checkUsername(){
    let input = document.getElementById("floatingUserRegInput");
    let username = input.value;
    let pattern = /^[a-zA-Z0-9_]+$/;
    let feedback = document.getElementById("invalid-user-feedback");
    showError(pattern, username, feedback, input);
    usernameCheck = check;
    showSubmit();
}

function checkPass(){
    let input = document.getElementById("floatingRegPassword");
    let pass = input.value;
    let pattern = /^[a-zA-Z0-9_]{8,15}$/;
    let feedback = document.getElementById("invalid-pass-feedback");
    showError(pattern, pass, feedback, input);
    passCheck = check;
    checkPassConf();
}

function checkPassConf(){
    let input = document.getElementById("floatingConfRegPassword");
    let pass = input.value;
    let firstPass = document.getElementById("floatingRegPassword").value;
    let feedback = document.getElementById("invalid-conf-feedback");
    if(pass !== firstPass){
        passconfCheck = false;
        feedback.classList.add("d-block");
        input.classList.remove("is-valid");
        input.classList.add("is-invalid");
    }else{
        passconfCheck = true;
        feedback.classList.remove("d-block");
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
    }
    showSubmit();
}

function showError(pattern, data, feedback, input){
    if(!pattern.test(data) || data === ''){
        check = false;
        feedback.classList.add("d-block");
        input.classList.remove("is-valid");
        input.classList.add("is-invalid");
    }else{
        check = true;
        feedback.classList.remove("d-block");
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
    }
}

function showSubmit(){
    let submitButton = document.getElementById("registerSubmit");
    let checkedInputs = nameCheck && surnameCheck && usernameCheck && passCheck && passconfCheck;
    console.log(nameCheck +"|"+ surnameCheck +"|"+ usernameCheck +"|"+ passCheck +"|"+ passconfCheck +"|"+ submitButton);
    if(checkedInputs && submitButton == null){
        let submit = document.createElement("button");
        submit.setAttribute("role", "button");
        submit.classList.add('w-100', 'mb-2', 'btn', 'btn-lg', 'rounded-4', 'btn-primary');
        submit.type = "submit";
        submit.innerHTML = "Registrati";
        submit.id = "registerSubmit";
        registerForm.append(submit);
    }else if(!checkedInputs && submitButton != null){
        registerForm.removeChild(submitButton);
    }
}