<%--
  Created by IntelliJ IDEA.
  User: raki
  Date: 2019/3/12
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        //1. 给超链接和图片绑定单击事件
        //2/ 重新设置双人床图片的值

        window.onload = function () {
            //1. 获取图片对象
            var img = document.getElementById("checkCode");
            var date = new Date().getTime();
            //2. 绑定单击事件

            img.onclick = function () {
                location.reload();
                img.src = "/checkCodeServlert?"+date;

            }

            var change = document.getElementById("change");
            change.onclick = function (){
                img.src = "/checkCodeServlert?"+date;
            }
        }


    </script>
    <style>
        div{
            color : red;
        }
    </style>
</head>
<body>
<form action="/login" method="post" enctype="multipart/form-data">
    USERNAME: <input type="text" name="username"> <br>
    PASSWORD: <input type="password" name="password"><br>
    CODE:<input type="text" name="checkcode"><br>
    <img id="checkCode" src="/checkCodeServlet">
    <a id="change" href="" f="" >换一张</a><br>
    <input type="submit" value="Log in">
    <div>
        <%= request.getAttribute("ccmsg")%>
    </div>
    <div>
        <%= request.getAttribute("loginmsg")%>
    </div>
</form>
</body>
</html>
