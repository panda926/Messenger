package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class UserListInfo extends BaseInfo {

	public ArrayList<UserInfo> _Users = new ArrayList<UserInfo>();
	
	public UserListInfo() {
		_InfoType = InfoType.UserList;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize() + 4;
		
		for(int i = 0; i < _Users.size(); i++)
			size += _Users.get(i).GetSize();
		
		return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
		try{
			
			super.GetBytes(bo);
			
			EncodeInteger(bo, _Users.size());
			
			for(int i = 0; i < _Users.size(); i++)
				_Users.get(i).GetBytes(bo);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int count = DecodeInteger(is);
		for(int i = 0; i < count; i++){
			UserInfo userInfo = (UserInfo)BaseInfo.CreateInstance(is);
			_Users.add(userInfo);
		}
	}

}
