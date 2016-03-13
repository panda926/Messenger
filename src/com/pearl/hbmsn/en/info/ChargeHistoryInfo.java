package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class ChargeHistoryInfo extends BaseInfo {

	public int Id = 0;
    public int Kind = 0;
    public int Cash = 0;
    public Date StartTime;
    public Date EndTime;
    public String OwnId = "";
    public String ApproveId = "";
    public int Complete = 0;
    public String BankAccount = "";

    private String[] _KindList = { "충전", "환전" };
    private String[] _CompleteList = { "신청", "승인", "취소" };
    
	public ChargeHistoryInfo() {
		_InfoType = InfoType.ChargeHistory;
	}
	
	public String GetKindString(){
		return _KindList[Kind];
	}
	public String GetCompleteString(){
		return _CompleteList[Complete];
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(Kind);
        size += EncodeCount(Cash);
        size += EncodeCount(ConvDateToString(StartTime));
        size += EncodeCount(ConvDateToString(EndTime));
        size += EncodeCount(OwnId);
        size += EncodeCount(ApproveId);
        size += EncodeCount(Complete);
        size += EncodeCount(BankAccount);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, Kind);
            EncodeInteger(bo, Cash);
            EncodeString(bo, ConvDateToString( StartTime ));
            EncodeString(bo, ConvDateToString( EndTime ));
            EncodeString(bo, OwnId);
            EncodeString(bo, ApproveId);
            EncodeInteger(bo, Complete);
            EncodeString(bo, BankAccount);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        Kind = DecodeInteger(is);
        Cash = DecodeInteger(is);
        StartTime = ToDateTime(DecodeString(is));
        EndTime = ToDateTime(DecodeString(is));
        OwnId = DecodeString(is);
        ApproveId = DecodeString(is);
        Complete = DecodeInteger(is);
        BankAccount = DecodeString(is);
	}

}
