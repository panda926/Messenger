package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class TableInfo extends BaseInfo {

	public String _TableId = "";     

    public int _RoundIndex = 0;

    public Date _RoundStartTime;
    public int _RoundDelayTime;

    public ArrayList<UserInfo> _Players = new ArrayList<UserInfo>();
    public ArrayList<UserInfo> _Viewers = new ArrayList<UserInfo>();

    public int m_lMinScore;
    public int[] m_lUserBetScore = new int[GameDefine.GAME_PLAYER];
    public int[] m_lUserWinScore = new int[GameDefine.GAME_PLAYER];

    public int m_ReadyTime;
    public int m_EndingTime;

    public int m_nCashOrPointGame = 0;
    
	public TableInfo() {
		_InfoType = InfoType.Table;
	}

	@Override
	public int GetSize() {
		 int size = super.GetSize();

         size += EncodeCount(_TableId);
         size += EncodeCount((int)_RoundIndex);
         size += EncodeCount(_RoundDelayTime);
         
         size += 4;  // player count

         for (int i = 0; i < _Players.size(); i++)
             size += _Players.get(i).GetSize();

         // added by usc at 2014/04/03
         size += 4;  // viewer count

         for (int i = 0; i < _Viewers.size(); i++)
             size += _Viewers.get(i).GetSize();

         size += EncodeCount(m_lMinScore);
         size += _Players.size() * 4;     // winer

         size += EncodeCount(m_nCashOrPointGame);

         return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeString(bo, _TableId);
             EncodeInteger(bo, _RoundIndex);
             EncodeInteger(bo, _RoundDelayTime);

             EncodeInteger(bo, _Players.size());

             for (int i = 0; i < _Players.size(); i++)
                 _Players.get(i).GetBytes(bo);

             // added by usc at 2014/04/03
             EncodeInteger(bo, _Viewers.size());

             for (int i = 0; i < _Viewers.size(); i++)
                 _Viewers.get(i).GetBytes(bo);

             EncodeInteger(bo, m_lMinScore);

             for (int i = 0; i < _Players.size(); i++)
                 EncodeInteger(bo, m_lUserWinScore[i]);

             EncodeInteger(bo, m_nCashOrPointGame);

         }
         catch (Exception ex)
         {
             // Error Handling 
         }
	}

	@Override
	public void FromBytes(InputStream is) {
		
            super.FromBytes(is);

            _TableId = DecodeString(is);
            _RoundIndex = DecodeInteger(is);
            _RoundDelayTime = DecodeInteger(is);

            int playerCount = DecodeInteger(is);

            for (int i = 0; i < playerCount; i++)
            {
                UserInfo userInfo = (UserInfo)BaseInfo.CreateInstance(is);
                _Players.add(userInfo);
            }

            // added by usc at 2014/04/03
            int viewerCount = DecodeInteger(is);

            for (int i = 0; i < viewerCount; i++)
            {
                UserInfo userInfo = (UserInfo)BaseInfo.CreateInstance(is);
                _Viewers.add(userInfo);
            }

            m_lMinScore = DecodeInteger(is);

            for (int i = 0; i < playerCount; i++)
                m_lUserWinScore[i] = DecodeInteger(is);

            m_nCashOrPointGame = DecodeInteger(is);

        
	}

}
