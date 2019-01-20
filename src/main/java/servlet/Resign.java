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
@WebServlet(name = "Resign",urlPatterns = "/Resign")
public class Resign extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password, number;
        int flag ;
        password = request.getParameter("password");
        number = request.getParameter("number");
        String code = request.getParameter("code");
        String code1=(String) request.getSession().getAttribute("code");
        System.out.println("number"+number+"password"+password);
        System.out.println( "code"+code.toLowerCase()+"code1"+code1.toLowerCase());
        PrintWriter out;
        //username = request.getParameter("username");
        //System.out.println(username + password);
        //System.out.println("用户名" + username);
        //System.out.println("密码" + password);
        //Connection con=DBconn.init();
        // PreparedStatement ps=con.prepareStatement("select * from new_table where username= ?");
        //用手机号查询
        flag = DBconn.SelectNum(number);
        System.out.println("flag"+flag);
        try {
            if (flag == 0) {
                    String s = JsonCode.JsonMess("0", "fail");//0代表手机号存在
                    out=response.getWriter();
                out.print(s);
                out.close();
            } else {
                if (code1.toLowerCase().equals(code.toLowerCase())) {
                int i = DBconn.update(number, password);
                System.out.println("i" + i);
                if (i == 1) {
                    String s = JsonCode.JsonMess("1", "success");//创建成功和mess无关
                    //String s = JsonCode.JsonMess("success", "creat success!");
                    //response.getWriter().print(s);
                    out = response.getWriter();
                    out.print(s);
                    out.close();
                } else {
                    String s = JsonCode.JsonMess("1", "fail");//数据库差额u失败
                    out = response.getWriter();
                    out.print(s);
                    out.close();
                }
//                JSONObject json = (JSONObject)request.getSession().getAttribute("code");
////                //对短信验证码进行比较
////                //if (code.equals(request.getSession().getAttribute("code")))
////                Long l=json.getLong("time");//currenttime为Long类型
////                Long l1=System.currentTimeMillis();
////                if((l1-l)>6000){
//////                    String s=JsonCode.JsonMess("0","fail");//0验证码超时
//////                    response.getWriter().print(s);
////                }else{
////                    if(json.get("code").equals(code)){//从json中获取code
////                        int i = DBconn.update( number,password);
////                        // int i = DBconn.update(username, password, number);
////                        if (i == 1) {
////                            String s = JsonCode.JsonMess("1", "success");//创建成功和mess无关
////                            //String s = JsonCode.JsonMess("success", "creat success!");
////                            response.getWriter().print(s);
////                        } else {
////                            String s=JsonCode.JsonMess("1", "fail");//1数据库插入失败
////                            //JsonCode.JsonMess("fail", "insert fail!");
////                            response.getWriter().print(s);
////                        }
////                    } else {
////                        String s = JsonCode.JsonMess("2", "fail");//验证码错误
////                        //String s = JsonCode.JsonMess("fail", "code error!");
////                        response.getWriter().print(s);
////                    }
////                }
            }else {
                    String s=JsonCode.JsonMess("2","fail");//验证码错误
                    out=response.getWriter();
                    out.print(s);
                    out.close();
                }
            }
        }//此处原先catch在后边
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*else{
            String code = (String) request.getSession().getAttribute("code");
            String s = request.getParameter("code");
            //如果验证码相等
            if (code.toLowerCase().equals(s.toLowerCase())) {
                int i = DBconn.update(username, password, number);
                if (i == 1){
                    JSONObject jo = new JSONObject();
                    jo.put("mess", 1);
                    jo.put("success", "check success and insert success!");
                    response.getWriter().print(jo.toString());
            }else {
                    JSONObject jo=new JSONObject();
                    jo.put("mess",0);
                    jo.put("error","insert is false ");
                    response.getWriter().print(jo.toString());
                }
            } else {
                JSONObject jo = new JSONObject();
                    jo.put("mess", 0);
                    jo.put("error", "CheckCode is wrong");
                    response.getWriter().print(jo.toString());
            }
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
   //rs= DBconn.select("select * from new_table where username '"+username+"'");
 //  String sql="insert into new_table(username,number,password) values('"+username+"','"+number+"','"+password+"')";
   }
*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
