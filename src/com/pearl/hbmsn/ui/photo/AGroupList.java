package com.pearl.hbmsn.ui.photo;

import java.util.ArrayList;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.ClassInfo;
import com.pearl.hbmsn.en.info.ClassTypeListInfo;
import com.pearl.hbmsn.en.info.ErrorType;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.ResultInfo;
import com.pearl.hbmsn.en.info.StringInfo;
import com.pearl.hbmsn.en.info.UserListInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AGroupList extends Activity implements OnClickListener {

	public	AGroupList	Instance;
	private ResultInfo	_ResultInfo;
	private ArrayList<String> m_GroupName;
	private GroupListAdapter m_GroupListAdapter;
	public final int FPP = 12;//페지당 폴더수
	private ProgressDialog m_ProgressDialog;
	private int m_SelectedItemId;
	private int m_TotalFolderCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.photogroup);
		TextView tvBackBtn = (TextView)findViewById(R.id.group_back);
		tvBackBtn.setOnClickListener(this);
		TextView tvTitle = (TextView)findViewById(R.id.photogroup_title);
		tvTitle.setText(CurrentInfo._ClassInfo.Class_Name);
		Instance = this;
		initData();
	}

	private int getFolderCount(){
		if(CurrentInfo._ClassTypeListInfo._ClassType.get(0).Class_File_Type == 0){
			m_TotalFolderCount = CurrentInfo._ClassTypeListInfo._ClassType.get(0).Class_File_Id;
		}
		else{
			m_TotalFolderCount = CurrentInfo._ClassTypeListInfo._ClassType.get(0).Class_File_Count;
		}
			
		return m_TotalFolderCount;
	}
	private void initData(){
		
		m_GroupName = new ArrayList<String>();
		ClassInfo currentPhotoInfo = CurrentInfo._ClassInfo;
		
		getFolderCount();
		int nPageCount;
		nPageCount = m_TotalFolderCount / FPP;//(전체폴더수/페지당 폴더수 = 전체페지수)
		if(m_TotalFolderCount % FPP > 0)
			nPageCount++;
		
		for(int nId = 0; nId < nPageCount; nId++){
			m_GroupName.add(currentPhotoInfo.Class_Type_Name);
		}
		
		m_GroupListAdapter = new GroupListAdapter(this, R.layout.group_list_low, m_GroupName);
		ListView listView = (ListView)findViewById(R.id.lv_group);
		listView.setAdapter(m_GroupListAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> ad, View v, int position,
					long id) {
				m_SelectedItemId = position;
				sendRequestPage(position);
				
				//viewGroupDetail(position);
				
			}
			
		});
	}
	private void OnBack(){
		this.onBackPressed();
	}
	private void showLoadingDialog(){
		m_ProgressDialog = ProgressDialog.show(this, "", getString(R.string.msg_loading));
	}
	private void HideLoadingDialog(){
		if(m_ProgressDialog != null){
			if(m_ProgressDialog.isShowing()){
				m_ProgressDialog.dismiss();
			}
		}
	}
	private void sendRequestPage(int position){
		CurrentInfo._ClassInfo.ToIndex = 1 + (FPP * position);
		CurrentInfo._ClassInfo.FromIndex = FPP * (position + 1);
		
		int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_ClassTypeInfo, CurrentInfo._ClassInfo);
		if(ErrorType == NetworkModule.RETURN_ERROR){
			  NetworkModule.GetInstance().Reconnect();
		}
		else{
			showLoadingDialog();
		}
	}
	@Override
	public void onClick(View view) {

		int resId = view.getId();
		
		switch(resId){
		
		case R.id.group_back:
			OnBack();
			break;
		}
		
	}
	
	
	private void viewGroupDetail(int position){
		if(CurrentInfo._ClassTypeListInfo._ClassType.size() != 0){
			Intent intent = new Intent();
			intent.setClass(this, AGroupDetail.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("title", m_GroupName.get(position));
			startActivity(intent);
		}
	}

		Handler AGroupListHandler = new Handler(){
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
    				  HideLoadingDialog();
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
    			  case Reply_ClassTypeInfo:
    				  HideLoadingDialog();
    				  CurrentInfo._ClassTypeListInfo = (ClassTypeListInfo)msg.obj;
    				  viewGroupDetail(m_SelectedItemId);
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
		NetworkModule.GetInstance().SetHandler(AGroupListHandler);
	}
}
