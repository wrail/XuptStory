package servlet;
import Javacode.DBconn;
import Javacode.JsonCode;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name = "ChangeInform", urlPatterns = "/ChangeInform")
public class ChangeInform extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String number=request.getParameter("number");
        //在session中已经记住number
       //String number=request.getParameter("number");
            String number = (String) request.getSession().getAttribute("number");
            String touxiang = request.getParameter("touxiang");
            String username = request.getParameter("username");
            String sex = request.getParameter("sex");
            String wechat = request.getParameter("wechat");
            //String birthday=request.getParameter("birthday");
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            String date = request.getParameter("date");
            String school = request.getParameter("school");
            String introduction = request.getParameter("introduction");
            System.out.println("number:"+number);
            int flag;
            PrintWriter out;
            // DBconn.SelectNum(number);
            flag = DBconn.Inform(number, touxiang, username, sex, wechat, year, month, date, school, introduction);
            System.out.println("flag:"+flag);
            if (flag >= 1) {
                //String s=JsonCode.JsonMess("1","succsess");
                JSONObject json = new JSONObject();
                json.put("tip", "success");
                json.put("number", number);
                json.put("touxiang", touxiang);
                json.put("username", username);
            json.put("sex", sex);
            json.put("wechat", wechat);
            json.put("year", year);
            json.put("month", month);
            json.put("date", date);
            json.put("school", school);
            json.put("introduction", introduction);
            out = response.getWriter();
            out.print(json.toString());
            out.close();
        } else {
            String s = JsonCode.JsonMess("1", "fail");
            out = response.getWriter();
            out.print(s);
            out.close();
        }}
//      String s=JsonCode.JsonMess("1","fail");//刷新一下
//       out=response.getWriter();
//       out.print(s);
//       out.close();
//}
        //  else {
        //  String s = JsonCode.JsonMess("0", "fail");//如上***
        //out = response.getWriter();
        //out.print(s);
        //out.close();
        //}
        // }

        protected void doGet (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            doPost(request, response);
        }
    }
