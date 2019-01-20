
var number = document.getElementById("number"); //手机号码
var password = document.getElementById("password"); //验证码
var btn = document.getElementById("btn"); //登录按钮
var resultbox = document.getElementById("resultbox"); //提示文本框


//点击登录按钮时
btn.onclick = function () {
    var xhr = new XMLHttpRequest();
    var num = number.value;
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) {
                var text = xhr.responseText;
                var resultText = JSON.parse(text);
                if (resultText.tip === "success") {
                    window.localStorage.setItem("number",num);
                    alert("您将跳转到主页！");
                    window.location.href="../html/index.html";
                } else {
                    if(resultText.tip==="fail" && resultText.mess==="0") {
                        resultbox.innerHTML="密码错误！";
                        //alert("密码错误！");
                    } else if (resultText.tip==="fail" && resultText.mess==="1") {
                        resultbox.innerHTML="用户不存在！";
                        //alert("用户不存在！");
                    }
                }
            }
        }
    }
    xhr.open("POST", "http://localhost:8080/index.jsp/SignIn", true);
    var data = "&number=" + number.value
       + "&password=" + password.value;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}


