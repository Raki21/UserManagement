package cn.rk.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1, 创建代理对象，增强getParameter方法
        ServletRequest proxy_req = (ServletRequest)Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParameter方法
                //判断是否为getParameter方法
                if(method.getName().equals("getParameter")){
                    //需要增强返回值
                    //首先获取返回值
                    String value = (String)method.invoke(req,args);
                    if(value != null){
                        for (String str : list) {
                            if(value.contains(str)){
                                value = value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }

                //判断是否为getParameterMap方法

                //判断是否为getParameterValue方法

                return method.invoke(req,args);
            }
        });



        //放行
        chain.doFilter(proxy_req, resp);
    }
    private List<String> list = new ArrayList<String>();//敏感词汇list集合
    public void init(FilterConfig config) throws ServletException {
        //因为init方法只加载一次，所以在这里加载配置文件


        try {
            //加载文件
            //获取文件真实路径
            String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/SensitiveWords.txt");
            //读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(realPath),"UTF-8"));
            //将文件的每一行数据添加到list
            String line = null;
            while((line = br.readLine())!=null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
