package servlet;
import Javacode.DBconn;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet(name="PicMess",urlPatterns = "/PicMess")
public class PicMess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*
* 1.连接到数据库中另外一个表里 Picture
* 2.在数据库上进行查询操作
* 3.通过Json发送给前端
* */
String name=null;
String local=null;
        Connection conn=DBconn.init();
        try {
            PreparedStatement ps= conn.prepareStatement("select * from Picture where name=? and local=?");
  ps.setString(1,name);
  ps.setString(2,local);
  ResultSet rs=ps.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
