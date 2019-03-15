package cn.rk.test;

import cn.rk.dao.UserDao;
import cn.rk.domain.User;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testlogin(){
        User loginuser = new User();
        loginuser.setUsername("admin");
        loginuser.setPassword("123");

        UserDao dao = new UserDao();
        User user = dao.login(loginuser);
        System.out.println(user);

    }

}
