package com.pearl.hbmsn.ui;

import java.util.ArrayList;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.ClassInfo;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoListAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> groupList = null;
	private ArrayList<ArrayList<ClassInfo>> content = null;
	private LayoutInflater inflater = null;
	private ChildViewHolder viewHolder = null;
	private GroupViewHolder groupViewHolder = null;
	private Context	m_Context;
	
	public PhotoListAdapter(Context c, ArrayList<String> groupList,ArrayList<ArrayList<ClassInfo>> content){
        super();
        m_Context = c;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.content = content;
    }
	
	@Override
	public Object getChild(int groupIndex, int childIndex) {
		// TODO Auto-generated method stub
		return content.get(groupIndex).get(childIndex);
	}

	@Override
	public long getChildId(int groupIndex, int childIndex) {
		
		return childIndex;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, 
			View convertView, ViewGroup parent) {
        View v = convertView;
        
        if(v == null){
            viewHolder = new ChildViewHolder();
            v = inflater.inflate(R.layout.photo_list_low, null);
            viewHolder.tv_VIP = (TextView) v.findViewById(R.id.tv_vip);
            viewHolder.tv_Type = (TextView) v.findViewById(R.id.tv_photo_type);
            viewHolder.tv_Count = (TextView) v.findViewById(R.id.tv_photo_count);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ChildViewHolder)v.getTag();
        }
        ClassInfo buffer = null;
        buffer = (ClassInfo)getChild(groupPosition, childPosition);
        viewHolder.tv_Type.setText(buffer.Class_Type_Name);
       
        if(groupPosition == 2){
        	Linkify.addLinks(viewHolder.tv_Type, Linkify.ALL);
        	viewHolder.tv_Count.setText("");
        	viewHolder.tv_VIP.setVisibility(View.INVISIBLE);
        }
        else{
        	viewHolder.tv_Count.setText(String.valueOf(buffer.ClassCount) + m_Context.getString(R.string.photo_unit));        
            //수량이 0이면 검은색, 0보다 크면 붉은색으로 설정
            if( buffer.ClassCount > 0)
            	viewHolder.tv_Count.setTextColor(m_Context.getResources().getColor(R.color.color_red));
            else
            	viewHolder.tv_Count.setTextColor(m_Context.getResources().getColor(R.color.color_text));
            //VIP표식
            if(buffer.Class_Type_Name.contains("VIP")){
            	viewHolder.tv_VIP.setText("VIP");
            	viewHolder.tv_VIP.setBackgroundResource(R.drawable.textview_vip_blue);
            	viewHolder.tv_VIP.setTextColor(m_Context.getResources().getColor(R.color.color_text_blue));
            	viewHolder.tv_VIP.setVisibility(View.VISIBLE);
            }
            else{
            	viewHolder.tv_VIP.setVisibility(View.INVISIBLE);
            }
            
        }
        
        return v;
	}

	
	@Override
	public int getChildrenCount(int groupId) {
		
		return content.get(groupId).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupId) {
		return groupId;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View v = convertView;
        
        if(v == null){
        	groupViewHolder = new GroupViewHolder();
            v = inflater.inflate(R.layout.photo_list_low, parent, false);
            groupViewHolder.iv_Icon = (ImageView) v.findViewById(R.id.iv_icon_photo);
            groupViewHolder.tv_GroupName = (TextView) v.findViewById(R.id.tv_group_photo);
            v.setTag(groupViewHolder);
        }else{
        	groupViewHolder = (GroupViewHolder)v.getTag();
        }
         
        if(isExpanded){
        	groupViewHolder.iv_Icon.setBackgroundResource(R.drawable.group_expand);
        }else{
        	groupViewHolder.iv_Icon.setBackgroundResource(R.drawable.group_normal);
        }
        groupViewHolder.tv_GroupName.setText((CharSequence)getGroup(groupPosition));
        
        return v;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	//ExpandableListView에서 Photo child정보를 보여주는 뷰에 대응한 클라스
	class ChildViewHolder {
		public TextView tv_VIP;//VIP표식
		public TextView tv_Type;//photo타입
        public TextView tv_Count;//photo 수량
	}
	
	//ExpandableList에서 그룹정보를 보여주는 뷰에 대응한 클라스
	class GroupViewHolder{
		public ImageView	iv_Icon;//그림기호, 혹은 사진
		public TextView		tv_GroupName;//그륩이름
	}
}

	


