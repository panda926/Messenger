package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusiceInfo extends BaseInfo {

	public byte[] MusiceData = null;
    public String MusiceName = "";    // 20
    
	public MusiceInfo() {
		_InfoType = InfoType.Musice;
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount(MusiceName) + 4 + MusiceData.length;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, MusiceName);
            EncodeInteger(bo, MusiceData.length);
            bo.write(MusiceData, 0, MusiceData.length);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		 
         try {
        	 super.FromBytes(is);

             MusiceName = DecodeString(is);
             int length = DecodeInteger(is);
             MusiceData = new byte[length];
			is.read(MusiceData, 0, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
