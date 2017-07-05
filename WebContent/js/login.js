
$( function(){ 
var pc = new Object();  //省/市 高中
var pc_t=new Object();  //省/市 大学
pc_t['unknown']='';
pc['unknown']='<option value="unknown">无</option>';
function initProvinceHS(){
	var data;
	$.ajax({
		type:'get',
		contentType:'application/json',
		url:'/yzwish/login/getHighSchoolList',
		data:'',	
		beforeSend:function(){
			//处理发送之前的检查事务。
			//alert("发送之前！");
		},										
	    success:function(data){
	    	//alert("返回页面");
	    	console.log(data[0]);
	    	var res = $.parseJSON(data);
	    	for(j=0;j<res.length;j++){
	    		$("#pchs_pc").append('<option value="'+
		    			res[j].provinceId+'">'+res[j].provinceName+'</option>');
		    	optionsServiceId =""; 
		    	for(i=0;i<res[j].highSchoolList.length;i++){
		    		var hsList=res[j].highSchoolList;
		    		optionsServiceId=optionsServiceId+"<option value='"+hsList[i].hId+"' >"+hsList[i].hname+"</option>";  	    		
		    	}
		    	pc[res[j].provinceId]=optionsServiceId;
	    	}
	    	
	    },
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//alert("进入error！");
			alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
	    }
	}
			);
}
function initProvinceCollege(){
	var data;
	$.ajax({
		type:'get',
		contentType:'application/json',
		url:'/yzwish/login/getCollegeList',
		data:'',	
		beforeSend:function(){
			//处理发送之前的检查事务。
			//alert("发送之前！");
		},										
	    success:function(data){
	    	//alert("返回页面");
	    	console.log(data[0]);
	    	var res = $.parseJSON(data);
	    	var count=1;
	    	for(j=0;j<res.length;j++){
	    		$("#pc_p").append('<option value="'+
		    			res[j].provinceId+'">'+res[j].provinceName+'</option>');
	    		$("#pum_p").append('<option value="'+
		    			res[j].provinceId+'">'+res[j].provinceName+'</option>');
		    	optionsServiceId =""; 
		    	for(i=0;i<res[j].cList.length;i++){
		    		var cList=res[j].cList;
		    		optionsServiceId=optionsServiceId+"<option value='"+cList[i].universityId+"' >"+cList[i].universityname+"</option>";  	    		
		    	}
		    	pc_t[res[j].provinceId]=optionsServiceId;
		    	if(count==1){		    	
		    		$("#pc_c").html(optionsServiceId);
		    	}
		    	count++;
	    	}
	    	
	    },
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//alert("进入error！");
			alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
	    }
	}
			);
}
function getMajor(){
	var data;
	var university=new Object();
	university.universityId=$("#pum_u").val();
	var info=JSON.stringify(university);
	$.ajax({
		type:'post',
		contentType:'application/json',
		url:'/yzwish/login/getMajorList',
		data:info,	
		beforeSend:function(){
			//处理发送之前的检查事务。
			//alert("发送之前！");
		},										
	    success:function(data){
	    	//alert("返回页面");
	    	console.log(data[0]);
	    	var res = $.parseJSON(data);
	    	$('#pum_m').html('');
	    	$("#pumm").css("display",'block');
	    	if(res==null||res.length==0){
	    		$('#pum_m').append("<option value='unknown'>抱歉暂未添加该大学开设专业</option>");
	    	}
	    	else{
	    		for(j=0;j<res.length;j++){	
		    		$('#pum_m').append("<option value='"+res[j].majorId+"'>"+res[j].majorName+"</option>");
		    	}
	    	}	    	
	    },
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//alert("进入error！");
			alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
	    }
	}
			);
}
initProvinceHS(); 
initProvinceCollege();

$('#loginCheck').click(
								function(){
									$('#msg').empty();//清空消息
									//alert("提交表单事件！");
									var account=new Object();
									account.id=$("#id1").val();
									account.password=$("#password1").val();
									account.from=$("#from").val();
									var info=JSON.stringify(account);
									$.ajax({
										type:'post',
										contentType:'application/json',
										url:'/yzwish/login/checkLogin',
										data:info,	
										beforeSend:function(){
											//处理发送之前的检查事务。
											//alert("发送之前！");
											//检查用户名或密码为空
											if($('#id1').val()==''||$('#password1').val()==''){
												document.getElementById("msg").innerHTML='用户名或密码不能为空';
												return false;
											}										
										},										
									    success:function(data){
									    	//alert("返回页面");
									    	var msg=document.getElementById("msg");
									    	console.log(data[0]);
									    	var res = $.parseJSON(data);									    	
									    	if(res[0].msg=='0')									    	
									    		msg.innerHTML='用户名或密码错误';//跳出验证错误信息
									    	//用户名密码验证成功
									    	else if(res[0].msg=='1'){
									    		if(res[0].from!=null){
									    			window.location.href="/yzwish/jsp/index.jsp?from="+res[0].from;//跳转链接
									    		}
									    		else{
									    			window.location.href="/yzwish/jsp/index.jsp?from=null";//跳转链接
									    		}
									    	}
									    	else if(res[0].msg=='3'){
									    		msg.innerHTML='服务器错误';//跳出验证错误信息
									    	}
									    	
									    },
										error:function(XMLHttpRequest, textStatus, errorThrown){
											//alert("进入error！");
											alert(XMLHttpRequest.status);
							                alert(XMLHttpRequest.readyState);
							                alert(textStatus);
									    }
									}
											);
									
								}
										
								);
$('#id2').keyup(
		function(){
			$('#id_info').empty();//清空消息
			//alert("提交表单事件！");
			var account=new Object();
			account.id=$("#id2").val();
			var info=JSON.stringify(account);
			$.ajax({
				type:'post',
				contentType:'application/json',
				url:'/yzwish/login/checkAccountExists',
				data:info,	
				beforeSend:function(){
					//处理发送之前的检查事务。
					//alert("发送之前！");
					var re =  /^[0-9a-zA-Z]*$/g;//字母数字组合
					if(document.getElementById("id2").value=="")  
				    {  
						$('#id_info').html('用户名不能为空');  
				        return false;  
				    } 
					//字母和数字组合
				    else if(!re.test(document.getElementById("id2").value))
				    {
				    	$('#id_info').html('用户名只能由数字和字母组成');  
				    	return false;
				    }
				    else if(document.getElementById("id2").value.length<5)
				    {
				    	$('#id_info').html('用户名长度至少为5位');  
				    	return false;
				    }
				},										
			    success:function(data){
			    	//alert("返回页面");
			    	var msg=document.getElementById("msg");
			    	console.log(data[0]);
			    	var res = $.parseJSON(data);									    	
			    	if(res[0].msg=='1')									    	
			    		$('#id_info').html('该用户名可以注册');  
			    	//用户名密码验证成功
			    	else if(res[0].msg=='0'){
			    		$('#id_info').html('该用户名已注册');  
			    	}
			    	else if(res[0].msg=='3'){
			    		$('#id_info').html('服务器错误');  
			    	}
			    	
			    },
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert("进入error！");
					alert(XMLHttpRequest.status);
	                alert(XMLHttpRequest.readyState);
	                alert(textStatus);
			    }
			}
					);
			
		}
				
		);
$('#password2').keyup(
		function(){
			$('#password_info').empty();
			$('#password2_info').empty();
			if(document.getElementById("password2").value!=document.getElementById("password3").value){
				$('#password2_info').html('两次输入密码不同');
			}
			else{
				$('#password2_info').html('');
			}
			if(document.getElementById("password2").value.length<5){
				$('#password_info').html('密码长度必须大于5');
			}		
		}						
);
$('#password3').keyup(
		function(){
			$('#password2_info').empty();
			if(document.getElementById("password2").value!=document.getElementById("password3").value){
				$('#password2_info').html('两次输入密码不同');
			}
			else{
				$('#password2_info').html('');
			}
		}						
);
$('#safetyAnswer').keyup(
		function(){
			$('#sa_info').empty();
			if($('#safetyAnswer').val()==''){
				$('#sa_info').html('密保答案不能为空');
			}
			else{
				$('#sa_info').html('');
			}
		}						
);
$('#sYear').keyup(
		function(){
			$('#syear_info').empty();
			var re =  /^[0-9]*$/g;//数字组合
			var date=new Date;
			var year=date.getFullYear(); 
			if($('#sYear').val().length==0){
				$('#syear_info').html('不能为空');
			}
			else if((!re.test($('#sYear').val()))){
				$('#syear_info').html('请输入四位整数');
			}
			else if($('#sYear').val().length<4){
				$('#syear_info').html('请输入四位整数');
			}
			else if($('#sYear').val()<1901||$('#sYear').val()>year.toString()){
				$('#syear_info').html('年份范围:1901-'+year.toString());
			}
			else{
				$('#syear_info').html('');
			}
		}						
);
$('#position').keyup(
		function(){
			$('#position_info').empty();
			if($('#position').val()==''){
				$('#position_info').html('不能为空');
			}
			else{
				$('#position_info').html('');
			}
		}						
);
$('#resDirection').keyup(
		function(){
			$('#rd_info').empty();
			if($('#resDirection').val()==''){
				$('#rd_info').html('不能为空');
			}
			else{
				$('#rd_info').html('');
			}
		}						
);
$('#realName').keyup(
		function(){
			$('#rn_info').empty();
			if($('#realName').val()==''){
				$('#rn_info').html('不能为空');
			}
			else{
				$('#rn_info').html('');
			}
		}						
);
(function(){
$("#pchs_pc").change(function(){  
    var provinceId = $("#pchs_pc").val();  
    $("#pchs_hs").html(pc[provinceId]);
});
})();
(function(){
	$("#pum_p,#pc_p").change(function(){  
	    var provinceId = $("#pc_p").val();  
	    var provinceId2 = $("#pum_p").val();
	    $("#pc_c").html(pc_t[provinceId]);
	    $("#pum_u").html(pc_t[provinceId2]);
	    $("#pum_info").html('');
	    if(provinceId2!='unknown'){
	    	 getMajor();
	    }
	    else{
	    	$("#pumm").css("display",'none');
	        $("#pum_info").html('不能为空');
	    }
	   
	   
	});
	$("#pum_u").change(function(){  	   
	    getMajor();	  
	    $("#pum_info").html('');
	});
})();
(function(){
	$("#sex").change(function(){  
		  if (document.getElementById("r1").checked || document.getElementById("r2").checked) { 
			  var sex_info=document.getElementById("sex_info");
				 if($('#sex').val()=='unknown')				        	
		     		sex_info.style.display='block';
		     	else{
		     		sex_info.style.display='none';
		     	}
		  }
		 
	});
})();
});
  