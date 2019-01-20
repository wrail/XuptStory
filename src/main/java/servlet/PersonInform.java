package servlet;
import Javacode.DBconn;
import Javacode.User;
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
@WebServlet(name = "PersonInform",urlPatterns = "/PersonInform")
public class PersonInform extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String number=request.getParameter("number");
  String username=null,touxiang=null,sex=null,wechat=null,year,month,date,school=null,introduction=null;
        PrintWriter out;
      //  try {
            User u= null;
            try {
                u = DBconn.GetInform(number);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        while (rs.next()){
//            number=rs.getString("number");
//            username=rs.getString("username");
//            touxiang=rs.getString("touxiang");
//            sex=rs.getString("sex");
//            wechat=rs.getString("wechat");
//            birthday=rs.getString("birthday");
//            school=rs.getString("school");
//            introduction=rs.getString("introduction");
//            System.out.println("number"+number+"\nusername"+username+"\nsex"+sex);
//        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        JSONObject js=new JSONObject();
        //js.put("tip","success");
        js.put("number",u.getNumber());
        js.put("username",u.getUsername());
        js.put("touxiang",u.getTouxiang());
        js.put("sex",u.getSex());
        js.put("wechat",u.getWechat());
        js.put("year",u.getYear());
        js.put("month",u.getMonth());
        js.put("date",u.getDate());
        js.put("school",u.getSchool());
        js.put("introduction",u.getIntroduction());
        out=response.getWriter();
        out.print(js.toString());
        out.close();
    //}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
