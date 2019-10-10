<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=gbk">--%>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="Mon, 23 Jan 1978 20:52:30 GMT">
</head>
<body>
<%
    request.setCharacterEncoding("utf8");
    String abc = request.getParameter("abc");
    if(abc == null) {
        out.println("空值");
    }
    else
    {
        out.println("原始编码：");
        out.println(abc);
        out.println("</br>");
        out.println("utf8编码：");
        String abc1 = new String(abc.getBytes("ISO-8859-1"),"utf8");
        System.out.println(abc1);
        out.println(abc1);
        out.println("</br>");
        out.println("gbk编码：");
        String abc2 = new String(abc.getBytes("ISO-8859-1"),"gbk");
        out.println(abc2);
    }
%>

</br>
</br>
</br>

<a href="test.jsp">返回</a>

</body>
</html>