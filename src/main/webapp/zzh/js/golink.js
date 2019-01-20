var goLink = document.getElementById("goLink");
/*开始GO按钮是不显示的，只有当浏览器卷去的高度超过一屏幕的高度的时候在显示*/
window.onscroll= computedDisplay;
function computedDisplay() { /*window.onscroll不管怎么操作，只要滚动条动了就会触发这个行为*/
    var curTop=  document.documentElement.scrollTop || document.body.scrollTop;
    var curHeight=document.documentElement.clientHeight || document.body.clientHeight; /*当前一屏幕高度*/
    goLink.style.display=curTop>curHeight?"block":"none";
}
goLink.onclick = function () {
    /*当点击的时候让当前的GO消失*/
    this.style.display="none";
    window.onscroll=null;
    /*光这样还不行，GO按钮往回走的时候，滚动条也在滚动，又触发了window.onscroll行为，让GO又显示了，
    我们需要在点击后，把window.onscroll绑定的事件取消掉*/
    var duration = 500, interval = 10,target = document.documentElement.scrollTop || document.body.scrollTop;
    var step = (target / duration) * interval;
    var timer = window.setInterval(function () {
        var curTop = document.documentElement.scrollTop || document.body.scrollTop;
        if (curTop===0) { /*已经到头了，清除定时器*/
            window.clearInterval(timer);
            window.onscroll= computedDisplay;/*当动画结束后还需要把对应的方法重新绑定给window.onscroll*/
            return;
        }
        curTop -= step;
        document.documentElement.scrollTop=curTop;
        document.body.scrollTop=curTop;
    }, interval);
}