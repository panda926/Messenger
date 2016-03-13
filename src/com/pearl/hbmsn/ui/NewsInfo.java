package com.pearl.hbmsn.ui;

import java.util.ArrayList;



public class NewsInfo {

	public String m_Icon;//채팅을 보낸 유저의 아이콘
	public String m_szId;//채팅을 보낸 유저의 아이디
	public ArrayList<String> m_szMSG = new ArrayList<String>();//채팅메시지배렬
	public String m_szTime;//메시지를 보낸 시간
	public String m_RoomId;//방아이디
	
	public NewsInfo(String bmIcon, String szId, String szMSG, String szTime, String roomId) {
		m_Icon = bmIcon;
		m_szId = szId;
		m_szMSG.add(szMSG);
		m_szTime = szTime;
		m_RoomId = roomId;
	}

}
