package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class RoomInfo extends BaseInfo {

	public String Id = "";         // 20
    public String Name = "";       // 20
    public int Kind;               // 4
    public String Icon = "";       // 50
    
    public String Owner = "";      // 20
    public int UserCount = 0;

    public int Cash = 0;
    public int Point = 0;
    public int MaxUsers = 0;
    //public int Total = 0;
    public String RoomPass = "";

    public int IsGame = 0;
    public GameInfo _GameInfo = null;
    
   
    //public List<UserInfo> listUserInfo = new List<UserInfo>();
    //public List<string> listUserID = new List<string>();

    public String _strUserID = "";
    public String _strTargetID = "";
    
	public RoomInfo() {
		_InfoType = InfoType.Room;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount( Id );
        size += EncodeCount( Name );
        size += EncodeCount( Kind );
        size += EncodeCount( Icon );
        size += EncodeCount( Owner );
        size += EncodeCount( UserCount );
        size += EncodeCount(Cash);
        size += EncodeCount(Point);
        size += EncodeCount(MaxUsers);
        size += EncodeCount(IsGame);
        size += EncodeCount(RoomPass);

        if (IsGame > 0)
            size += _GameInfo.GetSize();

        size += EncodeCount(_strUserID);
        size += EncodeCount(_strTargetID);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, Id );
            EncodeString(bo, Name );
            EncodeInteger(bo, Kind);
            EncodeString(bo, Icon );
            EncodeString(bo, Owner );
            EncodeInteger(bo, UserCount);
            EncodeInteger(bo, Cash);
            EncodeInteger(bo, Point);
            EncodeInteger(bo, MaxUsers);
            EncodeInteger(bo, IsGame);
            EncodeString(bo, RoomPass);

            if (IsGame > 0)
                _GameInfo.GetBytes(bo);

            EncodeString(bo, _strUserID);
            EncodeString(bo, _strTargetID);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		
		super.FromBytes(is);

        Id = DecodeString(is);
        Name = DecodeString(is);
        Kind = DecodeInteger(is);
        Icon = DecodeString(is);
        Owner = DecodeString(is);
        UserCount = DecodeInteger(is);
        Cash = DecodeInteger(is);
        Point = DecodeInteger(is);
        MaxUsers = DecodeInteger(is);
        IsGame = DecodeInteger(is);
        RoomPass = DecodeString(is);

        if (IsGame > 0)
            _GameInfo = (GameInfo)BaseInfo.CreateInstance(is);

        _strUserID = DecodeString(is);
        _strTargetID = DecodeString(is);
	}
	
}
