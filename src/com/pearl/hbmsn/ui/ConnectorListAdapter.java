package com.pearl.hbmsn.ui;

import java.util.ArrayList;

import com.pearl.hbmsn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnectorListAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> groupList = null;
	private ArrayList<ArrayList<ConnectorInfo>> content = null;
	private LayoutInflater inflater = null;
	private ChildViewHolder viewHolder = null;
	private GroupViewHolder groupViewHolder = null;
	
	public ConnectorListAdapter(Context c, ArrayList<String> groupList,ArrayList<ArrayList<ConnectorInfo>> content){
        super();
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
            v = inflater.inflate(R.layout.connectors_list_low, null);
            viewHolder.iv_Icon = (ImageView) v.findViewById(R.id.iv_iconimage);
            viewHolder.tv_Name = (TextView) v.findViewById(R.id.tv_name);
            viewHolder.tv_State = (TextView) v.findViewById(R.id.tv_state);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ChildViewHolder)v.getTag();
        }
        ConnectorInfo buffer = null;
        buffer = (ConnectorInfo)getChild(groupPosition, childPosition);
        viewHolder.tv_Name.setText(buffer.m_Name);
        viewHolder.tv_State.setText(buffer.m_State);
        viewHolder.iv_Icon.setImageResource(ConvertMgr.GetResId(buffer.m_IconString));
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
            v = inflater.inflate(R.layout.connectors_list_low, parent, false);
            groupViewHolder.iv_Icon = (ImageView) v.findViewById(R.id.iv_icon);
            groupViewHolder.tv_GroupName = (TextView) v.findViewById(R.id.tv_group);
            groupViewHolder.tv_ChildCount = (TextView) v.findViewById(R.id.tv_number);
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
        groupViewHolder.tv_ChildCount.setText(String.valueOf(content.get(groupPosition).size()));
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
	//ExpandableListView에서 child정보를 보여주는 뷰에 대응한 클라스
	class ChildViewHolder {
		public ImageView iv_Icon;//그림기호, 혹은 사진
        public TextView tv_Name;//채팅자의 이름을 보여주는 텍스트
        public TextView tv_State;//채팅자의 현재상태를 보여주는 텍스트
	}
	
	//ExpandableList에서 그룹정보를 보여주는 뷰에 대응한 클라스
	class GroupViewHolder{
		public ImageView	iv_Icon;//그림기호, 혹은 사진
		public TextView		tv_GroupName;//그륩이름
		public TextView		tv_ChildCount;//그륩애 표시할 자식의 수
	}
}

	


