package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VoiceInfo extends BaseInfo {

	public String UserId = "";
    public int PlayState = 0;

    public ArrayList<byte[]> Frames = new ArrayList<byte[]>();
    
	public VoiceInfo() {
		_InfoType = InfoType.Voice;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(UserId);
        size += EncodeCount(PlayState);
        
        size += 4;

        for( int i = 0; i < Frames.size(); i++ )
        {
            size += 4;
            size += Frames.get(i).length;
        }

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeString(bo, UserId );
             EncodeInteger(bo, PlayState);

             EncodeInteger( bo, Frames.size());

             for (int i = 0; i < Frames.size(); i++)
             {
                 EncodeInteger(bo, Frames.get(i).length);
                 bo.write(Frames.get(i));
             }
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
	        PlayState = DecodeInteger(is);
	
	        int frameCount = DecodeInteger(is);
	
	        for (int i = 0; i < frameCount; i++)
	        {
	            int length = DecodeInteger(is);
	
	            byte[] frame = new byte[length];
	            
				is.read(frame, 0, length);
				
	            Frames.add(frame);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
