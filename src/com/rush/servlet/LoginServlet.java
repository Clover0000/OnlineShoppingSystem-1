//���һ��servletֻ����һ�߼�����û��Ҫ��type����
//���servlet���ڼ����û�е�¼�������Բ鿴session����û���û���¼��Ϣ
package com.rush.servlet;

import com.rush.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String type=request.getParameter("type");
		UserBean ub=(UserBean)request.getSession().getAttribute("userInfo");
//=================================1. ����ǰ�����Ͻ�================================
		if(type.equals("new")){
			if(ub==null){		//˵���û�û�е�¼
				request.getRequestDispatcher("preLogin.jsp").forward(request, response);
			}else{	//Session���Ѿ����ˣ�˵����¼����
				request.getRequestDispatcher("index.jsp?state=logged").forward(request, response);
			}
//******************************************************************************************************			
//=================================2. ������(�������)==============================
		
		}else if(type.equals("normal")){
			
			if(ub==null){
				//˵���û�û�е�¼������ת��login.jsp
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}else{
				//2. �ѹ��ﳵ����Ϣȡ������׼��������һ��ҳ����ʾ
				MyCartBO mcb=(MyCartBO)request.getSession().getAttribute("mycart");
				ArrayList al=mcb.showMyCart();
				request.setAttribute("mycartInfo",al);  //��Ϊ������Ϣ̫�󣬲��ʺϷ���Session������request����
				request.getRequestDispatcher("order.jsp").forward(request,response);
				
			}
		}
	}
//******************************************************************************************************

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request,response);

	}

}
