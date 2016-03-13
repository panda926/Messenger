package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class PresentHistoryInfo extends BaseInfo {

    public String SendId = "";
    public String ReceiveId = "";
    public int Cash = 0;
    public String Descripiton = "";
    public Date SendTime;

    // 2013-12-29: GreenRose
    public String strRoomID = "";
    
	public PresentHistoryInfo() {
		_InfoType = InfoType.PresentHistory;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(SendId);
        size += EncodeCount(ReceiveId);
        size += EncodeCount(Cash);
        size += EncodeCount(Descripiton);
        size += EncodeCount(ConvDateToString(SendTime));

        size += EncodeCount(strRoomID);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, SendId );
            EncodeString(bo, ReceiveId );
            EncodeInteger(bo, Cash);
            EncodeString(bo, Descripiton);
            EncodeString(bo, ConvDateToString(SendTime));

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

        SendId = DecodeString(is);
        ReceiveId = DecodeString(is);
        Cash = DecodeInteger(is);
        Descripiton = DecodeString(is);
        SendTime = ToDateTime(DecodeString(is));

        strRoomID = DecodeString(is);
	}

}
