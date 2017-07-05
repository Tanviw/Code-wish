<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>预志愿——志愿填报参考平台</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<meta http-equiv="charset" content="UTF-8">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->

<link href='http://fonts.lug.ustc.edu.cn/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
<link href='http://fonts.lug.ustc.edu.cn/css?family=Niconne' rel='stylesheet' type='text/css'>
<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/bootstrap-social.css" rel="stylesheet" type="text/css">	
<link href="<%=request.getContextPath()%>/css/templatemo_style.css" rel="stylesheet" type="text/css">
<script type="application/javascript" src="<%=request.getContextPath()%>/js/jquery-3.1.1.js"></script>
<script type="application/javascript" src="<%=request.getContextPath()%>/js/demo.js"></script>
<!--/script-->
<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},900);
				});
			})

</script>

</head>
<body>
<%
  String qs=request.getQueryString();
   if(qs!=null)//参数不空
 {
   if(qs.indexOf("from")!=-1)//找得到from参数
  {
  
	   String from=request.getParameter("from");
  
	   if(from!=null&&!from.equals("null")&&!from.equals("")) 
	  {
		  //from=new String(from.getBytes("ISO-8859-1"),"UTF-8");
		  //pageContext.setAttribute("from",from,PageContext.PAGE_SCOPE);
		  

		  from=from.replace("&amp;", "&");
		  //from=URLEncoder.encode(from,"UTF-8");
		  //from=java.net.URLDecoder.decode(from, "UTF-8");
		  //from=from.replace("%3F", "?").replace("%3D","=").replace("%26", "&");
		  response.sendRedirect("http"+from);
	  
      } 
	   else{
	   }
  }
  else{
	  
  }
 }
  %>
  
	<!-- header-section-starts -->
	<div class="header-banner">
		<div class="container">
			<div class="home">
					<a href="<%=request.getContextPath()%>/"><span class="glyphicon glyphicon-home"></span></a>
			</div>
			<div class="header-top">
				<div class="social-icons">
				<c:if test="${sessionScope.login_status!=1}">
				  <a href="<%=request.getContextPath()%>/jsp/login.jsp" id="login" style="font-size:20px;">注册/登录</a>
				</c:if>
				<c:if test="${sessionScope.login_status==1}">
				  <a href="#"><span style="color: #FFFFFF;font-size:20px;">${sessionScope.id}</span></a>
				  &nbsp;&nbsp;
					<a href="<%=request.getContextPath()%>/login/logout" id="login" style="color: #FFFFFF;color: #FFFFFF;font-size:20px;">注销</a>
				</c:if>
				</div>
			<span class="menu"><img src="images/nav.png" alt=""/></span>
				<div class="top-menu">
					<ul>
					<nav class="cl-effect-13">
					    <li><a href="#">关于插班生</a></li>
						<li><a href="#">友情链接</a></li>
						<li><a href="#">关于我们</a></li>
						<li><a href="#">联系我们</a></li>						
					</nav>
					</ul>
				</div>
				<!-- script for menu -->
					<script> 
						$( "span.menu" ).click(function() {
						$( ".top-menu ul" ).slideToggle( 300, function() {
						 // Animation complete.
						});
						});
					</script>
				<!-- //script for menu -->

				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<!--标题-->
			<div class="banner-info text-center">
				<h1><a href="<%=request.getContextPath()%>/">Dream College</a></h1>
			</div>


			<!--四个导航-->
			<div class="header-bottom-grids text-center">
				<div class="header-bottom-grid1">
					<span class="glyphicon glyphicon-leaf"></span>
					<br/>
					<br/>
					<br/>
					<a href="#">高校风云榜</a>
				</div>
				<div class="header-bottom-grid2">
					<span class="glyphicon glyphicon-certificate"></span>
					<br/>
					<br/>
					<br/>
					<a href="#">专业小锦囊</a>
				</div>
				<div class="header-bottom-grid3">
					<span class="glyphicon glyphicon-tree-deciduous"></span>
					<br/>
					<br/>
					<br/>
					<a href="/yzwish/topic/showHotTopic">
                                                    学长来帮忙</a>
				</div>
				<div class="header-bottom-grid4">
					<span class="glyphicon glyphicon-screenshot"></span>
					<br/>
					<br/>
					<br/>
					<a href="#">
                                                    权威咨询</a> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<!--注册登录框-->
		<div id="loginForm" class="text-center">
		</div>
	</div>
	<!-- header-section-ends -->
	<!-- content-section-starts -->
	<div class="services">
		<div class="container">
			<!--四个导航概述-->
			<div class="services-top-grids text-center">
				<div class="secvice-top-grid-1">
					<h3>高校排行榜</h3>
					<p>知名大学的胜负之分。如果您是学霸，绝对不要错过，即使你很普通，你也可以创造奇迹</p>
					<div class="icon1">
						<i class="icon1"></i>
					</div>
				</div>
				<div class="secvice-top-grid-2">
					<h3>热门专业介绍</h3>
					<p>提供核心课程、就业前景、平均薪酬信息，更有该专业的大学排名供您参考</p>
					<div class="icon1">
						<i class="icon2"></i>
					</div>
				</div>
				<div class="secvice-top-grid-3">
					<h3>与高校校友互动</h3>
					<p>高考生了解高校的快速直接途径，校友吐槽的绝佳平台，赶快来提问点赞吧</p>
					<div class="icon1">
						<i class="icon3"></i>
					</div>
				</div>
				<div class="secvice-top-grid-4">
					<h3>让高校老师解惑</h3>
					<p>在网上查阅了许多专业资料，是不是依旧分不清楚相似专业呢？赶紧来向专业老师提问吧</p>
					<div class="icon1">
						<i class="icon4"></i>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>

		</div>
		<div style="height:100px"></div>
		<div class="footer">
		<div class="container">
			<div class="copyright text-center">
				<p>Copyright © 2017 BCNF All Rights Reserved.</p>
                <p>膜藓煲 芭拉拉老魔仙 版权所有</p>
			</div>
		</div>
	</div>
	</div>
</body>
</html>