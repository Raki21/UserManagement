package cn.rk.web;

import cn.rk.domain.User;
import cn.rk.service.UserService;
import cn.rk.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        /*user.setName(request.getParameter("name"));
        user.setGender(request.getParameter("sex"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        user.setAddress(request.getParameter("address"));
        user.setQq(request.getParameter("qq"));
        user.setEmail(request.getParameter("email"));*/

        //2, 调用修改信息Service
        UserService service = new UserServiceImpl();
        service.addUser(user);
        //资源转发到/userListServlet,  由于没有共享数据，采用response
        //request.getRequestDispatcher("/userListServlet").forward(request,response);
        response.sendRedirect(request.getContextPath()+"/userListServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
