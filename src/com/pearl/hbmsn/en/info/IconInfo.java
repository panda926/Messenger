package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class IconInfo extends BaseInfo {

	 public String Id = "";         // 20
     public String Name = "";       // 20
     
     public String Icon = "";       // 50
     public int Point = 0;          // 4
     
	public IconInfo() {
		_InfoType = InfoType.Present;
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount( Id ) + EncodeCount( Name ) + EncodeCount( Icon ) + EncodeCount( Point );
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeString(bo, Id );
            EncodeString(bo, Name );
            EncodeString(bo, Icon );
            EncodeInteger(bo, Point);
        }
        catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        Id = DecodeString(is);
        Name = DecodeString(is);
        Icon = DecodeString(is);
        Point = DecodeInteger(is);
	}

}
