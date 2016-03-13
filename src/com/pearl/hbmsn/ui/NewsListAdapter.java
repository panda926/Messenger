package com.pearl.hbmsn.ui;


import java.util.ArrayList;

import com.pearl.hbmsn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class NewsListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	ArrayList<NewsInfo> m_NewsInfoArray;
	//private Context	mContext;
	private int mListLayout;
	private NewsViewHolder newsHolder = null;
	public String TAG = "listAdapter";
	public int listCount = 0;
	
	public NewsListAdapter(Context tContext, int listLayout, ArrayList<NewsInfo> newsInfoArray) {
		//mContext = tContext;
		mListLayout = listLayout;
		m_NewsInfoArray = newsInfoArray;
		inflater = LayoutInflater.from(tContext);
		
		if(newsInfoArray != null)
			listCount = newsInfoArray.size();
	}

	@Override
	public int getCount() {
		
		return listCount;
	}

	//사용하지 않음
	@Override
	public Object getItem(int position) {
		return m_NewsInfoArray.get(position);
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
			newsHolder.tv_Name = (TextView) v.findViewById(R.id.tv_name_news);
			newsHolder.tv_Content = (TextView)v.findViewById(R.id.tv_content_news);
			newsHolder.iv_icon = (ImageView)v.findViewById(R.id.iv_icon_news);
			newsHolder.tv_Time = (TextView)v.findViewById(R.id.tv_time_news);
			v.setTag(newsHolder);
		}
		else{
			newsHolder = (NewsViewHolder)v.getTag();
		}
		newsHolder.tv_Name.setText(m_NewsInfoArray.get(position).m_szId);
		newsHolder.tv_Content.setText(m_NewsInfoArray.get(position).m_szMSG.get(m_NewsInfoArray.get(position).m_szMSG.size()-1));
		newsHolder.iv_icon.setImageResource(ConvertMgr.GetResId(m_NewsInfoArray.get(position).m_Icon));
		newsHolder.tv_Time.setText(m_NewsInfoArray.get(position).m_szTime);
		return v;
	}

	class NewsViewHolder{
		public ImageView	iv_icon;
		public TextView		tv_Name;
		public TextView		tv_Content;
		public TextView		tv_Time;
	}
}
