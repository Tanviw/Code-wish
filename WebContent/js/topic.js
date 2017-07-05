function searchTopic() {
    var searchValue = $("#search-topic").val();
    if(searchValue!=""){
    	var valueLen=searchValue.length;
    	
    	if(valueLen>50){
    		alert("搜索内容长度不能超过50！");
    		return false;
    	}
	    var topic=new Object();
		topic.topicName=searchValue;
		var info=JSON.stringify(topic);
	     $.ajax({
	            type:"POST",
	            contentType:'application/json',
	            url:"/yzwish/topic/searchTopic",
	            data:info,
	            success:function(data,status) {
	                if(data!="error"){
	                	if(data!="[]"){
	                		var res = $.parseJSON(data);
	                		var resList="";
	                    	for(var i=0;i<res.length;i++){
	                    		resList+="<li><a href='/yzwish/topic/showTopic?topicId="+res[i].topicId+"' target='_blank'>"+res[i].topicName+"</a></li>";
	                    	}
	                    	$("#search-res-list").html(resList);
	                	}else{
	                		$("#search-res-list").html("");
	                	}
	                
	                           	
	                }else{
	                	alert("服务器出错啦");
	                }
	            },
	            error: function() {
	                alert("查找失败");
	            }
	        });
    }else{
    	$("#search-res-list").html("");
    }
}


function  checkPostTopic(){
	var flag=false;
	$("#topicName-hint").html("");
	$("#topicDiscription-hint").html("");
	$("#topicBanner-hint").html("");
	$("#topicAvatar-hint").html("");
	//验证是否登录
	$.ajax({
        type: "GET",
        async:false,
        url: "/yzwish/topic/loginVerify",
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
            
        }
    });
	if(flag==false){
		return false;
	}else{
		
		var topicName = $("#topicName").val();
		var topicDiscription = $("#topicDiscription").val();
		var bannerPic = $("#bannerPic").val();
		var avatarPic = $("#avatarPic").val();
		 
		if(topicName == ""){
			$("#topicName-hint").html("话题名称不能为空");
			return false;
		}else if(topicDiscription == ""){
			$("#topicDiscription-hint").html("话题简介不能为空");
			return false;
		}else if(bannerPic == ""){
			$("#topicBanner-hint").html("话题海报不能为空");
			return false;
		}else if(avatarPic == ""){
			$("#topicAvatar-hint").html("话题头像不能为空");
			return false;
		}
		
		var len1 = topicName.length;
		var len2 = topicDiscription.length;
		var len3 = bannerPic.length;
		var len4 = avatarPic.length;
		
		if(len1 > 50){
			$("#topicName-hint").html("话题名称长度不能超过50");
			return false;
		}else if(len2 > 100){
			$("#topicDiscription-hint").html("话题简介长度不能超过100");
			return false;
		}else if(len3 > 900){
			$("#topicBanner-hint").html("话题海报名称长度不能超过900");
			return false;
		}else if (len4 > 900){
			$("#topicAvatar-hint").html("话题头像名称长度不能超过900");
			return false;
		}else{
			
			return true;
		}
	}
	
}

function createQuestion() {
	
	var flag=false;
	$("#quesTitleHint").html("");
    $("#quesContHint").html("");
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
	    var questionTitle = $("#questionTitle").val();
	    var questionContent = $("#questionContent").val();
	    
	    if (questionTitle == "") {
	        $("#quesTitleHint").html("标题不能为空");
	        return false;
	    } else if (questionContent == "") {
	        $("#quesContHint").html("内容不能为空");
	        return false;
	    }
		
	    var len1 = questionTitle.length;
	
			if(len1 > 100){
				$("#quesTitleHint").html("标题长度不能超过100");
				return false;
		    }
		    else{
		    	var len2 = questionContent.length;
	
		    		if(len2 > 200){
		    			$("#quesContHint").html("内容长度不能超过200");
		    			return false;
		    		}else{
		    			return true;
		    		}
		    }
			
	}
}

function answerQuestion(){
	var flag=false;
	$("#answer-hint").html("");
	 $.ajax({
	        type: "GET",
	        async:false,
	        url: "/yzwish/topic/loginVerify",
	        data: {},
	        success: function(data, status) {
	            if (data == "error") {
	                window.location.href = "/yzwish/jsp/login.jsp";
	                
	            }else{
	            	flag=true;
	            }
	        },
	        error: function() {
	            alert("验证失败");
	           
	        }
	    });
	 if(flag==false){
			return false;
	}else{
	 
		 var answerContent=$("#answerContent").val();
		 if(answerContent == ""){
			 $("#answer-hint").html("回复内容不能为空");
			 return false;
		 }else if(answerContent.length>200){
			 $("#answer-hint").html("回复内容长度不能超过200");
			 return false;
		 }
			 
			 
			    var answer = new Object();
			    var thisQuesId=$("#questionId").val();
			   answer.answerUserId=$("#answerUserId").val();
			   answer.questionId=thisQuesId;
			   answer.answerContent=$("#answerContent").val();
			   answer.questionUserId=$("#questionUserId").html();
			   answer.questionTitle=$("#question-title-for-mess").html();
			    var info = JSON.stringify(answer);
				$.ajax({
			        type: "POST",
			        contentType:'application/json',
			        url: "/yzwish/topic/answerQuestion",
			        data: info,
			        success: function(data, status) {
			            if (data == "error") {
			                alert("服务器出错啦")
			                
			            }else{
			            	if(data == "award"){
			            		alert("回答成功！奖励1虚拟币~");
			            	}else if(data == "overflow"){
				            	alert("回答成功！本月奖励虚拟币数量已达上限100~再接再厉哦");
				            }
			            	 window.location.href = "/yzwish/topic/showQA?questionId="+thisQuesId;
			            	 
			            }
			        },
			        error: function() {
			            alert("回答失败");
			        }
			    });
			
		 }
	
}

function checkSearch(){
	var searchWord=$("#searchWord").val();
	if($.trim(searchWord)==""){
		return false;
	}else if(searchWord.length>100){
		alert("搜索内容长度不能超过100！");
		return false;
	}else{
		
		return true;
	}
	

}


function followTopic(){

	var flag=false;
     
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var topicId=$("#topicId").val();
	    var userId=$("#userId").val();
	    var follow = new Object();
	    follow.followedId = topicId;
	    follow.followUserId = userId;
	    follow.followType = 2;
	    var info = JSON.stringify(follow);
		$.ajax({
	        type: "POST",
	        contentType:'application/json',
	        url: "/yzwish/topic/follow",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	var followItem='<button type="button" class="btn" onclick="cancelFollowTopic()" style="font-size: 16px;">'
					+'<span class="fa fa-check">已关注</span></button>';
	            	$("#followTopic").html(followItem);
	            	var fn=Number($("#follow-number").html());
	            	fn=fn+1;
	            	$("#follow-number").html(fn);
	            }
	        },
	        error: function() {
	            alert("关注失败");
	        }
	    });
	}
}


function cancelFollowTopic(){


	var topicId=$("#topicId").val();
    var userId=$("#userId").val();
    var follow = new Object();
    follow.followedId = topicId;
    follow.followUserId = userId;
    follow.followType = 2;
    var info = JSON.stringify(follow);
	$.ajax({
        type: "POST",
        contentType:'application/json',
        url: "/yzwish/topic/cancleFollow",
        data: info,
        success: function(data, status) {
            if (data == "error") {
                alert("服务器出错啦")
                
            }else{
            	 var flwc='<button type="button" class="btn btn-primary" onclick="followTopic()" '
 					+'style="font-size: 16px;"><span  class=" glyphicon glyphicon-plus" '
 					+'>关注</span></button>';
 				
 	                $("#followTopic").html(flwc);
            
            	var fn=Number($("#follow-number").html());
            	fn=fn-1;
            	$("#follow-number").html(fn);
            }
        },
        error: function() {
            alert("取关失败");
        }
    });
	
}


function followQuestion(){

	var flag=false;
     
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var questionId=$("#questionId").val();
	    var userId=$("#answerUserId").val();
	    var follow = new Object();
	    follow.followedId = questionId;
	    follow.followUserId = userId;
	    follow.followType = 1;
	    var info = JSON.stringify(follow);
		$.ajax({
	        type: "POST",
	        contentType:'application/json',
	        url: "/yzwish/topic/follow",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	var followQuesItem='<button type="button" onclick="cancelFollowQues()" class="btn fa fa-check"  style="margin-top:20px;">'
					+'已关注</button>';
	            	$("#followQuesItem").html(followQuesItem);
	            	
	            }
	        },
	        error: function() {
	            alert("关注失败");
	        }
	    });
	}
}

function cancelFollowQues(){

	
		var questionId=$("#questionId").val();
	    var userId=$("#answerUserId").val();
	    var follow = new Object();
	    follow.followedId = questionId;
	    follow.followUserId = userId;
	    follow.followType = 1;
	    var info = JSON.stringify(follow);
		$.ajax({
	        type: "POST",
	        contentType:'application/json',
	        url: "/yzwish/topic/cancleFollow",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	var fic = '<button type="button" class="btn btn-primary"'
						+ 'style="margin-top: 20px;"  onclick="followQuestion()">关注问题</button>';
	            	$("#followQuesItem").html(fic);
	            }
	        },
	        error: function() {
	            alert("取关失败");
	        }
	    });
	
}


function reportQuestion(){
	
	var flag=false;
     
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var questionId=$("#questionId").val();
	    var userId=$("#answerUserId").val();
	    var checkValue = $("input[name='reportReasons']:checked").val();
	    
	    var report = new Object();
	    report.reportReason = checkValue;
	    report.reportType = 1;
	    report.reporterId = userId;
	    report.reportedId = questionId;
	    var info = JSON.stringify(report);
		$.ajax({
	        type: "POST",
	        async:false,
	        contentType:'application/json',
	        url: "/yzwish/topic/report",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	alert("举报成功！请等待管理员审核..");
	            	
	            }
	        },
	        error: function() {
	            alert("举报失败");
	          
	          
 		 	
	        }
	    });
		 $('#modal-container-report').modal("hide");
 		 $(".modal-backdrop").remove();
		
	}
}


function reportAnswer(e){
	var flag=false;
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var answerId=$(e).parent().find('input:eq(0)').val();
		console.log(answerId);
	    var userId=$("#answerUserId").val();
	    var checkValue = $("input[name='reportReasons-"+answerId+"']:checked").val();
	    
	    var report = new Object();
	    report.reportReason = checkValue;
	    report.reportType = 3;
	    report.reporterId = userId;
	    report.reportedId = answerId;
	    var info = JSON.stringify(report);
		$.ajax({
	        type: "POST",
	        async:false,
	        contentType:'application/json',
	        url: "/yzwish/topic/report",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	alert("举报成功！请等待管理员审核..");
	            	
	            }
	        },
	        error: function() {
	            alert("举报失败");
	          
	          
 		 	
	        }
	    });
		 $("#modal-container-report-ans-"+answerId).modal("hide");
 		 $(".modal-backdrop").remove();
		
	}
}



function reportTopic(){
	var flag=false;
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var topicId=$("#topicId").val();
	    var userId=$("#userId").val();
	    var checkValue = $("input[name='reportReasons']:checked").val();
	    
	    var report = new Object();
	    report.reportReason = checkValue;
	    report.reportType = 2;
	    report.reporterId = userId;
	    report.reportedId = topicId;
	    var info = JSON.stringify(report);
		$.ajax({
	        type: "POST",
	        async:false,
	        contentType:'application/json',
	        url: "/yzwish/topic/report",
	        data: info,
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	alert("举报成功！请等待管理员审核..");
	            	
	            }
	        },
	        error: function() {
	            alert("举报失败");
	          
	          
 		 	
	        }
	    });
		 $('#modal-container-report-topic').modal("hide");
 		 $(".modal-backdrop").remove();
		
	}
}


function approvalAns(id){
var flag=false;
    
    $.ajax({
        type: "GET",
        url: "/yzwish/topic/loginVerify",
        async:false,
        data: {},
        success: function(data, status) {
            if (data == "error") {
                window.location.href = "/yzwish/jsp/login.jsp";
                
            }else{
            	flag=true;
            }
        },
        error: function() {
            alert("验证失败");
        }
    });
    if(flag==false){
		return false;
	}else{
		var answerId=id;
		$.ajax({
	        type: "POST",
	        url: "/yzwish/topic/approveAns",
	        data:
	        	{"answerId":answerId},
	        success: function(data, status) {
	            if (data == "error") {
	                alert("服务器出错啦")
	                
	            }else{
	            	var num=Number($("#approvalNum-"+id).html());
	            	num=num+1;
	            	$("#approvalNum-"+id).html(num);
	            	
	            }
	        },
	        error: function() {
	            alert("点赞失败");
	          
	          
 		 	
	        }
	    });
	}
}