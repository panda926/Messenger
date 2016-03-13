package com.pearl.hbmsn.ui.photo;


import java.util.ArrayList;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pearl.hbmsn.R;




import com.pearl.hbmsn.volley.AppController;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class FolderListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	ArrayList<FolderInfo> m_FolderInfoArray;
	private int mListLayout;
	private FolderViewHolder folderViewHolder = null;
	public String TAG = "listAdapter";
	public int listCount = 0;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private Context m_Context;
	public FolderListAdapter(Context tContext, int listLayout, ArrayList<FolderInfo> folderInfoArray) {
		mListLayout = listLayout;
		m_FolderInfoArray = folderInfoArray;
		m_Context = tContext;
		inflater = LayoutInflater.from(tContext);
		
		if(folderInfoArray != null)
			listCount = folderInfoArray.size();
	}

	
	@Override
	public int getCount() {
		
		return listCount;
	}

	//사용하지 않음
	@Override
	public Object getItem(int position) {
		return m_FolderInfoArray.get(position);
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
			folderViewHolder = new FolderViewHolder();
			v = inflater.inflate(mListLayout, parent, false);
			folderViewHolder.iv_image = (NetworkImageView) v.findViewById(R.id.iv_icon_folder);
			folderViewHolder.tv_Name = (TextView)v.findViewById(R.id.tv_title_folder);
			folderViewHolder.tv_Kind = (TextView)v.findViewById(R.id.tv_kind_folder);
			folderViewHolder.tv_Count = (TextView)v.findViewById(R.id.tv_count_folder);
			folderViewHolder.tv_Time = (TextView)v.findViewById(R.id.tv_release_time);
			v.setTag(folderViewHolder);
		}
		else{
			folderViewHolder = (FolderViewHolder)v.getTag();
		}
		
		folderViewHolder.tv_Name.setText(m_FolderInfoArray.get(position)._Name);
		folderViewHolder.tv_Kind.setText(m_FolderInfoArray.get(position)._Kind);
		folderViewHolder.tv_Count.setText(String.valueOf(m_FolderInfoArray.get(position)._Count) + m_Context.getString(R.string.photo_unit));
		folderViewHolder.tv_Time.setText(m_FolderInfoArray.get(position)._Time);
		
		//folderViewHolder.iv_image.setDefaultImageResId(R.drawable.temp);
		folderViewHolder.iv_image.setImageUrl(m_FolderInfoArray.get(position)._ImageUrl, imageLoader);		
		
		return v;
	}

	public ArrayList<FolderInfo> getObjects(){
		return m_FolderInfoArray;
	}
	
	public void setObjects(ArrayList<FolderInfo> objects){
		this.m_FolderInfoArray = objects;
	}
	
	class FolderViewHolder{
		public NetworkImageView	iv_image;
		public TextView		tv_Name;
		public TextView		tv_Time;
		public TextView		tv_Kind;
		public TextView		tv_Count;
	}
}
