package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class GiveInfo extends BaseInfo {

	 private String _UserId = "";     
     private String _PresentId = "";  
     
	public GiveInfo() {
		_InfoType = InfoType.Give;
	}

	public String GetUserId(){
		return _UserId;
	}
	public void SetUserId(String userId){
		_UserId = userId;
	}
	public String GetPresentId(){
		return _PresentId;
	}
	public void SetPresentId(String presentId){
		_PresentId = presentId;
	}
	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount( _UserId ) + EncodeCount( _PresentId );
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString( bo, _UserId );
            EncodeString( bo, _PresentId );
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        _UserId = DecodeString( is);
        _PresentId = DecodeString( is);
	}

}
