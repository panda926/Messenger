package com.pearl.hbmsn.ui.photo;


import java.util.ArrayList;

import com.pearl.hbmsn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class GroupListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	ArrayList<String> m_GroupName;
	private Context	mContext;
	private int mListLayout;
	private NewsViewHolder newsHolder = null;
	public String TAG = "listAdapter";
	public int listCount = 0;
	
	public GroupListAdapter(Context tContext, int listLayout, ArrayList<String> groupName) {
		mContext = tContext;
		mListLayout = listLayout;
		m_GroupName = groupName;
		inflater = LayoutInflater.from(tContext);
		
		if(groupName != null)
			listCount = groupName.size();
	}

	@Override
	public int getCount() {
		
		return listCount;
	}

	//사용하지 않음
	@Override
	public Object getItem(int position) {
		return m_GroupName.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null){
			newsHolder = new NewsViewHolder();
			v = inflater.inflate(mListLayout, parent, false);
			newsHolder.tv_GroupName = (TextView) v.findViewById(R.id.tv_group_name);
			newsHolder.tv_SubName = (TextView) v.findViewById(R.id.tv_sub_name);
			//newsHolder.tv_allow = (TextView)v.findViewById(R.id.tv_allow);
			v.setTag(newsHolder);
		}
		else{
			newsHolder = (NewsViewHolder)v.getTag();
		}
		newsHolder.tv_GroupName.setText(m_GroupName.get(position));
		newsHolder.tv_SubName.setText(getSubName(position + 1));
		
		//newsHolder.tv_Number.setText(String.valueOf(position + 1));
		return v;
	}

	private String getSubName(int Position){
		String subName = mContext.getString(R.string.sub_title_start) + String.valueOf(Position) + mContext.getString(R.string.sub_title_end);
		return subName;
	}
	class NewsViewHolder{
		
		public TextView		tv_GroupName;
		public TextView		tv_SubName;
	}
}
