//������֤�û����������Ƿ���ȷ

package com.rush.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rush.model.MyCartBO;
import com.rush.model.UserBean;
import com.rush.model.UserBeanBO;

public class LoginValidator extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		//�õ��û��������룬��֤�û�
		String u=request.getParameter("username");
		String p=request.getParameter("password");
		//��ʼ��֤
		String type=request.getParameter("type");
		
//=====================================1. �ڹ���֮ǰ��¼=============================
		if(type.equals("pre")){	
		  UserBeanBO ubb=new UserBeanBO();
			if(ubb.checkUser(u, p)){
				//���浽session������
				UserBean ub=ubb.getUserBean(u);
				request.getSession().setAttribute("userInfo", ub);
				request.getRequestDispatcher("index.jsp?state=ok").forward(request,response);
			}else{
				request.getRequestDispatcher("index.jsp?state=error").forward(request,response);
			}
		}

//******************************************************************************************************
//=====================================2. �ڹ���֮���¼=============================
		else if(type.equals("normal")){
			UserBeanBO ubb=new UserBeanBO();
			if(ubb.checkUser(u, p)){
				//1. ����Ҫ���û��ɹ���¼����Ϣ���浽session
				UserBean ub=ubb.getUserBean(u);
				request.getSession().setAttribute("userInfo", ub);
				//2. �ѹ��ﳵ����Ϣȡ������׼��������һ��ҳ����ʾ
				MyCartBO mcb=(MyCartBO)request.getSession()	.getAttribute("mycart");
				ArrayList al=mcb.showMyCart();
				request.setAttribute("mycartInfo",al);  //��Ϊ������Ϣ̫�󣬲��ʺϷ���Session������request����
				
				request.getRequestDispatcher("order.jsp").forward(request,response);
			}else{
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}
//===============================3. ��order.jsp alter�û���Ϣ=============================			
		}else if(type.equals("alter")){
			UserBeanBO ubb=new UserBeanBO();
			
			String username=request.getParameter("username");
			String truename=request.getParameter("truename");
			String address=request.getParameter("address");
			String phone=request.getParameter("phone");
			String email=request.getParameter("email");
			String postcode=request.getParameter("postcode");
			String id=request.getParameter("id");
			String[] alteredUsers={username,truename,address,phone,email,postcode,id};
			if(ubb.updateUser(alteredUsers)){
				UserBean ub=ubb.getUserBean(username);
				request.getSession().setAttribute("userInfo", ub);
				MyCartBO mcb=(MyCartBO)request.getSession()	.getAttribute("mycart");
				ArrayList al=mcb.showMyCart();
				request.setAttribute("mycartInfo",al); 
				request.getRequestDispatcher("order.jsp?type=altered").forward(request,response);
			}
			
			
			
		}
		
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
