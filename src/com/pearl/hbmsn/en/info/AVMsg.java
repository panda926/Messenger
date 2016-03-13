package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class AVMsg extends BaseInfo {

	 public String _strRoomID = "";

     public String _strLocalIP = "";
     public int _nLocalPort = 0;
     public String _strRemoteIP = "";
     public int _nRemotePort = 0;

     public int bBitCount = 0;
     public int biClrImportant = 0;
     public int biClrUsed = 0;
     public int biCompression = 0;
     public int biHeight = 0;
     public int biPlanes = 0;
     public int biSize = 0;
     public int biSizeImage = 0;
     public int biWidth = 0;
     public int biXPelsPerMeter = 0;
     public int biYPelsPerMeter = 0;
     
	public AVMsg() {
		_InfoType = InfoType.AVMsg;
	}

	@Override
	public int GetSize() {
		
		 int size = super.GetSize();

         size += EncodeCount(_strRoomID);
         size += EncodeCount(_strLocalIP);
         size += EncodeCount(_nLocalPort);
         size += EncodeCount(_strRemoteIP);
         size += EncodeCount(_nRemotePort);

         size += EncodeCount(bBitCount);
         size += EncodeCount(biClrImportant);
         size += EncodeCount(biClrUsed);
         size += EncodeCount(biCompression);
         size += EncodeCount(biHeight);
         size += EncodeCount(biPlanes);
         size += EncodeCount(biSize);
         size += EncodeCount(biSizeImage);
         size += EncodeCount(biWidth);
         size += EncodeCount(biXPelsPerMeter);
         size += EncodeCount(biYPelsPerMeter);

         return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 
		try{
             super.GetBytes(bo);

             EncodeString(bo, _strRoomID);
             EncodeString(bo, _strLocalIP);
             EncodeInteger(bo, _nLocalPort);
             EncodeString(bo, _strRemoteIP);
             EncodeInteger(bo, _nRemotePort);

             EncodeInteger(bo, bBitCount);
             EncodeInteger(bo, biClrImportant);
             EncodeInteger(bo, biClrUsed);
             EncodeInteger(bo, biCompression);
             EncodeInteger(bo, biHeight);
             EncodeInteger(bo, biPlanes);
             EncodeInteger(bo, biSize);
             EncodeInteger(bo, biSizeImage);
             EncodeInteger(bo, biXPelsPerMeter);
             EncodeInteger(bo, biYPelsPerMeter);
         }
         catch (Exception ex){ 
        	 ex.printStackTrace();
         }
	}

	@Override
	public void FromBytes(InputStream is) {

		super.FromBytes(is);

        _strRoomID = DecodeString(is);
        _strLocalIP = DecodeString(is);
        _nLocalPort = DecodeInteger(is);
        _strRemoteIP = DecodeString(is);
        _nRemotePort = DecodeInteger(is);

        bBitCount = DecodeInteger(is);
        biClrImportant = DecodeInteger(is);
        biClrUsed = DecodeInteger(is);
        biCompression = DecodeInteger(is);
        biHeight = DecodeInteger(is);
        biPlanes = DecodeInteger(is);
        biSize = DecodeInteger(is);
        biSizeImage = DecodeInteger(is);
        biXPelsPerMeter = DecodeInteger(is);
        biYPelsPerMeter = DecodeInteger(is);
	}
	
	
}
