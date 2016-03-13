package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class PaymentInfo extends BaseInfo {

	public String strID = "";
    public String strAccountID = "";
    public String strAccountNumber = "";
    public int nPaymentMoney = 0;
    
	public PaymentInfo() {
		_InfoType = InfoType.Payment;
	}

	@Override
	public int GetSize() {
		int nSize = super.GetSize();

        nSize += EncodeCount(strID);
        nSize += EncodeCount(strAccountID);
        nSize += EncodeCount(strAccountNumber);
        nSize += EncodeCount(nPaymentMoney);

        return nSize;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeString(bo, strID);
            EncodeString(bo, strAccountID);
            EncodeString(bo, strAccountNumber);
            EncodeInteger(bo, nPaymentMoney);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        strID = DecodeString(is);
        strAccountID = DecodeString(is);
        strAccountNumber = DecodeString(is);
        nPaymentMoney = DecodeInteger(is);
	}

}
