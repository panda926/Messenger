package com.pearl.hbmsn.ui;

import java.util.ArrayList;

import com.pearl.hbmsn.R;



import com.pearl.hbmsn.en.info.ClassInfo;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.ui.photo.AGroupList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class FPhotosView extends Fragment{
	 
	private ExpandableListView m_ListView;
	private ArrayList<String> m_GroupList = null;
	private ArrayList<ArrayList<ClassInfo>> m_ChildList = null;
	private ArrayList<ClassInfo> m_PhotoList = null;
	private ArrayList<ClassInfo> m_FilmList = null;
	private ArrayList<ClassInfo> m_AVList = null;
	
	public final int CLASS_PHOTO = 0;
	public final int CLASS_NOVLE = 1;
	public final int CLASS_FILM = 2;
	public final int CLASS_SITE = 3;
	public static FPhotosView instance;
	
	public void InputData(){
		
		m_GroupList.add(getString(R.string.photo_group));
		m_GroupList.add(getString(R.string.film_group));
		m_GroupList.add(getString(R.string.AV_group));
		
		for(int nId = 0; nId < CurrentInfo._ClassListInfo._Classes.size(); nId++){
			
			ClassInfo classInfo = CurrentInfo._ClassListInfo._Classes.get(nId);

			
			switch(classInfo.ClassInfo_Id){
			
			case CLASS_PHOTO:
				
				m_PhotoList.add(classInfo);
				break;
			case CLASS_FILM:
				m_FilmList.add(classInfo);
				break;
			case CLASS_SITE:
				m_AVList.add(classInfo);
				break;
			default:
				break;
			}
			
		}
		sortArrayList(m_PhotoList);
		sortArrayList(m_FilmList);
		
		m_ChildList.add(m_PhotoList);
		m_ChildList.add(m_FilmList);
		m_ChildList.add(m_AVList);
		
		m_ListView.setAdapter(new PhotoListAdapter(getActivity(), m_GroupList, m_ChildList));
	}
	
	private void sortArrayList(ArrayList<ClassInfo> classInfoList){
		
		ArrayList<ClassInfo> vipList = new ArrayList<ClassInfo>();
		ArrayList<ClassInfo> normalList = new ArrayList<ClassInfo>();
		ClassInfo classInfoItem;
		
		for(int nId = 0; nId < classInfoList.size(); nId++){
			
			classInfoItem = classInfoList.get(nId);
			
			if(classInfoItem.Class_Type_Name.contains("VIP")) 
				vipList.add(classInfoItem);
			else 
				normalList.add(classInfoItem);
		}
		classInfoList.clear();
		classInfoList.addAll(normalList);
		classInfoList.addAll(vipList);
		
	}
	private void initializeContent(){
		
		m_GroupList = new ArrayList<String>();
		m_ChildList = new ArrayList<ArrayList<ClassInfo>>();
		m_PhotoList = new ArrayList<ClassInfo>();
		m_FilmList = new ArrayList<ClassInfo>();
		m_AVList = new ArrayList<ClassInfo>();
		InputData();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.connectors_view, container, false);
		try{
			
			m_ListView = (ExpandableListView)rootView.findViewById(R.id.connectors_list);
			initializeContent();
			//그룹을 클릭했을때 사건처리부
			m_ListView.setOnGroupClickListener(new OnGroupClickListener(){
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					
					return false;
				}
			});
			
			//자식을 클릭했을때의 사건처리부
			 m_ListView.setOnChildClickListener(new OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent, View view,
							int groupPosition, int childPosition, long id) {
						if(groupPosition == 2)
							goURL(childPosition);
						else
							setSelectedPhotoInfo(groupPosition, childPosition);
						return true;
					}
		    });
			
			
			 
			//그룹이 닫길때의 사건처리부
			m_ListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

				@Override
				public void onGroupCollapse(int groupPosition) {
					//Toast.makeText(getApplicationContext(), "g collapse = " + groupPosition, Toast.LENGTH_SHORT).show();
				}
			});
			//그룹이 열릴때의 사건처리부
			m_ListView.setOnGroupExpandListener(new OnGroupExpandListener() {

				@Override
				public void onGroupExpand(int groupPosition) {
					//Toast.makeText(getApplicationContext(), "g expand= " + groupPosition, Toast.LENGTH_SHORT).show();				
				}
			});
			
			instance = this;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return rootView;
	}
	
	 private void goURL(int position){
		 ClassInfo selectedInfo = m_ChildList.get(2).get(position);
		 Intent intent = new Intent(Intent.ACTION_VIEW);
		 String strURL = "http://" + selectedInfo.Class_Img_Uri;
		 Uri uri = Uri.parse(strURL);
		 intent.setData(uri);
		 startActivity(intent);
	 }
	 private void setSelectedPhotoInfo(int groupPosition, int childPosition){
		 
		 ClassInfo selectedInfo = m_ChildList.get(groupPosition).get(childPosition);
		 
		 if(selectedInfo.ClassCount == 0){
			 return;
		 }
		 if(selectedInfo.Class_Type_Name.contains("VIP")){
			 if(CurrentInfo._UserInfo.nVIP == 0){//nVIP = 0은 vip가 아님
				 //vip성원만이 열람할수 있습니다.
				 Toast.makeText(getActivity(), R.string.msg_vip_able, Toast.LENGTH_LONG).show();
				 return;
			 }
		 }
		 selectedInfo.ToIndex = 1;
		 
		 if(selectedInfo.ClassInfo_Id == 1)
			 selectedInfo.FromIndex = 18;
		 else
			 selectedInfo.FromIndex = 12;
		 
		 CurrentInfo._ClassInfo = selectedInfo;
		 
		 int ErrorType = NetworkModule.GetInstance().sendInfo(NotifyType.Request_ClassTypeInfo, CurrentInfo._ClassInfo);
		 
		 if(ErrorType == 1){
			  NetworkModule.GetInstance().Reconnect();
		 }
		 else if(ErrorType == 2){
			 return;
		 }
		 else if(ErrorType == 0){
			 if(AMainView.Instance != null)
				 AMainView.Instance.ShowLoadingDialog();
		 }
	 }
	 
	 public void ViewGrouplist(){
		Intent intent = new Intent();
		intent.setClass(getActivity(), AGroupList.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
}

