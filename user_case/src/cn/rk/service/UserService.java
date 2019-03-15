package cn.rk.service;

import cn.rk.domain.PageBean;
import cn.rk.domain.User;

import java.util.List;

public interface UserService {

    /*
    * 查询所有用户信息
    * */
     List<User> findAll();

    /*
    * 根据id查询
    * */
     User findUserById(String id);

    /*
    * 根据id删除用户信息
    * */
    void deleteUser(String id);

    /*
    * 根据id修改用户信息
    * .*/
     void updateUser(User user);

     void addUser(User user);

    /*
    * Login
    * */
     User login(User user);

     /*
     * 删除选中用户，批量删除
     * */
    void delSelectedUser(String[] ids);

    /*
    * 分页查询
    * */
    PageBean<User> findUserByPage(String curruntPage, String rows);
}
