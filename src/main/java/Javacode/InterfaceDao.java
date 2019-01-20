package Javacode;

import java.util.List;

public interface InterfaceDao {
    public List<User> getUser();
    public boolean add(String password,String number);
    public boolean delete(String password,String number);
    public boolean update(User u);
}
/*
* 实现接口可用多态
* */
