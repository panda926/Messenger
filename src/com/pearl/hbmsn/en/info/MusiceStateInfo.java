package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class MusiceStateInfo extends BaseInfo {

	 public String MusiceName = "";       // 20
     public int M_Kind = 0;
     static public String[] M_KindList = { "Paly", "Pause", "Stop" };
     
	public MusiceStateInfo() {
		_InfoType = InfoType.MusiceState;
	}

	public String GetKindString(){
		return M_KindList[M_Kind];
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount(MusiceName) + EncodeCount(M_Kind);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, MusiceName);
            EncodeInteger(bo, M_Kind);
        }
        catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		 super.FromBytes(is);

         MusiceName = DecodeString(is);
         M_Kind = DecodeInteger(is);
	}

}
