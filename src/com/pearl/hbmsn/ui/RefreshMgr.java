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
 * 련계인들의 목록, 뉴스목록 등의 Refresh를 진행하는 클라스
 * */
public class RefreshMgr {

	//련계인들의 목록을 Refresh한다.
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
		
		//만일 지금까지 도착한 메시지가 하나도 없다면 새로 추가한다.
		if(CurrentInfo._NewInfoList.size() == 0){
			
			NewsInfo newsInfo = 
					new NewsInfo(getIconById(strInfo.UserId), strInfo.UserId, strInfo.String, ConvertMgr.GetCurrentTime(context), strInfo.strRoomID);
			CurrentInfo._NewInfoList.add(newsInfo);
		}
		else{
			//메시지배렬이 존재한다면
			//이전에 메시지가 온것이 있는가를 체크하고 있다면 메시지를 추가한다.
			boolean isExist = false;
			for(int nId = 0; nId < CurrentInfo._NewInfoList.size(); nId++){
				NewsInfo newsInfo = CurrentInfo._NewInfoList.get(nId);
				if(newsInfo.m_szId.contentEquals(strInfo.UserId)){
					newsInfo.m_szMSG.add(strInfo.String);
					isExist = true;
					break;
				}
			}
			//만일 새로운 유저에게서 온 메시지라면 유저배렬에 새로 추가한다.
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
				AMainView.Instance.m_btNews.setImageResource(R.drawable.news_arrived);//메시지가 도착하였다는 표시를 한다.
		}
	}
	
	//유저목록으로부터 Id에 해당한 Icon문자렬을 얻는다.
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
