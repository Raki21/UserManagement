package cn.rk.dao.impl;

import cn.rk.dao.UserDao;
import cn.rk.domain.User;
import cn.rk.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    //创建JdbcTemplate对象，依赖于数据源DataSource
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        //1, 定义sql
        String sql = "select * from user";
        //将查询到的数据封装为JavaBean再装到List集合中
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

        return users;
    }

    @Override
    public void deleteUser(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql,id);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id=?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),
                user.getEmail(),user.getId());
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),
                user.getQq(), user.getEmail(), null, null);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {

        String sql = "select * from user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ? ";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);


    }

    @Override
    public int findTotalCount() {
        String sql = "select count(*) from user ";

        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public List<User> findByPage(int start, int rows) {
        String sql = "select * from user limit ? , ? ";
        //BeanPropertyRowMapper<User>(User.class)中，如果查询到的是多结果集，就会返回List，如果是单结果集，就返回一个对象
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),start,rows);
    }
}
