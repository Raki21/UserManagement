package cn.rk.dao.impl;

import cn.rk.dao.UserDao;
import cn.rk.domain.User;
import cn.rk.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public int findTotalCount(Map<String, String[]> condition) {
        //定义初始化sql,注意最后的空格，最好多加
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //获取map
        Set<String> keySet = condition.keySet();
        //定义一个参数的集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value))
            {
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//?条件的值
            }
        }

        return template.queryForObject(sb.toString(), Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1  ";
        StringBuilder sb = new StringBuilder(sql);
        //获取map
        Set<String> keySet = condition.keySet();
        //定义一个参数的集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value))
            {
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//?条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);

        //BeanPropertyRowMapper<User>(User.class)中，如果查询到的是多结果集，就会返回List，如果是单结果集，就返回一个对象
        return template.query(sb.toString(),new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}
