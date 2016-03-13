package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class StringInfo extends BaseInfo {

	public String UserId = "";   // 20
    public String String = "";

    public int FontSize = 0;
    public String FontName = "";
    public String FontStyle = "";
    public String FontWeight = "";
    public String FontColor = "";

    // 2013-12-25: GreenRose
    public String strRoomID = "";
    
	public StringInfo() {
		_InfoType = InfoType.String;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();
        
        size += EncodeCount(UserId);
        size += EncodeCount(String);
        size += EncodeCount( FontSize );
        size += EncodeCount( FontName );
        size += EncodeCount( FontStyle );
        size += EncodeCount( FontWeight );
        size += EncodeCount( FontColor );
        size += EncodeCount(strRoomID);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeString( bo, UserId );
            EncodeString(bo, String );
            EncodeInteger(bo, FontSize );
            EncodeString(bo, FontName );
            EncodeString(bo, FontStyle );
            EncodeString(bo, FontWeight );
            EncodeString(bo, FontColor );
            EncodeString(bo, strRoomID);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        UserId = DecodeString(is);
        String = DecodeString(is);
        FontSize = DecodeInteger(is);
        FontName = DecodeString(is);;
        FontStyle = DecodeString(is);
        FontWeight = DecodeString(is);
        FontColor = DecodeString(is);
        strRoomID = DecodeString(is);
	}

}
