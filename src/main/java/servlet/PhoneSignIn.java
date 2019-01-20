package servlet;
import Javacode.DBconn;
import Javacode.JsonCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name = "PhoneSignIn",urlPatterns = "/PhoneSignIn")//通过动态短信验证登陆
public class PhoneSignIn extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        String code=request.getParameter("msmcode");
        int i= DBconn.SelectNum(number);
        PrintWriter out;
        String msmcode=(String) request.getSession().getAttribute("msmcode");
        if(i==1){
            String s = JsonCode.JsonMess("0", "false");//0代表number不存在
            out=response.getWriter();
            out.print(s);
            out.close();
        }else {
            if(code.equals(msmcode)){
                String s = JsonCode.JsonMess("1", "success");//验证成功
                out=response.getWriter();
                out.print(s);
                out.close();
            }else {
                String s = JsonCode.JsonMess("1", "fail");//验证码错误
                out=response.getWriter();
                out.print(s);
                out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
