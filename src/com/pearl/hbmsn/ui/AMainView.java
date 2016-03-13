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
        case R.id.tv_PhotoFragment://사진의 경우에는 응답이 요청에 해당한 응답이 와야 프래그먼트를 실행시킨다.
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
                	//현재 재련결상태라면 아무 처리도 진행하지 않는다.
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
    		m_btConnectors.setImageResource(R.drawable.connectors_selected);//활성화
    		m_tvConnectors.setTextColor(textSelectColor);//활성화
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
    		m_btPhotos.setImageResource(R.drawable.photo_selected);//활성화
    		m_tvPhotos.setTextColor(textSelectColor);//활성화
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
    		m_btNews.setImageResource(R.drawable.news_selected);//활성화
    		m_tvNews.setTextColor(textSelectColor);//활성화
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
    		m_btMyInfo.setImageResource(R.drawable.myinfo_selected);//활성화
    		m_tvMyInfo.setTextColor(textSelectColor);
    		m_tvTitle.setText(R.string.title_myinfo);
    		break;
    	default:
            Log.d(TAG, "Unhandle case");
            break;
    	}
    }

    //Back키를 클릭했을때 Home키를 클릭한 효과를 주기 위하여 onBackPressed를 오버라이드 함
	@Override
	public void onBackPressed() {
		onHomePressed();
	}
	
	//강제 Home키 클릭이벤트를 주기위한 메서드
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
		// 스레드에서 메세지를 받을 핸들러.
    	public void handleMessage(Message msg) 
    	{
    		NotifyType notifyType = NotifyType.values()[msg.what];
    		
    		switch (notifyType) 
    		{
			  case Reply_UserList://서버로 부터 전송된 유저들의 목록을 얻는다.
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
			  case Reply_Error://Login이 실패하였을때
				  String errorMsg = getString(R.string.msg_login_error);
				  break;
			default:
				break;
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
		NetworkModule.GetInstance().SetHandler(mainHandler);
	}

	@Override
	protected void onDestroy() {
		Instance = null;
		super.onDestroy();
	}
}


