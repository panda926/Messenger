package com.pearl.hbmsn.ui;

import java.util.ArrayList;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;
import com.pearl.hbmsn.ui.connector.AConnectorDetail;

import android.content.Intent;
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


public class FConnectorsView extends Fragment{
	
		private ExpandableListView m_ListView;
		private ArrayList<String> m_GroupList = null;
		private ArrayList<ArrayList<ConnectorInfo>> m_ChildList = null;
		private ArrayList<ConnectorInfo> m_VIPSellerList = null;
		private ArrayList<ConnectorInfo> m_ChatterList = null;
		private ConnectorListAdapter m_ConnectorListAdapter = null;
		public static FConnectorsView Instance = null;
		private View m_RootView;
		
		public void InputData(){
			
			if(CurrentInfo._UserInfo.Kind == 0)//0이면 남자 1이면 녀자
				m_GroupList.add(getString(R.string.vip_group));
			m_GroupList.add(getString(R.string.connector_group));
			
			for(int nId = 0; nId < CurrentInfo._UserListInfo._Users.size(); nId++){
				
				UserInfo userInfo = CurrentInfo._UserListInfo._Users.get(nId);
				if(userInfo.nVIP == 2)//VIP판매원이면
					m_VIPSellerList.add(new ConnectorInfo(getActivity(), userInfo));
				else//일반유저이면
					m_ChatterList.add(new ConnectorInfo(getActivity(), userInfo));
			}
			
			if(CurrentInfo._UserInfo.Kind == 0)//0이면 남자 1이면 녀자
				m_ChildList.add(m_VIPSellerList);
			m_ChildList.add(m_ChatterList);
			
			m_ConnectorListAdapter = new ConnectorListAdapter(getActivity(), m_GroupList, m_ChildList);
			m_ListView.setAdapter(m_ConnectorListAdapter);
			
		}
		
		public void RefreshView(){
			m_ConnectorListAdapter = null;
			m_VIPSellerList.clear();
			m_ChatterList.clear();
			for(int nId = 0; nId < CurrentInfo._UserListInfo._Users.size(); nId++){
				UserInfo userInfo = CurrentInfo._UserListInfo._Users.get(nId);
				if(userInfo.nVIP == 2)//VIP판매원이면
					m_VIPSellerList.add(new ConnectorInfo(getActivity(), userInfo));
				else//일반유저이면
					m_ChatterList.add(new ConnectorInfo(getActivity(), userInfo));
			
			}
			if(CurrentInfo._UserInfo.Kind == 0)//0이면 남자 1이면 녀자
				m_ChildList.add(m_VIPSellerList);
			m_ChildList.add(m_ChatterList);
			m_ConnectorListAdapter = new ConnectorListAdapter(getActivity(), m_GroupList, m_ChildList);
			m_ConnectorListAdapter.notifyDataSetChanged();
			m_ListView = (ExpandableListView)m_RootView.findViewById(R.id.connectors_list);
			m_ListView.setAdapter(m_ConnectorListAdapter);
			
		}
		private void initializeContent(){
			
			m_GroupList = new ArrayList<String>();
			m_ChildList = new ArrayList<ArrayList<ConnectorInfo>>();
			m_VIPSellerList = new ArrayList<ConnectorInfo>();
			m_ChatterList = new ArrayList<ConnectorInfo>();
			InputData();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			m_RootView = inflater.inflate(R.layout.connectors_view, container, false);
			m_ListView = (ExpandableListView)m_RootView.findViewById(R.id.connectors_list);
			
			try{
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
							//새로운 액티비티를 창조하기전에 현재 설정된 유저정보를 보관한다.
							setSelectItemInfo(groupPosition, childPosition);
							vewDetail();
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
				Instance = this;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return m_RootView;
		}
		

		
		private void setSelectItemInfo(int groupPosition, int childPosition){
			
			SelectedUserInfo.Id = m_ChildList.get(groupPosition).get(childPosition).m_Name;
			SelectedUserInfo.icon = m_ChildList.get(groupPosition).get(childPosition).m_IconString;
			SelectedUserInfo.Address = m_ChildList.get(groupPosition).get(childPosition).m_Address;
			SelectedUserInfo.NickName = m_ChildList.get(groupPosition).get(childPosition).m_NickName;
			SelectedUserInfo.Sign = m_ChildList.get(groupPosition).get(childPosition).m_Sign;
			SelectedUserInfo.Year = m_ChildList.get(groupPosition).get(childPosition).m_nYear;
			SelectedUserInfo.Month = m_ChildList.get(groupPosition).get(childPosition).m_nMonth;
			SelectedUserInfo.Day = m_ChildList.get(groupPosition).get(childPosition).m_nDay;
			
			CurrentInfo._selectUserInfo = CurrentInfo._UserListInfo._Users.get(childPosition);
			
			int ErrorValue = NetworkModule.GetInstance().sendInfo(NotifyType.Request_PartnerDetail, CurrentInfo._selectUserInfo);
			if(ErrorValue == 1){
				NetworkModule.GetInstance().Reconnect();
			}
		}
		private void vewDetail(){
			Intent intent = new Intent();
			intent.setClass(getActivity(), AConnectorDetail.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			Instance = null;
		}
}

