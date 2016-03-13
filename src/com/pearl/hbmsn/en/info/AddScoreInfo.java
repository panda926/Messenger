package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class AddScoreInfo extends BaseInfo {

	public int								wCurrentUser;
    public int								wAddScoreUser;
    public int								lAddScoreCount;
    public int								lTurnLessScore;
    public int								lTurnMaxScore;
    public int								lAddLessScore;
    public int[]							cbShowHand = new int[DzCardDefine.GAME_PLAYER];
    
    public Boolean _isGiveUp = false;
    
	public AddScoreInfo() {
		_InfoType = InfoType.AddScore;
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize();

        size += EncodeCount(wCurrentUser);
        size += EncodeCount(wAddScoreUser);
        size += EncodeCount(lAddScoreCount);
        size += EncodeCount(lTurnLessScore);
        size += EncodeCount(lTurnMaxScore);
        size += EncodeCount(lAddLessScore);

        for (int i = 0; i < cbShowHand.length; i++)
            size += EncodeCount(cbShowHand[i]);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger( bo, wCurrentUser );
            EncodeInteger( bo, wAddScoreUser );
            EncodeInteger(bo, lAddScoreCount);
            EncodeInteger(bo, lTurnLessScore);
            EncodeInteger(bo, lTurnMaxScore);
            EncodeInteger(bo, lAddLessScore);

            for (int i = 0; i < cbShowHand.length; i++)
                EncodeInteger(bo, cbShowHand[i]);
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

            wCurrentUser = DecodeInteger(is);
            wAddScoreUser = DecodeInteger(is);
            lAddScoreCount = DecodeInteger(is);
            lTurnLessScore = DecodeInteger(is);
            lTurnMaxScore = DecodeInteger(is);
            lAddLessScore = DecodeInteger(is);

            for (int i = 0; i < cbShowHand.length; i++)
                cbShowHand[i] = DecodeInteger(is);
        }
        catch(Exception ex)
        { ex.printStackTrace();}
	}
	
	

}
