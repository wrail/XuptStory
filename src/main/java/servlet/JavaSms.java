package servlet;

import Javacode.DBconn;
import Javacode.JavaSmsApi;
import Javacode.JsonCode;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet( name="JavaSms",urlPatterns = "/JavaSms")
public class JavaSms extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //本地测试可用发送短信功能
        //获取随机数
        Integer c=(int)(Math.random()*8999)+1000+1;
        //将整数转化为字符串
        String msmcode=c.toString();
        PrintWriter out;
      //从前台获取数据
        String number=request.getParameter("number");
        int i= DBconn.SelectNum(number);
        if(i==0) {
            Long msmtime= System.currentTimeMillis();//放入当前时间单位是毫秒
            request.getSession().setAttribute("msmtime",msmtime);
            String apikey = "ba14b5ed1abebac0040aade7c6506805";
            String text = "【马晨阳】您的验证码是" + msmcode ;
            String mobile = number;
            System.out.println(JavaSmsApi.sendSms(apikey, text, mobile));
            request.getSession().setAttribute("msmcode",msmcode);
          String s=JsonCode.JsonMess("1","success");
           out=response.getWriter();
            out.print(s);
            out.close();
            //设置session的有效时间为60s,哈哈 不能这样用，60s是session的非活动时间，那就用系统的时间方法来记录当前时间
            // session.setMaxInactiveInterval(60);
        }else {
            String s=JsonCode.JsonMess("0","fail");
            out=response.getWriter();
            out.print(s);
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
