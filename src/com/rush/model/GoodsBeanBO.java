//������goods����ص�ҵ���߼�
package com.rush.model;
import java.sql.*;
import java.util.*;


public class GoodsBeanBO {
	//����һЩ����
	private Connection ct=null;
	private ResultSet rs=null;
	private PreparedStatement ps=null; //�����ǹ�ȥʱ
//=======================������Ʒ�Ļ���id���õ�������Ϣ�ĺ���=====================
	public GoodsBean getGoodsBean(String id){
		GoodsBean gb=new GoodsBean();
		try{
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement("select * from goods where goodsId=?");//���ﲻ�ǹ�ȥʽ
			ps.setString(1, id);//ps������������ã���
			rs=ps.executeQuery();
			if(rs.next()){  //������δ��������
				gb.setGoodsId(rs.getInt(1));
				gb.setGoodsName(rs.getString(2));
				gb.setGoodsIntro(rs.getString(3));
				gb.setGoodsPrice(rs.getFloat(4));
				gb.setGoodsNum(rs.getInt(5));
				gb.setPublisher(rs.getString(6));
				gb.setPhoto(rs.getString(7));
				gb.setType(rs.getString(8));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close();
		}
		return gb;
	}
//******************************************************************************************************
	
//=============================�����ж���ҳ��======================================
	public int getPageCount(int pageSize){
		int pageCount=0;
		int rowCount=0;
		try {
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement("select count(*) from goods");
			rs=ps.executeQuery();
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
			if(rowCount%pageSize==0){
				pageCount=rowCount/pageSize;
			}else{
				pageCount=rowCount/pageSize+1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.close();
		}
		return pageCount;
	}
//******************************************************************************************************
	
//=============================��ҳ��ʾ������Ϣ====================================
	/*
	 * @parameter int pageSize: ÿҳ������¼
	 * @parameter int pageNow: ����Ҫ��ʾ�ڼ�ҳ
	 * @return ArrayList: ����Ҫ��ʾ�Ļ��� 
	 */
	public ArrayList getGoodsByPage(int pageSize, int pageNow){
		ArrayList al=new ArrayList();
		try {
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement("select top "+pageSize+" * from goods where goodsId not in (select top "+(pageNow-1)*pageSize+" goodsId from goods)");
			rs=ps.executeQuery();
			while(rs.next()){
				GoodsBean gb=new GoodsBean();
				gb.setGoodsId(rs.getInt(1));
				gb.setGoodsName(rs.getString(2));
				gb.setGoodsIntro(rs.getString(3));
				gb.setGoodsPrice(rs.getFloat(4));
				gb.setGoodsNum(rs.getInt(5));
				gb.setPublisher(rs.getString(6));
				gb.setPhoto(rs.getString(7));
				gb.setType(rs.getString(8));
				al.add(gb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.close();
		}
		return al;
	}
	
	
	
	
	
	
//*****************************************************************************************************	

	
//=====================================CLOSE=======================================
	public void close(){
		try{
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(ps!=null){
				ps.close();
				ps=null;
			}if(!ct.isClosed()){   //ct�����Լ����������
				ct.close();
				ct=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
//*****************************************************************************************************
	
	
	
}
