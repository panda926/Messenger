package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class RoomPrice extends BaseInfo {

	public String RoomId = "";         // 20
    public String UserId = "";         // 20
    public String ReceiveId = "";
    public int RoomValue = 0;       // 20
    
	public RoomPrice() {
		_InfoType = InfoType.RoomValue;
	}

	@Override
	public int GetSize() {
        int size = super.GetSize();

        size += EncodeCount(RoomId);
        size += EncodeCount(UserId);
        size += EncodeCount(ReceiveId);
        size += EncodeCount(RoomValue);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeString(bo, RoomId);
            EncodeString(bo, UserId);
            EncodeString(bo, ReceiveId);
            EncodeInteger(bo, RoomValue);

        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        RoomId = DecodeString(is);
        UserId = DecodeString(is);
        ReceiveId = DecodeString(is);
        RoomValue = DecodeInteger(is);
	}

}
