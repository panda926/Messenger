package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeInfo extends BaseInfo {

	public ArrayList<UserInfo> Users = new ArrayList<UserInfo>();
	public ArrayList<GameInfo> Games = new ArrayList<GameInfo>();
	public ArrayList<BoardInfo> Letters = new ArrayList<BoardInfo>();
	public ArrayList<BoardInfo> Notices = new ArrayList<BoardInfo>();
	
	public HomeInfo() {
		_InfoType = InfoType.Home;
	}

	@Override
	public int GetSize() {
		{
            int size = super.GetSize() + 4 + 4 + 4 + 4;

            for (int i = 0; i < Users.size(); i++)
            {
                size += Users.get(i).GetSize();
            }

            for (int i = 0; i < Games.size(); i++)
            {
                size += Games.get(i).GetSize();
            }

            for (int i = 0; i < Letters.size(); i++)
            {
                size += Letters.get(i).GetSize();
            }

            for (int i = 0; i < Notices.size(); i++)
            {
                size += Notices.get(i).GetSize();
            }

            return size;
        }
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		{
            try
            {
                super.GetBytes(bo);

                EncodeInteger(bo, Users.size());
                EncodeInteger(bo, Games.size());
                EncodeInteger(bo, Letters.size());
                EncodeInteger(bo, Notices.size());

                for (int i = 0; i < Users.size(); i++)
                    //Users[i].Body.GetBytes(bo);
                	Users.get(i).GetBytes(bo);

                for (int i = 0; i < Games.size(); i++)
                    Games.get(i).GetBytes(bo);

                for (int i = 0; i < Letters.size(); i++)
                    Letters.get(i).GetBytes(bo);

                for (int i = 0; i < Notices.size(); i++)
                    Notices.get(i).GetBytes(bo);
            }
            catch (Exception ex)
            {
                ex.printStackTrace(); 
            }
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		
		 super.FromBytes(is);

         int userCount = DecodeInteger(is);
         int gameCount = DecodeInteger(is);
         int boardCount = DecodeInteger(is);
         int noticeCount = DecodeInteger(is);

         for (int i = 0; i < userCount; i++)
         {
             UserInfo userInfo = new UserInfo();
             DecodeInteger(is);

             userInfo.FromBytes(is);
             Users.add(userInfo);
         }

         for (int i = 0; i < gameCount; i++)
         {
             GameInfo gameInfo = new GameInfo();
             DecodeInteger(is);

             gameInfo.FromBytes(is);
             Games.add(gameInfo);
         }

         for (int i = 0; i < boardCount; i++)
         {
             BoardInfo boardInfo = new BoardInfo();
             DecodeInteger(is);

             boardInfo.FromBytes(is);
             Letters.add(boardInfo);
         }

         for (int i = 0; i < noticeCount; i++)
         {
             BoardInfo noticeInfo = new BoardInfo();
             DecodeInteger(is);

             noticeInfo.FromBytes(is);
             Notices.add(noticeInfo);
         }
	}

}
