var center=document.getElementsByClassName("center");
var login=document.getElementById("login");
var register=document.getElementById("register");
    if (window.localStorage.getItem("number")!=null) {
        center[0].style.display="block";
        login.style.display="none";
        register.style.display="none";
    }
