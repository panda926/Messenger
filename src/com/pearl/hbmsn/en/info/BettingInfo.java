package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class BettingInfo extends BaseInfo {

	public int _UserIndex = 0;
    public int _Area = 0;     
    public int _Score = 0;
	
    public Boolean _isGiveUp = false;
    
    public BettingInfo() {
		_InfoType = InfoType.Betting;
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount( _Area ) + EncodeCount( _Score ) + EncodeCount( _UserIndex );
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
		try{
            super.GetBytes(bo);

            EncodeInteger(bo, _Area );
            EncodeInteger(bo, _Score );
            EncodeInteger(bo, _UserIndex);
        }
		catch (Exception ex){
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		 super.FromBytes(is);

         _Area = DecodeInteger(is);
         _Score = DecodeInteger(is);
         _UserIndex = DecodeInteger(is);
	}

}
