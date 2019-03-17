package cn.rk.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/*
* 登陆验证过滤器
* */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1 , 强制转换，因为这是ServletRequest接口，他的孩子HttpServlet才有相应的关于Http请求消息和响应消息的相关方法
        HttpServletRequest request = (HttpServletRequest)req;

        //2  获取资源请求路径
        String uri = request.getRequestURI();

        //3 判断是否包含登陆相关资源,注意：要排除掉css，js，图片，验证码
            if(uri.contains("/login.jsp") || uri.contains("/loginServlet")
                    || uri.contains("/css") || uri.contains("/js") || uri.contains("fonts")
                    || uri.contains("/checkCodeServlet")){
                //包含，用户就是想登陆，放行
                chain.doFilter(req, resp);
            }else {
                //从session中获取user
                Object user = request.getSession().getAttribute("user");
                if(user != null)
                {
                    chain.doFilter(req, resp);
                }else {
                    request.setAttribute("login_msg","您尚未登陆，请登录");
                    request.getRequestDispatcher("/login.jsp").forward(req,resp);
                }
            }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
