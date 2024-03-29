package cn.rk.servlet.download;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1, 获取请求参数，文件名称
        String filename = request.getParameter("filename");
        //2， 使用字节流加载文件进内存
        //2。1 找到资源的服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/"+filename);
        //2。2 用字节流关联
        FileInputStream fis = new FileInputStream(realPath);


        //3, 设置response的响应头
        //3，1 设置响应头类型：content——type
        String mimeType = servletContext.getMimeType(filename);//获取文件的MIME类型
        response.setHeader("content-type",mimeType);
        //3，2 设置响应头打开方式：content—disposition
        response.setHeader("content-disposition","attachement;filename="+filename);
        //4， 将输入流的数据写出道输出流中
        ServletOutputStream sos = response.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len = 0;
        while ((len=fis.read(buff)) != -1){
            sos.write(buff,0,len);
        }
        fis.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doPost(request, response);
    }
}
