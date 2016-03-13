package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class RoomDetailInfo extends BaseInfo {

	public ArrayList<UserInfo> Users = new ArrayList<UserInfo>();
    public ArrayList<IconInfo> Emoticons = new ArrayList<IconInfo>();
    public ArrayList<IconInfo> Presents = new ArrayList<IconInfo>();

    public String strRoomID = "";
    
	public RoomDetailInfo() {
		_InfoType = InfoType.RoomDetail;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize() + 4 + 4 + 4;

        for (int i = 0; i < Users.size(); i++)
        {
            size += Users.get(i).GetSize();
        }

        for (int i = 0; i < Emoticons.size(); i++)
        {
            size += Emoticons.get(i).GetSize();
        }

        for (int i = 0; i < Presents.size(); i++)
        {
            size += Presents.get(i).GetSize();
        }

        size += EncodeCount(strRoomID);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);
             
             EncodeInteger(bo, Users.size());
             EncodeInteger(bo, Emoticons.size());
             EncodeInteger(bo, Presents.size());

             for (int i = 0; i < Users.size(); i++)
                 Users.get(i).GetBytes(bo);

             for (int i = 0; i < Emoticons.size(); i++)
                 Emoticons.get(i).GetBytes(bo);

             for (int i = 0; i < Presents.size(); i++)
                 Presents.get(i).GetBytes(bo);

             EncodeString(bo, strRoomID);
         }
         catch (Exception ex)
         {
        	 ex.printStackTrace(); 
         }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        int userCount = DecodeInteger(is);
        int emoticonCount = DecodeInteger(is);
        int presentCount = DecodeInteger(is);

        for (int i = 0; i < userCount; i++)
        {
            UserInfo userInfo = (UserInfo)BaseInfo.CreateInstance(is);
            Users.add(userInfo);
        }

        for (int i = 0; i < emoticonCount; i++)
        {
            IconInfo presentInfo = (IconInfo)BaseInfo.CreateInstance(is);
            Emoticons.add(presentInfo);
        }

        for (int i = 0; i < presentCount; i++)
        {
            IconInfo presentInfo = (IconInfo)BaseInfo.CreateInstance(is);
            Presents.add(presentInfo);
        }

        strRoomID = DecodeString(is);

	}

}
