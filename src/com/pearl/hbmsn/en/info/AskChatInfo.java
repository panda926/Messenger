package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class AskChatInfo extends BaseInfo {

	 public String TargetId = "";
     public String AskContent = "";
     public int Price = 0;
     
     public int Agree = 0;
     public String AskingID = "";
     public RoomInfo MeetingInfo = new RoomInfo();
     
	public AskChatInfo(){
		_InfoType = InfoType.AskChat;
	}
	@Override
	public int GetSize() {
        return super.GetSize() + 
        		EncodeCount( TargetId ) + 
        		EncodeCount( AskContent ) + 
        		EncodeCount( Price ) + 
        		EncodeCount( Agree ) +
        		EncodeCount( AskingID ) +
        		MeetingInfo.GetSize();
    }

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString( bo, TargetId );
            EncodeString(bo, AskContent );
            EncodeInteger(bo, Price);
            EncodeInteger(bo, Agree);
            EncodeString(bo, AskingID);
            
            MeetingInfo.GetBytes(bo);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		try{
			super.FromBytes(is);

	        TargetId = DecodeString(is);
	        AskContent = DecodeString(is);
	        Price = DecodeInteger(is);
	        
	        Agree = DecodeInteger(is);
	        AskingID = DecodeString(is);
	        DecodeInteger(is);
	        MeetingInfo.FromBytes(is);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
