package com.pearl.hbmsn.ui;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.ui.connector.AConnectorChat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FNewsView extends Fragment{
	 
	public Context m_Context;
	public static FNewsView Instance = null;
	NewsListAdapter m_ListAdapter;
	private View m_RootView;
	private LinearLayout m_Container;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		m_RootView = inflater.inflate(R.layout.news_view, container, false);
		m_Container = (LinearLayout)m_RootView.findViewById(R.id.ll_news);
		try{
			setBackground();
			init();
			m_ListAdapter = new NewsListAdapter(getActivity(), R.layout.news_list_low, CurrentInfo._NewInfoList);
	        ListView listView = (ListView)m_RootView.findViewById(R.id.lv_news);
	        listView.setAdapter(m_ListAdapter);
	  
	        listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position,
						long id) {
					startChat(position);
					//Toast.makeText(m_Context, "c click = " + position, Toast.LENGTH_SHORT).show();
					
				}
	        	
	        });
	        Instance = this;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
        return m_RootView;
	}
	@SuppressLint("ResourceAsColor") public void RefreshView(){
		m_ListAdapter = null;
		m_ListAdapter = new NewsListAdapter(getActivity(), R.layout.news_list_low, CurrentInfo._NewInfoList);
		m_ListAdapter.notifyDataSetChanged();
		ListView listView = (ListView)m_RootView.findViewById(R.id.lv_news);
        listView.setAdapter(m_ListAdapter);
        setBackground();
	}
	private void init(){
		m_Context = getActivity();
	}
	private void setBackground(){
		 if(CurrentInfo._NewInfoList.size() == 0)
	        	m_Container.setBackgroundResource(R.drawable.news_back);
	     else
	        	m_Container.setBackgroundResource(R.color.color_fragment_background);
	}
	private void startChat(int position){
		SelectedUserInfo.Id = CurrentInfo._NewInfoList.get(position).m_szId;
		SelectedUserInfo.icon = CurrentInfo._NewInfoList.get(position).m_Icon;
		SelectedUserInfo.RoomId = CurrentInfo._NewInfoList.get(position).m_RoomId;
		SelectedUserInfo.MsgList = CurrentInfo._NewInfoList.get(position).m_szMSG;
		Intent intent = new Intent();
		intent.setClass(m_Context, AConnectorChat.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		String backString = m_Context.getString(R.string.back_news);
		intent.putExtra("back", backString);
		startActivity(intent);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Instance = null;
	}
 
}

