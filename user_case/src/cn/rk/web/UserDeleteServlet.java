package cn.rk.web;

import cn.rk.service.UserService;
import cn.rk.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取需要删除的人的姓名
        String id = request.getParameter("id");
        System.out.println(id);
        //2, 调用service
        UserService service = new UserServiceImpl();
        service.deleteUser(id);
        //转发到/userListServlet,
        //request.getRequestDispatcher("/userListServlet").forward(request,response);
        response.sendRedirect(request.getContextPath()+"/userListServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
