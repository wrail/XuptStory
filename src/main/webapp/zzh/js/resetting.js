var password1 = document.getElementById("password1");
var password2 = document.getElementById("password2");
var btn = document.getElementById("btn");
btn.onclick = function () {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) {
                var text = xhr.responseText;
                var resultText = JSON.parse(text);
                if (resultText.tip === "success") {
                    alert("修改密码成功！");
                    window.location.href="../html/login.html";
                } else if (resultText.tip==="fail") {
                    alert("两次输入密码不一致,请重新输入!");
                    window.location.href="../html/resetting.html";
                }
            }
        }
    }
    xhr.open("POST", "http://localhost:8080/index.jsp/InputPassword", true);
    var data = "&password1=" + password1.value
        + "&password2=" + password2.value;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}