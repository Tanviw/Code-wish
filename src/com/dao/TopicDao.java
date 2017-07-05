package com.dao;

import java.io.File;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.model.Answer;
import com.model.Follow;
import com.model.Question;
import com.model.Report;
import com.model.Topic;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TopicDao {

	//上传图片
	public static boolean uploadTopicPic(Topic topic){
		String topicBannerName=topic.getTopicBanner();
		String topicAvatarName=topic.getTopicAvatar();
		String uploadPath="D:\\javaeepro\\yzwish\\WebContent\\topicImages\\";
		File targetFile1 = new File(uploadPath,topicBannerName);  
		File targetFile2 = new File(uploadPath,topicAvatarName);  
        if(!targetFile1.exists()){  
            targetFile1.mkdirs();  
        }  
        if(!targetFile2.exists()){  
            targetFile2.mkdirs();  
        }  
        //保存  
        try {  
            topic.getBannerPic().transferTo(targetFile1);  
            topic.getAvatarPic().transferTo(targetFile2);  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
		
	}
	
	
	//发起话题
	public static String createTopic(Topic topic,String userId){
		
		String sql2="SELECT * FROM topic WHERE topicName=?";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		try {
			ptmt = conn.prepareStatement(sql2);	
			ptmt.setString(1, topic.getTopicName());
			rst = ptmt.executeQuery();	
    		//服务器错误
    		if(rst.next()){
    			return "topicName already exist!";
    		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//上传话题banner和头像
		if(!uploadTopicPic(topic)){
			return "error";
		}
			
        String sql="INSERT INTO topic VALUES (null,?,?,0,0,?,?,?)";
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setString(1, topic.getTopicName());
    		ptmt.setString(2, topic.getTopicDiscription());
    		ptmt.setString(3, topic.getTopicAvatar());
    		ptmt.setString(4, topic.getTopicBanner());
    		ptmt.setString(5,userId);
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			
    			return "ok";
    		}
    			
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
        
	}
	
	//搜索话题
	public static String searchTopic(Topic topic){

		String sql="SELECT topicId,topicName FROM topic WHERE topicState=1"
				+ " AND topicName LIKE ? LIMIT 15 ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setString(1,"%"+topic.getTopicName()+"%");
        	rst=ptmt.executeQuery();
        	JSONArray array=new JSONArray();
			java.sql.ResultSetMetaData metaData=rst.getMetaData();
			int columnCount =metaData.getColumnCount();
			while (rst.next()) {  
		        JSONObject jsonObj = new JSONObject();  
		         
		        // 遍历每一列  
		        for (int i = 1; i <= columnCount; i++) {  
		            String columnName =metaData.getColumnLabel(i);  
		            String value = rst.getString(columnName);  
						jsonObj.put(columnName, value);
		        }   
		        array.add(jsonObj);   
		    }  
		    
			return array.toString();
        	
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
	}
	
	//显示热门话题
	public static ArrayList<Topic> showHotTopic(){
		String sql="SELECT  topicId,topicName FROM  topic WHERE topicState=1 "
				+ " ORDER BY  followNumber DESC LIMIT  10";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		ArrayList<Topic> hotTopicList=new ArrayList<Topic>();
		Topic topic=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	rst=ptmt.executeQuery();
        	while(rst.next()){
        		topic=new Topic();
        		topic.setTopicId(rst.getInt("topicId"));
        		topic.setTopicName(rst.getString("topicName"));
        		hotTopicList.add(topic);
        		 
        	}
        	return hotTopicList;
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
	}
	
	//显示推荐话题
	public static ArrayList<Topic> showRecommTopic(String userId,int duty){
		String sql1 ="";
		String sql2 ="";
		String sql3 ="";
		if(duty==1){ 
			//心仪的省
			sql1 = "SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar  FROM topic WHERE topicState=1 AND "
					+ " topicName IN (SELECT provinceName FROM province WHERE"
					+ " provinceId IN (SELECT rId FROM volunschool"
					+ " WHERE id=? AND nameDuty =1 )) ;";
			//心仪的大学
			sql2 = "SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName IN (SELECT universityName FROM university WHERE"
					+ " universityId IN (SELECT rId FROM volunschool"
					+ " WHERE id=? AND nameDuty =2 )) ;";
			//心仪的专业
			sql3 = "SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName IN (SELECT majorName FROM major WHERE"
					+ " majorId IN (SELECT rId FROM volunschool"
					+ " WHERE id=? AND nameDuty =3 )) ;";
			
			
		}else if(duty==2){
			//大学生所在学校
			sql1 ="SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName=(SELECT universityName FROM university"
					+ " WHERE universityId =( SELECT universityId FROM collegestu WHERE id=?));";
			//大学生所学专业
			sql2="SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName=(SELECT majorName FROM major"
					+ " WHERE majorId =( SELECT majorId FROM collegestu WHERE id=?));";
		
		
		}else if(duty==3){
			//教师任职学校
			sql1 ="SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName=(SELECT universityName FROM university"
					+ " WHERE universityId =( SELECT universityId FROM teacher WHERE id=?));";
			//教师研究方向
			sql2 ="SELECT topicId,topicName,topicDiscription,followNumber,topicAvatar FROM topic WHERE topicState=1 AND "
					+ " topicName=( SELECT resDirection FROM teacher WHERE id=?));";
		
		
		}
		
			Connection conn = ConnectionManager.getInstance().getConnection();  
			PreparedStatement ptmt=null;
			ResultSet rst=null;
			ArrayList<Topic> recommtTopicList=new ArrayList<Topic>();
			Topic topic=null;
			
	        try{
	        	if(sql1!=""){
	        		
		        	ptmt = conn.prepareStatement(sql1);
		        	ptmt.setString(1,userId);
		        	rst=ptmt.executeQuery();
		        	while(rst.next()){
		        		topic=new Topic();
		        		topic.setTopicId(rst.getInt("topicId"));
		        		topic.setTopicName(rst.getString("topicName"));
		        		topic.setTopicDiscription(rst.getString("topicDiscription"));
		        		topic.setFollowNumber(rst.getInt("followNumber"));
		        		topic.setTopicAvatar(rst.getString("topicAvatar"));
		        		recommtTopicList.add(topic);
		        		 
		        	}
	        	}
	        	if(sql2!=""){
	        		
		        	ptmt = conn.prepareStatement(sql2);
		        	ptmt.setString(1,userId);
		        	rst=ptmt.executeQuery();
		        	while(rst.next()){
		        		topic=new Topic();
		        		topic.setTopicId(rst.getInt("topicId"));
		        		topic.setTopicName(rst.getString("topicName"));
		        		topic.setTopicDiscription(rst.getString("topicDiscription"));
		        		topic.setFollowNumber(rst.getInt("followNumber"));
		        		topic.setTopicAvatar(rst.getString("topicAvatar"));
		        		recommtTopicList.add(topic);
		        		 
		        	}
	        	}
	        	if(sql3!=""){
	        		ptmt = conn.prepareStatement(sql3);
	        		ptmt.setString(1,userId);
		        	rst=ptmt.executeQuery();
		        	while(rst.next()){
		        		topic=new Topic();
		        		topic.setTopicId(rst.getInt("topicId"));
		        		topic.setTopicName(rst.getString("topicName"));
		        		topic.setTopicDiscription(rst.getString("topicDiscription"));
		        		topic.setFollowNumber(rst.getInt("followNumber"));
		        		topic.setTopicAvatar(rst.getString("topicAvatar"));
		        		recommtTopicList.add(topic);
		        		 
		        	}
	        	}
	        	return recommtTopicList;
	        }catch (SQLException e) {
				e.printStackTrace();
				return null;
	        }finally{
	        	ConnectionManager.close(conn,rst,ptmt);

	        }
		
		
		
		
		
	}
	
	//显示话题主页
	public static Topic showTopic(int topicId){

		String sql="SELECT topicName,topicBanner,topicAvatar,followNumber FROM topic WHERE "
				+ "topicId=?";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		Topic topic=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setInt(1,topicId);
        	rst=ptmt.executeQuery();
        	if(rst.next()){
        		topic=new Topic();
        		topic.setTopicName(rst.getString("topicName"));
        		topic.setTopicBanner(rst.getString("topicBanner"));
        		topic.setTopicAvatar(rst.getString("topicAvatar"));
        		topic.setFollowNumber(rst.getInt("followNumber"));
        		topic.setTopicId(topicId);
        		return topic;
        		 
        	}else{
        		return null;
        	}
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
		
	}
	
	//显示问题列表
	public static ArrayList<Question> showQuestionList(int topicId){
		String sql="SELECT questionId,questionTitle,answerCount,userId,lastAnswerTime FROM question WHERE topicId=?"
				+ " ORDER BY lastAnswerTime DESC ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		ArrayList<Question> quesList=new ArrayList<Question>();
		Question question=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setInt(1,topicId);
        	rst=ptmt.executeQuery();
        	while(rst.next()){
        		question=new Question();
        		question.setQuestionId(rst.getInt("questionId"));
        		question.setQuesTitle(rst.getString("questionTitle"));
        		question.setAnswerCount(rst.getInt("answerCount"));
        		question.setUserId(rst.getString("userId"));
        		question.setLastAnswerTime(rst.getTimestamp("lastAnswerTime"));
        		quesList.add(question);
        		 
        	}
        	return quesList;
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
		
	}
	
	//发布问题
	public static String createQuestion(Question question){
		String sql="INSERT INTO question VALUES (null,?,?,?,now(),0,0,now(),?)";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setInt(1, question.getTopicId());
    		ptmt.setString(2,question.getQuesTitle());
    		ptmt.setString(3, question.getQuesContent());
    		ptmt.setString(4,question.getUserId());
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			
    			return "ok";
    		}
    			
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,ptmt);

        }
	}
	//显示问题信息
	public static Map<String, Question> showQuestion(int questionId){
		String sql="SELECT topicName,topic.topicId,questionId,questionTitle,questionContent,userId,quesCreateTime FROM"
				+ " topic INNER JOIN question ON topic.topicId=question.topicId WHERE "
				+ "questionId=?";
		Map<String, Question> map=new HashMap<String, Question>();
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		Question question=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setInt(1,questionId);
        	rst=ptmt.executeQuery();
        	if(rst.next()){
        		question=new Question();
        		question.setQuestionId(rst.getInt("questionId"));
        		question.setTopicId(rst.getInt("topicId"));
        		question.setQuesTitle(rst.getString("questionTitle"));
        		question.setUserId(rst.getString("userId"));
        		question.setQuesContent(rst.getString("questionContent"));
        		question.setQuestionCreateTime(rst.getTimestamp("quesCreateTime"));
        		map.put(rst.getString("topicName"),question);
        		
        	}
        	return map;
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
		
	}
	
	//显示问题回复
	public static ArrayList<Answer> showQA(int questionId){
		String sql="SELECT * FROM answer WHERE questionId=? ORDER BY answerApprovalNum DESC ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		ArrayList<Answer> answerList=new ArrayList<Answer>();
		Answer answer=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setInt(1,questionId);
        	rst=ptmt.executeQuery();
        	while(rst.next()){
        		answer=new Answer();

        		answer.setAnswerId(rst.getInt("answerId"));
        		answer.setQuestionId(rst.getInt("questionId"));
        		answer.setAnswerContent(rst.getString("answerContent"));
        		answer.setAnswerUserId(rst.getString("answerUserId"));
        		answer.setAnswerTime(rst.getTimestamp("answerTime"));
        		answer.setAnswerApprovalNum(rst.getInt("answerApprovalNum"));
        		
        		answerList.add(answer);
        		 
        	}
        	return answerList;
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
		
		
		
	}
	
	
	//回答问题
	public static String answerQuestion(Answer answer,int duty){
		String sql="INSERT INTO answer VALUES (null,?,?,?,now(),0);";
		String sqlMess="INSERT INTO message VALUES (null,?,?,now(),0);";
		String messUserId=answer.getQuestionUserId();
		String context="您的提问有新的回答：<a href='/yzwish/topic/showQA?questionId="+answer.getQuestionId()+"' target='_blank'>"+
		answer.getQuestionTitle()+"</a>";
		String result="";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setInt(1, answer.getQuestionId());
    		ptmt.setString(2,answer.getAnswerContent());
    		ptmt.setString(3,answer.getAnswerUserId());
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			String sql2="UPDATE question SET answerCount=answerCount+1 ,lastAnswerTime=now() "
    					+ "WHERE questionId="+answer.getQuestionId();
    			ptmt = conn.prepareStatement(sql2);
    			int rs2 = ptmt.executeUpdate();	
    			if(rs2==0){
    				return "error";
    			}else{
    				if(duty==2||duty==3){
    					Date currentTime =new Date();
    				    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    				    String dateString = formatter.format(currentTime);
    					String sql3 = "SELECT SUM(virtualNum) AS totalCurrency FROM transrecord WHERE id=?  AND way =2 AND time"
    							+ " LIKE ? ;";
    					ptmt = conn.prepareStatement(sql3);
    					ptmt.setString(1,answer.getAnswerUserId());
    					ptmt.setString(2,"%"+dateString+"%");
    	    			rst = ptmt.executeQuery();	
    	    			if(rst.next()){
    	    				double num = rst.getDouble("totalCurrency");
    	    				//每月奖励虚拟币上限100
    	    				if(num>=100){
    	    					//通知提问用户
    	    					ptmt = conn.prepareStatement(sqlMess);
    	    					ptmt.setString(1,messUserId);
    	    					ptmt.setString(2,context);
    	    					int res1=ptmt.executeUpdate();
    	    					if(res1==0){
    	    						return "error";
    	    					}else{
    	    						
    	    						return "overflow";
    	    					}
    	    					
    	    				}
    	    				
    	    				
    	    			}
    	    				
    	    				String sql4 = "INSERT INTO  transrecord VALUES (null,?,now(),1,2)";
    	    				ptmt = conn.prepareStatement(sql4);
        					ptmt.setString(1,answer.getAnswerUserId());
        					
        	    			int rs3 = ptmt.executeUpdate();
        	    			if(rs3==0){
        	    				
        	    				return "error";
        	    			}else{
        	    				
        	    				//通知提问用户
        	    				ptmt = conn.prepareStatement(sqlMess);
    	    					ptmt.setString(1,messUserId);
    	    					ptmt.setString(2,context);
    	    					int res2=ptmt.executeUpdate();
    	    					if(res2==0){
    	    						return "error";
    	    					}else{
    	    						
    	    						return "award";
    	    					}
        	    				
        	    				
        	    			}
    	    			
    					
    				}else{
    					
    					//通知提问用户
    					ptmt = conn.prepareStatement(sqlMess);
    					ptmt.setString(1,messUserId);
    					ptmt.setString(2,context);
    					int res3=ptmt.executeUpdate();
    					if(res3==0){
    						return "error";
    					}else{
    						
    						return "ok";
    					}
    					
    					
    				}
    			
    			}
    		}
    			
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,ptmt);

        }
	}
	
	//搜索问题
	public static ArrayList<Question> searchQuestion(String searchWord,int thisTopicId){
		String sql="SELECT questionId,questionTitle,answerCount,userId,lastAnswerTime FROM question WHERE "
				+ "topicId=? AND questionTitle LIKE ? ORDER BY answerCount DESC ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		ArrayList<Question> quesList=new ArrayList<Question>();
		Question question=null;
        try{
        	ptmt = conn.prepareStatement(sql);
        	ptmt.setInt(1,thisTopicId);
        	ptmt.setString(2,"%"+searchWord+"%");
        	rst=ptmt.executeQuery();
        	while(rst.next()){
        		question=new Question();
        		question.setQuestionId(rst.getInt("questionId"));
        		question.setQuesTitle(rst.getString("questionTitle"));
        		question.setAnswerCount(rst.getInt("answerCount"));
        		question.setUserId(rst.getString("userId"));
        		question.setLastAnswerTime(rst.getTimestamp("lastAnswerTime"));
        		quesList.add(question);
        		 
        	}
        	return quesList;
        }catch (SQLException e) {
			e.printStackTrace();
			return null;
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
	}
	
	//关注
	public static String follow(Follow follow){
		String sql = "INSERT INTO follow VALUES (null,?,?,?,now()) ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		int type = follow.getFollowType();
		String followedId=follow.getFollowedId();
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setInt(1,type);
    		ptmt.setString(2,follow.getFollowUserId());
    		ptmt.setString(3,followedId);
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			String tableName="";
    			String columnName="";
    			
    			switch(type){
    			case 1:
    				tableName="question";
    				columnName="questionId";
    				
    				break;
    			case 2:
    				tableName="topic";
    				columnName="topicId";
    				
    				break;
    				
    			default:break;
    			}
    			String sql2 = "UPDATE "+tableName+" SET followNumber=followNumber+1 WHERE "+columnName
    					+" = '"+followedId+"';";
    			ptmt = conn.prepareStatement(sql2);		
    			
    			int rs2 = ptmt.executeUpdate();	
        		//服务器错误
        		if(rs2==0){
        			return "error";
        		}else{
        			return "ok";
        		}
    			
    			
    			
    		}
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,ptmt);

        }
	}
	//取消关注
	public static String cancleFollow(Follow follow){
		String sql = "DELETE FROM follow WHERE followType=? AND followUserId =? AND followedId=? ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		int type = follow.getFollowType();
		String followedId=follow.getFollowedId();
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setInt(1,type);
    		ptmt.setString(2,follow.getFollowUserId());
    		ptmt.setString(3,followedId);
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			String tableName="";
    			String columnName="";
    			
    			switch(type){
    			case 1:
    				tableName="question";
    				columnName="questionId";
    				
    				break;
    			case 2:
    				tableName="topic";
    				columnName="topicId";
    				
    				break;
    				
    			default:break;
    			}
    			String sql2 = "UPDATE "+tableName+" SET followNumber=followNumber-1 WHERE "+columnName
    					+" = '"+followedId+"';";
    			ptmt = conn.prepareStatement(sql2);		
    			
    			int rs2 = ptmt.executeUpdate();	
        		//服务器错误
        		if(rs2==0){
        			return "error";
        		}else{
        			return "ok";
        		}
    			
    			
    			
    		}
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,ptmt);

        }
	}
	
	
	
	//检查用户是否已关注
	public static String checkFollow(Follow follow){
		String sql="SELECT * FROM follow WHERE followUserId=? AND followType=? AND followedId=? ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		ResultSet rst=null;
		 try{
			ptmt = conn.prepareStatement(sql);
	    	ptmt.setString(1,follow.getFollowUserId());
	    	ptmt.setInt(2,follow.getFollowType());
	    	ptmt.setString(3,follow.getFollowedId());
	    	
	    	rst=ptmt.executeQuery();
	    	if(rst.next()){
	    		return "ok";
	    	}else{
	    		return "error";
	    	}
		 }catch (SQLException e) {
				e.printStackTrace();
				return "error";
        }finally{
        	ConnectionManager.close(conn,rst,ptmt);

        }
	}
	
	//举报
	public static String report(Report report){
		String sql = "INSERT INTO report VALUES (null,?,?,?,?,now(),0) ;";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		int type = report.getReportType();
		String reportedId=report.getReportedId();
        try{
        	ptmt = conn.prepareStatement(sql);				
    		ptmt.setInt(1,type);
    		ptmt.setString(2,report.getReporterId());
    		ptmt.setString(3,reportedId);
    		ptmt.setString(4,report.getReportReason());
    		int rs1 = ptmt.executeUpdate();	
    		//服务器错误
    		if(rs1==0){
    			return "error";
    		}else{
    			return "ok";
    			
    		}
        }catch (SQLException e) {
			e.printStackTrace();
			return "error";
        }finally{
        	ConnectionManager.close(conn,ptmt);

        }
	}
		
	//点赞
	public static String approveAns(int id){
		String sql="UPDATE answer SET answerApprovalNum=answerApprovalNum+1 WHERE answerId=?";
		Connection conn = ConnectionManager.getInstance().getConnection();  
		PreparedStatement ptmt=null;
		 try{
	        	ptmt = conn.prepareStatement(sql);				
	    		ptmt.setInt(1,id);
	    		int rs1 = ptmt.executeUpdate();	
	    		//服务器错误
	    		if(rs1==0){
	    			return "error";
	    		}else{
	    			return "ok";
	    			
	    		}
	        }catch (SQLException e) {
				e.printStackTrace();
				return "error";
	        }finally{
	        	ConnectionManager.close(conn,ptmt);

	        }
	}
}
