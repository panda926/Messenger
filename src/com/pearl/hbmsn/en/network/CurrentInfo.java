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

	public static UserInfo _UserInfo = null;//�ڱ��ڽ��� ����
	public static UserListInfo _UserListInfo = null;//���� ���� �ִ� �������� ���
	public static ClassListInfo _ClassListInfo = null;
	public static AskChatInfo _AskChatInfo = null;//ä�ý����� �䱸�ϴ� ����
	public static ArrayList<NewsInfo> _NewInfoList = new ArrayList<NewsInfo>();//ä�ø޽����� ���� �������� ���
	public static ClassInfo _ClassInfo = null;
	public static ClassTypeListInfo _ClassTypeListInfo = null;
	public static ClassTypeInfo _ClassTypeInfo = null;
	public static ClassPictureDetailInfo _ClassPictureDetailInfo = null;
	public static UserInfo _selectUserInfo = null;
	public static UserDetailInfo _selectUserDetail = null;//���õ� ������ ������
	public static long _EndPingTime;//���������� ���� ������ �ð�
	
}
