package cn.rk.dao;

import cn.rk.domain.User;
import cn.rk.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
    //声明JDBCTemplate对象共用
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /*
    登陆方法
    * */
    public User login(User loginUser){
        User user = null;
        try {
            //1，编写sql
            String sql="select * from user where username = ? and password = ? ";
            //2。调用querry方法
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }



    };

}
