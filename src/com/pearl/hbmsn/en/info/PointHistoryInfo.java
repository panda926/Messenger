package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class PointHistoryInfo extends BaseInfo {

	public int Id = 0;
    public String TargetId = "";
    public int Point = 0;

    public Date AgreeTime;
    public String Content;
    public int Kind;
    
	public PointHistoryInfo() {
		_InfoType = InfoType.PointHistory;
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount(TargetId) + EncodeCount(Point) + EncodeCount(ConvDateToString(AgreeTime)) + EncodeCount(Content);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeString(bo, TargetId);
            EncodeInteger(bo, Point);
            EncodeString(bo, ConvDateToString(AgreeTime));
            EncodeString(bo, Content);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        TargetId = DecodeString(is);
        Point = DecodeInteger(is);
        AgreeTime = ToDateTime(DecodeString(is));
        Content = DecodeString(is);
	}

}
