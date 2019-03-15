package cn.rk.web;

import cn.rk.domain.User;
import cn.rk.service.UserService;
import cn.rk.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getIdServlet")
public class GetIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1, 获取要删除的人的id
        String id = request.getParameter("id");
        //request.getSession().setAttribute("id",id);

        UserService service = new UserServiceImpl();
        User user = service.findUserById(id);

        request.setAttribute("user",user);

        //资源转发到/userListServlet
        request.getRequestDispatcher("/update.jsp").forward(request,response);
        //System.out.println(id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
