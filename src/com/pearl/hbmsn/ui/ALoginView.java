package com.pearl.hbmsn.ui;

import java.io.IOException;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.BoardInfo;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.HomeInfo;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.HBConstant;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.en.update.UpdateMgr;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class ALoginView extends Activity implements OnTouchListener {

	private InputMethodManager m_InputMethodManager;
	private LinearLayout m_Container;
	private BoardInfo _BoardInfo;
	private HomeInfo _HomeInfo;
	private ResultInfo _ResultInfo;
	private Boolean m_ConnectFlag = false;
	private ProgressDialog m_ProgressDialog = null;
	private UpdateMgr updateMgr = null;
	public  static ALoginView Instance = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setKeyboard();
        initUI();
        Instance = this;
//        updateMgr = new UpdateMgr(this);
//        updateMgr.init();
        
        setNetwork();
    }
    
    public int setNetwork(){
    	int ErrorValue = 0;
    	try {
    		if(m_ConnectFlag == true)
    			return ErrorValue;
    		NetworkModule netCore = NetworkModule.GetInstance();
        	netCore.SetHandler(handler);
			netCore.connect();
			netCore.ReconnectTaskStart();
			m_ConnectFlag = true;
			enableUI();//네트워크설정이 유효할때만 UI를 활성화 시킨다.
		} catch (IOException e) {
			ErrorValue = 1;
			ShowMsg("Connection Error!");
			e.printStackTrace();
		}
    	return ErrorValue;
    }
    private void enableUI(){
    	EditText id = (EditText)findViewById(R.id.account); id.setEnabled(true);
    	EditText password = (EditText)findViewById(R.id.pass); password.setEnabled(true);
    	Button btLogin = (Button)findViewById(R.id.btn_login); btLogin.setEnabled(true);
    }
    
    private void initUI(){
    	
    	SharedPreferences sharedPrefer = getSharedPreferences(getResources().getString(R.string.information_string), Context.MODE_PRIVATE);
       	
    	String id = sharedPrefer.getString(getResources().getString(R.string.account_id), "");
       	String password = sharedPrefer.getString(getResources().getString(R.string.account_password), "");
       	String icon = sharedPrefer.getString(getResources().getString(R.string.account_icon), "");
       	
    	EditText etId = (EditText)findViewById(R.id.account);			etId.setText(id);
    	EditText etPassword = (EditText)findViewById(R.id.pass);		etPassword.setText(password);
    	ImageView ivIcon = (ImageView)findViewById(R.id.user_photo);	ivIcon.setImageResource(ConvertMgr.GetResId(icon));
    	
    }
    
    private void saveAccountInfo(){
    	
    	SharedPreferences sharedPrefer = getSharedPreferences(getResources().getString(R.string.information_string), Context.MODE_PRIVATE);
    	SharedPreferences.Editor sharedEditor = sharedPrefer.edit();
    	sharedEditor.putString(getResources().getString(R.string.account_id), CurrentInfo._UserInfo.Id);
    	sharedEditor.putString(getResources().getString(R.string.account_password), CurrentInfo._UserInfo.Password);
    	sharedEditor.putString(getResources().getString(R.string.account_icon), CurrentInfo._UserInfo.Icon);
    	sharedEditor.commit();
    }
    private void setKeyboard(){
    	m_Container = (LinearLayout)findViewById(R.id.container);
    	m_InputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	m_Container.setOnTouchListener(this);
    }

	@Override
	public boolean onTouch(View view, MotionEvent me) {
		if(view.equals(m_Container)){
			//화면의 빈령역을 터치하면 떠있던 키보드가 사라지ㄷ록 설정한다.
			m_InputMethodManager.hideSoftInputFromWindow(m_Container.getWindowToken(), 0);
		}
		return true;
	}
	//Login버튼을 클릭할때 
	@SuppressLint("NewApi") public void OnLoginClick(View view){
		
		if(m_ConnectFlag == false)
			return;
		
		EditText etID = (EditText)findViewById(R.id.account);
		EditText etPassword = (EditText)findViewById(R.id.pass);
		String UserId = etID.getText().toString();
		String UserPassword = etPassword.getText().toString();
		
		if(UserId.isEmpty()){
			String strID = getString(R.string.msg_account);
			ShowMsg(strID);
			return;
		}
		if(UserPassword.isEmpty()){
			String strPassword = getString(R.string.msg_password);
			ShowMsg(strPassword);
			return;
		}
		
		//서버에 로그인정보를 전송한다.
		UserInfo userInfo = new UserInfo();
		userInfo.Id = UserId;
		userInfo.Password = UserPassword;
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_Login, userInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			//오유처리
		}
		else
			ShowLoadingDialog();
	}
	 public void ShowLoadingDialog(){
	    	m_ProgressDialog = ProgressDialog.show(this, "", getString(R.string.msg_loading));
	    	
	 }
	 public void HideLoadingDialog(){
	    	if(m_ProgressDialog != null ){
	    		if(m_ProgressDialog.isShowing())
	    			m_ProgressDialog.dismiss();
	    	}
	 }

	public void OnRegister(View view){
		GoURL();
	}
	private void startMainActiviy(){
//		updateMgr.Destory();
//		updateMgr = null;
		System.gc();
		finish();
		Intent intent = new Intent(this, AMainView.class);
		startActivity(intent);
	}
	private void GoURL(){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HBConstant._RegisterUrl));
		startActivity(intent);
	}
	private void ShowMsg(String Msg){
		Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
	}
	Handler handler = new Handler(){
		// 스레드에서 메세지를 받을 핸들러.
    	public void handleMessage(Message msg) 
    	{
    		NotifyType notifyType = NotifyType.values()[msg.what];
    		
    		switch (notifyType) 
    		{
			  case Reply_Login: // 서버로부터 전송된 유저정보를 얻는다.
				  CurrentInfo._UserInfo = null;
				  CurrentInfo._UserInfo = (UserInfo)msg.obj;
				  NetworkModule._loginState = true;
				  break;
			  case Reply_UserList://서버로 부터 전송된 유저들의 목록을 얻는다.
				  CurrentInfo._UserListInfo = null;
				  CurrentInfo._UserListInfo = (UserListInfo)msg.obj;
				  HideLoadingDialog();
				  saveAccountInfo();//현재 자신의 정보를 보관한다.
				  startMainActiviy();//유저들의 목록까지 받은다음에 기본대면으로 넘어간다.
				  break;
			  case Reply_AdminNotice:
				  _BoardInfo = null;
				  _BoardInfo = (BoardInfo)msg.obj;
				  break;
			  case Reply_Home:
				  _HomeInfo = null;
				  _HomeInfo = (HomeInfo)msg.obj;
				  break;
			  case Notify_Ping://ping지령이 들어오면 ping응답파멧을 보낸다.
				  CurrentInfo._EndPingTime = System.currentTimeMillis();
				  _ResultInfo = (ResultInfo)msg.obj;
				  _ResultInfo.SetErrorType(ErrorType.None);
				  int errorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
				  if(errorType == NetworkModule.RETURN_ERROR){
					  NetworkModule.GetInstance().Reconnect();
				  }
				  break;
			  case Reply_Error://Login이 실패하였을때
				  String errorMsg = getString(R.string.msg_login_error);
				  HideLoadingDialog();
				  ShowMsg(errorMsg);
				  break;
			 
			default:
				break;
    		}
    	}
	};

	@Override
	public void onBackPressed() {
		//Network 련결 해제
		NetworkModule.GetInstance().ConnectClose();
		NetworkModule.GetInstance().ReconnectTaskTerminate();
		NetworkModule.NullInstance();
		
//		UpdateMgr.instance.Destory();
//		UpdateMgr.instance = null;
		
		//현재 보관된 정보들 모두 해제
		CurrentInfo._AskChatInfo = null;
		CurrentInfo._ClassInfo = null;
		CurrentInfo._ClassListInfo = null;
		CurrentInfo._ClassPictureDetailInfo = null;
		CurrentInfo._ClassTypeInfo = null;
		CurrentInfo._ClassTypeListInfo = null;
		CurrentInfo._EndPingTime = 0;
		CurrentInfo._NewInfoList = null;
		CurrentInfo._selectUserDetail = null;
		CurrentInfo._selectUserInfo = null;
		CurrentInfo._UserInfo = null;
		CurrentInfo._UserListInfo = null;
		
		super.onBackPressed();
	}
}
