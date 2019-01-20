var number = document.getElementById("number"); //手机号码
var code = document.getElementById("verify"); //验证码
var btn = document.getElementById("btn"); //注册按钮


btn.onclick = function () {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) {
                var text = xhr.responseText;
                var resultText = JSON.parse(text);
                if (resultText.tip === "success") {
                    alert("您将跳转到登陆页面！");
                    window.location.href="../html/login.html";
                } else{
                    if (resultText.tip==="fail" && resultText.mess==="0") {
                        alert("手机号已存在！");
                    } else if (resultText.tip==="fail" && resultText.mess==="1") {
                        alert("数据库插入异常！");
                    } else {
                        alert("验证码错误！");
                    }
                }
            }
        }
    }
    xhr.open("POST", "http://localhost:8080/index.jsp/Resign", true);
    var data = "&number=" + number.value
        + "&password=" + password.value
        + "&code=" + code.value;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}
function refreshcode() {
    var refresh = document.getElementById("refreshimg");//获取图片
    refresh.src = "http://localhost:8080/index.jsp/CheckCode";
}