package Javacode;

import java.io.PrintWriter;
import java.sql.*;

public class DBconn {
    static String url = "jdbc:mysql://127.0.0.1:3306/new_schema?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    static String user = "root";
    static String password = "wrial.qq.com";
    public static Connection con;

    public static Connection init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static int update(String number, String password) {
        int i = 0;
        PreparedStatement ps = null;
        try {
            Connection con = DBconn.init();
            //ps=con.prepareStatement("insert into new_table value (?,?,?)");
            ps = con.prepareStatement("insert into new_table value (?,?)");
            // ps.setString(1,username);
            ps.setString(1, number);
            ps.setString(2, password);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                DBconn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return i;
    }

    public static int ReviseInform(String number, String password) {
        PreparedStatement ps = null;
        int flag = 0;
        Connection con = DBconn.init();
        try {
            ps = con.prepareStatement("update new_table set password=? where number=?");
            ps.setString(1, password);
            ps.setString(2, number);
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBconn.close();
        }


        return flag;
    }

    //public static int select(String username,String password) {
    public static int select(String number, String password) {
        //未解决在servlet中result中为什么判空错误呢
        int flag = 1;//查寻存在flag0否则为1
        Connection con = DBconn.init();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select * from new_table where number= ? and  password= ?");
            ps.setString(1, number);
            ps.setString(2, password);
            //ps.close();
            rs = ps.executeQuery();
            while (rs.next()) {
                flag = 0;
                //return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                DBconn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static int SelectNum(String number) {
        int flag = 1;//查寻存在flag0否则为1
        Connection con = DBconn.init();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select number from new_table where number= ?");
            ps.setString(1, number);
            //  ps.close();
            //   ps.setString(2,password);
            rs = ps.executeQuery();
            while (rs.next()) {
                flag = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        //return  flag;
        return flag;
    }

    public static int InsertNum(String number) {
        int flag = 0;
        // ResultSet rs=null;
        Connection con;
        con = DBconn.init();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("insert into information(number, touxiang, username, sex, wechat, year, month, date, school, introduction) values (?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, number);
            ps.setString(2, null);
            ps.setString(3, null);
            ps.setString(4, null);
            ps.setString(5, null);
            ps.setString(6, null);
            ps.setString(7, null);
            ps.setString(8, null);
            ps.setString(9, null);
            ps.setString(10, null);
            flag = ps.executeUpdate();//受影响行数
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBconn.close();
        }
        return flag;
    }

    public static int Inform(String number, String touxiang, String username, String sex, String wechat, String year, String month, String date, String school, String introduction) {
        int flag = 0;
        int j = 0;
        User u=null;
        PreparedStatement ps = null;
        Connection con = DBconn.init();
        try {
            u= DBconn.GetInform(number);//从数据库查找num
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (u.getNumber()!=null) {
            try {
                ps = con.prepareStatement("update information set username=?,sex=?,wechat=?,year=?,month=?,date=?,school=?,introduction=? where number=?");
                //   ps=  con.prepareStatement("update information set touxiang=?,username=?,sex=?,wechat=?,year=?,month=?,date=?,school=?,introduction where number=?");
                //ps.setString(1,number);
                //ps.setString(2,touxiang);
                ps.setString(1, username);
                ps.setString(2, sex);
                ps.setString(3, wechat);
                ps.setString(4, year);
                ps.setString(5, month);
                ps.setString(6, date);
                ps.setString(7, school);
                ps.setString(8,introduction);
                ps.setString(9, number);
                flag = ps.executeUpdate();
                System.out.println("flag1" + flag);
                if (flag == 1) {
                    return flag;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                DBconn.close();
            }
        } else {
            j = DBconn.InsertNum(number);
            System.out.println("Insert" + j);
            try {
                ps = con.prepareStatement("update information set username=?,sex=?,wechat=?,year=?,month=?,date=?,school=?,introduction=? where number=?");
                //   ps.setString(1,number);
                //ps.setString(2,touxiang);
                ps.setString(1, username);
                ps.setString(2, sex);
                ps.setString(3, wechat);
                ps.setString(4, year);
                ps.setString(5, month);
                ps.setString(6, date);
                ps.setString(7, school);
                ps.setString(8,introduction);
                ps.setString(9, number);
                flag = ps.executeUpdate();
                System.out.println("flag" + flag);
                if (flag == 1) {
                    return flag;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                DBconn.close();
            }
        }
        return 0;
    }

    public static User GetInform(String number) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con;
        con = DBconn.init();
        User u=new User();
        try {
            ps = con.prepareStatement("select * from information where number=?");
            ps.setString(1, number);
            rs = ps.executeQuery();
            while (rs.next()){
                number=rs.getString("number");
               String username=rs.getString("username");
                String touxiang=rs.getString("touxiang");
               String sex=rs.getString("sex");
                String wechat=rs.getString("wechat");
                String year=rs.getString("year");
                String month=rs.getString("month");
                String date=rs.getString("date");
                String school=rs.getString("school");
                String introduction=rs.getString("introduction");
                u.setNumber(number);
                u.setUsername(username);
                u.setTouxiang(touxiang);
                u.setSex(sex);
                u.setWechat(wechat);
                u.setYear(year);
                u.setMonth(month);
                u.setMonth(date);
                u.setSchool(school);
                u.setIntroduction(introduction);
                System.out.println("number"+number+"\nusername"+username+"\nsex"+sex);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            DBconn.close();
        }
        return u;
    }

    public static void close() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}