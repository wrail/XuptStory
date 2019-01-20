package servlet;

import Javacode.DBconn;
import Javacode.JavaSmsApi;
import Javacode.JsonCode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "SignIn", urlPatterns = "/SignIn")
public class SignIn extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//     int flag=0;
//        PrintWriter out;
//     String password=request.getParameter("password");
//     String number=request.getParameter("number");
//    // flag=DBconn.SelectNum(number);
//      flag=DBconn.select(number,password);
//        System.out.println("This is "+flag);
//        System.out.println("Number:"+number+"Password:"+password);
//         out=response.getWriter();
//     if(flag==0){
//        String s=JsonCode.JsonMess("1","success");
//       out=response.getWriter();
//        out.print(s);
//        out.close();
//     }
//     else{
//         String s=JsonCode.JsonMess("0","fail");
//         out=response.getWriter();
//         out.print(s);
//         out.close();}
//     }
        PrintWriter out=null;
        int flag = 1;
        response.setContentType("application/json");
        String number = request.getParameter("number");
        //String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("This is " + flag);
        System.out.println("Number:" + number + "Password:" + password);
        flag = DBconn.select(number, password);
        System.out.println("This is " + flag);
        System.out.println("Number:" + number + "Password:" + password);
        request.getSession().setAttribute("number",number);
        if (flag == 0) {//flag存在为0否则为1
           out = response.getWriter();
            String s = JsonCode.JsonMess("1", "success");
            out.print(s);
            out.close();
        } else {
            //判断错误原因
            int i = DBconn.SelectNum(number);
            System.out.println("This is "+i);
            if (i == 0) {//
                 out = response.getWriter();
                String s = JsonCode.JsonMess("0", "fail");//0代表密码错误
                out.print(s);
                out.close();
            } else {
                try {
                    //0密码错误，1用户不存在
                    out = response.getWriter();
                    String s = JsonCode.JsonMess("1", "fail");//1代表用户不存在
                    out.print(s);
                    out.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
     HttpSession session=request.getSession();
     session.setAttribute("username", username);
     session.setAttribute("passward",passward);
     response.getWriter().print(session.getAttribute(username));
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//doPost(request,response);
    }
}
