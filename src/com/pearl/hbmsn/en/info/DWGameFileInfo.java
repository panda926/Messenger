package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DWGameFileInfo extends BaseInfo {

	public ArrayList<String> listGameFile = new ArrayList<String>();
    public GameInfo gameInfo = new GameInfo();
    
	public DWGameFileInfo() {
		_InfoType = InfoType.DWGameFile;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize() + 4;

        for (int i = 0; i < listGameFile.size(); i++)
        {
            size += EncodeCount(listGameFile.get(i));
        }

        size += gameInfo.GetSize();

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeInteger(bo, listGameFile.size());

             for (int i = 0; i < listGameFile.size(); i++)
             {
                 EncodeString(bo, listGameFile.get(i));
             }

             gameInfo.GetBytes(bo);
         }
         catch (Exception ex)
         {
             ex.printStackTrace();
         }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        int nDWFileCount = DecodeInteger(is);

        for (int i = 0; i < nDWFileCount; i++)
        {
            //DecodeInteger(br);

            String strFileName = DecodeString(is);
            listGameFile.add(strFileName);
        }

        gameInfo = (GameInfo)BaseInfo.CreateInstance(is);
	}

}
