var number = document.getElementById("number"); //手机号码
var msmcode = document.getElementById("msmcode"); //验证码
var btn = document.getElementById("btn"); //登录按钮
var sendbtn=document.getElementById("sendbtn");//发送验证码按钮
/*发送验证码*/
sendbtn.onclick=function() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) {
                var text = xhr.responseText;
                var resultText = JSON.parse(text);
                if (resultText.tip==="fail") {
                    alert("手机号不存在！");
                }
            }
        }
    }
    xhr.open("POST", "http://localhost:8080/index.jsp/JavaSms", true);
    var data = "&number=" + number.value;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}
/*提交按钮*/
btn.onclick = function () {
    var xhr2 = new XMLHttpRequest();
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === 4) {
            if (xhr2.status >= 200 && xhr2.status < 300 || xhr2.status === 304) {
                var text2 = xhr2.responseText;
                var resultText2 = JSON.parse(text2);
                if (resultText2.tip === "success") {
                    alert("您将跳转到重置密码页面！");
                    window.location.href="../html/resetting.html";
                } else if(resultText2.tip==="fail" && resultText2.mess==="1") {
                    alert("验证码错误！");
                }
            }
        }
    }
    xhr2.open("POST", "http://localhost:8080/index.jsp/Resetpassword", true);
    var data2 = "&number=" + number.value
        + "&msmcode=" + msmcode.value;
    xhr2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr2.send(data2);
}

//验证码倒计时
$(".getCode").on("click",function(){
    time(this);
});
var wait = 60;
function time(obj) {
    if(wait==0) {
        $(".getCode").removeAttr("disabled");
        $(".getCode").text("获取验证码");
        wait = 60;
    }else {
        $(".getCode").attr("disabled","true");
        $(".getCode").text(wait+"秒后重试");
        wait--;
        setTimeout(function() { //倒计时方法
            time(obj);
        },1000);    //间隔为1s
    }
}


