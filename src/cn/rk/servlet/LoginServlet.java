package cn.rk.servlet;

import cn.rk.dao.UserDao;
import cn.rk.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        /*//2。获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //3。封装User对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);*/
        //2. 获取所有请求参数
        Map<String, String[]> map = request.getParameterMap();
        //3。 创建User对象
        User user = new User();
        //3。2 使用BeanUtils封装
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4. 调用userdao的login方法；
        UserDao userDao = new UserDao();
        User login = userDao.login(user);
        //判断验证码
        //1, 先获取验证码session
        HttpSession session = request.getSession();
        String checkCode = (String) session.getAttribute("checkCodeSession");
        //判断验证码正确？
        String checkcode = request.getParameter("checkcode");
        if  (checkCode.equalsIgnoreCase(checkcode)){
            // 5. 判断user
            if (login == null){
                request.setAttribute("loginmsg","用户名活密码错误");
               // request.getRequestDispatcher("/fail").forward(request,response);
            }else{
                //储存数据
                request.setAttribute("user",login);
                request.getRequestDispatcher("/success").forward(request,response);
            }
        }
        else{
            request.setAttribute("ccmsg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }


        /*if (login == null){
            request.getRequestDispatcher("/fail").forward(request,response);
        }else{
            //储存数据
            request.setAttribute("user",login);
            request.getRequestDispatcher("/success").forward(request,response);
        }*/

    }
}
