/*JS方法*/
/*flag这个变量不销毁，存储的是判断当前浏览器是否兼容getComputedStyle，兼容的话是标准浏览器，但是如果flag=false说明当前的浏览器是IE6-8*/
/*使用 惰性思想（JS高级编程技巧之一）来封装我的常用的方法库：在第一次给utils赋值的时候我们就已经把兼容处理好了把最后的结果存放在flag变量中，以后在每一个方法中，只要是IE6-8不兼容的，我们不需要重新的检测，只需要使用flag的值即可*/
var utils = (function () {
    var flag = "getComputedStyle" in window;

    //listToArray:实现将类数组转换为数组
    function listToArray(likeAry) {
        if (flag) {
            return Array.prototype.slice.call(likeAry, 0);
        }
        var ary = [];
        for (var i = 0; i < likeAry.length; i++) {
            ary[ary.length] = likeAry[i];
        }
        return ary;
    }

    //formatJSON:将JSON格式的字符串转换为JSON格式的对象
    function formatJSON(jsonStr) {
        return "JSON" in window ? JSON.parse(jsonStr) : eval("(" + jsonStr + ")");
    }

    //getCSS:获取当前元素所有经过浏览器计算过的样式中的[attr]对应的值
    function getCss(attr) {
        var val = null, reg = null;
        if (flag) {
            val = window.getComputedStyle(this, null)[attr];
        } else { /*如果传递进来的结果是opacity，说明要获取到的是透明度，但是在IE6-8下获取透明度需要使用filter*/
            if (attr === "opacity") {
                val = this.currentStyle["filter"];
                /*alpha(opacity=10)把获取到的结果进行剖析，获取里面的数字，
                                                                    把数字除以100才和标准浏览器保持一致*/
                reg = /^alpha\(opacity=(\d+(?:\.\d+)?)\)$/i;
                val = reg.test(val) ? reg.exec(val)[1] / 100 : 1;
            } else {
                val = this.currentStyle[attr];
            }
        }
        reg = /^(-?\d+(\.\d+)?)(px|pt|rem|em)?$/i;
        return reg.test(val) ? parseFloat(val) : val;
    }

    //offset:获取页面中任意元素距离body的偏移
    function offset(curEle) {
        var totalLeft = null, totalTop = null, par = curEle.offsetParent;
        /*offsetParent：父级参照物*/
        /*1、首先把自己本身的偏移进行累加*/
        totalLeft += curEle.offsetLeft;
        totalTop += curEle.offsetTop;
        /*只要没有找到body，我们就把父级参照物的边框和偏移也进行累加*/
        while (par) {
            /*2、累加父级参照物的边框*/
            /*在标准的IE8浏览器中，我们使用offsetLeft/offsetTop其实是把父级参照物的边框已经算在内了，所以不需要在单独加边框*/
            if (navigator.userAgent.indexOf("MSIE 8.0") === -1) { /*不是IE8浏览器才进行累加边框*/
                totalLeft += par.clientLeft;
                totalTop += par.clientTop;
            }
            /*累加父级参照物本身的偏移*/
            totalLeft += par.offsetLeft;
            totalTop += par.offsetTop;
            par = par.offsetParent;
        }
        return {left: totalLeft, top: totalTop};
    }

    //win:获取或者设置关于浏览器的盒子模型的信息
    //如果只传递了attr没有传递value，默认的意思是“获取”如果两个参数都传递了，意思是“设置”,不严谨的来说这就是有关于“类的重载”，同一个方法，通过传递参数的不同实现了不同的功能
    function win(attr, value) {
        if (typeof value === "undefined") { /*说明没有传递value值，属于“获取”操作*/
            return document.documentElement[attr] || document.body[attr];
        }
        document.documentElement[attr] = value;
        document.body[attr] = value;
    }

    //children：获取curEle下所有的元素子节点（兼容所有浏览器），如果传递了tagname,可以在获取的集合中进行二次筛选，把指定标签名的获取到*/
    function children(curEle, tagName) {
        var ary = [];
        if (!flag) {
            var nodeList = curEle.childNodes;
            for (var i = 0, len = nodeList.length; i < len; i++) {
                var curNode = nodeList[i];
                curNode.nodeType === 1 ? ary[ary.length] = curNode : null;
            }
            nodeList = null;
        } else {
            ary = this.listToArray(curEle.children);
        }
        if (typeof tagName === "string") {
            for (var k = 0; k < ary.length; k++) {
                var curEleNode = ary[k];
                if (curEleNode.nodeName.toLowerCase() !== tagName.toLowerCase()) {
                    ary.splice(k, 1);
                    k--;
                }

            }
        }
        return ary;
    }

    //prev:获取上一个哥哥元素节点
    function prev(curEle) {
        if (flag) {
            return curEle.previousElementSibling;
        }
        //首先获取当前元素的上一个哥哥节点，判断是否为元素节点，不是的话基于当前的继续找上面的哥哥节点，一直找到哥哥元素节点为止，如果没有哥哥元素节点，返回null即可
        var pre = curEle.previousSibling;
        while (pre && pre.nodeType !== 1) {
            pre = pre.previousSibling;
        }
        return pre;
    }

    //next:获取下一个弟弟元素节点
    function next(curEle) {
        if (flag) {
            return curEle.nextElementSibling;
        }
        var nex = curEle.nextSibling;
        while (nex && nex.nodeType !== 1) {
            nex = nex.nextSibling;
        }
        return nex;
    }

    //prevAll:获取所有的哥哥元素节点
    function prevAll(curEle) {
        var ary = [];
        var pre = this.prev(curEle);
        while (pre) {
            ary.unshift(pre);
            pre = this.prev(pre);
        }
        return ary;
    }

    //nextAll:获取所有的弟弟元素节点
    function nextAll(curEle) {
        var ary = [];
        var nex = this.next(curEle);
        while (nex) {
            ary.push(nex);
            nex = this.next(nex);
        }
        return ary;
    }

    //sibling:获取相邻的两个元素节点
    function sibling(curEle) {
        var pre = this.prev(curEle);
        var nex = this.next(curEle);
        var ary = [];
        pre ? ary.push(pre) : null;
        nex ? ary.push(nex) : null;
        return ary;
    }

    //siblings:获取所有的兄弟元素节点，所有哥哥+所有弟弟
    function siblings(curEle) {
        return this.prevAll(curEle).concat(this.nextAll(curEle));
    }

    //index:获取当前元素索引,有几个哥哥索引就是几
    function index(curEle) {
        return this.prevAll(curEle).length;
    }

    //firstChild:获取第一个元素子节点
    function firstChild(curEle) {
        var chs = this.children(curEle);
        return chs.length > 0 ? chs[0] : null;
    }

    //lastChild:获取最后一个元素子节点
    function lastChild(curEle) {
        var chs = this.children(curEle);
        return chs.length > 0 ? chs[chs.length - 1] : null;
    }

    //append:向指定容器的末尾追加元素
    function append(newEle, container) {
        container.appendChild(newEle);
    }

    //prepend:向指定容器的开头追加元素,把新的元素添加到容器中第一个子元素节点的前面,如果一个元素子节点都没有，那就放到末尾就好啦
    function prepend(newEle, container) {
        var fir = this.firstChild(container);
        if (fir) {
            container.insertBefore(newEle, fir);
            return;
        }
        container.appendChild(newEle);
    }

    //insertBefore:把新元素(newEle)追加到指定元素(oldEle)的前面
    function insertBefore(newEle, oldEle) {
        oldEle.parentNode.insertBefore(newEle, oldEle);
    }

    //insertAfter:把新元素(newEle)追加到指定元素(oldEle)的后面,相当于追加到oldEle弟弟元素的前面,如果弟弟不存在，也就是当前元素已经是最后一个了，就把新的元素放到末尾就好了
    function insertAfter(newEle, oldEle) {
        var nex = this.next(oldEle);
        if (nex) {
            oldEle.parentNode.insertBefore(newEle, nex);
            return;
        }
        oldEle.parentNode.appendChild(newEle);
    }

    //hasClass:验证当前元素是否包含className这个样式类名
    function hasClass(curEle, className) {
        var reg = new RegExp("(^| +)" + className + "( +|$)");
        return reg.test(curEle.className);
    }

    //addClass:给元素增加样式类名
    function addClass(curEle, className) {
        /*如果className传递进来的值包含多个样式类名，有可能会造成样式类名重复添加，所以我们把传递进来的字符串按照一到多个空格拆分成数组中的每一项，在进行验证*/
        var ary = className.replace(/(^ +| +$)/g, "").split(/ +/g);
        /*先去除首尾空格 在进行拆分*/
        /*循环数组，一项项的进行验证增加即可*/
        for (var i = 0, len = ary.length; i < len; i++) {
            var curName = ary[i];
            if (!this.hasClass(curEle, curName)) {
                curEle.className += " " + curName;
            }
        }
    }

    //removeClass:给元素移除样式类名
    function removeClass(curEle, className) {
        var ary = className.replace(/(^ +| +$)/g, "").split(/ +/g);
        for (var i = 0, len = ary.length; i < len; i++) {
            var curName = ary[i];
            if (this.hasClass(curEle, className)) {
                var reg = new RegExp("(^| +)" + curName + "( +|$)", "g");
                curEle.className = curEle.className.replace(reg, " ");
            }
        }
    }

    //getElementsByClass:通过元素的样式类名获取一组元素集合
    function getElementsByClass(strClass, context) {
        //strClass:要获取的样式类名，可能是一个也可能是多个
        //context:获取元素的上下文，如果这个值不传递的话，默认document
        context = context || document;
        if (flag) {
            return this.listToArray(context.getElementsByClassName(strClass));
        }
        var ary = [];
        var strClassAry = strClass.replace(/(^ +| +$)/g, "").split(/ +/g);
        var nodeList = context.getElementsByTagName("*");
        /* *代表通配符 */
        for (var i = 0, len = nodeList.length; i < len; i++) {
            var curNode = nodeList[i];
            var isOk = true;
            for (var k = 0; k < strClassAry.length; k++) {
                var reg = new RegExp("(^| +)" + strClassAry[k] + "( +|$)");
                if (!reg.test(curNode.className)) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                ary[ary.length] = curNode;
            }
        }
        return ary;
    }

    //setCss:给当前元素的某一个样式属性设置值（增加在行内样式上的）
    function setCss(attr, value) {
        /*在JS中设置float样式值的话也需要处理兼容*/
        if (attr === "float") {
            this["style"]["cssFloat"] = value;
            this["style"]["styleFloat"] = value;
            return;
        }
        if (attr === "opacity") {/*透明度在IE6-8下不兼容 需要判断*/
            this["style"]["opacity"] = value;
            this["style"]["filter"] = "alpha(opacity=" + value * 100 + ")";
            return;
        }
        reg = /^(width|height|top|bottom|left|right|((margin|padding)(Top|Bottom|Left|Right)?))$/;
        if (reg.test(attr)) {
            if (!isNaN(value)) {/*在判断传递进来的value值是否是一个有效数字*/
                value += "px";
            }
        }
        this["style"][attr] = value;
    }

    //setGroupCss:给当前元素批量的设置样式属性值
    function setGroupCss(options) {
        /*遍历对象中的每一项，调取setCss方法一个个的进行设置即可*/
        for (var key in options) {
            if (options.hasOwnProperty(key)) {
                setCss.call(this, key, options[key]);
            }
        }
    }

    //css:获取、单独设置、批量设置元素的样式值
    function css(curEle) {
        var ary = Array.prototype.slice.call(arguments, 1);
        var argTwo = arguments[1];
        if (typeof argTwo === "string") {
            /*第二个参数值是一个字符串，这样的话很有可能就是在获取样式，因为还需要判断是否存在第三个参数，如果第三个参数存在的话，就不是获取，而是在单独的设置样式属性值*/
            if (typeof arguments[2] === "undefined") {
                return getCss.apply(curEle, ary);
                /*第三个参数不存在则为获取*/
            }
            /*第三个参数存在则为单独设置*/
            setCss.apply(curEle, ary);
        }
        argTwo = argTwo || 0;
        if (argTwo.toString() === "[object Object]") {
            /*批量设置*/
            setGroupCss.apply(curEle, ary);
        }
    }

    //把外界需要使用的方法暴露给utils
    return {
        win: win,
        offset: offset,
        listToArray: listToArray,
        formatJSON: formatJSON,
        // getCss: getCss,
        children: children,
        prev: prev,
        next: next,
        prevAll: prevAll,
        nextAll: nextAll,
        sibling: sibling,
        siblings: siblings,
        index: index,
        firstChild: firstChild,
        lastChild: lastChild,
        append: append,
        prepend: prepend,
        insertBefore: insertBefore,
        insertAfter: insertAfter,
        hasClass: hasClass,
        addClass: addClass,
        removeClass: removeClass,
        getElementsByClass: getElementsByClass,
        // setCss:setCss,
        // setGroupCss:setGroupCss,
        css: css
    }
})();