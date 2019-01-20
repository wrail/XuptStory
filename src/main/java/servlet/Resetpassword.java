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

@WebServlet(name="Resetpassword",urlPatterns = "/Resetpassword")
public class Resetpassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       PrintWriter out;
        String number=request.getParameter("number");
        String msmcode=request.getParameter("msmcode");
        String msmcode1=(String) request.getSession().getAttribute("msmcode");
       int i= DBconn.SelectNum(number);
        if(i==1){
            String s = JsonCode.JsonMess("0", "fail");//0代表number不存在
            out=response.getWriter();
            out.print(s);
            out.close();
        }else {
            Long time=System.currentTimeMillis();
            Long msmtime=(Long) request.getSession().getAttribute("msmtime");
            /*if(time-msmtime>2*60*1000){
                JsonCode.JsonMess("1","fail");//1代表验证码超时
            }*/
            if(msmcode.equals(msmcode1)){
                //如果验证成功，将number放入session
                request.getSession().setAttribute("number",number);
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
