//���servlet�������ǣ�����ҳ�õ�һ����Ʒ��id������BO������������Ʒ��Ϣ����ϸҳ��
package com.rush.servlet;
import java.io.IOException;
import com.rush.model.*;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowGoodsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//���Ҫ��ʾ���ľ���Ҫ����һ�仰��
		response.setCharacterEncoding("utf-8");
		//�õ�type���ж��û������ҳ��������ʾ�������ϸ��Ϣ
		String type=request.getParameter("type");
		if(type.equals("showDetail")){
			//�õ�Ҫ��ʾ�����id
			String goodsId=request.getParameter("id");//����reqest���յ��Ķ���string
			//����Bo(���԰����õ���Ʒ�ľ�����Ϣ��
			GoodsBean gb=new GoodsBeanBO().getGoodsBean(goodsId);
			//��gbb�ŵ�request
			request.setAttribute("goodsInfo", gb);
			//��ת������û��response.sendRedirect(url?x=...)
			request.getRequestDispatcher("showDetail.jsp").forward(request, response);
		}else if(type.equals("page")){
			//�õ�pageNow
			String pageNow=request.getParameter("pageNow");
			//���ع����������pageNow˳�����request
			request.setAttribute("changedPage", pageNow);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
