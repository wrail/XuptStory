var username=document.getElementById("username");
var sex=document.getElementById("sex");
var year=document.getElementById("year");
var month=document.getElementById("month");
var date=document.getElementById("date");
var number = document.getElementById("phonenumber"); //手机号码
number.value=window.localStorage.getItem("number");
var password = document.getElementById("password"); //验证码
var btn = document.getElementById("btn"); //登录按钮
btn.onclick = function () {
    var xhr = new XMLHttpRequest();
    var id=username.value;
    var xingbie=sex.value;
    var nian=year.value;
    var yue=month.value;
    var ri=date.value;

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) {
                var text = xhr.responseText;
                var resultText = JSON.parse(text);
                if (resultText.tip === "success") {
                    alert("保存成功！");
                    window.localStorage.setItem("id",id);

                    window.localStorage.setItem("nian",nian);
                    window.localStorage.setItem("yue",yue);
                    window.localStorage.setItem("ri",ri);
                    window.localStorage.setItem("xingbie",xingbie);
                } else if (resultText.tip==="fail") {
                    alert("保存失败！");
                }
            }
        }
    }
    xhr.open("POST", "http://localhost:8080/index.jsp/ChangeInform", true);
    var data = "&username=" + username.value
        + "&sex=" + sex.value
        + "&year=" + year.value
        + "&month=" + month.value
        + "&date=" + date.value
        + "&number=" + number;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}


