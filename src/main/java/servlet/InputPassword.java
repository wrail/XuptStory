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

@WebServlet(name="InputPassword",urlPatterns = "/InputPassword")
public class InputPassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //一个为输入密码，一个为重复输入密码，
        PrintWriter out;
        String number=(String) request.getSession().getAttribute("number");
        String password1=request.getParameter("password1");
        String password2=request.getParameter("password2");

        if(password1.equals(password2)){
            String s= JsonCode.JsonMess("1","success");//验证成功
           DBconn.ReviseInform(number,password1);
            out=response.getWriter();
            out.print(s);
            out.close();
        }else {
            String s= JsonCode.JsonMess("0","fail");//验证失败
            out=response.getWriter();
            out.print(s);
            out.close();

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
