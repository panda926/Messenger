package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class GameDetailInfo extends BaseInfo {

	private String _Properties = "";
	
	public GameDetailInfo() {
		 _InfoType = InfoType.GameDetail;
	}

	@Override
	public int GetSize() {
		 return super.GetSize() + EncodeCount( _Properties );
	}
	public String GetProperties(){
		return _Properties;
	}
	public void SetProperties(String str){
		_Properties = str;
	}
	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, _Properties );
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        _Properties = DecodeString( is );
	}

}
