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
			enableUI();//��Ʈ��ũ������ ��ȿ�Ҷ��� UI�� Ȱ��ȭ ��Ų��.
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
			//ȭ���� ��ɿ��� ��ġ�ϸ� ���ִ� Ű���尡 ��������� �����Ѵ�.
			m_InputMethodManager.hideSoftInputFromWindow(m_Container.getWindowToken(), 0);
		}
		return true;
	}
	//Login��ư�� Ŭ���Ҷ� 
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
		
		//������ �α��������� �����Ѵ�.
		UserInfo userInfo = new UserInfo();
		userInfo.Id = UserId;
		userInfo.Password = UserPassword;
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_Login, userInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			//����ó��
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
		// �����忡�� �޼����� ���� �ڵ鷯.
    	public void handleMessage(Message msg) 
    	{
    		NotifyType notifyType = NotifyType.values()[msg.what];
    		
    		switch (notifyType) 
    		{
			  case Reply_Login: // �����κ��� ���۵� ���������� ��´�.
				  CurrentInfo._UserInfo = null;
				  CurrentInfo._UserInfo = (UserInfo)msg.obj;
				  NetworkModule._loginState = true;
				  break;
			  case Reply_UserList://������ ���� ���۵� �������� ����� ��´�.
				  CurrentInfo._UserListInfo = null;
				  CurrentInfo._UserListInfo = (UserListInfo)msg.obj;
				  HideLoadingDialog();
				  saveAccountInfo();//���� �ڽ��� ������ �����Ѵ�.
				  startMainActiviy();//�������� ��ϱ��� ���������� �⺻������� �Ѿ��.
				  break;
			  case Reply_AdminNotice:
				  _BoardInfo = null;
				  _BoardInfo = (BoardInfo)msg.obj;
				  break;
			  case Reply_Home:
				  _HomeInfo = null;
				  _HomeInfo = (HomeInfo)msg.obj;
				  break;
			  case Notify_Ping://ping������ ������ ping�����ĸ��� ������.
				  CurrentInfo._EndPingTime = System.currentTimeMillis();
				  _ResultInfo = (ResultInfo)msg.obj;
				  _ResultInfo.SetErrorType(ErrorType.None);
				  int errorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
				  if(errorType == NetworkModule.RETURN_ERROR){
					  NetworkModule.GetInstance().Reconnect();
				  }
				  break;
			  case Reply_Error://Login�� �����Ͽ�����
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
		//Network �ð� ����
		NetworkModule.GetInstance().ConnectClose();
		NetworkModule.GetInstance().ReconnectTaskTerminate();
		NetworkModule.NullInstance();
		
//		UpdateMgr.instance.Destory();
//		UpdateMgr.instance = null;
		
		//���� ������ ������ ��� ����
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
