package com.pearl.hbmsn.ui;

import java.util.ArrayList;



public class NewsInfo {

	public String m_Icon;//ä���� ���� ������ ������
	public String m_szId;//ä���� ���� ������ ���̵�
	public ArrayList<String> m_szMSG = new ArrayList<String>();//ä�ø޽������
	public String m_szTime;//�޽����� ���� �ð�
	public String m_RoomId;//����̵�
	
	public NewsInfo(String bmIcon, String szId, String szMSG, String szTime, String roomId) {
		m_Icon = bmIcon;
		m_szId = szId;
		m_szMSG.add(szMSG);
		m_szTime = szTime;
		m_RoomId = roomId;
	}

}
