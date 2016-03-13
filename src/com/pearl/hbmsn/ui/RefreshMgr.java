package com.pearl.hbmsn.ui;

import android.annotation.SuppressLint;
import android.content.Context;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.HBConstant;

/*
 * �ð��ε��� ���, ������� ���� Refresh�� �����ϴ� Ŭ��
 * */
public class RefreshMgr {

	//�ð��ε��� ����� Refresh�Ѵ�.
	public static void RefreshUserList(){
		if(FConnectorsView.Instance != null){
			FConnectorsView.Instance.RefreshView();
		}
	}
	
	@SuppressLint("NewApi") public static void RefreshNewsList(StringInfo strInfo, Context context){
		
		if(strInfo.String.isEmpty() || 
				strInfo.String.contentEquals(HBConstant._WrittingStart) ||
				strInfo.String.contentEquals(HBConstant._WritingEnd))
		{
			return;
		}
		
		//���� ���ݱ��� ������ �޽����� �ϳ��� ���ٸ� ���� �߰��Ѵ�.
		if(CurrentInfo._NewInfoList.size() == 0){
			
			NewsInfo newsInfo = 
					new NewsInfo(getIconById(strInfo.UserId), strInfo.UserId, strInfo.String, ConvertMgr.GetCurrentTime(context), strInfo.strRoomID);
			CurrentInfo._NewInfoList.add(newsInfo);
		}
		else{
			//�޽�������� �����Ѵٸ�
			//������ �޽����� �°��� �ִ°��� üũ�ϰ� �ִٸ� �޽����� �߰��Ѵ�.
			boolean isExist = false;
			for(int nId = 0; nId < CurrentInfo._NewInfoList.size(); nId++){
				NewsInfo newsInfo = CurrentInfo._NewInfoList.get(nId);
				if(newsInfo.m_szId.contentEquals(strInfo.UserId)){
					newsInfo.m_szMSG.add(strInfo.String);
					isExist = true;
					break;
				}
			}
			//���� ���ο� �������Լ� �� �޽������ ������Ŀ� ���� �߰��Ѵ�.
			if(isExist == false){
				NewsInfo newsInfo = new NewsInfo(getIconById(strInfo.UserId), strInfo.UserId, strInfo.String, ConvertMgr.GetCurrentTime(context), strInfo.strRoomID);
				CurrentInfo._NewInfoList.add(newsInfo);
			}
		}
		
		if(FNewsView.Instance != null){
			FNewsView.Instance.RefreshView();
		}
		else if(AMainView.Instance != null && AMainView.Instance.mCurrentFragmentIndex != AMainView.NEWS){
				AMainView.Instance.m_FlagNewsArrived = true;
				AMainView.Instance.m_btNews.setImageResource(R.drawable.news_arrived);//�޽����� �����Ͽ��ٴ� ǥ�ø� �Ѵ�.
		}
	}
	
	//����������κ��� Id�� �ش��� Icon���ڷ��� ��´�.
	public static String getIconById(String Id){
			String strIcon = "";
			UserListInfo userListInfo = CurrentInfo._UserListInfo;
			for(int nId = 0; nId < userListInfo._Users.size(); nId++){
				UserInfo userInfo = userListInfo._Users.get(nId);
				if(userInfo.Id.contentEquals(Id)){
					strIcon = userInfo.Icon;
					break;
				}
			}
			return strIcon;
		}
}
