//Viewer����Controllerͨ����������Controller����Model�����ù�model����Ķ��󷽷�����
//Controller�ٵ���viewer��response.sendRedirect(..), ����request.getRequestDispatcher(...).forward(req,res);
package com.rush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rush.model.MyCartBO;

public class ShoppingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//�õ�typeֵ���ж��û���Ҫ�����Ĳ���
		String type=request.getParameter("type");
//-------------------------------�õ����ﳵ---------------------------------------
		//����MyCartBO,��ɹ���.  ��Ϊ���ﳵ�Ǹ��ֲ������õģ����Էŵ����
		//���ǲ�������д���ᵼ���ж�����ﳵ����Ϊsession��MyCartBO mcb=new MyCartBO();
		MyCartBO mcb=(MyCartBO)request.getSession().getAttribute("mycart");
		if(mcb==null){ //˵���û���һ�ι���,�򴴽�һ�����ﳵ�ŵ�session
			mcb=new MyCartBO();
			request.getSession().setAttribute("mycart", mcb);
		}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//һ��servlet�ж���������ò��������ж�
		if(type.equals("addGoods")){ //���
		
			//���Ҫ����Ļ����id
			String goodsId=request.getParameter("goodsId");
			
			//Ĭ�Ϲ���һ���飬֮������ٽ����޸�
			mcb.addGoods(goodsId, "1");
			
		}else if(type.equals("delGoods")){	//ɾ��
			String goodsId=request.getParameter("goodsId");
			mcb.delGoods(goodsId);
		}
		else if(type.equals("show")){	} //ʲô���������ù�������
		else if(type.equals("delAll")){  //ȫ��ɾ��
			mcb.clear();
		}else if(type.equals("updateGoods")){ //�û�ϣ���޸�����
			//��ô��servlet�еõ������id���µ��������ѵ㣩������ʹ�����ر��������ر����Դ��������
			String goodsId[]=request.getParameterValues("goodsId");//ע�⣡getParameterValues�õ���������
			String newNums[]=request.getParameterValues("newNums");
			for(int i=0; i<goodsId.length;i++){  //ѭ���޸�
				mcb.updateGoods(goodsId[i], newNums[i]);
			}
		}

//-------------------------------�ܶ������������δ���---------------------------
		//�ѹ��ﳵ�Ļ���ȡ������׼������һ��ҳ����ʾ
		ArrayList al=mcb.showMyCart();
		request.setAttribute("mycartinfo",al);
		request.getRequestDispatcher("showMyCart.jsp").forward(request,response);
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request,response);
	}

}
