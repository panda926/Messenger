package com.pearl.hbmsn.ui;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.BoardInfo;
import com.pearl.hbmsn.en.info.ClassListInfo;
import com.pearl.hbmsn.en.info.ClassTypeListInfo;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.HomeInfo;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserDetailInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;




import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class AMainView extends FragmentActivity implements OnClickListener {

	public static AMainView	Instance;
	
	final String TAG = "MainActivity";
	 
    int mCurrentFragmentIndex;
    public final static int CONNECTORS = 0;
    public final static int PHOTO = 1;
    public final static int NEWS = 2;
    public final static int MYINFO = 3;
    
    private TextView	m_tvTitle;
    private ImageButton m_btConnectors;
    private ImageButton m_btPhotos;
    public  ImageButton m_btNews;
    private ImageButton m_btMyInfo;
    
    private TextView	m_tvConnectors;
    private TextView	m_tvPhotos;
    private TextView	m_tvNews;
    private TextView	m_tvMyInfo;
    
	private BoardInfo _BoardInfo;
	private HomeInfo _HomeInfo;
	private ResultInfo _ResultInfo;
    
	public boolean m_FlagNewsArrived = false;
	
	private ProgressDialog m_ProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        init();
        //NetworkModule.GetInstance().SetHandler(mainHandler);
        Instance = this;
    }
 
    private void init(){
    	
    	m_btConnectors = (ImageButton) findViewById(R.id.bt_ConnectorsFragment);
    	m_btConnectors.setOnClickListener(this);
    	m_btPhotos = (ImageButton) findViewById(R.id.bt_PhotoFragment);
    	m_btPhotos.setOnClickListener(this);
    	m_btNews = (ImageButton) findViewById(R.id.bt_NewsFragment);
    	m_btNews.setOnClickListener(this);
    	m_btMyInfo = (ImageButton) findViewById(R.id.bt_MyInfoFragment);
    	m_btMyInfo.setOnClickListener(this);
    	m_tvTitle = (TextView) findViewById(R.id.selected_title);
    	
    	m_tvConnectors = (TextView)findViewById(R.id.tv_ConnectorsFragment);
    	m_tvConnectors.setOnClickListener(this);
    	m_tvPhotos = (TextView)findViewById(R.id.tv_PhotoFragment);
    	m_tvPhotos.setOnClickListener(this);
    	m_tvNews = (TextView)findViewById(R.id.tv_NewsFragment);
    	m_tvNews.setOnClickListener(this);
    	m_tvMyInfo = (TextView)findViewById(R.id.tv_MyInfoFragment);
    	m_tvMyInfo.setOnClickListener(this);
    	
    	mCurrentFragmentIndex = CONNECTORS;
    	
        fragmentReplace(mCurrentFragmentIndex);
    }
    public void fragmentReplace(int reqNewFragmentIndex) {
 
        Fragment newFragment = null;
 
        Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);
 
        newFragment = getFragment(reqNewFragmentIndex);
 
        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
 
        transaction.replace(R.id.ll_fragment, newFragment);
 
        // Commit the transaction
        transaction.commit();
 
    }
 
    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
 
        switch (idx) {
        case CONNECTORS:
            newFragment = new FConnectorsView();
            break;
        case PHOTO:
            newFragment = new FPhotosView();
            break;
        case NEWS:
            newFragment = new FNewsView();
            break;
        case MYINFO:
        	newFragment = new FMyInfoView();
        	break;
        default:
            Log.d(TAG, "Unhandle case");
            break;
        }
 
        return newFragment;
    }
 
    private void excuteFragment(int fragmentId){
    	onChangeButtonImage(fragmentId);
        fragmentReplace(fragmentId);
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
    @Override
    public void onClick(View v) {
 
        switch (v.getId()) {
 
        case R.id.bt_ConnectorsFragment:
        case R.id.tv_ConnectorsFragment:	
            mCurrentFragmentIndex = CONNECTORS;
            onChangeButtonImage(CONNECTORS);
            fragmentReplace(mCurrentFragmentIndex);
            break;
        case R.id.bt_PhotoFragment:
        case R.id.tv_PhotoFragment://������ ��쿡�� ������ ��û�� �ش��� ������ �;� �����׸�Ʈ�� �����Ų��.
            mCurrentFragmentIndex = PHOTO;
            
            if(CurrentInfo._ClassListInfo != null){
              onChangeButtonImage(PHOTO);
              fragmentReplace(mCurrentFragmentIndex);
            }
            else{
            	int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_ClassInfo, CurrentInfo._UserInfo);
                if(ErrorType == NetworkModule.RETURN_ERROR){
    				  NetworkModule.GetInstance().Reconnect();
    			}
                else if(ErrorType == NetworkModule.RETURN_RECONNECT){
                	//���� ��ð���¶�� �ƹ� ó���� �������� �ʴ´�.
                }
                else{
                	ShowLoadingDialog();
                }
            }

            break;
        case R.id.bt_NewsFragment:
        case R.id.tv_NewsFragment:
            mCurrentFragmentIndex = NEWS;
            onChangeButtonImage(NEWS);
            fragmentReplace(mCurrentFragmentIndex);
            break;
        case R.id.bt_MyInfoFragment:
        case R.id.tv_MyInfoFragment:
        	mCurrentFragmentIndex = MYINFO;
        	onChangeButtonImage(MYINFO);
        	fragmentReplace(mCurrentFragmentIndex);
        	break;
        default:
            Log.d(TAG, "Unhandle case");
            break;
 
        }
 
    }
    
    
    private void onChangeButtonImage(int nCurrentButton){
    	
    	int textSelectColor =  getResources().getColor(R.color.color_text_blue);
    	int textDeselectColor = getResources().getColor(R.color.color_text);
    	int newsDeselectedId;
    	
    	if(m_FlagNewsArrived)
    		newsDeselectedId = R.drawable.news_arrived;
    	else
    		newsDeselectedId = R.drawable.news_deselected;
    	
    	switch (nCurrentButton){
    	
    	case CONNECTORS:
    		m_btConnectors.setImageResource(R.drawable.connectors_selected);//Ȱ��ȭ
    		m_tvConnectors.setTextColor(textSelectColor);//Ȱ��ȭ
    		m_btPhotos.setImageResource(R.drawable.photo_deselected);
    		m_tvPhotos.setTextColor(textDeselectColor);
    		m_btNews.setImageResource(newsDeselectedId);
    		m_tvNews.setTextColor(textDeselectColor);
    	    m_btMyInfo.setImageResource(R.drawable.myinfo_deselected);
    	    m_tvMyInfo.setTextColor(textDeselectColor);
    	    
    	    m_tvTitle.setText(R.string.title_connectors);
    		break;
    	case PHOTO:
    		m_btConnectors.setImageResource(R.drawable.connectors_deselected);
    		m_tvConnectors.setTextColor(textDeselectColor);
    		m_btPhotos.setImageResource(R.drawable.photo_selected);//Ȱ��ȭ
    		m_tvPhotos.setTextColor(textSelectColor);//Ȱ��ȭ
    		m_btNews.setImageResource(newsDeselectedId);
    		m_tvNews.setTextColor(textDeselectColor);
    		m_btMyInfo.setImageResource(R.drawable.myinfo_deselected);
    		m_tvMyInfo.setTextColor(textDeselectColor);
    		m_tvTitle.setText(R.string.title_photos);
    		break;
    	case NEWS:
    		m_btConnectors.setImageResource(R.drawable.connectors_deselected);
    		m_tvConnectors.setTextColor(textDeselectColor);
    		m_btPhotos.setImageResource(R.drawable.photo_deselected);
    		m_tvPhotos.setTextColor(textDeselectColor);
    		m_btNews.setImageResource(R.drawable.news_selected);//Ȱ��ȭ
    		m_tvNews.setTextColor(textSelectColor);//Ȱ��ȭ
    		m_btMyInfo.setImageResource(R.drawable.myinfo_deselected);
    		m_tvMyInfo.setTextColor(textDeselectColor);
    		
    		m_tvTitle.setText(R.string.title_news);
    		
    		m_FlagNewsArrived = false;
    		break;
    	case MYINFO:
    		m_btConnectors.setImageResource(R.drawable.connectors_deselected);
    		m_tvConnectors.setTextColor(textDeselectColor);
    		m_btPhotos.setImageResource(R.drawable.photo_deselected);
    		m_tvPhotos.setTextColor(textDeselectColor);
    		m_btNews.setImageResource(newsDeselectedId);
    		m_tvNews.setTextColor(textDeselectColor);
    		m_btMyInfo.setImageResource(R.drawable.myinfo_selected);//Ȱ��ȭ
    		m_tvMyInfo.setTextColor(textSelectColor);
    		m_tvTitle.setText(R.string.title_myinfo);
    		break;
    	default:
            Log.d(TAG, "Unhandle case");
            break;
    	}
    }

    //BackŰ�� Ŭ�������� HomeŰ�� Ŭ���� ȿ���� �ֱ� ���Ͽ� onBackPressed�� �������̵� ��
	@Override
	public void onBackPressed() {
		onHomePressed();
	}
	
	//���� HomeŰ Ŭ���̺�Ʈ�� �ֱ����� �޼���
	private void onHomePressed(){
		Intent intent = new Intent();
	    intent.setAction("android.intent.action.MAIN");
	    intent.addCategory("android.intent.category.HOME");
	    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
	    | Intent.FLAG_ACTIVITY_FORWARD_RESULT
	    | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
	    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	    startActivity(intent);
	}
    
	Handler mainHandler = new Handler(){
		// �����忡�� �޼����� ���� �ڵ鷯.
    	public void handleMessage(Message msg) 
    	{
    		NotifyType notifyType = NotifyType.values()[msg.what];
    		
    		switch (notifyType) 
    		{
			  case Reply_UserList://������ ���� ���۵� �������� ����� ��´�.
				  CurrentInfo._UserListInfo = null;
				  CurrentInfo._UserListInfo = (UserListInfo)msg.obj;
				  RefreshMgr.RefreshUserList();
				  HideLoadingDialog();
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
				  int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Notify_Ping, _ResultInfo);
				  if(ErrorType == NetworkModule.RETURN_ERROR){
					  NetworkModule.GetInstance().Reconnect();
				  }
				  break;
			  case Reply_StringChat:
				  StringInfo strInfo = (StringInfo)msg.obj;
				  OnNewMsg(strInfo);
				  break;
			  case Reply_ClassInfo:
				  CurrentInfo._ClassListInfo = (ClassListInfo)msg.obj;
				  HideLoadingDialog();
				  excuteFragment(mCurrentFragmentIndex);
				  break;
			  case Reply_ClassTypeInfo:
				  HideLoadingDialog();
				  CurrentInfo._ClassTypeListInfo = (ClassTypeListInfo)msg.obj;
				  if(FPhotosView.instance != null)
					  FPhotosView.instance.ViewGrouplist();
				  break;
			  case Reply_PartnerDetail:
				  CurrentInfo._selectUserDetail = (UserDetailInfo)msg.obj;
				  break;
			  case Reply_Error://Login�� �����Ͽ�����
				  String errorMsg = getString(R.string.msg_login_error);
				  break;
			default:
				break;
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
		NetworkModule.GetInstance().SetHandler(mainHandler);
	}

	@Override
	protected void onDestroy() {
		Instance = null;
		super.onDestroy();
	}
}


