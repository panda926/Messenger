package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class RoomListInfo extends BaseInfo {

	private ArrayList<RoomInfo> _Rooms = new ArrayList<RoomInfo>();
	public RoomListInfo() {
		_InfoType = InfoType.RoomList;
	}
	public ArrayList<RoomInfo> GetRooms(){
		return _Rooms;
	}
	public void SetRooms(ArrayList<RoomInfo> rooms){
		_Rooms = rooms;
	}
	@Override
	public int GetSize() {
        
		int size = super.GetSize() + 4;

        for (int i = 0; i < _Rooms.size(); i++)
        {
            size += _Rooms.get(i).GetSize();
        }

        return size;
	}
	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);
            EncodeInteger(bo, _Rooms.size());

            for (int i = 0; i < _Rooms.size(); i++)
                _Rooms.get(i).GetBytes(bo);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}
	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        int count = DecodeInteger(is);

        for (int i = 0; i < count; i++)
        {
            RoomInfo roomInfo = new RoomInfo();
            DecodeInteger(is);

            roomInfo.FromBytes(is);

            _Rooms.add(roomInfo);
        }
	}

}
