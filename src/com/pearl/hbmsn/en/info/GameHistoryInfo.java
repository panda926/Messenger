package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class GameHistoryInfo extends BaseInfo {

	public int Id = 0;

    public String GameId = "";
    public String BuyerId = "";
    public String OfficerId = "";
    public String ManagerId = "";

    public String RecommenderId = "";
    public Date StartTime;
    public Date EndTime;
    public int BuyerTotal = 0;
    public int RecommenderTotal = 0;
    public int RecommendOfficerTotal = 0;
    public int ManagerTotal = 0;
    
    // added newly
    public int AdminTotal = 0;

    public String GameSource = "";
    
	public GameHistoryInfo() {
		_InfoType = InfoType.GameHistory;
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize();

        size += EncodeCount(GameId);
        size += EncodeCount(GameSource);
        size += EncodeCount(BuyerId);
        size += EncodeCount(RecommenderId);
        size += EncodeCount(ConvDateToString(StartTime));
        size += EncodeCount(ConvDateToString(EndTime));
        size += EncodeCount(BuyerTotal);
        size += EncodeCount(RecommenderTotal);
        size += EncodeCount(RecommendOfficerTotal);
        size += EncodeCount(ManagerTotal);
        size += EncodeCount(AdminTotal);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, GameId );
            EncodeString(bo, GameSource);
            EncodeString(bo, BuyerId);
            EncodeString(bo, RecommenderId);
            EncodeString(bo, ConvDateToString(StartTime));
            EncodeString(bo, ConvDateToString(EndTime));
            EncodeInteger(bo, BuyerTotal);
            EncodeInteger(bo, RecommenderTotal);
            EncodeInteger(bo, RecommendOfficerTotal);
            EncodeInteger(bo, ManagerTotal);
            EncodeInteger(bo, AdminTotal);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        GameId = DecodeString(is);
        GameSource = DecodeString(is);
        BuyerId = DecodeString(is);
        RecommenderId = DecodeString(is);
        StartTime = ToDateTime(DecodeString(is));
        EndTime = ToDateTime(DecodeString(is));
        BuyerTotal = DecodeInteger(is);
        RecommenderTotal = DecodeInteger(is);
        RecommendOfficerTotal = DecodeInteger(is);
        ManagerTotal = DecodeInteger(is);
        AdminTotal = DecodeInteger(is);
	}

}
