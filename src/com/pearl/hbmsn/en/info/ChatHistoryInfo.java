package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class ChatHistoryInfo extends BaseInfo {

	public int Id = 0;

    public String BuyerId = "";
    public String ServicemanId = "";
    public String OfficerId = "";
    public String ManagerId = "";

    public int ServicePrice = 0;
    public Date StartTime;
    public Date EndTime;

    public int BuyerTotal = 0;
    public int ServicemanTotal = 0;
    public int ServiceOfficerTotal = 0;
    public int ManagerTotal = 0;

    // server
    public String RoomId = "";
    
	public ChatHistoryInfo() {
		_InfoType = InfoType.ChatHistory;
	}

	@Override
	public int GetSize() {
		 int size = super.GetSize();

         size += EncodeCount(BuyerId);
         size += EncodeCount(ServicemanId);

         size += EncodeCount(ServicePrice);
         size += EncodeCount(ConvDateToLongString( StartTime ));
         size += EncodeCount(ConvDateToLongString( EndTime ));

         size += EncodeCount(BuyerTotal);
         size += EncodeCount(ServicemanTotal);
         size += EncodeCount(ServiceOfficerTotal);
         size += EncodeCount(ManagerTotal);

         return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString( bo, BuyerId );
            EncodeString( bo, ServicemanId );

            EncodeInteger( bo, ServicePrice );
            EncodeString( bo, ConvDateToString( StartTime ));
            EncodeString( bo, ConvDateToString( EndTime ));

            EncodeInteger( bo, BuyerTotal );
            EncodeInteger( bo, ServicemanTotal );
            EncodeInteger(bo, ServiceOfficerTotal);
            EncodeInteger(bo, ManagerTotal);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		 super.FromBytes(is);

         BuyerId = DecodeString( is );
         ServicemanId = DecodeString(is );

         ServicePrice = DecodeInteger(is);
         StartTime = ToDateTime( DecodeString(is));
         EndTime = ToDateTime(DecodeString(is));

         BuyerTotal = DecodeInteger(is);
         ServicemanTotal = DecodeInteger(is);
         ServiceOfficerTotal = DecodeInteger(is);
         ManagerTotal = DecodeInteger(is);
	}
	
	

}
