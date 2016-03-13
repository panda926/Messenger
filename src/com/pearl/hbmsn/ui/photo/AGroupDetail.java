package com.pearl.hbmsn.ui.photo;


import java.util.ArrayList;





import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.ClassPictureDetailInfo;
import com.pearl.hbmsn.en.info.ClassTypeInfo;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.ui.ConvertMgr;
import com.pearl.hbmsn.ui.RefreshMgr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AGroupDetail extends Activity implements OnClickListener, OnScrollListener {

	public	AGroupDetail	Instance;
	private ResultInfo		_ResultInfo;
	private FolderListAdapter m_FolderListAdapter = null;
	private ArrayList<FolderInfo> m_FolderInfoList = null;
	private ProgressDialog m_ProgressDialog;
	private boolean isItemClick = false;
	
	public int m_nTotalCount;
	public int m_nProgresspos;
	
	private int taskPosition = -1;
	
	DownloadTorrentAsync _DownloadTorrentAsync;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.photogroup_detail);
		
		TextView tvBackBtn = (TextView)findViewById(R.id.groupdetail_back);
		tvBackBtn.setOnClickListener(this);
		tvBackBtn.setText(getString(R.string.left_allow)+ " " + CurrentInfo._ClassInfo.Class_Name);
		
		TextView tvTitle = (TextView)findViewById(R.id.groupdetail_title);
		Intent intent = getIntent();
		tvTitle.setText(intent.getExtras().getString("title"));
		Instance = this;
		initData();
	}


	private void initData(){
		 
		m_FolderInfoList = getFolderInfoList(CurrentInfo._ClassTypeListInfo._ClassType);
		m_FolderListAdapter = new FolderListAdapter(this, R.layout.group_detail_low, m_FolderInfoList);
		ListView folderList = (ListView)findViewById(R.id.lv_folder_list);
		folderList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> ad, View v, int position,
					long id) {
				isItemClick = true;
				CurrentInfo._ClassTypeInfo = CurrentInfo._ClassTypeListInfo._ClassType.get(position);
				
				if(CurrentInfo._ClassTypeInfo.Class_File_Type == 0){//�����̸�
					sendRequestMsg();
					ShowLoadingDialog();
				}
				else if(CurrentInfo._ClassTypeInfo.Class_File_Type == 2){
					playTorrent(CurrentInfo._ClassTypeInfo.Class_File_Name);
				}
			}
			
		});
		folderList.setAdapter(m_FolderListAdapter);
		folderList.setOnScrollListener(this);
	}
	private void ShowLoadingDialog(){
		m_ProgressDialog = ProgressDialog.show(this, "", getString(R.string.msg_loading));
	}
	private void HideLoadingDialog(){
		if(m_ProgressDialog != null){
			if(m_ProgressDialog.isShowing()){
				m_ProgressDialog.dismiss();
			}
		}
	}
	private void playTorrent(String torrentUrl){
		String url = ConvertMgr.getEcodeUrl(this, torrentUrl);
		//������ AsyncTask�� �������̶��
		if(_DownloadTorrentAsync != null){
			if(!_DownloadTorrentAsync.isCancelled()){
				_DownloadTorrentAsync.cancel(true);
				_DownloadTorrentAsync = null;
				System.gc();
			}
		}
		_DownloadTorrentAsync = new DownloadTorrentAsync(this);
		_DownloadTorrentAsync.execute(url, "1", "1");
		
	}
	private ArrayList<FolderInfo> getFolderInfoList(ArrayList<ClassTypeInfo> classTypeInfoList){
		
		ArrayList<FolderInfo> folderInfoList = new ArrayList<FolderInfo>();
		
		for(int nId = 0; nId < classTypeInfoList.size(); nId++){
			
			ClassTypeInfo classTypeInfo = classTypeInfoList.get(nId);
			
			//FolderInfo(Bitmap image, String name, String time, String kind, int count, String imageUrl)
			String strImageUrl;
			if(classTypeInfo.Class_File_Type == 2){//���� ��ȭ�̸�
				
				if(classTypeInfo.Class_Video_Title.contentEquals(getString(R.string.temp_image)))
					strImageUrl = classTypeInfo.Class_Video_Title;
				else{
					int nTokenPos = classTypeInfo.Class_Video_Title.indexOf("||");
					if(nTokenPos == -1){nTokenPos = classTypeInfo.Class_Video_Title.length();}
					String titleName = classTypeInfo.Class_Video_Title.substring(0, nTokenPos);
					strImageUrl = classTypeInfo.Class_File_Name.substring(0, classTypeInfo.Class_File_Name.lastIndexOf("/") + 1) + titleName;
				}
				
			}
			else{
				strImageUrl = classTypeInfo.Class_File_Name;
			}
				
			folderInfoList.add(new FolderInfo(
					null, //Bitmap image
					classTypeInfo.Class_Img_Folder,//name
					ConvertMgr.GetDateTimeString(classTypeInfo.Class_File_Date),//time 
					CurrentInfo._ClassInfo.Class_Type_Name,//count
					classTypeInfo.Class_File_Count,//count
					ConvertMgr.getEcodeUrl(this, strImageUrl)));//imageUrl
		}
		
		return folderInfoList;
	}
	
	
	private void sendRequestMsg(){
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_ClassPictureDetail, 
				CurrentInfo._ClassTypeInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			  NetworkModule.GetInstance().Reconnect();
		}
	}
	
	
	private void OnBack(){
		this.onBackPressed();
	}

	@Override
	public void onClick(View view) {

		int resId = view.getId();
		
		switch(resId){
		
		case R.id.groupdetail_back:
			OnBack();
			break;
		}
		
	}
	
	private void StartPictureView(){
		
		Intent intent = new Intent();
		intent.setClass(this, APhotoView.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

		Handler AGroupDetailHandler = new Handler(){
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
    				  HideLoadingDialog();
    				  break;
        		  case Reply_StringChat:
    				  StringInfo strInfo = (StringInfo)msg.obj;
    				  OnNewMsg(strInfo);
    				  break;
        		  case Reply_ClassPictureDetail:
        			  CurrentInfo._ClassPictureDetailInfo = (ClassPictureDetailInfo)msg.obj;
        			  HideLoadingDialog();
        			  if(isItemClick){
        				  isItemClick = false;
        				  StartPictureView();
        			  }
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
		NetworkModule.GetInstance().SetHandler(AGroupDetailHandler);
	}

	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		for(int nId = firstVisibleItem; nId < firstVisibleItem + visibleItemCount; nId++){
			
			if(taskPosition < nId){
				taskPosition = nId;
			}
		}
		
	}


	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onDestroy() {
		
		if(_DownloadTorrentAsync != null){
			if(!_DownloadTorrentAsync.isCancelled()){
				_DownloadTorrentAsync.cancel(true);
				_DownloadTorrentAsync = null;
				System.gc();
			}
		}
		super.onDestroy();
	}
	
}
