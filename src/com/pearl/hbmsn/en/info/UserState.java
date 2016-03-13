package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class UserState extends BaseInfo {

	public String _strUserID = "";
    public int _nUserState = 0;     // 0: Online, 1:Offline, 2: Busy, 3: GoAway
    
	public UserState() {
		_InfoType = InfoType.UserState;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(_strUserID);
        size += EncodeCount(_nUserState);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, _strUserID);
            EncodeInteger(bo, _nUserState);
        }
        catch (Exception ex)
        { ex.printStackTrace();}
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        _strUserID = DecodeString(is);
        _nUserState = DecodeInteger(is);
	}

}
