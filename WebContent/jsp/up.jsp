<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link href="../css/style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/navf.css" rel='stylesheet' type='text/css' />
    <link href="<%=request.getContextPath()%>/css/colrankf.css" rel='stylesheet' type='text/css' />
    <link href="<%=request.getContextPath()%>/css/lbtf.css" rel='stylesheet' type='text/css' />
    <link href="<%=request.getContextPath()%>/font-awesome-4.7.0/css/font-awesome.min.css" rel='stylesheet' type='text/css' />
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/cbs.js"></script>
    <script src="<%=request.getContextPath()%>/js/lbt.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
 </head>
<body>   
<div class="header-top">
 <% 
   String proName= this.getServletContext().getContextPath()+"/";//项目名称 
  
   //当前页面链接
  String uri= (String)pageContext.getRequest().getAttribute("javax.servlet.forward.request_uri");
  //用于action
    if(uri!=null)
  {
     uri=uri.replace(proName, "");
     //out.println("uri:"+uri);
  }
    else
  {
    	//out.println("uri:null");
  }
  String qs=request.getQueryString();
  //用于jsp
     
	  String url=request.getRequestURL().toString();
	 // out.println("url:"+url);
	 
	 String address=null;//登录前页面
				String adType=null;//登录前页面类型：action。。。
				//结果为action
				if(uri!=null&&!uri.equals("null"))
				{
					//uri中含有?代表request是url编码，地址就为uri
					//非url编码，qs若为空，则处理uri
					if(qs!=null)
					{
					
						if(!qs.equals("null")&&uri.indexOf("?")==-1)
						{
							address=uri+"?"+qs;
						}
					}
					else
						address=uri;
					adType="action";
				}
				//结果为jsp
				else if(url!=null&&!url.equals("null"))
				{
					url=url.substring(url.indexOf("bcnf/")+5);
					if(qs!=null)
					{
						if(!qs.equals("null")&&url.indexOf("?")==-1)//有参数的jsp或html
							address=url+"?"+qs;
					}
					
					else
						address=url;
					adType="jsp";
				}
				String from=address;
				if(from!=null)
				{
					from=from.replace("&", "&amp;");
					from=URLEncoder.encode(from,"UTF-8");
				}
  %>
  
        <div class="inner">
            <div>
			<a href="index.jsp" class="siteLogo"></a>
			</div>
            <div class="nav">
                <ul>
                    <li><a href="#">高校排行</a></li>
                    <li><a href="#">专业解析</a></li>
                     
                    <li>
                        <a href="/yzwish/topic/showHotTopic">学长问答</a> 
                    </li>
                    <li>
                        <a href="#">权威咨询</a> 
                    </li>
                    <li><a href="transstu.jsp">关于插班生</a></li>
                </ul>
            </div>
            <!--  
            <div class="searchT fl">
            <form action="#" >
                <input type="text" placeholder="搜索你感兴趣的学校，专业" class="sr fl" name="searchFor"/>
                <input type="submit" value="搜索" class="sr_btn fl" />
            </form> 
            </div>
            -->
            <div class="user fr">

                  <c:if test="${sessionScope.login_status!=1}">
				  <a href="<%=request.getContextPath()%>/jsp/login.jsp?from=<%=from %>" class="log signUp">登录/注册</a>
				  </c:if>
				  <c:if test="${sessionScope.login_status==1}">
				  <a href="<%=request.getContextPath()%>/userInfoowBasicInfo">
				  <span style="color: #FFFFFF;font-size:20px;">${sessionScope.id}</span>
				  </a>
				  &nbsp;&nbsp;
				  <a href="<%=request.getContextPath()%>/login/logout?from=<%=from %>" id="login" style="color: #FFFFFF;color: #FFFFFF;font-size:20px;">注销</a>
				  </c:if>

            </div>

        </div>
</div>
</body> 