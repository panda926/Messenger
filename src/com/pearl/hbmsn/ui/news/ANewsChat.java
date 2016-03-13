package com.pearl.hbmsn.ui.news;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.RoomInfo;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.ui.ConvertMgr;
import com.pearl.hbmsn.ui.SelectedUserInfo;
import com.pearl.hbmsn.ui.connector.ChatView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ANewsChat extends Activity implements OnClickListener, OnTouchListener{

	public	ANewsChat	Instance;
	private ChatView m_ChatView;
	private EditText m_ChatText;
	private LinearLayout m_Container;
	private InputMethodManager m_InputMethodManager;
	private Button	m_btnSend;
	private Bitmap m_bmYouIcon;
	private Bitmap m_bmMyIcon;
	private final String _WrittingStart = "WritingStart+GIT";
	private final String _WritingEnd = "WritingEnd+GIT";
	private ResultInfo _ResultInfo;
	private RoomInfo mettingInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.chat_view);
		//NetworkModule.GetInstance().SetHandler(chatHandler);
		
		setUI();
		initData();
		Instance = this;
	}

	private void initData(){
		
		UserInfo myInfo = CurrentInfo._UserInfo;
		m_bmMyIcon = BitmapFactory.decodeResource(getResources(), ConvertMgr.GetResId(myInfo.Icon));
		m_bmYouIcon = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
		
        LinearLayout chatView = (LinearLayout)findViewById(R.id.chatview);
        chatView.addView(m_ChatView);
	}
	private void OnBack(){
		onBackPressed();
	}

	private void setUI(){
		m_ChatView = new ChatView(this);
		m_ChatView.setRightBackgroundImage(R.drawable.right_message_bg);
		m_ChatView.setLeftBackgroundImage(R.drawable.left_message_bg);
	
		m_Container = (LinearLayout)findViewById(R.id.chatview);
		m_InputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		 
		m_Container.setOnTouchListener(this);
		
		
		TextView tvBackButton = (TextView)findViewById(R.id.back_detail_to_chat);
		tvBackButton.setOnClickListener(this);
		Intent intent  = getIntent();
		String backString = intent.getExtras().getString("back");
		tvBackButton.setText(backString);
		
		TextView szName = (TextView)findViewById(R.id.connector_name);
        szName.setText(SelectedUserInfo.Id);//Test 코드
        
        m_ChatText = (EditText)findViewById(R.id.et_chattext);
        
        m_btnSend = (Button)findViewById(R.id.bt_send);
        m_btnSend.setOnClickListener(this);
        
	}
	
	@Override
	public void onClick(View v) {
		
		int nId = v.getId();
		
		switch(nId){
		case R.id.back_detail_to_chat:
			OnBack();
			break;
		case R.id.bt_send:
			SendChatMsg();
			OnAddMyMessage();
			m_InputMethodManager.hideSoftInputFromWindow(m_Container.getWindowToken(), 0);
			break;
		}
		
	}

	@SuppressLint("NewApi") private void OnAddMyMessage(){
		
		String msg = m_ChatText.getText().toString();
		if(!msg.isEmpty()){
			m_ChatView.addRightChatMessage(m_bmMyIcon, msg);
			m_ChatText.setText("");
		}
	}
	@SuppressLint("NewApi") private void OnAddYouMessage(String msg){
		
		if(msg.isEmpty() || msg.contentEquals(_WrittingStart) || msg.contentEquals(_WritingEnd))
			return;
		else
			m_ChatView.addLeftChatMessage(m_bmYouIcon, msg);
		
	}
	private void SendChatMsg(){
		String msg = m_ChatText.getText().toString();
		StringInfo messageInfo = new StringInfo();
		messageInfo.UserId = CurrentInfo._UserInfo.Id;
		messageInfo.String = msg;
		messageInfo.FontSize = 24;
		messageInfo.FontName = "Microsoft Sans Serif";
		messageInfo.FontStyle = "b";//Font Style normal
		messageInfo.FontWeight = "d";
		messageInfo.FontColor = "#FF008000";
		messageInfo.strRoomID = mettingInfo.Id;
		
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_StringChat, messageInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			  NetworkModule.GetInstance().Reconnect();
		}
	}
	//채팅창을 클로우즈 할때 서버에 보내는 파켓
	private void SendChatCloseMsg(){
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_OutMeeting, mettingInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			  NetworkModule.GetInstance().Reconnect();
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		//LinearLayout과 ChatView가 겹쳐져 있는데 이 두 레이아웃중 하나를 터치하는 경우에 자판이 사라지도록 하는 코드
		if(v.getId() == R.id.chatview){
			m_InputMethodManager.hideSoftInputFromWindow(m_Container.getWindowToken(), 0);
		}
		return true;
	}


	//대화상대자한테서 날아온 메시지를 대면부에 추가한다.이때 여러가지 조건들을 따져본다.
	private void addMsg(StringInfo stringInfo){
		
		if(stringInfo.UserId != CurrentInfo._UserInfo.Id){
			if(stringInfo.strRoomID.contentEquals(mettingInfo.Id)){
				OnAddYouMessage(stringInfo.String);
			}
		}
		
	}
	
	Handler chatHandler = new Handler(){
		// 스레드에서 메세지를 받을 핸들러.
    	public void handleMessage(Message msg) 
    	{
    		NotifyType notifyType = NotifyType.values()[msg.what];
    		
    		switch (notifyType) 
    		{
			  case Notify_Ping://ping지령이 들어오면 ping응답파멧을 보낸다.
				  CurrentInfo._EndPingTime = System.currentTimeMillis();
				  _ResultInfo = (ResultInfo)msg.obj;
				  _ResultInfo.SetErrorType(ErrorType.None);
				  int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
				  if(ErrorType == NetworkModule.RETURN_ERROR){
						  NetworkModule.GetInstance().Reconnect();
				  }
				  break;
			  case Reply_StringChat:
				  StringInfo _StringInfo = null;
				  _StringInfo = (StringInfo)msg.obj;
				  addMsg(_StringInfo);
				  break;
			  case Reply_Error://Login이 실패하였을때
				  String errorMsg = getString(R.string.msg_login_error);
				  
				  break;
			  case Reply_Logout: // 소켓접속을 끊는다.
				  try
				  {       
					  NetworkModule.GetInstance().ConnectClose();
					//logger("접속이 끊어졌습니다.");
        
				  }
				  catch (Exception e)
				  {
					  //logger("접속이 이미 끊겨 있습니다." + e.getMessage());
					  finish();
				  }
				  break;
			default:
				break;
    		}
    	}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SendChatCloseMsg();
	}
}
