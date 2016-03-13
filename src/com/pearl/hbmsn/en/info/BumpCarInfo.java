package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class BumpCarInfo extends TableInfo {

	
    public int[]				m_lAllJettonScore = new int[BumperCarDefine.AREA_COUNT+1];
	
    
    public int[][]				m_lUserJettonScore = new int[BumperCarDefine.AREA_COUNT+1][BumperCarDefine.GAME_PLAYER];

    //
    public int						m_lAreaLimitScore = BumperCarDefine.AREA_LIMIT_SCORE;
    public int						m_lUserLimitScore = BumperCarDefine.USER_LIMIT_SCORE;
    public int						m_lApplyBankerCondition;

    //public int[]					m_lUserWinScore = new int[BumperCarDefine.GAME_PLAYER];
    public int[]					m_lUserReturnScore = new int[BumperCarDefine.GAME_PLAYER];
    public int[]					m_lUserRevenue = new int[BumperCarDefine.GAME_PLAYER];
    public int						m_cbLeftCardCount;
    public Boolean						m_bContiueCard;

    public int[]						m_cbCardCount = new int[1];
    public int[][]						m_cbTableCardArray = new int[1][1];

    public int							m_dwJettonTime;

    
    //public CWHArray<WORD>					m_ApplyUserArray;						//
    public int							m_wCurrentBanker = GameDefine.INVALID_CHAIR;
    public int							m_wBankerTime;
    public int						m_lBankerScore;
    public int						m_lBankerWinScore;
    public int						m_lBankerCurGameScore;
    public Boolean							m_bEnableSysBanker = true;


   
    public Boolean							m_bRefreshCfg;
    public int						m_StorageScore;
    public int						m_StorageDeduct;
    public String							m_szConfigFileName;
    public String							m_szGameRoomName;

    
    public int								m_nMaxChipRobot;
    public int								m_nChipRobotCount;
    public int						m_lRobotAreaLimit;
    public int						m_lRobotBetCount;
    public int[]						m_lRobotAreaScore = new int[BumperCarDefine.AREA_COUNT+1];

    public int						m_lBankerMAX;
    public int						m_lBankerAdd;

    public int						m_lBankerScoreMAX;
    public int						m_lBankerScoreAdd;

    public int						m_lPlayerBankerMAX;

    public Boolean							m_bExchangeBanker;


    public int							m_cbFreeTime = BumperCarDefine.TIME_FREE;
    public int							m_cbBetTime = BumperCarDefine.TIME_PLACE_JETTON;
    public int							m_cbEndTime = BumperCarDefine.TIME_GAME_END;

    public BumpCarInfo(){
    	_InfoType = InfoType.BumperCar;

        m_EndingTime = m_cbEndTime;

        // added by usc at 2014/03/21
        m_StorageScore = BumperCarDefine.FIRST_EVENT_SOCRE;
        m_StorageDeduct = 1;
    }
	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += (BumperCarDefine.AREA_COUNT + 1) * 4;
        size += 4;

        // added by usc at 2014/03/18
        size += _Players.size() * (BumperCarDefine.AREA_COUNT + 1) * 4;
        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
		 super.GetBytes(bo);

         for (int i = 0; i <= BumperCarDefine.AREA_COUNT; i++)
             EncodeInteger(bo, m_lAllJettonScore[i]);

         EncodeInteger(bo, m_cbTableCardArray[0][0]);

         // added by usc at 2014/03/18
         for (int i = 0; i < _Players.size(); i++)
             for (int j = 0; j <= BumperCarDefine.AREA_COUNT; j++)
                 EncodeInteger(bo, m_lUserJettonScore[j][i]);
	}

	@Override
	public void FromBytes(InputStream is) {
		 super.FromBytes(is);

         for (int i = 0; i <= BumperCarDefine.AREA_COUNT; i++)
             m_lAllJettonScore[i] = DecodeInteger(is);

         m_cbTableCardArray[0][0] = DecodeInteger(is);

         // added by usc at 2014/03/18
         for (int i = 0; i < _Players.size(); i++)
             for (int j = 0; j <= BumperCarDefine.AREA_COUNT; j++)
                 m_lUserJettonScore[j][i] = DecodeInteger(is);
	}

}
