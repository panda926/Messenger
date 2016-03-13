package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VideoInfo extends BaseInfo {

	public String UserId = "";    // 20
    public int IsEnd = 0;
    public byte[] Data = null;
    public byte[] ImgData = null;
    public String ImgName = "";    // 20
    
	public VideoInfo() {
		_InfoType = InfoType.Video;
	}

	@Override
	public int GetSize() {
		 return super.GetSize() + EncodeCount(UserId) + EncodeCount(IsEnd) + 4 + Data.length + 4 + ImgData.length + EncodeCount(ImgName);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeString(bo, UserId );
             EncodeInteger(bo, IsEnd);

             EncodeInteger( bo, Data.length);
             bo.write(Data, 0, Data.length);
             EncodeInteger(bo, ImgData.length);
             bo.write(ImgData, 0, ImgData.length);

             EncodeString(bo, ImgName);
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
	
	        UserId = DecodeString(is);
	        IsEnd = DecodeInteger(is);
	
	        int length = DecodeInteger(is);
	
	        Data = new byte[length];
	        
			is.read(Data, 0, length);
			
			int imglength = DecodeInteger(is);
	
	        ImgData = new byte[imglength];
	        is.read(ImgData, 0, imglength);
	
	        ImgName = DecodeString(is);
        
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}

}
