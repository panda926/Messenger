package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class EvalHistoryInfo extends BaseInfo {

	public int Id = 0;
    public String OwnId = "";
    public String BuyerId = "";
    public int Value = 0;
    public Date EvalTime;
    
	public EvalHistoryInfo() {
		_InfoType = InfoType.EvalHistory;
	}

	@Override
	public int GetSize() {
		 return super.GetSize() + EncodeCount(OwnId) + EncodeCount(BuyerId) + EncodeCount(Value) + EncodeCount( ConvDateToString( EvalTime ));
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeString(bo, OwnId);
             EncodeString(bo, BuyerId);
             EncodeInteger(bo, Value);
             EncodeString(bo, ConvDateToString( EvalTime ));
         }
         catch (Exception ex)
         {
             ex.printStackTrace();
         }
	}

	@Override
	public void FromBytes(InputStream is) {
		
		super.FromBytes(is);

        OwnId = DecodeString(is);
        BuyerId = DecodeString(is);
        Value = DecodeInteger(is);
        EvalTime = ToDateTime(DecodeString(is));
	}

}
