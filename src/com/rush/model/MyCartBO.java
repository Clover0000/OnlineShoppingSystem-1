//���ڴ����ڹ�����ص�ҵ���߼�
package com.rush.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MyCartBO {
	//����һ��hashmap���ϣ����ڴ�����id���������
	HashMap<String, String> hm=new HashMap<String, String>();
	private Connection ct=null;
	private ResultSet rs=null;
	private PreparedStatement ps=null; 
	private float allPrice=0.0f;
	
	//1.��ӻ���
	public void addGoods(String goodsId, String goodsNum){
		hm.put(goodsId, goodsNum);
	}
	//2.ɾ������
	public void delGoods(String goodsId){
		hm.remove(goodsId);
	}
	//3.��ջ���
	public void clear(){
		hm.clear();
	}
	//4.�޸Ļ�������
	public void updateGoods(String goodsId, String newNum){
		hm.put(goodsId, newNum); //�����addһ������Ϊhm�������ڸ���
	}
	//5.����goodsId����goods������
	public String getGoodsNumById(String goodsId){
		return hm.get(goodsId);
	}
	//6.�����ܼ�
	public float getAllPrice(){  //��ʵ˳����showMyCart�ĺ������Ѿ������
		return this.allPrice;
	}
	
	//7.��ʾ���ﳵ(hashmap�ĵ�����ȥkeySet��
	public ArrayList showMyCart(){
		ArrayList<GoodsBean> al=new ArrayList<GoodsBean>();
		try {
			//��֯sql���
			String sql="select * from goods where goodsId in";
			//ʹ�õ�������ɴ�hm��ȡ������id��Ҫ��
			Iterator it=hm.keySet().iterator();
			String sub="(";
			while(it.hasNext()){
				String goodsId=(String)it.next();
				//�ж��ǲ������һ��id
				if(it.hasNext()){
					sub+=goodsId+",";
				}else{
					sub+=goodsId+")";
				}
			}
			if(sub.equals("(")){
				sub+="0)";
			}
			sql+=sub;
			
			ct=new ConnDB().getConn();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			//���ܼ����
			this.allPrice=0;
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
				float unitPrice=rs.getFloat(4);
				float quantity=Integer.parseInt(this.getGoodsNumById(rs.getInt(1)+""));
				this.allPrice+=unitPrice*quantity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.close();
		}
		return al;
	}

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
