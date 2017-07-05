<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>

	<title>登录/注册</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/bootstrap-social.css" rel="stylesheet" type="text/css">	
	<link href="<%=request.getContextPath()%>/css/templatemo_style.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/css/colrank.css" rel='stylesheet' type='text/css' />
    <link href="<%=request.getContextPath()%>/css/nav.css" rel='stylesheet' type='text/css' />
    <script src="../js/jquery.min.js"></script>
    <link href="<%=request.getContextPath()%>/css/lbt.css" rel='stylesheet' type='text/css' />
    <script src="<%=request.getContextPath()%>/js/lbt.js"></script>
	<script src="<%=request.getContextPath()%>/js/uploadPreview.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/login.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery-3.1.1.js" type="text/javascript"></script>
	<style type="text/css">
	/*
	div{
	border:1px solid red;
	}
	*/
	</style>	
</head>
<body class="templatemo-bg-image-1">
<script type="text/javascript">
						function openLogin(){
							document.getElementById("login-1").style.display="";
							document.getElementById("login-2").style.display="";
							document.getElementById("f1").style.display="";
							document.getElementById("re-1").style.display="none";
							document.getElementById("re-2").style.display="none";
							document.getElementById("f2").style.display="none";
							}
						function closeLogin(){
							document.getElementById("login-1").style.display="none";
							document.getElementById("login-2").style.display="none";
							document.getElementById("f1").style.display="none";
							document.getElementById("re-1").style.display="";
							document.getElementById("re-2").style.display="";
							document.getElementById("f2").style.display="";
							if($("#re-1").height() > $("#re-2").height()){
					    		$("#re-2").css("height",$("#re-1").height())
					    		}
					    	else{
					    		$("#re-1").css("height",$("#re-2").height())					    		
					    	}
							
						}
						
						//注册表单是否填全？
						function checkEmpty2()  
						{  			
						    if(!document.getElementById("confirmRegis").checked)
						    {
						    	alert("请同意注册！");
						    	return false;
						    }
						    if(document.getElementById("r0").checked){
						    	var ok=1;
						    	if($('#id_info').html()!='该用户名可以注册'){
						    		ok=0;
						    	}
						    	else if($('#password_info').html()!=''){
						    		ok=0;
						    	}
						    	else if($('#password2_info').html()!=''){
						    		ok=0;
						    	}
						    	else if($('#sa_info').html()!=''){
						    		ok=0;
						    	}
						    	if(ok!=1){
						    		alert("有信息缺失！");
						    		return false;
						    	}
						    	
						    }
						    else if(document.getElementById("r1").checked)
						    {
						    	var provinceId2 = $("#pum_p").val();
						    	if($('#pum_m').val()=='unknown'||$('#pum_m').val()==null){
						 	    	if(provinceId2!='unknown')	 	    	
						 	    		$("#pum_info").html('专业必须选择');	
						 	    }
						 	    else{
						 	    	if(provinceId2!='unknown')	    	
						 	    		$("#pum_info").html('');	
						 	    }
						    	if($('#pum_info').html()!=''||$('#syear_info').html()!=''||
						    			$('#rn_info').html()!='')
						    	{
						    		alert("请完善信息以便审核！");
						    		return false;
						    	}
						    	
						    	 var imgShow=document.getElementById("imgShow");//预览图片
						    
								 var file=document.getElementById("up_img");
								 var filevalue=document.form2.pic.value;
								    if(filevalue =="")
								  {
								    	alert("请上传图片以待审核！");
								    	return false;
								    }
								    else{
								    	 var fileSize=file.files[0].size;//文件大小
									     //alert(fileSize);
								    	if(!yanzheng(fileSize))//超过限制大小
								    		return false;
								    }
						    }
						    else if(document.getElementById("r2").checked)
						    {
						    	var imgShow=document.getElementById("imgShow");//预览图片
							    
								 var file=document.getElementById("up_img");
								 var filevalue=document.form2.pic.value;
								    if(filevalue =="")
								  {
								    	alert("请上传图片以待审核！");
								    	return false;
								    }
								    else{
								    	 var fileSize=file.files[0].size;//文件大小
									     //alert(fileSize);
								    	if(!yanzheng(fileSize))//超过限制大小
								    		return false;
								    }
						    	
						    }

						    //alert($('#pum_u').val());
						    return true;  
						}
						//不同身份不同信息填写
					    function moreInfo(){ 
					    	
					        var sYear = document.getElementById("input3");
					        var career = document.getElementById("input4");
					        var type = document.getElementById("input5");
					        var name=document.getElementById("input6");
					        var picture=document.getElementById("input7");
					        var provinceCityHS=document.getElementById("provinceCityHS");
					        var provinceUM=document.getElementById("provinceUM");
					        var provinceCollege=document.getElementById("provinceCollege");
					        var sex_info=document.getElementById("sex_info");
					        //var picHint=document.getElementById("picHint");
					        if (document.getElementById("r1").checked || document.getElementById("r2").checked) {  
					        	$("#re-2").height(800);
				        		type.style.display='none';
				        		name.style.display='block';
					        	career.style.display='none';
					        	sYear.style.display='block';
					        	picture.style.display='block';
					        	provinceCityHS.style.display='none';
					        	provinceUM.style.display='block';
					        	provinceCollege.style.display='none';
					        	if($('#sex').val()=='unknown')				        	
					        		sex_info.style.display='block';
					        	else{
					        		sex_info.style.display='none';
					        	}
					        	if(document.getElementById("r2").checked){
							        picHint.innerHTML="请上传相关证明以待审核！(如：工作证等,最大：1M,支持格式：gif,jpeg,jpg,bmp,png)";		
					        		type.style.display='block';
					        		name.style.display='block';
						        	career.style.display='block';
						        	sYear.style.display='none';
						        	provinceUM.style.display='none';
						        	provinceCollege.style.display='block';
							    }
					        	else{
					        		picHint.innerHTML="请上传相关证明以待审核！(如：学生证、校园一卡通、毕业证书等,最大：1M,支持格式：gif,jpeg,jpg,bmp,png)";
					        	}
					        }
					        else{
					        	$("#re-1").height(722);
					        	$("#re-2").height(722);
					        	type.style.display='none';
					        	sYear.style.display='none';
					        	career.style.display='none';
					        	name.style.display='none';
					        	picture.style.display='none';
					        	provinceCityHS.style.display='block';
					        	provinceCollege.style.display='none';
					        	provinceUM.style.display='none';
					        	sex_info.style.display='none';
					        }
					        if($("#re-1").height() > $("#re-2").height()){
					    		$("#re-2").css("height",$("#re-1").height())
					    		}
					    	else{
					    		$("#re-1").css("height",$("#re-2").height())					    		
					    	}					      
					    }   

						//上传图片预览
						function init(){
							//调整框体大小
					        	if($("#login-1").height() > $("#login-2").height()){
					        		$("#login-2").css("height",$("#login-1").height())
					        		}
					        	else{
					        		$("#login-1").css("height",$("#login-2").height())					    		
					        	}
					        }
					    window.onload = function () { 
						
					    	var result='${requestScope.result }';
					    	if(result!=null&&result!='')					    	
					    		alert(result);
					        init();
				            new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });				
					    }
					    //上传图片大小限制
					    function yanzheng(fileSize) 
                      {        
					         if(fileSize>1024*1024*1)//大小大于1M
					        {
					            alert("图片不得大于1M！");
					            return false;
					           }
					         else if(fileSize==0){
					        	 alert("请上传审核图片");
						         return false;
					         }
					         else
					        	 return true;
                      } 
			</script>						

	 <div class="header-top">
	    <div class="inner">
			<div class="siteLogo">
			<a href="<%=request.getContextPath()%>/"></a>
			</div>
            <div class="navT">
                <ul>
                    <li><a href="#">高校排行</a></li>
                    <li><a href="#">专业解析</a></li>
                    <li>
                        <a href="#">学长问答</a> 
                    </li>
                    <li>
                        <a href="#">权威咨询</a> 
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/jsp/transstu.jsp">关于插班生</a> 
                    </li>
                </ul>
            </div>
            
          
            <div class="user fr">
				  <!--  <a href="" class="log login">登录</a>
                  <a href="" class="log signUp" onclick="return false;">登录/注册</a>-->
            </div>
              
        
        </div>
	</div>

	    <div class="container">
	   
		<div class="col-md-12">	
		<%String from=request.getParameter("from"); %>
		<%//request.setAttribute("uri", request.getParameter("uri")); %>
			<form id=f1 class="form-horizontal templatemo-login-form-2" role="form" >
					<input type=hidden name="from" value="<%=from %>"/>
					<div class="col-md-6" style="text-align:center;background-color: rgba(13,13,13,0.25);">
						<a href="javascript:openLogin();">
						    <font size=15px color=#fff>登录</font>
						</a>
					</div>
					<div class="col-md-6" style="text-align:center;background-color: rgba(13,13,13,0.25);">					
						<a href="javascript:closeLogin();">
						    <font size=15px color=#fff>注册</font>
						</a>
					</div>
				
					<div id=login-1 class="templatemo-one-signin col-md-6">
				        <div class="form-group">
				          <div class="col-md-12">		          	
				            <label for="username" class="control-label">用户名</label>
				            <div class="templatemo-input-icon-container">
				            	<i class="fa fa-user"></i>
				            	<input type="text" class="form-control" name="id" id="id1" placeholder="">
				            </div>		            		            		            
				          </div>              
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">密码</label>
				            <div class="templatemo-input-icon-container">
				            	<i class="fa fa-lock"></i>
				            	<input type="password" class="form-control" name="password" id="password1" placeholder="">
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <div class="checkbox">
				                <label>
				                   <p style="color:red" id="msg"></p>
				                </label>
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <input type="button" id="loginCheck" value="登录" class="btn btn-warning">
				          </div>
				        </div>
				        <div class="form-group">
				          	<div class="col-md-12">
				        		<!-- <a href="forgot-password.html" class="text-center">无法登陆?</a>-->
				       	 	</div>
				    	</div>
					</div>
					<div id=login-2 class="templatemo-other-signin col-md-6" style="background-color: rgba(13,13,13,0.25);">
						<div style="height:30px"></div>
						<div>
						<label class="margin-bottom-15">
							
							<a href="#">忘记密码?</a>
							
						</label>
						</div>
						<label class="margin-bottom-15">
							没有账号，请点击注册
						</label>				
					</div>  
					
					

			</form>
			
			<form name="form2" id="f2" class="form-horizontal templatemo-login-form-2" role="form" 
			action="/yzwish/login/register" method="post"
			style="display:none" onSubmit="return checkEmpty2()" enctype="multipart/form-data">
			        <input type=hidden name="from" value="<%=from %>"/>
					<div class="col-md-6" style="text-align:center;background-color: rgba(13,13,13,0.25);"">
						<a href="javascript:openLogin();">
						    <font size=15px color=#fff>登录</font>
						</a>
					</div>
					<div class="col-md-6" style="text-align:center;background-color: rgba(13,13,13,0.25);"">					
						<a href="javascript:closeLogin();">
						    <font size=15px color=#fff>注册</font>
						</a>
					</div>
			        <div id=re-1 class="templatemo-one-signin col-md-6" style="display:none">
				        <div>
				        <span>身份选择:</span>
						<input type=radio id="r0" name="duty" checked="checked" value="1" onclick="moreInfo()"/>
						<span style="color:#FFFFFF">高考生</span>
						<input type=radio id="r1" name="duty" value="2" onclick="moreInfo()"/>
						<span style="color:#FFFFFF">学长学姐</span>
						<input type=radio id="r2" name="duty" value="3" onclick="moreInfo()"/>
						<span style="color:#FFFFFF">老师</span>
						<div style="height:20px"></div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">		          	
				            <label for="id" class="control-label">用户名</label>
				            <span id="id_info" style="color:red">用户名不能为空</span>
				            <div class="templatemo-input-icon-container">
				            	<i class="fa fa-user"></i>
				            	<input type="text" class="form-control" id="id2" 
				            	name="id" placeholder="由5-10位数字和字母组成" maxlength=10>
				            </div>		            		            		            
				          </div>              
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">密码</label>
				            <span id="password_info" style="color:red">密码不能为空</span>
				            <div class="templatemo-input-icon-container">
				            	<i class="fa fa-lock"></i>
				            	<input type="password" class="form-control" id="password2" name="password" 
				            	  maxlength=20 placeholder="5-20位字符组成">
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">确认密码</label>
				            <span id="password2_info" style="color:red">确认密码不能为空</span>
				            <div class="templatemo-input-icon-container">
				            	<i class="fa fa-lock"></i>
				            	<input type="password" class="form-control" id="password3" placeholder="">
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">选择密保问题</label>
				            <div class="templatemo-input-icon-container">
				            	<select class="form-control" name="safetyProblem">
				            	<option value="1">你的母亲名字是</option>
				            	</select>
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">输入密保答案</label>
				            <span id="sa_info" style="color:red">密保问题答案不能为空</span>
				            <div class="templatemo-input-icon-container">
				            	<input id="safetyAnswer" name="safetyAnswer"
				            	type="text" class="form-control" placeholder="答案为1-20个字" maxlength="20"/>
				            </div>
				          </div>
				        </div>
				        <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">性别</label>
				            <span id="sex_info" style="color:red;display:none">请选择性别</span>
				            <div class="templatemo-input-icon-container">
				            	<select class="form-control" id="sex" name="sex">
				            	<option value="unknown">未选择</option>
				            	<option value="男">男</option>
				            	<option value="女">女</option>
				            	</select>
				            </div>
				          </div>
				        </div>
				         <div class="form-group">
				          <div class="col-md-12">
				            <label for="password" class="control-label">年龄</label>
				            <div class="templatemo-input-icon-container">
				            	<input type="text" class="form-control" maxlength="3" name="hsage"/>
				            </div>
				          </div>
				        </div>
				        
				        <div class="form-group">
				          <div class="col-md-12">
				            <div class="checkbox">
				                <label>
				                  <input type="checkbox" checked="true" id="confirmRegis"> 同意注册
				                </label>
				            </div>
				          </div>
				        </div>

				        <div class="form-group">
				          <div class="col-md-12">
				            <input type="submit" value="注册" class="btn btn-warning">
				          </div>
				          
				        </div>
                        <div style="height:10px"></div>
					</div>
					<div id=re-2 class="templatemo-other-signin col-md-6" style="display:none;background-color: rgba(13,13,13,0.25);">
					    
						<div id="moreInfo">
						<div id="provinceCityHS">
						<label class="control-label">所在高中</label>
						<select class="form-control" id="pchs_pc">
						<option value="unknown" selected="selected">无(点击选择省/市)</option>
						</select>
						<div style="height:20px"></div>
						<select class="form-control" id="pchs_hs" name="hId">
						<option value="unknown">无</option>
						</select>
						<div style="height:20px"></div>
						</div>
						<div id="provinceUM" style="display:none">
						<label class="control-label">所在大学专业</label>
						<span id="pum_info" style="color:red;">不能为空</span>
						<select class="form-control" id="pum_p">
						   <option value="unknown" selected="selected">请选择省/市</option>
						</select>
						<div style="height:20px"></div>
						
						<div style="display:none" id="pumm">
						<select class="form-control" id="pum_u" name="universityId">
						</select>						
						<div style="height:20px"></div>
						
						
						<select class="form-control" id="pum_m" name="majorId">
						</select>
						<div style="height:20px"></div>
						</div>
						
						</div>
						<div id="provinceCollege" style="display:none">
						<label class="control-label">所在大学</label>
						<select class="form-control" id="pc_p">
						</select>
						<div style="height:20px"></div>
						<select class="form-control" id="pc_c" name="ui">
						</select>
						<div style="height:20px"></div>
						</div>
						<div style="display:none" id="input3">
						<label class="control-label">入学年份</label>
						<span id="syear_info" style="color:red;">不能为空</span>
						<input type="text" class="form-control" id="sYear" maxlength="4" name="syear"/>
						<div style="height:20px"></div>
						</div>
						<div style="display:none" id="input4">
						<label class="control-label">学校职位</label>
						<span id="position_info" style="color:red;">不能为空</span>
						<input type="text" class="form-control" id="position" name="position"
						maxlength="10" placeholder="例如：讲师"/>
						<div style="height:20px"></div>
						</div>
						<div style="display:none" id="input5">
						<label class="control-label">研究方向</label>
						<span id="rd_info" style="color:red;">不能为空</span>
						<input type="text" class="form-control" name="resDirection"
						id="resDirection" maxlength="10"/>
						<div style="height:20px"></div>
						</div>
						<div style="display:none" id="input6">
						<label class="control-label">真实姓名</label>
						<span id="rn_info" style="color:red;">不能为空</span>
						<input type="text" class="form-control" id="realName" name="realName"
						 maxlength="10"/>
						<div style="height:20px"></div>
						</div>
						<div style="display:none" id="input7">
						<span id="picHint"></span>
                        <div id="imgdiv"><img id="imgShow"  width="200px" height="120px"/></div>
                        <input type="file" id="up_img" name="pic"/>
						</div>
						</br>
						</br>
						
						<label class="margin-bottom-15">
							注册须知 
						</label>
						<label class="margin-bottom-15">
							高考生只需填写账户、密码和密保问题及答案即可注册
						</label>
						<label class="margin-bottom-15">
							学长学姐、老师请填写个人信息并上传有效证件以待审核
						</label>
					    </div>   
				</div>
			</form>			
		</div>				 	   		      
	</div>
	<div style="height:170px"></div>
    <jsp:include page="down.jsp"/>	
    
</body>

</html>