package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class DzCardInfo extends TableInfo {

    public int							m_wDUser;
    public int							m_wCurrentUser;
    public UserInfo[]                   m_Seatter = new UserInfo[DzCardDefine.GAME_PLAYER];

    public boolean[]					m_cbPlayStatus = new boolean[DzCardDefine.GAME_PLAYER];

    public int							m_lCellScore;
    public int							m_lTurnLessScore;
    public int							m_lAddLessScore;
    public int							m_lTurnMaxScore;
    public int							m_lBalanceScore;
    public int							m_wOperaCount;
    public int							m_cbBalanceCount;
    public int[]						m_lTableScore = new int[DzCardDefine.GAME_PLAYER];
    public int[]						m_lTotalScore = new int[DzCardDefine.GAME_PLAYER];
    public int[]						m_lUserMaxScore = new int[DzCardDefine.GAME_PLAYER];
    public boolean[]					m_cbShowHand = new boolean[DzCardDefine.GAME_PLAYER];

    public int					m_cbSendCardCount;
    public int[]				m_cbCenterCardData = new int[DzCardDefine.MAX_CENTERCOUNT];
    public int[][]              m_cbHandCardData = new int[DzCardDefine.GAME_PLAYER][];

    public int cbTotalEnd = 0;
    public int[] m_GameScore = new int[DzCardDefine.GAME_PLAYER];

    public int[][] m_cbOverCardData = new int[DzCardDefine.GAME_PLAYER][];
	
    public DzCardInfo() {
        _InfoType = InfoType.DzCard;

        for (int i = 0; i < m_cbHandCardData.length; i++)
            m_cbHandCardData[i] = new int[DzCardDefine.MAX_COUNT];

        for (int i = 0; i < m_cbOverCardData.length; i++)
            m_cbOverCardData[i] = new int[DzCardDefine.MAX_CENTERCOUNT];

        m_ReadyTime = 10;
        m_EndingTime = 15;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(m_wDUser);
        size += EncodeCount(m_wCurrentUser);

        for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
        {
            String userName = "";

            if (m_Seatter[i] != null)
                userName = m_Seatter[i].Id;

            size += EncodeCount(userName);
        }
        
        for (int i = 0; i < m_cbPlayStatus.length; i++){
        	int value = (m_cbPlayStatus[i])?1:0;//Boolean to Int
        	size += EncodeCount(value);
        }

        size += EncodeCount(m_lCellScore);
        size += EncodeCount(m_lTurnLessScore);
        size += EncodeCount(m_lAddLessScore);
        size += EncodeCount(m_lTurnMaxScore);
        size += EncodeCount(m_lBalanceScore);
        size += EncodeCount(m_wOperaCount);
        size += EncodeCount(m_cbBalanceCount);

        for (int i = 0; i < m_lTableScore.length; i++)
            size += EncodeCount(m_lTableScore[i]);

        for (int i = 0; i < m_lTotalScore.length; i++)
            size += EncodeCount(m_lTotalScore[i]);

        for (int i = 0; i < m_lUserMaxScore.length; i++)
            size += EncodeCount(m_lUserMaxScore[i]);

        for( int i = 0; i < m_cbShowHand.length; i++ ){
            int value = (m_cbShowHand[i])?1:0;
        	size += EncodeCount(value);
        }

        size += EncodeCount(m_cbSendCardCount);

        for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
            for (int k = 0; k < DzCardDefine.MAX_COUNT; k++)
                size += EncodeCount(m_cbHandCardData[i][k]);

        for (int i = 0; i < m_cbCenterCardData.length; i++)
            size += EncodeCount(m_cbCenterCardData[i]);

        size += EncodeCount(cbTotalEnd);

        for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
            size += EncodeCount(m_GameScore[i]);

        for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
            for (int k = 0; k < DzCardDefine.MAX_CENTERCOUNT; k++)
                size += EncodeCount(m_cbOverCardData[i][k]);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, m_wDUser);
            EncodeInteger(bo, m_wCurrentUser);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
            {
                String userName = "";

                if (m_Seatter[i] != null)
                    userName = m_Seatter[i].Id;

                EncodeString( bo, userName);
            }

            for (int i = 0; i < m_cbPlayStatus.length; i++){
            	int value = (m_cbPlayStatus[i])?1:0;
            	EncodeInteger(bo, value);
            }

            EncodeInteger(bo, m_lCellScore);
            EncodeInteger(bo, m_lTurnLessScore);
            EncodeInteger(bo, m_lAddLessScore);
            EncodeInteger(bo, m_lTurnMaxScore);
            EncodeInteger(bo, m_lBalanceScore);
            EncodeInteger(bo, m_wOperaCount);
            EncodeInteger(bo, m_cbBalanceCount);

            for (int i = 0; i < m_lTableScore.length; i++)
                EncodeInteger(bo, m_lTableScore[i]);

            for (int i = 0; i < m_lTotalScore.length; i++)
                EncodeInteger(bo, m_lTotalScore[i]);

            for (int i = 0; i < m_lUserMaxScore.length; i++)
                EncodeInteger(bo, m_lUserMaxScore[i]);

            for (int i = 0; i < m_cbShowHand.length; i++){
                int value = (m_cbShowHand[i])?1:0;
            	EncodeInteger(bo, value);
            }

            EncodeInteger(bo, m_cbSendCardCount);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
                for (int k = 0; k < DzCardDefine.MAX_COUNT; k++)
                    EncodeInteger(bo, m_cbHandCardData[i][k]);

            for (int i = 0; i < m_cbCenterCardData.length; i++)
                EncodeInteger(bo, m_cbCenterCardData[i]);

            EncodeInteger(bo, cbTotalEnd);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
                EncodeInteger(bo, m_GameScore[i]);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
                for (int k = 0; k < DzCardDefine.MAX_CENTERCOUNT; k++)
                    EncodeInteger(bo, m_cbOverCardData[i][k]);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		try
        {
            super.FromBytes(is);

            m_wDUser = DecodeInteger(is);
            m_wCurrentUser = DecodeInteger(is);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
            {
                String userName = DecodeString(is);

                for (UserInfo userInfo : _Players)
                {
                    if (userInfo != null && userName == userInfo.Id)
                    {
                        m_Seatter[i] = userInfo;
                        break;
                    }
                }
            }

            for (int i = 0; i < m_cbPlayStatus.length; i++){
            	boolean b = (DecodeInteger(is) != 0);//int to Boolean
            	m_cbPlayStatus[i] = b;
            }
                

            m_lCellScore = DecodeInteger(is);
            m_lTurnLessScore = DecodeInteger(is);
            m_lAddLessScore = DecodeInteger(is);
            m_lTurnMaxScore = DecodeInteger(is);
            m_lBalanceScore = DecodeInteger(is);
            m_wOperaCount = DecodeInteger(is);
            m_cbBalanceCount = DecodeInteger(is);


            for (int i = 0; i < m_lTableScore.length; i++)
                m_lTableScore[i] = DecodeInteger(is);

            for (int i = 0; i < m_lTotalScore.length; i++)
                m_lTotalScore[i] = DecodeInteger(is);

            for (int i = 0; i < m_lUserMaxScore.length; i++)
                m_lUserMaxScore[i] = DecodeInteger(is);

            for (int i = 0; i < m_cbShowHand.length; i++){
            	boolean b = (DecodeInteger(is) != 0);
            	m_cbShowHand[i] = b;
            }

            m_cbSendCardCount = DecodeInteger(is);

            for( int i = 0; i < DzCardDefine.GAME_PLAYER; i++ )
                for( int k = 0; k < DzCardDefine.MAX_COUNT; k++ )
                    m_cbHandCardData[i][k] = DecodeInteger(is);

            for (int i = 0; i < m_cbCenterCardData.length; i++)
                m_cbCenterCardData[i] = DecodeInteger(is);

            cbTotalEnd = DecodeInteger(is);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
                m_GameScore[i] = DecodeInteger(is);

            for (int i = 0; i < DzCardDefine.GAME_PLAYER; i++)
                for (int k = 0; k < DzCardDefine.MAX_CENTERCOUNT; k++)
                    m_cbOverCardData[i][k] = DecodeInteger(is);

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
	}

}
