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
		tvName.setText(SelectedUserInfo.Id);//ID����
		ImageView userIcon = (ImageView)findViewById(R.id.my_user_photo);
		userIcon.setImageResource(ConvertMgr.GetResId(SelectedUserInfo.icon));
		TextView tvNickName = (TextView)findViewById(R.id.nick_name);
		tvNickName.setText(SelectedUserInfo.NickName);//ä������ NickName
		TextView tvBirthday = (TextView)findViewById(R.id.bithday);
		tvBirthday.setText(String.valueOf(SelectedUserInfo.Year) + "." + String.valueOf(SelectedUserInfo.Month) + "." + String.valueOf(SelectedUserInfo.Day) );//���ϼ���
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
	//������ ä���� �����ϰڴٴ� �޽����� �����Ѵ�.
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
		// �����忡�� �޼����� ���� �ڵ鷯.
    	public void handleMessage(Message msg) 
    	{
    		try{
    			NotifyType notifyType = NotifyType.values()[msg.what];
        		
        		switch (notifyType) 
        		{
        		  case Reply_UserList://������ ���� ���۵� �������� ����� ��´�.
    				  CurrentInfo._UserListInfo = null;
    				  CurrentInfo._UserListInfo = (UserListInfo)msg.obj;
    				  RefreshMgr.RefreshUserList();
    				  break;
        		  case Reply_StringChat:
    				  StringInfo strInfo = (StringInfo)msg.obj;
    				  OnNewMsg(strInfo);
    				  break;  
    			  case Notify_Ping://ping������ ������ ping�����ĸ��� ������.
    				  CurrentInfo._EndPingTime = System.currentTimeMillis();
    				  _ResultInfo = (ResultInfo)msg.obj;
    				  _ResultInfo.SetErrorType(ErrorType.None);
    				  int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
    				  if(ErrorType == NetworkModule.RETURN_ERROR){
    						  NetworkModule.GetInstance().Reconnect();
    				  }
    				  break;
    			  case Reply_EnterMeeting:
    				  if(_isChatButtonClick){//chating��ư�� Ŭ�������� ���ƿ��� ���信 ���ؼ��� ä��â���� �Ѿ���� �Ѵ�.
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
	/*� �ð������κ��� ���ο� �޽����� ���ƿ����� �̿� ���� ó���� �����Ѵ�.
	 * ���ο� �޽����� ���� �ð��ο� ���� ������ �޽����� �����ð��ε��� �����ϴ� ���Ŀ� �߰��Ѵ�.
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
