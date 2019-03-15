package cn.rk.dao;

import cn.rk.domain.User;

import java.util.List;

/*
* 用户操作的DAO
* */
public interface UserDao {

    public List<User> findAll();

    public void deleteUser(int id);

    public void updateUser(User user);

    public void addUser(User user);

    public User findUserByUsernameAndPassword(String username, String password);

    User findById(int id);

    //查询总记录数
    int findTotalCount();
    //分页查询每页记录
    List<User> findByPage(int start, int rows);
}
