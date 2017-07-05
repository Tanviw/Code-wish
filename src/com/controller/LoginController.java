package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dao.UserDao;
import com.model.Account;
import com.model.CollegeStu;
import com.model.HighSchool;
import com.model.HighSchoolStu;
import com.model.Major;
import com.model.Teacher;
import com.model.University;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/login")
public class LoginController {

	
	//此函数使用ajax处理登录验证
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST) 
    public @ResponseBody String checkLogin(@RequestBody Account account,HttpServletRequest request,HttpSession session){  
       
		String from=account.getFrom();
		JSONArray array=new JSONArray();
		JSONObject jsonObj = new JSONObject();
		int result=UserDao.login(account);
		if(result==1)//成功
		{
			session.setAttribute("login_status", 1);//登录状态
			session.setAttribute("id", account.getId());
			session.setAttribute("duty", String.valueOf(account.getDuty()));
			if(from!=null)
            {
            	from=from.replace("&", "&amp;");
				try {
					from=URLEncoder.encode(from,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
		
		jsonObj.put("msg", result);
		jsonObj.put("from", from);
        array.add(jsonObj);   
        System.out.println(array.toString());
        return array.toString();	        
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET) 
    public ModelAndView logout(HttpSession session,HttpServletRequest request,String from){  
		System.out.println("进入logoutAction！");
		if(from!=null)
		{
		
			from=from.replace("&amp;", "&");
			try {
				from=URLEncoder.encode(from,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("from", from);
		System.out.println("from:"+from);
		
		//清除会话域状态
		if(session!=null)
		{
			System.out.println("清除此次会话");
			session.invalidate();
		}
		System.out.println("退出logoutAction！");
		return new ModelAndView("redirect:/jsp/index.jsp?from="+from);
	}
	
	@RequestMapping(value="/getHighSchoolList",method=RequestMethod.GET) 
    public @ResponseBody String getHighSchoolList(){  
        System.out.println("进入getHighSchoolList");
		JSONArray array=new JSONArray();
		HashMap<String,String> proMap=new HashMap<String,String>();
		HashMap<String,ArrayList<HighSchool>> hm=UserDao.getPCHS(proMap);
		Iterator iter = proMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String provinceId = (String) entry.getKey();
			String provinceName = (String) entry.getValue();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("provinceId", provinceId);
			jsonObj.put("provinceName",provinceName);
			JSONArray hsList=new JSONArray();//高中列表
			JSONObject hs = new JSONObject();
			ArrayList<HighSchool> temp_hsList=hm.get(provinceId);
			if(temp_hsList!=null){
				Iterator itor=temp_hsList.iterator();
				while(itor.hasNext()){
					HighSchool temp_hs=(HighSchool) itor.next();
					hs.put("hId",temp_hs.gethId());
					hs.put("hname", temp_hs.getHname());
					hsList.add(hs);
				}						
				jsonObj.put("highSchoolList", hsList);
		        array.add(jsonObj);
			}
			
		}
        System.out.println(array.toString());
        return array.toString();	        
	}
	@RequestMapping(value="/getCollegeList",method=RequestMethod.GET) 
    public @ResponseBody String getCollegeList(){  
        System.out.println("进入getCollegeList");
		JSONArray array=new JSONArray();
		HashMap<String,String> proMap=new HashMap<String,String>();
		HashMap<String,ArrayList<University>> hm=UserDao.getPC(proMap);
		Iterator iter = proMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String provinceId = (String) entry.getKey();
			String provinceName = (String) entry.getValue();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("provinceId", provinceId);
			jsonObj.put("provinceName",provinceName);
			JSONArray hsList=new JSONArray();//高中列表
			JSONObject hs = new JSONObject();
			ArrayList<University> temp_hsList=hm.get(provinceId);
			if(temp_hsList!=null){
				Iterator itor=temp_hsList.iterator();
				while(itor.hasNext()){
					University temp_hs=(University) itor.next();
					hs.put("universityId",temp_hs.getUniversityId());
					hs.put("universityname", temp_hs.getUniversityName());
					hsList.add(hs);
				}						
				jsonObj.put("cList", hsList);
				array.add(jsonObj);
			}			
	      
		}
        System.out.println(array.toString());
        return array.toString();	        
	}
	@RequestMapping(value="/getMajorList",method=RequestMethod.POST) 
    public @ResponseBody String getMajorList(@RequestBody University university){  
        System.out.println("进入getMajorList");
        System.out.println(university.getUniversityId());
		JSONArray array=new JSONArray();
		
		ArrayList<Major> mList=UserDao.getMajorList(university);
		Iterator itor=mList.iterator();
		while(itor.hasNext()){
			JSONObject jsonObj = new JSONObject();
			Major temp=(Major) itor.next();
			jsonObj.put("majorId", temp.getMajorId());
			jsonObj.put("majorName", temp.getMajorName());
			array.add(jsonObj);
		}		
        System.out.println(array.toString());
        return array.toString();	        
	}
	
	@RequestMapping(value="/checkAccountExists",method=RequestMethod.POST) 
    public @ResponseBody String checkAccountExists(@RequestBody Account account){  
        System.out.println("进入checkLogin");
		System.out.println(account.toString());
		JSONArray array=new JSONArray();
		JSONObject jsonObj = new JSONObject();
		int result=UserDao.hasAccount(account);		
		jsonObj.put("msg", result);
        array.add(jsonObj);   
        System.out.println(array.toString());
        return array.toString();	        
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST) 
    public ModelAndView register(Account ac,HighSchoolStu hsstu,CollegeStu cs,Teacher tea,
    		String from,HttpServletRequest request,HttpSession session){  	
        System.out.println("进入register");
    	System.out.println(ac.toString()+hsstu.toString()+tea.toString());
		int result=UserDao.register(ac,hsstu,cs,tea);
		if(result==1){
			session.setAttribute("login_status", 1);//登录状态
			session.setAttribute("id", ac.getId());
			session.setAttribute("duty", String.valueOf(ac.getDuty()));
			if(from!=null)
            {
            	from=from.replace("&", "&amp;");
				try {
					from=URLEncoder.encode(from,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
			request.setAttribute("from", from);
			System.out.println("from:"+from);
			return new ModelAndView("redirect:/jsp/index.jsp?from="+from);
		}
		else{
			request.setAttribute("result", "服务器错误");
			return new ModelAndView("/jsp/login");
		}
        	        
	}
}
