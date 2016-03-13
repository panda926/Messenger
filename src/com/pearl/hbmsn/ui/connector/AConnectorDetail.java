package com.pearl.hbmsn.ui.connector;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.AskChatInfo;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserDetailInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.ui.ConvertMgr;
import com.pearl.hbmsn.ui.RefreshMgr;
import com.pearl.hbmsn.ui.SelectedUserInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AConnectorDetail extends Activity implements OnClickListener {

	public	AConnectorDetail	Instance;
	private ResultInfo	_ResultInfo;
	private boolean _isChatButtonClick = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.connector_detail);
		//NetworkModule.GetInstance().SetHandler(detailHandler);
		try{
			Instance = this;
			TextView btnBack = (TextView)findViewById(R.id.back);
			btnBack.setOnClickListener(this);
			ImageButton btnPhoto = (ImageButton)findViewById(R.id.bt_photo);
			btnPhoto.setOnClickListener(this);
			ImageButton btnChat = (ImageButton)findViewById(R.id.bt_chat);
			btnChat.setOnClickListener(this);
			
			initData();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void initData(){
		
		TextView tvName = (TextView)findViewById(R.id.account);
		tvName.setText(SelectedUserInfo.Id);//ID설정
		ImageView userIcon = (ImageView)findViewById(R.id.my_user_photo);
		userIcon.setImageResource(ConvertMgr.GetResId(SelectedUserInfo.icon));
		TextView tvNickName = (TextView)findViewById(R.id.nick_name);
		tvNickName.setText(SelectedUserInfo.NickName);//채팅자의 NickName
		TextView tvBirthday = (TextView)findViewById(R.id.bithday);
		tvBirthday.setText(String.valueOf(SelectedUserInfo.Year) + "." + String.valueOf(SelectedUserInfo.Month) + "." + String.valueOf(SelectedUserInfo.Day) );//생일설정
		TextView tvSign = (TextView)findViewById(R.id.sign);
		tvSign.setText(SelectedUserInfo.Sign);
		
	}
	private void OnBack(){
		this.onBackPressed();
	}

	@Override
	public void onClick(View view) {

		int resId = view.getId();
		
		switch(resId){
		
		case R.id.back:
			OnBack();
			break;
		case R.id.bt_photo:
			viewPhoto();
			break;
		case R.id.bt_chat:
			_isChatButtonClick = true;
			sendChatStartMsg();
			break;
		}
		
	}
	
	private void viewPhoto(){
		if(CurrentInfo._selectUserDetail == null)
			return;
		Intent intent = new Intent();
		intent.setClass(this, AConnectorPicture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	private void startChat(){
		Intent intent = new Intent();
		intent.setClass(this, AConnectorChat.class);
		String backString = getString(R.string.back_detail);
		intent.putExtra("back", backString);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
	}
	//서버에 채팅을 시잔하겠다는 메시지를 전송한다.
	private void sendChatStartMsg(){
		AskChatInfo askChatInfo = new AskChatInfo();
		askChatInfo.AskingID = CurrentInfo._UserInfo.Id;
		askChatInfo.TargetId  = SelectedUserInfo.Id;
		askChatInfo.Agree = 1;
		
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Reply_EnterMeeting, askChatInfo);
		
		if(ErrorType == NetworkModule.RETURN_ERROR){
			  NetworkModule.GetInstance().Reconnect();
		}
	}	
	
	Handler detailHandler = new Handler(){
		// 스레드에서 메세지를 받을 핸들러.
    	public void handleMessage(Message msg) 
    	{
    		try{
    			NotifyType notifyType = NotifyType.values()[msg.what];
        		
        		switch (notifyType) 
        		{
        		  case Reply_UserList://서버로 부터 전송된 유저들의 목록을 얻는다.
    				  CurrentInfo._UserListInfo = null;
    				  CurrentInfo._UserListInfo = (UserListInfo)msg.obj;
    				  RefreshMgr.RefreshUserList();
    				  break;
        		  case Reply_StringChat:
    				  StringInfo strInfo = (StringInfo)msg.obj;
    				  OnNewMsg(strInfo);
    				  break;  
    			  case Notify_Ping://ping지령이 들어오면 ping응답파멧을 보낸다.
    				  CurrentInfo._EndPingTime = System.currentTimeMillis();
    				  _ResultInfo = (ResultInfo)msg.obj;
    				  _ResultInfo.SetErrorType(ErrorType.None);
    				  int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
    				  if(ErrorType == NetworkModule.RETURN_ERROR){
    						  NetworkModule.GetInstance().Reconnect();
    				  }
    				  break;
    			  case Reply_EnterMeeting:
    				  if(_isChatButtonClick){//chating버튼을 클릭했을때 날아오는 응답에 한해서만 채팅창으로 넘어가도록 한다.
    					  _isChatButtonClick = false;
	    				  CurrentInfo._AskChatInfo = null;
	    				  CurrentInfo._AskChatInfo = (AskChatInfo)msg.obj;
	    				  startChat();
    				  }
    				  break;
    			 case Reply_PartnerDetail:
    				  CurrentInfo._selectUserDetail = (UserDetailInfo)msg.obj;
    				  break; 
    			default:
    				break;
        		}
    		}
    		catch(Exception ex){
    			ex.printStackTrace();
    		}
    		
    	}
	};
	/*어떤 련계인으로부터 새로운 메시지가 날아왔을때 이에 대한 처리를 진행한다.
	 * 새로운 메시지를 보낸 련계인에 대한 정보를 메시지를 보낸련계인들을 보관하는 대기렬에 추가한다.
	 * */
	@SuppressLint("NewApi") private void OnNewMsg(StringInfo strInfo){
		
		RefreshMgr.RefreshNewsList(strInfo, this);
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		NetworkModule.GetInstance().SetHandler(detailHandler);
	}
}
