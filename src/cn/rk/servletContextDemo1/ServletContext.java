package cn.rk.servletContextDemo1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/servletContext")
public class ServletContext extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { javax.servlet.ServletContext servletContext = this.getServletContext();
        //1。通过HttpServlet获取
        javax.servlet.ServletContext context = this.getServletContext();

        //设置数据
        context.setAttribute("meg","hello");

        //获取文件的服务器路径
        /*
        * 若a.txt在/web文件夹下，则写/a.txt
        * 若a.txt在/web/WEB-INF文件夹下，则写/WEB-INF/a.txt
        * 若a.txt在/src文件夹下，则写/WEB-INF/clasese/a.txt
        * */
        String realPath = context.getRealPath("/a.txt");//web目录下的文件

        File file = new File(realPath);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
