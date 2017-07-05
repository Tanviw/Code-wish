package com.dao;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.model.Account;
import com.model.CollegeStu;
import com.model.HighSchool;
import com.model.HighSchoolStu;
import com.model.Major;
import com.model.Teacher;
import com.model.University;

public class UserDao {

	//登录
	public static int login(Account account){
		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {
			
	    	String sql="select * from Account where id='"+account.getId()+"' "
	    			+"and password='"+account.getPassword()+"' ";
	    	rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	    	
	    	if(rs==null||!rs.next())
	    	{
				return 0;//失败
			}
			else{
				account.setDuty(rs.getInt(3));
				return 1;//成功
			}
	    	
	    			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}

	//得到省/市和高中
	public static HashMap<String,ArrayList<HighSchool>> getPCHS(HashMap<String,String> proMap){
		
		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {	
			synchronized(UserDao.class){
		    	String sql="select B.provinceId,A.hId,A.hname,B.provinceName from highschool A,province B where A.provinceId= B.provinceId ";
		    	rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	  
		    	HashMap<String,ArrayList<HighSchool>> hm=new HashMap<String,ArrayList<HighSchool>>();
		    	while(rs.next()){
		    		proMap.put(rs.getString(1), rs.getString(4));
		    		HighSchool hs=new HighSchool(rs.getString(2),rs.getString(3));
		    		ArrayList<HighSchool> hsList=hm.get(rs.getString(1));
		    		if(hsList==null||hsList.isEmpty()){
		    			hsList=new ArrayList<HighSchool>();
		    		}
		    		hsList.add(hs);
	    			hm.put(rs.getString(1), hsList);
		    	}
		    	
		    	return hm;
			}
			
	    			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}
	//得到省/市和大学
	public static HashMap<String,ArrayList<University>> getPC(HashMap<String,String> proMap){
		
		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {
			synchronized(UserDao.class){
		    	String sql="select B.provinceId,A.universityId,A.universityName,B.provinceName from university A,province B where A.provinceId= B.provinceId ";
		    	rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	  
		    	HashMap<String,ArrayList<University>> hm=new HashMap<String,ArrayList<University>>();
		    	while(rs.next()){
		    		proMap.put(rs.getString(1), rs.getString(4));
		    		University hs=new University(rs.getString(2),rs.getString(3));
		    		ArrayList<University> hsList=hm.get(rs.getString(1));
		    		if(hsList==null||hsList.isEmpty()){
		    			hsList=new ArrayList<University>();
		    		}
		    		hsList.add(hs);
	    			hm.put(rs.getString(1), hsList);
		    	}
		    	return hm;
			}
			
	    			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}
	
	//根据大学id得到专业列表
	public static ArrayList<Major> getMajorList(University us){
		
		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {
			synchronized(UserDao.class){
		    	String sql="select A.majorId,A.majorName from major A,university B,majorlog C "
		    			+ "where A.majorId = C.majorId and B.universityId = C.universityId "
		    			+ "and B.universityId= '"+us.getUniversityId()+"' ";
		    	rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	  
		    	ArrayList<Major> mList=new ArrayList<Major>();
		    	while(rs.next()){
		    		Major mj=new Major(rs.getString(1),rs.getString(2));
		    		mList.add(mj);
		    	}
		    	return mList;
			}				    			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}
		
	//检查账户是否存在
	public static int hasAccount(Account account){
		
		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {

			synchronized(UserDao.class){
		    	String sql="select * from Account where id='"+account.getId()+"' ";
		    	rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	    	
		    	if(rs==null||!rs.next())
		    	{
					return 1;//可以注册
				}
				else{
					return 0;//用户名存在
				}
		    	
			}
			
	    			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		} finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
    	

	}

	//注册
	public static int register(Account ac,HighSchoolStu hsstu,CollegeStu cs,Teacher tea){
		//记录初始id
		String init_id=ac.getId();
		//进行字符串转换
		ac.setId(changeSpecialSymbols(ac.getId()));
		ac.setPassword(changeSpecialSymbols(ac.getPassword()));
		
		
		Connection conn = ConnectionManager.getInstance().getConnection();  
        String sql=null;   
        try {
		
        	if(ac.getDuty()==1){
    			hsstu.setId(changeSpecialSymbols(hsstu.getId()));
    			hsstu.setSafetyAnswer(changeSpecialSymbols(hsstu.getSafetyAnswer()));			          
    			sql="insert into account values(?,?,?,?)";
        		PreparedStatement ptmt1 = conn.prepareStatement(sql);				
        		ptmt1.setString(1, ac.getId());
        		ptmt1.setString(2, ac.getPassword());
        		ptmt1.setInt(3, 1);
        		ptmt1.setInt(4, 0);  		
        		int rs1 = ptmt1.executeUpdate();	
        		//服务器错误
        		if(rs1==0)
        			return 3;
        		sql="insert into highschoolstu values(?,?,?,?,?,?,?)";
        		PreparedStatement ptmt2 = conn.prepareStatement(sql);				
        		ptmt2.setString(1, hsstu.getId());
        		if(!hsstu.getHsage().equals(""))        		
        			ptmt2.setInt(2, Integer.parseInt(hsstu.getHsage()));
        		else{
        			ptmt2.setInt(2,0);
        		}
        		if(hsstu.gethId().equals("unknown"))
        			hsstu.sethId(null);
        		ptmt2.setString(3,hsstu.gethId());
        		ptmt2.setInt(4,hsstu.getSafetyProblem());
        		ptmt2.setString(5, hsstu.getSafetyAnswer());
        		if(hsstu.getSex().equals("unknown"))
        			hsstu.setSex(null);
        		ptmt2.setString(6, hsstu.getSex());
        		ptmt2.setDouble(7, 0.0);   		
        		int rs2 = ptmt2.executeUpdate();	
        		//服务器错误
        		if(rs2==0)
        			return 3;
        		if(ptmt1!=null)
        			ptmt1.close();
        		if(ptmt2!=null)
        			ptmt2.close();
        		
    		}	
    		else if(ac.getDuty()==2){
    			cs.setId(changeSpecialSymbols(cs.getId()));
    			cs.setSafetyAnswer(changeSpecialSymbols(cs.getSafetyAnswer()));
    			sql="insert into account values(?,?,?,?)";
        		PreparedStatement ptmt1 = conn.prepareStatement(sql);				
        		ptmt1.setString(1, ac.getId());
        		ptmt1.setString(2, ac.getPassword());
        		ptmt1.setInt(3, 2);
        		ptmt1.setInt(4, 2);  		
        		int rs1 = ptmt1.executeUpdate();	
        		//服务器错误
        		if(rs1==0)
        			return 3;
        		sql="insert into collegestu values(?,?,?,?,?,?,?,?,?,?,?,?)";
        		PreparedStatement ptmt2 = conn.prepareStatement(sql);				
        		ptmt2.setString(1, cs.getId());
        		if(!cs.getHsage().equals(""))              		
        			ptmt2.setInt(2, cs.getAge());
        		else{
        			ptmt2.setInt(2, 0);
        		}
        		ptmt2.setString(3, cs.getSex());
        		ptmt2.setString(4, cs.getRealName());
        		ptmt2.setString(5, cs.getUniversityId());
        		ptmt2.setString(6, cs.getMajorId());
        		cs.setsYear(Integer.parseInt(cs.getSyear()));
        		ptmt2.setInt(7, cs.getsYear());
        		String fileName = cs.getPic().getOriginalFilename();  
        		fileName="verifiedImg/"+init_id+fileName.substring(fileName.indexOf("."),fileName.length());
        		ptmt2.setString(8,fileName);
        		ptmt2.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
        		ptmt2.setInt(10,cs.getSafetyProblem());
        		ptmt2.setString(11, cs.getSafetyAnswer());
        		ptmt2.setDouble(12, 0.0);   		
        		int rs2 = ptmt2.executeUpdate();	
        		//服务器错误
        		if(rs2==0)
        			return 3;
    			//上传图片
        		if(!uploadPic(ac,cs,tea,init_id))
        			return 3;
        		if(ptmt1!=null)
        			ptmt1.close();
        		if(ptmt2!=null)
        			ptmt2.close();
    		}
    		else if(ac.getDuty()==3){
    			tea.setId(changeSpecialSymbols(tea.getId()));
    			tea.setSafetyAnswer(changeSpecialSymbols(tea.getSafetyAnswer()));
    			sql="insert into account values(?,?,?,?)";
        		PreparedStatement ptmt1 = conn.prepareStatement(sql);				
        		ptmt1.setString(1, ac.getId());
        		ptmt1.setString(2, ac.getPassword());
        		ptmt1.setInt(3, 3);
        		ptmt1.setInt(4, 2);  		
        		int rs1 = ptmt1.executeUpdate();	
        		//服务器错误
        		if(rs1==0)
        			return 3;
        		sql="insert into teacher values(?,?,?,?,?,?,?,?,?,?,?,?)";
        		PreparedStatement ptmt2 = conn.prepareStatement(sql);				
        		ptmt2.setString(1, tea.getId());
        		if(!tea.getHsage().equals(""))              		
        			ptmt2.setInt(2, tea.getAge());
        		else{
        			ptmt2.setInt(2, 0);
        		}
        		ptmt2.setString(3, tea.getSex());
        		ptmt2.setString(4, tea.getRealName());
        		ptmt2.setString(5, tea.getUi());
        		ptmt2.setString(6, tea.getResDirection());
        		ptmt2.setString(7, tea.getPosition());
        		String fileName = tea.getPic().getOriginalFilename();  
        		fileName="verifiedImg/"+init_id+fileName.substring(fileName.indexOf("."),fileName.length());
        		ptmt2.setString(8,fileName);
        		ptmt2.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
        		ptmt2.setInt(10,tea.getSafetyProblem());
        		ptmt2.setString(11, tea.getSafetyAnswer());
        		ptmt2.setDouble(12, 0.0);   		
        		int rs2 = ptmt2.executeUpdate();	
        		//服务器错误
        		if(rs2==0)
        			return 3;
    			//上传图片
        		if(!uploadPic(ac,cs,tea,init_id))
        			return 3;
        		if(ptmt1!=null)
        			ptmt1.close();
        		if(ptmt2!=null)
        			ptmt2.close();
    		}
        	if(conn!=null){
        		conn.close();
        	}
    		return 1;
        }
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		}
		
				
}
	public static boolean uploadPic(Account ac,CollegeStu cs,Teacher tea,String init_id){
		
		String fileName;
		if(ac.getDuty()==2){
			fileName = cs.getPic().getOriginalFilename();  
    		fileName=init_id+fileName.substring(fileName.indexOf("."),fileName.length());
		}
		else if(ac.getDuty()==3){ 
			fileName = tea.getPic().getOriginalFilename();  
    		fileName=init_id+fileName.substring(fileName.indexOf("."),fileName.length());
		}
		else{
			return false;
		}
        String path=ConnectionManager.picUploadPath;
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            cs.getPic().transferTo(targetFile);  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
	}
	//将 '改为''
	public static String changeSpecialSymbols(String str){
		if(str !=null)		
			str=str.replaceAll("'", "''");
		return str;
	}
	
	public static void showBasicInfo(HighSchoolStu hss,CollegeStu cs,Teacher tea,String id,int duty){

		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {			
	    	String sql=null;
	    	if(duty==1){	    	
	    		sql="select B.sex,B.age,B.hId,C.provinceId from account A,highschoolstu B,highschool C "
	    				+ "where A.id=B.id and A.id='"+id+"' "
	    						+ "and B.hId=C.hId ";	    	
	    		rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	    		
		    	if(rs==null||!rs.next())
		    	{

				}
				else{
					hss.setSex(rs.getString(1));
					hss.setAge(rs.getInt(2));
					hss.sethId(rs.getString(3));
					hss.setProvinceId(rs.getString(4));
				}
	    		    
	    	}
	    	else if(duty==2){
	    		sql="select B.sex,B.age,A.status from account A,collegestu B where A.id=B.id and A.id='"+id+"' ";
	    		rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	    		
		    	if(rs==null||!rs.next())
		    	{

				}
				else{
					cs.setSex(rs.getString(1));
					cs.setAge(rs.getInt(2));
					cs.setStatus(rs.getInt(3));
				}
	    	}
	    	else if(duty==3){
	    		sql="select B.sex,B.age,A.status from account A,teacher B where A.id=B.id and A.id='"+id+"' ";
	    		rs=ConnectionManager.excuteSelect(sql,conn,ptmt,rs);	    		
		    	if(rs==null||!rs.next())
		    	{

				}
				else{
					tea.setSex(rs.getString(1));
					tea.setAge(rs.getInt(2));
					tea.setStatus(rs.getInt(3));
				}
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}
	//设置基础信息
	public static int setBasicInfo(String duty,String sex,String age,String hId,String id){

		Connection conn=ConnectionManager.getInstance().getConnection(); 
		PreparedStatement ptmt=null;
    	ResultSet rs=null;
		try {

	    	String sql=null;
	    	int tempage=0;
	    	if(sex.equals("unknown"))
	    		sex=null;
    		if(age!=null&&!age.equals(""))
    			tempage=Integer.parseInt(age);
	    	if(duty.equals("1")){
	    		
	    		if(hId.equals("unknown"))
	    			hId=null;
	    		if(sex==null&&hId!=null){
	    			sql="update highschoolstu set sex=null, age="+tempage
		    				+",hId='"+hId+"' where id= '"+id+"'";
	    		}
	    		else if(hId==null&&sex!=null){
	    			sql="update highschoolstu set sex= '"+sex
		    				+"', age="+tempage
		    				+",hId=null where id= '"+id+"'";
	    		}
	    		else if(hId==null&&sex==null)
	    			sql="update highschoolstu set sex=null, age="+tempage
    				+",hId=null where id= '"+id+"'";
	    		else{
	    			sql="update highschoolstu set sex= '"+sex
		    				+"', age="+tempage
		    				+",hId='"+hId+"' where id= '"+id+"'";
	    		}
	    	}
	    	else if(duty.equals("2")||duty.equals("3")){
	    		String[] roles={"","collegestu","teacher"};
	    		int tempduty=Integer.parseInt(duty);
	    		String role=roles[tempduty];
                //sex不可能为null
	    		sql="update "+role+" set sex= '"+sex
	    				+"', age="+tempage
	    				+" where id='"+id+"'";
	    	}
	    	if(sql!=null){
	    		ConnectionManager.excuteIUD(sql,conn,ptmt);
	    		
	    	}
	    	return 1;
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return 3;
		}finally{
			ConnectionManager.close(conn,rs,ptmt);
		}
	}
	
}
