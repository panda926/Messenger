package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class DiceInfo extends TableInfo {

	 public int[][] m_lUserScore = new int[GameDefine.GAME_PLAYER][4];

    //added by usc at 2014/04/03
    public int[] m_lPlayerBetAll = new int[HorseDefine.AREA_ALL];

    //
    public int[] m_enCards = new int[3];       // 
    public int[] m_bWinner = new int[4];       // 

    // added by usc at 2014/03/21
    public int m_StorageScore;
    public int m_StorageDeduct;
    
	public DiceInfo() {
		_InfoType = InfoType.Dice;
        
        m_EndingTime = 23;

        // added by usc at 2014/03/21
        m_StorageScore = DiceDefine.FIRST_EVENT_SCORE;
        m_StorageDeduct = 1;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += _Players.size() * 16;
        //size += m_lUserScore.Length * 4;

        // added newly
        size += m_lPlayerBetAll.length * 4;

        size += m_enCards.length * 4;
        size += m_bWinner.length * 4;

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            for (int i = 0; i < _Players.size(); i++)
            {
                for( int k = 0; k < 4; k++ )
                    EncodeInteger(bo, m_lUserScore[i][k]);
            }

            // added newly
            for (int i = 0; i < m_lPlayerBetAll.length; i++)
                EncodeInteger(bo, m_lPlayerBetAll[i]);

            for (int i = 0; i < m_enCards.length; i++)
                EncodeInteger(bo, m_enCards[i]);

            for (int i = 0; i < m_bWinner.length; i++)
                EncodeInteger(bo, m_bWinner[i]);
        }
        catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        for (int i = 0; i < this._Players.size(); i++)
        {
            for( int k = 0; k < 4; k++ )
                m_lUserScore[i][k] = DecodeInteger(is);
        }

        for (int i = 0; i < m_lPlayerBetAll.length; i++)
            m_lPlayerBetAll[i] = DecodeInteger(is);

        for (int i = 0; i < 3; i++)
            m_enCards[i] = DecodeInteger(is);

        for (int i = 0; i < 4; i++)
            m_bWinner[i] = DecodeInteger(is);
	}

}
