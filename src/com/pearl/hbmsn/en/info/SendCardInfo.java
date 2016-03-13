package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class SendCardInfo extends BaseInfo {

	 public int 						cbPublic;
	 public int 						wCurrentUser;
	 public int 						cbSendCardCount;
	 public int[]						cbCenterCardData = new int[DzCardDefine.MAX_CENTERCOUNT];
	    
	public SendCardInfo() {
		_InfoType = InfoType.SendCard;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(cbPublic);
        size += EncodeCount(wCurrentUser);
        size += EncodeCount(cbSendCardCount);

        for (int i = 0; i < cbCenterCardData.length; i++)
            size += EncodeCount(cbCenterCardData[i]);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, cbPublic);
            EncodeInteger(bo, wCurrentUser);
            EncodeInteger(bo, cbSendCardCount);

            for (int i = 0; i < cbCenterCardData.length; i++)
                EncodeInteger(bo, cbCenterCardData[i]);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		try
        {
            super.FromBytes(is);

            cbPublic = DecodeInteger(is);
            wCurrentUser = DecodeInteger(is);
            cbSendCardCount = DecodeInteger(is);

            for (int i = 0; i < cbCenterCardData.length; i++)
                cbCenterCardData[i] = DecodeInteger(is);
        }
        catch(Exception ex)
        { ex.printStackTrace();}
	}

}
