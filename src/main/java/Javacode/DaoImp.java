package Javacode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoImp implements InterfaceDao {
    public List<User> getUser() {
        java.util.List<User> list=new ArrayList<User>();
      try {Connection con=DBconn.init();

               PreparedStatement ps=con.prepareStatement("select(username,passward) from  new_table");
           ResultSet rs=ps.executeQuery();
          try {
              while(rs.next()) {
                  User u=new User();
                  u.setUsername(rs.getString("username"));
                 // u.setPassword(rs.getString("passward"));
                  list.add(u);
                  DBconn.close();
              }
          } catch (SQLException e) {

              e.printStackTrace();
          }
       }catch (SQLException e){
           e.printStackTrace();
       }
        return list;
    }

    @Override
    public boolean add(String password,String number) {
        boolean flag=false;
        DBconn.init();
       // String sql ="insert into new_table "+"values('"+u.getUsername()+"','"+u.getNumber()+"','"+u.getPassword()+"')";
        int i=DBconn.update(number,password);
        if(i>0){
            flag = true;
        }
        DBconn.close();
        return flag;
    }

    @Override
    public boolean delete(String password,String number) {
        boolean flag=false;
        DBconn.init();
        //String sql="delete form new_table where number="+num;
        int i =DBconn.update(password,number);
        if(i==1){
            flag = true;
        }
        DBconn.close();
        return flag;
    }

    @Override
    public boolean update(User u) {
        return false;
    }
}
