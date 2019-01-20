var phone=document.getElementById("phonenumber");
var username=document.getElementById("username");
var sex=document.getElementById("sex");
var year=document.getElementById("year");
var month=document.getElementById("month");
var day=document.getElementById("day");
if (window.localStorage.getItem("number")!=null) {
    phone.value=window.localStorage.getItem("number");
    username.value=window.localStorage.getItem("id");

    year.value=window.localStorage.getItem("nian");
    month.value=window.localStorage.getItem("yue");
    day.value=window.localStorage.getItem("ri");
    sex.value=window.localStorage.getItem("xingbie");
}