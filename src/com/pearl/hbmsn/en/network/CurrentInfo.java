package com.pearl.hbmsn.en.network;


import java.util.ArrayList;


import com.pearl.hbmsn.en.info.AskChatInfo;
import com.pearl.hbmsn.en.info.ClassInfo;
import com.pearl.hbmsn.en.info.ClassListInfo;
import com.pearl.hbmsn.en.info.ClassPictureDetailInfo;
import com.pearl.hbmsn.en.info.ClassTypeInfo;
import com.pearl.hbmsn.en.info.ClassTypeListInfo;
import com.pearl.hbmsn.en.info.UserDetailInfo;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.ui.NewsInfo;

public class CurrentInfo {

	public static UserInfo _UserInfo = null;//자기자신의 정보
	public static UserListInfo _UserListInfo = null;//현재 들어와 있는 유저들의 목록
	public static ClassListInfo _ClassListInfo = null;
	public static AskChatInfo _AskChatInfo = null;//채팅시작을 요구하는 정보
	public static ArrayList<NewsInfo> _NewInfoList = new ArrayList<NewsInfo>();//채팅메시지를 보낸 유저들의 목록
	public static ClassInfo _ClassInfo = null;
	public static ClassTypeListInfo _ClassTypeListInfo = null;
	public static ClassTypeInfo _ClassTypeInfo = null;
	public static ClassPictureDetailInfo _ClassPictureDetailInfo = null;
	public static UserInfo _selectUserInfo = null;
	public static UserDetailInfo _selectUserDetail = null;//선택된 유저의 상세정보
	public static long _EndPingTime;//마지막으로 핑이 도착한 시간
	
}
