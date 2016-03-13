package com.pearl.hbmsn.ui;

import com.pearl.hbmsn.en.info.UserInfo;

import android.content.Context;
import android.graphics.Bitmap;


public  class ConnectorInfo{
	 public String m_Name = "";
	 public String m_State = "";
	 public String m_IconString;
	 
	 public String m_NickName="";
	 public String m_Address = "";
	 public String m_Sign = "";
	 public int m_nYear;
	 public int m_nMonth;
	 public int m_nDay;
	 
	 public ConnectorInfo(Context context, UserInfo userInfo){
		 m_Name = userInfo.Id;
		 m_State = context.getString(ConvertMgr.GetUserStateStringId(userInfo.nUserState));
		 m_IconString = userInfo.Icon;
		 
		 m_NickName = userInfo.NickName;
		 m_Address = userInfo.Address;
		 m_Sign = userInfo.Sign;
		 m_nYear = userInfo.Year;
		 m_nMonth = userInfo.Month;
		 m_nDay = userInfo.Day;
		 
	 }
//	 public ConnectorInfo(String childName, String childContent, Bitmap iconImage){
//		 m_Name = childName;
//		 m_State = childContent;
//		 m_IconImage = iconImage;
//	 }

	public void setConent(String childName, String childContent, Bitmap iconImage) {
		m_Name = childName;
		m_State = childContent;
		
	}
}