/*动画效果*/
~function () {
    var zzhEffect = {
        //t:time当前已走的时间 b:begin起始位置 c:change总距离 d:duration总时间
        Linear: function (t, b, c, d) {
            return c * t / d + b;
        },
        //指数衰减的反弹缓动
        Bounce:{
            easeIn:function (t,b,c,d) {
                return c-zhufengEffect.Bounce.easeOut(d-t,0,c,d)+b;
            },
            easeOut:function (t,b,c,d) {
                if((t/=d)<(1/2.75)){
                    return c*(7.5625*t*t)+b;
                }else if (t < (2/2.75)){
                    return c*(7.5625*(t-=(1.5/2.75))*t+ .75)+b;
                }else if(t < (2.5/2.75)){
                    return c*(7.5625*(t-=(2.25/2.75))*t+ .9375)+b;
                }else {
                    return c*(7.5625*(t-=(2.625/2.75))*t + .984375)+b;
                }
            },
            easeInOut:function (t,b,c,d) {
                if (t<d/2){
                    return zhufengEffect.Bounce.easeIn(t*2,0,c,d)* .5+b;
                }
                return zhufengEffect.Bounce.easeOut(t*2-d,0,c,d)*.5+c*.5+b;
            }
        },
        //二次方的缓动
        Quad:{
            easeIn:function (t,b,c,d) {
                return c*(t/=d)*t+b;
            },
            easeOut:function (t,b,c,d) {
                return -c*(t/=d)*(t-2)+b;
            },
            easeInOut:function (t,b,c,d) {
                if ((t/=d/2)<1){
                    return c/2*t*t+b;
                }
                return -c/2*((--t)*(t-2)-1)+b;
            }
        },
        //三次方的缓动
        Cubic: {
            easeIn: function (t, b, c, d) {
                return c * (t /= d) * t * t + b;
            },
            easeOut: function (t, b, c, d) {
                return c * ((t = t / d - 1) * t * t + 1) + b;
            },
            easeInOut: function (t, b, c, d) {
                if ((t /= d / 2) < 1) {
                    return c / 2 * t * t * t + b;
                }
                return c / 2 * ((t -= 2) * t * t + 2) + b;
            }
        },
        //四次方的缓动
        Quart:{
            easeIn:function (t,b,c,d) {
                return c*(t/=d)*t*t*t+b;
            },
            easeOut:function (t,b,c,d) {
                return -c*((t=t/d-1)*t*t*t-1)+b;
            },
            easeInOut:function (t,b,c,d) {
                if ((t/=d/2)<1){
                    return c/2*t*t*t*t+b;
                }
                return -c/2*((t-=2)*t*t*t-2)+b;
            }
        },
        //五次方的缓动
        Quint:{
            easeIn:function (t,b,c,d) {
                return c*(t/=d)*t*t*t*t+b;
            },
            easeOut:function (t,b,c,d) {
                return c*((t=t/d-1)*t*t*t*t+1)+b;
            },
            easeInOut:function (t,b,c,d) {
                if ((t/=d/2)<1){
                    return c/2*t*t*t*t*t+b;
                }
                return c/2*((t-=2)*t*t*t*t+2)+b;
            }
        },
        //正弦曲线的缓动
        Sine:{
            easeIn:function (t,b,c,d) {
                return -c*Math.cos(c/d*(Math.PI/2))+c+b;
            },
            easeOut:function (t,b,c,d) {
                return c*Math.sin(t/d*(Math.PI/2))+b;
            },
            easeInOut:function (t,b,c,d) {
                return -c/2*(Math.cos(Math.PI*t/d)-1)+b;
            }
        },
        //指数曲线的缓动
        Expo:{
            easeIn:function (t,b,c,d) {
                return (t==0)?b:c*Math.pow(2,10*(t/d-1))+b;
            },
            easeOut:function (t,b,c,d) {
                return (t==d)?b+c:c*(-Math.pow(2,-10*t/d)+1)+b;
            },
            easeInOut:function (t,b,c,d) {
                if (t==0)return b;
                if (t==d)return b+c;
                if ((t/=d/2)<1)return c/2*Math.pow(2,10*(t-1))*b;
                return c/2*(-Math.pow(2,-10*--t)+2)+b;
            }
        },
        //圆形曲线的缓动
        Circ:{
            easeIn:function (t,b,c,d) {
                return -c*(Math.sqrt(1-(t/=d)*t)-1)+b;
            },
            easeOut:function (t,b,c,d) {
                return c*Math.sqrt(1-(t=t/d-1)*t)+b;
            },
            easeInOut:function (t,b,c,d) {
                if ((t/=d/2)<1){
                    return -c/2*(Math.sqrt(1-t*t)-1)+b;
                }
                return c/2*(Math.sqrt(1-(t-=2)*t)+1)+b;
            }
        },
        //超过范围的三次方缓动
        Back:{
            easeIn:function (t,b,c,d,s) {
                if (s==undefined) s=1.70158;
                return c*(t/=d)*t*((s+1)*t-s)+b;
            },
            easeOut:function (t,b,c,d,s) {
                if (s==undefined) s=1.70158;
                return c*((t=t/d-1)*t*((s+1)*t+s)+1)+b;
            },
            easeInOut:function (t,b,c,d,s) {
                if (s==undefined) s=1.70158;
                if ((t/=d/2)<1){
                    return c/2*(t*t*(((s*=(1.525))+1)*t-s))+b;
                }
                return c/2*((t-=2)*t*(((s*=(1.525))+1)*t+s)+2)+b;
            }
        },
        //指数衰减的正弦曲线缓动
        Elastic:{
            easeIn:function (t,b,c,d,a,p) {
                if (t==0)return b;
                if ((t/=d)==1)return b+c;
                if (!p)p=d*.3;
                var s;
                !a||a<Math.abs(c)?(a=c,s=p/4):s=p/(2*Math.PI)*Math.asin(c/a);
                return -(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;
            },
            easeOut:function(t,b,c,d,a,p){
                if (t==0) return b;
                if ((t/=d)==1)return b+c;
                if (!p)p = d*.3;
                var s;
                !a||a<Math.abs(c)?(a=c,s=p/4):s=p/(2*Math.PI)*Math.asin(c/a);
                return (a*Math.pow(2,-10*t)*Math.sin((t*d-s)*(2*Math.PI)/p)+c+b);
            },
            easeInOut(t,b,c,d,a,p){
                if (t==0)return b;
                if ((t/=d/2)==2)return b+c;
                if (!p)p = d*(.3*1.5);
                var s;
                !a||a<Math.abs(c)?(a=c,s=p/4):s=p/(2*Math.PI)*Math.asin(c/a);
                if (t<1)return -.5*(a*Math.pow(2,10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p))+b;
                return a*Math.pow(2,-10*(t-=1))*Math.sin((t*d-s)*(2*Math.PI)/p)*.5+c+b;
            }
        }
    };

    //move:实现多方向的运动动画
    function move(curEle, target, duration,effect,callBack) {
        //curEle:当前要操作运动的元素
        //target:当前动画的目标位置，存储的是每一个方向的目标位置{top:1,left:2}...
        //duration:当前动画的总时间
        //根据target获取每一个方向的起始值begin和总距离change
        //在每一次指向方法之前，首先把当前元素之前正在运行的动画结束掉
        var tempEffect = zzhEffect.Linear;
        if (typeof effect ==="number"){
            switch (effect) {
                case 0:
                    tempEffect = zzhEffect.Linear;
                    break;
                case 1:
                    tempEffect = zzhEffect.Circ.easeInOut;
                    break;
                case 2:
                    tempEffect = zzhEffect.Elastic.easeOut;
                    break;
                case 3:
                    tempEffect = zzhEffect.Back.easeOut;
                    break;
                case 4:
                    tempEffect = zzhEffect.Bounce.easeOut;
                    break;
                case 5:
                    tempEffect = zzhEffect.Expo.easeIn;
                    break;
            }
        }else if(effect instanceof Array){
            tempEffect = effect.length>=2?zzhEffect[effect[0]][effect[1]]:zzhEffect[effect[0]];
        }else if(typeof  effect === "function"){
            //实际意义是：effect是不传递值的，传递进来的函数应该是回调函数的值，tempEffect还是默认的匀速公式即可
            callBack = effect;
        }

        window.clearInterval(curEle.timer);
        var begin = {}, change = {};
        for (var key in target) {
            //key:方向，例如top,left
            if (target.hasOwnProperty(key)) {
                begin[key] = utils.css(curEle,key);
                change[key] = target[key] - begin[key];
            }
        }
        //2、实现多方向的运动动画
        var time = 0;
        curEle.timer = window.setInterval(function () {
            time += 10;
            //到达目标位置，结束动画，让当前元素的样式等于目标样式值
            if (time >= duration) {
                utils.css(curEle, target);//批量设置目标值
                window.clearInterval(curEle.timer);
                //在动画结束的时候，如果用户把回调函数传给我了，我就把用户传递进来的那个函数执行
                callBack && callBack.call(curEle);//typeof  callBack==="function"?callBack():null;
                return;
            }
            //没有达到目标：分别的获取每一个方向的当前位置，给当前元素设置样式即可
            for (var key in target) {
                if (target.hasOwnProperty(key)) {
                    var curPos = tempEffect(time, begin[key], change[key], duration);
                    utils.css(curEle, key, curPos);
                }

            }
        }, 10);


    }

    window.zzhAnimate = move;

}();